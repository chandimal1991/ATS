
package com.ust.entity;

public class User {
    
    private final String name;
    private final String email;
    private final String password;
    private final String nic;
    private final String status;
    private String funds;
    

    public User(String name, String email, String password, String nic, String status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nic = nic;
        this.status = status;
    }
    
    // getters

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNic() {
        return nic;
    }
    
    public String getStatus() {
        return status;
    }

    public String getFunds() {
        return funds;
    }
      
}
