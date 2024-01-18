package FinalPrjTest.FinalPrj.service;

import org.springframework.transaction.annotation.Transactional;
import FinalPrjTest.FinalPrj.domain.*;
import FinalPrjTest.FinalPrj.repository.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Transactional
public class ReservationService {
    private final FPReservationRepository FPreservationRepository;

    public ReservationService(FPReservationRepository FPreservationRepository){
        this.FPreservationRepository =FPreservationRepository;
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
    public void insertAiresult(Ai_result aiResult){
        FPreservationRepository.insertAiresult(aiResult);
    }
    public List<Ai_result> findbyidAiPhoto(String id){
        List<Ai_result> aiResults = FPreservationRepository.findbyidAiPhoto(id);
        return aiResults;
    }

    public void insertRequest_table(Request_table request_table){
        FPreservationRepository.insertRequest_table(request_table);
    }
    public List<Request_table> findAllRequest(){
        List<Request_table> WantedList = FPreservationRepository.findAllRequest();
        return WantedList;
    }

    public Request_table findRequest(int seq){
        Request_table Wanted = FPreservationRepository.findRequest(seq);
        return Wanted;
    }
    public boolean InsertReservation(Reservation_date reservationDate){
        Boolean boo = FPreservationRepository.InsertReservation(reservationDate);
        return boo;
    }
    public Boolean RemoveRequest(int seq){
        /*
            만약 삭제하는데 ajax를 쓰거나 혹은 댓글이 있고 없고의 반응을 어떠한 페이지로서 해줄 꺼라면 요렇게
            List<Offer> offers =  FPreservationRepository.findBySeqOffer(seq);
            if(offers==null){

            */
                FPreservationRepository.RemoveRequest(seq);
        return true;
         /*
            }else{
                return=false;
            }

        */

    }

    public void updateViews(int seq){
        FPreservationRepository.updateViews(seq);
    }
    /*페이징*/
    public List<Request_table> findAllShowOff(){
        List<Request_table> showOff = FPreservationRepository.findAllShowOff();
        return showOff;
    }
    public List<Request_table> findAllShowOffPage(int vpage){
        List<Request_table> showOff = FPreservationRepository.findAllShowOffPage(vpage);
        return showOff;
    }

    public List<Request_table> findLikeNameShowOff(String Writer_id, int vpage){
        List<Request_table> showOff = FPreservationRepository.findLikeNameShowOff(Writer_id, vpage);
        return showOff;
    }
    public List<Request_table> countLikeNameShowOff(String Writer_id){
        List<Request_table> showOff = FPreservationRepository.countLikeNameShowOff(Writer_id);
        return showOff;
    }

    public List<Request_table> findLikeTitleShowOff(String address, int vpage){
        List<Request_table> showOff = FPreservationRepository.findLikeTitleShowOff(address, vpage);
        return showOff;
    }
    public List<Request_table> countLikeTitleShowOff(String address){
        List<Request_table> showOff = FPreservationRepository.countLikeTitleShowOff(address);
        return showOff;
    }

    public List<OfferList> findBySeq(int seq,int vpage) {
        List<OfferList> offers = FPreservationRepository.findBySeq(seq,vpage);
        return offers;
    }

    public List<Offer> findOfferSize(int seq) {
        List<Offer> offerSize = FPreservationRepository.findOfferSize(seq);
        return offerSize;
    }

    public List<Request_table> findByIdRequest(String id) {
        List<Request_table> requestTables = FPreservationRepository.findByIdRequest(id);
        return requestTables;
    }

    public List<ShowOff> findByIdShowOff(String id) {
        List<ShowOff> showOffs = FPreservationRepository.findByIdShowOff(id);
        return showOffs;
    }

    public List<Offer> findByIdOffer(String id) {
        List<Offer> offers = FPreservationRepository.findByIdOffer(id);
        return offers;
    }

    public List<Reservation> findByIdReservation(String id) {
        List<Reservation> reservationDates = FPreservationRepository.findByIdReservation(id);
        return reservationDates;
    }

    public Reservation findByseqReservation(int seq) {
        Reservation reservation = FPreservationRepository.findBySeqReservation(seq);
        return reservation;
    }

    public Offer findByseqOffer(int seq) {
        Offer offer = FPreservationRepository.findBySeqOffer(seq);
        return offer;
    }

    public List<Reservation_date> findAlarmReservations(String userId) {
        List<Reservation_date> reservationDates = FPreservationRepository.findAlarmReservations(userId);
        return reservationDates;
    }

    public void insertAlarm(Reservation_date reservationDate,String id,int type){
        Request_table requestTable = findRequest(reservationDate.getRequest_seq());
        Alarm alarm = new Alarm();
        alarm.setId(id);
        alarm.setRequest_seq(reservationDate.getRequest_seq());
        alarm.setTitle(requestTable.getTitle());
        alarm.setType(type);
        FPreservationRepository.inssertAlarm(alarm);
    }

    public void updateReservationData(int seq) {
        FPreservationRepository.updateReservationData(seq);
    }
}
