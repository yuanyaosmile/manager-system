package org.example.yy.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleInfo {

    private String userId;
    private String accountName;
    private Role role;
}
