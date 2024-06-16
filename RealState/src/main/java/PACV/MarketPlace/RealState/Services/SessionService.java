package PACV.MarketPlace.RealState.Services;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import PACV.MarketPlace.RealState.Dto.UserRegisterDto;
import PACV.MarketPlace.RealState.Models.User;
import PACV.MarketPlace.RealState.Repositories.UserRepository;


@Service
public class SessionService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@Value("${realState.security.clientSecret}")
	private String CLIENT_SECRET;
	
	@Value("${realState.security.url}")
	private String URL;
	
	@Value("${realState.security.urlAdmin}")
	private String URL_ADMIN;
	
	@Value("${realState.security.urlLogout}")
	private String URL_LOGOUT;
	
	@Value("${realState.security.urlIntrospect}")
	private String URL_INTROSPECT;
	
	@Value("${realState.security.realm}")
	private String REALM;
	
	@Value("${realState.security.client}")
	private String CLIENT;
	
	@Value("${realState.security.clientAdmin}")
	private String CLIENT_ADMIN;
	
	@Value("${realState.security.clientSecretAdmin}")
	private String CLIENT_SECRET_ADMIN;


	protected static Gson gson = new Gson();
	protected static UserRole userRole = new UserRole();

	private static Logger logger = LoggerFactory.getLogger(SessionService.class);

	public ResponseEntity<String> getAccessToken(MultiValueMap<String, String> body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		body.add("grant_type", "password");
		body.add("client_id", CLIENT);
		body.add("client_secret", CLIENT_SECRET);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

		System.out.println("\n\n"+request.getBody()+"\n\n");
		System.out.println("\n\n"+request.getHeaders()+"\n\n");
			
		return postIntoKeycloak(request, URL);
	}
	
	public ResponseEntity<String> refreshAccessToken(MultiValueMap<String, String> body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		body.add("grant_type", "refresh_token");
		body.add("client_id", CLIENT);
		body.add("client_secret", CLIENT_SECRET);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

		return postIntoKeycloak(request, URL);
	}

	public ResponseEntity<String> logout(MultiValueMap<String, String> body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		body.add("client_id", CLIENT);
		body.add("client_secret", CLIENT_SECRET);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

		try {
			restTemplate.postForEntity(URL_LOGOUT, request, String.class);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Unauthorized e) {
			logger.error("Unauthorized exception at postIntoKeycloak() at SessionService.");
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} catch (Forbidden e) {
			logger.error("Forbidden exception at postIntoKeycloak() at SessionService.");
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		} catch (BadRequest e) {
			logger.error("Bad Request exception at postIntoKeycloak() at SessionService.");
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} catch (InternalServerError e) {
			logger.error("Internal Server Error exception at postIntoKeycloak() at SessionService.");
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<String> getIntrospect(String accessToken) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("token", accessToken);
		body.add("client_id", CLIENT);
		body.add("client_secret", CLIENT_SECRET);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
		
		return postIntoKeycloak(request, URL_INTROSPECT);
	}

	public ResponseEntity<User> getUserInfo(String accessToken) {
		// Create a JSONObject from the JSON string
        ResponseEntity<String> introspectResponse = getIntrospect(accessToken);
        JsonObject jsonObject = JsonParser.parseString(introspectResponse.getBody()).getAsJsonObject();

        // Extract the username
		try {
			
		
        String username = jsonObject.get("username").getAsString();

		User user = userRepository.findByUserName(username);

		if(user != null){
        	return ResponseEntity.ok(user);
		}
		else{
			return ResponseEntity.ofNullable(null);
		}
	} catch (NullPointerException e) {
		return ResponseEntity.ofNullable(null);
	}
	}

	public ResponseEntity<String> registerUser(UserRegisterDto user) {
        String keycloakAdminToken = getAdminAccessToken(); // You need to implement this to get the admin access token

		System.out.println("\n\n"+keycloakAdminToken+"\n\n");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(keycloakAdminToken);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", user.getUserName());
        userMap.put("enabled", true);
        userMap.put("emailVerified", true);
        userMap.put("firstName", "");
        userMap.put("lastName", "");
        userMap.put("email", user.getEmail());
        
        // Add required credentials
        Map<String, String> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", user.getPassword());
        credentials.put("temporary", "false");
        
        userMap.put("credentials", new Map[]{credentials});

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userMap, headers);

		System.out.println("\n\n"+request.getBody()+"\n\n");
		System.out.println("\n\n"+request.getHeaders()+"\n\n");

        String keycloakUrl = "http://localhost:8090/admin/realms/" + REALM + "/users";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(keycloakUrl, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
				user.setRegisterDate(new Date()); // Definir a data de registro
				User userToSave = new User(user);
                userRepository.save(userToSave); // Save the user to your local database
                return ResponseEntity.ok("User registered successfully");
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Failed to register user in Keycloak");
            }
        } catch (Exception e) {
            logger.error("Exception while registering user: ", e);
			if(e.toString().contains("Invalid email address.")){
				return ResponseEntity.status(400).body("Invalid email address.");
			}
			if(e.toString().contains("User exists with same username")){
				return ResponseEntity.status(409).body("User exists with same username");
			}
			if(e.toString().contains("User exists with same email")){
				return ResponseEntity.status(409).body("User exists with same email");
			}
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

	private String getAdminAccessToken() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "client_credentials");
        body.add("client_id", CLIENT_ADMIN);
		body.add("client_secret", CLIENT_SECRET_ADMIN); // Use your Keycloak admin client ID
       
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

		System.out.println("\n\n"+request.getBody()+"\n\n");
		System.out.println("\n\n"+request.getHeaders()+"\n\n");
		System.out.println("\n\n"+URL_ADMIN+"\n\n");
	
		ResponseEntity<String> response = restTemplate.postForEntity(URL_ADMIN, request, String.class);
	
		if (response.getStatusCode().is2xxSuccessful()) {
			JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
			return jsonObject.get("access_token").getAsString();
		} else {
			throw new RuntimeException("Failed to get admin access token from Keycloak");
		}
	}

	private ResponseEntity<String> postIntoKeycloak(HttpEntity<MultiValueMap<String, String>> request, String url) {
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
			
			return response;
		} catch (Unauthorized e) {
			logger.error("Unauthorized exception at postIntoKeycloak() at SessionService.");
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} catch (Forbidden e) {
			logger.error("Forbidden exception at postIntoKeycloak() at SessionService.");
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		} catch (BadRequest e) {
			logger.error("Bad Request exception at postIntoKeycloak() at SessionService.");
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} catch (InternalServerError e) {
			logger.error("Internal Server Error exception at postIntoKeycloak() at SessionService.");
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	protected List<String> getUserRolesValue(String accessToken) {
		accessToken = "Bearer " + accessToken;
		String userData = this.getIntrospect(accessToken.substring(7)).getBody();
		
		// Parse user data and check if it contains the expected structure
		JsonObject userJsonObject = gson.fromJson(userData, JsonObject.class);
	
		if (userJsonObject != null) {
			// Check if "realm_access" is present and is a JSON object
			JsonElement realmAccessElement = userJsonObject.get("realm_access");
	
			if (realmAccessElement != null && realmAccessElement.isJsonObject()) {
				// If "realm_access" is a valid JSON object, proceed to get the "roles" array
				JsonObject realmAccessObject = realmAccessElement.getAsJsonObject();
				JsonElement rolesElement = realmAccessObject.get("roles");
	
				if (rolesElement != null && rolesElement.isJsonArray()) {
					// Parse the "roles" array into a list of strings
					Type listType = new TypeToken<List<String>>() {}.getType();
					List<String> userRoleNames = gson.fromJson(rolesElement.getAsJsonArray(), listType);
	
					// Return the user roles
					return userRole.getAllValuesByName(userRoleNames);
				} else {
				}
			} else {
			}
		} else {
		}
	
		// Return an empty list if any checks fail
		return Collections.emptyList();
	}
	
}