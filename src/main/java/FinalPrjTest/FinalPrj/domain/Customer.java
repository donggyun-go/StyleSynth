package FinalPrjTest.FinalPrj.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer {
    @Id
    @Column(nullable = false)//수정 못하는거
    private String id;
    @Column(nullable = false)
    private String pw;
    @Column(nullable = false)//수정 못하는거
    private String name;
    @Column(nullable = false)//수정 못하는 거
    private String birthday;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String gender;//수정 불가능
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone_num;
    @Column(nullable = false)
    private int hair_condition;
    @Column(nullable = false)
    private int hair_length;
    @Column(nullable = false)
    private String head_shape;
    @Column(nullable = false)
    private int dye_times;//염색 횟수
    @Column(nullable = false)
    private int perm_times;//파마 횟수
    @Column(columnDefinition = "int default 1")
    private Integer memberType = 1;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles= new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Set<String> getRoles() {
        return roles;
    }
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
