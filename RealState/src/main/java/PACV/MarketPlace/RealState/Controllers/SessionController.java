package PACV.MarketPlace.RealState.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import PACV.MarketPlace.RealState.Services.SessionService;


@CrossOrigin
@RestController
public class SessionController extends BaseController {
    @Autowired
    private SessionService userCredentialsService;

    SessionController(SessionService service) {
        this.userCredentialsService = service;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> getAccessToken(@RequestBody MultiValueMap<String, String> userCredentials) {
        return userCredentialsService.getAccessToken(userCredentials);
    }

    @PostMapping(path = "/login/refresh", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> refreshAccessToken(@RequestBody MultiValueMap<String, String> refreshToken) {
        return userCredentialsService.refreshAccessToken(refreshToken);
    }

    @PostMapping(path = "/logout", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> logout(@RequestBody MultiValueMap<String, String> access_token) {
        return userCredentialsService.logout(access_token);
    }
}