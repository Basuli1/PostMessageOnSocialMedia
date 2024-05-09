package com.example.SocialMedia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SocialMedia.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {

    List<Message> findByPostedBy(Integer accountId);
}