package com.sukanta.springboottodo.controllers;

import com.sukanta.springboottodo.config.ApiResponse;
import com.sukanta.springboottodo.config.Constant;
import com.sukanta.springboottodo.models.User;
import com.sukanta.springboottodo.services.userServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class userController {

    private final Logger logger = LoggerFactory.getLogger(userController.class);
    private final userServices services;

    public userController(userServices services) {
        this.services = services;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<User>> signup(@RequestBody User request) {

        ApiResponse<User> apiResponse = new ApiResponse<>();
        try {
            User newUser = services.doSignUp(request);

            apiResponse.setData(newUser);
            apiResponse.setError(false);
            apiResponse.setMessage(Constant.USER_CREATED);
            apiResponse.setCode("USER_CREATED");

            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception err) {
            logger.error("Error in signup ==>> " + err);
            apiResponse.setError(true);
            apiResponse.setMessage(err.getMessage());
            apiResponse.setCode("INTERNAL_SERVER_ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody User request) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        try {
            String access_token = services.doLogin(request);

            apiResponse.setAccess_token(access_token);
            apiResponse.setError(false);
            apiResponse.setMessage(Constant.USER_LOGGED_IN);
            apiResponse.setCode("LOGGED_IN");

            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception err) {
            logger.error("Error in login ==>> " + err);
            apiResponse.setError(true);
            apiResponse.setMessage(err.getMessage());
            apiResponse.setCode("INTERNAL_SERVER_ERROR");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<User>> forgotPassword(@RequestBody User request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();

        try {
            User user = services.forgotPassword(request);

            apiResponse.setError(false);
            apiResponse.setMessage(Constant.RESET_PASSWORD_SUCCESS);
            apiResponse.setCode("RESET_PASSWORD_SUCCESS");

            return ResponseEntity.ok().body(apiResponse);
        } catch (Exception err) {
            logger.error("Error in login ==>> " + err);
            apiResponse.setError(true);
            apiResponse.setMessage(err.getMessage());
            apiResponse.setCode("INTERNAL_SERVER_ERROR");
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }

    }

}
