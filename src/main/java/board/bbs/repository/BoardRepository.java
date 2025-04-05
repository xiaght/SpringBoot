package board.bbs.repository;

import board.bbs.entity.BoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {



/*    @Modifying
    @Query(value="update BoardEntity b set b.boardHits=b,boardHits+1 where b.id=:id" )
    void updateHits(@Param("id") Long id);*/

    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

}

