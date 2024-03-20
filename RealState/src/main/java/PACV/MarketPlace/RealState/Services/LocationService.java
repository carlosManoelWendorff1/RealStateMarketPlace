
package PACV.MarketPlace.RealState.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import PACV.MarketPlace.RealState.Models.Location;
import PACV.MarketPlace.RealState.Repositories.LocationRepository;

@Service
public class LocationService {

    @Autowired
    LocationRepository LocationRepository;

    public Optional<Location> getLocationByName(Long locationName) {
        return  LocationRepository.findById(locationName);
    }

    public Optional<Location> getLocationById(Long id) {
        return  LocationRepository.findById(id);
    }

    public HttpStatus setLocation(Location location){
        this.LocationRepository.save(location);

        if(LocationRepository.existsById(location.getId())){
            return HttpStatus.CREATED;
        }
        return HttpStatus.BAD_REQUEST;
    }
    
    public HttpStatus removeLocation(Long id){
        Optional<Location> locationOptional = LocationRepository.findById(id);
        if(locationOptional.isPresent()){
            this.LocationRepository.deleteById(locationOptional.get().getId());
            if(!LocationRepository.existsById(locationOptional.get().getId())){
                return HttpStatus.FOUND;
            }
        }
        return HttpStatus.NOT_FOUND;
    }

    public HttpStatus updateLocation(Location location){
        if(LocationRepository.existsById(location.getId())){
            Optional<Location> oldLocationOptional = LocationRepository.findById(location.getId());
            if(oldLocationOptional.isPresent()){
                Location oldLocation = oldLocationOptional.get();
                if(!oldLocation.toString().equals(location.toString())){
                    this.LocationRepository.save(location);
                    if(LocationRepository.existsById(location.getId())){
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

    public List<Location> getAllLocations() {
        return (List<Location>) LocationRepository.findAll();
    }


    

}
