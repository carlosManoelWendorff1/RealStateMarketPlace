package PACV.MarketPlace.RealState.Repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import PACV.MarketPlace.RealState.Models.Property;
import java.util.List;


public interface PropertyRepository extends CrudRepository<Property,Long>, JpaSpecificationExecutor<Property> {

    List<Property> findByTitle(String title);
    List<Property> findByTitleContaining(String title);

    
} 

