package FinalPrjTest.FinalPrj.domain;

import javax.persistence.*;

@Entity
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;
    @Column(nullable = false)
    private String id;
    @Column(nullable = false)
    private int request_seq;// 지금은 의뢰서 관한 중심으로 돌아가지만 자랑게시판도 알림을 할 경우엔 그냥 게시물 seq 오는 자리라 생각하면 편함
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private int type;//1은 제안서가 달렸단 알림 //2는 예약할때 //3은 당일이 예약일일때

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

    public int getRequest_seq() {
        return request_seq;
    }

    public void setRequest_seq(int request_seq) {
        this.request_seq = request_seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
