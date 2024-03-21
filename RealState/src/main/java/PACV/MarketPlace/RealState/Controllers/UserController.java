package PACV.MarketPlace.RealState.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import PACV.MarketPlace.RealState.Services.UserService;

@RestController
@RequestMapping("/user/")
public class UserController {
    
    @Autowired
    UserService userService;
}
