<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="_csrf" content="${_csrf.token}" />
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <title>Insert title here</title>
</head>
<body>
<input id="dId" type="text"/>
<button id="gBI" type="button">id检索</button>
<br/>
<button id="gA" type="button">检索所有</button>
<h5>name: <input id="aN" type="text"/> color: <input id="aC" type="text"/>
    <button id="add" type="button">新增</button>
</h5>
<table id="dogs">

</table>

<script type="text/javascript" src="static/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
    $(function () {
        // get by id
        $("#gBI").click(function () {
            var dId = $("#dId").val();
            if(isNaN(dId) || dId < 1){
                alert("请输入正整数！！！");
            }else{
                var str = "" + dId;
                var i = str.indexOf(".");
                if(i > 0){
                    dId = str.substring(0,i);
                }
                $.get("dog/getOne", {id: dId}, function (data) {
                    alert(JSON.stringify(data));
                })
            }
        });
        // get all
        $("#gA").click(function () {
            $.get("dog/getAll", function (data) {
                var dogs = data;
                if (dogs.length > 0) {
                    var th = "<thead><tr><th>id</th><th>name</th><th>color</th></tr></thead>";
                    $.each(dogs, function (i, dog) {
                        th += "<tr>";
                        $.each(dog, function (k, v) {
                            th += "<td>" + v + "</td>";
                        });
                        th += "</tr>";
                    });
                    $("#dogs").html(th);
                }
            });
        });
        // add one
        $("#add").click(function () {
            var name = $("#aN").val();
            var color = $("#aC").val();
            var dog = {"name": name, "color": color};

            // 解决加了SpringSecurity后不能发送Post请求的问题！！！
            var header = $("meta[name='_csrf_header").attr("content");
            var token = $("meta[name='_csrf']").attr("content");
            console.log(header);
            console.log(token);
            $.ajax({
                type: "POST",
                url: "dog/add",
                data: dog,
                beforeSend: function(xhr){
                    xhr.setRequestHeader(header,token)
                },
                success: function(){
                    $("#gA").click();
                }
            });
        });
    });


</script>

</body>
</html>