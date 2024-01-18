package FinalPrjTest.FinalPrj.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Review {
    @Id
    @Column(nullable = false)
    private int request_seq;
    @Column(nullable = false)
    private String customer_id;
    @Column(nullable = false)
    private String designer_id;

    @Column(nullable = false)
    private Float rating;

    @Column(nullable = false)
    private String content;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column
    private String photo_path;


    public int getRequest_seq() {
        return request_seq;
    }

    public void setRequest_seq(int request_seq) {
        this.request_seq = request_seq;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getDesigner_id() {
        return designer_id;
    }

    public void setDesigner_id(String designer_id) {
        this.designer_id = designer_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
