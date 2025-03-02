package com.api.redis.dao;

import com.api.redis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Repository
public class UserDao {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private static final String KEY = "USER";

    //SAVE USER

    public User save(User user){
        redisTemplate.opsForHash().put(KEY, user.getUserId(),user);
        return user;
    }

    public User get(String userId){
        return (User) redisTemplate.opsForHash().get(KEY,userId);
    }

    //find all
    public Map<Object,Object> findAll(){
        return redisTemplate.opsForHash().entries(KEY);
    }

    public void delete(String userId){
        redisTemplate.opsForHash().delete(KEY,userId);
    }

    public User update(String userId,User user){
        if (!redisTemplate.opsForHash().hasKey(KEY,userId)){
            throw new RuntimeException("User with ID "+ userId + "not found in cache");
        }
        User user1=get(userId);
        user.setUserId(userId);
        if(user.getEmail() != null){
            user.setEmail(user.getEmail());
        }else
            user.setEmail(user1.getEmail());

        redisTemplate.opsForHash().put(KEY,userId,user);

        return user;
    }
}
