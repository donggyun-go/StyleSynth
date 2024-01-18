package FinalPrjTest.FinalPrj.repository;

import FinalPrjTest.FinalPrj.domain.*;

import java.util.List;

public interface FPOfferRepository {
    Designer getDesigner(String designerID);

    void submitOffer(Offer offer);

    List<Designerphoto> getPortfolio(String designerID);

    List<Review> getReviews(String designerID);

    Request_table getRequest(int seq);

    Offer findBySeq(int seq);

    void insertAlarm(Alarm alarm);
}
