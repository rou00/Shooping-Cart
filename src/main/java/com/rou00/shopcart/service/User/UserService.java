package com.rou00.shopcart.service.User;

import com.rou00.shopcart.model.dto.UserDTO;
import com.rou00.shopcart.model.entity.User;
import com.rou00.shopcart.request.CreateUserRequest;
import com.rou00.shopcart.request.UpdateUseRequest;

public interface UserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUseRequest request , Long userId);
    void deleteUser(Long userId);

    UserDTO convertUserToDTO(User user);

    User getAuthenticatedUser();
}
