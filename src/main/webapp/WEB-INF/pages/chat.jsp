<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>

<body>

<input type="text" id="message" /><button onclick="sendMessage()">Send Message</button>
<button onclick="closeChat()">Close Chat</button>
<div id="messageDiv">

</div>


<script src="/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
    var websocket = null;
    if('WebSocket' in window){
        websocket = new WebSocket("ws://localhost:8888/socket/chat");
    }else{
        alert("您的浏览器不支持WebSocket！请换个浏览器再访问本站！");
    }

    if(null !== websocket){
        //websocket method
        websocket.onerror = function(){
            setMessageInnerHtml("ERROR!!!");
        };
        websocket.onopen = function(){
            setMessageInnerHtml("OPEN");
        };
        websocket.onmessage = function(event){
            setMessageInnerHtml(event.data);
        };
        websocket.onclose = function(){
            setMessageInnerHtml("CLOSE");
        };

        window.onbeforeunload = function(){
            websocket.close();
        }
    }

    //js method
    function setMessageInnerHtml(message){
        var $messageDiv = $("#messageDiv");
        var oldMessage = $messageDiv.html();
        $messageDiv.html(oldMessage + "<br/>" + message);
    }
    function closeChat(){
        if(null !== websocket){
            websocket.close();
        }
    }
    function sendMessage(){
        if(null !== websocket){
            var message = $("#message").val();
            websocket.send(message);
        }
    }

</script>

</body>
</html>