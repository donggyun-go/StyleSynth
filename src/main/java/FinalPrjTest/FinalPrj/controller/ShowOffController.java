package FinalPrjTest.FinalPrj.controller;

import FinalPrjTest.FinalPrj.domain.*;
import FinalPrjTest.FinalPrj.repository.ShowOffRepository;
import FinalPrjTest.FinalPrj.service.CommentService;
import FinalPrjTest.FinalPrj.service.DesignerService;
import FinalPrjTest.FinalPrj.service.ShowOffService;
import FinalPrjTest.FinalPrj.utils.CommentDTO;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

//import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title;

@Controller
public class ShowOffController {

    private final ShowOffService showoffService;

    private final CommentService commentService;

    @Autowired
    public ShowOffController(ShowOffService showoffService, CommentService commentService) {
        this.showoffService = showoffService;
        this.commentService = commentService;
    }

    @GetMapping("/post")
    public String Post(HttpSession session) {
        String id = (String) session.getAttribute("userId");
        String result ="";
        if(id==null){
            result="Login/login";
        } else{
            result="ShowOffBoard/write";
        }
        return result;
    } //여기까지 그냥 페이지 이동


    @PostMapping("/write")
    public String write(HttpSession session,
                        @RequestParam String title,
                        @RequestParam String content,
                        @RequestParam(value = "file", required = false) MultipartFile file,
                        @RequestParam(value = "file2", required = false) MultipartFile file2,
                        RedirectAttributes redirectAttributes,
                        ShowOffRepository showOffRepository) {

        String id = (String) session.getAttribute("userId");
        int membertype = (int) session.getAttribute("membertype");

        String name = "";
        Integer dtype = 0;
        if (membertype == 1) {
            Customer customer = showoffService.findByIdCustomer(id);
            if (customer != null) {
                name = customer.getName();
                dtype = customer.getMemberType();
            }
        } else if (membertype == 2) {
            Designer designer = showoffService.findByIdDesigner(id);
            if (designer != null) {
                name = designer.getName();
                dtype = designer.getMemberType();
            }
        }

        String uploadDir = "uploads/photos";
        try {
            // 파일 저장
            String fileName = null;
            if (file != null && !file.isEmpty()) {
                // 파일 유효성 검사
                if (!file.getContentType().startsWith("image")) {
                    redirectAttributes.addFlashAttribute("message", "이미지 파일만 업로드 가능합니다.");
                    return "redirect:/";
                }
                fileName = showoffService.store(file);
                Path uploadPath = Paths.get(uploadDir, fileName);
                File uploadFile = new File(uploadPath.toAbsolutePath().toString());
                FileUtils.forceMkdirParent(uploadFile);
                file.transferTo(uploadFile);
            }

            String fileName2 = null;
            if (file2 != null && !file2.isEmpty()) {
                // 파일 유효성 검사
                if (!file2.getContentType().startsWith("image")) {
                    redirectAttributes.addFlashAttribute("message", "이미지 파일만 업로드 가능합니다.");
                    return "redirect:/";
                }
                fileName2 = showoffService.store(file2);
                Path uploadPath2 = Paths.get(uploadDir, fileName2);
                File uploadFile2 = new File(uploadPath2.toAbsolutePath().toString());
                FileUtils.forceMkdirParent(uploadFile2);
                file2.transferTo(uploadFile2);
            }

            if (id == null) {
                redirectAttributes.addFlashAttribute("message", "로그인 후에 글을 작성할 수 있습니다.");
                return "redirect:/login"; // 로그인 페이지로 리다이렉트
            }

            // DB에 이미지 파일 경로 저장
            String imageFilePath = fileName;
            String imageFilePath2 = fileName2;
            ShowOff showOff = new ShowOff();
            showOff.setId(id);
            showOff.setTitle(title);
            showOff.setContent(content);
            showOff.setCreatedDate(LocalDateTime.now());
            showOff.setModifiedDate(LocalDateTime.now());
            showOff.setImg1_path(imageFilePath);
            showOff.setImg2_path(imageFilePath2);
            showOff.setName(name);
            showOff.setDtype(dtype);
            showoffService.save(showOff);

            redirectAttributes.addFlashAttribute("message", "이미지를 업로드하였습니다.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "이미지 업로드에 실패하였습니다.");
            e.printStackTrace();
        }
        return "redirect:/imageList";
    }


    @GetMapping("/imageList")
    public String imageList(HttpSession session, Model model, @RequestParam(value = "vpage", defaultValue = "1") int vpage,
                            @RequestParam(value = "id", required = false) String id,
                            @RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "searchType", required = false) String searchType,
                            @RequestParam(value = "search", required = false) String search,
                            @RequestParam(value = "dtype", defaultValue = "3") int dtype) {

        //int membertype = (int) session.getAttribute("membertype");

        List<ShowOff> showoffs=null;
        List<ShowOff> showoffSize=null;

        if (dtype == 1) {
            if (searchType != null && search != null && !search.isEmpty()) {
                if (searchType.equals("all")) {
                    // 모든 조건으로 조회하는 경우
                    showoffs = showoffService.findLikeAllShowOffDtype(dtype, search, (vpage - 1) * 20);
                    showoffSize = showoffService.countLikeAllShowOffDtype(dtype, search);
                } else if (searchType.equals("id")) {
                    // ID로 조회하는 경우
                    showoffs = showoffService.findLikeNameShowOffDtype(dtype, search, (vpage - 1) * 20);
                    showoffSize = showoffService.countLikeNameShowOffDtype(dtype, search);
                } else if (searchType.equals("title")) {
                    // 제목으로 조회하는 경우
                    showoffs = showoffService.findLikeTitleShowOffDtype(dtype, search, (vpage - 1) * 20);
                    showoffSize = showoffService.countLikeTitleShowOffDtype(dtype, search);
                } else {
                    // 검색 조건이 잘못된 경우
                    showoffs = showoffService.findAllShowOffPageDtype(dtype, (vpage - 1) * 20);
                    showoffSize = showoffService.findAllShowOffDtype(dtype);
                }
                /*model.addAttribute("searchType", searchType);
                model.addAttribute("search", search);*/
            } else {
                // 검색 조건이 없는 경우
                showoffs = showoffService.findAllShowOffPageDtype(dtype, (vpage - 1) * 20);
                showoffSize = showoffService.findAllShowOffDtype(dtype);
            }

        } else if (dtype == 2) {
            if (searchType != null && search != null && !search.isEmpty()) {
                if (searchType.equals("all")) {
                    // 모든 조건으로 조회하는 경우
                    showoffs = showoffService.findLikeAllShowOffDtype(dtype, search, (vpage - 1) * 20);
                    showoffSize = showoffService.countLikeAllShowOffDtype(dtype, search);
                } else if (searchType.equals("id")) {
                    // ID로 조회하는 경우
                    showoffs = showoffService.findLikeNameShowOffDtype(dtype, search, (vpage - 1) * 20);
                    showoffSize = showoffService.countLikeNameShowOffDtype(dtype, search);
                } else if (searchType.equals("title")) {
                    // 제목으로 조회하는 경우
                    showoffs = showoffService.findLikeTitleShowOffDtype(dtype, search, (vpage - 1) * 20);
                    showoffSize = showoffService.countLikeTitleShowOffDtype(dtype, search);
                } else {
                    // 검색 조건이 잘못된 경우
                    showoffs = showoffService.findAllShowOffPageDtype(dtype, (vpage - 1) * 20);
                    showoffSize = showoffService.findAllShowOffDtype(dtype);
                }
                /*model.addAttribute("searchType", searchType);
                model.addAttribute("search", search);*/
            } else {
                // 검색 조건이 없는 경우
                showoffs = showoffService.findAllShowOffPageDtype(dtype, (vpage - 1) * 20);
                showoffSize = showoffService.findAllShowOffDtype(dtype);
            }
        } else if (dtype == 3) {
            if (searchType != null && search != null && !search.isEmpty()) {
                if (searchType.equals("all")) {
                    // 모든 조건으로 조회하는 경우
                    showoffs = showoffService.findLikeAllShowOff(search, (vpage - 1) * 20);
                    showoffSize = showoffService.countLikeAllShowOff(search);
                } else if (searchType.equals("id")) {
                    // ID로 조회하는 경우
                    showoffs = showoffService.findLikeNameShowOff(search, (vpage - 1) * 20);
                    showoffSize = showoffService.countLikeNameShowOff(search);
                } else if (searchType.equals("title")) {
                    // 제목으로 조회하는 경우
                    showoffs = showoffService.findLikeTitleShowOff(search, (vpage - 1) * 20);
                    showoffSize = showoffService.countLikeTitleShowOff(search);
                } else {
                    // 검색 조건이 잘못된 경우
                    showoffs = showoffService.findAllShowOffPage((vpage - 1) * 20);
                    showoffSize = showoffService.findAllShowOff();
                }
                /*model.addAttribute("searchType", searchType);
                model.addAttribute("search", search);*/
            } else {
                // 검색 조건이 없는 경우
                showoffs = showoffService.findAllShowOffPage((vpage - 1) * 20);
                showoffSize = showoffService.findAllShowOff();
            }

        }
        model.addAttribute("searchType", searchType);
        model.addAttribute("search", search);
        model.addAttribute("dtype", dtype);

        int showoffCount = showoffSize.size();  //designerCount는 수(int형)
        System.out.println(showoffCount);
        int pageCount = (showoffCount + 19) / 20;
        model.addAttribute("ListSize", pageCount);
        model.addAttribute("showoffs", showoffs);
        model.addAttribute("vpage", vpage);

        return "ShowOffBoard/imageList";
    }


