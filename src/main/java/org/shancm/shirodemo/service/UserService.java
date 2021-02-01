package org.shancm.shirodemo.service;

import org.shancm.shirodemo.domain.entity.User;

public interface UserService {

    User getUserByName(String name);
}
