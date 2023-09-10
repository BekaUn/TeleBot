package com.project.wecare.repository;

import com.project.wecare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findByChatId(long chatId);
    Boolean existsByChatId(long cheatId);

}
