package ua.kalahgame.service;

import org.springframework.stereotype.Service;
import ua.kalahgame.domain.Game;
import ua.kalahgame.domain.Player;

/**
 * Created by a.lomako on 2/16/2017.
 */
@Service
public interface GameService {
    Game findOne(long id);

    Game makeGame(String nameActing, String nameOpposite);

    Game updateGame(long id, int i);

    Player createNewActingPlayer(String name);

    Player createNewOppositePlayer(String name);

    boolean makeMove(long gameId, int number);

    boolean checkIfEndGame(Player actingPlayer, Player oppositePlayer);

    int checkIfFirstIsTheWinner(Player first, Player second);

    boolean makeMoveEndActivePit(int number, Player acting, Player opposite, int times, int initial, long gameId);

    boolean makeMoveEndActiveKalah(int number, Player acting, Player opposite, int times, int initial, long gameId);

    boolean makeMoveEndOppositePit(int number, Player acting, Player opposite, int times, int initial, long gameId);

    boolean makeMoveEndActingPitNotFullRound(int number, Player acting, Player opposite, int times, int initial, long gameId);

    int makeFullMove(int number, Player acting, Player opposite, int times);

    int getStonesCountInPits(Player player);
}