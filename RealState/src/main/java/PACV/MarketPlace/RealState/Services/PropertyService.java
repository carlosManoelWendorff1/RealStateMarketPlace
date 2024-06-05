package PACV.MarketPlace.RealState.Services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import PACV.MarketPlace.RealState.Models.Location;
import PACV.MarketPlace.RealState.Models.Property;
import PACV.MarketPlace.RealState.Models.User;
import PACV.MarketPlace.RealState.Repositories.LocationRepository;
import PACV.MarketPlace.RealState.Repositories.PropertyRepository;
import PACV.MarketPlace.RealState.Repositories.UserRepository;
import PACV.MarketPlace.RealState.utils.PropertySpecification;

import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

@Service
public class PropertyService{
    private static Logger logger = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionService sessionService;

    public List<Property> getAllProperties(){
        return  (List<Property>) propertyRepository.findAll();
    }

    public List<Property> getPropertyByTittle(String propertyName) {
        return  propertyRepository.findByTitle(propertyName);
    }

    public List<Property> getAllPropertiesLike(String propertyName) {
        return  propertyRepository.findByTitleContaining(propertyName);
    }

    public List<Property> searchProperties(String title, String description, Double price, Long size, String ownerName, String city) {
        Specification<Property> spec = Specification.where(null);

        if (title != null && !title.isEmpty()) {
            spec = spec.and(PropertySpecification.hasTitle(title));
        }
        if (description != null && !description.isEmpty()) {
            spec = spec.and(PropertySpecification.hasDescription(description));
        }
        if (price != null) {
            spec = spec.and(PropertySpecification.hasPrice(price));
        }
        if (size != null) {
            spec = spec.and(PropertySpecification.hasSize(size));
        }
        if (ownerName != null && !ownerName.isEmpty()) {
            spec = spec.and(PropertySpecification.hasOwnerName(ownerName));
        }
        if (city != null && !city.isEmpty()) {
            spec = spec.and(PropertySpecification.hasLocationCity(city));
        }

        return propertyRepository.findAll(spec);
    }

    public Optional<Property> getPropertyById(Long id) {
        return  propertyRepository.findById(id);
    }

    public HttpStatus setProperty(Property property, String token) {
        Location location = property.getLocation();
        User owner = property.getOwner();
        
        ResponseEntity<Map<String, Object>> introspectResponse = sessionService.getIntrospect(token);
        if (introspectResponse.getStatusCode().is2xxSuccessful() && introspectResponse.getBody() != null) {
            Map<String, Object> tokenData = introspectResponse.getBody();
            String username = (String) tokenData.get("username");
            
            if (username != null) {
                owner = userRepository.findByFullName(username);
                property.setOwner(owner);
            } else {
                logger.error("Username not found in token introspect response");
                return HttpStatus.BAD_REQUEST;
            }
        } else {
            logger.error("Failed to introspect token");
            return (HttpStatus) introspectResponse.getStatusCode();
        }
        
        // Save the location if it's not already persisted
        if (location != null && location.getId() == 0) {
            location = locationRepository.save(location);
            property.setLocation(location);
        }
        
        if (owner != null && owner.getId() == 0) {
            owner = userRepository.save(owner);
            property.setOwner(owner);
        }
        
        propertyRepository.save(property);
        
        if (propertyRepository.existsById(property.getId())) {
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
