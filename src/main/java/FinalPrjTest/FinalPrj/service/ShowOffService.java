package FinalPrjTest.FinalPrj.service;

import FinalPrjTest.FinalPrj.domain.*;
import FinalPrjTest.FinalPrj.repository.FPDesignerRepository;
import FinalPrjTest.FinalPrj.repository.FPShowOffRepository;
import FinalPrjTest.FinalPrj.repository.ShowOffRepository;
import FinalPrjTest.FinalPrj.utils.CommentDTO;
import org.apache.catalina.User;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Transactional
public class ShowOffService {
    private FPShowOffRepository FPShowOffRepository;
    private int seq;

    public ShowOffService(FPShowOffRepository FPShowOffRepository) {
        this.FPShowOffRepository = FPShowOffRepository;
    }

    public void write(ShowOff showOff) {
        FPShowOffRepository.write(showOff);
    }
    /*public List<ShowOff> getShowOffList() {
        return FPShowOffRepository.findAll();
    }*/
    public void save(ShowOff showOff) {
        FPShowOffRepository.save(showOff);
    }

/*    public List<ShowOff> findAll() {  ////일단 페이징할땐 쓰지말아봐.
        return FPShowOffRepository.findAll();
    }*/

    public List<ShowOff> findAllShowOff(){
        List<ShowOff> showOff = FPShowOffRepository.findAllShowOff();
        return showOff;
    }
    public List<ShowOff> findAllShowOffPage(int vpage){
        List<ShowOff> showOff = FPShowOffRepository.findAllShowOffPage(vpage);
        return showOff;
    }

    public List<ShowOff> findLikeNameShowOff(String id, int vpage){
        List<ShowOff> showOff = FPShowOffRepository.findLikeNameShowOff(id, vpage);
        return showOff;
    }
    public List<ShowOff> countLikeNameShowOff(String id){
        List<ShowOff> showOff = FPShowOffRepository.countLikeNameShowOff(id);
        return showOff;
    }

    public List<ShowOff> findLikeTitleShowOff(String title, int vpage){
        List<ShowOff> showOff = FPShowOffRepository.findLikeTitleShowOff(title, vpage);
        return showOff;
    }
    public List<ShowOff> countLikeTitleShowOff(String title){
        List<ShowOff> showOff = FPShowOffRepository.countLikeTitleShowOff(title);
        return showOff;
    }
    // 여기 잘 봐!!!
    public List<ShowOff> findLikeAllShowOff(String search, int vpage) {
        List<ShowOff> showOff = FPShowOffRepository.findLikeAllShowOff(search, vpage);
        return showOff;
    }
    public List<ShowOff> countLikeAllShowOff(String search) {
        List<ShowOff> showOff = FPShowOffRepository.countLikeAllShowOff(search);
        return showOff;
    }

    //1번째조건: 고객vs디자이너
    public List<ShowOff> findLikeAllShowOffDtype(int dtype, String search, int vpage) {
        List<ShowOff> showOff = FPShowOffRepository.findLikeAllShowOffDtype(dtype, search, vpage);
        return showOff;
    }
    public List<ShowOff> countLikeAllShowOffDtype(int dtype, String search) {
        List<ShowOff> showOff = FPShowOffRepository.countLikeAllShowOffDtype(dtype, search);
        return showOff;
    }

    public List<ShowOff> findLikeNameShowOffDtype(int dtype, String id, int vpage) {
        List<ShowOff> showOff = FPShowOffRepository.findLikeNameShowOffDtype(dtype, id, vpage);
        return showOff;
    }
    public List<ShowOff> countLikeNameShowOffDtype(int dtype, String id) {
        List<ShowOff> showOff = FPShowOffRepository.countLikeNameShowOffDtype(dtype, id);
        return showOff;

    }

    public List<ShowOff> findLikeTitleShowOffDtype(int dtype, String title, int vpage) {
        List<ShowOff> showOff = FPShowOffRepository.findLikeTitleShowOffDtype(dtype, title, vpage);
        return showOff;
    }
    public List<ShowOff> countLikeTitleShowOffDtype(int dtype, String title) {
        List<ShowOff> showOff = FPShowOffRepository.countLikeTitleShowOffDtype(dtype, title);
        return showOff;
    }

    public List<ShowOff> findAllShowOffPageDtype(int dtype, int vpage) {
        List<ShowOff> showOff = FPShowOffRepository.findAllShowOffPageDtype(dtype, vpage);
        return showOff;
    }
    public List<ShowOff> findAllShowOffDtype(int dtype) {
        List<ShowOff> showOff = FPShowOffRepository.findAllShowOffDtype(dtype);
        return showOff;
    }

    /*댓글 페이징*/
    public List<Comment> findAllShowOffC(int seq){
        List<Comment> showOff = FPShowOffRepository.findAllShowOffC(seq);
        return showOff;
    }
    public List<Comment> findAllShowOffPageC(int seq, int vpage){
        List<Comment> showOff = FPShowOffRepository.findAllShowOffPageC(seq, vpage);
        return showOff;
    }


