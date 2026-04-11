package com.gustavodscruz.livranada.service;

import com.gustavodscruz.livranada.exception.UserNotFoundException;
import com.gustavodscruz.livranada.exception.UserNotValidException;
import com.gustavodscruz.livranada.model.User;
import com.gustavodscruz.livranada.model.mappers.UserMapper;
import com.gustavodscruz.livranada.repository.UserRepository;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gustavodscruz.livranada.utils.SecurityUtils.hashString;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Validator validator;
    private final UserMapper userMapper;

    public User create(User dto) throws UserNotValidException {
        validateUser(dto);

        dto.setPasswordHash(hashString(dto.getPassword()));
        return userRepository.save(dto);
    }

    public User update(Long id, User dto) throws UserNotValidException, UserNotFoundException {
        validateUser(dto);
        dto.setPasswordHash(hashString(dto.getPassword()));

        User userFound = findById(id);
        userMapper.updateEntityFromDTO(dto, userFound);
        return userRepository.save(userFound);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public void delete(Long id) throws UserNotFoundException {
        User user = findById(id);
        userRepository.delete(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    private void validateUser(User user) throws UserNotValidException {
        var violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new UserNotValidException(violations);
        }
    }
}
