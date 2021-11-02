package az.musicapp.services.impl;

import az.musicapp.domain.Comment;
import az.musicapp.domain.Song;
import az.musicapp.domain.User;
import az.musicapp.exceptions.NotFoundException;
import az.musicapp.exceptions.UnauthorizedUserException;
import az.musicapp.repositories.CommentRepository;
import az.musicapp.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final SongServiceImpl songService;
    private final UserServiceImpl userService;

    public Comment findCommentById(Long id) {

        Optional<Comment> optionalComment =  commentRepository.findById(id);
    if (optionalComment.isPresent()){
       Comment comment =  optionalComment.get();
       return  comment;
    }else {
     throw new NotFoundException(String.format("Comment ID: %s does not exist", id));
    }




    }

    public Iterable<Comment> findCommentsBySongId(Long id) {

        Song song = songService.findSongById(id);
        return song.getComments();
    }

    public Comment saveComment(Long id, String content, User user) {

        user = userService.findUserById(user.getId());
        Song song = songService.findSongById(id);

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setSong(song);

        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, String content, User user) {

        user = userService.findUserById(user.getId());

        Comment comment = findCommentById(id);
        if (!comment.getUser().equals(user)) {
            throw new UnauthorizedUserException(String.format("User: %s cannot update this comment", user.getUsername()));
        }

        comment.setContent(content);
        return commentRepository.save(comment);
    }

    public void removeComment(Long id, User user) {

        user = userService.findUserById(user.getId());

        Comment comment = findCommentById(id);
        if (!comment.getUser().equals(user)) {
            throw new UnauthorizedUserException(String.format("User: %s cannot delete this comment", user.getUsername()));
        }


        commentRepository.deleteById(comment.getId());
    }
}
