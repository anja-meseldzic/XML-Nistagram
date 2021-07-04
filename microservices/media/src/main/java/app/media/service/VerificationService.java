package app.media.service;

public interface VerificationService {
    boolean verifyPostLink(Long postId, String username);

    boolean verifyStoryLink(String author, String username);
}
