package PACV.MarketPlace.RealState.Repositories;

import org.springframework.data.repository.CrudRepository;

import PACV.MarketPlace.RealState.Models.User;

public interface UserRepository extends CrudRepository<User,Long> {
    
    
}
