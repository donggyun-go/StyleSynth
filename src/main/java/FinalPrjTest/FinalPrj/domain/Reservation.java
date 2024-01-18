package FinalPrjTest.FinalPrj.domain;

import java.util.Date;

/*
    시간은 시간대로 쓰고 보여줄때는 포멧한 형태로 보여줄려고 만든 곳
*/
public class Reservation {
    private Reservation_date reservationDate;

    private String date;

    public Reservation(Reservation_date reservationDate, String date) {
        this.reservationDate = reservationDate;
        this.date = date;
    }

    public Reservation_date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Reservation_date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
