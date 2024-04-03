package com.example.scim_cam.model.scim;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ScimExceptionResponse extends ScimResource{
    //The ScimExceptionResponse objects has its own SCIM schema, with the identifier 'Error'
public final static String ERROR_SCHEMA =  "urn:ietf:params:scim:api:message:2.0:Error";

private String detail;
private String status;

public ScimExceptionResponse(){
    super();
    getSchemas().add(ERROR_SCHEMA);
}

}
