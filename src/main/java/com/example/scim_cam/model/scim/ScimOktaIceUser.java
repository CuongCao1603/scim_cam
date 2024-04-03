package com.example.scim_cam.model.scim;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ScimOktaIceUser extends ScimEnterpriseUser{
    /**
     *  The ScimOktaIceUser class extends ScimEnterpriseUser class.
     *  It contains the SCHEMA_USER_OKTA_ICE string to store the custom schema for ICE Research.
     */
    public static final String SCHEMA_USER_OKTA_ICE = SCHEMA_BASE + ":extension:ice:2.0:User";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SCHEMA_USER_OKTA_ICE)
    private OktaIceAttributes oktaIceAttributes;

    public ScimOktaIceUser(){
        super();
        getSchemas().add(SCHEMA_USER_OKTA_ICE);
    }

    public static class OktaIceAttributes {
        private String iceCream;

        public String getIceCream() {
            return iceCream;
        }

        public void setIceCream(String iceCream) {
            this.iceCream = iceCream;
        }
    }

    public static void main(String[] args) {
        ScimUser scimUser = new ScimUser();
        System.out.println("ScimUser schemas: " + scimUser.getSchemas());

        ScimEnterpriseUser scimEnterpriseUser = new ScimEnterpriseUser();
        System.out.println("ScimEnterpriseUser schemas: " + scimEnterpriseUser.getSchemas());

        ScimOktaIceUser scimOktaIceUser = new ScimOktaIceUser();
        System.out.println("ScimOketaIceUser schemas: " + scimOktaIceUser.getSchemas());
    }
}
