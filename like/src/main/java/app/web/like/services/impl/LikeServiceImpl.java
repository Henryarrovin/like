package app.web.like.services.impl;

import app.web.like.models.Post;
import app.web.like.models.UserLike;
import app.web.like.repositories.PostRepository;
import app.web.like.repositories.UserLikeRepository;
import app.web.like.services.LikeService;

import java.util.Optional;

public class LikeServiceImpl implements LikeService {

    private final PostRepository postRepository;
    private final UserLikeRepository  userLikeRepository;

    public LikeServiceImpl(PostRepository postRepository, UserLikeRepository userLikeRepository) {
        this.postRepository = postRepository;
        this.userLikeRepository = userLikeRepository;
    }

    @Override
    public void likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        boolean alreadyLiked = userLikeRepository.findByUserIdAndPost(userId, post).isPresent();
        if (alreadyLiked) return;

        // Save like
        UserLike like = new UserLike();
        like.setUserId(userId);
        like.setPost(post);
        userLikeRepository.save(like);

        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
    }

    @Override
    public void unlikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Optional<UserLike> likeOpt = userLikeRepository.findByUserIdAndPost(userId, post);
        if (likeOpt.isEmpty()) return;

        userLikeRepository.delete(likeOpt.get());
        post.setLikeCount(post.getLikeCount() - 1);
        postRepository.save(post);
    }
}
