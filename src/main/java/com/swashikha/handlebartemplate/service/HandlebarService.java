package com.swashikha.handlebartemplate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.context.MethodValueResolver;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class HandlebarService {

    public String hydrateTemplate(String templateName, String sourceData) {
        TemplateLoader templateLoader = new ClassPathTemplateLoader("/templates", ".json");
        Handlebars handlebars = new Handlebars(templateLoader);

        handlebars.registerHelper("json", Jackson2Helper.INSTANCE);

        JsonNode jsonNode = null;
        String rsp = null;

        try {
            jsonNode = new ObjectMapper().readValue(new FileReader("src/main/resources/"+sourceData+".json"), JsonNode.class);

            Context context = Context
                    .newBuilder(jsonNode)
                    .resolver(JsonNodeValueResolver.INSTANCE,
                            JavaBeanValueResolver.INSTANCE,
                            FieldValueResolver.INSTANCE,
                            MapValueResolver.INSTANCE,
                            MethodValueResolver.INSTANCE
                    )
                    .build();

            Template template = handlebars.compile(templateName);
            rsp = template.apply(context);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsp;
    }
}
