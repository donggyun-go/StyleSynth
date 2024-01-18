package FinalPrjTest.FinalPrj.domain;

import java.util.List;

public class MyPageData {
    private List<RequestAndReservation> requestAndReservation;
    private List<ShowOff> showOffs;

    public MyPageData(List<RequestAndReservation> requestAndReservation, List<ShowOff> showOffs) {
        this.requestAndReservation = requestAndReservation;
        this.showOffs = showOffs;
    }

    public List<RequestAndReservation> getRequestAndReservation() {
        return requestAndReservation;
    }

    public void setRequestAndReservation(List<RequestAndReservation> requestAndReservation) {
        this.requestAndReservation = requestAndReservation;
    }

    public List<ShowOff> getShowOffs() {
        return showOffs;
    }

    public void setShowOffs(List<ShowOff> showOffs) {
        this.showOffs = showOffs;
    }
}