    private Integer getMemberType(Integer memberType) {
        // memberType이 null이면 기본값인 1로 설정
        return memberType == null ? 1 : memberType;
    }

    // 게시글 상세 페이지
    @GetMapping("/edit/{seq}")
    public String detail(@PathVariable int seq, Model model, HttpSession session, @RequestParam(value = "vpage", defaultValue = "1") int vpage) {
        showoffService.updateViews(seq);
        ShowOff showOff = showoffService.findBySeq(seq);
        model.addAttribute("test", showoffService.findBySeq(seq));
        System.out.println("id"+showOff.getId());

        //model.addAttribute("countHeart", showoffService.countHeart(seq));
        List<Likes> likes = showoffService.findLikeList(seq);
        boolean alreadyHeart = false;
        if (session.getAttribute("userId") != null) {
            String userId = session.getAttribute("userId").toString();
            for (Likes like : likes) {
                if (like.getUser().equals(userId)) {
                    alreadyHeart = true;
                    break;
                }
            }
        }
        model.addAttribute("likes", likes);
        model.addAttribute("alreadyHeart", alreadyHeart);

        /*댓글*/
//        List<CommentDTO> commentDTOList = commentService.findAll(seq);
//        model.addAttribute("commentList", commentDTOList);

        /*댓글 페이징*/
        List<Comment> showoffSize;
        List<Comment> showoffs;

        // 검색 조건이 없는 경우
        showoffSize = showoffService.findAllShowOffC(seq);
        showoffs = showoffService.findAllShowOffPageC(seq,(vpage - 1) * 10);

//        for (Comment comment : showoffs) {
//            String writer = comment.getCommentWriter();
//            String maskedWriter = writer.substring(0, 2) + "*".repeat(writer.length() - 2);
//            comment.setCommentWriter(maskedWriter);
//        }

        int showoffCount = showoffSize.size();  //designerCount는 수(int형)
        System.out.println(showoffCount);
        int pageCount = (showoffCount + 9) / 10;
        model.addAttribute("ListSize", pageCount);  //전체 데이터 개수.
        model.addAttribute("showoffs", showoffs);  //인덱스 첫 번호
        model.addAttribute("vpage", vpage);  //페이징 수:1,2,3
        return "ShowOffBoard/detail";
    }
    @GetMapping("/{seq}")
    public String edit(@PathVariable int seq, Model model) {
        model.addAttribute("test", showoffService.findBySeq(seq));
        return "ShowOffBoard/edit";
    }
    /*@PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/";
    }*/

