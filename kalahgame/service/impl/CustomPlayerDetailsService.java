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
import ua.kalahgame.infrastructure.exceptions.EntityNotFoundException;
import ua.kalahgame.infrastructure.passwordGenerateAndHash.PasswordHash;
import ua.kalahgame.repository.PlayerRepository;

/**
 * Created by a.lomako on 2/1/2017.
 */

@Service
@Transactional(readOnly = true)
public class CustomPlayerDetailsService implements UserDetailsService {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        try {

            Player domainUser = playerRepository.findOne(playerRepository.getUserByLogin(login));
            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;
            PasswordHash passwordHash;
            return new User(
                    domainUser.getPlayer_login(),
                    domainUser.getPlayer_password(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities(domainUser.getRole().getId())
            );
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error in retrieving user");
        }
    }
    public Collection<? extends GrantedAuthority> getAuthorities(Long role) {

        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));

        return authList;
    }

    public List<String> getRoles(Long role) {

        List<String> roles = new ArrayList<>();

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

        return authorities;
    }

}


