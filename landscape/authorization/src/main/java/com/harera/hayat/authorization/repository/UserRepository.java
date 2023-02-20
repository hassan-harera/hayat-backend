package com.harera.hayat.authorization.repository;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.harera.hayat.authorization.model.user.AuthUser;

@Primary
@Repository
public interface UserRepository extends JpaRepository<AuthUser, Long> {

    boolean existsByMobile(String subject);

    boolean existsByEmail(String subject);

    boolean existsByUsername(String subject);

    Optional<AuthUser> findByMobile(String subject);

    Optional<AuthUser> findByEmail(String subject);

    Optional<AuthUser> findByUsername(String subject);

    boolean existsByUid(String uid);

    @Query("select u from AuthUser u where u.uid = :uid")
    Optional<AuthUser> findByUid(@Param("uid") String uid);
}
