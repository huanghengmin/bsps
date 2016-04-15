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
        }, {
            header : "角色",
            dataIndex : 'rolename',
            width:200
        }, {
            header : "权限",
            dataIndex : 'rights',
            width:200
        }, {
            header : "资源",
            dataIndex : 'resourcename',
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
        var returnStr = "<a onclick='updmodel()'>编辑</a>";
        return returnStr;

    }
    var ds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../PowerAction_findPowers.action'
        }),//调用的动作
        reader : new Ext.data.JsonReader({
            totalProperty : 'powerlist',
            root : 'powerrow'
        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'int'
        }, {
            name : 'rolename',
            mapping : 'rolename',
            type : 'string'
        }, {
            name : 'rights',
            mapping : 'rights',
            type : 'string'
        }, {
            name : 'resourcename',
            mapping : 'resourcename',
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
        id : 'powergrid',
        title : '权限管理',
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

        tbar : [
            '角色名称:'
        ,{
            xtype :'textfield',
            name : 'text1',
            id : 'text1'
        },
            '资源名称:'
        ,{
            xtype :'textfield',
            name : 'text2',
            id : 'text2'
        },{
            text : '搜索',
            iconCls : 'searchbutton',
            handler : function() {
                var rolename = Ext.getCmp("text1").getValue();
                var rename = Ext.getCmp("text2").getValue();
                Ext.Ajax.request({
                    url:'../../PowerAction_execute.action',
                    params:{rolename:rolename, resourcename:rename}
                });
                grid.render();
                ds.proxy = new Ext.data.HttpProxy({url:'../../PowerAction_findPowersByNames.action'});
                ds.load({params:{start:0,limit:10}});
            }
        }, {
            id : 'New1',
            text : ' 添加  ',
            tooltip : '添加一个权限',
            iconCls : 'addbutton',
            handler : function() {
                createrRoleGrid();
                roleds.load({params:{start:0,limit:10}});
                winrole(rolegrid);
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
                            url:'../../PowerAction_delPowers.action',
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
        },{
//            text : '启动代理服务',
//            iconCls : 'searchbutton',
//            handler : function() {
//
//                Ext.Ajax.request({
//                    url:'../../PowerAction_startBsAgent.action',
//                    success:function(response,result){
//                        var reText = Ext.util.JSON.decode(response.responseText);
//                        Ext.Msg.alert('提示',reText.msg);
//                    }
//                });
//
//            }
        }],
//        width : 1472,
        height :600,
        frame : true,
        loadMask : true,// 载入遮罩动画
        autoShow : true
    });

    var rolecsm = new Ext.grid.CheckboxSelectionModel({
    });
    var rolecm = new Ext.grid.ColumnModel([
        rolecsm,
        {
            header : 'ID',
            dataIndex : 'id',
//            hidden:true,
            width:250
        }, {
            header : "角色名称",
            dataIndex : 'rolename',
            width:250
        }, {
            header : "角色描述",
            dataIndex : 'description',
            width:250
        }
    ]);
    rolecm.defaultSortable = true;

    var roleds = new Ext.data.Store({
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


    var rolegrid = null;
    function createrRoleGrid(){
        rolegrid = new Ext.grid.GridPanel({
            // var grid = new Ext.grid.EditorGridPanel( {
            /* collapsible : true,// 是否可以展开
             animCollapse : false,// 展开时是否有动画效果*/
            id : 'usergrid',
            title : '选择角色',
            store : roleds,
            cm : rolecm,
            sm : new Ext.grid.CheckboxSelectionModel({                     singleSelect : true                 }),
            renderTo :Ext.getBody(), /*'noteDiv',*/
            /*
             * // 添加内陷的按钮 buttons : [ { text : '保存' }, { text : '取消' }],
             * buttonAlign : 'center',// 按钮对齐
             *
             */
            // 添加分页工具栏
        bbar : new Ext.PagingToolbar({
            pageSize : 10,
            store : roleds,
            displayInfo : true,
            displayMsg : '显示 {0}-{1}条 / 共 {2} 条',
            emptyMsg : "无数据。"
        }),
            // 添加内陷的工具条
            tbar : [  {
                text:'角色名：'
            },{
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
                    rolegrid.render();
                    roleds.proxy = new Ext.data.HttpProxy({url:'../../RoleManageAction_findRoleManagesByRolname.action'});
                    roleds.load({params:{start:0,limit:10}});
//                Ext.getCmp("likename").setValue(null);
//                    Ext.Ajax.request({
//                        url:'../../RoleManageAction_setNull.action'
//                    });
                }
            }],
            width : 788,
            height :335,
            frame : true,
            loadMask : true,// 载入遮罩动画
            autoShow : true
        });
    }
    var win;
    var rolename;
    function winrole(gridx){
        var urgrid = gridx;
        win = new Ext.Window({
            title : '分配新权限(为角色添加资源)',
            width : 800,
            height :400,
            items: [urgrid],
            buttons : [
                {
                    text : '下一步',
                    handler : function(){
                        var ids = null;
                        var gs = rolegrid.getSelectionModel().getSelections();
                        if(gs.length!=1) {
                            Ext.MessageBox.alert('信息提示',"请选择一个角色");
                            return;
                        }
                        rolename = gs[0].get("rolename");
                        win.close();
                        createRighform();
                        winiright(myform)
                    }
                },{
                    text:'返回',
                    handler:function() {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    }

    var myform;
    function createRighform() {
        myform = new Ext.form.FormPanel({
            labelWidth:150,
            //renderTo : "formt",
            height:270,
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                new Ext.form.RadioGroup({
                    fieldLabel : "策略授权方式",
                    id:'pradio',
                    items : [{
                        boxLabel : '禁止',
                        inputValue : 0,
                        checked : true,
                        name : "rights"
                    }, {
                        boxLabel : '允许',
                        name : "rights",
                        inputValue : 1
                    }]
                })
            ]
        });
    }
    var rights;
    function winiright(form) {
        var formright = form
        win = new Ext.Window({
            title : '',
            width : 390,
            height :270,
            items: [formright],
            buttons : [
                {
                    text : ' 下一步  ',
                    tooltip : '',
                    iconCls : 'imp',
                    handler : function() {
                        Ext.Ajax.request({
                            url:'../../ResourceAction_donull.action',
                            params:{rolename:rolename}
                        });
                        rights =  Ext.getCmp("pradio").getValue().inputValue;
                        win.close();
                        createReGrid();
                        regrid.render();
                        reds.load({params:{start:0,limit:10}});
                        winresource(regrid);
                    }
                },{
                    text : ' 上一步  ',
                    tooltip : '',
                    iconCls : 'esc',
                    handler : function() {
                        win.close();
                        createrRoleGrid();
                        winrole(rolegrid);
                    }
                }
            ]
        });
        win.show();
    }

    var recsm = new Ext.grid.CheckboxSelectionModel({
    });
    var recm = new Ext.grid.ColumnModel([
        recsm,
        {
            header : 'ID',
            dataIndex : 'id',
//            hidden:true,
            width:188
        }, {
            header : "资源名称",
            dataIndex : 'name',
            width:188
        }, {
            header : "资源类型",
            dataIndex : 'type',
            width:188
        }, {
            header : "资源说明",
            dataIndex : 'description',
            width:188
        }
    ]);
    recm.defaultSortable = true;
    var reds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../ResourceAction_findResourcesByOtherRoleName.action'
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
//    reds.load();
    var regrid;
    function createReGrid(){
        regrid = new Ext.grid.GridPanel({
            // var grid = new Ext.grid.EditorGridPanel( {
            /* collapsible : true,// 是否可以展开
             animCollapse : false,// 展开时是否有动画效果*/
            id : 'resourcegrid',
            title : '选择资源',
            store : reds,
            cm : recm,
            sm:recsm,
            selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
            renderTo :Ext.getBody(), /*'noteDiv',*/
            bbar : new Ext.PagingToolbar({
                pageSize : 10,
                store : reds,
                displayInfo : true,
                displayMsg : '显示 {0}-{1}条 / 共 {2} 条',
                emptyMsg : "无数据。"
            }),
            bbar : new Ext.PagingToolbar({
                pageSize : 10,
                store : reds,
                displayInfo : true,
                displayMsg : '显示 {0}-{1}条 / 共 {2} 条',
                emptyMsg : "无数据。"
            }),
            viewConfig:{
//                autoFill:true
                //forceFit:true
            },

            tbar : [ '资源类型:'
                ,new Ext.form.ComboBox({
                    id:'select3',
                    store : new Ext.data.SimpleStore({fields:['value','name'],data:[ [0,'IP地址段'], [1,'WEB应用'], [9,'所有']]}),
                    valueField : "value",
                    displayField : "name",
                    typeAhead : true,
                    mode : "local",
                    forceSelection : true,
                    triggerAction : "all",
                    OnFocus : true
                }),
                '资源名:',
                {
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
                        regrid.render();
                        reds.proxy = new Ext.data.HttpProxy({url:'../../ResourceAction_findResourcesByTypeAndLikeNameOtherRoleName.action'});
                        reds.load({params:{start:0,limit:10}});
                    }
                }
            ],
            width : 788,
            height :335,
            frame : true,
            loadMask : true,// 载入遮罩动画
            autoShow : true
        });
    }
    function winresource(regridx) {
        var urgrid = regridx;
        win = new Ext.Window({
            title : '添加新权限',
            width : 800,
            height :400,
            items: [urgrid],
            buttons : [
                {
                    text : '完成',
                    handler : function(){
                        var renames = "";
                        var gs = urgrid.getSelectionModel().getSelections();
                        if(gs.length==0) {
                            Ext.MessageBox.alert('信息提示',"请至少选择一条资源添加");
                            return;
                        }
                        for(var i = 0; i < gs.length; i++){
                            renames += gs[i].get("name");
                            if(i < gs.length-1) {
                                renames += ",";
                            }
                        }
                        Ext.Ajax.request({
                            url:'../../PowerAction_addPowers.action',
                            success:function(response,result){
                                var reText = Ext.util.JSON.decode(response.responseText);
                                Ext.Msg.alert('提示',reText.msg);
                                grid.render();
                                ds.reload();
                                rolename=null;
                                rights=null;
                                win.close();
                            },
                            params:{rolename:rolename,rights:rights,renames:renames}
                        });
                        Ext.Ajax.request({
                            url:'../../ResourceAction_execute.action',
                            params:{types:null, name:null}
                        });

                    }
                },{
                    text:'上一步',
                    handler:function() {
                        win.close();
                        createRighform();
                        winiright(myform)
                    }
                }
            ]
        });
        win.show();
    }

    Model.updpower = function uppower(){
        var rr = grid.getSelectionModel().getSelections()[0].get("rights");
        if(rr=="允许"){
            winupdyx();
        }else if(rr=="禁止"){
            winupdjz();
        }

    }
    function winupdyx(){
        win = new Ext.Window({
            title : '',
            width : 200,
            height :100,
            items: [
                new Ext.form.FormPanel({
                    labelWidth:150,
                    //renderTo : "formt",
                    frame : true ,
                    defaultType : 'textfield' ,
                    buttonAlign : 'right' ,
                    labelAlign : 'right' ,
                    items:[{
                        fieldLabel : '当前为允许，是否改为禁止',
                        id:'updyx',
                        width:10,
                        xtype:'checkbox'
                    }]
                })
            ],
            buttons : [
                {
                    text : '更改',
                    handler : function(){
                        if( Ext.getCmp("updyx").getValue() ){
                            var id = grid.getSelectionModel().getSelections()[0].get("id")
                            Ext.Ajax.request({
                                url:'../../PowerAction_updPowersRightsById.action',
                                success:function(response,result){
                                    var reText = Ext.util.JSON.decode(response.responseText);
                                    Ext.Msg.alert('提示',reText.msg);
                                    grid.render();
                                    ds.reload();
                                    win.close();
                                },
                                params:{id:id,rights:0}
                            });
                        }else{
                            alert("你打钩，不更改请返回");
                        }
                    }
                },{
                    text:'返回',
                    handler:function() {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    }
    function winupdjz(){
        win = new Ext.Window({
            title : '',
            width : 200,
            height :100,
            items: [
                new Ext.form.FormPanel({
                    labelWidth:150,
                    //renderTo : "formt",
                    frame : true ,
                    defaultType : 'textfield' ,
                    buttonAlign : 'right' ,
                    labelAlign : 'right' ,
                    items:[{
                        fieldLabel : '当前为禁止，是否改为允许',
                        id:'updyx',
                        width:10,
                        xtype:'checkbox'
                    }]
                })
            ],
            buttons : [
                {
                    text : '更改',
                    handler : function(){
                        if( Ext.getCmp("updyx").getValue() ){
                            var id = grid.getSelectionModel().getSelections()[0].get("id")
                            Ext.Ajax.request({
                                url:'../../PowerAction_updPowersRightsById.action',
                                success:function(response,result){
                                    var reText = Ext.util.JSON.decode(response.responseText);
                                    Ext.Msg.alert('提示',reText.msg);
                                    grid.render();
                                    ds.reload();
                                    win.close();
                                },
                                params:{id:id,rights:1}
                            });
                        }else{
                            alert("你打钩，不更改请返回");
                        }
                    }
                },{
                    text:'返回',
                    handler:function() {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    }
});

var Model = new Object;

function updmodel() {
    Model.updpower();
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