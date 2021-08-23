//入库总量生成
function reckon_stock_total(num, stock_proportion) {  //（输入的库存数量 库存比例)
    //I_Form.getField('in_confirmed_no').get('value');
    num = "" + num;//防止输入数值造成replace函数报错；
    var decimal = num.replace(/\d+\.(\d*)/, "$1");//获取小数部分
    var unit = parseInt(num);//去除小数部分
    stock_proportion = parseInt(stock_proportion);//防止比例数输入为小数
    if (parseFloat(num) != unit) {
        unit = unit * stock_proportion;
        num = unit + parseInt(decimal);
        return num;
    }
    num = unit * stock_proportion;
    return num;
}

//根据比例调整库存数量
function calculate_amount(num, stock_proportion) { //数量，比例数
                                                   //I_Form.getField('in_confirmed_no').get('value');
    num = "" + num;//防止输入数值造成replace函数报错；
    var decimal = num.replace(/\d+\.(\d*)/, "$1");//获取小数部分
    var unit = parseInt(num);//去除小数部分
    stock_proportion = parseInt(stock_proportion);//防止比例数输入为小数
    decimal = parseInt(decimal);
    if (parseFloat(num) == unit) {
        return num;
    } else if (decimal >= stock_proportion) {
        var mu = parseInt(decimal / stock_proportion);//求小数部分有多少单位数量
        unit = unit + mu; //单位数量相加
        decimal = decimal - (mu * stock_proportion);
        while (decimal >= 1) {
            decimal = decimal / 10;
        }
        num = unit + decimal;
    }
    return num;
}

//根据库存总量生成库存数量
function makeInNum(total, stock_proportion) { //总量，比例数
    stock_proportion = parseInt(stock_proportion);
    var num = parseInt(total)% stock_proportion;
    while (num >= 1) {
        num = num / 10;
    }
    num = parseInt(total / stock_proportion) + num;
    if (!num || num == 'NaN') {
        return 0;
    }
    return num;
}

//数量根据比例重置
function conversion_Num(num, no_1, no_2) {
    no_1 = parseInt(no_1);
    no_2 = parseInt(no_2);
    no_1 = reckon_stock_total(num, no_1);
    return makeInNum(no_1, no_2);
}

//库存科室下拉框数据生成
function stock_officeSelect(offices_select, stock_office) {
    if (stock_office == undefined || $.isEmptyObject(stock_office)) {
        for (var i in offices_select) {
            if (offices_select[i].office_function == '1') {
                stock_office[offices_select[i].value] = offices_select[i].text;
            }
        }
    }
    return stock_office;
}

//生成库存键值对用于GridForenumRenderer
function getOfficeMakeGrid(offices_select, stock_office) {
    if (stock_office == undefined || $.isEmptyObject(stock_office)) {
        for (var i in offices_select) {

            stock_office[offices_select[i].value] = offices_select[i].text;

        }
    }
    return stock_office;
}

//判断数据是否为Null或者undefined或者为空字符串
function CheckIsNullOrEmpty(value) {
    //正则表达式用于判斷字符串是否全部由空格或换行符组成
    var reg = /^\s*$/
    //返回值为true表示不是空字符串
    return (value != null && value != undefined && !reg.test(value))
}