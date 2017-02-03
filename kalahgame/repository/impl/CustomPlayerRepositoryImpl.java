package ua.kalahgame.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ua.kalahgame.domain.Player;
import ua.kalahgame.repository.CustomPlayerRepository;
import ua.kalahgame.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by a.lomako on 2/2/2017.
 */
public class CustomPlayerRepositoryImpl implements CustomPlayerRepository {

    @Autowired
    PlayerRepository playerRepository;
    @Override
    public Optional<Player> getUser(String player_login) {
        List<Player> userList;
        userList = (List<Player>) playerRepository.findAll();
        return  userList.stream()
                .filter(player -> player.getPlayer_login().equals(player_login))
                .findFirst();
    }

}
