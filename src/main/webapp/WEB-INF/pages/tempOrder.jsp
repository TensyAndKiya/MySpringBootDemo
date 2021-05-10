<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@ page import="java.util.Calendar" %>
<%
    // 该参数用于用户查询时限制 只能查当年的数据
    // 这样获取而不是用js获取的原因是避免用户通过修改自己机器的时间而绕过限制
    long curYear = Calendar.getInstance().get(Calendar.YEAR);
    request.setAttribute("curYear",curYear);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
开始时间：<input type="text" id="startDate"/><br/>
结束时间：<input type="text" id="endDate"/><br/>
第几页：<input type="text" id="page"/><br/>
每页条数：<input type="text" id="size"/><br/>
<button id="getByPage" type="button">根据页检索</button>

<table id="orderTable">

</table>

<script type="text/javascript" src="/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
    $(function () {
        // getByPage
        $("#getByPage").click(function () {
            console.log("根据页检索");
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            if (viewLimitByCurYear(startDate, endDate)) {
                $.get("/tempOrder/getByPage", {
                    startDate: startDate,
                    endDate: endDate,
                    page: $("#page").val(),
                    size: $("#size").val(),
                    parkLotId: "f635cb442b964a0da188fef47f852462",
                    carLicense: "川A588T0",
                    orderType: 0
                }, function (data) {
                    var orders = data;
                    if (orders.length > 0) {
                        var th = "<thead><tr>";
                        var tableHead = orders[0];
                        $.each(tableHead,function(k,v){
                            th += "<td>"+k+"</td>";
                        });
                        th += "</tr></thead>";
                        $.each(orders, function(i,dog){
                            th += "<tr>";
                            $.each(dog,function(k,v){
                                th += "<td>"+v+"</td>";
                            });
                            th += "</tr>";
                        });
                        $("#orderTable").html(th);
                    }
                });
            } else{
                alert("时间校验没通过");
            }
        });
    });

    function checkTime(startTime,endTime){
        try{
            var startDate=new Date(startTime);
            var endDate=new Date(endTime);
            var yearToMonth = (endDate.getFullYear() - startDate.getFullYear()) * 12;
            var month_count = endDate.getMonth() - startDate.getMonth() + yearToMonth;
            if(month_count > 2 || month_count < 0){
                return false;
            }else{
                return true;
            }
        }catch (e) {
            console.log(e);
            return false;
        }
    }

    function viewLimitByCurYear(startTime,endTime){
        console.log("走你");
        try{
            var startYear = new Date(startTime).getFullYear();
            var endYear = new Date(endTime).getFullYear();
            console.log("start :" + startYear);
            console.log("end :" + endYear);
            console.log("sys now:" + "${curYear}");
            console.log("local now:" + new Date().getFullYear());
            return false;
        }catch (e) {
            console.log(e);
            return false;
        }
    }

</script>

</body>
</html>