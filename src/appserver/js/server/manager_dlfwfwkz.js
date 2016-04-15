Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo :Ext.getBody(),
        height : 800,
        deferredRender:false
    });

    var sscsm = new Ext.grid.CheckboxSelectionModel();
    var sscm = new Ext.grid.ColumnModel([
        sscsm,
        {
            header : 'ID',
            dataIndex : 'id',
            width:160,
            hidden:true
        }, {
            header : "服务名称",
            dataIndex : 'servename',
            width:160
        }, {
            header : "对外服务IP、端口",
            dataIndex : 'ipport',
            width:160
        }, {
            header : "服务协议",
            dataIndex : 'serveagreement',
            width:160
        }
    ]);
    sscm.defaultSortable = true;

    var ssds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
//            url : '../../ResourceAction_findAllResources.action'
            url : '../../AccessControlAction_findServeStatus.action'

        }),//调用的动作
        reader : new Ext.data.JsonReader({
            totalProperty : 'servelist',
            root : 'serverow'
        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'int'
        }, {
            name : 'servename',
            mapping : 'servename',
            type : 'string'
        }, {
            name : 'ipport',
            mapping : 'ipport',
            type : 'string'
        }, {
            name : 'serveagreement',
            mapping : 'serveagreement',
            type : 'string'
        }//列的映射
        ])
    });
    ssds.load();

    var ssgrid = new Ext.grid.GridPanel({
        // var grid = new Ext.grid.EditorGridPanel( {
        /* collapsible : true,// 是否可以展开
         animCollapse : false,// 展开时是否有动画效果*/
        id : 'ssgrid',
        title : '代理服务状态列表',
        store : ssds,
        cm : sscm,
        sm:sscsm,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
//        renderTo :Ext.getBody(), /*'noteDiv',*/
        /*
         * // 添加内陷的按钮 buttons : [ { text : '保存' }, { text : '取消' }],
         * buttonAlign : 'center',// 按钮对齐
         *
         */
        // 添加分页工具栏
//        bbar : new Ext.PagingToolbar({
//            pageSize : 10,
//            store : wapds,
//            displayInfo : true,
//            displayMsg : '显示 {0}-{1}条 / 共 {2} 条',
//            emptyMsg : "无数据。"
//        }),
        // 添加内陷的工具条
        viewConfig:{
            autoFill:true
            //forceFit:true
        },
        tbar : [ //工具栏
        ],
//        width : 1472,
        height :600,
        frame : true,
        loadMask : true,// 载入遮罩动画
        autoShow : true
    });
    tabs.add({
        title : '代理服务器状态',
        items : [
            {
                html : "图标说明：</br>" +
                    "<img src='../../js/ext/resources/images/default/dd/drop-yes.gif'/>服务已运行</br>" +
                    "<img src='../../img/bsexample/icon-error.jpg'/>代理系统上的服务未启动或不可用"
            },
            ssgrid
        ]


    });
//访问控制------------------------------------------------------------------------------------------访问控制--------------------------------------------------------------------------------访问控制------------------------------
    var wapcsm = new Ext.grid.CheckboxSelectionModel();
    var wapcm = new Ext.grid.ColumnModel([
        wapcsm,
        {
            header : 'ID',
            dataIndex : 'id',
            width:160,
            hidden:true
        }, {
            header : "无线网关IP",
            dataIndex : 'wapip',
            width:160
        },{
            header:"操作",
            dataIndex:"button",
            renderer:xxbutton,
            width:100
        }
    ]);
    wapcm.defaultSortable = true;
    function xxbutton() {
        var returnStr = "<a onclick='updmodel()'>修改</a>";
        return returnStr;

    }
    var wapds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
