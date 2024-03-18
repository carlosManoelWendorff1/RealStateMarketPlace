package PACV.MarketPlace.RealState.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PACV.MarketPlace.RealState.Models.Property;
import PACV.MarketPlace.RealState.Repositories.PropertyRepository;

@Service
public class PropertyService{

    @Autowired
    PropertyRepository propertyRepository;
    
    public List<Property> getAllProperties(){
        return  (List<Property>) propertyRepository.findAll();
    }

    
}
