package ua.kalahgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kalahgame.domain.Game;

/**
 * Created by a.lomako on 2/16/2017.
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findOne(Long id);

}