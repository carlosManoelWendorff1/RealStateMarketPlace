package PACV.MarketPlace.RealState.Models;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Property {
    
    public enum Type{
        House,
        Apartament,
    }

    public enum SellerType{
        REAL_ESTATE_AGENCY ,
        PRIVATE_SELLER ,
        DEVELOPER ,
        BUILDER ,
        INVESTOR 
    }

    @Id
    @GeneratedValue
    private long id;
    
    private String title;
    private String description;
    private double price;
    private long size;
    private List<String> imageSources;

    @Column(name = "optionals")
    private List<String> optionals;
    
    private int bedRooms;
    private int bathRoom;
    private int yearBuilt;
    private Type type;
    private SellerType sellerType;

    @ManyToOne
    private User seller;

    @OneToOne
    private Location location;


    
    public void setTitle(String title) {
        this.title = title;
    }
    public List<String> getImageSources() {
        return imageSources;
    }
    public void setImageSources(List<String> imageSources) {
        this.imageSources = imageSources;
    }
    public int getBedRooms() {
        return bedRooms;
    }
    public void setBedRooms(int bedRooms) {
        this.bedRooms = bedRooms;
    }
    public int getBathRoom() {
        return bathRoom;
    }
    public void setBathRoom(int bathRoom) {
        this.bathRoom = bathRoom;
    }
    public int getYearBuilt() {
        return yearBuilt;
    }
    public void setYearBuilt(int yearBuilt) {
        this.yearBuilt = yearBuilt;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public SellerType getSellerType() {
        return sellerType;
    }
    public void setSellerType(SellerType sellerType) {
        this.sellerType = sellerType;
    }
    public User getOwner() {
        return seller;
    }
    public void setOwner(User owner) {
        this.seller = owner;
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
        optionals = optionals;
    }
    public List<String> getOptionals() {
        return optionals;
    }
    public String getTitle() {
        return title;
    }
    
    
}
