package FinalPrjTest.FinalPrj.domain;

import javax.persistence.*;

@Entity
public class ReservationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;
    @Column(nullable=false, length = 20)
    private String designer_name;
    @Column(nullable=false, length = 20)
    private String customer_id;
    @Column(nullable=false, length = 20)
    private String getDesigner_id;
    @Column(nullable=false, length = 13)
    private String phone_num;
    @Column(nullable=false, length = 50)
    private String photo_path;
    @Column(nullable=false, length = 50)
    private String address;
    @Column(nullable=false, length = 200)
    private String content1;
    @Column(nullable=false, length = 200)
    private String Content2;
    @Column(nullable=false, length = 200)
    private String Content3;
    @Column(nullable=false, length = 20)
    private String hour;
    @Column(nullable=false, length = 20)
    private String minute;
    private int request_table_seq;
    @Column(nullable=false, length = 10)
    private String price;


}
