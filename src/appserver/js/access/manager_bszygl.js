Ext.onReady(function(){
    var data3 = [ [0,'IP地址段'], [1,'WEB应用'], [9,'所有']];
    var store3 = new Ext.data.SimpleStore({fields:['value','name'],data:data3});
    var select3 = new Ext.form.ComboBox({
        id:'select3',
        store : store3,
        valueField : "value",
        displayField : "name",
        typeAhead : true,
        mode : "local",
        forceSelection : true,
        triggerAction : "all",
        OnFocus : true
    });

    var rowNumber = new Ext.grid.RowNumberer();
    var csm = new Ext.grid.CheckboxSelectionModel({
    });
    var cm = new Ext.grid.ColumnModel([
        csm,
        rowNumber,
        {
            header : 'ID',
            dataIndex : 'id',
//            hidden:true,
            width:200,
            hidden:true
        }, {
            header : "资源名称",
            dataIndex : 'name',
            width:200
        }, {
            header : "资源类型",
            dataIndex : 'type',
            width:200
        }, {
            header : "资源说明",
            dataIndex : 'description',
            width:200
        },{
            header:"操作",
            dataIndex:"button",
            renderer:xxbutton,
            width:350
        }
    ]);
    cm.defaultSortable = true;
    function xxbutton() {
        var returnStr = "<a onclick='lookmodel()'>详细</a>";
        return returnStr;

    }
    var ds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../ResourceAction_findAllResources.action'
        }),//调用的动作
        reader : new Ext.data.JsonReader({
            totalProperty : 'relist',
            root : 'rerow'
        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'int'
        }, {
            name : 'name',
            mapping : 'name',
            type : 'string'
        }, {
            name : 'type',
            mapping : 'type',
            type : 'string'
        }, {
            name : 'description',
            mapping : 'description',
            type : 'string'
        }//列的映射
        ])
    });
    ds.load({params:{start:0,limit:10}});

    var grid;
    grid = new Ext.grid.GridPanel({
        // var grid = new Ext.grid.EditorGridPanel( {
        /* collapsible : true,// 是否可以展开
         animCollapse : false,// 展开时是否有动画效果*/
        id : 'resourcegrid',
        title : '资源管理',
        store : ds,
        cm : cm,
        sm:csm,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        renderTo :Ext.getBody(), /*'noteDiv',*/
        /*
         * // 添加内陷的按钮 buttons : [ { text : '保存' }, { text : '取消' }],
         * buttonAlign : 'center',// 按钮对齐
         *
         */
        // 添加分页工具栏
        bbar : new Ext.PagingToolbar({
            pageSize : 10,
            store : ds,
            displayInfo : true,
            displayMsg : '显示 {0}-{1}条 / 共 {2} 条',
            emptyMsg : "无数据。"
        }),
        // 添加内陷的工具条
        viewConfig:{
            autoFill:true
            //forceFit:true
        },

        tbar : [  '资源类型:'
        ,select3,
            '名称:'
        ,{
            xtype :'textfield',
            name : 'text3',
            id : 'text3'
        },{
            text : '搜索',
            iconCls : 'searchbutton',
            handler : function() {
                var typex = Ext.getCmp("select3").getValue();
                var namex = Ext.getCmp("text3").getValue();
                Ext.Ajax.request({
                    url:'../../ResourceAction_execute.action',
                    params:{types:typex, name:namex}
                });
                grid.render();
                ds.proxy = new Ext.data.HttpProxy({url:'../../ResourceAction_findResourcesByTypeAndLikeName.action'});
                ds.load({params:{start:0,limit:10}});
            }
        },{
            id : 'New1',
            text : ' 添加  ',
            tooltip : '添加一个资源',
            iconCls : 'addbutton',
            handler : function() {
                createAddIpform();
//                createAddWebform();
                Ext.getCmp("select1").setValue(0);
                winAddIp(myform);
            }
        }, {
            text : ' 删除 ',
            tooltip : '删除被选择的内容',
            iconCls : 'delbutton',
            handler : function() {
                var ids = null;
                var gs = grid.getSelectionModel().getSelections();
                if(gs.length==0) {
                    Ext.MessageBox.alert('信息提示',"请至少选择一条记录进行删除");
                    return;
                }
                for(var i = 0; i < gs.length; i++){
                    ids += gs[i].get("id");
                    if(i < gs.length-1) {
                        ids += ",";
                    }
                }
//                Ext.Msg.alert('提示',ids);
                Ext.MessageBox.confirm('提示', '是否确定删除这'+gs.length+'条记录', callBack);
                function callBack(qrid) {
                    if("yes"==qrid){
                        Ext.Ajax.request({
                            url:'../../ResourceAction_delResources.action',
                            success:function(response,result){
                                var reText = Ext.util.JSON.decode(response.responseText);
                                Ext.Msg.alert('提示',reText.msg);
                                grid.render();
                                ds.reload();
                            },
                            params:{ids:ids}
                        });
                    }
                }
            }
        }],
        width : 1472,
        height :600,
        frame : true,
        loadMask : true,// 载入遮罩动画
        autoShow : true
    });

    var data1 = [ [0,'IP地址段'], [1,'WEB应用'] ];
    var store1 = new Ext.data.SimpleStore({fields:['value','name'],data:data1});
    var myform;
    function createAddIpform(){
        myform = new Ext.form.FormPanel({
            labelWidth:150,
            //renderTo : "formt",
            height:270,
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url: '../../ResourceAction_addIpResource.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                {
                    fieldLabel : '资源名称' ,
                    name : 'name',
                    id:'naem',
                    width:163,
                    regex:/^\w{1,10}$/,
                    allowBlank: false,
                    regexText: '请输入资源名称'
                },
                new Ext.form.ComboBox({
                    fieldLabel:'资源类型',
                    hiddenName:'type',
                    id:'select1',
                    store : store1,
                    valueField : "value",
                    displayField : "name",
                    typeAhead : true,
                    mode : "local",
                    forceSelection : true,
                    triggerAction : "all",
                    OnFocus : true,
                    allowBlank: false,
                    listeners:{
                        select:function(){
                            var v = this.value;
                            if(1==v){
                                win.close();
                                createAddWebform()
                                Ext.getCmp("select1").setValue(1);
                                winAddIp(myform);
                            }
                        }
                    }
                }),
                {
                    fieldLabel : '网段IP地址' ,
                    name : 'ipaddress',
                    id : 'ipaddress',
                    width:163,
                    allowBlank: false,
                    regex:/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/
                },{
                    fieldLabel : '子网掩码' ,
                    name : 'subnetmask',
                    id:'subnetmask',
                    width:163,
                    allowBlank: false,
                    regex:/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/
                },{
                    fieldLabel : '资源描述' ,
                    name : 'description',
                    width:163
                }
            ]
        });
    };
    var data2 = [ ['HTTP','HTTP'], ['HTTPS','HTTPS'] ];
    var store2 = new Ext.data.SimpleStore({fields:['value','name'],data:data2});
    function createAddWebform(){
        myform = new Ext.form.FormPanel({
            labelWidth:150,
            //renderTo : "formt",
            height:270,
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url: '../../ResourceAction_addWebResource.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                {
                    fieldLabel : '资源名称' ,
                    name : 'name',
                    id:'naem',
                    width:163,
                    regex:/^\w{1,10}$/,
                    allowBlank: false,
                    regexText: '请输入资源名称'
                },new Ext.form.ComboBox({
                    fieldLabel:'资源类型',
                    hiddenName:'type',
                    id:'select1',
                    store : store1,
                    valueField : "value",
                    displayField : "name",
                    typeAhead : true,
                    mode : "local",
                    forceSelection : true,
                    triggerAction : "all",
                    OnFocus : true,
                    allowBlank: false,
                    listeners:{
                        select:function(){
                            var v = this.value;
                            if(0==v){
                                win.close();
                                createAddIpform();
                                Ext.getCmp("select1").setValue(0);
                                winAddIp(myform);
                            }
                        }
                    }
                }), new Ext.form.ComboBox({
                    fieldLabel:'资源协议',
                    hiddenName:'agreement',
                    id:'select2',
                    store : store2,
                    valueField : "value",
                    displayField : "name",
                    typeAhead : true,
                    mode : "local",
                    forceSelection : true,
                    triggerAction : "all",
                    OnFocus : true,
                    allowBlank: false
                }),
                {
                    fieldLabel : 'IP地址/域名' ,
                    name : 'ipaddress',
                    id : 'ipaddress',
                    width:163,
                    allowBlank: false
                },{
                    fieldLabel : '主机端口' ,
                    name : 'port',
                    id:'port',
                    width:163,
                    allowBlank: false
                },
                {
                    fieldLabel : '资源路径' ,
                    name : 'url',
                    id:'url',
                    width:163,
                    allowBlank: false
                },{
                    fieldLabel : '资源描述' ,
                    name : 'description',
                    width:163
                }
            ]
        });
    }
    var win = null;
    function winAddIp(form){
        var myform = form;
        win = new Ext.Window({
            title : '添加新资源',
            width : 390,
            height :270,
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
                                    if(msg=="mccf"){
                                        Ext.Msg.alert('提示','资源名称重复');
                                    }else{
                                        Ext.Msg.alert('提示',msg);
                                        grid.render();
                                        ds.reload();
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
    };

    Model.lookrexx= function lookrexx(){
        var gs = grid.getSelectionModel().getSelections();
        var id = gs[0].get("id");
        var tt = gs[0].get("type");
        if(tt=="IP地址段"){
            createReIpForm();
            Ext.Ajax.request({
                url:'../../ResourceAction_findResourceIp.action',
                success:function(response,result){
                    var data = Ext.decode(response.responseText);
                    Ext.getCmp("name").setValue(gs[0].get("name"));
                    Ext.getCmp("type").setValue("IP地址段");
                    Ext.getCmp("ipaddress").setValue(data.ipaddress);
                    Ext.getCmp("subnetmask").setValue(data.subnetmask);
                    Ext.getCmp("description").setValue(gs[0].get("description"));
                },
                failure:function(response,result){
                    Ext.Msg.alert('提示','查看失败！');
                },
                params:{id:id}
            });
        }else if(tt=="WEB应用"){
            createReWebForm();
            Ext.Ajax.request({
                url:'../../ResourceAction_findResourceWeb.action',
                success:function(response,result){
                    var data = Ext.decode(response.responseText);
                    Ext.getCmp("name").setValue(gs[0].get("name"));
                    Ext.getCmp("agreement").setValue(data.agreement);
                    Ext.getCmp("type").setValue("IP地址段");
                    Ext.getCmp("ipaddress").setValue(data.ipaddress);
                    Ext.getCmp("port").setValue(data.portweb);
                    Ext.getCmp("url").setValue(data.urlweb);
                    Ext.getCmp("description").setValue(gs[0].get("description"));
                },
                failure:function(response,result){
                    Ext.Msg.alert('提示','查看失败！');
                },
                params:{id:id}
            });
        }
        winre(myform);
    }
    function createReIpForm(){
        myform = new Ext.form.FormPanel({
            labelWidth:150,
            //renderTo : "formt",
            height:270,
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                {
                    fieldLabel : '资源名称' ,
                    name : 'name',
                    id:'name',
                    width:163,
                    xtype:'displayfield'
                },{
                    fieldLabel : '资源类型' ,
                    name : 'type',
                    id:'type',
                    width:163,
                    xtype:'displayfield'
                },
                {
                    fieldLabel : '网段IP地址' ,
                    name : 'ipaddress',
                    id : 'ipaddress',
                    width:163,
                    xtype:'displayfield'
                },{
                    fieldLabel : '子网掩码' ,
                    name : 'subnetmask',
                    id:'subnetmask',
                    width:163,
                    xtype:'displayfield'
                },{
                    fieldLabel : '资源描述' ,
                    name : 'description',
                    id:'description',
                    width:163,
                    xtype:'displayfield'
                }
            ]
        });
    }
    function createReWebForm(){
        myform = new Ext.form.FormPanel({
            labelWidth:150,
            //renderTo : "formt",
            height:270,
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                {
                    fieldLabel : '资源名称' ,
                    name : 'name',
                    id:'name',
                    width:163,
                    xtype:'displayfield'
                },{
                    fieldLabel : '资源协议' ,
                    name : 'agreement',
                    id:'agreement',
                    width:163 ,
                    xtype:'displayfield'
                },{
                    fieldLabel : '资源类型' ,
                    name : 'type',
                    id:'type',
                    width:163 ,
                    xtype:'displayfield'
                },{
                    fieldLabel : 'IP地址/域名' ,
                    name : 'ipaddress',
                    id : 'ipaddress',
                    width:163 ,
                    xtype:'displayfield'
                },{
                    fieldLabel : '主机端口' ,
                    name : 'port',
                    id:'port',
                    width:163 ,
                    xtype:'displayfield'
                },
                {
                    fieldLabel : '资源路径' ,
                    name : 'url',
                    id:'url',
                    width:163 ,
                    xtype:'displayfield'
                },{
                    fieldLabel : '资源描述' ,
                    name : 'description',
                    id:'description',
                    width:163 ,
                    xtype:'displayfield'
                }
            ]
        });
    }
    function winre(form){
        var myform = form;
        win = new Ext.Window({
            title : '查看资源信息',
            width : 390,
            height :270,
            items: [myform],
            buttons : [
                {
                    text:'关闭',
                    handler:function() {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    };
});

var Model = new Object;
function lookmodel(){
    Model.lookrexx();
}
//function updmodel() {
//    Model.updxx();
//}