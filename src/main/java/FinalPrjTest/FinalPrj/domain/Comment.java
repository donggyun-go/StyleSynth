package FinalPrjTest.FinalPrj.domain;

import FinalPrjTest.FinalPrj.utils.CommentDTO;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;



@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;  //댓글 개수!!!

    @Column(length = 20, columnDefinition = "VARCHAR(20) default ''")
    private String commentWriter;


    @Column(length = 20, columnDefinition = "VARCHAR(20) default ''")
    private String commentName;

    @Column
    private String commentContents;

    @CreatedDate
    @Column(updatable = false)  //여기이름바꿔
    private LocalDateTime createdDate;

    private int sseq;


    //    @ManyToOne(fetch = FetchType.LAZY)  //게시글 seq번호임!!!
//    @JoinColumn(name = "showOff_id")  //"showOff_id"는 showOff테이블의 seq를 참조함.
//    private ShowOff showOff;  //ShowOff클래스의 자식임을 나타냄.



    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getCommentWriter() {
        return commentWriter;
    }

    public void setCommentWriter(String commentWriter) {
        this.commentWriter = commentWriter;
    }
    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }
    public String getCommentContents() {
        return commentContents;
    }

    public void setCommentContents(String commentContents) {
        this.commentContents = commentContents;
    }

    public int getSseq() {
        return sseq;
    }

    public void setSseq(int sseq) {
        this.sseq = sseq;
    }
/*    public ShowOff getShowOff() {
        return showOff;
    }

    public void setShowOff(ShowOff showOff) {
        this.showOff = showOff;
    }*/

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

//    public static Comment toSaveEntity(CommentDTO commentDTO, ShowOff showOff){
//        Comment comment = new Comment();
//        comment.setCommentWriter(commentDTO.getCommentWriter());
//        comment.setCommentContents(commentDTO.getCommentContents());
//        comment.setCreatedDate(LocalDateTime.now());
//        comment.setShowOff(showOff);
//        return comment;
//    }
}