    @PostMapping("/update/{seq}")
    public String update(@PathVariable("seq") int seq, ShowOff edit, @RequestParam("file") MultipartFile file){

        ShowOff temp = showoffService.findBySeq(seq);
        temp.setTitle(edit.getTitle());
        temp.setContent(edit.getContent());
        temp.setImg1_path(saveFile(file, "C:/img/photos"));

        showoffService.write(temp);
        return "redirect:/imageList";
    }

    @PostMapping("/deletePost/{seq}")
    public String deletePost(@PathVariable("seq") int seq){
        showoffService.deletePost(seq);
        return "redirect:/imageList";
    }

    public String saveFile(MultipartFile file, String path) {
        try {
            String originalFileName = file.getOriginalFilename();  // 업로드된 파일의 원본 파일 이름을 가져옵니다.
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);  // 파일 확장자를 추출합니다.
            String fileName = UUID.randomUUID().toString() + "." + fileExtension;  // UUID를 사용하여 유니크한 파일 이름을 생성합니다.
            Path filePath = Paths.get(path, fileName);  // 저장 경로와 파일 이름을 결합하여 파일의 저장 경로를 생성합니다.
            Files.write(filePath, file.getBytes());  // MultipartFile에서 바이트 배열 형태로 파일 내용을 읽어와서 해당 경로에 파일을 저장합니다.
            return fileName;  // 생성된 파일 이름을 반환합니다.
        } catch (IOException e
        ) {
            e.printStackTrace();
            return null;
        }
    }

    /*@PostMapping("/update/{seq}")
    public String update(@ModelAttribute Test test, Model model){
        Test testtemp = showoffService.update(test);
        model.addAttribute("testtemp", testtemp);
        return "edit";
    }*/

    /*@PostMapping("/update/{seq}")
    public String boardUpdate(@PathVariable("seq") int seq, testboard board){
        //기존에있던글이 담겨져서온다.
        testboard boardTemp = boardService.boardview(seq);

        //기존에있던 내용을 새로운 내용으로 덮어씌운다.
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp); //추가 → 수정한내용을 boardService의 write부분에 넣기
        return "redirect:/board/list";
    }*/

    /* 게시물 좋아요 */

    @PostMapping(value="/heart")
    public ResponseEntity<String> addHeart(@RequestParam("showOff") int showOff, HttpServletRequest request) {

        System.out.println("메롱ㅁ얾ㅇ라ㅓㅁㄴㅇ로면ㄻㄴㅇㄹㅊㅁㅎㅁ");
       /* // showOff 값이 "undefined"인 경우 처리
        if (showOff == null || showOff.equals("undefined")) {
            // 오류 응답 또는 기본 값 설정 등
            return new ResponseEntity<>("Invalid showOff value", HttpStatus.BAD_REQUEST);
        }*/
        System.out.println("여기확인해봐!!!!!!!!!!!"+showOff);

        Likes likes = new Likes(); // Likes 객체 생성
        ShowOff showoff = showoffService.findBySeq(showOff);

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        int membertype = (int) session.getAttribute("membertype");
        //likes likes = new likes(); // likes 객체 생성

        if(membertype==1){
            Customer customer = showoffService.findByIdCustomer(userId);
            likes.setUser(customer.getId());
            System.out.println(customer.getId()+"아이디아이디");

        }else if(membertype == 2) {
            Designer designer = showoffService.findByIdDesigner(userId);
            likes.setUser(designer.getId());
            System.out.println(designer.getId()+"아이디아이디");
        }


        //int showOff = Integer.parseInt(requestBody.get("showOff").toString());
        //int showOffId = Integer.parseInt(showOff);
        HashMap<String, Object> map = new HashMap<>();
        map.put("showOff", showoff);
        map.put("userId", userId);
        int alreadyHeart = showoffService.searchHeart(map);
        boolean flag = false;
        String result = "";
        if (alreadyHeart == 0) {
            flag = showoffService.addHeart(map);
            result = "add";
        } else {
            flag = showoffService.deleteHeart(map);
            result = "delete";
        }
        if (flag) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        //return null;
    }

    public int searchHeart(int showOff, String userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("showOff", showOff);
        map.put("userId", userId);
        int alreadyHeart = showoffService.searchHeart(map);

        return alreadyHeart;
    }



}