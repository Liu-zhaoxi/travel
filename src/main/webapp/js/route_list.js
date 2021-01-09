$(function () {
    //获取cid的参数值
    let cid = getParameter("cid");
    //获取rname的参数值
    let rname = getParameter("rname");

    //判断rname如果不为null或者""
    if (rname){
        //url解码为中文
        rname = window.decodeURIComponent(rname);
    }else {
        rname = ""; //没有搜索时
    }

    //当页码加载完成后，调用load方法，发送ajax请求加载数据
    load(cid,null,rname);

});

function load(cid,currentPage,rname) {
    /**
     * 分页工具条代码
     */
    //发送ajax请求，请求route/pageQuery，传递cid
    $.get("route/pageQuery", {cid: cid,currentPage:currentPage,rname:rname}, function (pageBean) {
        //解析pageBean数据，展示到页面上
        //1.分页工具条数据展示
        //1.1 展示总页码和总记录数
        $("#totalPage").html(pageBean.totalPage);
        $("#totalCount").html(pageBean.totalCount);

        //1.2 展示分页页码
        let page_list = "";
        let firstPage = '<li class="page" onclick="javascript:load('+cid+',1,\''+rname+'\');"><a href="javascript:void(0);">首页</a></li>';

        //计算上一页的页码
        let beforeNum = pageBean.currentPage - 1;
        //判断上一页的页码，防止跳到第0页
        if (beforeNum <= 0){
            beforeNum = 1;
        }
        let beforePage = '<li class="page" onclick="javascript:load('+cid+','+beforeNum+',\''+rname+'\');"><a href="javascript:void(0);">上一页</a></li>';

        page_list += firstPage;
        page_list += beforePage;
        /*
            1.一共展示10个页码，能够达到前5后4的效果
            2.如果前边不够5个，后边补齐10个
            3.如果后边不足4个，前边补齐10个
         */
        //定义开始和结束位置
        let begin;
        let end;

        //1.要显示10个页码
        if (pageBean.totalPage < 10){
            begin = 1;
            end = pageBean.totalPage;
        }
        else {
            //总页码数超过10页
            begin = pageBean.currentPage - 5;
            end = pageBean.currentPage + 4;

            //2.如果前边不够5个，后边补齐10个
            if (begin < 1){
                begin = 1;
                end = begin + 9;
            }

            //3.如果后边不足4个，前边补齐10个
            if (end > pageBean.totalPage){
                end = pageBean.totalPage;
                begin = end -9;
            }
        }

        for (let i = begin; i <= end; i++) {
            let li;
            //判断当前页码是否等于i
            if (pageBean.currentPage == i){
                li = '<li class="curPage" onclick="javascript:load('+cid+','+i+',\''+rname+'\');"><a href="javascript:void(0);">' + i + '</a></li>';
            }else {
                //创建页码的li
                li = '<li onclick="javascript:load('+cid+','+i+',\''+rname+'\');"><a href="javascript:void(0);">' + i + '</a></li>';
            }
            //拼接字符串
            page_list += li;
        }

        //计算下一页的页码
        let nextNum = pageBean.currentPage + 1;
        let lastNum = pageBean.totalPage;
        //判断下一页的页码，防止跳到第0页
        if (nextNum > lastNum){
            nextNum = pageBean.totalPage;
        }
        let nextPage = '<li class="page" onclick="javascript:load('+cid+','+nextNum+',\''+rname+'\');"><a href="javascript:void(0);">下一页</a></li>';
        let lastPage = '<li class="page" onclick="javascript:load('+cid+','+pageBean.totalPage+',\''+rname+'\');"><a href="javascript:void(0);">末页</a></li>';

        page_list += nextPage;
        page_list += lastPage;
        //将list内容设置到ul
        $("#paging").html(page_list);

        /**
         * 展示数据
         * @type {string}
         */
        //2.列表数据展示
        let route_list = '';

        for (let i = 0; i < pageBean.list.length; i++) {
            let route = pageBean.list[i];

            let li = '<li>' +
'                        <div class="img col-md-6"><img src="' + route.rimage + '" alt="" style="width: 300px"></div>' +
'                        <div class="text col-md-4">' +
'                            <p>' + route.rname + '</p>' +
'                            <p>' + route.routeIntroduce + '</p>' +
'                        </div>' +
'                        <div class="price col-md-2">' +
'                            <p class="price_num">' +
'                                <span>&yen;</span>' +
'                                <span>' + route.price + '</span>' +
'                                <span>起</span>' +
'                            </p>' +
'                            <p><a href="route_detail.html?rid='+route.rid+'">查看详情</a></p>' +
'                        </div>' +
'                    </li>';
            route_list += li;
        }
        $("#route").html(route_list);

        //定位到页面的顶部
        //scrollTo()把内容滚动到指定的坐标。
        window.scrollTo(0,0);
    });
};