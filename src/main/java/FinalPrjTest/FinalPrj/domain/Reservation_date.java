package FinalPrjTest.FinalPrj.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Reservation_date {

    //의뢰서 seq 같은 의뢰서에 대해서 또 예약을 할려고 할 때 이걸 이용해서 하면 될듯함?
    @Id
    private int request_seq;

    //제안서 seq 나중에 필요 할까봐 주석만
//    @Column(nullable = false)
//    private int offerSeq;

    @Column(nullable = false)
    private String customer_id;

    @Column(nullable = false)
    private String designer_id;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date time_date;

    @Column(columnDefinition = "int default 0")
    private int review = 0;

    @Column(columnDefinition = "int default 0")
    private int alarm=0;

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

    public Date getTime_date() {
        return time_date;
    }

    public void setTime_date(Date time_date) {
        this.time_date = time_date;
    }

    public int getRequest_seq() {
        return request_seq;
    }

    public void setRequest_seq(int request_seq) {
        this.request_seq = request_seq;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }
}
