package FinalPrjTest.FinalPrj.controller;

import FinalPrjTest.FinalPrj.domain.*;
import FinalPrjTest.FinalPrj.service.CommentService;
import FinalPrjTest.FinalPrj.service.ShowOffService;
import FinalPrjTest.FinalPrj.utils.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private  CommentService commentService;
    private  ShowOffService showoffService;
    @Autowired
    public CommentController(CommentService commentService, ShowOffService showoffService) {
        this.commentService = commentService;
        this.showoffService = showoffService;
    }

    //    @PostMapping("/save")  //ajax에서 요청한걸 여기서 받는다(='/save' 주소로 받는다고 표현하기도 함)
//        public ResponseEntity save(@ModelAttribute CommentDTO commentDTO, HttpServletRequest request) {
//
//        HttpSession session = request.getSession();
//        String userId = (String) session.getAttribute("userId");
//        // 세션에서 가져온 userId 값을 CommentDTO 객체에 설정
//        commentDTO.setCommentWriter(userId);
//
//        System.out.println("commentDTO.getTestSeq()= "+commentDTO.getTestSeq());
//        System.out.println("commentDTO = " + commentDTO);
//        int saveResult = commentService.save(commentDTO);
//        List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getTestSeq());
//        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
//    }
    @PostMapping("/save")  //ajax에서 요청한걸 여기서 받는다(='/save' 주소로 받는다고 표현하기도 함)
    public ResponseEntity save(@ModelAttribute Comment comment, HttpServletRequest request, @RequestParam("testSeq")int Sseq, Model model) {

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        int membertype = (int) session.getAttribute("membertype");

        if(membertype==1){
            Customer customer = showoffService.findByIdCustomer(userId);
            comment.setCommentName(customer.getName());

        }else if(membertype == 2) {
            Designer designer = showoffService.findByIdDesigner(userId);
            comment.setCommentName(designer.getName());
        }

        comment.setCommentWriter(userId);
        comment.setSseq(Sseq);
        comment.setCreatedDate(LocalDateTime.now());
        commentService.newsave(comment);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

//    @GetMapping("/{testSeq}")
//    public ResponseEntity<List<CommentDTO>> getCommentsByTestSeq(@PathVariable int testSeq) {
//        List<CommentDTO> commentDTOList = commentService.findAll(testSeq);
//        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
//    }
@ResponseBody
@PostMapping("/delete")
public void delete(@RequestParam("commentSeq") int seq, HttpServletRequest request, Model model) {
        /*HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        // 해당 댓글 조회
        Comment comment = commentService.findBySeqComment(seq);
        if (comment != null && comment.getCommentWriter().equals(userId)) {
            // 해당 댓글이 속해 있는 게시글의 식별자를 가져옴
            int showOffId = comment.getSseq();

            // 게시글에 해당하는 댓글 목록을 조회
            List<Comment> comments = commentService.findBySeqCommentL(seq, showOffId);

            // 댓글 목록 중 본인이 작성한 댓글을 삭제
            for (Comment c : comments) {
                if (c.getCommentWriter().equals(userId)) {
                    commentService.delete(c);
                }
            }
            //commentService.delete(comment);
            return new ResponseEntity<>("댓글이 삭제되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 댓글을 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }*/
    System.out.println("뭐가 올까"+seq);
    commentService.deleteBySeq(seq);
}

    @ResponseBody
    @PostMapping("/update")
    public void update(@RequestParam("commentSeq") int seq, @RequestParam("editedContent")String editedContent) {
        commentService.updateBySeq(seq, editedContent);
    }

}