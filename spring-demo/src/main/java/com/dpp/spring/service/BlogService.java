package com.dpp.spring.service;

import com.dpp.spring.dto.Blog;

public class BlogService {

    private AuthorService authorService;

    public Blog getBlog(String blogId){
        Blog blog = new Blog();
        blog.setId(blogId);
        blog.setAuthorId("1");
        blog.setContext("Spring启动流程分析");
        blog.setAuthorName(authorService.getAuthorName(blog.getAuthorId()));
        return blog;
    }

    public AuthorService getAuthorService() {
        return authorService;
    }

    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }
}
