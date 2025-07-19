package app.web.like.repositories;

import app.web.like.models.Post;
import app.web.like.models.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    Optional<UserLike> findByUserIdAndPost(Long userId, Post post);
}
