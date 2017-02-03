package ua.kalahgame.service;

import ua.kalahgame.domain.Player;

import java.util.Optional;

/**
 * Created by a.lomako on 1/30/2017.
 */

public interface PlayerService {

    Player findPlayerById(Long id);

    Optional<Player> getUser(String player_login);

    Player addPlayer(Player player);

    Iterable<Player> findAll();

    boolean generatePasswordAndSendByMail(Long id);

    char[] generatePasswordAndSaveInDatabase(Long id);

    boolean sendPasswordByMail(Long id, char[] password);

}
