package org.example.yy.service;

import org.example.yy.model.AccessInfo;
import org.example.yy.web.api.UserInfo;

public interface AccessService {

    AccessInfo addUser(UserInfo userInfo);

    String getResource(String userId, String resource);
}
