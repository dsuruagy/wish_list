package com.dsuruagy.wishlist.repository;

import com.dsuruagy.wishlist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
