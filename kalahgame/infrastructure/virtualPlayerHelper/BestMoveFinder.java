/*
package ua.kalahgame.infrastructure.virtualPlayerHelper;

        import ua.kalahgame.domain.Board;

        import java.util.ArrayList;
        import java.util.List;

*/
/**
 * Created by a.lomako on 2/3/2017.
 *//*

class BestMoveFinder {

    private final Board board;

    private boolean playerStarted;

    private boolean playerTurn;

    private boolean gameOver;

    public BestMoveFinder(final Board aBoard, final boolean isPlayerTurn) {
        this.board = aBoard;
        this.playerTurn = isPlayerTurn;
        this.playerStarted = isPlayerTurn;
    }

    public int findBestMove(final int startIndex) {
        houseSelected(startIndex);

        if (this.gameOver || currentLevel == NUMBER_OF_MOVES) {
            if (this.playerStarted) {
                return this.board.getOpponentKalah().getNumberOfSeeds()
                        - this.board.getPlayerKalah().getNumberOfSeeds();
            } else {
                return this.board.getPlayerKalah().getNumberOfSeeds()
                        - this.board.getOpponentKalah().getNumberOfSeeds();
            }
        }

        IHouse[] houses = null;
        if (this.playerTurn) {
            houses = this.board.getPlayerHouses();
        } else {
            houses = this.board.getOpponentHouses();
        }

        int bestMove = Integer.MAX_VALUE;
        currentLevel++;
        for (int i = 0; i < houses.length; i++) {
            final int moveValue = new BestMoveFinder(new Board(this.board), !this.playerTurn).findBestMove(i);
            if (moveValue < bestMove) {
                bestMove = moveValue;
            }
        }

        return bestMove;

    }

    private boolean checkGameOver() {
        if (this.board.arePlayerHousesEmpty()) {
            this.board.addAllOpponentSeedsToOpponentKalah();
            return true;
        }

        if (this.board.areOpponentHousesEmpty()) {
            this.board.addAllPlayerSeedsToPlayerKalah();
            return true;
        }

        return false;
    }

    private void houseSelected(final int houseIndex) {
        final IHouse[] houses = this.playerTurn ? this.board.getPlayerHouses() : this.board.getOpponentHouses();
        if (houseIndex > -1 && houseIndex < houses.length) {
            final IHouse selectedHouse = houses[houseIndex];
            if (!selectedHouse.isEmpty()) {
                final List<Seed> seeds = new ArrayList<Seed>(selectedHouse.getSeeds());
                selectedHouse.clear();

                moveSeeds(houseIndex, seeds, this.playerTurn);
            } else {
                return;
            }
        }

        this.playerTurn = !this.playerTurn;

        if (checkGameOver()) {
            this.gameOver = true;
            return;
        }
    }

    private IHouse[] moveSeeds(final int houseIndex, final List<Seed> seeds,
                               final boolean isPlayerTurn) {
        final IHouse[] houses = this.board.getHouses(isPlayerTurn);
        int startIndex = houseIndex + 1;

        for (int i = 0; i < seeds.size(); i++) {
            final Seed seed = seeds.get(i);
            final IHouse house = houses[startIndex];
            final boolean isHouseEmpty = house.isEmpty();
            house.add(seed);
            startIndex++;

            if (startIndex == houses.length) {
                startIndex = 0;
            }

            if (i == seeds.size() - 1) {
                if (this.playerTurn) {
                    // Special case: If the last seed drops into the Player kalah, the player gets a new move
                    if (house instanceof PlayerKalah) {
                        this.playerTurn = !this.playerTurn;
                    }

                    // Special case: If the last seed drops into an empty house and the opposite house is not empty,
                    // the player gets all the seeds from ths house and the opposite house
                    if (house instanceof PlayerHouse && isHouseEmpty) {
                        final int oppositeHouseIndex = houses.length - startIndex;
                        final IHouse oppositeHouse = houses[oppositeHouseIndex];
                        if (!oppositeHouse.isEmpty()) {
                            this.board.getPlayerKalah().addAll(house);
                            this.board.getPlayerKalah().addAll(oppositeHouse);
                        }
                    }
                } else {
                    // Special case: If the last seed drops into the Player kalah, the player gets a new move
                    if (house instanceof OpponentKalah) {
                        this.playerTurn = !this.playerTurn;
                    }

                    // Special case: If the last seed drops into an empty house and the opposite house is not empty,
                    // the player gets all the seeds from ths house and the opposite house
                    if (house instanceof OpponentHouse && isHouseEmpty) {
                        final int oppositeHouseIndex = houses.length - startIndex;
                        final IHouse oppositeHouse = houses[oppositeHouseIndex];
                        if (!oppositeHouse.isEmpty()) {
                            this.board.getOpponentKalah().addAll(house);
                            this.board.getOpponentKalah().addAll(oppositeHouse);
                        }
                    }
                }
            }
        }

        return houses;
    }
}

*/
