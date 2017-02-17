package ua.kalahgame.domain;

/**
 * Created by a.lomako on 2/16/2017.
 */
public class GameForm {
    public GameForm() {}
    private String firstName;
    private String secondName;
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSecondName() {
        return secondName;
    }
   public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}