package com.example.test2.controller;

import com.example.test2.entity.Course;
import com.example.test2.entity.User;
import com.example.test2.repositories.courseRepo;
import com.example.test2.repositories.userRepo;
import com.example.test2.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("userFromDb")  // Store userFromDb in session
public class LoginController {

    @Autowired
    private userRepo userRepo;

    @Autowired
    private courseRepo courseRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Login page routing
    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);  // Save method will generate the ID if the entity is correctly configured
        return "redirect:/";
    }

    // Credential matching with database and home page routing based on role
    @PostMapping("/userLogin")
    public String loginUser(@ModelAttribute("user") User user, Model model) {
        User userFromDb = userRepo.findByUserName(user.getUserName());
        if (userFromDb != null) {
            if (passwordEncoder.matches(user.getPassword(), userFromDb.getPassword())) {
                model.addAttribute("userFromDb", userFromDb);
                if (userFromDb.getSecurityAnswer1() == null || userFromDb.getSecurityAnswer2() == null ||
                        userFromDb.getSecurityAnswer1().isEmpty() || userFromDb.getSecurityAnswer2().isEmpty()) {
                    // Redirect to security questions setup if answers are not set
                    return "redirect:/securityQuestions";
                } else {
                    // Redirect to validation page if answers are set
                    return "redirect:/validateSecurityAnswers";
                }
            } else {
                model.addAttribute("message", "Incorrect password!");
                return "login"; // Return the login view to display the message
            }
        } else {
            model.addAttribute("message", "User not found!");
            return "login"; // Return the login view to display the message
        }
    }

    @GetMapping("/securityQuestions")
    public String securityQuestions(Model model) {
        model.addAttribute("user", new User());
        return "securityQuestions";
    }

    // Save security answers
    @PostMapping("/securityQuestions")
    public String securityQuestions(@ModelAttribute("userFromDb") User userFromDb,
                                    @ModelAttribute("user") User user,
                                    Model model) {
        if (userFromDb != null) {
            userFromDb.setSecurityAnswer1(EncryptionUtil.encrypt(user.getSecurityAnswer1()));
            userFromDb.setSecurityAnswer2(EncryptionUtil.encrypt(user.getSecurityAnswer2()));
            userRepo.save(userFromDb);
            model.addAttribute("userFromDb", userFromDb);
            List<Course> courses = courseRepo.findAll();
            model.addAttribute("courses", courses);
            return "redirect:/";
        } else {
            // Handle scenario where userFromDb is null
            System.out.println("Error saving security answers! User not found in DB.");
            return "error"; // Consider redirecting to an error page
        }
    }

    @GetMapping("/validateSecurityAnswers")
    public String showValidateSecurityAnswersForm(Model model) {
        model.addAttribute("user", new User());
        return "validateSecurityAnswers";
    }

    // Validate security answers
    @PostMapping("/validateSecurityAnswers")
    public String validateSecurityAnswers(@SessionAttribute("userFromDb") User userFromDb,
                                          @ModelAttribute("user") User user,
                                          Model model) {
        if (userFromDb != null) {
            String decryptedAnswer1 = EncryptionUtil.decrypt(userFromDb.getSecurityAnswer1());
            String decryptedAnswer2 = EncryptionUtil.decrypt(userFromDb.getSecurityAnswer2());
            boolean answersMatch = decryptedAnswer1.trim().equalsIgnoreCase(user.getSecurityAnswer1().trim()) &&
                    decryptedAnswer2.trim().equalsIgnoreCase(user.getSecurityAnswer2().trim());

            System.out.println(user.getSecurityAnswer2());
            System.out.println(user.getSecurityAnswer1());
            System.out.println(decryptedAnswer1);
            System.out.println(decryptedAnswer2);
            if (answersMatch) {
                // Redirect based on user role
                System.out.println("Both answers are matched you can go the view page");
                model.addAttribute("userFromDb", userFromDb);
                if ("admin".equals(userFromDb.getUserRole())) {
                    return "redirect:/admin";
                } else {
                    return "redirect:/home";
                }
            } else {
                model.addAttribute("message", "Security answers are incorrect. Please try again.");
                return "validateSecurityAnswers"; // Return the same view to show the message
            }
        } else {
            System.out.println("User not found during validation!");
            return "error"; // Consider redirecting to an error page
        }
    }

    // Admin page routing
    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        List<Course> courses = courseRepo.findAll();
        model.addAttribute("courses", courses);
        model.addAttribute("course", new Course());
        return "admin";
    }

    @GetMapping("/addNewCourse")
    public String addNewCourse1(@ModelAttribute("course") Course course, Model model) {
        return "addNewCourse";
    }

    @PostMapping("/addNewCourse")
    public String addNewCourse(@ModelAttribute("course") Course course, Model model) {
        courseRepo.save(course);
        List<Course> courses = courseRepo.findAll();
        model.addAttribute("courses", courses);
        return "admin";
    }

    // Home page routing
    @GetMapping("/home")
    public String viewCourses(Model model) {
        List<Course> courses = courseRepo.findAll();
        model.addAttribute("courses", courses);
        return "home";
    }

    // Add course routing
    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute("course") Course course, Model model) {
        courseRepo.save(course);
        List<Course> courses = courseRepo.findAll();
        model.addAttribute("courses", courses);
        return "admin";
    }

    // Update course routing
    @GetMapping("/updateCourse/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model) {
        Optional<Course> optionalCourse = courseRepo.findById(id);
        if (optionalCourse.isPresent()) {
            model.addAttribute("course", optionalCourse.get());
            return "updateSuccess";
        } else {
            model.addAttribute("message", "Course not found.");
            return "error";
        }
    }

    @PostMapping("/updateCourse/{id}")
    public String updateCourse(@PathVariable("id") long id,
                               @ModelAttribute("course") Course course,
                               Model model) {
        Optional<Course> optionalCourse = courseRepo.findById(id);
        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            existingCourse.setCourseName(course.getCourseName());
            existingCourse.setCourseTime(course.getCourseTime());
            existingCourse.setCategory(course.getCategory());
            existingCourse.setDescription(course.getDescription());
            courseRepo.save(existingCourse);
            List<Course> courses = courseRepo.findAll();
            model.addAttribute("courses", courses);
            return "redirect:/admin";
        } else {
            return "error";
        }
    }

    // Delete course routing
    @PostMapping("/deleteCourse")
    public String deleteCourse(@RequestParam Long courseId, Model model) {
        courseRepo.deleteById(courseId);
        List<Course> courses = courseRepo.findAll();
        model.addAttribute("courses", courses);
        return "redirect:/admin";
    }

//     Filter routing
    @GetMapping("/filterCourses")
    public String filterCourses(@RequestParam String category, Model model) {
        List<Course> courses = courseRepo.findByCategory(category);
        model.addAttribute("courses", courses);
        return "course";
    }

//    @GetMapping("/filterCourses")
//    public String filterCourses(@RequestParam(value = "category", required = false) String category,
//                                @RequestParam(value = "keyword", required = false) String keyword, Model model) {
//        List<Course> courses=courseRepo.findAll();
//
//        if ((category != null && !category.isEmpty()) && (keyword != null && !keyword.isEmpty())) {
//            courses = courseRepo.findByCategoryAndCourseNameContainingIgnoreCase(category, keyword);
//        } else if (category != null && !category.isEmpty()) {
//            courses = courseRepo.findByCategory(category);
//        } else if (keyword != null && !keyword.isEmpty()) {
//            courses = courseRepo.findByCourseNameContainingIgnoreCase(keyword);
//        } else {
//            courses = courseRepo.findAll();
//        }
//        model.addAttribute("courses", courses);
//        return "home";
//    }
}
