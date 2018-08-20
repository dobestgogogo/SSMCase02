package com.tjx.dao;

import com.tjx.pojo.Product;
import com.tjx.pojo.Sale;
import com.tjx.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserMapper {

    User login(@Param("userName") String userName, @Param("password") String password);

    List<Product> sale();

    void addSale(@Param("pid") Integer pid, @Param("price") Double price, @Param("quantity") Integer quantity, @Param("uid") Integer uid, @Param("date")Date date);

    List<Sale> lookInfo(@Param("pageindex") Integer pageindex,@Param("pageSize") Integer pageSize,@Param("num") Integer num);

    Integer getCount();

    Product getQuantity(@Param("pid") Integer pid);

    void updateQuantity(@Param("pid") Integer pid,@Param("quantity") Integer quantity,@Param("num") Integer num);

    List<Sale> findAll(@Param("num") Integer num);
}

