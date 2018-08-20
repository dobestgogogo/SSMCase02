package com.tjx.service;

import com.github.pagehelper.PageInfo;
import com.tjx.pojo.Product;
import com.tjx.pojo.Sale;
import com.tjx.pojo.User;

import java.util.Date;
import java.util.List;

public interface UserService {

    User login(String userName,String password);

    List<Product> sale();

    void addSale(Integer pid, double price, Integer quantity, Integer uid, Date date);

    List<Sale> lookInfo(Integer pageindex,Integer pageSize,Integer num);

    Integer getCount();

    Product getQuantity(Integer pid);

    void updateQuantity(Integer pid, Integer quantity, Integer num);

    PageInfo findAll(Integer pageNum, Integer pageSize, Integer num);
}
