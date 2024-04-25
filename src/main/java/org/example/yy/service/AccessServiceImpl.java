package org.example.yy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.yy.data.repository.UserRepository;
import org.example.yy.exception.AccessException;
import org.example.yy.exception.ErrorCode;
import org.example.yy.model.AccessInfo;
import org.example.yy.web.api.UserInfo;
import org.springframework.stereotype.Component;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {

    private final UserRepository userRepository;

    @Override
    public AccessInfo addUser(UserInfo userInfo) {
        var accessInfo = AccessInfo.from(userInfo);
       return userRepository.saveUser(accessInfo);
    }

    @Override
    public String getResource(String userId, String resource) {
        AccessInfo accessInfo = userRepository.getResource(userId);
        if (accessInfo == null) {
            log.error("{} not found.", kv("userId", userId));
            throw new AccessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        boolean isContainsed = accessInfo.getEndpoints().contains(resource);

        if (isContainsed) {
            return "user " + userId  + " access resource " +  resource +" success.";
        } else {
            log.error("{}, {} not found.", kv("userId", userId), kv("resource", resource));
            throw new AccessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
    }

}
