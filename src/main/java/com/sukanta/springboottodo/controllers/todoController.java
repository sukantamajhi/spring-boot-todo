package com.sukanta.springboottodo.controllers;

import com.sukanta.springboottodo.config.ApiResponse;
import com.sukanta.springboottodo.config.Constant;
import com.sukanta.springboottodo.config.JwtTokenUtil;
import com.sukanta.springboottodo.models.Todo;
import com.sukanta.springboottodo.services.todoServices;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class todoController {
    private final Logger logger = LoggerFactory.getLogger(todoController.class);

    private final todoServices todoServices;
    private final JwtTokenUtil jwtTokenUtil;

    public todoController(todoServices todoServices, JwtTokenUtil jwtTokenUtil) {
        this.todoServices = todoServices;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Todo>> addTodo(@NotNull @RequestHeader(name = "Authorization") String authorization, @RequestBody Todo request) {
        ApiResponse<Todo> apiResponse = new ApiResponse<>();

        if (!jwtTokenUtil.verifyJWT(authorization)) {
            apiResponse.setError(true);
            apiResponse.setMessage(Constant.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        } else {
            ObjectId userId = jwtTokenUtil.getUserId(authorization);

            Todo todo = todoServices.addTodo(request, userId);

            apiResponse.setError(false);
            apiResponse.setMessage(Constant.TODO_ADDED);
            apiResponse.setData(todo);

            return ResponseEntity.ok().body(apiResponse);
        }
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Todo>>> getTodo(@NotNull @RequestHeader(name = "Authorization") String authorization) {
        ApiResponse<List<Todo>> apiResponse = new ApiResponse<>();

        if (!jwtTokenUtil.verifyJWT(authorization)) {
            apiResponse.setError(true);
            apiResponse.setMessage(Constant.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        } else {
            ObjectId userId = jwtTokenUtil.getUserId(authorization);

            List<Todo> todos = todoServices.getTodos(userId);

            apiResponse.setData(todos);
            apiResponse.setError(false);
            apiResponse.setMessage(Constant.TODO_FETCHED);

            return ResponseEntity.ok().body(apiResponse);
        }
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<ApiResponse<Todo>> updateTodo(
            @NotNull @RequestHeader(name = "Authorization") String authToken,
            @RequestBody Todo request,
            @PathVariable String todoId) {

        System.out.println(authToken + " <<-- Authtoken \n" + todoId + " <<-- todoId");

        ApiResponse<Todo> apiResponse = new ApiResponse<>();

        if (!jwtTokenUtil.verifyJWT(authToken)) {
            apiResponse.setError(true);
            apiResponse.setMessage(Constant.UNAUTHORIZED);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        } else {
            todoServices.updateTodo(todoId, request);

            apiResponse.setError(false);
            apiResponse.setMessage(Constant.TODO_UPDATED);
//            apiResponse.setData(updatedTodo);

            return ResponseEntity.ok().body(apiResponse);
        }
    }

}
