Ext.onReady(function(){
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
        },
        {
            header : "角色名称",
            dataIndex : 'rolename',
            width:200
        }, {
            header : "角色描述",
            dataIndex : 'description',
            width:200
        },{
            header:"操作",
            dataIndex:"button",
            renderer:jsczbutton,
            width:350
        }
    ]);
    cm.defaultSortable = true;
    function jsczbutton() {
        var returnStr = "<a onclick='updmodel()'>编辑</a>&nbsp<a onclick='timebutton()'>时间属性</a>&nbsp;<a onclick='userbutton()'>用户成员</a>&nbsp;<a onclick='rulebutton()'>规则成员</a>";
        return returnStr;

    }
    var ds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../RoleManageAction_findAllRoleManages.action'
        }),//调用的动作
        reader : new Ext.data.JsonReader({
            totalProperty : 'rolemanagelist',
            root : 'rolerows'
        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'int'
        }, {
            name : 'rolename',
            mapping : 'rolename',
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
        id : 'usergrid',
        title : '终端设备管理',
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
        tbar : [ {
            id : 'New1',
            text : ' 添加  ',
            tooltip : '添加一个角色',
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
                            url:'../../RoleManageAction_delRoleManagesByIds.action',
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
        },'&nbsp;&nbsp;&nbsp;&nbsp;角色名:'
        ,{
            xtype : 'textfield' ,
            id : 'likename',
            name : 'likename',
            width:163
        },{
            text : ' 搜索  ',
            tooltip : '按角色名模糊查询',
            iconCls : 'importuser',
            handler : function() {
                var likename = Ext.getCmp("likename").getValue();
                Ext.Ajax.request({
                    url:'../../RoleManageAction_execute.action',
                    params:{likename:likename}
                });
                grid.render();
                ds.proxy = new Ext.data.HttpProxy({url:'../../RoleManageAction_findRoleManagesByRolname.action'});
                ds.load({params:{start:0,limit:10}});
//                Ext.getCmp("likename").setValue(null);
//                Ext.Ajax.request({
//                    url:'../../RoleManageAction_setNull.action'
//                });
            }
        }],
        width : 1472,
        height :600,
        frame : true,
        loadMask : true,// 载入遮罩动画
        autoShow : true
    });

    var myform;
    function createMyform() {
        myform = new Ext.form.FormPanel({
            labelWidth:150,
            height:250,
            //renderTo : "formt",
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url: '../../RoleManageAction_addRoleManage.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                {
                    fieldLabel : '角色名称' ,
                    name : 'rolename',
                    width:163,
                    regex:/^\S{1,10}$/,
                    allowBlank: false,
                    regexText: '请输入角色名称'
                },{
                    fieldLabel : '角色描述' ,
                    name : 'description',
                    width:163,
                    regex:/^\S{1,30}$/
                }
            ]
        });
    }

    var win = null;
    function winopen(form) {
        var myform = form;
        win = new Ext.Window({
            title : '添加新角色',
            width : 390,
            height :250,
            items: [myform],
            buttons : [
                {
                    text : '确定',
                    handler : function(){
                        //FormPanel自身带异步提交方式
                        if(myform.getForm().isValid()) {
                            myform.getForm().submit({
//                            url: '../../RoleManageAction_addRoleManage.action',
                                waitTitle : '请等待' ,
                                waitMsg: '正在提交中',
                                success:function(form,action) {
                                    var msg = action.result.msg;
                                    if(msg=="ssadd"){
                                        Ext.Msg.alert('提示','角色名称重复');
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
    }

    function winupd(form) {
        var myform = form;
        win = new Ext.Window({
            title : '修改角色信息',
            width : 390,
            height :250,
            items: [myform],
            buttons : [
                {
                    text : '确定',
                    handler : function(){
                        //FormPanel自身带异步提交方式
                        if(myform.getForm().isValid()) {
                            myform.getForm().submit({
                                url: '../../RoleManageAction_updRoleManagesByIds.action',
                                waitTitle : '请等待' ,
                                waitMsg: '正在提交中',
                                success:function(form,action) {
                                    var msg = action.result.msg;
                                    if(msg=='mccf'){
                                        Ext.Msg.alert('提示','角色名称重复');
                                    } else {
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
    }
    function createUpdform() {
        myform = new Ext.form.FormPanel({
            labelWidth:150,
            //renderTo : "formt",
            height:250,
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
                    fieldLabel : '角色名称' ,
                    name:'rolename',
                    id:'rolename',
                    width:163,
                    regex:/^\S{1,10}$/,
                    allowBlank: false,
                    regexText: '请输入角色名称'
                },{
                    fieldLabel : '用户描述' ,
                    name : 'description',
                    id : 'description',
                    width:163
                }
            ]
        });
    }
    Model.updxx = function updxx() {
        createUpdform();
        Ext.getCmp("id").setValue(grid.getSelectionModel().getSelections()[0].get("id"));
        Ext.getCmp("id1").setValue(grid.getSelectionModel().getSelections()[0].get("id"));
        Ext.getCmp("rolename").setValue(grid.getSelectionModel().getSelections()[0].get("rolename"));
        Ext.getCmp("description").setValue(grid.getSelectionModel().getSelections()[0].get("description"));
        winupd(myform);
    }

    function createTimeform() {
        myform = new Ext.form.FormPanel({
            labelWidth:150,
            height:250,
            //renderTo : "formt",
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            url: '../../UserManageAction_addUsermanagesByLDAP.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            items : [
                {
                    fieldLabel : '角色默认时间属性' ,
                    width:163,
                    xtype:'displayfield',
                    value:'角色默认属性为所有'
                },{
                    fieldLabel : '自定义时间属性',
                    id:'iftime',
                    width:163,
                    xtype:'checkbox'
                }
            ]
        });
    }

    function wintime(form) {
        var myform = form;
        win = new Ext.Window({
            title : '为角色添加时间属性',
            width : 390,
            height :250,
            items: [
                myform
            ],
            buttons : [
                {
                    text : ' 下一步  ',
                    tooltip : '',
                    iconCls : 'imp',
                    handler : function() {
                        var ift = Ext.getCmp("iftime").getValue();
                        var id = grid.getSelectionModel().getSelections()[0].get("id");
                        var rolename = grid.getSelectionModel().getSelections()[0].get("rolename");
                        if(ift){
                            win.close();
                            timeaddxx();
                        }else{
//                            alert(ift);
//                            alert(id);
//                            alert(rolename);
                            Ext.MessageBox.show({
                                title:'信息',
                                msg:'确定要为角色"'+rolename+'"添加所有时间',
                                buttons:{'ok':'确定','no':'取消'},
                                icon:Ext.MessageBox.INFO,
                                closable:false,
                                fn:function(e){
                                    if(e == 'ok'){
                                        var timetype = 0;
                                        var starttime=null;
                                        var endtime=null;
                                        Ext.Ajax.request({
                                            url:'../../RoleManageAction_addRoleManageAndTime.action',
                                            success:function(response,result){
                                                var reText = Ext.util.JSON.decode(response.responseText);
                                                Ext.Msg.alert('提示',reText.msg);
                                                grid.render();
                                                ds.reload();
                                                win.close();
                                            },
                                            params:{id:id,timetype:timetype,starttime:starttime,endtime:endtime}
                                        });
                                    }
                                }
                            });
                        }
                    }
                },{
                    text : ' 返回  ',
                    tooltip : '',
                    iconCls : 'esc',
                    handler : function() {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    }
    Model.timexx = function timexx() {
        createTimeform()
        wintime(myform);
    }

    function timeaddxx() {
        createAddTimeform();
        winaddtime(myform);
    }

    var ttrg = new Ext.form.RadioGroup({
        fieldLabel : "类型",
        items : [{
            boxLabel : '指定',
            inputValue : 1,
            name : "timetype",
            checked : true
        }, {
            boxLabel : '每日',
            name : "timetype",
            inputValue : 2
        }, {
            boxLabel : '每周',
            name : "timetype",
            inputValue : 3
        }]
    });
    function createAddTimeform() {
        myform = new Ext.form.FormPanel({
            labelWidth:150,
            //renderTo : "formt",
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            url: '../../RoleManageAction_addRoleManageAndTime.action',
            baseParams : {create : true },
            defaults:{
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [ ttrg,
                {
                    fieldLabel : '开始时间' ,
                    id:'starttime',
                    name: 'starttime',
                    width:163,
                    allowBlank: false,
                    regexText: '请输入开始时间'
                
                },{
                    fieldLabel : '开始时间' ,
                    id:'endtime',
                    name: 'endtime',
                    width:163,
                    allowBlank: false,
                    regexText: '请输入开始时间'
                }
            ]
        });
    }
    function winaddtime(form){
        var myform = form;
        win = new Ext.Window({
            title : '',
            width : 390,
            height :350,
            items: [
                myform
            ],
            buttons : [
                {
                    text : ' 保存  ',
                    tooltip : '',
                    iconCls : 'imp',
                    handler : function() {
                        var id = grid.getSelectionModel().getSelections()[0].get("id");
                        if(myform.getForm().isValid()) {
                            myform.getForm().submit({
                                waitTitle : '请等待' ,
                                waitMsg: '正在提交中',
                                success:function(form,action) {
                                    var msg = action.result.msg;
                                    Ext.Msg.alert('提示',msg);
                                    grid.render();
                                    ds.reload();
                                    win.close();
                                },
                                params:{id:id}
                            });
                        }else {
                            Ext.Msg.alert('提示','请先填写完正确信息');
                        }
                    }
                },{
                    text : ' 返回  ',
                    tooltip : '',
                    iconCls : 'esc',
                    handler : function() {
                        win.close();
                        Model.timexx();
                    }
                }
            ]
        });
        win.show();
    }

    Model.roleuserxx = function roleuserxx() {
        var id = grid.getSelectionModel().getSelections()[0].get("id")
        Ext.Ajax.request({
            url:'../../RoleManageAction_donull.action',
            params:{id:id}
        });
        createUrGridPande();
        winur(urgrid);
//        urgrid.render();
        urds.load({params:{start:0,limit:10}});
    }
    var urcm = new Ext.grid.ColumnModel([
        {
            header : '用户ID',
            dataIndex : 'id',
            width:127
        }, {
            header : "证书CN项",
            dataIndex : 'cacn',
            width:127
        }, {
            header : "省份",
            dataIndex : 'province',
            width:100,
            hidden:true
        }, {
            header : "市",
            dataIndex : 'city',
            width:100,
            hidden:true
        }, {
            header : "所属单位",
            dataIndex : 'department',
            width:100,
            hidden:true
        }, {
            header : "所属派出所",
            dataIndex : 'policestation',
            width:100,
            hidden:true
        }, {
            header : "用户邮箱",
            dataIndex : 'email',
            width:127
        }, {
            header : "联系电话",
            dataIndex : 'tel',
            width:127
        }, {
            header : "联系地址",
            dataIndex : 'address',
            width:100,
            hidden:true
        }, {
            header : "身份证",
            dataIndex : 'iddcard',
            width:127
        }, {
            header : "用户描述",
            dataIndex : 'description',
            width:100,
            hidden:true
        },{
            header:"操作",
            dataIndex:"button",
            renderer:urxxbutton,
            width:127
        }
    ]);
    urcm.defaultSortable = true;
    function urxxbutton() {
        var returnStr = "<a onclick='delroleuserxx()'>删除</a>";
        return returnStr;
    }
    Model.delroleuser = function delroleuser(){
        var roleid = grid.getSelectionModel().getSelections()[0].get("id")
        var userid = urgrid.getSelectionModel().getSelections()[0].get("id");
        Ext.MessageBox.confirm('提示', '是否确定删除这该角色中的这个用户', callBack);
        function callBack(qrid) {
            if("yes"==qrid){
                Ext.Ajax.request({
                    url:'../../RoleManageAction_delRoleUser.action',
                    success:function(response,result){
                        var reText = Ext.util.JSON.decode(response.responseText);
                        Ext.Msg.alert('提示',reText.msg);
                        urgrid.render();
                        urds.reload();
                    },
                    params:{id:roleid, userid:userid}
                });
            }
        }
//        win.close();
//        Model.roleuserxx();
    }

    var urds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../RoleManageAction_findUserManagesByRoleManageId.action'
        }),//调用的动作
        reader : new Ext.data.JsonReader({
            totalProperty : 'urlist',
            root : 'urrow'
        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'int'
        }, {
            name : 'cacn',
            mapping : 'cacn',
            type : 'string'
        }, {
            name : 'province',
            mapping : 'province',
            type : 'string'
        }, {
            name : 'city',
            mapping : 'city',
            type : 'string'
        },{
            name : 'department',
            mapping : 'department',
            type : 'string'
        },{
            name : 'policestation',
            mapping : 'policestation',
            type : 'string'
        },{
            name : 'email',
            mapping : 'email',
            type : 'string'
        }, {
            name : 'tel',
            mapping : 'tel',
            type : 'string'
        }, {
            name : 'address',
            mapping : 'address',
            type : 'string'
        }, {
            name : 'idcard',
            mapping : 'idcard',
            type : 'string'
        }, {
            name : 'description',
            mapping : 'description',
            type : 'string'
        }//列的映射
        ])
    });
    var urgrid;
    function createUrGridPande(){
        urgrid = new Ext.grid.GridPanel({
            id : 'usergrid',
            title : '角色中已有用户成员',
            store : urds,
            cm : urcm,
            renderTo :Ext.getBody(), /*'noteDiv',*/
            bbar : new Ext.PagingToolbar({
                pageSize : 10,
                store : urds,
                displayInfo : true,
                displayMsg : '显示 {0}-{1}条 / 共 {2} 条',
                emptyMsg : "无数据。"
            }),
            tbar : [ {
                id : 'New1',
                text : ' 添加  ',
                tooltip : '为角色添加用户',
                iconCls : 'adduesebutton',
                handler : function() {
                    var id = grid.getSelectionModel().getSelections()[0].get("id")
                    Ext.Ajax.request({
                        url:'../../RoleManageAction_donull.action',
                        success:function(response,result){
                            var reText = Ext.util.JSON.decode(response.responseText);
                            Ext.Msg.alert('提示',reText.msg);
                            grid.render();
                            ds.reload();
                            win.close();
                            Model.roleuserxx();
                        },
                        params:{id:id}
                    });
                    win.close();
                    createUserGridPande();
                    winuser(usergrid);
                    userds.load({params:{start:0,limit:10}});
                }
            },{
                text : '返回',
                iconCls : 'searchbutton',
                handler : function() {
                    win.close();
                }
            }
            ],
            width : 788,
            height :535,
            frame : true,
            loadMask : true,// 载入遮罩动画
            autoShow : true

        });
    }

    function winur(pgrid) {
        var urgrid = pgrid;
        win = new Ext.Window({
            title : '为角色添加用户成员',
            width : 800,
            height :600,
            items: [urgrid],
            buttons : [
                {
                    text : '确定',
                    handler : function(){
                        win.close();
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

    /*----------------------------------------------------------------------------------------------------------------------------------------------------角色中需要添加用户窗口*/
    var usercsm = new Ext.grid.CheckboxSelectionModel({
    });
    var usercm = new Ext.grid.ColumnModel([
        usercsm,
        {
            header : '用户ID',
            dataIndex : 'id',
            width:150
        }, {
            header : "证书CN项",
            dataIndex : 'cacn',
            width:150
        }, {
            header : "省份",
            dataIndex : 'province',
            width:100,
            hidden:true
        }, {
            header : "市",
            dataIndex : 'city',
            width:100,
            hidden:true
        }, {
            header : "所属单位",
            dataIndex : 'department',
            width:100,
            hidden:true
        }, {
            header : "所属派出所",
            dataIndex : 'policestation',
            width:100,
            hidden:true
        }, {
            header : "用户邮箱",
            dataIndex : 'email',
            width:150
        }, {
            header : "联系电话",
            dataIndex : 'tel',
            width:150
        }, {
            header : "联系地址",
            dataIndex : 'address',
            width:100,
            hidden:true
        }, {
            header : "身份证",
            dataIndex : 'iddcard',
            width:150
        }, {
            header : "用户描述",
            dataIndex : 'description',
            width:100,
            hidden:true
        }
    ]);
    usercm.defaultSortable = true;

    var userds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../RoleManageAction_findOtherUserManagesByOtherRoleId.action'
        }),//调用的动作
        reader : new Ext.data.JsonReader({
            totalProperty : 'modellist',
            root : 'rows'
        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'int'
        }, {
            name : 'cacn',
            mapping : 'cacn',
            type : 'string'
        }, {
            name : 'province',
            mapping : 'province',
            type : 'string'
        }, {
            name : 'city',
            mapping : 'city',
            type : 'string'
        },{
            name : 'department',
            mapping : 'department',
            type : 'string'
        },{
            name : 'policestation',
            mapping : 'policestation',
            type : 'string'
        },{
            name : 'email',
            mapping : 'email',
            type : 'string'
        }, {
            name : 'tel',
            mapping : 'tel',
            type : 'string'
        }, {
            name : 'address',
            mapping : 'address',
            type : 'string'
        }, {
            name : 'idcard',
            mapping : 'idcard',
            type : 'string'
        }, {
            name : 'description',
            mapping : 'description',
            type : 'string'
        }//列的映射
        ])
    });
//    userds.load({params:{start:0,limit:5}});

    var usergrid;
    function createUserGridPande(){
        usergrid = new Ext.grid.GridPanel({
            id : 'usergrid',
            title : '为角色添加用户成员',
            store : userds,
            cm : usercm,
            sm:usercsm,
            selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
            renderTo :Ext.getBody(), /*'noteDiv',*/
            bbar : new Ext.PagingToolbar({
                pageSize : 10,
                store : userds,
                displayInfo : true,
                displayMsg : '显示 {0}-{1}条 / 共 {2} 条',
                emptyMsg : "无数据。"
            }),
            // 添加内陷的工具条
            tbar : [
//                {
//                    xtype :'textfield',
//                    name : 'text1',
//                    id : 'text1'
//                },{
//                    text : '搜索',
//                    iconCls : 'searchbutton',
//                    handler : function() {
//                        var coloum = Ext.getCmp("select1").getValue();
//                        var name = Ext.getCmp("text1").getValue();
//                        Ext.Ajax.request({
//                            url:'../../UserManageAction_select.action',
//                            params:{coloum:coloum, name:name}
//                        });
//                        grid.render();
//                        ds.reload();
//                        Ext.Ajax.request({
//                            url:'../../UserManageAction_setNull.action'
//                        });
//                    }
//                }
            ],
            width : 788,
            height :535,
            frame : true,
            loadMask : true,// 载入遮罩动画
            autoShow : true

        });
    }
    function winuser(pgrid) {
        var usergrid = pgrid;
        win = new Ext.Window({
            title : '',
            width : 800,
            height :600,
            items: [usergrid],
            buttons : [
                {
                    text : '保存',
                    handler : function(){
                        var roleid = grid.getSelectionModel().getSelections()[0].get("id")
                        var ids = null;
                        var gs = usergrid.getSelectionModel().getSelections();
                        if(gs.length==0) {
                            Ext.MessageBox.alert('信息提示',"请至少选择一个用户");
                            return;
                        }
                        for(var i = 0; i < gs.length; i++){
                            ids += gs[i].get("id");
                            if(i < gs.length-1) {
                                ids += ",";
                            }
                        }
                        Ext.Ajax.request({
                            url:'../../RoleManageAction_addRoleUsers.action',
                            success:function(response,result){
                                var reText = Ext.util.JSON.decode(response.responseText);
                                usergrid.render();
                                userds.reload();
                                win.close();
                                Model.roleuserxx();
                                Ext.Msg.alert('提示',reText.msg);
                            },
                            params:{id:roleid,userids:ids}
                        });
                    }
                },{
                    text:'返回',
                    handler:function() {
                        win.close();
                        Model.roleuserxx();
                    }
                }
            ]
        });
        win.show();

    }
});

var Model = new Object;

function updmodel() {
    Model.updxx();
}

function timebutton(){
    Model.timexx();
}

function userbutton(){
    Model.roleuserxx();
}

function delroleuserxx(){
    Model.delroleuser();
}