package org.shancm.shirodemo.domain.entity;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String userName;
    private String password;
    /**
     * 用户对应的角色集合
     */
    private Set<Role> roleSet;

    public Set<String> getRoles() {
        Set<String> roles = new HashSet<>(8);

        if (CollectionUtils.isEmpty(roleSet)) {
            return null;
        }

        for (Role role : roleSet) {
            roles.add(role.getRoleName());
        }

        return roles;
    }

    public Set<String> getPermissions() {
        Set<String> permissions = new HashSet<>(8);

        if (CollectionUtils.isEmpty(roleSet)) {
            return null;
        }

        for (Role userRole : roleSet) {
            for (Permission userRolePermission : userRole.getPermissions()) {
                permissions.add(userRolePermission.getPermissionsName());
            }
        }
        return permissions;
    }

    public Set<Role> roleAdminister() {
        this.setUserName("administer");
        this.setPassword("123");

        Set<Role> roles = new HashSet<>(8);
        Set<Permission> permissions = new HashSet<>(8);
        permissions.add(new Permission("1", "write"));
        roles.add(new Role("1", "administer", permissions));
        return roles;
    }

    public Set<Role> roleManager() {
        this.setUserName("manager");
        this.setPassword("123");

        Set<Role> roles = new HashSet<>(8);
        Set<Permission> permissions = new HashSet<>(8);
        permissions.add(new Permission("2", "read"));
        roles.add(new Role("2", "manager", permissions));
        return roles;
    }



}
