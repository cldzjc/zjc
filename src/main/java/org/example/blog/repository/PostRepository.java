package org.example.blog.repository;

import org.example.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 最新在上
    List<Post> findAllByOrderByCreateTimeDesc();
}