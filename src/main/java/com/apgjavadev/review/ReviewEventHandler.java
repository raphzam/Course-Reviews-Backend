package com.apgjavadev.review;

import com.apgjavadev.user.User;
import com.apgjavadev.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class ReviewEventHandler {

  private final UserRepository users;

  @Autowired
  public ReviewEventHandler(UserRepository userRepository) {
    users = userRepository;
  }

  @HandleBeforeCreate
  public void addReviewerBasedOnLoggedInUser(Review review) {
    String username = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getName();
    User user = users.findByUsername(username);
    review.setReviewer(user);
  }
}
