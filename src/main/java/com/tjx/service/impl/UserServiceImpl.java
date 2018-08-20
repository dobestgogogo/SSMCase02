package com.tjx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tjx.dao.UserMapper;
import com.tjx.pojo.Product;
import com.tjx.pojo.Sale;
import com.tjx.pojo.User;
import com.tjx.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(@Param("userName") String userName, @Param("password") String password) {
        User user = userMapper.login(userName, password);
        if (user != null){
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    @Override
    public List<Product> sale() {
        List<Product> sales = userMapper.sale();
        return sales;
    }

    @Override
    public void addSale(Integer pid, double price, Integer quantity, Integer uid, Date date) {
        userMapper.addSale(pid, price, quantity, uid, date);
    }

    @Override
    public List<Sale> lookInfo(Integer pageindex,Integer pageSize,Integer num) {
        List<Sale> sales = userMapper.lookInfo(pageindex,pageSize,num);
        return sales;
    }

    @Override
    public Integer getCount() {
        Integer count = userMapper.getCount();
        return count;
    }

    @Override
    public Product getQuantity(Integer pid) {
        Product product = userMapper.getQuantity(pid);
        return product;
    }

    @Override
    public void updateQuantity(Integer pid, Integer quantity, Integer num) {
        userMapper.updateQuantity(pid,quantity,num);
    }

    @Override
    public PageInfo findAll(Integer pageNum, Integer pageSize, Integer num) {
        PageHelper.startPage(pageNum,pageSize);
        List<Sale> sales = userMapper.findAll(num);
        PageInfo pageInfo = new PageInfo(sales);
        return pageInfo;
    }
}
