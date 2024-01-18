package FinalPrjTest.FinalPrj.repository;

import FinalPrjTest.FinalPrj.domain.*;
import FinalPrjTest.FinalPrj.utils.CommentDTO;
import org.apache.catalina.User;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ShowOffRepository implements FPShowOffRepository {
    private EntityManager em;

    public ShowOffRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public void write(ShowOff showOff) {
        em.persist(showOff);
    }

    @Override  //일단 페이징할땐 쓰지말아봐.
    public List<ShowOff> findAll(String id) {
        return em.createQuery("select s from ShowOff s", ShowOff.class).getResultList();
    }

    //dtype=3일때 검색
    @Override  //전체 list 조회
    public List<ShowOff> findAllShowOff() {
        List<ShowOff> showOffList = em.createQuery("select p from ShowOff p order by p.createdDate DESC", ShowOff.class)
                .getResultList();  //리턴값만 있는get(=메소드)은 인자x/
        return showOffList;
    }

    @Override  //전체 리스트조회한 것 중에서 첫번째 데이터의 인덱스번호.
    public List<ShowOff> findAllShowOffPage(int vpage) {
        List<ShowOff> showOffList = em.createQuery("select p from ShowOff p order by p.createdDate DESC", ShowOff.class)
                .setFirstResult(vpage).setMaxResults(20).getResultList();  //1~10개의 데이터를 가져오겠다.
        return showOffList;
    }


    @Override  //이름으로 조회했을때 전체list나옴.
    public List<ShowOff> findLikeNameShowOff(String id, int vpage) {
        List<ShowOff> showOffList = em.createQuery("select p from ShowOff p where p.id like: id", ShowOff.class)
                .setParameter("id", "%" + id + "%")
                .setFirstResult(vpage)
                .setMaxResults(10).getResultList();
        return showOffList;
    }

    @Override  //이름으로 조회했을때 페이징된 페이지 수.
    public List<ShowOff> countLikeNameShowOff(String id) {
        List<ShowOff> showOffList = em.createQuery("select p from ShowOff p where p.id like: id", ShowOff.class)
                .setParameter("id", "%" + id + "%")
                .getResultList();
        return showOffList;
    }


    @Override  //title로 조회했을때 전체list나옴.
    public List<ShowOff> findLikeTitleShowOff(String title, int vpage) {
        List<ShowOff> showOffList = em.createQuery("select p from ShowOff p where p.title like: title", ShowOff.class)
                .setParameter("title", "%" + title + "%")
                .setFirstResult(vpage)
                .setMaxResults(10).getResultList();
        return showOffList;
    }

    @Override  //title로 조회했을때 페이징된 페이지 수.
    public List<ShowOff> countLikeTitleShowOff(String title) {
        List<ShowOff> showOffList = em.createQuery("select p from ShowOff p where p.title like: title", ShowOff.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
        return showOffList;
    }
    //여기잘봐!!!
    @Override
    public List<ShowOff> findLikeAllShowOff(String search, int vpage) {
        List<ShowOff> showOffList = em.createQuery("SELECT p FROM ShowOff p WHERE p.title LIKE :search OR p.id LIKE :search", ShowOff.class)
                .setParameter("search", "%" + search + "%")
                .setFirstResult(vpage)
                .setMaxResults(10)
                .getResultList();
        return showOffList;
    }
    @Override
    public List<ShowOff> countLikeAllShowOff(String search) {
        List<ShowOff> showOffList = em.createQuery("SELECT p FROM ShowOff p WHERE p.title LIKE :search OR p.id LIKE :search", ShowOff.class)
                .setParameter("search", "%" + search + "%")
                .getResultList();
        return showOffList;
    }

    //dtype=1,2검색: 고객vs디자이너
    @Override
    public List<ShowOff> findLikeAllShowOffDtype(int dtype, String search, int vpage) {
        List<ShowOff> showOffList = em.createQuery("SELECT p FROM ShowOff p WHERE p.dtype = :dtype and p.title LIKE :search OR p.id LIKE :search", ShowOff.class)
                .setParameter("dtype", dtype)
                .setParameter("search", "%" + search + "%")
                .setFirstResult(vpage)
                .setMaxResults(10)
                .getResultList();
        return showOffList;
    }
    @Override
    public List<ShowOff> countLikeAllShowOffDtype(int dtype, String search) {
        List<ShowOff> showOffList = em.createQuery("SELECT p FROM ShowOff p WHERE p.dtype = :dtype and p.title LIKE :search OR p.id LIKE :search", ShowOff.class)
                .setParameter("dtype", dtype)
                .setParameter("search", "%" + search + "%")
                .getResultList();
        return showOffList;
    }

    @Override  //이름으로 조회했을때 전체list나옴.
    public List<ShowOff> findLikeNameShowOffDtype(int dtype, String id, int vpage) {
        List<ShowOff> showOffList = em.createQuery("select p from ShowOff p where p.dtype= :dtype and p.id like: id", ShowOff.class)
                .setParameter("dtype", dtype)
                .setParameter("id", "%" + id + "%")
                .setFirstResult(vpage)
                .setMaxResults(10).getResultList();
        return showOffList;
    }
    @Override  //이름으로 조회했을때 페이징된 페이지 수.
    public List<ShowOff> countLikeNameShowOffDtype(int dtype, String id) {
        List<ShowOff> showOffList = em.createQuery("select p from ShowOff p where p.dtype= :dtype and p.id like: id", ShowOff.class)
                .setParameter("dtype", dtype)
                .setParameter("id", "%" + id + "%")
                .getResultList();
        return showOffList;
    }

    @Override  //title로 조회했을때 전체list나옴.
    public List<ShowOff> findLikeTitleShowOffDtype(int dtype, String title, int vpage) {
        List<ShowOff> showOffList = em.createQuery("select p from ShowOff p where p.dtype= :dtype and p.title like: title", ShowOff.class)
                .setParameter("dtype", dtype)
                .setParameter("title", "%" + title + "%")
                .setFirstResult(vpage)
                .setMaxResults(10).getResultList();
        return showOffList;
    }
    @Override  //title로 조회했을때 페이징된 페이지 수.
    public List<ShowOff> countLikeTitleShowOffDtype(int dtype, String title) {
        List<ShowOff> showOffList = em.createQuery("select p from ShowOff p where p.dtype= :dtype and p.title like: title", ShowOff.class)
                .setParameter("dtype", dtype)
                .setParameter("title", "%" + title + "%")
                .getResultList();
        return showOffList;
    }

    @Override  //title로 조회했을때 전체list나옴.
    public List<ShowOff> findAllShowOffPageDtype(int dtype, int vpage) {
        List<ShowOff> showOffList = em.createQuery("select p from ShowOff p where p.dtype = :dtype", ShowOff.class)
                .setParameter("dtype", dtype)
                .setFirstResult(vpage)
                .setMaxResults(10).getResultList();
        return showOffList;
    }
    @Override  //title로 조회했을때 페이징된 페이지 수.
    public List<ShowOff> findAllShowOffDtype(int dtype) {
        List<ShowOff> showOffList = em.createQuery("select p from ShowOff p where p.dtype = :dtype", ShowOff.class)
                .setParameter("dtype", dtype)
                .getResultList();
        return showOffList;
    }

    /*댓글 페이징*/
    @Override  //전체 list 조회
    public List<Comment> findAllShowOffC(int seq) {
        List<Comment> showOffList = em.createQuery("select p from Comment p where sseq=:seq order by p.seq DESC", Comment.class)
                .setParameter("seq", seq).getResultList();  //리턴값만 있는get(=메소드)은 인자x/
        return showOffList;
    }

    @Override  //전체 리스트조회한 것 중에서 첫번째 데이터의 인덱스번호. = seq에 해당하는 게시글을 찾는거.
    public List<Comment> findAllShowOffPageC(int seq, int vpage) {
        List<Comment> showOffList = em.createQuery("select p from Comment p where p.sseq= :seq order by p.seq DESC", Comment.class)
                .setParameter("seq", seq).setFirstResult(vpage).setMaxResults(10).getResultList();  //1~10개의 데이터를 가져오겠다.
        return showOffList;
    }




    @Value("${upload.dir}")
    private String uploadDir;

    @Override
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

    @Override
    public void save(ShowOff showoff) {
        em.persist(showoff);
    }

    /*@Override
    public ShowOff findBySeq(int seq) {
        return em.find(ShowOff.class, seq);
    }*/
    @Override
    public Optional<ShowOff> findBySeq(int seq) {
        ShowOff showOff = em.find(ShowOff.class, seq);
        return Optional.ofNullable(showOff);
    }

    /* @Override
     public static ShowOff toUpdateEntity(Test test){
         ShowOff showoff = new ShowOff();
         showoff.setSeq(test.getSeq());
         showoff.setTitle(test.getTitle());
         showoff.setContent(test.getContent());
         return showoff;
     }*/

 /*  @Override
    @Modifying
    @Query(value = "update ShowOff v set v.views=v.views+1 where v.seq=:seq")
    void updateViews(@Param("seq") int seq);*/

    @Override
    public void updateViews(int seq){
        em.createQuery("update ShowOff v set v.views=v.views+1 where v.seq=:seq")
                .setParameter("seq", seq)
                .executeUpdate();
    }

    /*@Override
    public void updateViews(String id) {
        // 중복 방지를 위해 현재 세션에 해당 게시물의 ID를 저장
        Set<String> viewedPosts = (Set<String>) session.getAttribute("viewedPosts");
        if (viewedPosts == null) {
            viewedPosts = new HashSet<>();
        }
        if (!viewedPosts.contains(id)) {
            // 중복 방지 조건을 만족하면 조회수를 증가시키고 세션에 게시물 ID를 추가
            em.createQuery("update ShowOff v set v.views=v.views+1 where v.id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            viewedPosts.add(id);
            session.setAttribute("viewedPosts", viewedPosts);
        }
    }*/
    /*@Repository
    public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
        // 좋아요 눌려있는지 확인
        @Query("SELECT COUNT(pl.id) FROM PostLike pl WHERE pl.post.id = :postId AND pl.user.id = :userId")
        int searchHeart(@Param("postId") Long postId, @Param("userId") Long userId);

        // 게시물 좋아요
        @Modifying
        @Query("INSERT INTO PostLike(pl.post, pl.user) VALUES (:post, :user)")
        void insertHeart(@Param("post") Post post, @Param("user") User user);

        // 게시물 좋아요 취소
        @Modifying
        @Query("DELETE FROM PostLike pl WHERE pl.post.id = :postId AND pl.user.id = :userId")
        void deleteHeart(@Param("postId") Long postId, @Param("userId") Long userId);

        // 게시물 좋아요 카운트
        @Query("SELECT COUNT(pl.id) FROM PostLike pl WHERE pl.post.id = :postId")
        int countHeart(@Param("postId") Long postId);
    }*/
    @Override
    public int searchHeart(HashMap<String, Object> map) {
        String jpql = "SELECT COUNT(pl) FROM Likes pl WHERE pl.showOff = :showOff AND pl.user = :userId";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("showOff", map.get("showOff"))
                .setParameter("userId", map.get("userId"))
                .getSingleResult();
        return count.intValue();
    }

    @Override
    public int insertHeart(HashMap<String, Object> map) {
        ShowOff showOff = (ShowOff) map.get("showOff");
        String userId = (String) map.get("userId");

        //ShowOff showOffId = em.find(ShowOff.class, showOff);

        Likes likes = new Likes();
        likes.setLikeCount(0);
        likes.setShowOff(showOff);
        likes.setUser(userId);

        em.persist(likes);

        // 추가된 레코드 수는 항상 1이므로 1을 반환합니다.
        return 1;
    }

    @Override
    public int deleteHeart(HashMap<String, Object> map) {
        ShowOff showOff = (ShowOff) map.get("showOff");
        String userId = (String) map.get("userId");

        String jpql = "DELETE FROM Likes pl WHERE pl.showOff = :showOff AND pl.user = :userId";
        int deletedCount = em.createQuery(jpql)
                .setParameter("showOff", showOff)
                .setParameter("userId", userId)
                .executeUpdate();

        return deletedCount;
    }

    @Override
    public int countHeart(int showOff) {
        ShowOff showOff1 = em.find(ShowOff.class, showOff);
        String jpql = "SELECT COUNT(pl) FROM Likes pl WHERE pl.showOff = :showOff";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("showOff", showOff1)
                .getSingleResult();
        return count.intValue();
    }

    public List<Likes> findLikeList(int seq){
        ShowOff showOff1 = em.find(ShowOff.class, seq);
        List<Likes> likes = em.createQuery("select l from Likes l where l.showOff = :showOff", Likes.class)
                .setParameter("showOff", showOff1)
                .getResultList();
        return likes;
    }

    /*@Override
    public Optional<User> findById(String userId) {
        Optional<User> showOffList = em.createQuery("SELECT t1.name, t2.name FROM table1 t1 JOIN table2 t2 ON t1.id = t2.id", User.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
        return showOffList;
    }*/
    /*@Override
    public Optional<User> findById(String userId) {
        String query = "SELECT u.name FROM User u WHERE u.id = :userId";

        TypedQuery<String> typedQuery = em.createQuery(query, String.class);
        typedQuery.setParameter("userId", userId);

        String name = typedQuery.getSingleResult();

        User user = new User();
        user.setName(name);

        return Optional.ofNullable(user);
    }*/
    @Override
    public Customer findByIdCustomer(String id) {
        try {
            Customer customer = em.createQuery("select c from Customer c where c.id=:id",Customer.class)
                    .setParameter("id",id)
                    .getSingleResult();
            return customer;
        } catch (NoResultException e) {
            return null;
        }
    }
    @Override
    public Designer findByIdDesigner(String id) {
        try {
            Designer designer = em.createQuery("select c from Designer c where c.id=:id",Designer.class)
                    .setParameter("id",id)
                    .getSingleResult();
            return designer;
        } catch (NoResultException e) {
            return null;
        }
    }
    @Override
    public Optional<User> findById(String id) {
        String query = "SELECT u FROM User u WHERE u.id = :id";
        TypedQuery<User> typedQuery = em.createQuery(query, User.class);
        typedQuery.setParameter("id", id);

        return typedQuery.getResultList().stream().findFirst();
    }

    public void deletePost(int seq){
        ShowOff showOff = em.find(ShowOff.class,seq);
        em.remove(showOff);
    }

    public Designer findByDesignerEmail(String designerOfferSeq){
        Designer designer = em.createQuery("select c from Designer c where c.id=:id", Designer.class)
                .setParameter("id",designerOfferSeq)
                .getSingleResult();
        return designer;
    }
}