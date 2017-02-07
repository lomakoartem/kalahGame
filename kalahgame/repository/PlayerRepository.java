package ua.kalahgame.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kalahgame.domain.Player;
/**
 * Created by a.lomako on 1/30/2017.
 */
@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    @Query(nativeQuery = true, value =  "SELECT  u.player_id FROM Users u WHERE u.player_login = :player_login")
    Long getUserByLogin(@Param("player_login") String player_login);

    @Query(nativeQuery = true, value =  "SELECT  u.player_id FROM Users u WHERE u.player_email = :player_email")
    Long getUserByEmail(@Param("player_email") String player_email);
}
