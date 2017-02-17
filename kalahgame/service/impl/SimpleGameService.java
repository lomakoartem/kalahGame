package ua.kalahgame.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kalahgame.domain.Game;
import ua.kalahgame.domain.Player;
import ua.kalahgame.repository.GameRepository;
import ua.kalahgame.service.GameService;
import ua.kalahgame.service.PlayerService;

/**
 * Created by a.lomako on 2/16/2017.
 */
@Service
public class SimpleGameService implements GameService {

    public SimpleGameService() {

    }

    @Autowired
    private GameRepository gameRepository;


    @Autowired

    private PlayerService playerService;
    public Game findOne(long id) {

        return gameRepository.findOne(id);

    }


    @Transactional
    public Game makeGame(String nameActing, String nameOpposite) {
        Game game = new Game();

        playerService.updatePlayer(createNewActingPlayer(nameActing));
        playerService.updatePlayer(createNewOppositePlayer(nameOpposite));
        game.setInitialFirstPlayer(playerService.findPlayerById(playerService.getUserByLogin(nameActing)));
        game.setInitialSecondPlayer(playerService.findPlayerById(playerService.getUserByLogin(nameOpposite)));
        game.setAsFirst(true);
        gameRepository.saveAndFlush(game);
        return game;
    }
    @Transactional
    public Game updateGame(long id, int i) {
        Game thisGame = gameRepository.findOne(id);
        thisGame.setNumberOfPitForLastMove(i);
        return thisGame;
    }

    @Transactional
    public Player createNewActingPlayer(String name) {
        Player newPlayer = new Player();
        newPlayer.setPlayer_first_name(name);
        newPlayer.setKalahForPlayer(0);
        int[] pits = new int[]{6, 6, 6, 6, 6, 6};
        newPlayer.setPitsForPlayer(pits);
        newPlayer.setInTurn(true);
        return newPlayer;
    }

    @Transactional
    public Player createNewOppositePlayer(String name) {
        Player newPlayer = new Player();
        newPlayer.setPlayer_first_name(name);
        newPlayer.setKalahForPlayer(0);
        int[] pits = new int[]{6, 6, 6, 6, 6, 6};
        newPlayer.setPitsForPlayer(pits);
        newPlayer.setInTurn(false);
        return newPlayer;
    }

    public boolean makeMove(long gameId, int number) {
        if (number == 0 || number > 6) {
            return false;
        }
        final Player first = findOne(gameId).getInitialFirstPlayer();
        final Player second = findOne(gameId).getInitialSecondPlayer();
        Player acting;
        Player opposite;
        if (first.isInTurn()) {
            acting = first;
            opposite = second;
        } else if (second.isInTurn()) {
            acting = second;
            opposite = first;
        } else {

            first.setInTurn(true);
            second.setInTurn(false);
            acting = first;
            opposite = second;
        }
        int[] pitsForActing = acting.getPitsForPlayer();
        int[] pitsForOpposite = opposite.getPitsForPlayer();
        int selected = number - 1;
        int amountOfStonesForTurn = pitsForActing[selected];
        /* Check if selected pit is empty

         */
        if (amountOfStonesForTurn == 0) {

            return false;
        }

        /* Check if will finish in one's pit before Kalah in first round

         */
        if (amountOfStonesForTurn <= (6 - number)) {

            return makeMoveEndActivePit(number, acting, opposite, 0, 0, gameId);
        }

        /* Check if will finish in one's Kalah

         */
        if (amountOfStonesForTurn == (6 - number + 1)) {

            return makeMoveEndActiveKalah(number, acting, opposite, 0, 0, gameId);
        }

        /* Check if will finish in opposite pit in first round

         */

        if (amountOfStonesForTurn <= (6 - number + 1 + 6)) {
            return makeMoveEndOppositePit(number, acting, opposite, 0, 0, gameId);
        }

        /* Check if will make less than one full round

         */

        if (amountOfStonesForTurn <= 12) {
            return makeMoveEndActingPitNotFullRound(number, acting, opposite, 0, 0, gameId);
        }

        /* If will make full round several times

         */

        else {
            int left = amountOfStonesForTurn % 13;
            int times = amountOfStonesForTurn / 13;
            if (left == 0) {
                makeFullMove(number, acting, opposite, times);
                if (times == 1) {

                    if (pitsForOpposite[5 - selected] != 0) {
                        acting.setKalahForPlayer(acting.getKalahForPlayer() + 1 + pitsForOpposite[5 - selected]);
                        pitsForOpposite[5 - selected] = 0;
                        pitsForActing[selected] = 0;

                    }
                }

                if (checkIfEndGame(acting, opposite)) {
                    if (checkIfFirstIsTheWinner(acting, opposite) == 1) {
                        findOne(gameId).setWinner(acting.getPlayer_first_name());
                    } else if (checkIfFirstIsTheWinner(acting, opposite) == -1) {
                        findOne(gameId).setWinner(opposite.getPlayer_first_name());
                    } else {
                        findOne(gameId).setWinner("none");
                    }
                    return true;
                } else {
                    acting.setInTurn(false);
                    opposite.setInTurn(true);
                    return false;
                }
            }
            if (left <= (6 - number)) {

                int initial = makeFullMove(number, acting, opposite, times);
                return makeMoveEndActivePit(number, acting, opposite, times, initial, gameId);
            }
            if (left == (6 - number + 1)) {
                int initial = makeFullMove(number, acting, opposite, times);
                return makeMoveEndActiveKalah(number, acting, opposite, times, initial, gameId);
            }
            if (left <= (6 - number + 1 + 6)) {
                int initial = makeFullMove(number, acting, opposite, times);
                return makeMoveEndOppositePit(number, acting, opposite, times, initial, gameId);

            } else {
                int initial = makeFullMove(number, acting, opposite, times);
                return makeMoveEndActingPitNotFullRound(number, acting, opposite, times, initial, gameId);

            }
        }
    }
    /* Check if end of the game

     */


