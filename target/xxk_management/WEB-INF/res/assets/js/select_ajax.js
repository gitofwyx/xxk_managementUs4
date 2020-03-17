$.ajax({
    url: '/getOfficeSelect',
    type: 'POST', //GET
    async: true,    //或false,是否异步
    /*timeout: 5000,*/    //超时时间(如果后台数据加载缓慢，设置超时时间会导致在时间超时后自动执行success函数而后台还没有返回data数据)
    dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
    success: function (data, textStatus, jqXHR) {
        if (data != null && data != undefined) {
            select_data = data.office_name;
            belong_to_select.set('items', select_data);
        }
    },
    error: function (xhr, textStatus) {
        console.log('错误')
        console.log(xhr)
        console.log(textStatus)
    }
})