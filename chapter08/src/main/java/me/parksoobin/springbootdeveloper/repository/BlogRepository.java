package me.parksoobin.springbootdeveloper.repository;

import me.parksoobin.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}