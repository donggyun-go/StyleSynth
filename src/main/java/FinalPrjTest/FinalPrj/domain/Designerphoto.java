package FinalPrjTest.FinalPrj.domain;

import javax.persistence.*;

@Entity
public class Designerphoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;
    @Column(nullable = false)
    String id;
    @Column(nullable = false)
    String photo_path;

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

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
