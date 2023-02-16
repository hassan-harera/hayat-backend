package com.harera.hayat.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.authorization.model.auth.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}
