<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2018/8/16
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <title>小型进销存系统</title>
    <script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">
            function beforeSubmit() {
                var username = $("[name=userName]").val();
                if (username==""){
                    alert("用户名必填！");
                    return false;
                }
                var password = $("[name=password]").val();
                if (password==""){
                    alert("密码必填！");
                    return false;
                }
                /*使用ajax请求*/
                if (password!="" && username!=""){
                $.ajax({
                    url:"/user/checkLogin",
                    data:{"userName":username,"password":password},
                    type:"post",
                    success:function (data) {
                            var con = eval("("+data+")");
                            if (con){
                                alert("登录失败！用户名或密码错误！")
                            }
                        }
                    })
                }
            }

            /*var con = ;
            if (con){
                alert("登录失败！用户名或密码错误！");
            }*/

    </script>
</head>
<body>
    <h1>小型进销存系统</h1>
    <form action="/user/login" method="post" onsubmit="return beforeSubmit()">
        用户名：<input type="text" name="userName">
        <br/>
        密&nbsp;&nbsp;&nbsp;码：<input type="text" name="password">
        <br/>
        <input type="submit" value="登录">
    </form>
</body>
</html>
