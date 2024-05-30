package PACV.MarketPlace.RealState.Models;

import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;

    private String email;
    
    private Time registerDate;

    private String avatarUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }


    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getFullName() {
        return fullName;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public Time getRegisterDate() {
        return registerDate;
    }


    public void setRegisterDate(Time registerDate) {
        this.registerDate = registerDate;
    }


    public enum UserEnum {
		Realtor, Client, Admin, Agent, Seller, Buyer;
	}

    
}
