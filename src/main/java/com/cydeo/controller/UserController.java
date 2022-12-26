package com.cydeo.controller;

import com.cydeo.annotation.DefaultExceptionMessage;
import com.cydeo.annotation.ExecutionTime;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name="UserController", description ="User API")
public class UserController {

  private final ResponseWrapper responseWrapper;
  private final UserService userService;

    public UserController(ResponseWrapper responseWrapper, UserService userService) {
        this.responseWrapper = responseWrapper;
        this.userService = userService;
    }

    @ExecutionTime
    @GetMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Get Users")
    public ResponseEntity<ResponseWrapper> getAllUsers(){
        return ResponseEntity.ok().body(new ResponseWrapper("Users are retrieved",userService.listAllUsers(), HttpStatus.ACCEPTED));

    }

    @GetMapping("/{userName}")
    @Operation(summary = "Get User by Username")
    public ResponseEntity<ResponseWrapper> getUserByUsername(@PathVariable("username") String username){
       return ResponseEntity.ok().body(new ResponseWrapper("User is retrieved",userService.findByUserName(username),HttpStatus.ACCEPTED));

    }

    @PostMapping("/create")
    @RolesAllowed("Admin")
    @Operation(summary = "Create User")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user){
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User created", HttpStatus.CREATED));
    }

    @PutMapping("/update/{username}")
    @Operation(summary = "Update User")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO){
        userService.update(userDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseWrapper("User is updated",HttpStatus.ACCEPTED));
    }

    @DeleteMapping("/delete/{username}")
    @RolesAllowed("Admin")
    @Operation(summary = "Delete User by Username")
    @DefaultExceptionMessage(defaultMessage = "Failed to delete user")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String username){
        userService.deleteByUserName(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper("User is deleted",HttpStatus.NO_CONTENT));

    }

}
