package PACV.MarketPlace.RealState.Services;

import java.lang.reflect.Type;
import java.util.Collections;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


@Service
public class SessionService {
	@Autowired
	private RestTemplate restTemplate;

	@Value("${realState.security.clientSecret}")
    private String CLIENT_SECRET;
	protected static final String URL = "http://localhost:8090/realms/realState/protocol/openid-connect/token";
	protected static final String URl_LOGOUT = "http://localhost:8090/realms/realState/protocol/openid-connect/logout";
	protected static final String URl_INTROSPECT = "http://localhost:8090/realms/realState/protocol/openid-connect/token/introspect";
	protected static final String REALM = "realState";
	protected static final String CLIENT = "realStateClient";
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
			restTemplate.postForEntity(URl_LOGOUT, request, String.class);
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
	
	public ResponseEntity<Map<String, Object>> getIntrospect(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("token", accessToken);
		body.add("client_id", CLIENT);
		body.add("client_secret", CLIENT_SECRET);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
		
		ResponseEntity<String> response = postIntoKeycloak(request, URl_INTROSPECT);
		if (response.getStatusCode().is2xxSuccessful()) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				Map<String, Object> introspectResponse = mapper.readValue(response.getBody(), Map.class);
				return new ResponseEntity<>(introspectResponse, response.getStatusCode());
			} catch (Exception e) {
				logger.error("Failed to parse introspect response", e);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(response.getStatusCode());
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
	
	// protected List<String> getUserRolesValue(String accessToken) {
	// 	accessToken = "Bearer " + accessToken;
	// 	String userData = this.getIntrospect(accessToken.substring(7)).getBody();
		
	// 	// Parse user data and check if it contains the expected structure
	// 	JsonObject userJsonObject = gson.fromJson(userData, JsonObject.class);
	
	// 	if (userJsonObject != null) {
	// 		// Check if "realm_access" is present and is a JSON object
	// 		JsonElement realmAccessElement = userJsonObject.get("realm_access");
	
	// 		if (realmAccessElement != null && realmAccessElement.isJsonObject()) {
	// 			// If "realm_access" is a valid JSON object, proceed to get the "roles" array
	// 			JsonObject realmAccessObject = realmAccessElement.getAsJsonObject();
	// 			JsonElement rolesElement = realmAccessObject.get("roles");
	
	// 			if (rolesElement != null && rolesElement.isJsonArray()) {
	// 				// Parse the "roles" array into a list of strings
	// 				Type listType = new TypeToken<List<String>>() {}.getType();
	// 				List<String> userRoleNames = gson.fromJson(rolesElement.getAsJsonArray(), listType);
	
	// 				// Return the user roles
	// 				return userRole.getAllValuesByName(userRoleNames);
	// 			} else {
	// 			}
	// 		} else {
	// 		}
	// 	} else {
	// 	}
	
	// 	// Return an empty list if any checks fail
	// 	return Collections.emptyList();
	// }
	
}