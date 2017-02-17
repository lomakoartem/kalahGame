package ua.kalahgame.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by a.lomako on 2/16/2017.
 */
@Entity
public class Game implements Serializable {

    static final long serialVersionUID = 42L;
    public Game() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GAME_ID")
    private long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "InitialPlayer1")
    private Player initialFirstPlayer;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    @JoinColumn (name = "InitialPlayer2")
    private Player initialSecondPlayer;
    @Column
    private boolean asFirst;
    @Column
    private String winner;
    @Column
    private int numberOfPitForLastMove;
    public void setWinner(String winner) {
        this.winner = winner;
    }
    public int getNumberOfPitForLastMove() {
        return numberOfPitForLastMove;
    }
    public void setNumberOfPitForLastMove(int numberOfPitForLastMove) {

        this.numberOfPitForLastMove = numberOfPitForLastMove;
    }
    public boolean isAsFirst() {
        return asFirst;
    }
    public void setAsFirst(boolean asFirst) {
        this.asFirst = asFirst;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Player getInitialFirstPlayer() {
        return initialFirstPlayer;
    }
    public void setInitialFirstPlayer(Player initialFirstPlayer) {
        this.initialFirstPlayer = initialFirstPlayer;

    }

    public Player getInitialSecondPlayer() {

        return initialSecondPlayer;

    }
    public void setInitialSecondPlayer(Player initialSecondPlayer) {

        this.initialSecondPlayer = initialSecondPlayer;
    }

    public String getWinner() {

        return winner;
    }


}