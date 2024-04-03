package com.example.scim_cam.model.scim;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScimListResponse extends ScimResource {
    //The ScimListResponse class has its own SCIM schema
    public static final String SCHEMA_LIST_RESPONE = "urn:ietf:params:scim:messages:2.0:ListResponse";

    //The ScimListResponse class contains meta information about the search that can be used for pagination.
    private Integer totalResults;
    private Integer startIndex;
    private Integer itemsPerPage;

    /**
     * The ScimListResponse serves as a wrapper for multiple SCIM Recourses.
     * For example, it can return a list of SCIM users or SCIM groups.
     */

    @JsonProperty("Resources")
    private List<ScimResource> resources = new ArrayList<>();
}
