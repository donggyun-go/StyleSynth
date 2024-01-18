package FinalPrjTest.FinalPrj.repository;

import FinalPrjTest.FinalPrj.domain.*;

import java.util.List;

public interface FPReservationRepository {
    void insertRequest_table(Request_table request_table);

    void insertAiresult(Ai_result aiResult);//저장하기를 눌렀을 때 합성된 이미지 이름을 가져와서 여기에 id하고 저장
    List<Ai_result> findbyidAiPhoto(String id);//의뢰서 작성때 사진 선택가능하게 사진들 리스트로 뽑아오기,다른 곳에서 사용할 수도 있음

    List<Request_table> findAllRequest();
    Request_table findRequest(int seq);
    boolean InsertReservation(Reservation_date reservationDate);
    void RemoveRequest(int seq);

    /*페이징*/
    List<Request_table> findAll(String Writer_id);
    List<Request_table> findAllShowOff();
    List<Request_table> findAllShowOffPage(int vpage);


    List<Request_table> findLikeNameShowOff(String Writer_id, int vpage);

    List<Request_table> countLikeNameShowOff(String Writer_id);

    List<Request_table> findLikeTitleShowOff(String address, int vpage);

    List<Request_table> countLikeTitleShowOff(String address);
    void updateViews(int seq);

    List<OfferList> findBySeq(int seq,int vpage);

    List<Offer> findOfferSize(int seq);

    List<Request_table> findByIdRequest(String id);

    List<ShowOff> findByIdShowOff(String id);

    List<Offer> findByIdOffer(String id);

    List<Reservation> findByIdReservation(String id);

    Reservation findBySeqReservation(int seq);

    Offer findBySeqOffer(int seq);

    List<Reservation_date> findAlarmReservations(String userId);


    void inssertAlarm(Alarm alarm);

    void updateReservationData(int seq);
}
