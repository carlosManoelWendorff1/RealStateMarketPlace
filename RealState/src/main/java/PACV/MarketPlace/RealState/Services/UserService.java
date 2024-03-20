package PACV.MarketPlace.RealState.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PACV.MarketPlace.RealState.Repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    
}
