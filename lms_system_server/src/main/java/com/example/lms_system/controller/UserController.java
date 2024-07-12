package com.example.lms_system.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_system.dto.request.UserRequest;
import com.example.lms_system.dto.response.UserResponse;
import com.example.lms_system.entity.User;
import com.example.lms_system.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/users", "/users/"})
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserResponse postMethodName(@RequestBody UserRequest userRequest) {
        return userService.insertUser(userRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<User>> getAttendances(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<User> userPage = userService.getUsers(page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-page-number", String.valueOf(userPage.getNumber()));
        headers.add("User-page-size", String.valueOf(userPage.getSize()));
        return ResponseEntity.ok().headers(headers).body(userPage);
    }

    @GetMapping("/details")
    public ResponseEntity<User> getUserDetails(@RequestParam String id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}