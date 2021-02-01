package org.shancm.shirodemo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Permission {
    private String id;
    private String permissionsName;
}