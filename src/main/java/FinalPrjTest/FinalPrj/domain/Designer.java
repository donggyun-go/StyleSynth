package FinalPrjTest.FinalPrj.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Designer {
    @Id
    @Column(nullable = false)//수정 못하는거
    private String id;
    @Column(nullable = false)//수정 못하는거
    private String name;
    @Column(nullable = false)
    private String pw;
    @Column(nullable = false)//수정 못하는거
    private String birthday;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone_num;
    @Column(nullable = false)
    private String portfolio_link;//널 허용
    @Column(nullable = false)
    private String photo_path;
    @Column(nullable = false)
    private String career;
    @Column(nullable = false)
    private String certificate_path1;//수정 불가능
    @Column(nullable = false)
    private String gender;//수정 불가능
    private String certificate_path2;//널 허용// 미 입력 시 수정 하번만 가능
    private String certificate_path3;//널 허용// 미 입력 시 수정 하번만 가능
    @Column(columnDefinition = "int default 2")
    private Integer memberType = 2;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String detailed_address;
    private String self_about;//널 허용
    private Integer point_count=0;//널 허용
    private Float point=0.0f;//널 허용
    private String review;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles= new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getPortfolio_link() {
        return portfolio_link;
    }

    public void setPortfolio_link(String portfolio_link) {
        this.portfolio_link = portfolio_link;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getCertificate_path1() {
        return certificate_path1;
    }

    public void setCertificate_path1(String certificate_path1) {
        this.certificate_path1 = certificate_path1;
    }

    public String getCertificate_path2() {
        return certificate_path2;
    }

    public void setCertificate_path2(String certificate_path2) {
        this.certificate_path2 = certificate_path2;
    }

    public String getCertificate_path3() {
        return certificate_path3;
    }

    public void setCertificate_path3(String certificate_path3) {
        this.certificate_path3 = certificate_path3;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailed_address() {
        return detailed_address;
    }

    public void setDetailed_address(String detailed_address) {
        this.detailed_address = detailed_address;
    }

    public String getSelf_about() {
        return self_about;
    }

    public void setSelf_about(String self_about) {
        this.self_about = self_about;
    }

    public Integer getPoint_count() {
        return point_count;
    }

    public void setPoint_count(Integer point_count) {
        this.point_count = point_count;
    }

    public Float getPoint() {
        return point;
    }

    public void setPoint(Float point) {
        this.point = point;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
