package PACV.MarketPlace.RealState.Models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Property {
    
    @Id
    @GeneratedValue
    private long id;
    
    private String title;
    private String description;
    private double price;
    private long size;
    private List<String> imagesUrl;
    private List<String> Optionals;
    
    @ManyToOne
    private User owner;

    @OneToOne
    private Location location;

    
    
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public List<String> getImagesUrl() {
        return imagesUrl;
    }
    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
    public void setOptionals(List<String> optionals) {
        Optionals = optionals;
    }

    public List<String> getOptionals() {
        return Optionals;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    
}
