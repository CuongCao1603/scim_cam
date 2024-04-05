package com.example.scim_cam.controller.api.scim;

import com.example.scim_cam.model.Group;
import com.example.scim_cam.model.User;
import com.example.scim_cam.model.scim.ScimListResponse;
import com.example.scim_cam.model.scim.ScimOktaIceUser;
import com.example.scim_cam.model.scim.ScimUser;
import com.example.scim_cam.model.scim.ScimUserPatchOp;
import com.example.scim_cam.model.scim.support.ScimPageFilter;
import com.example.scim_cam.repository.UserRepository;
import com.example.scim_cam.service.ScimService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


import java.util.regex.Matcher;

import static com.example.scim_cam.service.ScimService.USERS_LOCATION_BASE;

@RestController
@ConditionalOnProperty(name = "scim.service", havingValue = "wip")
@RequestMapping(USERS_LOCATION_BASE)

public class ScimUserController extends ScimBaseController{

    private static final Logger logger = LoggerFactory.getLogger(ScimUserController.class);

    UserRepository userRepository;
    ScimService scimService;

    public ScimUserController(UserRepository userRepository, ScimService scimService) {
        this.userRepository = userRepository;
        this.scimService = scimService;
        logger.info("Using ScimUserController...");
    }

    /**
     * TODO: Implement the getUser method
     */
    @GetMapping("/{uuid}")
    public @ResponseBody ResponseEntity<ScimOktaIceUser> getUser(@PathVariable String uuid) {
        User user = userRepository.findOneByUuid(uuid);
        if (user == null) {
            // Nếu không tìm thấy User, trả về 404 Not Found
            return ResponseEntity.notFound().build();
        }

        // Chuyển đổi User thành ScimOktaIceUser
        ScimOktaIceUser scimUser = scimService.userToScimOktaIceUser(user);

        // Trả về ScimOktaIceUser với 200 OK
        return ResponseEntity.ok(scimUser);
    }

    @GetMapping
    public @ResponseBody ScimListResponse getUsers(@ModelAttribute ScimPageFilter scimPageFilter) {
        // Prepare pagination request. Uncomment the following line if sorting is needed.
        PageRequest pageRequest = PageRequest.of(
                scimPageFilter.getStartIndex() - 1,
                scimPageFilter.getCount()
                // , Sort.by(Sort.Direction.ASC, "userName") // Uncomment and adjust if sorting is needed
        );

        Page<User> users;

        // Parse search filter
        Matcher match = scimPageFilter.parseFilter();
        if (match.find()) {
            String searchKeyName = match.group(1);
            String searchValue = match.group(2);
            // Apply the filter based on the search key
            switch (searchKeyName) {
                case ScimPageFilter.USER_USERNAME:
                    users = userRepository.findByUsername(searchValue, pageRequest);
                    break;
                case ScimPageFilter.USER_ACTIVE:
                    users = userRepository.findByActive(Boolean.valueOf(searchValue), pageRequest);
                    break;
                case ScimPageFilter.USER_FIRST_NAME:
                    users = userRepository.findByFirstName(searchValue, pageRequest);
                    break;
                case ScimPageFilter.USER_LAST_NAME:
                    users = userRepository.findByLastName(searchValue, pageRequest);
                    break;
                default:
                    throw new HttpClientErrorException(HttpStatus.NOT_IMPLEMENTED, "Filter not implemented");
            }
        } else {
            // Find all users if there's no filter
            users = userRepository.findAll(pageRequest);
        }

        // Convert Page<User> to ScimListResponse
        return scimService.usersToListResponse(users.getContent(), scimPageFilter.getStartIndex(), scimPageFilter.getCount());
    }



    /**
     * TODO: Implement the createUser method
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ScimUser createUser(@RequestBody ScimUser scimUser) {
        //This is the line to delete
        return new ScimOktaIceUser();
        //Get new user's information

        //Save new user to DB

        //Returns the user information and convert it to a SCIM User

    }

    /**
     * TODO: Implement the replaceUser method
     */
    @PutMapping("/{uuid}")
    public ResponseEntity<ScimOktaIceUser> replaceUser(@RequestBody ScimUser scimUser, @PathVariable String uuid) {
        User existingUser = userRepository.findOneByUuid(uuid);
        if (existingUser == null) {
            // If the user cannot be found, return 404 Not Found
            return ResponseEntity.notFound().build();
        }

        // Convert the SCIM User to a Repository User format (this step might need customization)
        User userWithUpdates = scimService.scimUserToUser(scimUser);

        // Copy attribute values from userWithUpdates to the existing Repository User
        copyUser(userWithUpdates, existingUser);

        // Save the updated user to the database
        User updatedUser = userRepository.save(existingUser);

        // Convert the updated User entity to a ScimOktaIceUser and return it
        ScimOktaIceUser scimOktaIceUser = scimService.userToScimOktaIceUser(updatedUser);

        return ResponseEntity.ok(scimOktaIceUser);
    }

    /**
     * The copyUser method takes in two Repository Users.
     * It copy information from the first Repository User (from) to the second Repository User (to)
     */
    private void copyUser(User from, User to) {
        Assert.notNull(from, "From User cannot be null");
        Assert.notNull(to, "To User cannot be null");

        to.setActive(from.getActive());
        to.setUserName(from.getUserName());

        to.setEmail(from.getEmail());

        to.setLastName(from.getLastName());
        to.setMiddleName(from.getMiddleName());
        to.setFirstName(from.getFirstName());

        to.setCostCenter(from.getCostCenter());
        to.setEmployeeNumber(from.getEmployeeNumber());

        to.setFavoriteIceCream(from.getFavoriteIceCream());
    }

    /**
     * TODO: Implement the updateUser method
     */
    @SuppressWarnings("unchecked")
    @PatchMapping("/{uuid}")
    public @ResponseBody ScimOktaIceUser updateUser(
            @RequestBody ScimUserPatchOp scimUserPatchOp, @PathVariable String uuid
    ) {
        //This is the line to delete
        return new ScimOktaIceUser();

        //Confirm that the ScimUserPatchOp is valid.

        //Finds the Repository User by uuid

        //If cannot find the user, returns "Resource not found" error message.


        // Do Patch Op (only active flag supported currently)


        //Return the updated user information and convert it to a SCIM User

    }

    /**
     * TODO: Review the deleteUser method
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String uuid) {
        User user = userRepository.findOneByUuid(uuid);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Resource not found");
        }

        //remove user from groups
        for (Group g : user.getGroups()) {
            g.getUsers().remove(user);
        }

        //delete user
        userRepository.delete(user);
    }
}