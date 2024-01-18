package FinalPrjTest.FinalPrj.domain;

import java.util.Objects;

public class CustomerReWrite {
    private String pw;
    private String address;
    private String email;
    private String phone_num;
    private int hair_condition;
    private int hair_length;
    private String head_shape;
    private int dye_times;
    private int perm_times;

    public CustomerReWrite(String pw, String address, String email, String phone_num, int hair_condition, int hair_length, String head_shape, int dye_times, int perm_times) {
        this.pw = pw;
        this.address = address;
        this.email = email;
        this.phone_num = phone_num;
        this.hair_condition = hair_condition;
        this.hair_length = hair_length;
        this.head_shape = head_shape;
        this.dye_times = dye_times;
        this.perm_times = perm_times;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        CustomerReWrite other = (CustomerReWrite) obj;

        return Objects.equals(pw, other.pw)
                && Objects.equals(address, other.address)
                && Objects.equals(email, other.email)
                && Objects.equals(phone_num, other.phone_num)
                && hair_condition == other.hair_condition
                && hair_length == other.hair_length
                && Objects.equals(head_shape, other.head_shape)
                && dye_times == other.dye_times
                && perm_times == other.perm_times;
    }
}
