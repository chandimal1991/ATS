
package com.ust.command;


public class LoginReq {
    
    private String nic;
    private String password;

    public LoginReq(String nic, String password) {
        this.nic = nic;
        this.password = password;
    }

    public String getNic() {
        return nic;
    }

    public String getPassword() {
        return password;
    }
     
}
