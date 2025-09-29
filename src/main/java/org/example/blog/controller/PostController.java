package org.example.blog.controller;

import org.example.blog.common.Result;
import org.example.blog.entity.Post;
import org.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    /**
     * 发贴
     * 登录后 JWT 过滤器会把用户名放入 Authentication
     */
    @PostMapping
    public Result<?> create(@RequestBody Post dto, Authentication auth) {
        Post p = new Post();
        p.setTitle(dto.getTitle());
        p.setContent(dto.getContent());
        p.setAuthor(auth.getName());   // 当前登录用户名
        postRepository.save(p);
        return new Result<>(200, "发布成功", null);
    }

    /**
     * 首页列表（最新在上）
     */
    @GetMapping
    public Result<List<Post>> list() {
        return new Result<>(200, "success", postRepository.findAllByOrderByCreateTimeDesc());
    }
    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Post dto, Authentication auth) {
        Post p = postRepository.findById(id).orElseThrow();
        if (!p.getAuthor().equals(auth.getName())) return Result.error("只能修改自己的帖");
        p.setTitle(dto.getTitle());
        p.setContent(dto.getContent());
        postRepository.save(p);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, Authentication auth) {
        Post p = postRepository.findById(id).orElseThrow();
        if (!p.getAuthor().equals(auth.getName())) return Result.error("只能删除自己的帖");
        postRepository.deleteById(id);
        return Result.ok();
    }

}