package FinalPrjTest.FinalPrj.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
 /*
    의뢰서 테이블
    아직 달력을 해결 못했다.
    아직 ai이미지 부분을 해결 못했다. 가정하고 테이블 까지 만들어 봤지만
    프론트로 이미지 보내주고 그 이미지를 선택했을 때
    서버로 그 이미지 이름값을 가지고 오는 방법을 아직 모른다.
  */

@Entity
public class Request_table {
    //@Column(nullable = false, length = 20) //이런식으로 글자 수 제한도 가능함

    //seg INT AUTO INCREMENT PRIMARY KEY
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    //작성자 id 세션에서 따와도 될듯
    @Column(nullable = false)
    private String Writer_id;

    //content varchar(200) NOT NULL
    @Column(nullable = false)
    private String content;

    //create_date varchar(100) NOT NULL
    @Column(nullable = false)
    private LocalDateTime create_date;

    //original_img_path varchar(50)
    @Column
    private String original_img_path;

    //al_img_path varchar(50)
    @Column
    private String al_img_path;

    //views int default 0
    @Column(nullable = false)
    private int views = 0;

    //available_date
    @Column//(nullable = false) 지금은 널허용 설정하고 나중에 달력 해결되면 바뀔수도 있고 여튼 지금은 null 허용
    private String available_date;

    //hair_condition int NOT NULL
    @Column(nullable = false)
    private int hair_condition;

    //hair length int NOT NULL
    @Column(nullable = false)
    private int hair_length;

    //head shape int NOT NULL
    @Column(nullable = false)
    private String head_shape;

    //dye_times int NOT NULL
    @Column(nullable = false)
    private int dye_times;

    //perm times int NOT NULL
    @Column(nullable = false)
    private int perm_times;

    //customer name varchar(20) NOT NULL
    @Column(nullable = false)
    private String customer_name;

    //address varchar(50) NOT NULL
    @Column(nullable = false)
    private String address;
    //email varchar(40) NOT NULL
    @Column(nullable = false)
    private String email;

    //phone_num varchar(13) NOT NULL
    @Column(nullable = false)
    private String phone_num;

    //gender String NOT NULL
    @Column(nullable = false)
    private String gender;

    //start hour int NOT NULL
    @Column//(nullable = false) 지금은 널허용 설정하고 나중에
    private String start_hour;

    //end_hour int NOT NULL
    @Column//(nullable = false) 지금은 널허용 설정하고 나중에
    private String end_hour;

    //start minute NOT NULL
    @Column//(nullable = false) 지금은 널허용 설정하고 나중에
    private String start_minute;

    //end minute NOT NULL
    @Column//(nullable = false) 지금은 널허용 설정하고 나중에
    private String end_minute;

    private String title;

    private int reservation = 0;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getWriter_id() {
        return Writer_id;
    }

    public void setWriter_id(String writer_id) {
        Writer_id = writer_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public String getOriginal_img_path() {
        return original_img_path;
    }

    public void setOriginal_img_path(String original_img_path) {
        this.original_img_path = original_img_path;
    }

    public String getAl_img_path() {
        return al_img_path;
    }

    public void setAl_img_path(String al_img_path) {
        this.al_img_path = al_img_path;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getAvailable_date() {
        return available_date;
    }

    public void setAvailable_date(String available_date) {
        this.available_date = available_date;
    }

    public int getHair_condition() {
        return hair_condition;
    }

    public void setHair_condition(int hair_condition) {
        this.hair_condition = hair_condition;
    }

    public int getHair_length() {
        return hair_length;
    }

    public void setHair_length(int hair_length) {
        this.hair_length = hair_length;
    }

    public String getHead_shape() {
        return head_shape;
    }

    public void setHead_shape(String head_shape) {
        this.head_shape = head_shape;
    }

    public int getDye_times() {
        return dye_times;
    }

    public void setDye_times(int dye_times) {
        this.dye_times = dye_times;
    }

    public int getPerm_times() {
        return perm_times;
    }

    public void setPerm_times(int perm_times) {
        this.perm_times = perm_times;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(String end_hour) {
        this.end_hour = end_hour;
    }

    public String getStart_minute() {
        return start_minute;
    }

    public void setStart_minute(String start_minute) {
        this.start_minute = start_minute;
    }

    public String getEnd_minute() {
        return end_minute;
    }

    public void setEnd_minute(String end_minute) {
        this.end_minute = end_minute;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReservation() {
        return reservation;
    }

    public void setReservation(int reservation) {
        this.reservation = reservation;
    }
}
