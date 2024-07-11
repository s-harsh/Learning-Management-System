package com.example.test2.controller;

import com.example.test2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.example.test2.repositories.userRepo;
import com.example.test2.util.EncryptionUtil;

@Controller
@SessionAttributes("userFromDb")
public class ForgotPasswordController
{
    @Autowired
    private userRepo userRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Login Page Routing
    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm(Model model){
        model.addAttribute("user",new User());
        return "forgotPassword";
    }

    // Process forgot Password form Submission
    @PostMapping("/forgotPassword")
    public String processForgotPassword(@ModelAttribute("user") User user, Model model){

        User userFromDb = userRepo.findByUserName(user.getUserName());
        if(userFromDb != null) {
            // Decrypt security answers from database
            String decryptedAnswer1 = EncryptionUtil.decrypt(userFromDb.getSecurityAnswer1());
            String decryptedAnswer2 = EncryptionUtil.decrypt(userFromDb.getSecurityAnswer2());

            // Compare decrypted answers with provided answers
            if(decryptedAnswer1.equals(user.getSecurityAnswer1()) &&
                    decryptedAnswer2.equals(user.getSecurityAnswer2())){
                model.addAttribute("user", userFromDb);
                return "resetPassword";
            }
        }

        model.addAttribute("message", "Invalid userName or security answers code. Please try again.");
        return "forgotPassword";
    }

    // save new password after reset
    @PostMapping("/resetPassword")
    public String resetPassword(@ModelAttribute("user") User user){
        User userToUpdate = userRepo.findByUserName(user.getUserName());
//        userToUpdate.setPassword(user.getPassword());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        userRepo.save(userToUpdate);
        return "redirect:/";
    }
}
