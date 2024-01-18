package FinalPrjTest.FinalPrj.utils;

import FinalPrjTest.FinalPrj.domain.Comment;
import FinalPrjTest.FinalPrj.domain.ShowOff;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentDTO {
    private int seq;
    private String commentWriter;
    private String commentContents;
    private int testSeq;
    private LocalDateTime createdDate;



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

    public String getCommentContents() {
        return commentContents;
    }

    public void setCommentContents(String commentContents) {
        this.commentContents = commentContents;
    }

    public int getTestSeq() {
        return testSeq;
    }

    public void setTestSeq(int testSeq) {
        this.testSeq = testSeq;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "seq=" + seq +
                ", commentWriter='" + commentWriter + '\'' +
                ", commentContents='" + commentContents + '\'' +
                ", testSeq=" + testSeq +
                ", createdDate=" + createdDate +
                '}';
    }

    /*@Override
    public String toString() {
        return "CommentDTO{" +
                "seq=" + seq +
                ", commentWriter='" + commentWriter + '\'' +
                ", commentContents='" + commentContents + '\'' +
                ", testSeq=" + testSeq +
                '}';
    }*/

    public CommentDTO() {}

    public static CommentDTO toCommentDTO(Comment comment, int testSeq) {  //엔티티를 dto로 변환하는 것.
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setSeq(comment.getSeq());
        commentDTO.setCommentWriter(comment.getCommentWriter());
        commentDTO.setCommentContents(comment.getCommentContents());
        commentDTO.setCreatedDate(comment.getCreatedDate().now());
        commentDTO.setTestSeq(testSeq);
        return commentDTO;
    }


}
