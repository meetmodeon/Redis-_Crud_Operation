package com.api.redis.controller;

import com.api.redis.dao.UserDao;
import com.api.redis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserDao userDao;

    @PostMapping
    public User createUser(@RequestBody User user){
        user.setUserId(UUID.randomUUID().toString());
        return userDao.save(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId){
        return userDao.get(userId);
    }

    @GetMapping
    public List<User> getAll(){
        Map<Object,Object> all= userDao.findAll();
        Collection<Object> values= all.values();
        List<User> users = values.stream().map(value->(User)value).collect(Collectors.toList());
        return users;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId){
        userDao.delete(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable String userId, @RequestBody User user){
        return userDao.update(userId,user);
    }
}
