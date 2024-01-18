package FinalPrjTest.FinalPrj.repository;

import FinalPrjTest.FinalPrj.domain.Comment;
import FinalPrjTest.FinalPrj.domain.ShowOff;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


public class CommentRepository implements FPCommentRepository {
    private EntityManager em;

    public CommentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Comment> findAllByShowOffOrderBySeqDesc(ShowOff showOff) {
        return em.createQuery("SELECT c FROM Comment c WHERE c.showOff = :showOff ORDER BY c.seq DESC", Comment.class)
                .setParameter("showOff", showOff)
                .getResultList();
    }

    /*@Override
    public List<Comment> findAllByShowOffOrderBySeqDesc(ShowOff showOff) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
        Root<Comment> root = query.from(Comment.class);
        query.select(root)
                .where(cb.equal(root.get("showOff"), showOff))
                .orderBy(cb.desc(root.get("seq")));
        return em.createQuery(query).getResultList();
    }*/

    @Override
    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }
    @Override
    public Optional<ShowOff> findBySeq(int testSeq) {
        System.out.println("testSeq = "+ testSeq);
        return Optional.ofNullable(em.find(ShowOff.class, testSeq));
    }

    public  void newsave(Comment comment){
        em.persist(comment);
    }

    @Override
    public Comment findBySeqComment(int seq) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.seq=:seq", Comment.class)
                .setParameter("seq", seq);
        try {
            Comment seqcomment = query.getSingleResult();
            return seqcomment;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Comment> findBySeqCommentL(int seq, int showOffId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.seq=:seq and c.showOffId=:showOffId", Comment.class)
                .setParameter("seq", seq)
                .setParameter("showOffId", showOffId);
        List<Comment> seqcomments = query.getResultList();
        return seqcomments;
    }



    @Override
    public void delete(Comment comment) {
        em.createQuery("DELETE FROM Comment c WHERE c.seq = :seq")
                .setParameter("seq", comment.getSeq())
                .executeUpdate();
    }
    /*@Override
    public void delete(Comment comment) {
        em.remove(comment);
    }*/
    public void deleteBySeq(int seq){
        Comment comment =em.find(Comment.class,seq);
        em.remove(comment);
    }


    public void updateBySeq(int seq, String editedContent) {
        em.createQuery("update Comment c set c.commentContents = :content where c.seq = :commentId")
                .setParameter("content", editedContent)
                .setParameter("commentId", seq)
                .executeUpdate();
    }


}