//入库总量生成
function reckon_stock_total(num, stock_proportion) {  //（输入的库存数量 库存比例)
  //I_Form.getField('in_confirmed_no').get('value');
  var decimal =num.replace(/\d+\.(\d*)/,"$1");//获取小数部分
  var unit = parseInt(num);//去除小数部分
  stock_proportion = parseInt(stock_proportion);//防止比例数输入为小数
  if(parseFloat(num)!=unit){
    unit=unit*stock_proportion;
    return unit+parseInt(decimal);
  }
  return unit*stock_proportion;
}

//根据比例调整库存数量
function calculate_amount(num, stock_proportion) { //数量，比例数
  //I_Form.getField('in_confirmed_no').get('value');
  var decimal =num.replace(/\d+\.(\d*)/,"$1");//获取小数部分
  var unit = parseInt(num);//去除小数部分
  stock_proportion = parseInt(stock_proportion);//防止比例数输入为小数
  decimal=parseInt(decimal);
  if(parseFloat(num)==unit){
    return num;
  }
  else if (decimal >= stock_proportion) {
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
  var num=total%stock_proportion;
  while (num>=1){
    num= num/10;
  }
  num=parseInt(total/stock_proportion)+num;
  return num;
}