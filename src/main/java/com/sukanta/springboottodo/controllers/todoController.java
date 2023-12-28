package com.sukanta.springboottodo.controllers;

import com.sukanta.springboottodo.config.ApiResponse;
import com.sukanta.springboottodo.config.Constant;
import com.sukanta.springboottodo.config.JwtTokenUtil;
import com.sukanta.springboottodo.models.Todo;
import com.sukanta.springboottodo.services.todoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class todoController {


    @Autowired
    private todoServices todoServices;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Todo>> addTodo(@RequestHeader(name = "Authorization") String authorization, @RequestBody Todo request) {
        ApiResponse<Todo> apiResponse = new ApiResponse<>();

        if (authorization.isEmpty()) {
            apiResponse.setError(true);
            apiResponse.setMessage(Constant.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        } else {
            if (jwtTokenUtil.validateToken(authorization)) {
                Todo todo = todoServices.addTodo(request);

                apiResponse.setError(false);
                apiResponse.setMessage(Constant.TODO_ADDED);
                apiResponse.setData(todo);

                return ResponseEntity.ok().body(apiResponse);
            } else {
                apiResponse.setError(true);
                apiResponse.setMessage(Constant.INVALID_TOKEN);

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
            }
        }
    }

}
