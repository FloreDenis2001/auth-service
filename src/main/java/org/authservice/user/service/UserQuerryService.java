package org.authservice.user.service;

import jakarta.transaction.Transactional;
import org.authservice.user.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public interface UserQuerryService {
    Optional<User> findByEmail(String email);
}

