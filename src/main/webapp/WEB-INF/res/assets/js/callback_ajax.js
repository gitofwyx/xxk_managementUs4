function CD_ajaxNoParam(url,async,callback){
    $.ajax({
        url:url,
        type: 'POST', //GET
        async: async,    //或false,是否异步
        /*timeout: 5000,*/    //超时时间(如果后台数据加载缓慢，设置超时时间会导致在时间超时后自动执行success函数而后台还没有返回data数据)
        dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
        success: function (data, textStatus, jqXHR) {
            if (typeof callback === "function") {
                //调用它，既然我们已经确定了它是可调用的
                 callback(data);
            }
        },
        error: function (xhr, textStatus) {
            console.log('错误')
            console.log(xhr)
            console.log(textStatus)
        }
    })
}

function CD_ajaxAllParam(url,v,async,callback,e_callback){
    $.ajax({
        url:url,
        data: v,
        type: 'POST', //GET
        async: async,    //或false,是否异步
        /*timeout: 5000,*/    //超时时间(如果后台数据加载缓慢，设置超时时间会导致在时间超时后自动执行success函数而后台还没有返回data数据)
        dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
        success: function (data, textStatus, jqXHR) {
            if (typeof callback === "function") {
                //调用它，既然我们已经确定了它是可调用的
                callback(data);
            }
        },
        error: function (xhr, textStatus) {
            console.log('错误')
            console.log(xhr)
            console.log(textStatus)
            if (typeof e_callback === "function") {
                //调用它，既然我们已经确定了它是可调用的
                e_callback(xhr);
            }
        }
    })
}

