package architecture.domain.entities.auth;

import architecture.domain.entities.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private UserRoles authority;

    public Role() {
    }

    public Role(UserRoles authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority.toString();
    }

    public void setAuthority(UserRoles authority) {
        this.authority = authority;
    }
}
