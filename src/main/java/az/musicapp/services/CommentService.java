package az.musicapp.services;

import az.musicapp.domain.Comment;
import az.musicapp.domain.User;

public interface CommentService {


    Comment findCommentById(Long id);

    Iterable<Comment> findCommentsBySongId(Long id);

    Comment saveComment(Long id, String content, User user);

    Comment updateComment(Long id, String content, User user);

    void removeComment(Long id, User user);
}

