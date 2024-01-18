package FinalPrjTest.FinalPrj.domain;

import java.util.Objects;

public class DesignerReWrite {
    private String pw;
    private String email;
    private String phone_num;
    private String photo_path;
    private String career;
    private String address;
    private String detailed_address;
    private String certificate_path2;//널 허용// 미 입력 시 수정 하번만 가능
    private String certificate_path3;
    private String portfolio_link;
    private String self_about; //널 허용

    public DesignerReWrite(String pw, String email, String phone_num, String photo_path, String career, String address, String detailed_address, String certificate_path2, String certificate_path3, String portfolio_link, String self_about) {
        this.pw = pw;
        this.email = email;
        this.phone_num = phone_num;
        this.photo_path = photo_path;
        this.career = career;
        this.address = address;
        this.detailed_address = detailed_address;
        this.certificate_path2 = certificate_path2;
        this.certificate_path3 = certificate_path3;
        this.portfolio_link = portfolio_link;
        this.self_about = self_about;
    }

    public String getPortfolio_link() {
        return portfolio_link;
    }

    public void setPortfolio_link(String portfolio_link) {
        this.portfolio_link = portfolio_link;
    }
//널 허용// 미 입력 시 수정 하번만 가능

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
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




    @Override//클래스 객체들의 필드에 저장된 값으로 비교
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        DesignerReWrite other = (DesignerReWrite) obj;

        return Objects.equals(pw, other.pw)
                && Objects.equals(email, other.email)
                && Objects.equals(phone_num, other.phone_num)
                && Objects.equals(photo_path, other.photo_path)
                && Objects.equals(career, other.career)
                && Objects.equals(address, other.address)
                && Objects.equals(detailed_address, other.detailed_address)
                && Objects.equals(certificate_path2, other.certificate_path2)
                && Objects.equals(certificate_path3, other.certificate_path3)
                && Objects.equals(portfolio_link, other.portfolio_link)
                && Objects.equals(self_about, other.self_about);
    }


}
