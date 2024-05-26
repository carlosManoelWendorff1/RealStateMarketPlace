package PACV.MarketPlace.RealState.Controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    public <T> ResponseEntity<T> genericApiResponseForGet(Optional<T> object) {
        if (object.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(object.get());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}