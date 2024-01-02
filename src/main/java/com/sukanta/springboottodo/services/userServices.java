package com.sukanta.springboottodo.services;

import com.sukanta.springboottodo.config.Constant;
import com.sukanta.springboottodo.config.JwtTokenUtil;
import com.sukanta.springboottodo.models.User;
import com.sukanta.springboottodo.repositories.userRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userServices {
    private final Logger logger = LoggerFactory.getLogger(userServices.class);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
    private final JwtTokenUtil jwtTokenUtil;
    private final userRepository userRepository;

    public userServices(JwtTokenUtil jwtTokenUtil, userRepository userRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    public User doSignUp(User user) throws Exception {
        User ExistingUser = userRepository.findByEmail(user.getEmail());
        if (ExistingUser != null) {
            throw new Exception(Constant.USER_ALREADY_EXISTS);
        } else {
            User user1 = new User(user.getEmail(), user.getName(), user.getPhone(), encoder.encode(user.getPassword()), user.getAddress());

            return userRepository.save(user1);
        }
    }

    public User getExistingUser(User request) {
        return userRepository.findByEmail(request.getEmail());
    }

    public String doLogin(User request) throws Exception {
        User ExistingUser = getExistingUser(request);
        if (ExistingUser != null) {
            if (encoder.matches(request.getPassword(), ExistingUser.getPassword())) {
                return jwtTokenUtil.generateToken(ExistingUser.getEmail(), ExistingUser.getId());
            } else {
                throw new Exception(Constant.PASSWORD_MISMATCHED);
            }
        } else {
            throw new Exception(Constant.USER_NOT_FOUND);
        }
    }

    public User forgotPassword(User request) throws Exception {
        User ExistingUser = getExistingUser(request);
        if (ExistingUser != null) {
            ExistingUser.setPassword(request.getPassword());
            return userRepository.save(ExistingUser);
        } else {
            throw new Exception(Constant.USER_NOT_FOUND);
        }
    }
}
