// var basePath = 'http://j27xrj.natappfree.cc';
var basePath = 'http://localhost';
var cityMap = new Map();

// ($(initCityMap()))

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return (false);
}

function initCityMap() {
    $.ajax({
        type: "get",
        url: "static/city.json",
        async: false,//同步  true ：异步
        dataType: "json",
        success: function (result) {
            var dataObj = result.provinces;
            con = "";
            for (var i = 0; i < dataObj.length; i++) {
                cityMap.set(dataObj[i].code, dataObj[i].name);
            }
        },
    });
}

Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}

//退出功能
function logout(){
    //请求登出的接口
    //重新跳转到登录页面
}