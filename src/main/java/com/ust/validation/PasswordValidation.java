
package com.ust.validation;

public class PasswordValidation {
    
    public static boolean validate(String password){
      String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@*#$%^&+=])(?=\\S+$).{8,}";
        return password.matches(pattern);
   }
    
    
}
