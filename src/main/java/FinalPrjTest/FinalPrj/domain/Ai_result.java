package FinalPrjTest.FinalPrj.domain;

import javax.persistence.*;
/*
   여기는 Ai합성 사진을 저장하겠다고 했을 때 들어갈 곳이다.
   합성에 필요한 재료사진은 c:/img/MakeAiPhoto에 들어갈 것이며
   모델이 합성한 결과는 c:/img/AiResult로 갈 것이다.
*/
@Entity
public class Ai_result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(nullable = false)
    private String id;//고객의 아이디

    @Column(nullable = false)
    private String ai_photo_path;//사진 이름이 들어갈 곳임

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

    public String getAi_photo_path() {
        return ai_photo_path;
    }

    public void setAi_photo_path(String ai_photo_path) {
        this.ai_photo_path = ai_photo_path;
    }
}
