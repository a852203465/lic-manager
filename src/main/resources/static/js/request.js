function get(url, params) {
    var defer = $.Deferred();
    $.get(url, params, function (res) {
        defer.resolve(callback(res));
    },'json');
    return defer.promise();
}


function callback(res) {
    if (res.code !== 0) {
        layer.msg(res.message)
        return;
    }
    return res.data;
}





















