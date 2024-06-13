package PACV.MarketPlace.RealState.utils;

import PACV.MarketPlace.RealState.Models.Property;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.SetJoin;

public class PropertySpecification {

    public static Specification<Property> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Property> hasDescription(String description) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }

    public static Specification<Property> hasPrice(double price) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("price"), price);
    }

    public static Specification<Property> hasSize(long size) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("size"), size);
    }

    public static Specification<Property> hasFullName(String ownerName) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.join("owner").get("fullName")), "%" + ownerName.toLowerCase() + "%");
    }

    public static Specification<Property> hasLocationContaining(String search) {
        return (root, query, criteriaBuilder) -> {
            String searchPattern = "%" + search.toLowerCase() + "%";
            Predicate addressPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.join("location").get("address")), searchPattern);
            Predicate cityPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.join("location").get("city")), searchPattern);
            Predicate statePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.join("location").get("state")), searchPattern);
            Predicate countryPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.join("location").get("country")), searchPattern); 
            return criteriaBuilder.or(addressPredicate, cityPredicate, statePredicate, countryPredicate);
        };
    }
    public static Specification<Property> hasType(Property.Type type) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("type"), type);
    }

    public static Specification<Property> hasMinBedrooms(int minBedrooms) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("bedrooms"), minBedrooms);
    }

    public static Specification<Property> hasPriceInRange(double minPrice, double maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }

    public static Specification<Property> hasSizeInRange(long minSize, long maxSize) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("size"), minSize, maxSize);
    }

    public static Specification<Property> hasMinBathrooms(int minBathrooms) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("bathroom"), minBathrooms);
    }

    public static Specification<Property> hasSellerType(Property.SellerType type) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("seller_type"), type);
    }

    public static Specification<Property> hasAmenity(String amenity) {
        return (root, query, criteriaBuilder) -> {
            // Joining the List<String> Optionals with the root Property entity
            ListJoin<Property, String> optionalsJoin = root.joinList("optionals", JoinType.INNER);
            
            // Creating a predicate that checks for equality of lowercase values
            return criteriaBuilder.equal(criteriaBuilder.lower(optionalsJoin), amenity.toLowerCase());
        };
    }    

    public static Specification<Property> hasAmenities(List<String> amenities) {
        Specification<Property> spec = Specification.where(null);
        
        if (amenities != null && !amenities.isEmpty()) {
            for (String amenity : amenities) {
                spec = spec.and(hasAmenity(amenity));
            }
        }
        
        return spec;
    }
}
