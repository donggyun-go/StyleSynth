package FinalPrjTest.FinalPrj.service;

import FinalPrjTest.FinalPrj.domain.*;
import FinalPrjTest.FinalPrj.repository.FPDesignerRepository;

import org.springframework.transaction.annotation.Transactional;


import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
public class DesignerService {

    private final FPDesignerRepository FPDesignerRepository;


    public DesignerService(FPDesignerRepository FPDesignerRepository) {
        this.FPDesignerRepository = FPDesignerRepository;
    }

    public List<Designer> findAllDesigner() {
        //미용사 전부 조회
        List<Designer> designers = FPDesignerRepository.findAllDesigner();
        return designers;
    }

    public List<Customer> findAllCustomer() {
        //일반 회원 전부 조회
        List<Customer> customers = FPDesignerRepository.findAllCustomer();
        return customers;
    }

    //05.25(수빈): 추가함
    public List<Designer> findLikeIdDesigners(String name, int v_page) {
        //디자이너 검색 조회 페이징 처리 된거임 v_page는 카운트해서 나온 숫자들 중에서 페이지 수로 선택한 값 1~10 까지 페이지 수 처리 해놓고 2번 클릭하면 2번 오게
        List<Designer> designers = FPDesignerRepository.findLikeIdDesigners(name, v_page);
        return designers;
    }

    //    public List<Designer> findLikeIdDesigners(String id){
//        List<Designer> designers=finalProjectRepository.findLikeIdDesigners(id);
//        return designers;
//    }
    public List<Designer> countLikeIdDesigners(String name) {
        //사실 검색 조회의 기능은 위에랑 비슷한데 페이징 처리할 페이지 카운트 하는데 필요해서 그냥 하나 더 씀
        List<Designer> designers = FPDesignerRepository.countLikeIdDesigners(name);

        return designers;
    }

    public Designer findByIdDesigner(String id) {
        //id로 미용사 조회
        Designer designer = FPDesignerRepository.findByIdDesigner(id);
        return designer;
    }

    public Customer findByIdCustomer(String id) {
        //id로 일반 회원 조회
        Customer customer = FPDesignerRepository.findByIdCustomer(id);
        return customer;
    }

    public void updateSelfAbout(String self_about, String id) {
        //자기소개만 업데이트
        FPDesignerRepository.updateSelfAbout(self_about, id);
    }

    public void insertCustomer(Customer customer) {
        //일반회원 회원가입
        FPDesignerRepository.insertCustomer(customer);
    }

    public void
    insertDesigners(Designer designer) {
        //미용사 회원가입
        FPDesignerRepository.insertDesigners(designer);
    }

    public void insertDesignersPortfolioPath(Designerphoto designerphoto) {
        //포트폴리오 테이블에 사진 저장할려고 쓰는 designerphoto=id 값이랑 사진 값 그리고 jpa에서 쓰려면 무조건 필요한 pk값 들어있는 클래스임
        FPDesignerRepository.insertDesignersPortfolioPath(designerphoto);
    }

    public void deleteCustomer(String id) {
        //아이디 값으로 일반 회원 탈퇴
        FPDesignerRepository.deleteCustomer(id);
    }

    public void deleteDesiger(String id) {
        //아이디 값으로 미용사 탈퇴
        FPDesignerRepository.deleteDesigner(id);
    }

    public String findByNameEmailCustomer(String name, String email) {
        String id = FPDesignerRepository.findByNameEmailCustomer(name, email);
        return id;
    }

    public String findByNameEmailDesigner(String name, String email) {
        String id = FPDesignerRepository.findByNameEmailDesigner(name, email);
        return id;
    }

    public String findByNameEmailCustomerPwd(String name, String email, String id) {
        String pwd = FPDesignerRepository.findByNameEmailCustomerPwd(name, email, id);
        return pwd;
    }

    public String findByNameEmailDesignerPwd(String name, String email, String id) {
        String pwd = FPDesignerRepository.findByNameEmailDesignerPwd(name, email, id);
        return pwd;
    }

    public void updateDesigner(DesignerReWrite designerReWrite, String id) {
        FPDesignerRepository.updateDesigner(designerReWrite, id);
    }

    public DesignerReWrite findByidRewrite(String id) {
        DesignerReWrite d2 = FPDesignerRepository.findByidRewrite(id);
        return d2;
    }

    public void updateCustomer(CustomerReWrite customerReWrite, String id) {
        FPDesignerRepository.updateCustomer(customerReWrite, id);
    }

    public CustomerReWrite findByCustomerReWrite(String id) {
        CustomerReWrite c2 = FPDesignerRepository.findByCustomerReWrite(id);
        return c2;
    }

    public void dropPortfolio(Designerphoto designerPhoto) {
        FPDesignerRepository.dropPortfolio(designerPhoto);
    }

    public List<ShowOff> findByIdShowOff(String id) {
        List<ShowOff> showOff = FPDesignerRepository.findByIdShowOff(id);
        return showOff;
    }

    //디자이너 평균 별점
    //05.25(수빈): 추가함
    public double calcAvg(Designer designer) {
        double avg = 0.0;

        if (designer.getPoint() != null) {
            avg = (double) designer.getPoint() / designer.getPoint_count();
        }

        return avg;
    }

