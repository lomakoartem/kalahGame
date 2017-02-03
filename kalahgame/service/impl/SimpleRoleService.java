package ua.kalahgame.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kalahgame.domain.Role;
import ua.kalahgame.repository.RoleRepository;
import ua.kalahgame.service.RoleService;

/**
 * Created by a.lomako on 2/1/2017.
 */
@Service
public class SimpleRoleService implements RoleService {


    RoleRepository roleRepository;

    @Autowired
    public SimpleRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRole(Long id) {
        return roleRepository.findOne(id);
    }
}
