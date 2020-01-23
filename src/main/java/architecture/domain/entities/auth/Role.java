package architecture.domain.entities.auth;

import architecture.domain.entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private UserRoles role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public Role(UserRoles role) {
        this.role = role;
    }

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