    @Value("${upload.dir}")
    private String uploadDir;
    public String store(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            String fileName = UUID.randomUUID().toString() + "." + fileExtension;
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ShowOff findBySeq(int seq) {
        return FPShowOffRepository.findBySeq(seq).get();
    }
    /*public Test update(Test test) {
        ShowOff testEntity = ShowOff.toUpdateEntity(test);
        FPShowOffRepository.save(testEntity);
    }*/
    /*public ShowOff getPost(int seq) {
        Optional<ShowOff> boardWrapper = FPShowOffRepository.findBySeq(seq); // id를 기준으로 조회

        if (boardWrapper.isPresent()) {
            ShowOff showoff = boardWrapper.get();

            ShowOff showOff = new ShowOff();
            showOff.setId(showoff.getId());
            showOff.setTitle(showoff.getTitle());
            showOff.setContent(showoff.getContent());
            showOff.setCreatedDate(showoff.getCreatedDate());

            return showOff;
        }
        return null;
    }
*/
    /*@Transactional
    public void increaseViewCount(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            // 중복 방지를 위해 현재 세션에 해당 게시물의 ID를 저장
            HttpSession session = request.getSession();
            Set<Long> viewedPosts = (Set<Long>) session.getAttribute("viewedPosts");
            if (viewedPosts == null) {
                viewedPosts = new HashSet<>();
            }
            if (!viewedPosts.contains(postId)) {
                // 중복 방지 조건을 만족하면 조회수를 증가시키고 세션에 게시물 ID를 추가
                post.setViewCount(post.getViewCount() + 1);
                viewedPosts.add(postId);
                session.setAttribute("viewedPosts", viewedPosts);
                postRepository.save(post);
            }
        }
    }*/
    public void updateViews(int seq){
        FPShowOffRepository.updateViews(seq);
    }
    /*public void toggleLike(String name, int showOffId) {
        // 사용자 정보 가져오기
        User user = FPShowOffRepository.findByUsername(name);

        // 게시물 정보 가져오기
        Post post = FPShowOffRepository.findById(showOffId).orElseThrow(() -> new ChangeSetPersister.NotFoundException("Post not found"));

        // 이미 해당 사용자가 게시물에 좋아요를 눌렀는지 확인
        Like existingLike = FPShowOffRepository.findByUserAndPost(user, post);

        if (existingLike == null) {
            // 좋아요 레코드가 존재하지 않는 경우, 새로운 좋아요 생성
            Like like = new Like();
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
        } else {
            // 이미 좋아요를 누른 경우, 좋아요 취소 (좋아요 레코드 삭제)
            FPShowOffRepository.delete(existingLike);
        }
    }*/
    /* 좋아요 눌려있는지 확인 */
    public int searchHeart(HashMap<String, Object> map) {
        int result = FPShowOffRepository.searchHeart(map);
        return result;
    }

    /* 게시물 좋아요 */
    public boolean addHeart(HashMap<String, Object> map) {
        boolean flag = false;

        int affectedCnt = 0;
        affectedCnt = FPShowOffRepository.insertHeart(map);

        if(affectedCnt > 0) {
            flag = true;
        }
        return flag;
    }

    /* 게시물 좋아요 취소 */
    public boolean deleteHeart(HashMap<String, Object> map) {
        boolean flag = false;

        int affectedCnt = 0;
        affectedCnt = FPShowOffRepository.deleteHeart(map);

        if(affectedCnt > 0) {
            flag = true;
        }
        return flag;
    }

    /* 게시물 좋아요 개수 카운트 */
    public int countHeart(int showOff) {
        int hearts = FPShowOffRepository.countHeart(showOff);
        return hearts;
    }
    //네임값 조회
    public Designer findByIdDesigner(String id){
        //id로 미용사 조회
        Designer designer = FPShowOffRepository.findByIdDesigner(id);
        return designer;
    }
    public Customer findByIdCustomer(String id){
        //id로 일반 회원 조회
        Customer customer = FPShowOffRepository.findByIdCustomer(id);
        return customer;
    }
    public String getName(String id) {
        // 데이터베이스에서 해당 ID에 해당하는 사용자를 조회합니다.
        User user = FPShowOffRepository.findById(id).orElse(null);

        // 사용자가 존재하면 이름을 반환하고, 존재하지 않으면 null을 반환합니다.
        return (user != null) ? user.getName() : null;
    }

    public void deletePost(int seq) {
        FPShowOffRepository.deletePost(seq);
    }

    public List<Likes> findLikeList(int seq) {
        List<Likes> likes = FPShowOffRepository.findLikeList(seq);
        return likes;
    }

    public Designer findByDesignerEmail(String designerOfferSeq) {
        Designer designerEmail = FPShowOffRepository.findByDesignerEmail(designerOfferSeq);
        return designerEmail;
    }//designerRepository.findById(designerId)
}