package org.authservice.user.service;

import org.authservice.user.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserCommandService {

    void addUser(UserDTO userDTO);

    void updateUser(String email , UserDTO userDTO);

    void deleteUser(String email);
}

