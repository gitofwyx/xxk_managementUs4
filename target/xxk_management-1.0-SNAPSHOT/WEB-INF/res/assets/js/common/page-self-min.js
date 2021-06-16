$(function () {

    var CLS_ACTION = 'page-action',
        FIELD_MAP = {
            'data-id' :'id',
            'title' : 'title',
            'data-href' : 'href',
            'data-close' : 'isClose',
            'data-search' : 'search',
            'data-mid' : 'moduleId',
            'data-type' : 'type'
        };

    function parseParams(attrs){
        var rst = {};
        $.each(attrs,function(index,attr){
            var name = attr.nodeName,
                filedName = FIELD_MAP[name];
            if(filedName){
                rst[filedName] = attr.nodeValue;
            }
        });
        return rst;
    }
    //只有在被框架页嵌套时，才起效
    if(top.topManager){
        $('body').delegate('.' + CLS_ACTION, 'click',function(ev){
            var sender = ev.currentTarget,
                attrs = sender.attributes,
                params = parseParams(attrs);
            if(!params.type || params.type == 'open'){
                top.topManager.openPage(params);
                ev.preventDefault();
                top.topManager.setPage(params.moduleId,params.id,params.type,params.search);
            }else if(params.type == 'setTitle'){
                top.topManager.setPageTitle(params.title,params.moduleId);
            }else{
                ev.preventDefault();
                top.topManager.operatePage(params.moduleId,params.id,params.type,params.search);
            }
        });
    }
    //释放所有的控件
    $(window).on('unload',function(){
        BUI.Component.Manager.eachComponent(function(component){
            component.destroy();
        });
    });

    var vars=window.location.pathname;
    vars = vars.substr(-36,36);
    if(vars=='/menu/delivery_tab/EV'||vars.length!=36){
        $("#stock_id").val('');
    }else if(vars.length==36){
        $("#stock_id").val(vars);
    }

});