package org.authservice.user.service;

import org.authservice.exceptions.ListEmptyException;
import org.authservice.user.models.User;
import org.authservice.user.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQuerryServiceImpl implements UserQuerryService {

    UserRepo userRepo;


    public UserQuerryServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override

    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            return user;
        } else {
            throw new ListEmptyException();
        }
    }
}