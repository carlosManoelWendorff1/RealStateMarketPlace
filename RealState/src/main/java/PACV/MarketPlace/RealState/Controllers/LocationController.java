package PACV.MarketPlace.RealState.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import PACV.MarketPlace.RealState.Models.Location;
import PACV.MarketPlace.RealState.Services.LocationService;

@RestController
@RequestMapping("/location/")
public class LocationController {
    
    @Autowired
    LocationService locationService;

    @GetMapping("one")
    public Location getOneLocation(@RequestParam String locationName) {
        return locationService.getLocationByName(locationName);
    }

    @GetMapping("all")
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }
}
