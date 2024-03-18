package PACV.MarketPlace.RealState.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PACV.MarketPlace.RealState.Services.PropertyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import PACV.MarketPlace.RealState.Models.Location;
import PACV.MarketPlace.RealState.Models.Property;

@RestController
@RequestMapping("/property/")
public class PropertyController {
    
    @Autowired
    PropertyService propertyService;

    @GetMapping("all")
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }
    
}
