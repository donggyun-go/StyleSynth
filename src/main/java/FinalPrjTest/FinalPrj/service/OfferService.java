package FinalPrjTest.FinalPrj.service;

import FinalPrjTest.FinalPrj.domain.*;
import FinalPrjTest.FinalPrj.repository.FPOfferRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class OfferService {

    private final FPOfferRepository fpOfferRepository;

    public OfferService(FPOfferRepository fpOfferRepository) {
        this.fpOfferRepository = fpOfferRepository;
    }

    public static void updateReservationStatus(int seq) {
    }

    public Designer getDesigner(String designerID) {
        return fpOfferRepository.getDesigner(designerID);
    }

    public void submitOffer(Offer offer) {
        fpOfferRepository.submitOffer(offer);
    }

    public List<Designerphoto> getPortfolio(String designerID) {
        return fpOfferRepository.getPortfolio(designerID);
    }

    public List<Review> getReviews(String designerID) {
        return fpOfferRepository.getReviews(designerID);
    }

    public Request_table getRequest(int seq) {
        return fpOfferRepository.getRequest(seq);
    }

    public Offer findBySeq(int seq) {
        Offer offer = fpOfferRepository.findBySeq(seq);
        return offer;
    }

    public void insertAlarm(Alarm alarm) {
        fpOfferRepository.insertAlarm(alarm);
    }
}
