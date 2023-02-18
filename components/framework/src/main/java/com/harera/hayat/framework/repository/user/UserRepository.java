package com.harera.hayat.framework.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.harera.hayat.framework.model.user.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByMobile(String subject);

    boolean existsByEmail(String subject);

    boolean existsByUsername(String subject);

    Optional<User> findByMobile(String subject);

    Optional<User> findByEmail(String subject);

    Optional<User> findByUsername(String subject);

    boolean existsByUid(String uid);

    @Query("select u from User u where u.uid = :uid")
    Optional<User> findByUid(@Param("uid") String uid);
}
