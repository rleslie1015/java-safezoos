package com.lambdaschool.zoos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// User is considered the parent entity
//

@Entity
@Table(name = "users")
// this gets called users here and users in the database
public class User extends Auditable {

    // the user class has a user id
    // user name
    // password (encrypted) with bcrypto

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Allows the server to auto generate the id (basically id++)
    private long userid;

    @Column(nullable = false, unique = true)
    // nullable = false // this means the value cant be null
    // unique = true // means that the value cant be duplicated or in use elsewhere
    private String username;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // we need to make it so the password doesnt get printed as json
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<UserRoles> userRoles = new ArrayList<>();

    // generated shit

    public User() {
    }

    // hand written

    public User(String username, String password, List<UserRoles> userRoles)
    {
        // we want to use setters to set this
        setUsername(username);
        setPassword(password);

        // loop & add user roles

        for (UserRoles ur : userRoles )
        {
            ur.setUser(this);
        }
        this.userRoles = userRoles;
    }

    // generated

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // manually edited
    // we encrypt it here.
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    // more generated code

    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }

    // Another manual thing
    // theres diff types of simplegrantedauth but this one works ery well for what we want to do
    // what we want to do is make a list of the type simplegrantedauthority which is built into spring security
    // this is the thing that allows our roles to truly work.
    // this is what spring wants

    public List<SimpleGrantedAuthority> getAuthority(){
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();
        for (UserRoles r : this.userRoles)
        {
            String myRole = "ROLE_" + r.getRole().getName().toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }
        return rtnList;
    }
}
