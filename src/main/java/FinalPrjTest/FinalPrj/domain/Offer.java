package FinalPrjTest.FinalPrj.domain;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(nullable = false)
    private String id;

    @ColumnDefault("0")
    private int is_cut;

    @ColumnDefault("0")
    private int is_perm;

    @ColumnDefault("0")
    private int is_dye;

    @ColumnDefault("0")
    private int is_dry;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private String service;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String hour;

    @Column(nullable = false)
    private String minute;

    @Column(nullable = false)
    private int request_seq;

    @Column(nullable = false)
    private String price;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIs_cut() {
        return is_cut;
    }

    public void setIs_cut(int is_cut) {
        this.is_cut = is_cut;
    }

    public int getIs_perm() {
        return is_perm;
    }

    public void setIs_perm(int is_perm) {
        this.is_perm = is_perm;
    }

    public int getIs_dye() {
        return is_dye;
    }

    public void setIs_dye(int is_dye) {
        this.is_dye = is_dye;
    }

    public int getIs_dry() {
        return is_dry;
    }

    public void setIs_dry(int is_dry) {
        this.is_dry = is_dry;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public int getRequest_seq() {
        return request_seq;
    }

    public void setRequest_seq(int request_seq) {
        this.request_seq = request_seq;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
//        @Column(nullable = false)
    //        private String customer_id;
}
