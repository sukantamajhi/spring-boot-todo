package com.sukanta.springboottodo.controllers;

import com.sukanta.springboottodo.config.ApiResponse;
import com.sukanta.springboottodo.config.Constant;
import com.sukanta.springboottodo.config.JwtTokenUtil;
import com.sukanta.springboottodo.models.Todo;
import com.sukanta.springboottodo.services.todoServices;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
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
        try {
            if (!jwtTokenUtil.verifyJWT(authorization)) {
                apiResponse.setError(true);
                apiResponse.setMessage(Constant.UNAUTHORIZED);
                apiResponse.setCode("UNAUTHORIZED");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
            } else {
                String userId = jwtTokenUtil.getUserId(authorization);

                Todo todo = todoServices.addTodo(request, userId);

                apiResponse.setError(false);
                apiResponse.setMessage(Constant.TODO_ADDED);
                apiResponse.setCode("TODO_ADDED");
                apiResponse.setData(todo);

                return ResponseEntity.ok().body(apiResponse);
            }
        } catch (Exception err) {
            logger.error(err + " <<-- Error in add todo");
            apiResponse.setError(true);
            apiResponse.setMessage(err.getMessage());
            apiResponse.setCode("INTERNAL_SERVER_ERROR");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Todo>>> getTodo(@NotNull @RequestHeader(name = "Authorization") String authorization) {
        ApiResponse<List<Todo>> apiResponse = new ApiResponse<>();
        try {
            if (!jwtTokenUtil.verifyJWT(authorization)) {
                apiResponse.setError(true);
                apiResponse.setMessage(Constant.UNAUTHORIZED);
                apiResponse.setCode("UNAUTHORIZED");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
            } else {
                String userId = jwtTokenUtil.getUserId(authorization);

                List<Todo> todos = todoServices.getTodos(userId);

                apiResponse.setData(todos);
                apiResponse.setError(false);
                apiResponse.setCode("TODO_FETCHED");
                apiResponse.setMessage(Constant.TODO_FETCHED);

                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
            }
        } catch (Exception err) {
            logger.error(err + " <<-- Error in getting todos");
            apiResponse.setError(true);
            apiResponse.setMessage(err.getMessage());
            apiResponse.setCode("INTERNAL_SERVER_ERROR");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<ApiResponse<Todo>> updateTodo(@NotNull @RequestHeader(name = "Authorization") String authToken, @RequestBody Todo request, @PathVariable String todoId) {
        ApiResponse<Todo> apiResponse = new ApiResponse<>();
        try {
            if (!jwtTokenUtil.verifyJWT(authToken)) {
                apiResponse.setError(true);
                apiResponse.setMessage(Constant.UNAUTHORIZED);
                apiResponse.setCode("UNAUTHORIZED");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
            } else {
                String userId = jwtTokenUtil.getUserId(authToken);
                Todo todo = todoServices.updateTodo(todoId, request, userId);

                if (todo != null) {
                    apiResponse.setError(false);
                    apiResponse.setCode("TODO_UPDATED");
                    apiResponse.setMessage(Constant.TODO_UPDATED);
                    apiResponse.setData(todo);
                } else {
                    apiResponse.setError(true);
                    apiResponse.setMessage(Constant.TODO_UPDATE_FAILED);
                    apiResponse.setCode("TODO_UPDATE_FAILED");
                }
                return ResponseEntity.ok().body(apiResponse);
            }
        } catch (Exception err) {
            logger.error(err + " <<-- Error in update todo");
            apiResponse.setError(true);
            apiResponse.setMessage(err.getMessage());
            apiResponse.setCode("INTERNAL_SERVER_ERROR");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<ApiResponse<Todo>> deleteTodo(@NotNull @RequestHeader(name = "Authorization") String authToken, @PathVariable String todoId) {
        ApiResponse<Todo> apiResponse = new ApiResponse<>();
        try {
            if (!jwtTokenUtil.verifyJWT(authToken)) {
                apiResponse.setError(true);
                apiResponse.setMessage(Constant.UNAUTHORIZED);
                apiResponse.setCode("UNAUTHORIZED");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
            } else {
                String userId = jwtTokenUtil.getUserId(authToken);
                boolean todo = todoServices.deleteTodo(todoId, userId);

                if (todo) {
                    apiResponse.setError(false);
                    apiResponse.setMessage(Constant.TODO_DELETED);
                    apiResponse.setCode("TODO_DELETED");
                } else {
                    apiResponse.setError(true);
                    apiResponse.setMessage(Constant.TODO_DELETE_FAILED);
                    apiResponse.setCode("TODO_DELETE_FAILED");
                }
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
            }
        } catch (Exception err) {
            logger.error(err + " <<-- Error in delete todo");
            apiResponse.setError(true);
            apiResponse.setMessage(err.getMessage());
            apiResponse.setCode("INTERNAL_SERVER_ERROR");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

}
