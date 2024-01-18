package FinalPrjTest.FinalPrj.repository;

import FinalPrjTest.FinalPrj.domain.*;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationRepository implements FPReservationRepository {
    private final EntityManager em;

    public ReservationRepository(EntityManager em){
        this.em=em;
    }

    public void insertRequest_table(Request_table request_table){
        em.persist(request_table);
    }
    public void insertAiresult(Ai_result aiResult){
        em.persist(aiResult);
    }
    public List<Ai_result> findbyidAiPhoto(String id){
        List<Ai_result> aiResults = em.createQuery("select a from Ai_result a where a.id=:id",Ai_result.class).setParameter("id",id).getResultList();
        return aiResults;//여기는 값이 null이더라도 오류는 안뜸
    }
    public List<Request_table> findAllRequest(){
        List<Request_table> WantedList = em.createQuery("select r from Request_table r",Request_table.class).getResultList();
        return WantedList;
    }
    public Request_table findRequest(int seq){
        Request_table Wanted = em.find(Request_table.class,seq);
        return Wanted;
    }
    public boolean InsertReservation(Reservation_date reservationDate){
        try {
            em.persist(reservationDate);
            Request_table requestTable = em.find(Request_table.class,reservationDate.getRequest_seq());
            requestTable.setReservation(1);
            em.merge(requestTable);
            return true; // 저장 성공
        } catch (Exception e) {
            return false; // 저장 실패
        }

    }
    public void RemoveRequest(int seq){
        Request_table Wanted = em.find(Request_table.class,seq);
        em.remove(Wanted);
    }
    /*페이징*/
    @Override  //일단 페이징할땐 쓰지말아봐.
    public List<Request_table> findAll(String Writer_id) {
        return em.createQuery("select s from Request_table s", Request_table.class).getResultList();
    }

    @Override  //전체 list 조회
    public List<Request_table> findAllShowOff() {
        List<Request_table> showOffList = em.createQuery("select p from Request_table p order by p.create_date DESC", Request_table.class)
                .getResultList();  //리턴값만 있는get(=메소드)은 인자x/
        return showOffList;
    }

    @Override  //전체 리스트조회한 것 중에서 첫번째 데이터의 인덱스번호.
    public List<Request_table> findAllShowOffPage(int vpage) {
        List<Request_table> showOffList = em.createQuery("select p from Request_table p order by p.create_date DESC", Request_table.class)
                .setFirstResult(vpage).setMaxResults(10).getResultList();  //1~10개의 데이터를 가져오겠다.
        return showOffList;
    }


    @Override  //이름으로 조회했을때 전체list나옴.
    public List<Request_table> findLikeNameShowOff(String Writer_id, int vpage) {
        List<Request_table> showOffList = em.createQuery("select p from Request_table p where p.Writer_id like: Writer_id order by p.create_date DESC", Request_table.class)
                .setParameter("Writer_id", "%" + Writer_id + "%")
                .setFirstResult(vpage)
                .setMaxResults(10).getResultList();
        return showOffList;
    }

    @Override  //이름으로 조회했을때 페이징된 페이지 수.
    public List<Request_table> countLikeNameShowOff(String Writer_id) {
        List<Request_table> showOffList = em.createQuery("select p from Request_table p where p.Writer_id like: Writer_id order by p.create_date DESC", Request_table.class)
                .setParameter("Writer_id", "%" + Writer_id + "%")
                .getResultList();
        return showOffList;
    }


    @Override  //address로 조회했을때 전체list나옴.
    public List<Request_table> findLikeTitleShowOff(String address, int vpage) {
        List<Request_table> showOffList = em.createQuery("select p from Request_table p where p.address like: address order by p.create_date DESC", Request_table.class)
                .setParameter("address", "%" + address + "%")
                .setFirstResult(vpage)
                .setMaxResults(10).getResultList();
        return showOffList;
    }

    @Override  //address로 조회했을때 페이징된 페이지 수.
    public List<Request_table> countLikeTitleShowOff(String address) {
        List<Request_table> showOffList = em.createQuery("select p from Request_table p where p.address like: address order by p.create_date DESC", Request_table.class)
                .setParameter("address", "%" + address + "%")
                .getResultList();
        return showOffList;
    }
    @Override
    public void updateViews(int seq){
        em.createQuery("update Request_table v set v.views=v.views+1 where v.seq=:seq")
                .setParameter("seq", seq)
                .executeUpdate();
    }

    public List<OfferList> findBySeq(int seq,int vpage){
        List<OfferList> offers = em.createQuery(
                        "SELECT NEW FinalPrjTest.FinalPrj.domain.OfferList(o.seq, o.id, o.is_cut, o.is_perm, o.is_dye, o.is_dry, o.detail, o.service, o.date, o.hour, o.minute, o.request_seq, d.photo_path,o.price, d.name, d.point_count, d.point, d.review, d.address, d.detailed_address) " +
                                "FROM Offer o JOIN Designer d " +
                                "ON o.id = d.id " +
                                "WHERE o.request_seq = :seq", OfferList.class)
                .setParameter("seq", seq)
                .setFirstResult(vpage)
                .setMaxResults(5)
                .getResultList();
        return offers;
    }

    public List<Offer> findOfferSize(int seq){
        List<Offer> offerSize= em.createQuery("select o from Offer o where o.request_seq=:seq",Offer.class).setParameter("seq",seq).getResultList();

        return offerSize;
    }

    public List<Request_table> findByIdRequest(String id){
        List<Request_table> requestTables = em.createQuery("select r from Request_table r where r.Writer_id = :id order by r.seq desc",Request_table.class).setParameter("id",id).getResultList();
        return requestTables;
    }

    public List<ShowOff> findByIdShowOff(String id){
        List<ShowOff> showOffs = em.createQuery("select s from ShowOff s where s.id = :id order by s.seq desc",ShowOff.class).setParameter("id",id).getResultList();
        return showOffs;
    }
    public List<Offer> findByIdOffer(String id){
        List<Offer> offers = em.createQuery("select o from Offer o where o.id = :id order by o.seq desc",Offer.class).setParameter("id",id).getResultList();
        return offers;
    }

    public List<Reservation> findByIdReservation(String id){
        List<Reservation_date> reservationDates = em.createQuery("select r from Reservation_date r where r.customer_id=:id",Reservation_date.class).setParameter("id",id).getResultList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation_date reservationDate : reservationDates) {
            Date d = reservationDate.getTime_date();
            String formattedDate = dateFormat.format(d);
            Reservation reservation = new Reservation(reservationDate,formattedDate);
            reservations.add(reservation);
        }

        return reservations;
    }

    public Reservation findBySeqReservation(int seq) {
        Reservation_date reservationDate = em.find(Reservation_date.class, seq);

        if (reservationDate == null) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = reservationDate.getTime_date();
        String formattedDate = dateFormat.format(d);
        Reservation reservation = new Reservation(reservationDate, formattedDate);
        return reservation;
    }

    public Offer findBySeqOffer(int seq){
        Reservation_date reservationDate = em.find(Reservation_date.class,seq);
        Offer offer = em.createQuery("select o from Offer o where o.id=:id and o.request_seq = :seq",Offer.class)
                .setParameter("id",reservationDate.getDesigner_id())
                .setParameter("seq",reservationDate.getRequest_seq())
                .getSingleResult();
        return offer;
    }

    public List<Reservation_date> findAlarmReservations(String userId){
        int alarm = 0;
        List<Reservation_date> reservationDates = em.createQuery("select r from Reservation_date r where r.customer_id = :id and r.alarm = :alarm",Reservation_date.class)
                .setParameter("id",userId)
                .setParameter("alarm",alarm)
                .getResultList();
        return reservationDates;
    }

    public void inssertAlarm(Alarm alarm){
        em.persist(alarm);
    }
    public void updateReservationData(int seq){
        Reservation_date reservationDate = em.find(Reservation_date.class,seq);
        reservationDate.setAlarm(1);
        em.merge(reservationDate);
    }

}
