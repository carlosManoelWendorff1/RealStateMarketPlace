package PACV.MarketPlace.RealState.Dto;

import PACV.MarketPlace.RealState.Models.User;

public class UserRegisterDto extends User{

    // Construtor padrão sem argumentos
    public UserRegisterDto() {
        super();
    }

    // Construtor que copia os campos de outro UserRegisterDto
    public UserRegisterDto(UserRegisterDto user) {
        super(user);
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
}
