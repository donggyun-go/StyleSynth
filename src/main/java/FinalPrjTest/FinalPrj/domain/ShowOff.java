package FinalPrjTest.FinalPrj.domain;

import org.aspectj.weaver.ast.Test;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ShowOff{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;
    @Column(nullable = false)
    private String id;
    @Column
    private String name;
    @Column
    private int dtype;  //dtype에 membertype을 가지고 와야한다... 어떻게 하지?

    public int getDtype() {
        return dtype;
    }

    public void setDtype(int dtype) {
        this.dtype = dtype;
    }

    @Column(nullable=false)
    private String title;
    @Column(nullable=false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column
    private String img1_path;

    @Column
    private String img2_path;

    @Column(columnDefinition= "int default 0")
    private int views;

//    @OneToMany(mappedBy = "showOff", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Comment> commentList = new ArrayList<>();  //부모가 삭제되면 자식도 삭제됨.




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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getImg1_path() {
        return img1_path;
    }

    public void setImg1_path(String img1_path) {
        this.img1_path = img1_path;
    }

    public String getImg2_path() {
        return img2_path;
    }

    public void setImg2_path(String img2_path) {
        this.img2_path = img2_path;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }




}