function exit() {
    location.href = "user/exit";
};

//根据传递过来的参数name获取对应的值
function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    var r = location.search.substr(1).match(reg);
    if (r!=null) return (r[2]); return null;
};

$(function () {

    //查询分类数据
    $.get("category/findAll", {}, function (data) {
        var list = '<li><a href="index.html">首页</a></li>';
        //遍历数组，拼接字符串(<li></li>)
        for (var i = 0; i < data.length; i++) {
            var li = '<li><a href="route_list.html?cid='+data[i].cid+'">'+data[i].cname+'</a></li>';
            list += li;
        }
        //将list字符串设置到ul的html内容中
        $("#category").html(list);
    });

    //查询用户信息
    $.get("user/findOne", {}, function (data) {
        var msg = "欢迎回来，" + data.name;
        $("#span_username").html(msg);
        var exit = "&nbsp;&nbsp;<a onclick='exit()'>退出</a>";
        $("#span_username").append(exit);
        $("#span_username").css("font-size", "16px", "color", "#5bc0de");
        $("#span_username").css("width", "auto", "margin", "0 auto");
        $("#span_username").css("line-height", "50px");
    });

    //给搜索按钮绑定绑定单击事件，获取搜索输入框的内容
    $("#search-btn").click(function () {
        //线路名称
        let rname = $("#search-input").val();

        let cid = getParameter("cid");
        //跳转路径
        location.href="http://localhost/travel/route_list.html?cid="+cid+"&rname="+rname;
    });
});


