package com.example.sample.repositories

import com.example.sample.entities.Article
import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long> {
    fun findByTitle(title: String): Article?
    fun findAllByOrderByAddedAtDesc() : Iterable<Article>
}