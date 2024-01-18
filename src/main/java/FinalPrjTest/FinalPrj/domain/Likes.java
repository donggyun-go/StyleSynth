package FinalPrjTest.FinalPrj.domain;

import javax.persistence.*;

@Entity
public class Likes {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int likeCount;  //좋아요 개수

        private String user;  //좋아요를 누른 사용자

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "showOff_seq")
        private ShowOff showOff;  //좋아요 대상 게시물(게시물판별id는 seq임.)
        //@ManyToOne(fetch = FetchType.LAZY)
        //@JoinColumn(name = "Designer_id")




        public int getLikeCount() {
                return likeCount;
        }

        public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
        }

        public String getUser() {
                return user;
        }

        public void setUser(String user) {
                this.user = user;
        }

        public ShowOff getShowOff() {
                return showOff;
        }

        public void setShowOff(ShowOff showOff) {
                this.showOff = showOff;
        }
}

