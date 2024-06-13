package PACV.MarketPlace.RealState.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import PACV.MarketPlace.RealState.Dto.UserRegisterDto;
import PACV.MarketPlace.RealState.Models.User;
import PACV.MarketPlace.RealState.Services.SessionService;
import PACV.MarketPlace.RealState.Services.UserService;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private SessionService userCredentialsService;
    
    @Autowired
    UserService userService;

     @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public User getOneUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("info")
    public ResponseEntity<User> userInfo(@RequestHeader String authorization) {
        return userCredentialsService.getUserInfo(authorization);
    }
    @PostMapping("register")
    public ResponseEntity<String> postUser(@RequestBody UserRegisterDto user) {        
        return userCredentialsService.registerUser(user);
    }

    @PutMapping("")
    public HttpStatus putUser(@RequestBody User user) {        
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteUser(@PathVariable Long id) {        
        return userService.deleteUser(id);
    }
}
