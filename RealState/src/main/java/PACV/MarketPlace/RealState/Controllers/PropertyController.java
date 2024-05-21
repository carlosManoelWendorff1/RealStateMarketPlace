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
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @GetMapping("oneByName")
    public List<Property> getOnePropertyByName(@RequestParam String PropertyName) {
        return propertyService.getPropertyByTittle(PropertyName);
    }

    @GetMapping("allLike")
    public List<Property> getAllPropertiesLike(@RequestParam String filterString) {
        return propertyService.getAllPropertiesLike(filterString);
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
