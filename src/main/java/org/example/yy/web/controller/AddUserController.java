package org.example.yy.web.controller;

import lombok.RequiredArgsConstructor;
import org.example.yy.config.Access;
import org.example.yy.model.AccessInfo;
import org.example.yy.service.AccessService;
import org.example.yy.web.api.Role;
import org.example.yy.web.api.UserInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddUserController {

    private final AccessService accessService;

    //not fit for restful style
    @PostMapping("/admin/addUser")
    @Access(roles = Role.ADMIN)
    public ResponseEntity<UserInfo> commit(@RequestBody UserInfo userInfo) {
        AccessInfo accessInfo = accessService.addUser(userInfo);
        return ResponseEntity.ok(accessInfo.to());
    }
}
