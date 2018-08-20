<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2018/8/16
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>成功页面</title>
    <script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
    <link rel="stylesheet" href="/resource/js/bootstrap.min.css">
    <script type="text/javascript" src="/resource/js/jquery.pagination.js"></script>
    <script type="text/javascript">
        function save(){
            var price = $("[name=price]").val();
            var reg = /^\d+$/;
            if (price==""){
                alert("价格必填！");
                return false;
            }else if(reg.test(price)==false){
                alert("销售价格必须是数字！")
                return false;
            }
            var quantity = $("[name=quantity]").val();
            if (quantity==""){
                alert("数量必填！");
                return false;
            }else if(reg.test(quantity)==false){
                alert("销售数量必须数字！")
                return false;
            }
            var pid = $("[name=pid]").val();
            if (pid==0){
                alert("请选择商品");
            }

            if (pid!=0 && price!="" && quantity!="" && reg.test(price)==true && reg.test(price)==true){
                $.ajax({
                    url:"/user/addSaleInfo",
                    data:{"num":quantity,"productId":pid},
                    type:"post",
                    success:function (data) {
                        var con = eval("("+data+")");
                        if (con){
                            alert("添加成功！")
                        }else{
                            alert("添加失败！")
                        }
                    }
                })
            }
        }
        
        function stock() {
            var pid = $("[name=pId]").val();
            if (pid==0){
                alert("请选择商品");
            }
        }

        function lookInfo(pageNum) {
            var num = $("[name=num]").val();
            $.ajax({
                url:"/user/lookInfo",
                type:"POST",
                data:{"pageNum":pageNum,"num":num},
                success:function (data) {
                    $("#content").html("");
                    $.each(data.list,function (i, item) {
                        $("#content").append("<tr>\n" +
                            "<td>"+item.id+"</td>\n" +
                            "<td>"+item.product.productName+"</td>\n" +
                            "<td>"+item.price+"</td>\n" +
                            "<td>"+item.quantity+"</td>\n" +
                            "<td>"+item.totalPrice+"</td>\n" +
                            "<td>"+item.saleDate+"</td>\n" +
                            "<td>"+item.user.realName+"</td>\n" +
                            "</tr>");
                    })
                    $("#pagination").pagination(data.total,{
                        current_page:data.pageNum,//当前页
                        items_per_page:data.pageSize,//页大小
                        prev_text:"上一页",
                        next_text:"下一页",
                        callback:lookInfo
                    })
                    /*var jsonStr = eval("("+data+")");
                    var htmls=["<tr><td>ID</td><td>商品</td><td>单价</td><td>数量</td><td>总价</td><td>销售日期</td><td>销售员</td></tr>"];
                    $.each(jsonStr,function (i,item) {
                        htmls[i+1]="<tr>\n" +
                            "<td>"+item.id+"</td>\n" +
                            "<td>"+item.product.productName+"</td>\n" +
                            "<td>"+item.price+"</td>\n" +
                            "<td>"+item.quantity+"</td>\n" +
                            "<td>"+item.totalPrice+"</td>\n" +
                            "<td>"+item.saleDate+"</td>\n" +
                            "<td>"+item.user.realName+"</td>\n" +
                            "</tr>"
                    })
                    $("table").html(htmls);
                    var first=$("<button onclick='lookInfo("+(1)+")'>首页</button>");
                    var btn1=$("<button onclick='lookInfo("+(pageNum-1)+")'>上一页</button>");
                    var btn2=$("<button onclick='lookInfo("+(pageNum+1)+")'>下一页</button>");
                    //var last=$("<button onclick='lookInfo("+(json.totalPage)+")'>末页</button>");
                    $("table").append(first);
                    $("table").append(btn1);
                    $("table").append(btn2);*/
                }
            })
        }
    </script>
