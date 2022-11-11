package com.example.challenge.s1_challenge.service;

import com.example.challenge.s1_challenge.domain.User;
import com.example.challenge.s1_challenge.exception.UserNotFoundException;
import com.example.challenge.s1_challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        User user = userRepository.findByUsernameAndPassword(username,password);
        if (user == null){
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUser(int userId) throws UserNotFoundException {
        boolean res = false;
        if (userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException();
        }else {
            userRepository.deleteById(userId);
            res = true;
        }
        return res;
    }
}
