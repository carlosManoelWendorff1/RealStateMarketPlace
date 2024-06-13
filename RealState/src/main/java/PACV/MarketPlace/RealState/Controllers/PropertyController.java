package PACV.MarketPlace.RealState.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PACV.MarketPlace.RealState.Services.PropertyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import PACV.MarketPlace.RealState.Models.Property;
import java.util.Optional;

@RestController
@RequestMapping("/properties")
public class PropertyController {
    
    @Autowired
    PropertyService propertyService;

    @GetMapping("all")
    public List<Property> getAllProperties(@RequestParam(required = false) String filterString) {
        if(filterString == null){
            return propertyService.getAllProperties();
        }
        else{
            return propertyService.getAllPropertiesLike(filterString);
        }
        
    }

    @GetMapping()
    public ResponseEntity<List<Property>> searchProperties(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Long size,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Property.Type type,
            @RequestParam(required = false) Integer minBedrooms,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long minSize,
            @RequestParam(required = false) Long maxSize,
            @RequestParam(required = false) Integer minBathrooms,
            @RequestParam(required = false) List<Property.SellerType> sellerTypes
            //,@RequestParam(required = false) List<String> amenities
            ) {

        List<Property> properties = propertyService.searchProperties(
                title, description, price, size, fullName, location, type,
                minBedrooms, minPrice, maxPrice, minSize, maxSize,
                minBathrooms, sellerTypes
                //, amenities
                );

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("oneByName")
    public List<Property> getOnePropertyByName(@RequestParam String PropertyName) {
        return propertyService.getPropertyByTittle(PropertyName);
    }

    @GetMapping("oneById")
    public Optional<Property> getOnePropertyById(@RequestParam Long id) {
        return propertyService.getPropertyById(id);
    }

    @PostMapping("one")
    public HttpStatus postProperty(@RequestBody Property Property) {        
        return propertyService.setProperty(Property);
    }

    @PutMapping("one")
    public HttpStatus putProperty(@RequestBody Property Property) {        
        return propertyService.updateProperty(Property);
    }

    @DeleteMapping("one/{id}")
    public HttpStatus deleteProperty(@PathVariable Long id) {        
        return propertyService.removeProperty(id);
    }
    
}
