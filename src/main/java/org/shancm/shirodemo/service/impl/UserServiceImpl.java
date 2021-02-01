package org.shancm.shirodemo.service.impl;

import org.shancm.shirodemo.domain.entity.User;
import org.shancm.shirodemo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByName(String name) {
        User user = new User();
        switch (name){
            case "administer":
                user.setRoleSet(user.roleAdminister());
                break;
            case "manager":
                user.setRoleSet(user.roleManager());
                break;
        }
        return user;
    }
}