    public boolean checkIfEndGame(Player actingPlayer, Player oppositePlayer) {
        if (actingPlayer.getKalahForPlayer() > 36) {
            return true;
        }
        if (getStonesCountInPits(oppositePlayer) == 0 || getStonesCountInPits(actingPlayer) == 0) {
            actingPlayer.setKalahForPlayer(actingPlayer.getKalahForPlayer() + getStonesCountInPits(actingPlayer));
            oppositePlayer.setKalahForPlayer(oppositePlayer.getKalahForPlayer() + getStonesCountInPits(oppositePlayer));
            return true;
        } else {
            return false;
        }
    }


    public int checkIfFirstIsTheWinner(Player first, Player second) {
        if (first.getKalahForPlayer() > second.getKalahForPlayer()) {
            return 1;
        } else if (first.getKalahForPlayer() < second.getKalahForPlayer()) {
            return -1;
        } else {
            return 0;
        }
    }

    public int getStonesCountInPits(Player player) {
        int i = 0;
        for (int pit : player.getPitsForPlayer()) {
            i += pit;
        }
        return i;
    }
    public boolean makeMoveEndActivePit(int number, Player acting, Player opposite, int times, int initial, long gameId) {
        int[] pitsForActing = acting.getPitsForPlayer();
        int[] pitsForOpposite = opposite.getPitsForPlayer();
        int amountOfStonesForTurn;
        int selected = number - 1;
        if (initial == 0) {
            amountOfStonesForTurn = pitsForActing[selected];
            pitsForActing[selected] = times;
            for (int i = amountOfStonesForTurn, j = 0; i > 0; i--, j++) {
                pitsForActing[number + j] = pitsForActing[number + j] + 1;
            }
        } else {
            amountOfStonesForTurn = (initial - 13 * times);
            pitsForActing[selected] = times;
            for (int i = amountOfStonesForTurn, j = 0; i > 0; i--, j++) {
                pitsForActing[number + j] = pitsForActing[number + j] + 1;
            }
        }
        if (pitsForActing[number + amountOfStonesForTurn - 1] == 1 && pitsForOpposite[5 - (number + amountOfStonesForTurn - 1)] != 0) {
            acting.setKalahForPlayer(acting.getKalahForPlayer() + (1 + pitsForOpposite[5 - (number + amountOfStonesForTurn - 1)]));
            pitsForOpposite[5 - (number + amountOfStonesForTurn - 1)] = 0;
            pitsForActing[number + amountOfStonesForTurn - 1] = 0;
        }
        if (checkIfEndGame(acting, opposite)) {
            acting.setPitsForPlayer(pitsForActing);
            opposite.setPitsForPlayer(pitsForOpposite);
            if (checkIfFirstIsTheWinner(acting, opposite) == 1) {
                findOne(gameId).setWinner(acting.getPlayer_first_name());
            } else if (checkIfFirstIsTheWinner(acting, opposite) == -1) {
                findOne(gameId).setWinner(opposite.getPlayer_first_name());
            } else {
                findOne(gameId).setWinner("none");
            }
            return true;
        } else {
            acting.setPitsForPlayer(pitsForActing);
            opposite.setPitsForPlayer(pitsForOpposite);
            acting.setInTurn(false);
            opposite.setInTurn(true);
            return false;
        }
    }


