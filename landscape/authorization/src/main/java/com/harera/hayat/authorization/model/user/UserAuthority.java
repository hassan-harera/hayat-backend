package com.harera.hayat.authorization.model.user;



import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "user_authorities")
@IdClass(UserAuthority.UserAuthorityId.class)
public class UserAuthority implements GrantedAuthority {

    @Id
    @Column(name = "authority")
    private String authority;

    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AuthUser user;

    @Override
    public String getAuthority() {
        return authority;
    }

    @Setter
    @Getter
    class UserAuthorityId implements Serializable {

        private String authority;
        private Long userId;
    }

}

