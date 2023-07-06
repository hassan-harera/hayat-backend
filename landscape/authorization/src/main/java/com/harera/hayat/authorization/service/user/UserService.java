package com.harera.hayat.authorization.service.user;

import com.harera.hayat.authorization.model.user.UserResponse;
import com.harera.hayat.authorization.repository.UserRepository;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponse get(long id) {
        return userRepository.findById(id)
                        .map(user -> modelMapper.map(user, UserResponse.class))
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
