package FinalPrjTest.FinalPrj.repository;

import FinalPrjTest.FinalPrj.domain.*;


import java.util.List;


public interface FPDesignerRepository {

    Customer customerSave(Customer customer);
    List<Customer> findAllCustomer();
    Customer findByIdCustomer(String id);


    Designer designerSave(Designer designer);
    List<Designer> findAllDesigner();
    List<Designer> findAllDesignerPage(int v_page);
    Designer findByIdDesigner(String id);
    List<Designer> findLikeIdDesigners(String name,int page );
    //List<Designer> findLikeIdDesigners(String id);
    List<Designer> countLikeIdDesigners(String name);


    void updateSelfAbout(String self_about,String id);

    void insertCustomer(Customer customer);
    void insertDesigners(Designer designer);

    void insertDesignersPortfolioPath(Designerphoto designerphoto);

    void deleteCustomer(String id);
    void deleteDesigner(String id);

    String findByNameEmailCustomer(String name,String email);

    String findByNameEmailDesigner(String name,String email);

    String findByNameEmailCustomerPwd(String name,String email,String id);

    String findByNameEmailDesignerPwd(String name,String email,String id);

    void updateDesigner(DesignerReWrite designerReWrite,String id);

    DesignerReWrite findByidRewrite(String id);

    void updateCustomer(CustomerReWrite customerReWrite, String id);
    CustomerReWrite findByCustomerReWrite(String id);

    void dropPortfolio(Designerphoto designerPhoto);

    List<ShowOff> findByIdShowOff(String id);

    //05.25(수빈): 추가함
    //정확하게 기억이 안났을 때를 고려해서 like 사용
    List<Designer> findLikeNameDesigners(String name);
    //05.25(수빈): 추가함
    List<Designer> listDesignerPage(String name, int v_page);

    List<Designerphoto> findByIdDesignerPhoto(String id);

    List<Review> findByIdReviews(String id);

    Boolean InsertReview(Review review);

    Boolean InsertReviewDesigner(Review review);

    Boolean InsertReviewReservation(Review review);

    List<Alarm> findByIdAlarm(String userId);

    Boolean deleteThisAlarm(int seq);

    Boolean findOffer(String id, int seq);

    List<Review> findByIdCReviews(String id);

    Boolean saveAiPhoto(Ai_result aiResult);
}
