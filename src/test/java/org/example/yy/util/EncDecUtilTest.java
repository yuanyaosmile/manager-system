package org.example.yy.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.yy.web.api.Role;
import org.example.yy.web.api.RoleInfo;
import org.junit.Test;

import java.util.Base64;

public class EncDecUtilTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void getUserIdHappyPath() throws JsonProcessingException {
        RoleInfo roleInfo = new RoleInfo("5678", "xxxx", Role.ADMIN);
        String encoded = Base64.getEncoder().encodeToString(OBJECT_MAPPER.writeValueAsBytes(roleInfo));
        System.out.println(encoded);
        byte[] decoded = Base64.getDecoder().decode(encoded);
        RoleInfo decryption = OBJECT_MAPPER.readValue(new String(decoded), RoleInfo.class);
        System.out.println(decryption.getUserId());
    }
}
