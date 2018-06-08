
package com.ust.dao;

import com.ust.entity.User;

public interface UserDAO {
    
    public void save(User user);
    public User get(String primaryKey);
    
}