</head>
<body>
    欢迎：${user.realName}
    <a href="/user/quit" onclick="return confirm('确定要退出系统吗？')">退出系统</a><br/>
    <a href="/user/sale">销售</a><br/>
    <%--<a href="/user/lookInfo?pageindex=1">销售信息查询</a><br/>--%>
    <a href="javascript:void(0);" onclick="return lookInfo(1)">销售信息查询</a><br/>
    <a href="/user/stock?pId=0">查看库存</a><br/>
    <div style="border: 1px solid black" width="700px" height="500px">
        <h1>欢迎使用小型进销存系统</h1>
    </div>
    <%--销售--%>
    <div style="border: 1px solid black">
        <h4>添加销售</h4>
        <form action="/user/addSale" method="post" onsubmit="return save()">
            商品名称：
            <select name="pid">
                <option value="0">-请选择商品-</option>
                <c:forEach items="${sales}" var="item">
                    <option value="${item.id}"${item.productName}</option>
                </c:forEach>
            </select>
            <br/>
            销售单价：<input type="text" name="price">
            <br/>
            销售数量：<input type="text" name="quantity">
            <br/>
            <input type="submit" value="保存">
        </form>
    </div>

    <%--销售信息--%>
    <%--<div style="border: 1px solid black">
        <h4>销售信息查询：</h4>
        <form action="/user/lookInfo" method="post">
            排序方式：
            <select name="num">
                <option value="1"<c:if test="${number eq '1'}">selected="selected"</c:if>>销售日期</option>
                <option value="2"<c:if test="${number eq '2'}">selected="selected"</c:if>>单笔总价</option>
            </select>
            <input type="submit" value="提交">
            <c:if test="${flag==true}">
                <table border="1">
                    <tr>
                        <td>ID</td><td>商品</td><td>单价</td><td>数量</td><td>总价</td><td>销售日期</td><td>销售员</td>
                    </tr>
                    <c:forEach items="${saleInfo}" var="item">
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.product.productName}</td>
                            <td>${item.price}</td>
                            <td>${item.quantity}</td>
                            <td>${item.totalPrice}</td>
                            <td>${item.saleDate}</td>
                            <td>${item.user.realName}</td>
                        </tr>
                    </c:forEach>
                </table>
                <a href="/user/lookInfo?pageindex=1">首页</a>
                <c:if test="${pageindex==1}">
                    上一页
                </c:if>
                <c:if test="${pageindex!=1}">
                    <a href="/user/lookInfo?pageindex=${pageindex-1}">上一页</a>
                </c:if>
                <c:if test="${pageindex==totalPage}">
                    下一页
                </c:if>
                <c:if test="${pageindex<totalPage}">
                    <a href="/user/lookInfo?pageindex=${pageindex+1}">下一页</a>
                </c:if>
                <a href="/user/lookInfo?pageindex=${totalPage}">末页</a>
                第${pageindex}页/共${totalPage}页(共${count}条记录)
            </c:if>
            <c:if test="${flag==false}">
                <h3>暂时没有销售记录</h3>
            </c:if>
        </form>
    </div>--%>

    <div style="border: 1px solid black" >
        <h4>销售信息查询：</h4>
            排序方式：
            <select name="num">
                <option value="1"<c:if test="${number eq '1'}">selected="selected"</c:if>>销售日期</option>
                <option value="2"<c:if test="${number eq '2'}">selected="selected"</c:if>>单笔总价</option>
            </select>
            <button onclick="lookInfo(1)">提交</button>
                <table border="1" id="table">
                    <thead>
                        <tr>
                            <td>ID</td><td>商品</td><td>单价</td><td>数量</td><td>总价</td><td>销售日期</td><td>销售员</td>
                        </tr>
                    </thead>
                    <tbody id="content">
                    </tbody>
                </table>
            <div class="pagination" id="pagination"></div>
    </div>




    <%--库存--%>
    <div style="border: 1px solid black">
        <h4>查看库存：</h4>
        <form action="/user/stock" method="post" onsubmit="return stock()">
            商品名称：
            <select name="pId">
                <option value="0">-请选择商品-</option>
                <c:forEach items="${sales}" var="item">
                    <option value="${item.id}"<c:if test="${productId eq item.id}">selected="selected"</c:if>>${item.productName}</option>
                </c:forEach>
            </select>
            <input type="submit" value="提交">
            <c:if test="${con==true}">
                <h3>该商品库存数量是：${num}</h3>
            </c:if>
        </form>
    </div>
</body>
</html>
