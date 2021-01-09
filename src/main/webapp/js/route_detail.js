$(document).ready(function () {
    goImg();
    //自动播放
    var timer = setInterval("auto_play()", 1000);
});

function goImg() {
    //焦点图效果
    //点击图片切换图片
    $('.little_img').on('mousemove', function () {
        $('.little_img').removeClass('cur_img');
        var big_pic = $(this).data('bigpic');
        $('.big_img').attr('src', big_pic);
        $(this).addClass('cur_img');
    });
    //上下切换
    var picindex = 0;
    var nextindex = 4;
    $('.down_img').on('click', function () {
        var num = $('.little_img').length;
        if ((nextindex + 1) <= num) {
            $('.little_img:eq(' + picindex + ')').hide();
            $('.little_img:eq(' + nextindex + ')').show();
            picindex = picindex + 1;
            nextindex = nextindex + 1;
        }
    });
    $('.up_img').on('click', function () {
        var num = $('.little_img').length;
        if (picindex > 0) {
            $('.little_img:eq(' + (nextindex - 1) + ')').hide();
            $('.little_img:eq(' + (picindex - 1) + ')').show();
            picindex = picindex - 1;
            nextindex = nextindex - 1;
        }
    });
}
//自动轮播方法
function auto_play() {
    var cur_index = $('.prosum_left dd').find('a.cur_img').index();
    cur_index = cur_index - 1;
    var num = $('.little_img').length;
    var max_index = 3;
    if ((num - 1) < 3) {
        max_index = num - 1;
    }
    if (cur_index < max_index) {
        var next_index = cur_index + 1;
        var big_pic = $('.little_img:eq(' + next_index + ')').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(' + next_index + ')').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    } else {
        var big_pic = $('.little_img:eq(0)').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(0)').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    }
};

$(function () {
    //1.获取rid
    let rid = getParameter("rid");

    //2.发送请求route/findOne
    $.get("route/findOne",{rid:rid},function (route) {
        $("#rname").html(route.rname);
        $("#routeIntroduce").html(route.routeIntroduce);
        $("#price").html("&yen;"+route.price);
        $("#sname").html(route.seller.sname);
        $("#consphone").html(route.seller.consphone);
        $("#address").html(route.seller.address);
        $("#favoriteCount").html("已收藏"+route.count+"次");

        //图片展示
        let ddStr = '<br>';

        //遍历routeImgList
        for (let i = 0; i < route.routeImgList.length; i++) {
                let aStr = '<a class="little_img cur_img"\n' +
                '               data-bigpic="'+route.routeImgList[i].bigPic+'">\n' +
                '               <img src="'+route.routeImgList[i].smallPic+'">\n' +
                '           </a>';
            ddStr += aStr;
        }

        ddStr += '';

        $("#dd").html(ddStr);

        //图片展示和代码调用
        goImg();
    });
});

$(function () {
    //发送请求，判断用户是否收藏过该线路
    var rid = getParameter("rid");
    $.get("route/isFavorite",{rid:rid},function (flag) {
        if (flag){
            //用户已经收藏过
            //设置收藏按钮的样式
            $("#favorite").addClass("already");
            $("#favorite").attr("disabled","disabled");

            //删除按钮的点击事件
            $("#favorite").removeAttr("onclick");
        }else {

        }
    });
});

//点击收藏按钮触发的方法
function addFavorite() {
    let rid = getParameter("rid");
    //1.判断是否登录
    $.get("user/findOne",{},function (user) {
        if (user){
            //用户登录了
            //添加登录
            $.get("route/addFavorite",{rid:rid},function () {
                //代码刷新页面
                location.reload();
            })
        }else{
            //用户没有登录
            alert("您尚未登录，请登录");
            location.href = "http://localhost/travel/login.html";
        }
    });
}