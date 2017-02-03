package ua.kalahgame.repository;

import ua.kalahgame.domain.Player;

import java.util.Optional;

/**
 * Created by a.lomako on 2/2/2017.
 */
public interface CustomPlayerRepository {
    Optional<Player> getUser(String player_login);
}
