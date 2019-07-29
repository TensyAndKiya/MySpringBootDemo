<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <title>文件上传</title>
</head>
<body>

<form id="form1" enctype="multipart/form-data">
    <input name="file1" type="file" />
    <input name="file2" type="file" />
</form>
<input type="button" id="doUpload" value="上传" />

<script type="text/javascript" src="static/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
    $(function () {
        $("#doUpload").click(function(){

            var formData = new FormData($('#form1')[0]);
            var token = {
                token:"hasaki"
            };
            formData.append("token",JSON.stringify(token));

            console.log(formData);

            $.ajax({
                url : "file/upload",
                type : "post",
                cache : false,
                data : formData,
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