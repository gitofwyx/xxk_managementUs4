function localstorageIO(ioKey, url, isReset, ioValue) {
    if (window.localStorage && (window.localStorage.setItem('a', 123) , window.localStorage.getItem('a') == 123)) {
        var stroage = window.localStorage;
        if (stroage.getItem(ioKey) == null || stroage.getItem(ioKey) == undefined || isReset == true) {
            if (ioValue != undefined && ioValue != null) {
                stroage.setItem(ioKey, ioValue);
                return null;
            }
            if (url != undefined && url != null) {
                $.ajax({
                    url: url,
                    type: 'POST', //GET
                    async: true,    //或false,是否异步
                    /*timeout: 5000,*/    //超时时间(如果后台数据加载缓慢，设置超时时间会导致在时间超时后自动执行success函数而后台还没有返回data数据)
                    dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                    success: function (data, textStatus, jqXHR) {
                        if (data != null && data != undefined) {
                            stroage.setItem(ioKey, JSON.stringify(data.result));
                        }
                    },
                    error: function (xhr, textStatus) {
                        console.log('错误')
                        console.log(xhr)
                        console.log(textStatus)
                    }
                })
            }
        } else {
            return JSON.parse(stroage.getItem(ioKey));
        }
    }
    return null;
}

function getOfficesEnum(offices) {
    var officesEnum = localstorageIO("officesEnum");
    if (officesEnum == null || officesEnum == undefined) {
        if (!$.isEmptyObject(offices) && offices != null) {
            officesEnum = {};
            for (var i in offices) {
                var key = offices[i].value;
                var value = offices[i].text;
                officesEnum[key] = value;
            }
            localstorageIO("officesEnum", null, officesEnum);
        }
    }
    return officesEnum;
}