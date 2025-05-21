package com.example.userapi.controller;

import com.example.userapi.model.dto.UserDto;
import com.example.userapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        var created = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        var edited = userService.editUser(id, userDto);
        return ResponseEntity.status(HttpStatus.OK).body(edited);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Optional<UserDto>> deleteUser(@PathVariable Long id) {
        var deleted = userService.getUser(id);
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}
