package PACV.MarketPlace.RealState;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import PACV.MarketPlace.RealState.Models.Location;
import PACV.MarketPlace.RealState.Models.Property;
import PACV.MarketPlace.RealState.Repositories.LocationRepository;
import PACV.MarketPlace.RealState.Repositories.PropertyRepository;

@SpringBootTest
class RealStateApplicationTests {

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testModels() {
		Location local = new Location();
		local.setCity("jaras");
		local.setCountry("braza");

		Property property = new Property();
		property.setLocation(local);
		property.setTittle("casa das primas");

		locationRepository.save(local);
		propertyRepository.save(property);

		assertNotNull(propertyRepository.findAll().iterator().hasNext());
		assertNotNull(locationRepository.findAll().iterator().hasNext());

	}

}
