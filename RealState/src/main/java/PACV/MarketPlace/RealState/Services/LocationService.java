
package PACV.MarketPlace.RealState.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public String getLocationByZipCode(Long id) throws IOException {
        String urlString = "http://viacep.com.br/ws/"+id;
        URL url = new URL(urlString);
        System.out.println(url);
        HttpURLConnection connection = (HttpURLConnection) 
        url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Error: HTTP response code " + responseCode);
        }

        // Read response content
        InputStream inputStream = (InputStream) connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        StringBuilder responseContent = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            responseContent.append(line);
            responseContent.append("\n"); // Add newline for readability
        }
        reader.close();

        // Process response content here
        System.out.println("Response content:");
        System.out.println(responseContent.toString());

        return responseContent.toString();
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
