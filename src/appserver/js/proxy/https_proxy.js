Ext.onReady(function(){
    var hpcsm = new Ext.grid.CheckboxSelectionModel();
    var hpcm = new Ext.grid.ColumnModel([
        hpcsm,
        {
            header : 'ID',
            dataIndex : 'id',
            width:160,
            hidden:true
        }, {
            header : "应用名称",
            dataIndex : 'applyname',
            width:160
        },{
            header : "服务IP地址",
            dataIndex : 'serveip',
            width:160,
            hidden:true
        },{
            header : "服务端口号",
            dataIndex : 'serveport',
            width:160,
            hidden:true
        }, {
            header : "映射地址",
            dataIndex : 'mapaddress',
            width:160
        },{
            header : "应用IP地址",
            dataIndex : 'applyip',
            width:160,
            hidden:true
        },{
            header : "应用端口号",
            dataIndex : 'applyport',
            width:160,
            hidden:true
        }, {
            header : "应用地址",
            dataIndex : 'applyaddress',
            width:160
        }
//        ,{
//            header:"操作",
//            dataIndex:"button",
//            renderer:xxbutton,
//            width:160
//        }
    ]);
    hpcm.defaultSortable = true;
    var hpds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
//            url : '../../ResourceAction_findAllResources.action'
            url : '../../HttpsProxyAction_findHttpsProxys.action'

        }),//调用的动作
        reader : new Ext.data.JsonReader({
            totalProperty : 'hplist',
            root : 'hprow'
        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'int'
        }, {
            name : 'applyname',
            mapping : 'applyname',
            type : 'string'
        }, {
            name : 'serveip',
            mapping : 'serveip',
            type : 'string'
        }, {
            name : 'serveport',
            mapping : 'serveport',
            type : 'string'
        }, {
            name : 'mapaddress',
            mapping : 'mapaddress',
            type : 'string'
        }, {
            name : 'applyip',
            mapping : 'applyip',
            type : 'string'
        }, {
            name : 'applyport',
            mapping : 'applyport',
            type : 'string'
        }, {
            name : 'applyaddress',
            mapping : 'applyaddress',
            type : 'string'
        }//列的映射
        ])
    });
    hpds.load();

    var hpgrid = new Ext.grid.GridPanel({
        // var grid = new Ext.grid.EditorGridPanel( {
        /* collapsible : true,// 是否可以展开
         animCollapse : false,// 展开时是否有动画效果*/
        id : 'hpgrid',
        title : '',
        store : hpds,
        cm : hpcm,
        sm:hpcsm,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        renderTo :Ext.getBody(), /*'noteDiv',*/
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
            {
                id : 'New1',
                text : ' 新增  ',
                tooltip : '新建一个表单',
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
                    var gs = hpgrid.getSelectionModel().getSelections();
                    if(gs.length==0) {
                        Ext.MessageBox.alert('信息提示',"请至少选择一条代理进行删除");
                        return;
                    }
                    for(var i = 0; i < gs.length; i++){
                        ids += gs[i].get("id");
                        if(i < gs.length-1) {
                            ids += ",";
                        }
                    }
//                Ext.Msg.alert('提示',ids);
                    Ext.MessageBox.confirm('提示', '是否确定删除这'+gs.length+'个代理', callBack);
                    function callBack(qrid) {
                        if("yes"==qrid){
                            Ext.Ajax.request({
                                url:'../../HttpsProxyAction_delHttpsProxy.action',
                                success:function(response,result){
                                    var reText = Ext.util.JSON.decode(response.responseText);
                                    Ext.Msg.alert('提示',reText.msg);
                                    hpgrid.render();
                                    hpds.reload();
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
    var storeoutlink = new Ext.data.JsonStore({
        fields : [ "interfaceName", "ip" ],
        url : "../../InterfaceManagerAction_readInterface.action",
        autoLoad : true,
        root : "rows"
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
            url: '../../HttpsProxyAction_addHttpsProxy.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                new Ext.form.ComboBox({
                    hiddenName:'termianlOutlink',
                    id:'termianlOutlink3',
                    fieldLabel:"ip地址",
                    emptyText: '请选择',
                    store: storeoutlink,
                    valueField:"interfaceName",
                    displayField:"ip",
                    typeAhead: true,
                    mode: "local",
                    forceSelection: true,
                    triggerAction: "all",
                    OnFocus:true
                }),
                {
                    fieldLabel : '应用名称' ,
                    name : 'applyname',
                    width:163,
                    regex:/^\w{1,10}$/,
                    allowBlank: false,
                    regexText: '请输入应用名称'
                },{
                    fieldLabel : '服务IP地址' ,
                    name : 'serveip',
                    width:163,
                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
                    allowBlank: false,
                    regexText: '请输入服务IP地址'
                },{
                    fieldLabel : '服务端口号' ,
                    name : 'serveport',
                    width:163,
                    regex:/^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,
                    allowBlank: false,
                    regexText: '请输入服务端口号'
                },{
                    fieldLabel : '应用IP地址' ,
                    name : 'applyip',
                    width:163,
                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
                    allowBlank: false,
                    regexText: '请输入IP地址'
                },{
                    fieldLabel : '应用端口号' ,
                    name : 'applyport',
                    width:163,
                    regex:/^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,
                    allowBlank: false,
                    regexText: '请输入应用端口号'
                }
            ]
        });
    }
    var win = null;
    function winopen(form) {
        var myform = form;
        win = new Ext.Window({
            title : '',
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
                                        Ext.Msg.alert('提示','应用名称重复');
                                    }else{
                                        Ext.Msg.alert('提示',msg);
                                        hpgrid.render();
                                        hpds.reload();
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
});
