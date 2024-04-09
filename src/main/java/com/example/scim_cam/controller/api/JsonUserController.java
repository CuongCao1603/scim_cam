package com.example.scim_cam.controller.api;

import com.example.scim_cam.model.Account;
import com.example.scim_cam.model.User;
import com.example.scim_cam.repository.GroupRepository;
import com.example.scim_cam.repository.UserRepository;
import com.example.scim_cam.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * REST API for User management
 */
@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class JsonUserController {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    /*
     * Return all users
     */
    @GetMapping
    public Map<String, Object> getUsers() {
        return JsonUtil.usersToPayLoad(userRepository.findAll());
    } // getUsers

    /*
     * Create a user
     */
    @PostMapping
    public Map createUser(@RequestBody Map<String, Object> params, HttpServletResponse response) {
        User newUser = JsonUtil.toUser(params);
        Account newAccount = new Account();
        // Không cần set secretKey nếu bạn muốn nó luôn là "cuongcao" như đã định nghĩa
        // trong class Account
        newAccount.setUser(newUser); // Liên kết User và Account
        newUser.setAccount(newAccount);
        userRepository.save(newUser);
        response.setStatus(HttpStatus.CREATED.value()); // 201
        return JsonUtil.userToPayLoad(newUser);
    } // createUser
}
