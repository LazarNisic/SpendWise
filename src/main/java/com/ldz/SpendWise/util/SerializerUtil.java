package com.ldz.SpendWise.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.ldz.SpendWise.exception.UnknownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SerializerUtil {

    public String toJson(Object param) {
        try {
            ObjectMapper mapper = JsonMapper.builder()
                    .addModule(new ParameterNamesModule())
                    .addModule(new Jdk8Module())
                    .addModule(new JavaTimeModule())
                    .build();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(param);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    public String toXml(Object param) {
        try {
            var xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new JavaTimeModule());
            xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
            xmlMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return xmlMapper.writeValueAsString(param);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    public <T> T fromXml(String xml, Class<T> clazz) {
        try {
            var xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return xmlMapper.readValue(xml, clazz);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = JsonMapper.builder()
                    .addModule(new ParameterNamesModule())
                    .addModule(new Jdk8Module())
                    .addModule(new JavaTimeModule())
                    .build();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }
}
