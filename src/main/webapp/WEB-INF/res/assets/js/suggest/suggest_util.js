//下拉提示框bsSuggest
function buildBsSuggest(cla, v,effective, search, Alias, onSet_ck,onUnset_ck) {
    var options = {
        /*url: "/getMaterialName?tab=" + v,*/
        data: v,
        effectiveFields: effective,
        searchFields: search,
        effectiveFieldsAlias: Alias,
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
        if (typeof onSet_ck === "function") {
            //调用它，既然我们已经确定了它是可调用的
            onSet_ck(keyword, data);
        }

    }).on('onUnsetSelectValue', function () {
        if (typeof onUnset_ck === "function") {
            //调用它，既然我们已经确定了它是可调用的
            onUnset_ck();
        }
    });


};

//下拉提示框bsSuggest
function buildBsSuggestByUrl(cla, url,effective, search, Alias, onSet_ck,onUnset_ck) {
    var options = {
        url: url,
        effectiveFields: effective,
        searchFields: search,
        effectiveFieldsAlias: Alias,
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
        if (typeof onSet_ck === "function") {
            //调用它，既然我们已经确定了它是可调用的
            onSet_ck(keyword, data);
        }

    }).on('onUnsetSelectValue', function () {
        if (typeof onUnset_ck === "function") {
            //调用它，既然我们已经确定了它是可调用的
            onUnset_ck();
        }
    });


};

//下拉提示框bsSuggest
function buildBsSuggestIO(cla,options, onSet_ck,onUnset_ck) {

    $("#" + cla).bsSuggest('init', options).on('onDataRequestSuccess', function (e, result) {
        console.log('onDataRequestSuccess: ', result);
    }).on('onSetSelectValue', function (e, keyword, data) {
        console.log('onSetSelectValue: ', keyword, data);
        //回调函数
        if (typeof onSet_ck === "function") {
            //调用它，既然我们已经确定了它是可调用的
            onSet_ck(keyword, data);
        }

    }).on('onUnsetSelectValue', function () {
        if (typeof onUnset_ck === "function") {
            //调用它，既然我们已经确定了它是可调用的
            onUnset_ck();
        }
    });


};

function buildBsSuggestOnRequest(cla,options,onReq_ck, onSet_ck,onUnset_ck) {

    $("#" + cla).bsSuggest('init', options).on('onDataRequestSuccess', function (e, result) {
        console.log('onDataRequestSuccess: ', result);
        if (typeof onReq_ck === "function") {
            //调用它，既然我们已经确定了它是可调用的
            onReq_ck(e, result);
        }
    }).on('onSetSelectValue', function (e, keyword, data) {
        console.log('onSetSelectValue: ', keyword, data);
        //回调函数
        if (typeof onSet_ck === "function") {
            //调用它，既然我们已经确定了它是可调用的
            onSet_ck(keyword, data);
        }
    }).on('onUnsetSelectValue', function () {
        if (typeof onUnset_ck === "function") {
            //调用它，既然我们已经确定了它是可调用的
            onUnset_ck();
        }
    });
};

function initBsSuggest(cla) {

    $("#" + cla).bsSuggest('destroy');

}