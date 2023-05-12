package com.swashikha.handlebartemplate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swashikha.handlebartemplate.service.HandlebarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value="/hydrate")
public class HandlebarController {

    @Autowired
    HandlebarService handlebarService;

    @GetMapping(value = "/template")
    public JsonNode getHydratedData(@PathParam("templateName") String templateName, @PathParam("source") String source) throws JsonProcessingException {
       String str = handlebarService.hydrateTemplate(templateName,source);
       return new ObjectMapper().readTree(str);
    }
}
