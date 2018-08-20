package com.tjx.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.pagehelper.PageInfo;
import com.tjx.pojo.Product;
import com.tjx.pojo.Sale;
import com.tjx.pojo.User;
import com.tjx.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录
     */
    @RequestMapping("/login")
    public ModelAndView login(String userName, String password, HttpSession session){
        User user = userService.login(userName,password);
        ModelAndView mv = new ModelAndView();
        boolean con = true;
        if (user != null){
            session.setAttribute("user",user);
            mv.setViewName("/welcome.jsp");
        }else{
            mv.setViewName("/index.jsp");
            mv.addObject("con",con);
        }
        return mv;
    }

    /**
     * 注销
     */
    @RequestMapping("/quit")
    public ModelAndView quit(HttpSession session){
        ModelAndView mv = new ModelAndView("/index.jsp");
        session.invalidate();
        return mv;
    }

    /**
     * 销售下拉
     */
    @RequestMapping("/sale")
    public ModelAndView sale(HttpServletRequest request){
        List<Product> sales = userService.sale();
        request.setAttribute("sales",sales);
        ModelAndView mv = new ModelAndView("/welcome.jsp");
        return mv;
    }

    /**
     * 添加销售记录
     */
    @RequestMapping("/addSale")
    public String addSale(Integer pid,Double price,Integer quantity,HttpSession session,HttpServletRequest request){
        User user = (User) session.getAttribute("user");
        Integer uid = user.getId();
        boolean con = true;
        Integer num = 0;
        if (pid>0){
            Product p = userService.getQuantity(pid);
            num = p.getQuantity();
        }
        if (num>=quantity && quantity!=null && price!=null && pid!=null && pid!=0){
            request.setAttribute("con",con);
            userService.addSale(pid, price, quantity, uid, new Date());
            userService.updateQuantity(pid,num,quantity);
        }
        return "forward:/user/sale";
    }

    /**
     *查看销售记录
     */
    /*@RequestMapping("/lookInfo")
    public ModelAndView lookInfo(Integer pageindex,Integer num,HttpServletRequest request){
        pageindex = pageindex == null ? 1 : pageindex;
        request.setAttribute("pageindex",pageindex);
        Integer count = userService.getCount();//获取总记录数
        ModelAndView mv = new ModelAndView("/welcome.jsp");
        boolean flag = false;
        if (count>0){
            flag = true;
            Integer pageSize = 3;//每页记录数
            Integer totalPage = count%pageSize!=0?count/pageSize+1:count/pageSize;//总页数
            request.setAttribute("totalPage",totalPage);
            mv.addObject("totalPage",totalPage);
            List<Sale> sales = userService.lookInfo(pageindex,pageSize,num);
            mv.addObject("count",count);
            mv.addObject("saleInfo",sales);
            mv.addObject("number",num);
        }
            mv.addObject("flag",flag);
        return mv;
    }*/

    /**
     *
     */
    @RequestMapping("/lookInfo")
    @ResponseBody
    public Object lookInfo(HttpServletRequest request){
        Integer pageNum = request.getParameter("pageNum") == null ? 1 :Integer.parseInt(request.getParameter("pageNum"));
        Integer num = Integer.parseInt(request.getParameter("num"));
        Integer count = userService.getCount();//获取总记录数
        System.out.println(count);
        Integer pageSize = 8;
        PageInfo pageInfo = userService.findAll(pageNum, pageSize, num);
        return pageInfo;
    }

    /**
     *ajax验证登录
     */
    @RequestMapping(value = "/checkLogin")
    public void checkLogin(HttpServletRequest request,HttpServletResponse response){
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        User user = userService.login(userName,password);
        boolean con = false;
        if (user==null){
            con = true;
        }
        try {
            PrintWriter pw = response.getWriter();
            pw.println(con);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证添加是否成功！
     */
    @RequestMapping("/addSaleInfo")
    public void addSaleInfo(HttpServletRequest request,HttpServletResponse response){
        Integer num = Integer.parseInt(request.getParameter("num"));
        Integer pid = Integer.parseInt(request.getParameter("productId"));
        boolean con = false;
        Product product = userService.getQuantity(pid);
        if (product.getQuantity()>=num){
            con = true;
        }
        try {
            PrintWriter pw = response.getWriter();
            pw.println(con);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看库存
     */
    @RequestMapping("/stock")
    public ModelAndView stock(Integer pId){
        ModelAndView mv = new ModelAndView("/welcome.jsp");
        List<Product> sales = userService.sale();
        mv.addObject("sales",sales);
        boolean con = false;
        if (pId!=0){
            con = true;
            Product quantity = userService.getQuantity(pId);
            Integer num = quantity.getQuantity();
            mv.addObject("num",num);
            mv.addObject("con",con);
            mv.addObject("productId",pId);
        }
        return mv;
    }
}
