package com.example.scim_cam.controller.api;

import com.example.scim_cam.model.User;
import com.example.scim_cam.repository.GroupRepository;
import com.example.scim_cam.repository.UserRepository;
import com.example.scim_cam.utils.JsonUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public Map<String, Object> getUsers(){
        return JsonUtil.usersToPayLoad(userRepository.findAll());
    } // getUsers

    /*
    * Create a user
    * */
    @PostMapping
    public Map createUser(@RequestBody Map<String, Object> params, HttpServletResponse response){
        User newUser = JsonUtil.toUser(params);
        userRepository.save(newUser);
        response.setStatus(HttpStatus.CREATED.value()); //201
        return JsonUtil.userToPayLoad(newUser);
    } // createUser
}
