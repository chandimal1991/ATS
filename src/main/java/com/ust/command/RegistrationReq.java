
package com.ust.command;

public class RegistrationReq {
    
    private String name;
    private String nic;
    private String email;
    private String password;
    private String status;

    public RegistrationReq(String name, String nic, String email, String password, String status) {
        this.name = name;
        this.nic = nic;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public String getName() {
        return name;
    }
    
    public String getNic() {
        return nic;
    } 

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    public String getStatus(){
        return status;
    }

    
}
