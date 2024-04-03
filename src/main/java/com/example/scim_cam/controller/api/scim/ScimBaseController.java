package com.example.scim_cam.controller.api.scim;

/*
* Handles errors and the SCIM configuration endpoints.
* The SCIM Configuration endpoints presents static JSON
* configuration files (under resource/scim-json) as output*/

import com.example.scim_cam.model.scim.ScimExceptionResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

@RestController
@RequestMapping("/scim/v2")
public class ScimBaseController {

    /*
    * TODO: review the handleException method
    *  Handle exceptions in SCIM format*/
    @ExceptionHandler(Exception.class)
    public ScimExceptionResponse handleException(Exception e, HttpServletResponse response){
        HttpStatusCode responseStatus = HttpStatus.NOT_ACCEPTABLE;
        if(e instanceof HttpStatusCodeException){
            responseStatus =  ((HttpStatusCodeException) e).getStatusCode();
        }
        response.setStatus(responseStatus.value());
        return new ScimExceptionResponse(e.getMessage(), responseStatus.toString());
    }

    /*
    * Return the features supported by the ICE Research SCIM API
    * The ICE Research API support patchop, but does not support bulk operation*/
    @GetMapping(value = "ServiceProviderConfig")
    public ResponseEntity<InputStreamResource> getServiceProviderConfig() throws IOException {
        return getResourceJsonFile("/scim-json/ServiceProviderConfig.json");
    }//getServiceProviderConfig


    /*
    * Helper method that read JSON files for the configguration endpoints*/
    private ResponseEntity<InputStreamResource> getResourceJsonFile(String filename) throws IOException {
        ClassPathResource jsonFile = new ClassPathResource(filename);

        return ResponseEntity
                .ok()
                .contentLength(jsonFile.contentLength())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new InputStreamResource(jsonFile.getInputStream()));
    }

}
