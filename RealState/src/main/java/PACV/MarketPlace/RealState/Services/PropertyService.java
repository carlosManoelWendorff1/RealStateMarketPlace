package PACV.MarketPlace.RealState.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Property> getAllProperties(){
        return  (List<Property>) propertyRepository.findAll();
    }

    public List<Property> getPropertyByTittle(String propertyName) {
        return  propertyRepository.findByTitle(propertyName);
    }

    public List<Property> getAllPropertiesLike(String propertyName) {
        return  propertyRepository.findByTitleContaining(propertyName);
    }

    public List<Property> searchProperties(
            String title,
            String description,
            Double price,
            Long size,
            String fullName,
            String city,
            Property.Type type,
            Integer minBedrooms,
            Double minPrice,
            Double maxPrice,
            Long minSize,
            Long maxSize,
            Integer minBathrooms,
            List<Property.SellerType> sellerTypes
            //,List<String> amenities
            ) {

        Specification<Property> spec = Specification.where(null);

        if (title != null && !title.isEmpty()) {
            spec = spec.and(PropertySpecification.hasTitle(title));
        }

        if (description != null && !description.isEmpty()) {
            spec = spec.and(PropertySpecification.hasDescription(description));
        }

        if (price != null && price > 0) {
            spec = spec.and(PropertySpecification.hasPrice(price));
        }

        if (size != null && size > 0) {
            spec = spec.and(PropertySpecification.hasSize(size));
        }

        if (fullName != null && !fullName.isEmpty()) {
            spec = spec.and(PropertySpecification.hasFullName(fullName));
        }

        if (city != null && !city.isEmpty()) {
            spec = spec.and(PropertySpecification.hasLocationContaining(city));
        }

        if (type != null) {
            spec = spec.and(PropertySpecification.hasType(type));
        }

        if (minBedrooms != null && minBedrooms > 0 ) {
            spec = spec.and(PropertySpecification.hasMinBedrooms(minBedrooms));
        }

        if ( maxPrice != null && minPrice != null && minPrice >= 0 && maxPrice >= 0 && minPrice <= maxPrice) {
            spec = spec.and(PropertySpecification.hasPriceInRange(minPrice, maxPrice));
        }

        if (minSize != null && maxSize != null && minSize >= 0 && maxSize >= 0 && minSize <= maxSize ) {
            spec = spec.and(PropertySpecification.hasSizeInRange(minSize, maxSize));
        }

        if ( minBathrooms != null && minBathrooms > 0 ) {
            spec = spec.and(PropertySpecification.hasMinBathrooms(minBathrooms));
        }

        if (sellerTypes != null && !sellerTypes.isEmpty()) {
            for (Property.SellerType sellerType : sellerTypes) {
                spec = spec.and(PropertySpecification.hasSellerType(sellerType));
            }
        }

        // if (amenities != null && !amenities.isEmpty()) {
        //     spec = spec.and(PropertySpecification.hasAmenities(amenities));
        // }

        return propertyRepository.findAll(spec);
    }

    public Optional<Property> getPropertyById(Long id) {
        return  propertyRepository.findById(id);
    }

    public HttpStatus setProperty(Property property){

        Location location = property.getLocation();
        User owner = property.getOwner();

        // Save the location if it's not already persisted
        if (location != null && location.getId() == 0) {
            location = locationRepository.save(location);
            property.setLocation(location);
        }
        if (owner != null && owner.getId() == 0) {
            owner = userRepository.save(owner);
            property.setOwner(owner);
        }
        
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
