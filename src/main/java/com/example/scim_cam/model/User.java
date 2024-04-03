package com.example.scim_cam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User Entity that handles User operations in database
 */
@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name="tbl_users")
public class User {

    //BEGIN: USER ATTRIBUTES
    @Id
    @GeneratedValue
    private int id;
    @Column(unique=true)
    private String uuid;
    private Boolean active = false;
    @Column(unique=true)
    private String userName;
    @Column(unique=true)
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String favoriteIceCream;
    private String employeeNumber;
    private String costCenter;
    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private List<Group> groups = new ArrayList<>();
    //END: USER ATTRIBUTES

    //BEGIN: CONSTRUCTORS
    public User(){
        this.uuid = UUID.randomUUID().toString();
    }

    public User(String userName, String firstName, String middleName, String lastName,
                String email, String favoriteIceCream,
                String employeeNumber, String costCenter, boolean active) {
        this.uuid = UUID.randomUUID().toString();
        this.active = active;
        this.userName = (userName!= null) ? userName : (firstName + "." + lastName + "@oktaice.com").toLowerCase();
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.favoriteIceCream = favoriteIceCream;
        this.employeeNumber = employeeNumber;
        this.costCenter = costCenter;
    }

    @Override
    public String toString() {
        return "username: "+this.userName+ " | email: " +this.email;
    }//toString
    //END: GETTERS AND SETTERS

    /**
     * For unit tests
     */
    public static void main(String[] args) {
        User user = new User(null, "John", null, "Doe", "john.doe@oktaice.com", "Vanilla",
                "123", "C123", true);
        ObjectMapper mapper = new ObjectMapper();
//        try {
//            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
//                    JsonUtil.
//            )
//        }

    }//main
    //END: SUPPORTING METHODS

}
