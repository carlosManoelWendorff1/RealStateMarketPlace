package PACV.MarketPlace.RealState.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import PACV.MarketPlace.RealState.Models.Property;
import PACV.MarketPlace.RealState.Repositories.PropertyRepository;

import java.util.Optional;

@Service
public class PropertyService{

    @Autowired
    PropertyRepository propertyRepository;
    
    public List<Property> getAllProperties(){
        return  (List<Property>) propertyRepository.findAll();
    }

    public List<Property> getPropertyByTittle(String propertyName) {
        return  propertyRepository.findByTittle(propertyName);
    }

    public Optional<Property> getPropertyById(Long id) {
        return  propertyRepository.findById(id);
    }

    public HttpStatus setProperty(Property property){
        this.propertyRepository.save(property);

        if(propertyRepository.existsById(property.getId())){
            return HttpStatus.CREATED;
        }
        return HttpStatus.BAD_REQUEST;
    }
    
    public HttpStatus removeProperty(Long id){
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        if(propertyOptional.isPresent()){
            this.propertyRepository.deleteById(propertyOptional.get().getId());
            if(!propertyRepository.existsById(propertyOptional.get().getId())){
                return HttpStatus.FOUND;
            }
        }
        return HttpStatus.NOT_FOUND;
    }

    public HttpStatus updateProperty(Property property){
        if(propertyRepository.existsById(property.getId())){
            Optional<Property> oldPropertyOptional = propertyRepository.findById(property.getId());
            if(oldPropertyOptional.isPresent()){
                Property oldProperty = oldPropertyOptional.get();
                if(!oldProperty.toString().equals(property.toString())){
                    this.propertyRepository.save(property);
                    if(propertyRepository.existsById(property.getId())){
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

    public List<Property> getAllPropertys() {
        return (List<Property>) propertyRepository.findAll();
    }


    
}
