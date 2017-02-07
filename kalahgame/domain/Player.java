package ua.kalahgame.domain;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by a.lomako on 1/30/2017.
 */
@Entity
@Table(name = "users")
public class Player{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;



    @Column(name = "player_first_name")
    private String player_first_name;


    @Column(name = "player_login")
    private String player_login;


    @Column(name = "player_password")
    private String player_password;

    @Column(name = "won_games")
    private int won_games;

    @Column(name = "lose_games")
    private int lose_games;

    @Column(name = "player_email")
    private String email;


    @OneToOne(cascade=  CascadeType.MERGE)
    @JoinTable(name="user_role",
            joinColumns = {@JoinColumn(name="player_id", referencedColumnName="player_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
    )
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;

    }

    public String getPlayer_first_name() {
        return player_first_name;
    }

    public void setPlayer_first_name(String player_first_name) {
        this.player_first_name = player_first_name;
    }

    public String getPlayer_login() {
        return player_login;
    }

    public void setPlayer_login(String player_login) {
        this.player_login = player_login;
    }

    public String getPlayer_password() {
        return player_password;
    }

    public void setPlayer_password(String player_password) {
        this.player_password = player_password;
    }

    public int getWon_games() {
        return won_games;
    }

    public void setWon_games(int won_games) {
        this.won_games = won_games;
    }

    public int getLose_games() {
        return lose_games;
    }

    public void setLose_games(int lose_games) {
        this.lose_games = lose_games;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (won_games != player.won_games) return false;
        if (lose_games != player.lose_games) return false;
        if (id != null ? !id.equals(player.id) : player.id != null) return false;
        if (player_login != null ? !player_login.equals(player.player_login) : player.player_login != null)
            return false;
        if (player_password != null ? !player_password.equals(player.player_password) : player.player_password != null)
            return false;
        return email != null ? email.equals(player.email) : player.email == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (player_login != null ? player_login.hashCode() : 0);
        result = 31 * result + (player_password != null ? player_password.hashCode() : 0);
        result = 31 * result + won_games;
        result = 31 * result + lose_games;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", player_first_name='" + player_first_name + '\'' +
                ", player_login='" + player_login + '\'' +
                ", player_password='" + player_password + '\'' +
                ", won_games=" + won_games +
                ", lose_games=" + lose_games +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
