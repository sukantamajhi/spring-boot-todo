package com.sukanta.springboottodo.services;

import com.sukanta.springboottodo.config.Constant;
import com.sukanta.springboottodo.config.JwtTokenUtil;
import com.sukanta.springboottodo.models.User;
import com.sukanta.springboottodo.repositories.userRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userServices {

    private final Logger logger = LoggerFactory.getLogger(userServices.class);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private userRepository userRepository;

    public User doSignUp(User user) throws Exception {
        System.out.println(user.getEmail() + "<<-- user email");
        User ExistingUser = userRepository.findByEmail(user.getEmail());
        logger.info("Existing user ===>> " + ExistingUser);

        if (ExistingUser != null) {
            throw new Exception(Constant.USER_ALREADY_EXISTS);
        } else {
            User user1 = new User(user.getEmail(), user.getName(), user.getPhone(), encoder.encode(user.getPassword()), user.getAddress());

            return userRepository.save(user1);
        }
    }

    public String doLogin(User request) throws Exception {
        User ExistingUser = userRepository.findByEmail(request.getEmail());
        logger.info("Existing user ===>> " + ExistingUser);

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
}
