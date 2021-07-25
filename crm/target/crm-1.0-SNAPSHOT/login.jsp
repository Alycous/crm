<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">

        $(function () {
            if (window.top != window) {
                window.top.location = window.location;
            }


            $("#logAct").focus();
            $("#logAct").val("");
            document.onkeydown = function (event) {
                if (event.keyCode == 13) {
                    //登录验证
                    // document.location.href = "workbench/index.jsp";
                    login()
                }
            }
            $("#logBtn").click(function () {
                //登录验证
                login()
            })

        })

        function login() {
            // alert("执行验证")
            var logAct = $.trim($("#logAct").val());
            var logPwd = $.trim($("#logPwd").val());
            /*alert(logAct)
            alert(logPwd)*/
            if (logAct == "" || logPwd == "") {
                $("#msg").html("账号或密码不可为空!")
                return false;
            }
            $.ajax({
                url: "settings/user/login.do",
                data: {"logAct": logAct, "logPwd": logPwd},
                dataType: "json",
                type: "post",
                success: function (data) {
                    if (data.success) {
                        document.location.href = "workbench/index.jsp";
                    } else {
                        $("#msg").html(data.msg);
                    }
                }
            })
        }

    </script>
</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="image/nico1080p.png" style="width: 1465px; height: 730px; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
        CRM &nbsp;<span style="font-size: 12px;">&copy;2021 by QL</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1 style="color: white">login</h1>
        </div>
        <form action="workbench/index.jsp" class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" type="text" id="logAct" placeholder="username">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" type="password" id="logPwd" placeholder="password">
                </div>
                <div class="checkbox" style="position: relative;top: 30px; left: 10px;">

                    <span id="msg" style="color: purple">请输入账号密码</span>

                </div>
                <button type="button" id="logBtn" class="btn btn-primary btn-lg btn-block"
                        style="width: 350px; position: relative;top: 45px;">login
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>