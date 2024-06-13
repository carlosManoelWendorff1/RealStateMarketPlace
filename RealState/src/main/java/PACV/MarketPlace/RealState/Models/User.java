package PACV.MarketPlace.RealState.Models;

import java.sql.Time;
import java.util.Date;

import PACV.MarketPlace.RealState.Dto.UserRegisterDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;

    private String userName;

    private String email;

    @Temporal(TemporalType.TIMESTAMP)    
    private Date registerDate;

    @Enumerated(EnumType.STRING)    
    private UserEnum userType;

    private String avatarUrl;

    public User(UserRegisterDto user) {
        this.email=user.getEmail();
        this.userName=user.getUserName();
        this.avatarUrl=user.getAvatarUrl();
        this.registerDate=user.getRegisterDate();
        this.fullName=user.getFullName();
        this.userType=user.getUserType();
    }


    public User() {
    }


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


    public UserEnum getUserType() {
        return userType;
    }


    public void setUserType(UserEnum userType) {
        this.userType = userType;
    }

    public Date getRegisterDate() {
        return registerDate;
    }


    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }


    public enum UserEnum {
		Realtor, Client, Admin, Agent, Seller, Buyer;
	}


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "User [id=" + id + ", fullName=" + fullName + ", userName=" + userName + ", email=" + email
                + ", registerDate=" + registerDate + ", userType=" + userType + ", avatarUrl=" + avatarUrl + "]";
    }

    
}
