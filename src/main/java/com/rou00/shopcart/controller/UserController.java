package com.rou00.shopcart.controller;


import com.rou00.shopcart.model.dto.UserDTO;
import com.rou00.shopcart.model.entity.User;
import com.rou00.shopcart.request.CreateUserRequest;
import com.rou00.shopcart.request.UpdateUseRequest;
import com.rou00.shopcart.service.User.Impl.UserServiceImpl;
import com.rou00.shopcart.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDTO userDto = userService.convertUserToDTO(user);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return  new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest request){
        try {
            User user = userService.createUser(request);
            UserDTO userDto = userService.convertUserToDTO(user);
            return new ResponseEntity<>(userDto,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateUseRequest request, @PathVariable Long userId){
        try {
            User user = userService.updateUser(request,userId);
            UserDTO userDto = userService.convertUserToDTO(user);
            return new ResponseEntity<>(userDto,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>("User: "+userId+" Deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User: "+userId+" not Found!",HttpStatus.NOT_FOUND);
        }
    }
}
