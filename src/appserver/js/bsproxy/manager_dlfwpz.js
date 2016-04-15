Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo :Ext.getBody(),
        height : 800
    });

    tabs.add({
        title : '服务状态',
        html : "<img src='../../img/bsexample/代理服务配置-服务状态.jpg'/>"
    });

    tabs.add({
        title : '基本参数',
        html : "<img src='../../img/bsexample/代理服务配置-基本参数.jpg'/>"
    });
    tabs.add({
        title : '代理证书',
        html : "<img src='../../img/bsexample/代理服务配置-代理证书1.jpg'/></br><img src='../../img/bsexample/代理服务配置-代理证书2.jpg'/>"
    });
    tabs.add({
        title : 'HTTPS代理',
        html : "<img src='../../img/bsexample/代理服务配置-HTTPS代理1.jpg'/></br><img src='../../img/bsexample/代理服务配置-HTTPS代理2.jpg'/>"
    });


    tabs.activate(0);
});