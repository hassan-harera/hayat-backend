package com.harera.hayat.authorization.model.auth;



import org.springframework.security.core.GrantedAuthority;

import com.harera.hayat.authorization.model.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_authorities")
@IdClass(UserAuthorityId.class)
public class UserAuthority implements GrantedAuthority {

    @Id
    @Column(name = "authority")
    private String authority;

    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public String getAuthority() {
        return authority;
    }
}
