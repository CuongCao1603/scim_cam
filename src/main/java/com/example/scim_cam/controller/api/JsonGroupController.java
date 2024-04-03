package com.example.scim_cam.controller.api;

import com.example.scim_cam.model.Group;
import com.example.scim_cam.repository.GroupRepository;
import com.example.scim_cam.repository.UserRepository;
import com.example.scim_cam.utils.JsonUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * REST API for group management
 */
@RestController
@RequestMapping("/api/v1/groups")
@AllArgsConstructor
public class JsonGroupController {
    GroupRepository groupRepository;
    UserRepository userRepository;

    /**
     * Return all groups
     * */
    @GetMapping
    public @ResponseBody Map getGroups(){
        return JsonUtil.groupsToPayload(groupRepository.findAll());
    } //getGroups

    /**
     * Create group while validating the membership ids
     * id's that don't match will be ignored
     */
    @PostMapping
    public @ResponseBody Map createGroup(@RequestBody Map<String, Object> params, HttpServletResponse response){
        Group newGroup = JsonUtil.toGroup(params, userRepository);
        groupRepository.save(newGroup);
        response.setStatus(201);
        return JsonUtil.groupToPayload(newGroup);
    } // createGroup
}
