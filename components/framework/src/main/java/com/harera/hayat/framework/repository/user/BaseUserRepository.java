package com.harera.hayat.framework.repository.user;

import com.harera.hayat.framework.model.user.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {

    @Query("SELECT u FROM BaseUser u WHERE u.mobile = :mobile")
    Optional<BaseUser> findByMobile(@Param("mobile") String mobile);
}