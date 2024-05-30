package PACV.MarketPlace.RealState.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PACV.MarketPlace.RealState.Services.PropertyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import PACV.MarketPlace.RealState.Models.Location;
import PACV.MarketPlace.RealState.Models.Property;
import java.util.Optional;

@RestController
@RequestMapping("/property/")
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

    @GetMapping("/search")
    public List<Property> searchProperties(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) Double price,
        @RequestParam(required = false) Long size,
        @RequestParam(required = false) String ownerName,
        @RequestParam(required = false) String city) {

        return propertyService.searchProperties(title, description, price, size, ownerName, city);
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
