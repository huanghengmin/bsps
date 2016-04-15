Ext.onReady(function () {
    var checkModel = new Ext.grid.CheckboxSelectionModel();
    var columnModel = new Ext.grid.ColumnModel([
        checkModel,
        {header:"源Ip", align:'center',dataIndex:'sourceIp', width:130},
        {header:"源端口",align:'center', dataIndex:'sourcePort', width:100},
        {header:"目标Ip",align:'center', dataIndex:'distIp', width:130},
        {header:"目标端口",align:'center', dataIndex:'distPort', width:100},
        {header:"协议类型",align:'center', dataIndex:'type', width:100},
        {header:"运行状态",align:'center', dataIndex:'flagrun', width:100,renderer:show_runflag},
//        {header:"描述", align:'center',dataIndex:'describe', width:100} ,
        {header:"操作",align:'center', dataIndex:'flag',renderer:flag_function, width:100}

    ]);
    columnModel.defaultSortable = true;

    function show_runflag(value, p, r){
        if(r.get("flagrun")=="1"){
            return String.format('<img src="../../img/icon/ok.png" alt="运行中" title="运行中" />');
        }else if(r.get("flagrun")=="0"){
            return String.format('<img src="../../img/icon/off.gif" alt="未运行" title="未运行" />');
        }
    }

    function flag_function() {
        return String.format('<a href="javascript:void(0);" onclick="modifyModel();return false;" style="color: green;">修改</a>'
            + '&nbsp;&nbsp;'
            + '<a id="delmodel.info" href="javascript:void(0);" onclick="delModel();return false;" style="color: green;">删除</a>');
    }

    var start = 0;
    var pageSize = 15;
    var grid_store = new Ext.data.GroupingStore({
        proxy:new Ext.data.HttpProxy({
            url:'../../ProcessAction_findConfig.action'

        }), //调用的动作
        reader:new Ext.data.JsonReader({
            totalProperty:'pclist',
            root:'pcrow'
        }, [
            {name:'id',mapping:'id'},
            {name:'sourceIp',mapping:'sourceIp'},
            {name:'sourcePort',mapping:'sourcePort'},
            {name:'distIp',mapping:'distIp' },
            {name:'distPort',mapping:'distPort'},
            {name:'isrun',mapping:'isrun'},
            {name:'flagrun',mapping:'flagrun'},
            {name:'describe',mapping:'describe'},
            {name:'type',mapping:'type'}
        ])
    });
    grid_store.load({
        params:{
            start:start, limit:pageSize
        }
    });

    var page_toolbar = new Ext.PagingToolbar({
        pageSize:pageSize,
        store:grid_store,
        displayInfo:true,
        displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });

    var hpgrid = new Ext.grid.GridPanel({
        id:'hpgrid',
        title:'',
        store:grid_store,
        cm:columnModel,
        sm:checkModel,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        renderTo:Ext.getBody(),
        viewConfig:{
            forceFit:true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
        },
        tbar:[
            //工具栏
            {
                id:'add',
                text:'添加',
                iconCls:'add',
                handler:function () {
                    addForm();
                    Ext.getCmp("protocol2").setValue("tcp");
                    addFormWindow(myForm);
                }
            } ,
            {
                id:'run',
                text:'启用',
                iconCls:'ok',
                handler:function () {
                    startModel();
                }
            }  ,
           /* {
                id:'runAll',
                text:'启用所有',
                iconCls:'ok',
                handler:function () {
                   startAllModel();
                }
            } ,*/
            {
                id:'clear',
                text:'清除',
                iconCls:'delete',
                handler:function () {
                    clearModel();
                }
            }  ,
            /*{
                id:'clearAll',
                text:'清除所有',
                iconCls:'delete',
                handler:function () {
                    clearAllModel();
                }
            }  ,*/
            {
                id:'delete',
                text:'删除',
                iconCls:'delete',
                handler:function () {
                   delBatchModel();
                }
            }
        ],
        frame:true,
        loadMask:true, // 载入遮罩动画
        autoShow:true ,
        bbar:page_toolbar
    });


    var port = new Ext.Viewport({
        layout:'fit',
        renderTo:Ext.getBody(),
        items:[hpgrid]
    });

    var protocolData = [
        ['udp', 'Udp 代理'],
        ['tcp', 'Tcp 代理']
    ];

    var protocolStore = new Ext.data.SimpleStore({fields:['value', 'name'], data:protocolData});
    var myForm;
    var win = null;
    var userJsonStore = new Ext.data.JsonStore({
        fields:[ "id", "username" ],
        url:'../../InterfaceManagerAction_readInterfaceComboBox.action',
        autoLoad:true,
        root:"rows"
    });

    function addForm() {
        myForm = new Ext.form.FormPanel({
            labelWidth:130,
            //renderTo : "formt",
            frame:true,
            defaultType:'textfield',
            buttonAlign:'right',
            labelAlign:'right',
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url:'../../ProcessAction_addConfig.action',
            baseParams:{create:true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText:'不能为空!',
                msgTarget:'side'
            },
            items:[
                new Ext.form.ComboBox({
                    hiddenName:'processEntity.type',
                    id:'protocol2',
                    fieldLabel:"代理类型",
                    emptyText:'请选择',
                    width:250,
                    store:protocolStore,
                    valueField:"value",
                    displayField:"name",
                    typeAhead:true,
                    mode:"local",
                    forceSelection:true,
                    triggerAction:"all",
                    OnFocus:true/*,
                    listeners:{
                        select:function (combo, record, index) {
                            var prvdr = record.get('value');

                        }
                    }*/
                }), new Ext.form.ComboBox({
                    hiddenName:'processEntity.sourceIp',
                    id:'sourceIp1',
                    fieldLabel:"源Ip",
                    emptyText:'请选择源Ip',
//                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
//                    regexText:'请输入正确的ip地址',
                    store:userJsonStore,
                    width:250,
                    valueField:"id",
                    displayField:"username",
                    typeAhead:true,
                    mode:"local",
                    forceSelection:true,
                    triggerAction:"all",
                    OnFocus:true,
                    allowBlank:false
                }), {
                    fieldLabel:'源端口',
                    name:'processEntity.sourcePort',
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'这个不是端口类型1~65536',
                    emptyText:'请输入端口1~65536',
                    allowBlank:false,
                    listeners:{
                        blur:function () {
                            var cmp = this;
                            var value = cmp.getValue();
                            if((0<=value&&value<=1024)||value==8000||value ==8080||value == 8443||value==10000||value>=65535){
                                Ext.MessageBox.show({
                                    title:'提示',
                                    width:400,
                                    msg:'0-1024端口可能被系统占用,端口不能为BS管理服务端口8000,8080,10000,8443,且端口不能大于65535!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        if(e=='ok'){
                                            cmp.setValue('');
                                        }
                                    }
                                });
                            }
                        }
                    }
                },
                {
                    fieldLabel:'目标Ip',
                    name:'processEntity.distIp',
                    width:250,
                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                    regexText:'请输入正确的ip地址',
                    allowBlank:false
                }, {
                    fieldLabel:'目标端口',
                    name:'processEntity.distPort',
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'这个不是端口类型1~65536',
                    emptyText:'请输入端口1~65536',
                    allowBlank:false/*,
                     listeners:{
                     blur:function () {
                     var cmp = this;
                     var value = cmp.getValue();
                     if((0<=value&&value<=1024)||value==8000||value ==8080||value == 8443||value==10000||value>=65535){
                     Ext.MessageBox.show({
                     title:'提示',
                     width:400,
                     msg:'0-1024端口可能被系统占用,端口不能为BS管理服务端口8000,8080,10000,8443,且端口不能大于65535!',
                     buttons:Ext.MessageBox.OK,
                     buttons:{'ok':'确定'},
                     icon:Ext.MessageBox.INFO,
                     closable:false,
                     fn:function(e){
                     if(e=='ok'){
                     cmp.setValue('');
                     }
                     }
                     });
                     }
                     }
                     }*/
                }
            ]
        });
    }

    function addFormWindow(form) {
        var form = form;
        win = new Ext.Window({
            title:'',
            width:500,
            items:[form],
            id:'winhttp',
            bbar:[
                '->',
                {
                    text:'确定',
                    handler:function () {
                        //FormPanel自身带异步提交方式
                        if (form.getForm().isValid()) {
                            form.getForm().submit({
                                waitTitle:'请等待',
                                waitMsg:'正在提交中',
                                success:function (form, action) {
                                    var msg = action.result.msg;
                                    Ext.Msg.alert('提示', msg);
                                    grid_store.reload();
                                    win.close();
                                },failure:function (form, action) {
                                    var msg = action.result.msg;
                                    Ext.Msg.alert('提示', msg);
                                }
                            });
                        } else {
                            Ext.Msg.alert('提示', '请先填写完正确信息');
                        }
                    }
                },
                {
                    text:'关闭',
                    handler:function () {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    }

    function updateForm(recode){
        var id =   recode.get("id");
        var sourceIp =   recode.get("sourceIp");
        var sourcePort =   recode.get("sourcePort");
        var distIp =   recode.get("distIp");
        var distPort =   recode.get("distPort");
        var protocol =   recode.get("type");
        myForm = new Ext.form.FormPanel({
            labelWidth:130,
            frame:true,
            defaultType:'textfield',
            buttonAlign:'right',
            labelAlign:'right',
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url:'../../ProcessAction_updateConfig.action',
//            baseParams:{create:true },
            baseParams:{id:id,oldSourceIp:sourceIp,oldSourcePort:sourcePort,oldDistIp:distIp,oldDistPort:distPort,oldType:protocol},
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText:'不能为空!',
                msgTarget:'side'
            },
            items:[
                new Ext.form.ComboBox({
                    hiddenName:'processEntity.type',
                    id:'updateprotocol2',
                    fieldLabel:"代理类型",
                    emptyText:'请选择',
                    width:250,
                    store:protocolStore,
                    value:protocol,
                    valueField:"value",
                    displayField:"name",
                    typeAhead:true,
                    mode:"local",
                    forceSelection:true,
                    triggerAction:"all",
                    OnFocus:true/*,
                    listeners:{
                        select:function (combo, record, index) {
                            var prvdr = record.get('value');
                           *//* if (prvdr == "https") {
                                Ext.getCmp("updatewinhttp").close();
                                win.close();
                                updateHsForm(recode);
                                Ext.getCmp("updateprotocol1").setValue("https");
                                updateHsWinOpen(myForm);
                            }*//*
                        }
                    }*/
                }), new Ext.form.ComboBox({
                    hiddenName:'processEntity.sourceIp',
                    id:'sourceIp1',
                    fieldLabel:"源Ip",
                    value:sourceIp,
                    emptyText:'请选择源Ip',
//                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
//                    regexText:'请输入正确的ip地址',
                    store:userJsonStore,
                    width:250,
                    valueField:"id",
                    displayField:"username",
                    typeAhead:true,
                    mode:"local",
                    forceSelection:true,
                    triggerAction:"all",
                    OnFocus:true,
                    allowBlank:false
                }), {
                    fieldLabel:'源端口',
                    name:'processEntity.sourcePort',
                    value:sourcePort,
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'这个不是端口类型1~65536',
                    emptyText:'请输入端口1~65536',
                    allowBlank:false,
                    listeners:{
                        blur:function () {
                            var cmp = this;
                            var value = cmp.getValue();
                            if((0<=value&&value<=1024)||value==8000||value ==8080||value == 8443||value==10000||value>=65535){
                                Ext.MessageBox.show({
                                    title:'提示',
                                    width:400,
                                    msg:'0-1024端口可能被系统占用,端口不能为BS管理服务端口8000,8080,10000,8443,且端口不能大于65535!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        if(e=='ok'){
                                            cmp.setValue('');
                                        }
                                    }
                                });
                            }
                        }
                    }
                },
                {
                    fieldLabel:'目标Ip',
                    name:'processEntity.distIp',
                    value:distIp,
                    width:250,
                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                    regexText:'请输入正确的ip地址',
                    allowBlank:false
                }, {
                    fieldLabel:'目标端口',
                    name:'processEntity.distPort',
                    value:distPort,
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'这个不是端口类型1~65536',
                    emptyText:'请输入端口1~65536',
                    allowBlank:false/*,
                     listeners:{
                     blur:function () {
                     var cmp = this;
                     var value = cmp.getValue();
                     if((0<=value&&value<=1024)||value==8000||value ==8080||value == 8443||value==10000||value>=65535){
                     Ext.MessageBox.show({
                     title:'提示',
                     width:400,
                     msg:'0-1024端口可能被系统占用,端口不能为BS管理服务端口8000,8080,10000,8443,且端口不能大于65535!',
                     buttons:Ext.MessageBox.OK,
                     buttons:{'ok':'确定'},
                     icon:Ext.MessageBox.INFO,
                     closable:false,
                     fn:function(e){
                     if(e=='ok'){
                     cmp.setValue('');
                     }
                     }
                     });
                     }
                     }
                     }*/
                }
            ]
        });
    }

    function updateFormWindow(form){
        var form = form;
        win = new Ext.Window({
            title:'',
            width:500,
            items:[form],
            id:'updatewinhttp',
            bbar:[
                '->',
                {
                    text:'确定',
                    handler:function () {
                        //FormPanel自身带异步提交方式
                        if (form.getForm().isValid()) {
                            form.getForm().submit({
                                waitTitle:'请等待',
                                waitMsg:'正在提交中',
                                success:function (form, action) {
                                    var msg = action.result.msg;
                                    Ext.Msg.alert('提示', msg);
                                    grid_store.reload();
                                    win.close();
                                },failure:function (form, action) {
                                    var msg = action.result.msg;
                                    Ext.Msg.alert('提示', msg);
                                }
                            });
                        } else {
                            Ext.Msg.alert('提示', '请先填写完正确信息');
                        }
                    }
                },
                {
                    text:'关闭',
                    handler:function () {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    }

    Model.delModel = function delModel() {
        var id =   hpgrid.getSelectionModel().getSelections()[0].get("id");
        Ext.MessageBox.confirm('提示', '是否确定删除这条记录', callBack);
        function callBack(qrid) {
            if ("yes" == qrid) {
                Ext.Ajax.request({
                    url:'../../ProcessAction_delConfig.action',
                    success:function (response, result) {
                        Ext.Msg.alert('提示', "删除成功");
                        hpgrid.render();
                        grid_store.reload();
                    },
                    failure:function (response, result) {
                        Ext.Msg.alert('提示', "删除失败");
                    },
                    params:{id:id}
                });
            }
        }
    }

    Model.delBatchModel = function delBatchModel() {
        var selModel = hpgrid.getSelectionModel();
        var count = selModel.getCount();
        if(count==0){
            Ext.MessageBox.show({
                title:'信息',
                width:200,
                msg:'您没有勾选任何记录!',
                animEl:'btnStart.info',
                buttons:{'ok':'确定'},
                icon:Ext.MessageBox.WARNING,
                closable:false
            });

        }else if(count > 0){
            var idArray = new Array();
            var record = selModel.getSelections();
            for(var i = 0; i < record.length; i++){
                idArray[i] = record[i].get('id');
            }
            Ext.MessageBox.show({
                title:'信息',
                width:200,
                msg:'确定要删除所选记录?',
                animEl:'btnStart.info',
                buttons:{'ok':'确定','no':'取消'},
                icon:Ext.MessageBox.WARNING,
                closable:false,
                fn:function(e){
                    if(e=='ok'){
                        var myMask = new Ext.LoadMask(Ext.getBody(),{
                            msg : '正在停用端口,请稍后...',
                            removeMask : true
                        });
                        myMask.show();
                        Ext.Ajax.request({
                            url : '../../ProcessAction_delBatch.action',
                            params :{idArray : idArray },
                            success : function(r,o){
                                var respText = Ext.util.JSON.decode(r.responseText);
                                var msg = respText.msg;
                                myMask.hide();
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:300,
                                    msg:msg,
                                    animEl:'btnStart.info',
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        if(e=='ok'){
                                            hpgrid.render();
                                            hpgrid.getStore().reload();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }
    }

    Model.updateModel = function updateModel() {
        var recode = hpgrid.getSelectionModel().getSelected();
        var protocol =   recode.get("type");
         if(protocol=="tcp"){
            updateForm(recode);
            Ext.getCmp("updateprotocol2").setValue("tcp");
            updateFormWindow(myForm);
        } else if(protocol=="udp") {
            updateForm(recode);
            Ext.getCmp("updateprotocol2").setValue("udp");
            updateFormWindow(myForm);
        }
    }

    Model.startModel = function startModel()  {
        var selModel = hpgrid.getSelectionModel();
        var count = selModel.getCount();
        if(count==0){
            Ext.MessageBox.show({
                title:'信息',
                width:200,
                msg:'您没有勾选任何记录!',
                animEl:'btnStart.info',
                buttons:{'ok':'确定'},
                icon:Ext.MessageBox.WARNING,
                closable:false
            });

        }else if(count > 0){
            var idArray = new Array();
            var record = selModel.getSelections();
            for(var i = 0; i < record.length; i++){
                idArray[i] = record[i].get('id');
            }
            Ext.MessageBox.show({
                title:'信息',
                width:200,
                msg:'确定要启用所选记录?',
                animEl:'btnStart.info',
                buttons:{'ok':'确定','no':'取消'},
                icon:Ext.MessageBox.WARNING,
                closable:false,
                fn:function(e){
                    if(e=='ok'){
                        var myMask = new Ext.LoadMask(Ext.getBody(),{
                            msg : '正在启用端口,请稍后...',
                            removeMask : true
                        });
                        myMask.show();
                        Ext.Ajax.request({
                            url : '../../ProcessAction_enable.action',
                            params :{idArray : idArray },
                            success : function(r,o){
                                var respText = Ext.util.JSON.decode(r.responseText);
                                var msg = respText.msg;
                                myMask.hide();
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:300,
                                    msg:msg,
                                    animEl:'btnStart.info',
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        if(e=='ok'){
                                            hpgrid.render();
                                            hpgrid.getStore().reload();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }
    }

    Model.startAllModel = function startAllModel(){
        Ext.MessageBox.confirm('提示', '是否确定启用这些记录', callBack);
        function callBack(qrid) {
            if ("yes" == qrid) {
                Ext.Ajax.request({
                    url:'../../ProcessAction_enableAll.action',
                    success:function (response, result) {
                        Ext.Msg.alert('提示', "启用成功");
                        hpgrid.render();
                        grid_store.reload();
                    },
                    failure:function (response, result) {
                        Ext.Msg.alert('提示', "启用失败");
                    }
                });
            }
        }
    }

    Model.clearModel = function clearModel() {
                var selModel = hpgrid.getSelectionModel();
                var count = selModel.getCount();
                if(count==0){
                    Ext.MessageBox.show({
                        title:'信息',
                        width:200,
                        msg:'您没有勾选任何记录!',
                        animEl:'btnStart.info',
                        buttons:{'ok':'确定'},
                        icon:Ext.MessageBox.WARNING,
                        closable:false
                    });

                }else if(count > 0){
                    var idArray = new Array();
                    var record = selModel.getSelections();
                    for(var i = 0; i < record.length; i++){
                        idArray[i] = record[i].get('id');
                    }
                    Ext.MessageBox.show({
                        title:'信息',
                        width:200,
                        msg:'确定要停用所选记录?',
                        animEl:'btnStart.info',
                        buttons:{'ok':'确定','no':'取消'},
                        icon:Ext.MessageBox.WARNING,
                        closable:false,
                        fn:function(e){
                            if(e=='ok'){
                                var myMask = new Ext.LoadMask(Ext.getBody(),{
                                    msg : '正在停用端口,请稍后...',
                                    removeMask : true
                                });
                                myMask.show();
                                Ext.Ajax.request({
                                    url : '../../ProcessAction_clear.action',
                                    params :{idArray : idArray },
                                    success : function(r,o){
                                        var respText = Ext.util.JSON.decode(r.responseText);
                                        var msg = respText.msg;
                                        myMask.hide();
                                        Ext.MessageBox.show({
                                            title:'信息',
                                            width:300,
                                            msg:msg,
                                            animEl:'btnStart.info',
                                            buttons:{'ok':'确定'},
                                            icon:Ext.MessageBox.INFO,
                                            closable:false,
                                            fn:function(e){
                                                if(e=='ok'){
                                                    hpgrid.render();
                                                    hpgrid.getStore().reload();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }

    Model.clearAllModel = function clearAllModel(){
        Ext.MessageBox.confirm('提示', '是否确定清除这些记录', callBack);
        function callBack(qrid) {
            if ("yes" == qrid) {
                Ext.Ajax.request({
                    url:'../../ProcessAction_clearAll.action',
                    success:function (response, result) {
                        Ext.Msg.alert('提示', "清除成功");
                        hpgrid.render();
                        grid_store.reload();
                    },
                    failure:function (response, result) {
                        Ext.Msg.alert('提示', "清除失败");
                    }
                });
            }
        }
    }


});

var Model = new Object;
function delModel() {
    Model.delModel();
}
function delBatchModel() {
    Model.delBatchModel();
}
function modifyModel() {
    Model.updateModel();
}
function startModel() {
    Model.startModel();
}
function startAllModel() {
    Model.startAllModel();
}
function clearModel() {
    Model.clearModel();
}
function clearAllModel() {
    Model.clearAllModel();
}

