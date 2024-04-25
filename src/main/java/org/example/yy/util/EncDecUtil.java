package org.example.yy.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.yy.web.api.RoleInfo;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncDecUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static RoleInfo getRoleInfo(String authorization) throws JsonProcessingException {
        byte[] bytes = Base64.getDecoder().decode(authorization.getBytes(StandardCharsets.UTF_8));
        return OBJECT_MAPPER.readValue(new String(bytes), RoleInfo.class);
    }
}
