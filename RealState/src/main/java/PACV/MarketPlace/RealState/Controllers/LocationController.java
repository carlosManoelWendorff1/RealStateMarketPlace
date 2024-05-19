package PACV.MarketPlace.RealState.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import PACV.MarketPlace.RealState.Models.Location;
import PACV.MarketPlace.RealState.Services.LocationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/location/")
public class LocationController {
    
    @Autowired
    LocationService locationService;
    
    @CrossOrigin
    @GetMapping("oneByName")
    public Optional<Location> getOneLocationByName(@RequestParam Long locationName) {
        return locationService.getLocationByName(locationName);
    }

    @GetMapping("oneById")
    public Optional<Location> getOneLocationById(@RequestParam Long id) {
        return locationService.getLocationById(id);
    }

    @GetMapping("all")
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @PostMapping("one")
    public HttpStatus postLocation(@RequestBody Location location) {        
        return locationService.setLocation(location);
    }

    @PutMapping("one")
    public HttpStatus putLocation(@RequestBody Location location) {        
        return locationService.updateLocation(location);
    }

    @DeleteMapping("one/{id}")
    public HttpStatus deleteLocation(@PathVariable Long id) {        
        return locationService.removeLocation(id);
    }

    
}
