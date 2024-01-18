package FinalPrjTest.FinalPrj.repository;

import FinalPrjTest.FinalPrj.domain.*;

import javax.persistence.EntityManager;
import java.util.List;

public class OfferRepository implements FPOfferRepository{

    private final EntityManager em;

    public OfferRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Designer getDesigner(String designerID) {
        return em.createQuery("select d from Designer d where d.id=:id", Designer.class)
                .setParameter("id", designerID)
                .getSingleResult();
    }

    @Override
    public void submitOffer(Offer offer){
        em.persist(offer);
    }

    @Override
    public List<Designerphoto> getPortfolio(String designerID) {
        return em.createQuery("SELECT dp from Designerphoto dp where dp.id=:id", Designerphoto.class)
                .setParameter("id", designerID)
                .getResultList();
    }

    @Override
    public List<Review> getReviews(String designerID) {
        return em.createQuery("SELECT r from Review r where r.designer_id=:designer_id", Review.class)
                .setParameter("designer_id", designerID)
                .getResultList();
    }

    @Override
    public Request_table getRequest(int seq) {
        return em.createQuery("SELECT r from Request_table r where r.seq=:seq", Request_table.class)
                .setParameter("seq", seq)
                .getSingleResult();
    }

    public Offer findBySeq(int seq){
        Offer offer = em.find(Offer.class,seq);
        return offer;
    }

    public void insertAlarm(Alarm alarm){
        em.persist(alarm);
    }
}
