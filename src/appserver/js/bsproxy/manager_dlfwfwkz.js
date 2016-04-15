
Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo :Ext.getBody(),
        height : 800,
        deferredRender:false
    });

    var data1=[
        [1, '移动应用代理服务', '192.168.1.1','tcp'],
        [2, 'SSL1.0兼容服务', '192.168.1.2','tcp'],
        [3, 'DNS服务', '192.168.1.3','http']
    ];
    var store1=new Ext.data.SimpleStore({data:data1,fields:["id","name","organization","homepage"]});
    var grid1 = new Ext.grid.GridPanel({
        height:100,
        width:600,
        columns:[{header:"服务名称",dataIndex:"name"},
            {header:"对外服务IP、端口",dataIndex:"organization"},
            {header:"服务协议",dataIndex:"homepage"}],
        store:store1,
        autoExpandColumn:2
    });
    tabs.add({
        title : '代理服务器状态',
        items : [
            {
                html : "图标说明：</br>" +
                    "<img src='../../js/ext/resources/images/default/dd/drop-yes.gif'/>服务已运行</br>" +
                    "<img src='../../img/bsexample/icon-error.jpg'/>代理系统上的服务未启动或不可用"
            },
            grid1
        ]


    });

    var data2=[
        [1, '【】', '192.168.1.1','tcp'],
        [2, '【】', '192.168.1.2','tcp'],
        [3, '【】', '192.168.1.3','http']
    ];
    var store2=new Ext.data.SimpleStore({data:data2,fields:["id","name","organization","homepage"]});
    var csm = new Ext.grid.CheckboxSelectionModel({
    });
    var grid2 = new Ext.grid.GridPanel({
        height:100,
        width:600,
        store:store2,
        columns:[
            csm,
            {header:"无线网关IP、端口",dataIndex:"organization"},
            {header:"操作",dataIndex:"button",renderer:xxbutton}
        ],
        autoExpandColumn:2
    });
    function xxbutton() {
        var returnStr = "<a href=''>修改</a>";
        return returnStr;
    }


    tabs.add({
        title : '访问控制',
        items : [
            {
                html : "设置可以访问管理系统的无限网关IP地址"

            },
            grid2,
            {
                html:"<button type='button'>添加</button><button type='button'>删除</button>"
            }
        ]
    });



    tabs.activate(0);
//    tabs.ownerCt.doLayout();
})
