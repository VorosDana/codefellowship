package com.example.codefellowship.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowDatabase extends JpaRepository<UserFollow, Long> {
}
