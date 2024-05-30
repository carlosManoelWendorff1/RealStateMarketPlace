package PACV.MarketPlace.RealState.utils;

import PACV.MarketPlace.RealState.Models.Property;
import org.springframework.data.jpa.domain.Specification;

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

    public static Specification<Property> hasOwnerName(String ownerName) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.join("owner").get("fullName")), "%" + ownerName.toLowerCase() + "%");
    }

    public static Specification<Property> hasLocationCity(String city) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.join("location").get("city")), "%" + city.toLowerCase() + "%");
    }
}
