package org.example.yy.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessInfo {
    private String userId;
    private String accountName;
    private List<String> resource;

    public static AccessInfo from(org.example.yy.model.AccessInfo accessInfo) {
        return new AccessInfoBuilder()
                .userId(accessInfo.getUserId())
                .accountName(accessInfo.getAccountName())
                .resource(accessInfo.getEndpoints())
                .build();
    }

    public org.example.yy.model.AccessInfo to() {
        return org.example.yy.model.AccessInfo.builder()
                .userId(this.userId)
                .accountName(this.accountName)
                .endpoints(this.resource)
                .build();
    }
}
