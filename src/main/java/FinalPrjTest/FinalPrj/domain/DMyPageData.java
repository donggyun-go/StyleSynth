package FinalPrjTest.FinalPrj.domain;

import java.util.List;

public class DMyPageData {
    private List<Offer> offers;
    private List<ShowOff> showOffs;

    public DMyPageData(List<Offer> offers, List<ShowOff> showOffs) {
        this.offers = offers;
        this.showOffs = showOffs;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<ShowOff> getShowOffs() {
        return showOffs;
    }

    public void setShowOffs(List<ShowOff> showOffs) {
        this.showOffs = showOffs;
    }
}
