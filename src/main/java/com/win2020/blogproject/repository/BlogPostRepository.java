package com.win2020.blogproject.repository;

import com.win2020.blogproject.model.BlogPost;
import org.springframework.data.repository.CrudRepository;

public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {

}
