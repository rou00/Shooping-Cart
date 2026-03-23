package com.rou00.shopcart.repository;

import com.rou00.shopcart.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {


    Role findByName(String role);
}
