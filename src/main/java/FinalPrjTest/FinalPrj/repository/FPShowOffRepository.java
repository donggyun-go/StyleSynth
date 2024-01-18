package FinalPrjTest.FinalPrj.repository;

import FinalPrjTest.FinalPrj.domain.*;
import FinalPrjTest.FinalPrj.utils.CommentDTO;
import org.apache.catalina.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface FPShowOffRepository {
    void write(ShowOff showOff);
    List<ShowOff> findAll(String id);
    public String store(MultipartFile file);
    void save(ShowOff showoff);

    List<ShowOff> findAllShowOff();
    List<ShowOff> findAllShowOffPage(int vpage);


    List<ShowOff> findLikeNameShowOff(String id, int vpage);

    List<ShowOff> countLikeNameShowOff(String id);

    List<ShowOff> findLikeTitleShowOff(String title, int vpage);

    List<ShowOff> countLikeTitleShowOff(String title);
    //여기 잘 봐!!!
    List<ShowOff> findLikeAllShowOff(String search, int vpage);

    List<ShowOff> countLikeAllShowOff(String search);


    //1번째조건: 고객vs디자이너
    List<ShowOff> findLikeAllShowOffDtype(int dtype, String search, int vpage);
    List<ShowOff> countLikeAllShowOffDtype(int dtype, String search);
    List<ShowOff> findLikeNameShowOffDtype(int dtype, String id, int vpage);
    List<ShowOff> countLikeNameShowOffDtype(int dtype, String id);
    List<ShowOff> findLikeTitleShowOffDtype(int dtype, String title, int vpage);
    List<ShowOff> countLikeTitleShowOffDtype(int dtype, String title);
    List<ShowOff> findAllShowOffPageDtype(int dtype, int vpage);
    List<ShowOff> findAllShowOffDtype(int dtype);


    /*댓글 페이징*/
    /*댓글 페이징*/
    List<Comment> findAllShowOffC(int seq);
    List<Comment> findAllShowOffPageC(int seq, int vpage);



    Optional<ShowOff> findBySeq(int seq);
    void updateViews(int seq);

    /* 좋아요 눌려있는지 확인 */
    int searchHeart(HashMap<String, Object> map);
    //int searchHeart(int showOffId, String userId);

    /* 게시물 좋아요 */
    int insertHeart(HashMap<String, Object> map);

    /* 게시물 좋아요 취소 */
    int deleteHeart(HashMap<String, Object> map);
    //void deleteHeart(int showOffId, String userId);

    /* 게시물 좋아요 개수 카운트 */
    int countHeart(int showOff);

    Customer findByIdCustomer(String id);
    Designer findByIdDesigner(String id);
    // ID를 사용하여 사용자 조회
    Optional<User> findById(String id);

    void deletePost(int seq);

    List<Likes> findLikeList(int seq);

    Designer findByDesignerEmail(String designerOfferSeq);
}