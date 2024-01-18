package FinalPrjTest.FinalPrj.controller;

import FinalPrjTest.FinalPrj.domain.*;
import FinalPrjTest.FinalPrj.service.CustomUserDetailsService;
import FinalPrjTest.FinalPrj.service.DesignerService;
import FinalPrjTest.FinalPrj.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@Controller
public class DesignerController {

    private final DesignerService designerService;

    private final ReservationService reservationService;

    private final CustomUserDetailsService customUserDetailsService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public DesignerController(DesignerService designerService,ReservationService reservationService,CustomUserDetailsService customUserDetailsService,AuthenticationManager authenticationManager) {
        this.designerService = designerService;
        this.reservationService = reservationService;
        this.customUserDetailsService=customUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * 로그인 할때 메인페이지로 오고 받은 값으로 세션에 정보 줄거임
     * 비로그인시에 메인페이지 진입도 생각해야 함으로 null 허용
     *
     * 05.25(수빈): 불필요한 조건문 삭제. 코드 가독성을 위해 리팩토링 완료.
     */
    @RequestMapping(value = "/mainPage", method = {RequestMethod.POST, RequestMethod.GET})
    public String mainpage(@RequestParam(value = "userId", required = false) String userId,
                           @RequestParam(value = "userPwd", required = false) String userPwd,
                           HttpSession session) {

        //로그인 정보가 들어왔을 경우
        if (userId != null && !userId.isEmpty() && userPwd != null && !userPwd.isEmpty()) {
            int membertype = 0;

            //고객 리스트에서 일치하는 아이디를 찾았을 경우
            Customer customer = designerService.findByIdCustomer(userId);
            String pwd;
            if (customer != null) {
                membertype = customer.getMemberType();
                pwd = customer.getPw();
            } else {
                //디자이너 리스트에서 일치하는 아이디를 찾았을 경우
                Designer designer = designerService.findByIdDesigner(userId);
                pwd= designer.getPw();
                membertype =designer.getMemberType(); //스프링 시큐리티 적용하니까 갑자기 이거 못불러옴
            }
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);

            String encodedPassword = passwordEncoder.encode(userPwd);
            System.out.println("암호화되어있는 비번"+encodedPassword);
            System.out.println("db에서 가져온 암호화되어있는 비번"+pwd);
            if(passwordEncoder.matches(userPwd,pwd)){
                System.out.println("일치");
            }else {
                System.out.println("불일치");
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userPwd);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            for(GrantedAuthority a : authentication.getAuthorities()){
                System.out.println("메인 최종권한"+a.getAuthority());
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //세션에 아이디와 회원 타입 번호 저장
            //session.setAttribute("userDetails", userDetails);
            session.setAttribute("userId", userId);
            session.setAttribute("membertype", membertype);
        }

        return "Mainpage";
    }

    @GetMapping("/login")//로그인
    public String login() {
        return "Login/login";
    }

    @GetMapping("/logout")//로그아웃
    public String logout(HttpSession session) {

        session.removeAttribute("userId");
        session.removeAttribute("membertype");
        session.removeAttribute("userDetails");

        return "redirect:/mainPage";
    }

    /*
     * 05.25(수빈): 코드 정리
     */
    @PostMapping("/checkLogin")//로그인 성공여부의 ajax
    @ResponseBody//여기서 원래 id랑 비번으로 해당 객체 하나만 가져와도 되는데 repository 있는거 쓸려고 All 사용
    public String checkLogin(@RequestParam("id") String id, @RequestParam("pwd") String pwd) {

        List<Designer> designers = designerService.findAllDesigner();
        for (Designer designer : designers) {
            if (designer.getId().equals(id) && passwordEncoder.matches(pwd, designer.getPw())) {
                return "true";
            }
        }

        List<Customer> customers = designerService.findAllCustomer();
        for (Customer customer : customers) {
            if (customer.getId().equals(id) &&  passwordEncoder.matches(pwd, customer.getPw())) {
                return "true";
            }
        }


        return "false";
    }


    /*
     * 05.25(수빈): 코드 리팩토링 - 별점 평점 구하는 함수를 DesignerService에 만들어서 호출함
     */
    @GetMapping("/goMyPage")
    public String gomypage(HttpSession session, Model model) {
        String id = (String) session.getAttribute("userId");
        int membertype = (int) session.getAttribute("membertype");

        //고객일 경우
        if (membertype == 1) {
            Customer customer = designerService.findByIdCustomer(id);
            model.addAttribute("customer", customer);
            return "Mypage/customerMyPage";
        }

        //디자이너일 경우
        Designer designer = designerService.findByIdDesigner(id);
        model.addAttribute("designer", designer);

        List<ShowOff> showOffs = designerService.findByIdShowOff(id);
//        if (showOffs == null) {
//            System.out.println("리스트가 널이다.");
//        }
//        System.out.println("list size" + showOffs.size());
        model.addAttribute("showOffs", showOffs); // 데이터를 모델에 추가합니다.

        double avg = designerService.calcAvg(designer);
        model.addAttribute("avg", avg);

        return "Mypage/designerMyPage";
    }

    @GetMapping("/testPage")
    public String testPage(){

        return "test/pageTest";
    }
    @GetMapping("/getAllData")
    public ResponseEntity<MyPageData> getAllData(HttpSession session) {
        String id = (String) session.getAttribute("userId");

        List<Request_table> requestTables = reservationService.findByIdRequest(id);
        List<RequestAndReservation> requestAndReservations = new ArrayList<>();
        for(Request_table r : requestTables){
            int seq = r.getSeq();
            Reservation reservation = reservationService.findByseqReservation(seq);
            RequestAndReservation requestAndReservation=null;
            requestAndReservation = new RequestAndReservation(r,reservation);
            System.out.println(requestAndReservation.getRequestTable().getTitle());
            requestAndReservations.add(requestAndReservation);
        }
        for(RequestAndReservation r : requestAndReservations){
            System.out.println(r.getRequestTable().getCustomer_name());
            if(r.getReservation()!=null){
                System.out.println("널 아닌애" + r.getReservation().getReservationDate().getRequest_seq());
            }
        }



        List<ShowOff> showOffs = reservationService.findByIdShowOff(id);

        MyPageData myPageData = new MyPageData(requestAndReservations,showOffs);

        return ResponseEntity.ok().body(myPageData);//여기서 두개를 보내야 한다.
    }

    @GetMapping("/getAllDataD")
    public ResponseEntity<DMyPageData> getAllDataD(HttpSession session) {
        String id = (String) session.getAttribute("userId");

        List<Offer> offers = reservationService.findByIdOffer(id);
        List<ShowOff> showOffs = reservationService.findByIdShowOff(id);

        DMyPageData dmyPageData = new DMyPageData(offers,showOffs);

        return ResponseEntity.ok().body(dmyPageData);//여기서 두개를 보내야 한다.
    }
    /*
     * 05.25(수빈): 불필요한 조건문 삭제
     */
    @GetMapping("/delete")//계정탈퇴페이지
    public String deleteMember(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        int membertype = (int) session.getAttribute("membertype");

        if (membertype == 1) {
            designerService.deleteCustomer(userId);
        } else {
            designerService.deleteDesiger(userId);
        }

        session.removeAttribute("userId");
        session.removeAttribute("membertype");

        return "redirect:/mainPage";
    }


    @GetMapping("/Regist-choice")
    public String registchoice() {
        //회원가입 선택
        return "Login/regist-choice";
    }

    @GetMapping("/Regist-customer")
    public String registCustomer() {
        //일반 회원용
        return "Login/regist-customer";
    }

    @GetMapping("/Regist-designer")
    public String registDesigner() {
        //미용사 회원용
        return "Login/regist-designer";
    }


    /*
     * 디자이너 리스트가 출력되는 디자이너 소개소 전체 조회
     * 여기서 오는 값은 이름 값으로 검색할 때, 다시 이곳을 매핑 시키고 검색을 안하는 경우도 고려해 null 허용
     * 검색한 값이 있으면 페이징을 검색한 결과로
     * 없으면 디자이너 리스트 전체를 페이징 시켜서 가져옴
     * 다른 게시판도 이런 식으로 사용해야함
     */
    @GetMapping("/designerBoard")
    public String designerBoard(Model model, @RequestParam(value = "vpage", defaultValue = "1") int vpage,
                                @RequestParam(value = "name", required = false) String name) {
        List<DesignerData> designerDataList = designerService.listDesignerPage(name, (vpage - 1) * 10);
        int pageCount = designerService.DesignerPagingCount(name);
        //이름 검색을 사용했을 경우
        if (name != null) {
            model.addAttribute("name", name);
        }
        model.addAttribute("ListSize", pageCount);
        model.addAttribute("designerDataList", designerDataList);
        model.addAttribute("vpage", vpage);

        return "DesignerBoard/designersList";
    }

    @GetMapping("/detail/{id}")
    public String getDesignerDetail(@PathVariable String id, Model model, HttpSession session) {
        //제대로 값이 오고있는지 확인하는 코드들은 나중에 지워도 무관 대부분 프린트 문들이 확인하는 코드들
        //디자이너 게시판에서 게시물 선택했을 때 보여줄 상세페이지
        //avg 구하는 코드 꽤 자주나오니 그냥 메소드 만들기
        Designer designer = designerService.findByIdDesigner(id);
        String userId = (String) session.getAttribute("userId");

        double avg = designerService.calcAvg(designer);

        //String userid="d30";회원가입과 로그인 구현하기 이전의 하드코딩 값
        model.addAttribute("avg", avg);
        model.addAttribute("userId", userId);
        model.addAttribute("designer", designer);
        return "designerDetailBoard";
    }

    //마이페이지에서 수정하기인데 마이페이지에서 정보는 전부다 수정하기이니까 고치긴 해야함
    //이거 ajax로 처리할듯? 같은곳에서 조건문 달아서 처리 할려고 했는데 그냥 경로를 일반 미용사 로 나눠서 처리해야 짦을듯
    //수정하기에서 자기 포트폴리오 사진도 가능하게 바꿈
    //포토폴리오 정보 수정은 인서트 및 삭제는 따로 관리하기로 함 같은 페이지 이지만 버튼을 더 줘서
    //이렇게 비교할 때 사진의 이름값을 designerReWrite 객체에 넣어줘야 하는데 바뀌지 않을 때 그러면 사진의 이름값을 어떻게 받을 수 있을까
    //사진값이 변할 값이든 안변할 값이든 가지고 있을 사진 이름을 value로 가지고 있는 객체가 필요함 만약 바꾸지 않으면
    //기존의 사진 이름을 가져와서 비교할 때 써야함 바뀌면 바뀐 값이 적용되어 있으면 되는데 value가 그건 알아서 해주니 상관 없긴 함
    @PostMapping("/DSelfAbout")//작동을 할련지 조차 모르겠음
    @ResponseBody
    public String DmyPageUpdate(DesignerReWrite designerReWrite, HttpSession session
            , @RequestParam(value = "photo_path_change", required = false) MultipartFile photo_path_change
            , @RequestParam(value = "certificate_path2_change", required = false) MultipartFile certificate_path2_change
            , @RequestParam(value = "certificate_path3_change", required = false) MultipartFile certificate_path3_change) {

        String id = (String) session.getAttribute("userId");
        String path = "/img/designers";

        if (photo_path_change != null && !photo_path_change.isEmpty()) {

            designerReWrite.setPhoto_path(saveFile(photo_path_change, path));
        }
        if (certificate_path2_change != null && !certificate_path2_change.isEmpty()) {
            designerReWrite.setCertificate_path2(saveFile(certificate_path2_change, path));
        }
        if (certificate_path3_change != null && !certificate_path3_change.isEmpty()) {
            designerReWrite.setCertificate_path3(saveFile(certificate_path3_change, path));
        }

        //designerService.updateDesigner(designerReWrite,id);
        DesignerReWrite d1 = designerService.findByidRewrite(id);
        String result = "false";
        if (!d1.equals(designerReWrite)) {//기존값 불러와서 비교해서 내용 같으면 업데이트 안함
            System.out.println("뭔가 변함");
            designerService.updateDesigner(designerReWrite, id);
            DesignerReWrite d2 = designerService.findByidRewrite(id);
            if (designerReWrite.equals(d2)) {//업데이트 후 같은 아이디 불러와서 비교해서 같으면 true
                result = "true";
                System.out.println("정?상");
            } else {
                System.out.println("망가질 위기");
                result = "fails";//최종 단계에서는 이 값을 받는 ajax를 삭제 지금은 업데이트에 실패한건지 업데이트를 값이 같아서 안한건지 성공한건지
                //세가지 여부를 알기 위해서 이렇게 작성함
            }
        } else {
            result = "false";
            System.out.println("변한거 없음");
        }
        return result;//저장 성공 여부 저장 작업의 여부
    }

    @PostMapping("/DportfolioAdd")
    public String DportAdd(@RequestParam(value = "portfolioPath", required = false) MultipartFile portfolioPath, HttpSession session) {
        String id = (String) session.getAttribute("userId");
        Designerphoto designer_photo = new Designerphoto();
        if (portfolioPath != null && !portfolioPath.isEmpty()) {
            designer_photo.setId(id);
            designer_photo.setPhoto_path(saveFile(portfolioPath, "/img/designersportfolio/"));
            designerService.insertDesignersPortfolioPath(designer_photo);
        }
        return "redirect:/goMyPage";
    }

    @PostMapping("/dropPortfolio")
    public String dropportfolio(@RequestParam(value = "dropPortfolioPath", required = false) String dropPortfolioPath, HttpSession session) {
        String id = (String) session.getAttribute("userId");
        Designerphoto designer_photo = new Designerphoto();
        designer_photo.setId(id);
        designer_photo.setPhoto_path(dropPortfolioPath);
        designerService.dropPortfolio(designer_photo);
        return "redirect:/goMyPage";
    }

    @PostMapping("/cSelfAbout")
    @ResponseBody
    public String CmyPageUpdate(CustomerReWrite customerReWrite, HttpSession session) {
        //여기는 사진 없어서 그나마 간결한 편
        String id = (String) session.getAttribute("userId");
        CustomerReWrite c1 = designerService.findByCustomerReWrite(id);
        String result = "false";
        if (!c1.equals(customerReWrite)) {
            designerService.updateCustomer(customerReWrite, id);
            CustomerReWrite c2 = designerService.findByCustomerReWrite(id);
            if (customerReWrite.equals(c2)) {
                result = "true";
            } else {
                result = "fails";
            }
        } else {
            result = "false";
        }
        return result;
    }

    @GetMapping("/viewCustomerRegisterForm")
    public String viewCustomerregistform() {
        //일반회원 회원가입 폼 목업 지워도 무관
        return "Login/registrcustomer";
    }

    @PostMapping("/checkID")
    @ResponseBody
    public String checkId(@RequestParam("id") String id) {
        //회원가입할 때 아이디 값으로 찾아서 둘다 널이면 true 주면서 ajax 처리
        String check = "false";
        Customer customer = designerService.findByIdCustomer(id);
        Designer designer = designerService.findByIdDesigner(id);
        if (customer == null && designer == null) {
            check = "true";
        }
        return check;
    }

    @GetMapping("/findID")
    public String findIdPage() {
        //아이디 찾기 페이지 이동
        return "Login/find-id";
    }

    @PostMapping("/findByIdMember")
    @ResponseBody
    public String findbyIdMember(@RequestParam("name") String name, @RequestParam("email") String email) {
        //이메일 이름 으로 아이디 찾는 부분임
        //받아온 값으로 두 테이블에서 조회하고
        //customer에서 false면 디자이너 꺼 보내주기 ajax 에서 리턴받을때 data="false"면 못찾는다고 알리고
        // 찾아온 아이디 값이면 해당 아이디 값을 span 태그 안에다 보여주려고 하기 때문에
        //cid 가 false 아니면 그냥 cid 보내주기 id 값이 있다는 뜻이니까 최대한 줄여봄
        String id = "";
        String Cid = designerService.findByNameEmailCustomer(name, email);
        String Did = designerService.findByNameEmailDesigner(name, email);
        if (Cid.equals("false")) {
            id = Did;
        } else {
            id = Cid;
        }
        return id;
    }

    @GetMapping("/findPwd")
    public String findPwdPage() {
        //비밀번호 찾기 페이지 이동
        return "Login/find-password";
    }

    @PostMapping("/findByPwdMember")
    @ResponseBody
    public String findByPwdMember(
            @RequestParam("name") String name
            , @RequestParam("id") String id
            , @RequestParam("email") String email) {
        //지금 처음 알았는데 순서도 페이지 순서대로 지켜줘야 하나봄? 아이디랑 email 위치만 바꿨는데 오류가 해결
        //이메일 이름 으로 비밀번호 찾는 부분임
        //받아온 값으로 두 테이블에서 조회하고
        //customer에서 false면 디자이너 꺼 보내주기 ajax 에서 리턴받을때 data="false"면 못찾는다고 알리고
        // 찾아온 비밀번호 값이면 해당 비밀번호 값을 span 태그 안에다 보여주려고 하기 때문에
        //cid 가 false 아니면 그냥 cid 보내주기 pw 값이 있다는 뜻이니까 최대한 줄여봄
        String pwd = "";
        String Cpw = designerService.findByNameEmailCustomerPwd(name, email, id);
        String Dpw = designerService.findByNameEmailDesignerPwd(name, email, id);
        if (Cpw.equals("false")) {
            pwd = Dpw;
        } else {
            pwd = Cpw;
        }
        return pwd;
    }


    @PostMapping("/registCustomer")//일반회원 임시 회원가입
    public String insertCustomer(Customer customer,
                                 @RequestParam("email1") String email1,
                                 @RequestParam("email2") String email2,
                                 @RequestParam("phone_num1") String phone_num1,
                                 @RequestParam("phone_num2") String phone_num2,
                                 @RequestParam("phone_num3") String phone_num3) {
        //일반 회원 회원가입 폼 받아서 insert 하는 곳임
        //줄줄이 써있는 부가적인 것도 클래스로 만들어서 처리는 가능하나 나중 일
        String email = email1 + "@" + email2;
        String phone_num = phone_num1 + phone_num2 + phone_num3;
        customer.setEmail(email);
        customer.setPhone_num(phone_num);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(customer.getPw());
        customer.setPw(encryptedPassword);

        Set<String> roles= new HashSet<>();
        roles.add("ROLE_CUSTOMER");
        customer.setRoles(roles);
        designerService.insertCustomer(customer);
        return "Login/login";
    }


    @GetMapping("/findCity")
    @ResponseBody
    public Map<String, Object> findCity(@RequestParam("local") String local) {
        //회원가입 시에 시,도 선택시 군,구 자동으로 맞춰서 나오게 하는 ajax 처리하는 곳
        //db에 한땀한땀 넣을 자신 있으신 분 있으시면 감사.
        String[] citys;
        citys = designerService.locateCities(local);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("citys", citys);
        return resultMap;
    }

    @GetMapping("/registDesignerForm")
    public String designerSave() {
        //디자이너 회원가입 폼 목업으로 가는 곳 지워도 무관
        return "Login/regist-designer";
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

    @PostMapping(value = "/registDesigners", produces = "application/json; charset=UTF-8")
    public String insertDesigner(Designer designer,
                                 @RequestParam("email1") String email1,
                                 @RequestParam("email2") String email2,
                                 @RequestParam("phone_num1") String phone_num1,
                                 @RequestParam("phone_num2") String phone_num2,
                                 @RequestParam("phone_num3") String phone_num3,
                                 @RequestParam(value = "photoPath", required = false) MultipartFile photoPath,
                                 @RequestParam(value = "certificatePath1", required = false) MultipartFile certificatePath1,
                                 @RequestParam(value = "certificatePath2", required = false) MultipartFile certificatePath2,
                                 @RequestParam(value = "certificatePath3", required = false) MultipartFile certificatePath3,
                                 @RequestParam(value = "portfolioPath", required = false) MultipartFile[] portfolioPath) {
        //일반 회원 회원가입 폼 받아서 insert 하는 곳임
        //줄줄이 써있는 부가적인 것도 클래스로 만들어서 처리는 가능하나 나중 일
        //사진은 클래스로 받아왔을 때 이 코드로 작동하는지 자신 없음
        if (portfolioPath == null) {
            System.out.println("사진이 널이다.");
        }

        String email = email1 + "@" + email2;
        String phone_num = phone_num1 + phone_num2 + phone_num3;

        designer.setEmail(email);
        designer.setPhone_num(phone_num);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(designer.getPw());
        designer.setPw(encryptedPassword);

        Set<String> roles= new HashSet<>();
        roles.add("ROLE_DESIGNER");
        designer.setRoles(roles);

        String path = "/img/designers";

        if (photoPath != null && !photoPath.isEmpty()) {
            designer.setPhoto_path(saveFile(photoPath, path));
        }
        if (certificatePath1 != null && !certificatePath1.isEmpty()) {
            designer.setCertificate_path1(saveFile(certificatePath1, path));
        }
        if (certificatePath2 != null && !certificatePath2.isEmpty()) {
            designer.setCertificate_path2(saveFile(certificatePath2, path));
        }
        if (certificatePath3 != null && !certificatePath3.isEmpty()) {
            designer.setCertificate_path3(saveFile(certificatePath3, path));
        }
        if (portfolioPath != null && portfolioPath.length > 0) {
            for(MultipartFile p : portfolioPath){
                Designerphoto designer_photo = new Designerphoto();
                designer_photo.setId(designer.getId());
                designer_photo.setPhoto_path(saveFile(p, "/img/designersportfolio/"));
                designerService.insertDesignersPortfolioPath(designer_photo);
            }
        }
        designerService.insertDesigners(designer);


        return "Login/login";
    }
    //의뢰서 작성 부분

    @GetMapping("/AiSynthesis")//로그인
    public String AiSynthesis() {
        return "AISynthesis/aiSynthesis";
    }

    @ResponseBody
    @PostMapping("/InsertReview")
    public boolean InsertReview(Review review){
        System.out.println("디자이너 아이디"+review.getDesigner_id());

        Boolean One = designerService.InsertReview(review);
        return One;
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }

    @ResponseBody
    @PostMapping("/getAlarm")
    public List<Alarm> getAlarm(HttpSession session){
        String userId = (String) session.getAttribute("userId");
        int membertype = (int) session.getAttribute("membertype");
        if(membertype == 1){
            List<Reservation_date> reservationDates = reservationService.findAlarmReservations(userId);
            Date date = new Date();
            for(Reservation_date r : reservationDates){
                Date reservationTime = r.getTime_date();

                // 날짜를 가져오기 위한 Calendar 객체 이곳은 오늘 부분
                Calendar currentCal = Calendar.getInstance();
                currentCal.setTime(date);
                int currentYear = currentCal.get(Calendar.YEAR);
                int currentDay = currentCal.get(Calendar.DATE);
                //이곳은 가져온 날짜에 년도와 일을 구하는 부분
                Calendar reservationCal = Calendar.getInstance();
                reservationCal.setTime(reservationTime);
                int reservationYear = reservationCal.get(Calendar.YEAR);
                int reservationDay = reservationCal.get(Calendar.DATE);

                if (currentYear == reservationYear && currentDay == reservationDay) {
                    // 오늘과 예약 날짜가 같은 경우
                    reservationService.insertAlarm(r,r.getCustomer_id(),3);
                    reservationService.updateReservationData(r.getRequest_seq());
                }
            }
        }

        //위에서 해당하는 날짜에 대한 데이터를 넣는 과정을 거치고 최종적으로 알림db에서 값을 가져옴
        List<Alarm> alarmList = designerService.findByIdAlarm(userId);

        return alarmList;

    }

    @ResponseBody
    @PostMapping("DeleteThisAlarm")
    public Boolean DeleteThisAlarm(@RequestParam("seq") int seq){
        Boolean result = false;
        result = designerService.deleteThisAlarm(seq);
        return result;
    }

    @ResponseBody
    @PostMapping("DeleteThisAllAlarm")
    public Boolean DeleteThisAllAlarm(@RequestBody List<Integer> seqList){
        Boolean result = false;
        for(int s : seqList ){
            result = designerService.deleteThisAlarm(s);
            if(result==false){
                System.out.println("뭔가 의도와 다르게 흘러간거임 해당 메세지가 뜨면 귀찮아 지는거");
                break;
            }
        }
        return result;
    }

    @PostMapping("/checkOffer")
    @ResponseBody
    public Boolean checkOffer(@RequestParam("seq") int seq,HttpSession session){
        System.out.println("체크 오퍼에 왔다감");
        String id = (String) session.getAttribute("userId");
        Boolean result = designerService.findOffer(id,seq);
        return result;
    }

    @PostMapping("/getReview")
    @ResponseBody
    public ResponseEntity<List<Review>> getReview(HttpSession session){
        String id = (String) session.getAttribute("userId");
        int membertype = (int) session.getAttribute("membertype");
        List<Review> reviews = null;
        if(membertype == 1){
            reviews = designerService.findByIdCReview(id);
        }else {
            reviews = designerService.findByIdDReview(id);
        }

        return ResponseEntity.ok().body(reviews);
    }

    @PostMapping("/saveAiPhoto")
    @ResponseBody
    public Boolean saveAiPhoto(HttpSession session){
        String id = (String) session.getAttribute("userId");
        String path = "/home/ubuntu/djangoSource/img_test/results/results/synthesized_image/";
        String fileName = "results_0.png";
        Boolean result;
        File imageFile = new File(path + fileName);
        Ai_result aiResult = new Ai_result();
        if (imageFile.exists()) {
            aiResult.setId(id);
            String destinationPath = "/img/AiResult/";
            String newFileName = UUID.randomUUID().toString();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            String savedFileName = newFileName + "." + fileExtension;
            String savedFilePath = destinationPath + savedFileName;
            try {
                Files.copy(imageFile.toPath(), Paths.get(savedFilePath));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            aiResult.setAi_photo_path(savedFileName);
            result = designerService.saveAiPhoto(aiResult);

        } else {
            return false;
        }

        return result;
    }






}