    public boolean makeMoveEndActiveKalah(int number, Player acting, Player opposite, int times, int initial, long gameId) {
        int[] pitsForActing = acting.getPitsForPlayer();
        int selected = number - 1;
        int amountOfStonesForTurn;
        if (initial == 0) {
            amountOfStonesForTurn = pitsForActing[selected];
            pitsForActing[selected] = times;
            if (amountOfStonesForTurn != 1) {
                for (int i = (amountOfStonesForTurn - 1), j = 0; i > 0; i--, j++) {
                    pitsForActing[number + j] = pitsForActing[number + j] + 1;
                }
            }
        } else {
            amountOfStonesForTurn = initial - 13 * times;
            pitsForActing[selected] = times;
            if (amountOfStonesForTurn != 1) {
                for (int i = (amountOfStonesForTurn - 1), j = 0; i > 0; i--, j++) {
                    pitsForActing[number + j] = pitsForActing[number + j] + 1;
                }
            }
        }
        acting.setKalahForPlayer(acting.getKalahForPlayer() + 1);
        if (checkIfEndGame(acting, opposite)) {
            acting.setPitsForPlayer(pitsForActing);
            if (checkIfFirstIsTheWinner(acting, opposite) == 1) {
                findOne(gameId).setWinner(acting.getPlayer_first_name());
            } else if (checkIfFirstIsTheWinner(acting, opposite) == -1) {
                findOne(gameId).setWinner(opposite.getPlayer_first_name());
            } else {
                findOne(gameId).setWinner("none");
            }
            return true;
        } else {
            acting.setPitsForPlayer(pitsForActing);
            return false;
        }
    }
    public boolean makeMoveEndOppositePit(int number, Player acting, Player opposite, int times, int initial, long gameId) {
        int[] pitsForActing = acting.getPitsForPlayer();
        int[] pitsForOpposite = opposite.getPitsForPlayer();
        int selected = number - 1;
        int amountOfStonesForTurn;
        if (initial == 0) {
            amountOfStonesForTurn = pitsForActing[selected];
            pitsForActing[selected] = times;
            int usedForActing = 0;
            for (int i = number, j = 1; i < 6; i++, j++) {
                pitsForActing[i] += 1;
                usedForActing = j;
            }
            acting.setKalahForPlayer(acting.getKalahForPlayer() + 1);
            usedForActing += 1;
           for (int i = (amountOfStonesForTurn - usedForActing), j = 0; i > 0; i--, j++) {
                pitsForOpposite[j] += 1;
            }
        } else {
            amountOfStonesForTurn = initial - 13 * times;
            pitsForActing[selected] = times;
            int usedForActing = 0;
            for (int i = number, j = 1; i < 6; i++, j++) {
                pitsForActing[i] += 1;
                usedForActing = j;
            }
            acting.setKalahForPlayer(acting.getKalahForPlayer() + 1);
            usedForActing += 1;
            for (int i = (amountOfStonesForTurn - usedForActing), j = 0; i > 0; i--, j++) {
                pitsForOpposite[j] += 1;
            }
        }
        //  pitsForActing[selected]=times;
        if (checkIfEndGame(acting, opposite)) {
            acting.setPitsForPlayer(pitsForActing);
            opposite.setPitsForPlayer(pitsForOpposite);
            if (checkIfFirstIsTheWinner(acting, opposite) == 1) {
                findOne(gameId).setWinner(acting.getPlayer_first_name());
            } else if (checkIfFirstIsTheWinner(acting, opposite) == -1) {
                findOne(gameId).setWinner(opposite.getPlayer_first_name());
            } else {
                findOne(gameId).setWinner("none");
            }
            return true;
        } else {
            acting.setPitsForPlayer(pitsForActing);
            opposite.setPitsForPlayer(pitsForOpposite);
            acting.setInTurn(false);
            opposite.setInTurn(true);
            return false;
        }
    }
    public boolean makeMoveEndActingPitNotFullRound(int number, Player acting, Player opposite, int times, int initial, long gameId) {
        int[] pitsForActing = acting.getPitsForPlayer();
        int[] pitsForOpposite = opposite.getPitsForPlayer();
        int selected = number - 1;
        int amountOfStonesForTurn;
        int leftForActing;
        int forFirstIteration;
        int forSecondIteration;
        if (initial == 0) {
            amountOfStonesForTurn = pitsForActing[selected];
            pitsForActing[selected] = times;
            acting.setKalahForPlayer(acting.getKalahForPlayer() + 1);
            for (int i = 6, j = 0; i > 0; i--, j++) {
                pitsForOpposite[j] += 1;
            }
            leftForActing = amountOfStonesForTurn - 6 - 1;
            forFirstIteration = (6 - number);
            forSecondIteration = leftForActing - forFirstIteration;
            for (int i = forFirstIteration, j = 0; i > 0; i--, j++) {
                pitsForActing[number + j] += 1;
            }
            for (int i = forSecondIteration, j = 0; i > 0; i--, j++) {
                pitsForActing[j] += 1;
            }
        } else {
            amountOfStonesForTurn = initial - 13 * times;
            pitsForActing[selected] = times;
            acting.setKalahForPlayer(acting.getKalahForPlayer() + 1);
            for (int i = 6, j = 0; i > 0; i--, j++) {
                pitsForOpposite[j] += 1;
            }
            leftForActing = amountOfStonesForTurn - 6 - 1;
            forFirstIteration = 6 - number;
            forSecondIteration = leftForActing - forFirstIteration;
            for (int i = forFirstIteration, j = 0; i > 0; i--, j++) {
                pitsForActing[number + j] += 1;
            }
            for (int i = forSecondIteration, j = 0; i > 0; i--, j++) {
                pitsForActing[j] += 1;
            }
        }
        if ((pitsForActing[forSecondIteration - 1] == 1) &&
                (pitsForOpposite[5 - (forSecondIteration - 1)] != 0)) {
            acting.setKalahForPlayer(acting.getKalahForPlayer() + (1 + pitsForOpposite[5 - (forSecondIteration - 1)]));
            pitsForOpposite[5 - (forSecondIteration - 1)] = 0;
            pitsForActing[forSecondIteration - 1] = 0;
        }
        if (checkIfEndGame(acting, opposite)) {
            acting.setPitsForPlayer(pitsForActing);
            opposite.setPitsForPlayer(pitsForOpposite);
            if (checkIfFirstIsTheWinner(acting, opposite) == 1) {
                findOne(gameId).setWinner(acting.getPlayer_first_name());
            } else if (checkIfFirstIsTheWinner(acting, opposite) == -1) {
                findOne(gameId).setWinner(opposite.getPlayer_first_name());
            } else {
                findOne(gameId).setWinner("none");
            }
            return true;
        } else {
            acting.setPitsForPlayer(pitsForActing);
            opposite.setPitsForPlayer(pitsForOpposite);
            acting.setInTurn(false);
            opposite.setInTurn(true);
            return false;
        }
    }
    public int makeFullMove(int number, Player acting, Player opposite, int times) {
        int[] pitsForActing = acting.getPitsForPlayer();
        int[] pitsForOpposite = opposite.getPitsForPlayer();
        int selected = number - 1;
        int initial = pitsForActing[selected];
        pitsForActing[selected] = 0;
        for (int i = 6, j = 0; i > 0; i--, j++) {
            pitsForActing[j] = pitsForActing[j] + times;
        }
        acting.setKalahForPlayer(acting.getKalahForPlayer() + times);
        for (int i = 6, j = 0; i > 0; i--, j++) {
            pitsForOpposite[j] = pitsForOpposite[j] + times;
        }
        acting.setPitsForPlayer(pitsForActing);
        opposite.setPitsForPlayer(pitsForOpposite);
        return initial;
    }
}

