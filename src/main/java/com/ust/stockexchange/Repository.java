package com.ust.stockexchange;

import com.ust.entity.User;

public interface Repository{
    
    void save(User user);
    User get(String PrimaryKey);
    
}
