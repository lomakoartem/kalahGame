package ua.kalahgame.service.impl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kalahgame.domain.Player;
import ua.kalahgame.repository.PlayerRepository;

/**
 * Created by a.lomako on 2/1/2017.
 */

@Service
@Transactional(readOnly=true)
public class CustomPlayerDetailsService implements UserDetailsService {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        System.out.println("ARSENALBefore");

        System.out.println(login);
        System.out.println(playerRepository.getUserByLogin(login));
        Player domainUser = playerRepository.findOne(playerRepository.getUserByLogin(login));
        System.out.println(domainUser.toString());
       // System.out.println(domainUser.getRole());
        //System.out.println("ARSENAL");
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
       // System.out.println(domainUser.toString());
        //System.out.println(domainUser.toString());
        ///System.out.println("Try to get role");

        System.out.println(getAuthorities(domainUser.getRole().getId()));
        //System.out.println("Fail to get role");
        return new User(
                domainUser.getPlayer_login(),
                domainUser.getPlayer_password(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(domainUser.getRole().getId())
        );

    }

    public Collection<? extends GrantedAuthority> getAuthorities(Long role) {
        System.out.println("I am in getAuthorities method");
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));


        return authList;
    }

    public List<String> getRoles(Long role) {

        System.out.println("I am in getAuthorities getRoles method");
        List<String> roles = new ArrayList<String>();

        if (role.intValue() == 1) {
            roles.add("ROLE_PLAYER");
            roles.add("ROLE_ADMIN");
        } else if (role.intValue() == 2) {
            roles.add("ROLE_PLAYER");
        }
        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
       // System.out.println("I am in getAuthorities getGrantedAuthorities method");
        return authorities;
    }

}


