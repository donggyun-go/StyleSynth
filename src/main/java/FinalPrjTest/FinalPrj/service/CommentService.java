package FinalPrjTest.FinalPrj.service;

import FinalPrjTest.FinalPrj.domain.Comment;
import FinalPrjTest.FinalPrj.domain.ShowOff;
import FinalPrjTest.FinalPrj.repository.FPCommentRepository;
import FinalPrjTest.FinalPrj.repository.FPShowOffRepository;
import FinalPrjTest.FinalPrj.utils.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Transactional
public class CommentService {

    @Autowired
    private FPCommentRepository FPCommentRepository;
    @Autowired
    private FPShowOffRepository FPShowOffRepository;

    public CommentService(FPCommentRepository FPCommentRepository) {
        this.FPCommentRepository = FPCommentRepository;
    }
    public CommentService(FPShowOffRepository FPShowOffRepository) {
        this.FPShowOffRepository = FPShowOffRepository;
    }

//    public int save(CommentDTO commentDTO){
//        System.out.println("commentDTO.getTestSeq()= "+commentDTO.getTestSeq());
//        //부모엔티티 조회
//        Optional<ShowOff> OptionalShowOff = FPShowOffRepository.findBySeq(commentDTO.getTestSeq());
//        System.out.println("FPShowOffRepository.findBySeq(commentDTO.getTestSeq()= " + FPShowOffRepository.findBySeq(commentDTO.getTestSeq()));
//            ShowOff showOff = OptionalShowOff.get();
//            //DTO로 받아온걸 엔티티로 변환하는 작업을 해야함.
//            Comment comment = Comment.toSaveEntity(commentDTO, showOff);
//            return FPCommentRepository.save(comment).getSeq();
//    }
//    public List<CommentDTO> findAll(int testSeq){
//       ShowOff showOff = FPShowOffRepository.findBySeq(testSeq).get();
//       List<Comment> commentList = FPCommentRepository.findAllByShowOffOrderBySeqDesc(showOff);
//       List<CommentDTO> commentDTOList = new ArrayList<>();
//       for (Comment comment: commentList){
//          CommentDTO commentDTO = CommentDTO.toCommentDTO(comment, testSeq);
//           commentDTOList.add(commentDTO);
//       }
//        return commentDTOList;
//    }

    public void newsave(Comment comment) {
        FPCommentRepository.newsave(comment);
    }

    public Comment findBySeqComment(int seq) {
        Comment seqcomment = FPCommentRepository.findBySeqComment(seq);
        return seqcomment;
    }
    public List<Comment> findBySeqCommentL(int seq, int showOffId) {
        List<Comment> seqcomment = FPCommentRepository.findBySeqCommentL(seq, showOffId);
        return seqcomment;
    }

    public void delete(Comment comment) {
        FPCommentRepository.delete(comment);
    }

    public void deleteBySeq(int seq) {
        FPCommentRepository.deleteBySeq(seq);
    }

    public void updateBySeq(int seq, String editedContent) {
        FPCommentRepository.updateBySeq(seq, editedContent);
    }
}