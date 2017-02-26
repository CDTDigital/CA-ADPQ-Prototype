package com.intimetec.crns.core.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.intimetec.crns.core.models.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);
    Optional<User> findOneByUserName(String userName);
    
    @Query(value="Select u from User where u.userId IN (Select distinct userId from UserLocations where zipCode = ?1)")
    Collection<User> findUserByZipCode(String zipCode);
}