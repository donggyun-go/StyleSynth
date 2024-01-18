package FinalPrjTest.FinalPrj.domain;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class OfferList {

    private int seq;


    private String id;


    private int is_cut;


    private int is_perm;


    private int is_dye;


    private int is_dry;


    private String detail;


    private String service;


    private String date;


    private String hour;


    private String minute;


    private int request_seq;

    private String photo_path;
    private String price;

    private String name;

    private Integer point_count;//널 허용

    private Float point;//널 허용

    private String review;

    private String address;

    private String detailed_address;
    private Float avg;

    public OfferList(int seq, String id, int is_cut, int is_perm, int is_dye, int is_dry, String detail, String service,
                     String date, String hour, String minute, int request_seq, String photo_path, String price, String name,
                     Integer point_count, Float point, String review, String address, String detailed_address) {
        this.seq = seq;
        this.id = id;
        this.is_cut = is_cut;
        this.is_perm = is_perm;
        this.is_dye = is_dye;
        this.is_dry = is_dry;
        this.detail = detail;
        this.service = service;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.request_seq = request_seq;
        this.photo_path = photo_path;
        this.price = price;
        this.name = name;
        this.point_count = point_count;
        this.point = point;
        this.review = review;
        this.address = address;
        this.detailed_address = detailed_address;
    }

    @Override
    public String toString() {
        return "OfferList{" +
                "seq=" + seq +
                ", id='" + id + '\'' +
                ", is_cut=" + is_cut +
                ", is_perm=" + is_perm +
                ", is_dye=" + is_dye +
                ", is_dry=" + is_dry +
                ", detail='" + detail + '\'' +
                ", service='" + service + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                ", request_seq=" + request_seq +
                ", photo_path='" + photo_path + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", point_count=" + point_count +
                ", point=" + point +
                ", review='" + review + '\'' +
                ", address='" + address + '\'' +
                ", detailed_address='" + detailed_address + '\'' +
                ", avg=" + avg +
                '}';
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getDesigner_id() {
        return id;
    }

    public void setDesigner_id(String designer_id) {
        this.id = designer_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public Float getAvg() {
        return avg;
    }

    public void setAvg(Float avg) {
        this.avg = avg;
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
}
