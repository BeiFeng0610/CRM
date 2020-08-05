<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>

    $.ajax({
        url : "",
        data : {

        },
        type : "",
        dataType : "",
        success : function (data) {

        }
    })


    String createTime = DateTimeUtil.getSysTime();
    String createBy = ((User)request.getSession().getAttribute("user")).getName();


    $(".time").datetimepicker({
        minView: "month",
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });


    if (data.success){

    }else {

        alert("")

    }


    // 为全选的复选框绑定事件，触发全选操作
    $("#qx").click(function () {

        $("input[name=xz]").prop("checked",this.checked);

    })


    // 动态全选
    // 因为动态生成的元素是不能以普通绑定事件的形式操作
    // 动态生成的元素，我们要以on方法的形式来触发事件
    // 语法：$(需要绑定元素的有效的外层元素).on(绑定事件的方式，需要绑定事件的元素的jQuery对象，回调函数)
    $("#activitySearchBody").on("click",$("input[name=xz]"),function () {

        $("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length);

    })
</body>
</html>
