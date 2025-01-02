package com.example.bookshop.controllers;

import com.example.bookshop.annotations.CurrentUser;
import com.example.bookshop.exceptions.EmailExistsException;
import com.example.bookshop.models.User;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.services.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;


@Controller
public class PrincipleController {
    private static Logger LOG = LoggerFactory.getLogger(PrincipleController.class);

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/cabinet", method = RequestMethod.GET)
    public String currentUserName(@CurrentUser MyUserDetailsService.MyUserPrincipal userDetails, Model model) {
        LOG.info(String.valueOf(userDetails.getUser()));
        User user = userDetails.getUser();
        LOG.info("Principle id: " + user.getId());
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            model.addAttribute("principle", optionalUser.get());
        } else {
            LOG.error("User not found for id: " + user.getId());
            return "redirect:/error";
        }
        return "cabinet";
    }

    @RequestMapping("principle/edit/{id}")
    public String edit(@PathVariable Long id, Model model) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            model.addAttribute("principle", optionalUser.get());
            LOG.info("Find user id before edit: " + id);
        } else {
            LOG.error("User not found for id: " + id);
            throw new UsernameNotFoundException("User not found for ID: " + id);
        }
        return "principle/principleform";
    }


    @RequestMapping(value = "principle", method = RequestMethod.POST)
    public String updateUser(User user) throws UsernameNotFoundException, EmailExistsException {
        userRepository.save(user);
        LOG.info("Update user: " + user);
        return "redirect:/cabinet";
    }
}
