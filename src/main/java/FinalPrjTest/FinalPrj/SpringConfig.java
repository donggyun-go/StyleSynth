package FinalPrjTest.FinalPrj;

import FinalPrjTest.FinalPrj.repository.*;
import FinalPrjTest.FinalPrj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em){
        this.em=em;
    }

    @Bean
    public DesignerService finalProjectService(){
        return new DesignerService(finalProjectRepository());
    }

    @Bean
    public FPDesignerRepository finalProjectRepository(){
        return new DesignerRepository(em);

    }

    @Bean
    public ShowOffService finalShowOffService(){
        return new ShowOffService(finalShowOffRepository());
    }

    @Bean
    public FPShowOffRepository finalShowOffRepository(){
        return new ShowOffRepository(em);
    }

    @Bean
    public ReservationService reservationService(){
        return new ReservationService(fPreservationRepository());

    }
    @Bean
    public FPReservationRepository fPreservationRepository(){
        return new ReservationRepository(em);
    }

    @Bean
    public OfferService finalOfferService(){
        return new OfferService(finalOfferRepository());
    }

    @Bean
    public FPOfferRepository finalOfferRepository(){
        return new OfferRepository(em);

    }
    @Bean
    public CommentService finalCommentService(){
        return new CommentService(finalCommentRepository());
    }
    @Bean
    public FPCommentRepository finalCommentRepository(){
        return new CommentRepository(em);
    }



}

