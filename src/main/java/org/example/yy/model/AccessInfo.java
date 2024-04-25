package org.example.yy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.yy.web.api.UserInfo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessInfo {

    private String userId;
    private String accountName;
    private List<String> endpoints;

    public static AccessInfo from(UserInfo userInfo) {
        return new AccessInfoBuilder()
                .userId(userInfo.getUserId())
                .endpoints(userInfo.getEndpoint())
                .build();
    }

    public UserInfo to() {
        return UserInfo.builder()
                .userId(this.userId)
                .endpoint(this.endpoints)
                .build();
    }
}
