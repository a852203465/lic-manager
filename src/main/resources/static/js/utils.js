function ts2Time(timestamp) {
    if (isNull(timestamp)) {
        return '';
    }
    if (typeof timestamp === 'string') {
        timestamp = Number(timestamp);
    }
    if (typeof timestamp !== 'number') {
        alert("输入参数无法识别为时间戳");
    }
    let date = new Date(timestamp);
    let Y = date.getFullYear() + '-';
    let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    let D = date.getDate() + ' ';
    let h = date.getHours() + ':';
    let m = date.getMinutes() + ':';
    let s = date.getSeconds();
    return Y + M + D + h + m + s;
}

function isNull(obj) {
    return typeof obj == "undefined" || obj == null || obj === "";
}

function isNotNull(obj) {
    return !isNull(obj);
}













