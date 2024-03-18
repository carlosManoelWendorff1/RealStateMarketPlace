
package PACV.MarketPlace.RealState.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PACV.MarketPlace.RealState.Models.Location;
import PACV.MarketPlace.RealState.Repositories.LocationRepository;

@Service
public class LocationService {

    @Autowired
    LocationRepository LocationRepository;

    public Location getLocationByName(String locationName) {
        return null;
    }

    public List<Location> getAllLocations() {
        return (List<Location>) LocationRepository.findAll();
    }


    

}
