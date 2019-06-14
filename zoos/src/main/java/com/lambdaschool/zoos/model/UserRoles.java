package com.lambdaschool.zoos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "userroles")
public class UserRoles extends Auditable implements Serializable {
    // this is us manually creating a many to many relationship
    // this will not have its own id, it will be a combo so it implements serializable.

    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("userRoles")
    // 1 -> 2 many relationship needed
    private User user;
    // select the user from the model., its type is going to be of type user.

    @Id
    @ManyToOne
    @JoinColumn(name = "roleid")
    @JsonIgnoreProperties("userRoles")
    private Role role;

    // generated code
    public UserRoles(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    // generated getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // because we implimented serializable we need overrrides
    // generated code, you fucking ijit

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoles)) return false;
        UserRoles userRoles = (UserRoles) o;
        return getUser().equals(userRoles.getUser()) &&
                getRole().equals(userRoles.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getRole());
    }
}
