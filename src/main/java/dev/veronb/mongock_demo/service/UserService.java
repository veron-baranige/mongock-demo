package dev.veronb.mongock_demo.service;

import dev.veronb.mongock_demo.entity.user.User;
import dev.veronb.mongock_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
