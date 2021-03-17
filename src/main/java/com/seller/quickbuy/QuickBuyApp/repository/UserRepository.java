package com.seller.quickbuy.QuickBuyApp.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
	User findByEmail(String email);
//	User findByUserName(String userName);
//    Collection<User> findAllByRole(String role);
//	User findByUsername(String username);

}