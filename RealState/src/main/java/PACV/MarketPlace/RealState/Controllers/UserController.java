package PACV.MarketPlace.RealState.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import PACV.MarketPlace.RealState.Models.User;
import PACV.MarketPlace.RealState.Services.UserService;

import java.util.List;


@RestController
@RequestMapping("/user/")
public class UserController {
    
    @Autowired
    UserService userService;

     @GetMapping("all")
    public List<User> getAllProperties() {
        return userService.getAllUsers();
    }

    @GetMapping("oneById")
    public User getOnePropertyById(@RequestParam Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("one")
    public User postProperty(@RequestBody User Property) {        
        return userService.addUser(Property);
    }

    @PutMapping("one")
    public HttpStatus putProperty(@RequestBody User Property) {        
        return userService.updateUser(Property);
    }

    @DeleteMapping("one/{id}")
    public HttpStatus deleteProperty(@PathVariable Long id) {        
        return userService.deleteUser(id);
    }
}