//            url : '../../ResourceAction_findAllResources.action'
            url : '../../AccessControlAction_findAllWapControls.action'

        }),//调用的动作
        reader : new Ext.data.JsonReader({
            totalProperty : 'waplist',
            root : 'waprow'
        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'int'
        }, {
            name : 'wapip',
            mapping : 'wapip',
            type : 'string'
        }//列的映射
        ])
    });
    wapds.load();

    var wapgrid = new Ext.grid.GridPanel({
        // var grid = new Ext.grid.EditorGridPanel( {
        /* collapsible : true,// 是否可以展开
         animCollapse : false,// 展开时是否有动画效果*/
        id : 'wapgrid',
        title : '可以访问的无线网关IP地址列表',
        store : wapds,
        cm : wapcm,
        sm:wapcsm,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
//        renderTo :Ext.getBody(), /*'noteDiv',*/
        /*
         * // 添加内陷的按钮 buttons : [ { text : '保存' }, { text : '取消' }],
         * buttonAlign : 'center',// 按钮对齐
         *
         */
        // 添加分页工具栏
//        bbar : new Ext.PagingToolbar({
//            pageSize : 10,
//            store : wapds,
//            displayInfo : true,
//            displayMsg : '显示 {0}-{1}条 / 共 {2} 条',
//            emptyMsg : "无数据。"
//        }),
        // 添加内陷的工具条
        viewConfig:{
            autoFill:true
            //forceFit:true
        },
        tbar : [ {
            id : 'New1',
            text : ' 添加  ',
            tooltip : '添加一个无线网关IP',
            iconCls : 'addbutton',
            handler : function() {
                createMyform();
                winopen(myform);
            }
        }, {
            text : ' 删除 ',
            tooltip : '删除被选择的内容',
            iconCls : 'delbutton',
            handler : function() {
                //var id = grid.getSelections()[0].get("id");
                var ids = null;
                var gs = wapgrid.getSelectionModel().getSelections();
                if(gs.length==0) {
                    Ext.MessageBox.alert('信息提示',"请至少选择一条IP进行删除");
                    return;
                }
                for(var i = 0; i < gs.length; i++){
                    ids += gs[i].get("id");
                    if(i < gs.length-1) {
                        ids += ",";
                    }
                }
//                Ext.Msg.alert('提示',ids);
                Ext.MessageBox.confirm('提示', '是否确定删除这'+gs.length+'条IP', callBack);
                function callBack(qrid) {
                    if("yes"==qrid){
                        Ext.Ajax.request({
                            url:'../../AccessControlAction_delWapControls.action',
                            success:function(response,result){
                                var reText = Ext.util.JSON.decode(response.responseText);
                                Ext.Msg.alert('提示',reText.msg);
                                wapgrid.render();
                                wapds.reload();
                            },
                            params:{ids:ids}
                        });
                    }
                }
            }
        }
        ],
//        width : 1472,
        height :600,
        frame : true,
        loadMask : true,// 载入遮罩动画
        autoShow : true
    });

    var myform;
    function createMyform() {
        myform = new Ext.form.FormPanel({
            labelWidth:150,
            //renderTo : "formt",
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url: '../../AccessControlAction_addWapControl.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                {
                    fieldLabel : '无线网关IP' ,
                    name : 'wapip',
                    width:163,
                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
                    allowBlank: false,
                    regexText: '请输入无线网关IP'
                }
            ]
        });
    }
    var win = null;
    function winopen(form) {
        var myform = form;
        win = new Ext.Window({
            title : '添加无线网关IP',
            width : 390,
//            height :440,
            items: [myform],
            buttons : [
                {
                    text : '确定',
                    handler : function(){
                        //FormPanel自身带异步提交方式
                        if(myform.getForm().isValid()) {
                            myform.getForm().submit({
//                            url: '../../UserManageAction_addUserManage.action',
                                waitTitle : '请等待' ,
                                waitMsg: '正在提交中',
                                success:function(form,action) {
                                    var msg = action.result.msg;
                                    if(msg=="ssadd"){
                                        Ext.Msg.alert('提示','IP重复');
                                    }else{
                                        Ext.Msg.alert('提示',msg);
                                        wapgrid.render();
                                        wapds.reload();
                                        win.close();
                                    }
                                }
                            });
                        }else {
                            Ext.Msg.alert('提示','请先填写完正确信息');
                        }
                    }
                },{
                    text:'关闭',
                    handler:function() {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    }

    Model.updxx = function updxx() {
        createUpdform();
        Ext.getCmp("id").setValue(wapgrid.getSelectionModel().getSelections()[0].get("id"));
        Ext.getCmp("id1").setValue(wapgrid.getSelectionModel().getSelections()[0].get("id"));
        Ext.getCmp("wapip").setValue(wapgrid.getSelectionModel().getSelections()[0].get("wapip"));
        winupd(myform);
    }
    function createUpdform() {
        myform = new Ext.form.FormPanel({
            labelWidth:150,
            //renderTo : "formt",
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            baseParams : {create : true },
            //  labelWidth : 70 ,
            items : [
                {
                    fieldLabel : 'ID' ,
                    id:'id',
                    width:163,
                    xtype:'displayfield'
                },{
                    xtype :'hidden',
                    name:'id',
                    id:'id1'
                },{
                    fieldLabel : '无线网关IP' ,
                    name : 'wapip',
                    id:'wapip',
                    width:163,
                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
                    allowBlank: false,
                    regexText: '请输入无线网关IP'
                }
            ]
        });
    }
    function winupd(form) {
        var myform = form;
        win = new Ext.Window({
            title : '',
            width : 390,
//            height :468,
            items: [myform],
            buttons : [
                {
                    text : '确定',
                    handler : function(){
                        //FormPanel自身带异步提交方式
                        if(myform.getForm().isValid()) {
                            myform.getForm().submit({
                                url: '../../AccessControlAction_updWapControl.action',
                                waitTitle : '请等待' ,
                                waitMsg: '正在提交中',
                                success:function(form,action) {
                                    var msg = action.result.msg;
                                    if(msg=="mccf"){
                                        Ext.Msg.alert('提示','IP重复');
                                    }else{
                                        Ext.Msg.alert('提示',msg);
                                        wapgrid.render();
                                        wapds.reload();
                                        win.close();
                                    }
                                }
                            });
                        }else {
                            Ext.Msg.alert('提示','请先填写完正确信息');
                        }
                    }
                },{
                    text:'关闭',
                    handler:function() {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    }


    tabs.add({
        title : '访问控制',
        items : [
            {
                html : "设置可以访问管理系统的无限网关IP地址"

            },
            wapgrid
        ]
    });



    tabs.activate(0);
//    tabs.ownerCt.doLayout();
});

var Model = new Object;

function updmodel() {
    Model.updxx();
}


