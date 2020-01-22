package architecture.domain.entities.auth;

import architecture.domain.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

//@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    private UserRoles role;
    private Set<User> users;

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
