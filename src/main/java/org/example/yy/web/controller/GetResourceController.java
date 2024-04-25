package org.example.yy.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.yy.config.Access;
import org.example.yy.service.AccessService;
import org.example.yy.util.EncDecUtil;
import org.example.yy.web.api.Role;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j

public class GetResourceController {

    private final AccessService accessService;

    //roleInfo eyJ1c2VySWQiOiIxMjM0NTYiLCJhY2NvdW50TmFtZSI6Inh4eHgiLCJyb2xlIjoiQURNSU4ifQ
    @GetMapping("/user/{resource}")
    @Access(roles = {Role.ADMIN, Role.USER})
    public ResponseEntity<String> visit(@PathVariable String resource, @RequestHeader HttpHeaders headers) {
        List<String> roleInfo = headers.get("RoleInfo");
        if (CollectionUtils.isEmpty(roleInfo)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("role info not found.");
        }
        String encoded = roleInfo.getFirst();
        String userId = null;
        try {
            userId = EncDecUtil.getRoleInfo(encoded).getUserId();
        } catch (JsonProcessingException exception) {
            log.error("parse role info failed.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("parse role info failed.");
        }

        String result = accessService.getResource(userId, resource);
        return ResponseEntity.ok(result);
    }
}
