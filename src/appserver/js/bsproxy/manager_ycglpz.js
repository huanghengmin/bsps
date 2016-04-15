
Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo :Ext.getBody(),
        height : 800
    });

    tabs.add({
        title : '服务状态',
        html : "<img src='../../img/bsexample/远程管理配置-服务状态.jpg'/>"
    });

    tabs.add({
        title : '基本参数',
        html :  "<img src='../../img/bsexample/远程管理配置-基本参数.jpg'/>"
    });
    tabs.add({
        title : '证书配置',
        html : "<img src='../../img/bsexample/远程管理配置-证书配置.jpg'/>"
    });
    tabs.add({
        title : '策略网段',
        html : "<img src='../../img/bsexample/远程管理配置-策略网段.jpg'/></br><img src='../../img/bsexample/远程管理配置-策略网段2.jpg'/></br><img src='../../img/bsexample/远程管理配置-策略网段3.jpg'/>"
    });
    tabs.add({
        title : '接入控制',
        html : "<img src='../../img/bsexample/远程管理配置-接入控制.jpg'/>"
    });
    tabs.add({
        title : '高级配置',
        html : "<img src='../../img/bsexample/远程管理配置-高级配置.jpg'/>"
    });

    tabs.activate(0);
});
