package PACV.MarketPlace.RealState.Repositories;

import org.springframework.data.repository.CrudRepository;

import PACV.MarketPlace.RealState.Models.User;
import java.util.List;


public interface UserRepository extends CrudRepository<User,Long> {
    
     User findByUserName(String userName);
    
}
