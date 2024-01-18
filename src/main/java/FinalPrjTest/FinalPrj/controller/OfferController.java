package FinalPrjTest.FinalPrj.controller;

import FinalPrjTest.FinalPrj.domain.*;
import FinalPrjTest.FinalPrj.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OfferController {

    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    /*
     * 제안서 작성페이지로 이동하는 함수
     * 디자이너의 기본 정보(이름, 전화번호, 근무지등)를 불러오기 위해 디자이너 객체를 넘겨줌
     * 포트폴리오 사진 리스트 넘겨줌
     * 후기 객체 리스트 넘겨줌
     * 05.22: 의뢰서 객체를 넘겨 받아서 의뢰서 고유번호(seq), 날짜(date), 시작 시간, 끝 시간을 보내줘야함
     * 시작 시간, 끝 시간을 가지고 html에서 그 사이 시간만 선택 할 수 있게 해야함
     */
    @GetMapping("/writeOffer/{seq}")
    public String writeOffer(@PathVariable int seq, HttpSession session, Model model) {
        //세션에 저장된 아이디 가져오기
        String loggedIn = (String) session.getAttribute("userId");

        Request_table request = offerService.getRequest(seq);

        Designer designer = offerService.getDesigner(loggedIn);
        List<Designerphoto> portfolios = offerService.getPortfolio(loggedIn);
        List<Review> reviews = offerService.getReviews(loggedIn);

        System.out.println("request.getStart_hour(): " + request.getStart_hour());

        int startHour = Integer.parseInt(request.getStart_hour());
        String startMin = request.getStart_minute();
        int endHour = Integer.parseInt(request.getEnd_hour());
        String endMin = request.getEnd_minute();

        String date = request.getAvailable_date();

        //requestSeq는 html에서 hidden으로
        int requestSeq = request.getSeq();

        model.addAttribute("requestSeq", requestSeq);
        model.addAttribute("request", request);
        model.addAttribute("startHour", startHour);
        model.addAttribute("startMin", startMin);
        model.addAttribute("endHour", endHour);
        model.addAttribute("endMin", endMin);
        model.addAttribute("date", date);
        model.addAttribute("designer", designer);
        model.addAttribute("portfolios", portfolios);
        model.addAttribute("reviews", reviews);

        return "Reservation/proposalWrite";
    }

    @PostMapping("/submitOffer")
    public String submitOffer(Offer offer, RedirectAttributes redirectAttributes) {
        offerService.submitOffer(offer);
        //redirect할 때, 객체 유지시켜주는 것
        //redirectAttributes.addFlashAttribute("offer", offer);
        Request_table request = offerService.getRequest(offer.getRequest_seq());
        Alarm alarm = new Alarm();
        alarm.setRequest_seq(offer.getRequest_seq());
        alarm.setId(request.getWriter_id());
        alarm.setTitle(request.getTitle());
        alarm.setType(1);
        offerService.insertAlarm(alarm);

        return "redirect:/viewOffer/"+offer.getSeq();
    }

    /*
     * 제안서 상세 조회 페이지로 이동하는 함수
     * 여기서 seq는 제안서의 seq
     * submitOffer에서 offer 객체를 받아와서 그대로 html로 넘겨줌
     * redirect에서 객체를 받아올 때, ModelAttribute 사용하면 됨.
     *
     * 추후, 세션에 로그인된 아이디와 offer 객체에 담긴 작성자의 아이디를 비교해서 같은 경우에만 상세 조회로 넘어가게 처리해야함
     */
    @GetMapping("/viewOffer/{seq}")
    public String viewOffer(HttpSession session, Model model,@PathVariable int seq) {

        Offer offer = offerService.findBySeq(seq);
        Designer designer = offerService.getDesigner(offer.getId());
        List<Designerphoto> portfolios = offerService.getPortfolio(offer.getId());
        List<Review> reviews = offerService.getReviews(offer.getId());


        model.addAttribute("offer", offer);
        model.addAttribute("designer", designer);
        model.addAttribute("portfolios", portfolios);
        model.addAttribute("reviews", reviews);

        return "Reservation/proposalDetail";
    }
}