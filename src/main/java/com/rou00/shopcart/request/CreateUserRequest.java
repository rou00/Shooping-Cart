package com.rou00.shopcart.request;

import com.rou00.shopcart.model.entity.Role;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;

@Data
public class CreateUserRequest {


    private String firstName;
    private String lastName;
    private  String email;
    private String password;
    private String role ;

}
