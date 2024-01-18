package FinalPrjTest.FinalPrj.repository;

import FinalPrjTest.FinalPrj.domain.Comment;
import FinalPrjTest.FinalPrj.domain.ShowOff;

import java.util.List;
import java.util.Optional;

public interface FPCommentRepository {
    List<Comment> findAllByShowOffOrderBySeqDesc(ShowOff showOff);
    Comment save(Comment comment);
    Optional<ShowOff> findBySeq(int testSeq);

    void newsave(Comment comment);

    Comment findBySeqComment(int seq);
    List<Comment> findBySeqCommentL(int seq, int showOffId);

    void delete(Comment comment);

    void deleteBySeq(int seq);

    void updateBySeq(int seq, String editedContent);
}