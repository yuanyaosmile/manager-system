package org.example.yy.data.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.yy.model.AccessInfo;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Component
public class UserRepository {

    private final String ACCESS_FILE_PATH = "access-info.txt";
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public AccessInfo getResource(String userId) {
        Map<String, org.example.yy.data.entity.AccessInfo> accessInfoMap = readUsersFromFile(ACCESS_FILE_PATH);
        org.example.yy.data.entity.AccessInfo accessInfo = accessInfoMap.get(userId);
        return  accessInfo == null ? null : accessInfo.to();
    }

    public AccessInfo saveUser(AccessInfo accessInfo) {
        Map<String, org.example.yy.data.entity.AccessInfo> userInfoMap = readUsersFromFile(ACCESS_FILE_PATH);

        if (userInfoMap.containsKey(accessInfo.getUserId())) {
            log.info("access info already exists. {}", kv("userId", accessInfo.getUserId()));
            throw new RuntimeException("Access info already exists.");
        }

        writeAccessInfo(org.example.yy.data.entity.AccessInfo.from(accessInfo), ACCESS_FILE_PATH);

        return accessInfo;
    }

    private Map<String, org.example.yy.data.entity.AccessInfo> readUsersFromFile(String fileName) {
        Map<String, org.example.yy.data.entity.AccessInfo> userInfoMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                org.example.yy.data.entity.AccessInfo accessInfo = OBJECT_MAPPER.readValue(line, org.example.yy.data.entity.AccessInfo.class);
                userInfoMap.put(accessInfo.getUserId(), accessInfo);
            }
        } catch (IOException exception) {
            log.info("can not get access info file: {}", fileName, exception);
            throw new RuntimeException("can not get access info file");
        }

        return userInfoMap;
    }

    private synchronized void writeAccessInfo(org.example.yy.data.entity.AccessInfo accessInfo, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(OBJECT_MAPPER.writeValueAsString(accessInfo));
            writer.write("\n");
            writer.flush();
        } catch (IOException exception) {
            log.error("save access info failed. {}", kv("userId", accessInfo), exception);
            throw new RuntimeException("save access info failed.");
        }
    }
}
