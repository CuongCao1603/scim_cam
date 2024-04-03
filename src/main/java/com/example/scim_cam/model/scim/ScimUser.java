package com.example.scim_cam.model.scim;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@JsonPropertyOrder({"schemas", "id", "active", "userName","name","emails","groups","meta"})
public class ScimUser extends ScimResource{
    /**
     * The ScimUser class extends ScimResource class.
     * It contains the SCHEMA_USER_CORE string to store the Core Schema.
     */
    public static final String SCHEMA_USER_CORE = SCHEMA_BASE  + ":core:2.0:User";

    private boolean active;
    private String userName;
    private Name name;
    private List<Email> emails = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();

    public ScimUser(){
        super();
        getSchemas().add(SCHEMA_USER_CORE);
    }

    public static class Name {
        private String givenName;
        private String middleName;
        private String familyName;

        public String getGivenName() {
            return givenName;
        }

        public void setGivenName(String givenName) {
            this.givenName = givenName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }
    }

    public static class Email {

        private boolean primary;
        private String value;
        private String type;

        public boolean isPrimary() {
            return primary;
        }

        public void setPrimary(boolean primary) {
            this.primary = primary;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Group {

        private String display;
        private String value;

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
