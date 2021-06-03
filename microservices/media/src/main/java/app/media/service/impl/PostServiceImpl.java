package app.media.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.media.repository.PostRepository;
import app.media.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


}
