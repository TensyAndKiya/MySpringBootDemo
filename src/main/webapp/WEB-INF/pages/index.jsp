<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String webUrl = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + "/";
    // 放入request 使得用el表达式可以访问到
    request.setAttribute("webUrl", webUrl);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>INDEX</title>
    <script type="text/javascript" src="static/js/jquery-3.3.1.js"></script>
</head>
<body>
<h2>Hello World!</h2>
<a href="dog">dog</a>
<a href="chat">chat</a>
<a href="tempOrder">order</a>
<a href="showRoles">showRoles</a>
<a href="/logout">退出登陆</a>
<form id="logout" action="/logout" method="post" style="display: none;">
</form>
<button onclick="jsonTest()">json test</button>

<script type="text/javascript">

    function logout() {
        $("#logout").submit();
    }

    var url = '${webUrl}';
    console.log(url);

    function jsonTest() {
        var data = {
            name: "张三",
            age: 18,
            weight: 235.05,
            dog: {
                name: "小黄",
                color: "blue"
            }
        };
        $.ajax({
            type: "GET",
            url: "jsonTest",
            data: {
                json: JSON.stringify(data)
            },
            contentType: "application/json;charset=UTF-8",
            success: function (msg) {
                alert(JSON.stringify(msg));
            }
        });
    }
</script>

</body>
</html>
