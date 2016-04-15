
Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo :Ext.getBody(),
        height : 800
    });
    tabs.add({
        title : '服务状态',
        html : "<table border='1'><tr><td>服务状态</td><td>未启动[启动]</td></tr><tr><td>运行时间</td><td>服务未启动</td></tr><tr><td>在线服务器</td><td>服务未启动</td></tr><tr><td>服务日志</td><td>运行日志|打包下载</td></tr></table>"
    });

    tabs.add({
        title : '基本参数',
        html : "<img src='../../img/bsexample/服务器配置-基本参数.jpg' />"
    });
    tabs.add({
        title : '证书配置',
        html : "<img src='../../img/bsexample/服务器配置-证书配置.jpg' />"
    });
    tabs.add({
        title : '访问控制',
        html : "<img src='../../img/bsexample/服务器配置-访问控制1.jpg' /></br><img src='../../img/bsexample/服务器配置-访问控制2.jpg' />"
    });
    tabs.add({
        title : '路由配置',
        html :  "<img src='../../img/bsexample/服务器配置-路由配置1.jpg' /></br><img src='../../img/bsexample/服务器配置-路由配置2.jpg' /></br><img src='../../img/bsexample/服务器配置-路由配置3.jpg' /></br><img src='../../img/bsexample/服务器配置-路由配置4.jpg' />"
    });
    tabs.add({
        title : '高级配置',
        html : "<img src='../../img/bsexample/服务器配置-高级配置.jpg' />"
    });

    tabs.activate(0);
});

