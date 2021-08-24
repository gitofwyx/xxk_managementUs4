//下拉提示框bsSuggest
function buildBsSuggest(cla, v,search,effective, callback) {
    var options = {
        /*url: "/getMaterialName?tab=" + v,*/
        data: v,
        effectiveFields: search,
        searchFields: search,
        effectiveFieldsAlias: effective,
        listAlign: 'auto',
        showBtn: false,
        autoMinWidth: true,
        getDataMethod: 'data',
        idField: "value",
        keyField: "text",
        clearable: true,
    };

    $("#" + cla).bsSuggest('init', options).on('onDataRequestSuccess', function (e, result) {
        console.log('onDataRequestSuccess: ', result);
    }).on('onSetSelectValue', function (e, keyword, data) {
        console.log('onSetSelectValue: ', keyword, data);
        //回调函数
        if (typeof callback === "function") {
            //调用它，既然我们已经确定了它是可调用的
            callback(keyword, data);
        }

    });


};