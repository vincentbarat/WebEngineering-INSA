package web.controller;

import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.service.UserService;

@RestController
@RequestMapping("api/v2/public/user")
@AllArgsConstructor
@CrossOrigin
public class PublicUserController {

    private final UserService userService;

    @PostMapping(value = "new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void newAccount(@RequestBody final UserDTO user) {
        try {
            userService.newAccount(user.login(), user.pwd());
        } catch (final IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not possible");
        }
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void login(@RequestBody final UserDTO user) {
        try {
            final boolean loginSuccess = userService.login(user.login(), user.pwd());

            if (!loginSuccess) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already logged in. Log out first");
            }
        } catch (final ServletException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not possible to log in");
        }
    }
}

record UserDTO(String login, String pwd) {
}
