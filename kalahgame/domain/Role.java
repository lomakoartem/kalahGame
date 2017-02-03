package ua.kalahgame.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by a.lomako on 2/1/2017.
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_roles",
            joinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="player_id", referencedColumnName="player_id")}
    )
    private Set<Player> playerRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<Player> getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(Set<Player> playerRole) {
        this.playerRole = playerRole;
    }

    @Override
    public String toString() {
        return name;
    }
}