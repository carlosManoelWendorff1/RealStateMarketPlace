package PACV.MarketPlace.RealState.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import PACV.MarketPlace.RealState.Repositories.UserRepository;
import PACV.MarketPlace.RealState.Models.User;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    
     public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public HttpStatus updateUser(User user){
        if(userRepository.existsById(user.getId())){
            Optional<User> oldUserOptional = userRepository.findById(user.getId());
            if(oldUserOptional.isPresent()){
                User oldUser = oldUserOptional.get();
                if(!oldUser.toString().equals(user.toString())){
                    this.userRepository.save(user);
                    if(userRepository.existsById(user.getId())){
                        return HttpStatus.OK;
                    }
                }   
                else{
                    return HttpStatus.NOT_MODIFIED;
                }
            }
        }
        return HttpStatus.NOT_FOUND;
    }

    public HttpStatus deleteUser(Long id) {
        if(userRepository.existsById(id)){
            Optional<User> oldUserOptional = userRepository.findById(id);
            if(oldUserOptional.isPresent()){
                userRepository.deleteById(id);
                if(!userRepository.existsById(id)){
                        return HttpStatus.OK;
                }   
                else{
                    return HttpStatus.NOT_MODIFIED;
                }
            }
        }
        return HttpStatus.NOT_FOUND;
    }
}
