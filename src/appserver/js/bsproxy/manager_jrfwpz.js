
Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo :Ext.getBody(),
        height : 800
    });

    tabs.add({
        title : '服务状态',
        html : "<img src='../../img/bsexample/兼容服务配置-服务状态.jpg' />"
    });

    tabs.add({
        title : '基本参数',
        html : "<img src='../../img/bsexample/兼容服务配置-基本参数.jpg' />",

        layout : 'fit',
        id : 'abc'
        /*
         * , item:[grid]
         */
    });
    tabs.add({
        title : '证书配置',
        html : "<img src='../../img/bsexample/兼容服务配置-证书配置.jpg' />"
    });
    tabs.add({
        title : '信息绑定',
        html : "<img src='../../img/bsexample/兼容服务配置-信息绑定.jpg' />"
    });
    tabs.add({
        title : '用户策略',
        html :"<img src='../../img/bsexample/兼容服务配置-用户策略.jpg' /></br><img src='../../img/bsexample/兼容服务配置-用户策略2.jpg' /></br><img src='../../img/bsexample/兼容服务配置-用户策略3.jpg' />"
    });

    tabs.activate(0);
});