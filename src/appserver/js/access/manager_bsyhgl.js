
Ext.onReady(function(){
    var data1 = [ ['cacn','证书CN项'], ['province','省份'], ['city','市'], ['department','所属单位'], ['policestation','所属派出所'], ['email','用户邮箱'], ['tel','联系电话'], ['address','联系地址'], ['idcard','身份证号码'], ['description','用户描述'] ];
    var store1 = new Ext.data.SimpleStore({fields:['value','name'],data:data1});
    var select1 = new Ext.form.ComboBox({
        id:'select1',
        store : store1,
        valueField : "value",
        displayField : "name",
        typeAhead : true,
        mode : "local",
        forceSelection : true,
        triggerAction : "all",
        OnFocus : true
    });

    var rowNumber = new Ext.grid.RowNumberer();
    var csm = new Ext.grid.CheckboxSelectionModel();
    var cm = new Ext.grid.ColumnModel([
        csm,
        rowNumber,
        {
            header : 'ID',
            dataIndex : 'id',
            width:160,
            hidden:true
        }, {
            header : "证书CN项",
            dataIndex : 'cacn',
            width:160
        }, {
            header : "省份",
            dataIndex : 'province',
            width:160,
            hidden:true
        }, {
            header : "市",
            dataIndex : 'city',
            width:160,
            hidden:true
        }, {
            header : "所属单位",
            dataIndex : 'department',
            width:160,
            hidden:true
        }, {
            header : "所属派出所",
            dataIndex : 'policestation',
            width:160,
            hidden:true
        }, {
            header : "用户邮箱",
            dataIndex : 'email',
            width:160
        }, {
            header : "联系电话",
            dataIndex : 'tel',
            width:160
        }, {
            header : "联系地址",
            dataIndex : 'address',
            width:160,
            hidden:true
        }, {
            header : "身份证",
            dataIndex : 'iddcard',
            width:160
        }, {
            header : "用户描述",
            dataIndex : 'description',
            width:160,
            hidden:true
        },{
            header:"操作",
            dataIndex:"button",
            renderer:xxbutton,
            width:160
        }
    ]);
    cm.defaultSortable = true;
    function xxbutton() {
        var returnStr = "<a onclick='lookmodel()'>详细</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='updmodel()'>修改</a>";
        return returnStr;

    }
    var ds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../UserManageAction_findAllUserManage.action'
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
    ds.load({params:{start:0,limit:10}});

    var grid;
    grid = new Ext.grid.GridPanel({
        // var grid = new Ext.grid.EditorGridPanel( {
        /* collapsible : true,// 是否可以展开
         animCollapse : false,// 展开时是否有动画效果*/
        id : 'usergrid',
        title : '用户管理列表',
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
                Ext.MessageBox.confirm('提示', '是否确定删除这'+gs.length+'个用户', callBack);
                function callBack(qrid) {
                    if("yes"==qrid){
                        Ext.Ajax.request({
                            url:'../../UserManageAction_delUserManageByIds.action',
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
            text : ' 导入用户  ',
            tooltip : '导入用户',
            iconCls : 'importuser',
            handler : function() {
//                 winimport();
                winwjdr();
            }
        },{
            text : ' 导出所有  ',
            tooltip : '导出所有用户',
            iconCls : 'export',
            handler : function() {
//                var data2Excel = new Ext.icss.Data2ExcelTool();
//                data2Excel.export2Excel(Ext.getCmp('usergrid'));
                if (!Ext.fly('test')) {
                    var frm = document.createElement('form');
                    frm.id = 'test';
                    frm.name = id;
                    frm.style.display = 'none';
                    document.body.appendChild(frm);
                }
                var type = 'out';
                Ext.Ajax.request({
                    url: '../../UserManageAction_outportFile.action',params:{type:type},
                    form: Ext.fly('test'),
                    method: 'POST',
                    isUpload: true
                });
//                Ext.Ajax.request({
//                    url:'../../UserManageAction_outportFile.action',
//                    success:function(response,result){
//                        var reText = Ext.util.JSON.decode(response.responseText);
//                        Ext.Msg.alert('提示',reText.msg);
//                        grid.render();
//                        ds.reload();
//                    }
//                });
            }
        },select1,
        {
            xtype :'textfield',
            name : 'text1',
            id : 'text1'
        },{
            text : '搜索',
            iconCls : 'searchbutton',
            handler : function() {
                var coloum = Ext.getCmp("select1").getValue();
                var name = Ext.getCmp("text1").getValue();
                Ext.Ajax.request({
                    url:'../../UserManageAction_execute.action',
                    params:{coloum:coloum, name:name}
                });
                grid.render();
                ds.proxy = new Ext.data.HttpProxy({url:'../../UserManageAction_select.action'});
                ds.load({params:{start:0,limit:10}});
//                Ext.Ajax.request({
//                    url:'../../UserManageAction_setNull.action'
//                });
            }
        }
        ],
        width : 1472,
        height :600,
        frame : true,
        loadMask : true,// 载入遮罩动画
        autoShow : true

    });

    //要增加的form表单：
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
            url: '../../UserManageAction_addUserManage.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                {
                    fieldLabel : '证书CN项' ,
                    name : 'cacn',
                    width:163,
                    regex:/^\w{1,10}$/,
                    allowBlank: false,
                    regexText: '请输入证书CN项'
                },{
                    fieldLabel : '省份' ,
                    name : 'province',
                    width:163,
                    regex:/^\S{1,30}$/
                },{
                    fieldLabel : '市' ,
                    name : 'city',
                    width:163,
                    regex:/^\S{1,30}$/
                },{
                    fieldLabel : '所属单位' ,
                    name : 'department',
                    width:163,
                    regex:/^\S{1,30}$/
                },{
                    fieldLabel : '所属派出所' ,
                    name : 'policestation',
                    width:163,
                    regex:/^\S{1,30}$/
                },{
                    fieldLabel : '用户邮箱' ,
                    name : 'email',
                    width:163,
                    regex:/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/
                },{
                    fieldLabel : '联系电话' ,
                    name : 'tel',
                    width:163,
                    regex:/[1][3][0-9]{9}|\d{3}-\d{8}|\d{3}-\d{7}|\d{4}-\d{8}|\d{4}-\d{7}/
                },{
                    fieldLabel : '联系地址' ,
                    name : 'address',
                    width:163,
                    regex:/^\S{1,30}$/
                },{
                    fieldLabel : '身份证号码' ,
                    name : 'idcard',
                    width:163,
                    regex:/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])(\d{3})(\d|[X]|[x])$/
                },{
                    fieldLabel : '用户描述' ,
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
            title : '增加新用户',
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
                                        Ext.Msg.alert('提示','CACN项重复');
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

    function createLookform() {
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
                    fieldLabel : '证书CN项' ,
                    name : 'cacn',
                    id:'cacn',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '省份' ,
                    name : 'province',
                    id: 'province',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '市' ,
                    name : 'city',
                    id: 'city',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '所属单位' ,
                    name : 'department',
                    id: 'department',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '所属派出所' ,
                    name : 'policestation',
                    id: 'policestation',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '用户邮箱' ,
                    name : 'email',
                    id: 'email',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '联系电话' ,
                    name : 'tel',
                    id: 'tel',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '联系地址' ,
                    name : 'address',
                    id: 'address',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '身份证号码' ,
                    name : 'idcard',
                    id: 'idcard',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '用户描述' ,
                    name : 'description',
                    id : 'description',
                    width:163,
                    xtype: 'displayfield'
                }
            ]
        });
    }
    Model.lookxx = function lookxx(){
        createLookform();
        Ext.getCmp("cacn").setValue(grid.getSelectionModel().getSelections()[0].get("cacn"));
        Ext.getCmp("province").setValue(grid.getSelectionModel().getSelections()[0].get("province"));
        Ext.getCmp("city").setValue(grid.getSelectionModel().getSelections()[0].get("city"));
        Ext.getCmp("department").setValue(grid.getSelectionModel().getSelections()[0].get("department"));
        Ext.getCmp("policestation").setValue(grid.getSelectionModel().getSelections()[0].get("policestation"));
        Ext.getCmp("email").setValue(grid.getSelectionModel().getSelections()[0].get("email"));
        Ext.getCmp("tel").setValue(grid.getSelectionModel().getSelections()[0].get("tel"));
        Ext.getCmp("address").setValue(grid.getSelectionModel().getSelections()[0].get("address"));
        Ext.getCmp("idcard").setValue(grid.getSelectionModel().getSelections()[0].get("idcard"))
        Ext.getCmp("description").setValue(grid.getSelectionModel().getSelections()[0].get("description"));
        winlookxx(myform);
    }
    function winlookxx(form) {
        var myform = form;
        win = new Ext.Window({
            title : '查看用户信息',
            width : 390,
//            height :490,
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
    }


    function winupd(form) {
        var myform = form;
        win = new Ext.Window({
            title : '修改用户信息',
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
                                url: '../../UserManageAction_updUserManage.action',
                                waitTitle : '请等待' ,
                                waitMsg: '正在提交中',
                                success:function(form,action) {
                                    var msg = action.result.msg;
                                    Ext.Msg.alert('提示',msg);
                                    grid.render();
                                    ds.reload();
                                    win.close();
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
                    fieldLabel : '证书CN项' ,
                    id:'cacn',
                    width:163,
                    xtype:'displayfield'
                },{
                    xtype :'hidden',
                    name:'cacn',
                    id:'cacn1'
                },{
                    fieldLabel : '省份' ,
                    name : 'province',
                    id: 'province',
                    width:163
                },{
                    fieldLabel : '市' ,
                    name : 'city',
                    id: 'city',
                    width:163
                },{
                    fieldLabel : '所属单位' ,
                    name : 'department',
                    id: 'department',
                    width:163
                },{
                    fieldLabel : '所属派出所' ,
                    name : 'policestation',
                    id: 'policestation',
                    width:163
                },{
                    fieldLabel : '用户邮箱' ,
                    name : 'email',
                    id: 'email',
                    width:163,
                    regex:/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/
                },{
                    fieldLabel : '联系电话' ,
                    name : 'tel',
                    id: 'tel',
                    width:163,
                    regex:/[1][3][0-9]{9}|\d{3}-\d{8}|\d{3}-\d{7}|\d{4}-\d{8}|\d{4}-\d{7}/
                },{
                    fieldLabel : '联系地址' ,
                    name : 'address',
                    id: 'address',
                    width:163
                },{
                    fieldLabel : '身份证号码' ,
                    name : 'idcard',
                    id: 'idcard',
                    width:163,
                    regex:/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])(\d{3})(\d|[X]|[x])$/
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
        Ext.getCmp("cacn").setValue(grid.getSelectionModel().getSelections()[0].get("cacn"));
        Ext.getCmp("cacn1").setValue(grid.getSelectionModel().getSelections()[0].get("cacn"));
        Ext.getCmp("province").setValue(grid.getSelectionModel().getSelections()[0].get("province"));
        Ext.getCmp("city").setValue(grid.getSelectionModel().getSelections()[0].get("city"));
        Ext.getCmp("department").setValue(grid.getSelectionModel().getSelections()[0].get("department"));
        Ext.getCmp("policestation").setValue(grid.getSelectionModel().getSelections()[0].get("policestation"));
        Ext.getCmp("email").setValue(grid.getSelectionModel().getSelections()[0].get("email"));
        Ext.getCmp("tel").setValue(grid.getSelectionModel().getSelections()[0].get("tel"));
        Ext.getCmp("address").setValue(grid.getSelectionModel().getSelections()[0].get("address"));
        Ext.getCmp("idcard").setValue(grid.getSelectionModel().getSelections()[0].get("idcard"))
        Ext.getCmp("description").setValue(grid.getSelectionModel().getSelections()[0].get("description"));
        winupd(myform);
    }

    function winwjdr() {
        win = new Ext.Window({
            title : '',
            width : 390,
//            height :350,
            items: [
                new Ext.form.RadioGroup({
                    fieldLabel : "策略授权方式",
                    id:'pradio',
                    items : [{
                        boxLabel : '文件方式',
                        inputValue : 0,
                        checked : true,
                        name : "rights"
                    }, {
                        boxLabel : 'LDAP方式',
                        name : "rights",
                        inputValue : 1
                    }],
                    listeners:{
                        change :function onclic(s,ck){
                            var flag1=ck.inputValue;
                            if(1==flag1){
                                win.close();
                                winimport();
                            }
                        }

                    }
                }),
                new Ext.form.FormPanel({
                    plain:true,
                    labelWidth:150,
                    labelAlign:'left',
                    fileUpload:true,
                    border:false,
                    defaults : {
                        width : 200,
                        allowBlank : false,
                        blankText : '该项不能为空！'
                    },
                    items : [
                        {
                            id:'outFile.info',
                            fieldLabel:"非xls文件不可上传",
                            name:'uploadFile',
                            xtype:'textfield',
                            inputType: 'file',
                            listeners:{
                                render:function(){
                                    Ext.get('outFile.info').on("change",function(){
                                        var file = this.getValue();
                                        var fs = file.split('.');
                                        if(fs[fs.length-1].toLowerCase()=='xls'){
                                            Ext.MessageBox.show({
                                                title:'信息',
                                                msg:'<font color="green">确定要上传文件:'+file+'？</font>',
                                                width:300,
                                                buttons:{'ok':'确定','no':'取消'},
                                                icon:Ext.MessageBox.WARNING,
                                                closable:false,
                                                fn:function(e){
                                                    if(e == 'ok'){
                                                        if (outForm.form.isValid()) {
//                                                var type = 'out';
                                                            outForm.getForm().submit({
                                                                url :'../../UserManageAction_importFile.action',
                                                                method :'POST',
//                                                    params:{type:type},
                                                                waitTitle :'系统提示',
                                                                waitMsg :'正在上传,请稍后...',
                                                                success : function(form,action) {
                                                                    var msg = action.result.msg;
                                                                    Ext.Msg.alert('提示',msg);
                                                                    grid.render();
                                                                    ds.reload();
                                                                    win.close();
                                                                }
                                                            });
                                                        } else {
                                                            Ext.MessageBox.show({
                                                                title:'信息',
                                                                width:200,
                                                                msg:'请填写完成再提交!',
                                                                //                                            animEl:'insert.win.info',
                                                                buttons:{'ok':'确定'},
                                                                icon:Ext.MessageBox.ERROR,
                                                                closable:false
                                                            });
                                                        }
                                                    }
                                                }
                                            });
                                        } else {
                                            Ext.MessageBox.show({
                                                title:'信息',
                                                width:200,
                                                msg:'上传文件格式不对,请重新选择!',
                                                buttons:{'ok':'确定'},
                                                icon:Ext.MessageBox.ERROR,
                                                closable:false,
                                                fn:function(e){
                                                    if(e=='ok'){
                                                        Ext.getCmp('outFile.info').setValue('');
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        }
                    ]
                })
            ]
        });
        win.show();
    }

    function winimport() {
        win = new Ext.Window({
            title : '',
            width : 390,
//            height :350,
            items: [
                new Ext.form.RadioGroup({
                    fieldLabel : "策略授权方式",
                    id:'pradio',
                    items : [{
                        boxLabel : '文件方式',
                        inputValue : 0,
                        name : "rights"
                    }, {
                        boxLabel : 'LDAP方式',
                        name : "rights",
                        checked : true,
                        inputValue : 1
                    }],
                    listeners:{
                        change :function onclic(s,ck){
                            var flag1=ck.inputValue;
                            if(0==flag1){
                                win.close();
                                winwjdr();
                            }
                        }

                    }
                }),
                new Ext.form.FormPanel({
                    labelWidth:150,
                    id:"ldapform",
                    //renderTo : "formt",
                    frame : true ,
                    defaultType : 'textfield' ,
                    buttonAlign : 'right' ,
                    labelAlign : 'right' ,
//                    url: '../../UserManageAction_addUsermanagesByLDAP.action',
                    baseParams : {create : true },
                    //  labelWidth : 70 ,
                    items : [
                        {
                            fieldLabel : 'LDAP服务器地址' ,
                            name : 'ldapip',
//                            id:'ldapip',
                            width:163,
                            allowBlank: false,
                            regexText: '请输入LDAP服务器地址'
                        },{
                            fieldLabel : 'LDAP服务器端口' ,
                            name : 'ldapport',
//                            id:'ldapport',
                            width:163,
                            allowBlank: false,
                            regexText: '请输入LDAP服务器端口'

                        },{
                            fieldLabel : 'LDAP用户名' ,
                            name : 'ldapusername',
//                            id: 'ldapusername',
                            width:163
                        },{
                            fieldLabel : 'LDAP密码' ,
                            name : 'ldappassword',
//                            id: 'ldappassword',
                            width:163
                        },{
                            fieldLabel : 'LDAP节点属性值' ,
                            name : 'ldapnode',
//                            id: 'ldapnode',
                            width:163
                        },{
                            fieldLabel : 'LDAP搜索路径' ,
                            name : 'ldaphpath',
//                            id: 'ldaphpath',
                            width:163,
                            allowBlank: false,
                            regexText: '请输入LDAP搜索路径'
                        },{
                            fieldLabel : 'LDAP节点过滤条件' ,
                            name : 'ldapfilter',
//                            id: 'ldapfilter',
                            width:163,
                            allowBlank: false,
                            regexText: '请输入服务器端口'
                        },{
                            fieldLabel : '连接超时限制' ,
                            name : 'ldaptime',
//                            id: 'ldaptime',
                            width:163
                        }
                    ]
                })
            ],
            buttons : [
                {
                    text : ' 导入  ',
                    tooltip : '',
                    iconCls : 'imp',
                    handler : function() {
                        if(Ext.getCmp("ldapform").getForm().isValid()) {
                            Ext.getCmp("ldapform").getForm().submit({
                                url: '../../UserManageAction_addUsermanagesByLDAP.action',
                                waitTitle : '请等待' ,
                                waitMsg: '正在提交中',
                                success:function(form,action) {
                                    var msg = action.result.msg;
                                    Ext.Msg.alert('提示',msg);
                                    grid.render();
                                    ds.reload();
                                    win.close();
                                }
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
                    }
                }
            ]
        });
        win.show();
    }
});

var Model = new Object;
function lookmodel(){
    Model.lookxx();
}
function updmodel() {
    Model.updxx();
}


