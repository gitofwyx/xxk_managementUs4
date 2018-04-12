/*var data = [
 {id: 1, address: "安徽", parent_id: 0},
 {id: 2, address: "江苏", parent_id: 0},
 {id: 3, address: "合肥", parent_id: 1},
 {id: 4, address: "庐阳区", parent_id: 3},
 {id: 5, address: "大杨镇", parent_id: 4},
 {id: 6, address: "南京", parent_id: 2},
 {id: 7, address: "玄武区", parent_id: 6},
 {id: 8, address: "梅园新村街道", parent_id: 7},
 {id: 9, address: "上海", parent_id: 0},
 {id: 10, address: "黄浦区", parent_id: 9},
 {id: 11, address: "外滩", parent_id: 10},
 {id: 12, address: "安庆", parent_id: 1}
 ];*/


//根据最后一个找到整个家族
function familyTree(arr, pid) {
    var temp = [];
    var forFn = function (arr, pid) {
        for (var i = 0; i < arr.length; i++) {
            var item = arr[i];
            if (item.id == pid) {
                temp.push(item);

                forFn(arr, item.parent_id);
            }
        }
    };
    forFn(arr, pid);
    return temp;
}

//子孙树，从顶级往下找到是有的子子孙孙
function sonsTree(arr, id) {
    var temp = [], lev = 0;
    var forFn = function (arr, id, lev) {
        for (var i = 0; i < arr.length; i++) {
            var item = arr[i];
            if (item.parent_id == id) {
                item.lev = lev;
                temp.push(item);
                forFn(arr, item.id, lev + 1);
            }
        }
    };
    forFn(arr, id, lev);
    return temp;
}

function GetMenus(id, arry) {
    //菜单列表html
    var menus =[];
//根据菜单主键id生成菜单列表html
//id：菜单主键id
//arry：菜单数组信息
    function GetData(id, arry) {
        var childArry = GetParentArry(id, arry);
        if (childArry.length > 0) {
           /* menus += '<ul>';*/
            for (var i in childArry) {
                menus.push( childArry[i]);
                GetData(childArry[i].id, arry);
                menus += '</li>';
            }
            menus += '</ul>';
        }
    }
    GetData(id, arry);
    return menus;
}


//根据菜单主键id获取下级菜单
//id：菜单主键id
//arry：菜单数组信息
function GetParentArry(id, arry) {
    var newArry = new Array();
    for (var i in arry) {
        if (arry[i].pid == id)
            newArry.push(arry[i]);
    }
    return newArry;
}

//console.log(sonsTree(data,0));

/*//利用 子孙树 生成dom结构
 var tree = sonsTree(data,0);
 var temp = [];
 for(var i=0;i<tree.length;i++){
 var item = tree[i],u = "";
 if(i>0){
 u = "</ul>";
 }
 if(item['lev']==0){
 temp.push(u+'<li><a class="one">'+item.address+'</a><ul>');
 }else{
 temp.push('<li><a>'+item.address+'</a></li>')
 }
 if(i+1==tree.length){
 temp.push("</ul>")
 }
 }*/
//console.log(temp.join(""));