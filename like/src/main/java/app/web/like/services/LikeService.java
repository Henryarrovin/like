package app.web.like.services;

public interface LikeService {
    void likePost(Long postId, Long userId);
    void unlikePost(Long postId, Long userId);
}
