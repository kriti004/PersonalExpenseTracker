package com.personalExpenseTracker.personalExpenseTracker.service;

import com.personalExpenseTracker.personalExpenseTracker.entity.User;
import com.personalExpenseTracker.personalExpenseTracker.user.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUsername(String Username) throws Exception;

    User findUserById(int id) throws Exception;

    User save(UserDTO userDTO);

}
