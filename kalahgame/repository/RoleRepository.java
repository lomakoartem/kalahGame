package ua.kalahgame.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kalahgame.domain.Player;
import ua.kalahgame.domain.Role;

/**
 * Created by a.lomako on 2/1/2017.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
