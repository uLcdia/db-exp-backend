package com.dbexp.db_experiment.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.dbexp.db_experiment.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT * FROM ForumUser WHERE user_id = :id")
    Optional<User> findById(long id);

    @Query("SELECT * FROM ForumUser WHERE username = :username")
    Optional<User> findByUsername(String username);

    @Query("SELECT * FROM ForumUser WHERE email = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(*) > 0 FROM ForumUser WHERE username = :username")
    boolean existsByUsername(String username);

    @Query("SELECT COUNT(*) > 0 FROM ForumUser WHERE email = :email")
    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE ForumUser SET username = :newUsername WHERE user_id = :userId")
    int updateUsername(Long userId, String newUsername);

    @Modifying
    @Query("UPDATE ForumUser SET password_hash = :newPasswordHash WHERE user_id = :userId")
    int updatePassword(Long userId, String newPasswordHash);

    @Modifying
    @Query("UPDATE ForumUser SET email = :newEmail WHERE user_id = :userId")
    int updateEmail(Long userId, String newEmail);

    @Modifying
    @Query("DELETE FROM ForumUser WHERE user_id = :userId")
    int deleteByUserId(Long userId);
}