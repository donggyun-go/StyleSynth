package FinalPrjTest.FinalPrj.controller;

import FinalPrjTest.FinalPrj.domain.*;
import FinalPrjTest.FinalPrj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ReservationController {
    private final DesignerService designerService;
    private final ReservationService reservationService;//기존에 있던 서비스에서 처리되는 내용이 있으면 사용하려고 연결함
    private final CustomUserDetailsService customUserDetailsService;
    private final ShowOffService showoffService;
    private final JavaMailSender javaMailSender;
    private final HttpServletRequest request;
    private final OfferService offerService;
    @Autowired
    public ReservationController(DesignerService designerService, ReservationService reservationService, CustomUserDetailsService customUserDetailsService, ShowOffService showoffService, JavaMailSender javaMailSender, HttpServletRequest request, OfferService offerService) {
        this.designerService = designerService;
        this.reservationService = reservationService;
        this.customUserDetailsService=customUserDetailsService;
        this.showoffService = showoffService;
        this.javaMailSender = javaMailSender;
        this.request = request;
        this.offerService = offerService;
    }

    /*
        임시로 만든 전체조회 나중에 페이징 처리 적용해서 작업할건데 시간 없으면 내가 함
    */
    @GetMapping("/WantedList")
    public String WantedList(Model model, @RequestParam(value = "vpage", defaultValue = "1") int vpage,
                             @RequestParam(value = "Writer_id", required = false) String Writer_id,
                             @RequestParam(value = "address", required = false) String address,
                             @RequestParam(value = "searchType", required = false) String searchType,
                             @RequestParam(value = "search", required = false) String search){
        //List<Request_table> WantedList = reservationService.findAllRequest();
        //model.addAttribute("WantedList",WantedList);
        List<Request_table> showoffSize;
        List<Request_table> showoffs;


        if (searchType != null && search != null && !search.isEmpty()) {
            if (searchType.equals("Writer_id")) {
                // ID로 조회하는 경우
                showoffs = reservationService.findLikeNameShowOff(search, (vpage - 1) * 10);
                showoffSize = reservationService.countLikeNameShowOff(search);
                model.addAttribute("searchType", "Writer_id");
            } else if (searchType.equals("address")) {
                // address으로 조회하는 경우
                showoffs = reservationService.findLikeTitleShowOff(search, (vpage - 1) * 10);
                showoffSize = reservationService.countLikeTitleShowOff(search);
                model.addAttribute("searchType", "address");
            } else {
                // 검색 조건이 잘못된 경우
                showoffSize = reservationService.findAllShowOff();
                showoffs = reservationService.findAllShowOffPage((vpage - 1) * 10);
            }
            model.addAttribute("search", search);
        } else {
            // 검색 조건이 없는 경우
            showoffSize = reservationService.findAllShowOff();
            showoffs = reservationService.findAllShowOffPage((vpage - 1) * 10);


        }
        int showoffCount = showoffSize.size();  //designerCount는 수(int형)
        System.out.println(showoffCount);
        int pageCount = (showoffCount + 9) / 10;
        model.addAttribute("ListSize", pageCount);  //전체 데이터 개수.
        model.addAttribute("showoffs", showoffs);  //인덱스 첫 번호
        model.addAttribute("vpage", vpage);  //페이징 수:1,2,3
        return "Reservation/requestBoard";
    }

    @GetMapping("/WantedDetail/{seq}")
    public String WantedDetail(@PathVariable int seq,Model model,
                               @RequestParam(value="vpage",defaultValue="1" )int vpage){
        reservationService.updateViews(seq);
        Request_table Wanted = reservationService.findRequest(seq);
        model.addAttribute("Wanted",Wanted);
        if(Wanted.getReservation()==0) {
            List<OfferList> offers = reservationService.findBySeq(seq, (vpage - 1) * 5);//시작 인덱스
            List<OfferList> offerLists = new ArrayList<>();
            for (OfferList o : offers) {
                Float op = 0.0f;
                if (o.getPoint_count() == 0) {
                    op = 0.0f;
                } else {
                    op = o.getPoint() / o.getPoint_count();
                }
                o.setAvg(op);
                offerLists.add(o);

            }
            List<Offer> offerSize = reservationService.findOfferSize(seq);

            int offercount = offerSize.size();
            int pageCount = (offercount + 4) / 5;
            for (OfferList o : offers) {
                o.toString();
            }
            model.addAttribute("ListSize", pageCount);
            model.addAttribute("offers", offerLists);
            model.addAttribute("vpage", vpage);
        }else {
            Offer offer = reservationService.findByseqOffer(Wanted.getSeq());
            Designer designer = offerService.getDesigner(offer.getId());
            List<Designerphoto> portfolios = offerService.getPortfolio(offer.getId());
            List<Review> reviews = offerService.getReviews(offer.getId());
            model.addAttribute("offer",offer);
            model.addAttribute("designer", designer);
            model.addAttribute("portfolios", portfolios);
            model.addAttribute("reviews", reviews);
        }
        return "Reservation/requestDetail";
    }


    /*
        /Wanted
        예약폼주면서 예약폼에 기입할 회원의 예약과 관련된 정보를 전달해 줌
        기존에 받았던 회원의 데이터 보내줌

        헤어합성이 완료된다면
        합성한 데이터에서 고객의 id로 만들어진 사진 리스트들을 가져와서 보내줘야 함

        여기 url로 넘어오기 전에는 스크립트 처리로 멤버타입을 비교해서 1번일 때만 2번이면 권한 없음
        비로그인 시 null 일 경우에는 로그인해야 이용 가능한 서비스라고 고지
    */
    @GetMapping("/Wanted")
    public String Wanted(HttpSession session, Model model){
        String id = (String) session.getAttribute("userId");
        System.out.println("또다른 컨트롤러에서"+id);
        Customer customer = designerService.findByIdCustomer(id);
        List<Ai_result> aiResult = reservationService.findbyidAiPhoto(id);
        System.out.println();
        for(Ai_result ai : aiResult){

            System.out.println(ai.getAi_photo_path());
        }
        model.addAttribute("customer",customer);
        model.addAttribute("aiResults",aiResult);
        return "customer/requestWrite";//작성폼 필요
    }



    /*
        /insertWanted
        작성한 의뢰서 저장하는 작업이 이뤄지는 곳
        여기서 의뢰서 작성폼에 불러온 회원의 정보를 바꾸게 되면 그 바뀐 정보를 저장하려고 작업을 추가함
        기존에 저장된 회원데이터에서 정보를 가져와서 비교
        비교하기 위해서는 여기 프론트에서 받는 값에 pw 가 없으니까 여기의 CustomerReWrite 객체는 pw가 null 상태
        기존 db에서 불러온 해당 id를 가진 데이터행의 CustomerReWrite에서 pw를 null로 바꾼뒤 오버라이딩 한 equls 를 이용해
        pw를 뺀 Custome+
        rReWrite 객체들의 필드에 저장된 값이 맞는지 비교해서 변경사항이 있을 때에만 변경하게 하였음

        의뢰서 테이블에서 타이틀 어떻게 하는지 모르겠음 일단 말 나온대로 선호지역+요청사항을 제목으로 가져옴
        그래서 기존 테이블에 타이틀이라고 추가 해주고 받아온 정보로 타이틀set 하는 형태로 하면 될듯함
    */
    @PostMapping("/insertWanted")
    public String insertWanted(Request_table request_table,
                               CustomerReWrite changeData,
                               HttpSession session,
                               @RequestParam("photo_path")MultipartFile photo_path){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        request_table.setCreate_date(LocalDateTime.parse(formattedDateTime, formatter));
        System.out.println("비번이 널인데 뭘로 들고올려나"+changeData.getPw());
        String id = (String) session.getAttribute("userId");
        System.out.println("세션 아이디"+id);
        request_table.setWriter_id(id);
        String title= "("+request_table.getAddress()+")"+request_table.getContent();//@*@*@일단 5월 22일 8시쯤 말 나온 방식으로 제목 정해줌
        request_table.setTitle(title);
        String img_path=reservationService.saveFile(photo_path,"/img/request");
        request_table.setOriginal_img_path(img_path);//사진 저장하는 메소드 그냥 가져옴
        //달력 들어오는 방식에 따라 코드가 추가될 수 있음
        CustomerReWrite c1=designerService.findByCustomerReWrite(id);
        c1.setPw(null);
        if(!c1.equals(changeData)){//의뢰서에서 기존 데이터를 불러오기 때문에 회원이 수정가능한 정보를 수정했을 시 변경사항을 회원 정보에 적용
            designerService.updateCustomer(changeData,id);
            System.out.println("의뢰서 작성 중 회원의 정보가 바뀐걸 감지해서 저장했습니다.");
        }else {
            System.out.println("회원의 정보가 바뀐게 없습니다.");
        }
        reservationService.insertRequest_table(request_table);
        return "redirect:/mainPage";//일단은 메인페이지로 보냄 만약 팝업으로 뜬 선택지 값이 들어온다면 선택지 값으로 조건문 걸어서 return을 다르게
    }
    /*
    여기는 프론트 페이징 테스트 하는 곳 입니다. 필요없는 페이지니까 나중에 지워도 상관 없는 곳 입니다.
    */

    @PostMapping("/insertReservation")  //이메일보내기~~~~~
    @ResponseBody
    public boolean insertReservation (Reservation_date reservationDate
            ,@RequestParam("date")String date
            ,@RequestParam("hour")String hour
            ,@RequestParam("minute")String minute,
                                      Model model){
        int seq = reservationDate.getRequest_seq();//의뢰서 seq로 작성자 id 찾아올려고
        Request_table Wanted = reservationService.findRequest(seq);
        String Customer_id= Wanted.getWriter_id();
        reservationDate.setCustomer_id(Customer_id);
        String rDate = date.replace("/", "");
        String aDate= rDate+hour+minute;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Date available;
        try {
            available = dateFormat.parse(aDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        reservationDate.setTime_date(available);

        Boolean boo = reservationService.InsertReservation(reservationDate);
        /*
        레포지토리에 추가하는거 만들고 서비스에 추가하는거 연결해야 하는데 귀찮다.
        */


        // HttpSession에서 로그인한 사용자의 정보 가져오기
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        //int memberType = (int) session.getAttribute("memberType");

        // 회원 유형에 따라 이메일 가져오기
        String userEmail = "";
        Customer customer = showoffService.findByIdCustomer(userId);
        userEmail = customer.getEmail();

        String designerEmail = "";
        //String designerOfferSeq = reservationService.findOfferId(seq);
        /*if (!designerOfferSeq1.isEmpty()) {
            String designerOfferSeq = designerOfferSeq1.get();
            Designer designer = showoffService.findByDesignerEmail(designerOfferSeq);
            designerEmail = designer.getEmail();
        } else {
            System.out.println("실패ㅠ");
        }*/


        Designer designer = showoffService.findByDesignerEmail(reservationDate.getDesigner_id());  //designerOfferSeq했을떄 offer객체를 반환중..오류임.
        designerEmail = designer.getEmail();

        // 이메일 발송
        sendConfirmationEmail(userEmail, designerEmail, model, date, hour, minute);
        reservationService.insertAlarm(reservationDate,reservationDate.getCustomer_id(),2);
        reservationService.insertAlarm(reservationDate,reservationDate.getDesigner_id(),2);

        return boo;
    }
    private void sendConfirmationEmail(String userEmail, String designerEmail, Model model, String available_date, String hour, String minute) {
        model.addAttribute("available_date", available_date);
        model.addAttribute("hour", hour);
        model.addAttribute("minute", minute);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail, designerEmail);
        message.setSubject("★StyleSynth 예약 완료★");
        message.setText("안녕하세요. StyleSynth 예약이 완료되었습니다." + "\n날짜: " + available_date + "\n시간:" + hour + ":" + minute + "\n감사합니다♥");

        javaMailSender.send(message);
    }
    /*private void sendConfirmationEmail2(String designerEmail) {
        SimpleMailMessage message2 = new SimpleMailMessage();
        message2.setTo(designerEmail);
        message2.setSubject("Reservation Confirmation");
        message2.setText("Your reservation has been confirmed. Thank you!");

        javaMailSender.send(message2);
    }*/
    /*
    만약 ajax로 변경하거나 제안서가 있고 없고의 경우로 삭제 실패 성공을 페이지로 나타낼 경우를 대비해서
    서비스를 boolean 형으로 작업함
    */
    @GetMapping("/DeleteWanted/{seq}")
    public String DeleteWanted(@PathVariable int seq){
        Boolean remove = reservationService.RemoveRequest(seq);
        if(remove==true){
            return "redirect:/mainPage";
        }else{
            return "redirect:/WantedDetail/"+seq;
        }

    }

    @GetMapping("/Check")
    public String CheckReservation(HttpSession session,Model model){
        String id = (String) session.getAttribute("userId");
        int type = (int) session.getAttribute("membertype");
        // 생각해보니까 sql 문에서 그냥 or로 커스터머나 디자이너나 둘중에 맞는거 가져오면 될듯
        List<Reservation> reservations = reservationService.findByIdReservation(id);


        model.addAttribute("reservations",reservations);

        return "test/ReservationCheckTest";


    }

}