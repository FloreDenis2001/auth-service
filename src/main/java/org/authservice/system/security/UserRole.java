package org.authservice.system.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum UserRole
{
    ADMIN(Sets.newHashSet(UserPermission.USER_READ, UserPermission.USER_WRITE));

    private final Set<UserPermission> permissions;

    public Set<UserPermission> getPermissions()
    {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities()
    {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }


}
