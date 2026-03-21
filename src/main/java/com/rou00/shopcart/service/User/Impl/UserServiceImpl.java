package com.rou00.shopcart.service.User.Impl;

import com.rou00.shopcart.exceptions.ResourceExists;
import com.rou00.shopcart.exceptions.ResourceNotFound;
import com.rou00.shopcart.model.dto.UserDTO;
import com.rou00.shopcart.model.entity.Role;
import com.rou00.shopcart.model.entity.User;
import com.rou00.shopcart.repository.RoleRepository;
import com.rou00.shopcart.repository.UserRepository;
import com.rou00.shopcart.request.CreateUserRequest;
import com.rou00.shopcart.request.UpdateUseRequest;
import com.rou00.shopcart.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("No such User Found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {

        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(req.getEmail());
                    user.setPassword(passwordEncoder.encode(req.getPassword()));
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());
                    Role role = roleRepository.findByName(req.getRole());
                    user.setRoles(Set.of(role));
                    return userRepository.save(user);
                }).orElseThrow(()-> new ResourceExists(request.getEmail() + " Already Exists!"));
    }

    @Override
    public User updateUser(UpdateUseRequest request , Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
                existingUser.setFirstName(request.getFirstName());
                existingUser.setLastName(request.getLastName());
                return userRepository.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFound("User not Found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, ()-> {
            throw new ResourceNotFound("User Not Found");
            });
    }

    @Override
    public UserDTO convertUserToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