    //05.25(수빈): 추가함
    public String[] locateCities(String local) {
        String[] cities = null;

        switch (local) {
            case "서울특별시":
                cities = new String[]{"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서울특별시",
                        "서초구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"};
                break;
            case "부산광역시":
                cities = new String[]{"강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구"};
                break;
            case "대구광역시":
                cities = new String[]{"남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"};
                break;
            case "인천광역시":
                cities = new String[]{"강화군", "계양구", "남동구", "동구", "미추홀구", "부평구", "서구", "연수구", "옹진군", "중구"};
                break;
            case "광주광역시":
                cities = new String[]{"광산구", "광주광역시", "남구", "동구", "북구", "서구"};
                break;
            case "세종특별자치시":
                cities = new String[]{"세종특별자치시"};
                break;
            case "대전광역시":
                cities = new String[]{"대덕구", "동구", "서구", "유성구", "중구"};
                break;
            case "울산광역시":
                cities = new String[]{"남구", "동구", "북구", "울주군", "중구"};
                break;
            case "경기도":
                cities = new String[]{"가평군", "고양시", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시", "부천시", "성남시", "수원시", "시흥시", "안산시",
                        "안성시", "안양시", "양주시", "양평군", "여주시", "연천군", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시",
                        "화성시"};
                break;
            case "강원도":
                cities = new String[]{"강릉시", "고성군", "동해시", "삼척시", "속초시", "양구군", "양양군", "영월군", "원주시", "인제군", "정선군", "철원군", "춘천시", "태백시", "평창군",
                        "홍천군", "화천군", "횡성군"};
                break;
            case "충청북도":
                cities = new String[]{"괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "제천시", "증평군", "진천군", "청주시", "충주시"};
                break;
            case "충청남도":
                cities = new String[]{"계룡시", "공주시", "금산군", "논산시", "당진시", "보령시", "부여군", "서산시", "사천군", "아산시", "연기군", "예산군", "천안시", "청양군", "태안군",
                        "홍성군"};
                break;
            case "전라북도":
                cities = new String[]{"고창군", "군산시", "김제시", "남원시", "무주군", "부안군", "순창군", "완주군", "익산시", "임실군", "장수군", "전주시", "정읍시", "진안군"};
                break;
            case "전라남도":
                cities = new String[]{"강진군", "고흥군", "곡성군", "광양시", "구례군", "나주시", "담양군", "목포시", "무안군", "보성군", "순천시", "신안군", "여수시", "영관군", "영암군",
                        "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군"};
                break;
            case "경상북도":
                cities = new String[]{"경산시", "경상북도", "경주시", "고령군", "구미시", "군위군", "김천시", "문경시", "봉화군", "상주시", "성주군", "안동시", "영덕군", "영양군", "영주시",
                        "영천시", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군", "포항시"};
                break;
            case "경상남도":
                cities = new String[]{"거제시", "거창군", "고성군", "김해시", "남해군", "밀양시", "사천시", "산청군", "양산시", "의령군", "진주시", "창녕군", "창원 마산합포회원구", "창원 의창성산구",
                        "창원 진해구", "통영시", "하동군", "함안군", "함양군", "합천군"};
                break;
            case "제주특별자치도":
                cities = new String[]{"서귀포시", "제주시", "제주특별자치도"};
                break;
        }

        return cities;
    }


    //05.25(수빈): 추가함
    public List<DesignerData> listDesignerPage(String name, int v_page) {
        //검색 안하고 페이징 조회 v_page는 34번 라인에 설명 써있음
        List<Designer> designers = FPDesignerRepository.listDesignerPage(name, v_page);
        List<DesignerData>designerDataList = new ArrayList<>();
        for(Designer d : designers){
            String id = d.getId();
            List<Designerphoto> designerphotos = FPDesignerRepository.findByIdDesignerPhoto(id);
            List<Review> reviews = FPDesignerRepository.findByIdReviews(id);
            DesignerData designerData = new DesignerData(d,designerphotos,reviews);
            designerDataList.add(designerData);
        }

        return designerDataList;
    }

    //디자이너 소개소 페이징
    //05.25(수빈): 추가함
    public int DesignerPagingCount(String name) {
        int pageCount = findAllDesigner().size();

        if (name != null) {
            pageCount = findLikeNameDesigners(name).size();
        }

        return (pageCount + 9) / 10;
    }

    //사실 검색 조회의 기능은 위에랑 비슷한데 페이징 처리할 페이지 카운트 하는데 필요해서 그냥 하나 더 씀
    //05.25(수빈): 추가함
    public List<Designer>findLikeNameDesigners(String name){
        return FPDesignerRepository.findLikeNameDesigners(name);
    }

    public Boolean InsertReview(Review review) {
        Boolean One = FPDesignerRepository.InsertReview(review);
        Boolean two = false;
        Boolean three = false;
        if(One == true){
            two = FPDesignerRepository.InsertReviewDesigner(review);
            if(two == true){
                three = FPDesignerRepository.InsertReviewReservation(review);
            }
        }
        return three;
    }

    public List<Alarm> findByIdAlarm(String userId) {
        List<Alarm> alarmList = FPDesignerRepository.findByIdAlarm(userId);
        return alarmList;
    }

    public Boolean deleteThisAlarm(int seq) {
        Boolean result = FPDesignerRepository.deleteThisAlarm(seq);
        return result;
    }

    public Boolean findOffer(String id, int seq) {
        Boolean result = FPDesignerRepository.findOffer(id,seq);
        return result;
    }
    public List<Review> findByIdCReview(String id) {
        List<Review> reviews = FPDesignerRepository.findByIdCReviews(id);
        return reviews;
    }

    public List<Review> findByIdDReview(String id) {
        List<Review> reviews = FPDesignerRepository.findByIdReviews(id);
        return reviews;
    }

    public Boolean saveAiPhoto(Ai_result aiResult) {
        Boolean result = FPDesignerRepository.saveAiPhoto(aiResult);
        return result;
    }
}
