var basePath = 'http://localhost:8080';
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