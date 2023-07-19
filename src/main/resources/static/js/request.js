$.ajaxSetup({
    async: false,
    // headers: {
    //     'Authorization': 'Bearer ' + token
    // }
});

function get(url, params) {
    let data;
    $.get(url, params, function (res) {
        data = callback(res);
    },'json');
    return data;
}

function post(url, params) {
    let data;
    $.post(url, params, function(res){
        data = callback(res);
    },'json');
    return data;
}







function callback(res) {
    if (res.code !== 0) {
        layer.msg(res.message)
        return;
    }
    return res.data;
}





















