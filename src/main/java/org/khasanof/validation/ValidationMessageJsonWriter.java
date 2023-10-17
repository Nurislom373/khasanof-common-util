package org.khasanof.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;

import java.util.*;

/**
 * Author: Nurislom
 * <br/>
 * Date: 09.06.2023
 * <br/>
 * Time: 17:03
 * <br/>
 * Package: uz.devops.util
 */
public abstract class ValidationMessageJsonWriter {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static <T> String constraintViolationToString(Set<ConstraintViolation<T>> violations) {
        try {
            return mapToJson(constraintViolationToMap(violations));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Map<String, List<String>> constraintViolationToMap(Set<ConstraintViolation<T>> violations) {
        Map<String, List<String>> map = new HashMap<>();
        for (ConstraintViolation<?> m : violations) {
            String key = m.getPropertyPath().toString();
            if (map.containsKey(key)) {
                List<String> messages = map.get(key);
                messages.add(m.getMessage());
                map.put(key, messages);
            } else {
                map.put(key, new ArrayList<>() {{
                    add(m.getMessage());
                }});
            }
        }
        return map;
    }

    private static String mapToJson(Map<String, List<String>> map) throws JsonProcessingException {
        return objectMapper.writeValueAsString(map);
    }

}
