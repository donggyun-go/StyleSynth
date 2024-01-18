package FinalPrjTest.FinalPrj.domain;

public class RequestAndReservation {

    private Request_table requestTable;
    private Reservation reservation;

    public RequestAndReservation(Request_table requestTable, Reservation reservation) {
        this.requestTable = requestTable;
        if (reservation == null) {
            // 예약 정보가 없는 경우에 대한 처리
            this.reservation = null;
        } else {
            this.reservation = reservation;
        }
    }

    public Request_table getRequestTable() {
        return requestTable;
    }

    public void setRequestTable(Request_table requestTable) {
        this.requestTable = requestTable;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
