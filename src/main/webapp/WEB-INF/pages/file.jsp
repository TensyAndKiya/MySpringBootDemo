<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <title>文件上传</title>
</head>
<body>

<form id="form1" enctype="multipart/form-data">
    <input name="file" type="file" />
    <input type="button" id="doUpload" value="上传" />
</form>
<script type="text/javascript" src="static/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
    $(function () {
        $("#doUpload").click(function(){

            console.log(new FormData($('form1')[0]));

            $.ajax({
                url : "file/upload",
                type : "post",
                cache : false,
                data : new FormData($('#form1')[0]),
                contentType : false,
                processData : false,
                dataType : "text",
                success : function(result){
                    alert(result);
                },
                error : function () {
                    alert("fail");
                }
            });
        });
    });
</script>

</body>
</html>