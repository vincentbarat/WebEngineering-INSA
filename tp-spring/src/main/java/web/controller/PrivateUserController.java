package web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("api/v2/private/user")
@AllArgsConstructor
@CrossOrigin
public class PrivateUserController {

    private final UserService userService;

    @GetMapping()
    public String hello(final Principal user) {
        return user.getName();
    }
}

