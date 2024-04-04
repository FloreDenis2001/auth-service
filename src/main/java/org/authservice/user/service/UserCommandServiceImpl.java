package org.authservice.user.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.authservice.exceptions.UserAlreadyExistsException;
import org.authservice.exceptions.UserNotFound;
import org.authservice.system.security.UserRole;
import org.authservice.user.dto.UserDTO;
import org.authservice.user.models.User;
import org.authservice.user.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private UserRepo userRepo;
    private BCryptPasswordEncoder passwordEncoder;



    @Override
    public void addUser(UserDTO userDTO) {

        Optional<User> user = userRepo.findByEmail(userDTO.email());
        if (user.isEmpty()) {
            User x = User.builder()
                    .email(userDTO.email())
                    .firstName(userDTO.firstName())
                    .lastName(userDTO.lastName())
                    .phoneNumber(userDTO.phoneNumber())
                    .password(passwordEncoder.encode(userDTO.password()))
                    .registeredAt(LocalDateTime.now())
                    .createdAt(userDTO.createdAt())
                    .active(userDTO.active())
                    .userRole(UserRole.ADMIN) // This line sets a hardcoded user role; adjust as necessary.
                    .build();
            userRepo.saveAndFlush(x);
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public void updateUser(String email , UserDTO userDTO) {

        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            User x = user.get();
            x.setEmail(userDTO.email());
            x.setFirstName(userDTO.firstName());
            x.setLastName(userDTO.lastName());
            x.setPhoneNumber(userDTO.phoneNumber());
            x.setPassword(userDTO.password());
            userRepo.saveAndFlush(x);
        } else {
            throw new UserNotFound();
        }

    }

    @Override
    public void deleteUser(String email) {

        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            userRepo.delete(user.get());
        } else {
            throw new UserNotFound();
        }

    }
}

