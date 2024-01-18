package FinalPrjTest.FinalPrj.repository;

import FinalPrjTest.FinalPrj.domain.*;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import java.util.List;

public class DesignerRepository implements FPDesignerRepository {

    private final EntityManager em;

    public DesignerRepository(EntityManager em){
        this.em=em;
    }

    @Override
    public Customer customerSave(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findAllCustomer() {
        List<Customer> customers = em.createQuery("select c from Customer c",Customer.class).getResultList();
        return customers;
    }

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
    //위 코드 리팩토링시 이거 적용시키면 좀더 간단함
    //@Override
    //public Customer findByIdCustomer(String id) {
    //    Customer customer = em.find(Customer.class, id);
    //    return customer;
    //}

    @Override
    public Designer designerSave(Designer designer) {
        return null;
    }

    @Override
    public List<Designer> findAllDesigner() {
        List<Designer> designers =em.createQuery("select d from Designer d",Designer.class).getResultList();
        return designers;
    }

    @Override
    public List<Designer> findAllDesignerPage(int v_page) {
        List<Designer> designers =em.createQuery("SELECT d FROM Designer d ORDER BY d.id ASC",Designer.class).setFirstResult(v_page).setMaxResults(10).getResultList();
        return designers;

    }

    @Override
    public Designer findByIdDesigner(String id) {
        try {
            Designer designer = em.createQuery("select d from Designer d where d.id = :id",Designer.class)
                    .setParameter("id",id)
                    .getSingleResult();
            return designer;
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public List<ShowOff> findByIdShowOff(String id) {
        try {
            List<ShowOff> showOff = em.createQuery("select s from ShowOff s where s.id = :id",ShowOff.class)
                    .setParameter("id",id)
                    .getResultList();
            return showOff;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Designer> findLikeIdDesigners(String name, int v_page) {
        List<Designer>designers =em.createQuery("select d from Designer d where d.name like :name",Designer.class)
                .setParameter("name","%"+name+"%")
                .setFirstResult(v_page)
                .setMaxResults(10).getResultList();
        return designers;
    }
//    @Override
//    public List<Designer> findLikeIdDesigners(String id) {
//        List<Designer> designers =em.createQuery("select d from Designer d where d.id like :id",Designer.class).setParameter("id","%"+id+"%").getResultList();
//        return designers;
//    }
    @Override
    public List<Designer> countLikeIdDesigners(String name) {
        List<Designer> designers=em.createQuery("select d from Designer d where d.name like :name",Designer.class)
                .setParameter("name","%"+name+"%")
                .getResultList();
        return designers;
    }


    public void updateSelfAbout(String self_about, String id) {
        Designer designer = em.find(Designer.class, id);
        designer.setSelf_about(self_about);

    }
    public void insertCustomer(Customer customer){
        em.persist(customer);
    }
    public void insertDesigners(Designer designer){em.persist(designer);}
    public void insertDesignersPortfolioPath(Designerphoto designerphoto){em.persist(designerphoto);}


    public void deleteCustomer(String id){
        Customer customer = em.find(Customer.class, id); // id에 해당하는 고객 정보 조회
        em.remove(customer);
    }
    public void deleteDesigner(String id){
        Designer designer = em.find(Designer.class, id); // id에 해당하는 고객 정보 조회
        em.remove(designer);
    }

    public String findByNameEmailCustomer(String name,String email){
        String id = "";
        try {
            //TypedQuery의 장점은 반환 결과의 타입 안정성을 제공하고, 개발자가 캐스팅을 수동으로 처리하지 않아도 되는 편의성을 제공한다는 점
            TypedQuery<String> query = em.createQuery("SELECT c.id FROM Customer c WHERE c.name = :name AND c.email = :email", String.class);
            query.setParameter("name", name);
            query.setParameter("email", email);
            id = query.getSingleResult();
        } catch (NoResultException e) {
            id = "false";
        }
        return id;


    }
    public String findByNameEmailDesigner(String name,String email){
        String id = "";
        try {
            //TypedQuery의 장점은 반환 결과의 타입 안정성을 제공하고, 개발자가 캐스팅을 수동으로 처리하지 않아도 되는 편의성을 제공한다는 점
            TypedQuery<String> query = em.createQuery("SELECT d.id FROM Designer d WHERE d.name = :name AND d.email = :email", String.class);
            query.setParameter("name", name);
            query.setParameter("email", email);
            id = query.getSingleResult();
        } catch (NoResultException e) {
            id = "false";
        }
        return id;


    }
    public String findByNameEmailCustomerPwd(String name,String email,String id){
        String pwd = "";
        try {
            //TypedQuery의 장점은 반환 결과의 타입 안정성을 제공하고, 개발자가 캐스팅을 수동으로 처리하지 않아도 되는 편의성을 제공한다는 점
            TypedQuery<String> query = em.createQuery("SELECT c.pw FROM Customer c WHERE c.name = :name AND c.email = :email And c.id = :id", String.class);
            query.setParameter("name", name);
            query.setParameter("email", email);
            query.setParameter("id",id);
            pwd = query.getSingleResult();
        } catch (NoResultException e) {
            pwd = "false";
        }
        return pwd;


    }
    public String findByNameEmailDesignerPwd(String name,String email,String id){
        String pwd = "";
        try {
            //TypedQuery의 장점은 반환 결과의 타입 안정성을 제공하고, 개발자가 캐스팅을 수동으로 처리하지 않아도 되는 편의성을 제공한다는 점
            TypedQuery<String> query = em.createQuery("SELECT d.pw FROM Designer d WHERE d.name = :name AND d.email = :email And d.id = :id", String.class);
            query.setParameter("name", name);
            query.setParameter("email", email);
            query.setParameter("id",id);
            pwd = query.getSingleResult();
        } catch (NoResultException e) {
            pwd = "false";
        }
        return pwd;


    }
    public void updateDesigner(DesignerReWrite designerReWrite, String id) {
        Designer designer = em.find(Designer.class, id);
        designer.setPw(designerReWrite.getPw());
        designer.setEmail(designerReWrite.getEmail());
        designer.setPhone_num(designerReWrite.getPhone_num());
        designer.setPhoto_path(designerReWrite.getPhoto_path());
        designer.setCareer(designerReWrite.getCareer());
        designer.setAddress(designerReWrite.getAddress());
        designer.setDetailed_address(designerReWrite.getDetailed_address());
        designer.setSelf_about(designerReWrite.getSelf_about());
        designer.setCertificate_path2(designerReWrite.getCertificate_path2());
        designer.setCertificate_path3(designerReWrite.getCertificate_path3());
        designer.setPortfolio_link(designerReWrite.getPortfolio_link());
        em.persist(designer);
    }

    public DesignerReWrite findByidRewrite(String id){
        DesignerReWrite designerReWrite = em.createQuery("SELECT NEW FinalPrjTest.FinalPrj.domain.DesignerReWrite(" +
                        "d.pw, d.email, d.phone_num, d.photo_path, d.career, d.address, d.detailed_address, d.certificate_path2, " +
                        "d.certificate_path3, d.portfolio_link, d.self_about ) FROM Designer d WHERE d.id = :id", DesignerReWrite.class)
                .setParameter("id", id)
                .getSingleResult();
        return designerReWrite;
    }
    public void updateCustomer(CustomerReWrite customerReWrite, String id) {
        Customer customer = em.find(Customer.class, id);
        if(customerReWrite.getPw()!=null){
            customer.setPw(customerReWrite.getPw());
        }
        customer.setAddress(customerReWrite.getAddress());
        customer.setEmail(customerReWrite.getEmail());
        customer.setPhone_num(customerReWrite.getPhone_num());
        customer.setHair_condition(customerReWrite.getHair_condition());
        customer.setHair_length(customerReWrite.getHair_length());
        customer.setHead_shape(customerReWrite.getHead_shape());
        customer.setDye_times(customerReWrite.getDye_times());
        customer.setPerm_times(customerReWrite.getPerm_times());
        em.persist(customer);
    }
    public CustomerReWrite findByCustomerReWrite(String id) {
        CustomerReWrite customerReWrite = em.createQuery(
                        "SELECT NEW FinalPrjTest.FinalPrj.domain.CustomerReWrite(" +
                                "c.pw, c.address, c.email, c.phone_num, " +
                                "c.hair_condition, c.hair_length, c.head_shape, " +
                                "c.dye_times, c.perm_times) " +
                                "FROM Customer c " +
                                "WHERE c.id = :id", CustomerReWrite.class)
                .setParameter("id", id)
                .getSingleResult();
        return customerReWrite;
    }

    public void dropPortfolio(Designerphoto designerPhoto){
        String id = designerPhoto.getId();
        String photopath = designerPhoto.getPhoto_path();
        em.createQuery("DELETE FROM Designerphoto dp WHERE dp.id = :id AND dp.photoName = :photoName",Designerphoto.class)
                .setParameter("id", id)
                .setParameter("photopath", photopath)
                .executeUpdate();
    }

    //정확하게 기억이 안났을 때를 고려해서 like 사용
    @Override
    public List<Designer> findLikeNameDesigners(String name) {
        return em.createQuery("select d from Designer d where d.name like :name", Designer.class).setParameter("name", "%" + name + "%").getResultList();
    }

    @Override
    public List<Designer> listDesignerPage(String name, int v_page) {
        List<Designer> designers = em.createQuery("SELECT d FROM Designer d ORDER BY d.id ASC", Designer.class).setFirstResult(v_page)
                .setMaxResults(10).getResultList();

        if (name != null) {
            designers = em.createQuery("select d from Designer d where d.name like :name", Designer.class)
                    .setParameter("name", "%" + name + "%")
                    .setFirstResult(v_page)
                    .setMaxResults(10).getResultList();
        }

        return designers;
    }

    public List<Designerphoto> findByIdDesignerPhoto(String id){
        List<Designerphoto> designerphotos = em.createQuery("select p from Designerphoto p where p.id=:id",Designerphoto.class)
                .setParameter("id",id)
                .getResultList();
        return designerphotos;
    }

    public List<Review> findByIdReviews(String id){
        List<Review> reviews = em.createQuery("select r from Review r where r.designer_id=:id",Review.class)
                .setParameter("id",id)
                .getResultList();
        return reviews;
    }

    //후기 one
    public Boolean InsertReview(Review review){
        try {
            em.persist(review);
            return true; // 저장 성공
        } catch (Exception e) {
            return false; // 저장 실패
        }
    }

    //후기 two
    public Boolean InsertReviewDesigner(Review review){
        Designer designer = em.find(Designer.class,review.getDesigner_id());
        if (designer != null) {
            designer.setReview(review.getContent());

            int count =0;
            if(designer.getPoint_count()==null){
                count=0;
            }else {
                count= designer.getPoint_count();
            }

            count = count + 1;
            designer.setPoint_count(count);

            Float point =0.0f;
            if(designer.getPoint()==null){
                point =0.0f;
            }else{
                point= designer.getPoint();
            }


            point = point + review.getRating();
            designer.setPoint(point);

            em.merge(designer); // 디자이너 객체를 데이터베이스에 저장/업데이트
            return true;
        }
        return false;
    }

    //후기 three
    public Boolean InsertReviewReservation(Review review){
        Reservation_date reservationDate = em.find(Reservation_date.class,review.getRequest_seq());
        if(reservationDate != null){
            reservationDate.setReview(1);
            em.merge(reservationDate);
            return true;
        }
        return false;
    }

    public List<Alarm> findByIdAlarm(String userId){
        List<Alarm> alarmList = em.createQuery("select a from Alarm a where a.id = :id",Alarm.class)
                .setParameter("id",userId)
                .getResultList();
        return alarmList;
    }

    public Boolean deleteThisAlarm(int seq){
        Alarm alarm = em.find(Alarm.class,seq);
        try {
            em.remove(alarm);
            return true; // 저장 성공
        } catch (Exception e) {
            return false; // 저장 실패
        }
    }

    public Boolean findOffer(String id, int seq){
        Boolean result = false;
        try {
            Offer offer = em.createQuery("select o from Offer o where o.id=:id and o.request_seq=:seq",Offer.class)
                    .setParameter("id",id)
                    .setParameter("seq",seq)
                    .getSingleResult();
            result = false; // 결과가 있을 경우
        } catch (NoResultException e) {
            result = true; // 결과가 없을 경우
        }
        return result;
    }

    public List<Review> findByIdCReviews(String id){
        List<Review> reviews = em.createQuery("select r from Review r where r.customer_id=:id",Review.class)
                .setParameter("id",id)
                .getResultList();
        return reviews;
    }

    public Boolean saveAiPhoto(Ai_result aiResult){
        try {
            em.persist(aiResult);
            return true; // 저장 성공
        } catch (Exception e) {
            return false; // 저장 실패
        }
    }

}
