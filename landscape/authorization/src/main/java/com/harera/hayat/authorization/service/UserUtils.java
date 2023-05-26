package com.harera.hayat.authorization.service;

import com.harera.hayat.authorization.model.user.User;
import com.harera.hayat.authorization.repository.UserRepository;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.harera.hayat.framework.util.RegexUtils.*;

@Service
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;

    public long getUserId(String subject) throws EntityNotFoundException {
        User user = getUser(subject);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        } else {
            return user.getId();
        }
    }

    public User getUser(String subject) {
        Optional<User> user = Optional.empty();
        if (isPhoneNumber(subject)) {
            user = userRepository.findByMobile(subject);
        } else if (isEmail(subject)) {
            user = userRepository.findByEmail(subject);
        } else if (isUsername(subject)) {
            user = userRepository.findByUsername(subject);
        }
        return user.orElse(null);
    }
}
