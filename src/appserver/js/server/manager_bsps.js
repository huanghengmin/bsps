Ext.onReady(function () {
    var hpcsm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});   //复选框单选
    var hpcm = new Ext.grid.ColumnModel([
        hpcsm,
        {header:"代理IP", dataIndex:'managerip', width:130},
        {header:"代理端口", dataIndex:'managerport', width:100},
        {header:"映射IP", dataIndex:'proxyip', width:130},
        {header:"映射端口", dataIndex:'proxyport', width:100},
        {header:"证书站点", dataIndex:'site_name', width:100},
        {header:"服务器证书站点", dataIndex:'server_site_name', width:100},
        {header:"代理类型", dataIndex:'protocol', width:100,renderer:showProtocol},
        {header:"操作", dataIndex:"button", renderer:show_flag, width:100}
    ]);
    hpcm.defaultSortable = true;

    function showProtocol(value,p,r){
        if(r.get("protocol")=="http"){
            return String.format('Http 反向代理');
        }else if(r.get("protocol")=="tcp"){
            return String.format('Tcp 代理');
        }else if(r.get("protocol")=="https"){
            return String.format('Https 反向代理');
        }else if(r.get("protocol")=="thttp"){
            return String.format('正向代理');
        }
    }

    function show_flag(value, p, r) {
        return String
            .format('<a href="javascript:void(0);" onclick="updateModel();return false;" style="color: green;">修改</a>'
            + '&nbsp;&nbsp;'
            + '<a id="delmodel.info" href="javascript:void(0);" onclick="delmodel();return false;" style="color: green;">删除</a>');
    };
    var start = 0;
    var pageSize = 15;
    var hpds = new Ext.data.GroupingStore({
        proxy:new Ext.data.HttpProxy({
            url:'../../BsProxyConfigAction_findConfig.action'

        }), //调用的动作
        reader:new Ext.data.JsonReader({
            totalProperty:'pclist',
            root:'pcrow'
        }, [
            {name:'id',mapping:'id'},
            {name:'site_name',mapping:'site_name'},
            {name:'site_id',mapping:'site_id'},
            {name:'server_site_name',mapping:'server_site_name'},
            {name:'server_site_id',mapping:'server_site_id'},
            {name:'managerip',mapping:'managerip'},
            {name:'managerport',mapping:'managerport'},
            {name:'proxyip',mapping:'proxyip' },
            {name:'proxyport',mapping:'proxyport'},
            {name:'protocol',mapping:'protocol'}
        ])
    });
    hpds.load({
        params:{
            start:start, limit:pageSize
        }
    });
//    hpds.load();

    var page_toolbar = new Ext.PagingToolbar({
        pageSize:pageSize,
        store:hpds,
        displayInfo:true,
        displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });

    var hpgrid = new Ext.grid.GridPanel({
        id:'hpgrid',
        title:'',
        store:hpds,
        cm:hpcm,
        sm:hpcsm,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
//        renderTo:Ext.getBody(),
        viewConfig:{
            forceFit:true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
        },
        tbar:[
            {
                id:'New1',
                text:' 新增  ',
                tooltip:'新建一个表单',
                iconCls:'add',
                handler:function () {
                    createHttpForm();
                    Ext.getCmp("protocol2").setValue("http");
                    http_window(my_form);
                }
            }
        ],
//        height:600,
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

    var protocol_data = [
        ['http', 'Http 反向代理'],
        ['https', 'Https 反向代理'],
        ['tcp', 'Tcp 代理'],
        ['thttp', '正向代理']
    ];

    var protocolstore= new Ext.data.SimpleStore({
        fields:['value', 'name'],
        data:protocol_data
    });

    var my_form;
    var win = null;

    var userJsonStore = new Ext.data.JsonStore({
        fields:[ "id", "username" ],
        url:'../../InterfaceManagerAction_readInterfaceComboBox.action',
        autoLoad:true,
        root:"rows"
    });

    function createHttpForm() {
        my_form = new Ext.form.FormPanel({
            labelWidth:130,
            frame:true,
            defaultType:'textfield',
            buttonAlign:'right',
            labelAlign:'right',
            url:'../../BsProxyConfigAction_addConfig.action',
            baseParams:{create:true },
            defaults:{
                blankText:'不能为空!',
                msgTarget:'side'
            },
            items:[
                new Ext.form.ComboBox({
                    hiddenName:'protocol',
                    id:'protocol2',
                    fieldLabel:"代理类型",
                    emptyText:'请选择',
                    width:250,
                    store:protocolstore,
                    valueField:"value",
                    displayField:"name",
                    typeAhead:true,
                    mode:"local",
                    forceSelection:true,
                    triggerAction:"all",
                    OnFocus:true,
                    listeners:{
                        select:function (combo, record, index) {
                            var prvdr = record.get('value');
                            if (prvdr == "https") {
                                Ext.getCmp("winhttp").close();
                                win.close();
                                createHttpsForm();
                                Ext.getCmp("protocol1").setValue("https");
                                https_window(my_form);
                            } else if (prvdr == "thttp") {
                                Ext.getCmp("winhttp").close();
                                win.close();
                                createTHttpForm();
                                Ext.getCmp("protocol2").setValue("thttp");
                                tHttp_window(my_form);
                            }
                        }
                    }
                }), new Ext.form.ComboBox({
                    hiddenName:'managerip',
                    id:'managerip1',
                    fieldLabel:"代理IP",
                    emptyText:'请选择代理IP',
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
                    fieldLabel:'代理端口',
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'请输入正确的端口',
                    name:'managerport',
                    width:250,
                    allowBlank:false,
                    listeners:{
                        blur:function () {
                            var cmp = this;
                            var value = cmp.getValue();
                            if((0<=value&&value<=1024)||value==8000||value ==8080||value == 8443||value==10000||value>=65535){
                                Ext.MessageBox.show({
                                    title:'提示',
                                    width:400,
                                    msg:'0-1024端口可能被系统占用,端口不能为BS代理服务端口8000,8080,8443,且端口不能大于65535!',
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
                    fieldLabel:'映射IP',
                    name:'proxyip',
                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                    regexText:'请输入正确的ip地址',
                    width:250,
                    regex:/^\S{1,30}$/,
                    allowBlank:false,
                    regexText:'请输入映射Ip'
                }, {
                    fieldLabel:'映射端口',
                    name:'proxyport',
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'请输入正确的端口',
                    width:250,
                    allowBlank:false
                }
            ]
        });
    };

    function createTHttpForm() {
        my_form = new Ext.form.FormPanel({
            labelWidth:130,
            frame:true,
            defaultType:'textfield',
            buttonAlign:'right',
            labelAlign:'right',
            url:'../../BsProxyConfigAction_addConfig.action',
            baseParams:{create:true },
            defaults:{
                blankText:'不能为空!',
                msgTarget:'side'
            },
            items:[
                new Ext.form.ComboBox({
                    hiddenName:'protocol',
                    id:'protocol2',
                    fieldLabel:"代理类型",
                    emptyText:'请选择',
                    width:250,
                    store:protocolstore,
                    valueField:"value",
                    displayField:"name",
                    typeAhead:true,
                    mode:"local",
                    forceSelection:true,
                    triggerAction:"all",
                    OnFocus:true,
                    listeners:{
                        select:function (combo, record, index) {
                            var prvdr = record.get('value');
                            if (prvdr == "https") {
                                Ext.getCmp("winhttp").close();
                                win.close();
                                createHttpsForm();
                                Ext.getCmp("protocol1").setValue("https");
                                https_window(my_form);
                            } else if(prvdr=="http"){
                                Ext.getCmp("winhttp").close();
                                win.close();
                                createHttpForm();
                                Ext.getCmp("protocol2").setValue("http");
                                http_window(my_form);
                            }  else if(prvdr=="tcp"){
                                Ext.getCmp("winhttp").close();
                                win.close();
                                createHttpForm();
                                Ext.getCmp("protocol2").setValue("tcp");
                                http_window(my_form);
                            }
                        }
                    }
                }), new Ext.form.ComboBox({
                    hiddenName:'managerip',
                    id:'managerip1',
                    fieldLabel:"代理IP",
                    emptyText:'请选择代理IP',
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
                    fieldLabel:'代理端口',
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'请输入正确的端口',
                    name:'managerport',
                    width:250,
                    allowBlank:false,
                    listeners:{
                        blur:function () {
                            var cmp = this;
                            var value = cmp.getValue();
                            if((0<=value&&value<=1024)||value==8000||value ==8080||value == 8443||value==10000||value>=65535){
                                Ext.MessageBox.show({
                                    title:'提示',
                                    width:400,
                                    msg:'0-1024端口可能被系统占用,端口不能为BS代理服务端口8000,8080,8443,且端口不能大于65535!',
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
                }/*,
                {
                    fieldLabel:'映射IP',
                    name:'proxyip',
                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                    regexText:'请输入正确的ip地址',
                    width:250,
                    regex:/^\S{1,30}$/,
                    allowBlank:false,
                    regexText:'请输入映射Ip'
                }, {
                    fieldLabel:'映射端口',
                    name:'proxyport',
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'请输入正确的端口',
                    width:250,
                    allowBlank:false
                }*/
            ]
        });
    };

    function http_window(form) {
        var my_form = form;
        win = new Ext.Window({
            title:'',
            width:500,
            items:[my_form],
            id:'winhttp',
            bbar:[
                '->',
                {
                    text:'确定',
                    handler:function () {
                        //FormPanel自身带异步提交方式
                        if (my_form.getForm().isValid()) {
                            my_form.getForm().submit({
//                            url: '../../UserManageAction_addUserManage.action',
                                waitTitle:'请等待',
                                waitMsg:'正在提交中',
                                success:function (form, action) {
                                    var msg = action.result.msg;
                                    Ext.Msg.alert('提示', msg);
                                    hpds.reload();
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
    };

    function tHttp_window(form) {
        var my_form = form;
        win = new Ext.Window({
            title:'',
            width:500,
            items:[my_form],
            id:'winhttp',
            bbar:[
                '->',
                {
                    text:'确定',
                    handler:function () {
                        //FormPanel自身带异步提交方式
                        if (my_form.getForm().isValid()) {
                            my_form.getForm().submit({
//                            url: '../../UserManageAction_addUserManage.action',
                                waitTitle:'请等待',
                                waitMsg:'正在提交中',
                                success:function (form, action) {
                                    var msg = action.result.msg;
                                    Ext.Msg.alert('提示', msg);
                                    hpds.reload();
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
    };

    var siteStore = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../SiteAction_findBySiteStore.action',
            method : "POST"

        }),
        reader : new Ext.data.JsonReader({
            fields : ["site", "site_name"],
            totalProperty : 'total',
            root : 'rows'
        })
    });

    function createHttpsForm() {
        my_form = new Ext.form.FormPanel({
            labelWidth:160,
            frame:true,
            fileUpload:true,
            defaultType:'textfield',
            buttonAlign:'right',
            labelAlign:'right',
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url:'../../BsProxyConfigAction_addConfig.action',
            baseParams:{create:true },
            defaults:{
                blankText:'不能为空!',
                msgTarget:'side'
            },
            items:[
                new Ext.form.ComboBox({
                    hiddenName:'protocol',
                    id:'protocol1',
                    fieldLabel:"代理类型",
                    emptyText:'请选择',
                    width:250,
                    store:protocolstore,
                    valueField:"value",
                    displayField:"name",
                    typeAhead:true,
                    mode:"local",
                    forceSelection:true,
                    triggerAction:"all",
                    OnFocus:true,
                    listeners:{
                        select:function (combo, record, index) {
                            var prvdr = record.get('value');
                            if (prvdr == "http") {
                                Ext.getCmp("winhttps").close();
                                createHttpForm();
                                Ext.getCmp("protocol2").setValue("http");
                                http_window(my_form);
                            }else if(prvdr=="tcp"){
                                Ext.getCmp("winhttps").close();
                                createHttpForm();
                                Ext.getCmp("protocol2").setValue("tcp");
                                http_window(my_form);
                            }else if(prvdr=="thttp"){
                                Ext.getCmp("winhttps").close();
                                createHttpForm();
                                Ext.getCmp("protocol2").setValue("thttp");
                                http_window(my_form);
                            }
                        }
                    }
                }), new Ext.form.ComboBox({
                    mode : 'remote',// 指定数据加载方式，如果直接从客户端加载则为local，如果从服务器断加载// 则为remote.默认值为：remote
                    border : true,
                    frame : true,
                    pageSize : 15,// 当元素加载的时候，如果返回的数据为多页，则会在下拉列表框下面显示一个分页工具栏，该属性指定每页的大小
                    // 在点击分页导航按钮时，将会作为start及limit参数传递给服务端，默认值为0，只有在mode='remote'的时候才能够使用
//                title : '省份',
                    editable : false,
                    emptyText:'请选择匹配站点',
                    fieldLabel:'请选择匹配站点',
                    id:'my.add.site',
//                hiddenName : 'hzihCa.hzihprovince',
                    triggerAction : "all",// 是否开启自动查询功能
                    store : siteStore,// 定义数据源
                    displayField : "site_name",// 关联某一个逻辑列名作为显示值
                    valueField : "site",// 关联某一个逻辑列名作为实际值
                    //mode : "local",// 如果数据来自本地用local 如果来自远程用remote默认为remote
                    emptyText : "请选择",// 没有选择时候的默认值
//                    name:'site',
                    hiddenName:'site',
                    allowBlank:true,
                    blankText:"请选择匹配站点"/*,
                     listeners:{
                     select:function(){
                     var value = this.getValue();
                     cityStore.proxy   = new Ext.data.HttpProxy ({
                     url:"../../../DistrictAction_comboChild.action?parentId="+value
                     })
                     cityStore.load();
                     } ,
                     render:function(){
                     Ext.getCmp("hzih.ca.addCa.province").getEl().up('.x-form-item').setDisplayed(false)
                     }
                     }*/

                }), new Ext.form.ComboBox({
                    mode : 'remote',// 指定数据加载方式，如果直接从客户端加载则为local，如果从服务器断加载// 则为remote.默认值为：remote
                    border : true,
                    frame : true,
                    pageSize : 15,// 当元素加载的时候，如果返回的数据为多页，则会在下拉列表框下面显示一个分页工具栏，该属性指定每页的大小
                    // 在点击分页导航按钮时，将会作为start及limit参数传递给服务端，默认值为0，只有在mode='remote'的时候才能够使用
//                title : '省份',
                    editable : false,
                    emptyText:'请选择目标服务器站点',
                    fieldLabel:'请选择目标服务器站点',
                    id:'my.add.server_site',
//                hiddenName : 'hzihCa.hzihprovince',
                    triggerAction : "all",// 是否开启自动查询功能
                    store : siteStore,// 定义数据源
                    displayField : "site_name",// 关联某一个逻辑列名作为显示值
                    valueField : "site",// 关联某一个逻辑列名作为实际值
                    //mode : "local",// 如果数据来自本地用local 如果来自远程用remote默认为remote
                    emptyText : "请选择",// 没有选择时候的默认值
//                    name:'site',
                    hiddenName:'serverSite',
                    allowBlank:true,
                    blankText:"请选择目标服务器站点"/*,
                     listeners:{
                     select:function(){
                     var value = this.getValue();
                     cityStore.proxy   = new Ext.data.HttpProxy ({
                     url:"../../../DistrictAction_comboChild.action?parentId="+value
                     })
                     cityStore.load();
                     } ,
                     render:function(){
                     Ext.getCmp("hzih.ca.addCa.province").getEl().up('.x-form-item').setDisplayed(false)
                     }
                     }*/

                }),/*,
                {
                    id:'uploadFile',
                    fieldLabel:"请选择上传证书",
                    width:250,
                    name:'uploadFile',
                    xtype:'textfield',
                    inputType:'file',
                    allowBlank:false,
                    regexText:'请选择要上传的证书',
                    listeners:{
                        render:function () {
                            Ext.get('uploadFile').on("change", function () {
                                var file = this.getValue();
                                var fs = file.split('.');
                                if (fs[fs.length - 1].toLowerCase() != 'p12' && fs[fs.length - 1].toLowerCase() != 'pfx') {
                                    Ext.MessageBox.show({
                                        title:'信息',
                                        width:200,
                                        msg:'上传文件格式不对,请重新选择!',
                                        buttons:{'ok':'确定'},
                                        icon:Ext.MessageBox.ERROR,
                                        closable:false,
                                        fn:function (e) {
                                            if (e == 'ok') {
                                                Ext.getCmp('uploadFile').setValue('');
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }

                }, {
                    fieldLabel:'证书密码',
                    name:'ecpassword',
                    inputType:'password',
                    width:250,
                    regex:/^\S{4,20}$/,
                    allowBlank:false,
                    regexText:'请输入应用名称'
                },*/
                new Ext.form.ComboBox({
                    hiddenName:'managerip',
                    id:'managerip1',
                    fieldLabel:"代理IP",
//                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
//                    regexText:'请输入正确的ip地址',
                    emptyText:'请选择代理IP',
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
                    fieldLabel:'代理端口',
                    name:'managerport',
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'请输入正确的端口',
                    allowBlank:false,
                    listeners:{
                        blur:function () {
                            var cmp = this;
                            var value = cmp.getValue();
                            if((0<=value&&value<=1024)||value==8000||value ==8080||value == 8443||value==10000||value>=65535){
                                Ext.MessageBox.show({
                                    title:'提示',
                                    width:400,
                                    msg:'0-1024端口可能被系统占用,端口不能为BS代理服务端口8000,8080,8443,且端口不能大于65535!',
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
                },{
                    fieldLabel:'映射IP',
                    name:'proxyip',
                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                    regexText:'请输入正确的ip地址',
                    width:250,
                    allowBlank:false
                },{
                    fieldLabel:'映射端口',
                    name:'proxyport',
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'请输入正确的端口',
                    allowBlank:false
                }
            ]
        });
    };

    function https_window(form) {
        var my_form = form;
        win = new Ext.Window({
            id:'winhttps',
            title:'',
            width:500,
            items:[my_form],
            bbar:[
                '->',
                {
                    text:'确定',
                    handler:function () {
                        if (my_form.getForm().isValid()) {
                            my_form.getForm().submit({
//                            url: '../../UserManageAction_addUserManage.action',
                                waitTitle:'请等待',
                                waitMsg:'正在提交中',
                                success:function (form, action) {
                                    var msg = action.result.msg;
                                        Ext.Msg.alert('提示', msg);
                                        hpds.reload();
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
    };

    function updateMyForm(recode){
        var id =   recode.get("id");
        var site_name =   recode.get("site_name");
        var site_id =   recode.get("site_id");
        var server_site_name =   recode.get("server_site_name");
        var server_site_id =   recode.get("server_site_id");
        var managerip =   recode.get("managerip");
        var managerport =   recode.get("managerport");
        var proxyip =   recode.get("proxyip");
        var proxyport =   recode.get("proxyport");
        var protocol =   recode.get("protocol");
        myform = new Ext.form.FormPanel({
            labelWidth:130,
            //renderTo : "formt",
            frame:true,
            defaultType:'textfield',
            buttonAlign:'right',
            labelAlign:'right',
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url:'../../BsProxyConfigAction_updateConfig.action',
//            baseParams:{create:true },
            baseParams:{id:id,oldManagerip:managerip,oldManagerPort:managerport,oldProxyip:proxyip,oldProxyport:proxyport,oldProtocol:protocol,oldSiteId:site_id,oldServerSiteId:server_site_id},
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText:'不能为空!',
                msgTarget:'side'
            },
            items:[
                new Ext.form.ComboBox({
                    hiddenName:'protocol',
                    id:'updateprotocol2',
                    fieldLabel:"代理类型",
                    emptyText:'请选择',
                    width:250,
                    store:protocolstore,
                    value:protocol,
                    valueField:"value",
                    displayField:"name",
                    typeAhead:true,
                    mode:"local",
                    forceSelection:true,
                    triggerAction:"all",
                    OnFocus:true,
                    listeners:{
                        select:function (combo, record, index) {
                            var prvdr = record.get('value');
                            if (prvdr == "https") {
                                Ext.getCmp("updatewinhttp").close();
                                win.close();
                                updateHsForm(recode);
                                Ext.getCmp("updateprotocol1").setValue("https");
                                updateHsWinOpen(myform);
                            }else if(prvdr=="thttp"){
                                Ext.getCmp("updatewinhttp").close();
                                win.close();
                                updateTHttpMyForm(recode);
                                Ext.getCmp("updateprotocol2").setValue("thttp");
                                updateThttpWinOpen(myform);
                            }
                        }
                    }
                }), new Ext.form.ComboBox({
                    hiddenName:'managerip',
                    id:'managerip1',
                    fieldLabel:"管理IP",
                    value:managerip,
                    emptyText:'请选择管理IP',
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
                    fieldLabel:'管理端口',
                    name:'managerport',
                    value:managerport,
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'请输入正确的端口',
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
                    fieldLabel:'代理IP',
                    name:'proxyip',
                    value:proxyip,
                    id:'update.proxyip',
                    width:250,
                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                    regexText:'请输入正确的ip地址',
                    allowBlank:false
                }, {
                    fieldLabel:'代理端口',
                    name:'proxyport',
                    value:proxyport,
                    id:'update.proxyport',
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'请输入正确的端口',
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

    function updateWinOpen(form){
        var myform = form;
        win = new Ext.Window({
            title:'',
            width:500,
//            height :440,
            items:[myform],
            id:'updatewinhttp',
            bbar:[
                '->',
                {
                    text:'确定',
                    handler:function () {
                        //FormPanel自身带异步提交方式
                        if (myform.getForm().isValid()) {
                            myform.getForm().submit({
//                            url: '../../UserManageAction_addUserManage.action',
                                waitTitle:'请等待',
                                waitMsg:'正在提交中',
                                success:function (form, action) {
                                    var msg = action.result.msg;
//                                    if (msg == "mmcw") {
//                                        Ext.Msg.alert('提示',msg);
//                                    } else if (msg == "ssadd") {
//                                        Ext.Msg.alert('提示', '应用名称重复');
//                                    } else if (msg == "portcf") {
//                                        Ext.Msg.alert('提示', '端口重复');
//                                    } else {
                                    Ext.Msg.alert('提示', msg);
//                                    hpgrid.render();
                                    hpds.reload();
                                    win.close();
//                                    }
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

    function updateThttpWinOpen(form){
        var myform = form;
        win = new Ext.Window({
            title:'',
            width:500,
//            height :440,
            items:[myform],
            id:'updatewinhttp',
            bbar:[
                '->',
                {
                    text:'确定',
                    handler:function () {
                        //FormPanel自身带异步提交方式
                        if (myform.getForm().isValid()) {
                            myform.getForm().submit({
//                            url: '../../UserManageAction_addUserManage.action',
                                waitTitle:'请等待',
                                waitMsg:'正在提交中',
                                success:function (form, action) {
                                    var msg = action.result.msg;
//                                    if (msg == "mmcw") {
//                                        Ext.Msg.alert('提示',msg);
//                                    } else if (msg == "ssadd") {
//                                        Ext.Msg.alert('提示', '应用名称重复');
//                                    } else if (msg == "portcf") {
//                                        Ext.Msg.alert('提示', '端口重复');
//                                    } else {
                                    Ext.Msg.alert('提示', msg);
//                                    hpgrid.render();
                                    hpds.reload();
                                    win.close();
//                                    }
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

    function updateHsForm(recode){
        var update_store =new Ext.data.Store({
            proxy : new Ext.data.HttpProxy({
                url : '../../SiteAction_findBySiteStore.action',
                method : "POST"

            }),
            reader : new Ext.data.JsonReader({
                fields : ["site", "site_name"],
                totalProperty : 'total',
                root : 'rows'
            }) ,
            listeners : {
                load : function(){
                    var value = Ext.getCmp('my.update.site').getValue();
                    Ext.getCmp('my.update.site').setValue(value);
                }
            }
        });


        var update_server_store =new Ext.data.Store({
            proxy : new Ext.data.HttpProxy({
                url : '../../SiteAction_findBySiteStore.action',
                method : "POST"

            }),
            reader : new Ext.data.JsonReader({
                fields : ["site", "site_name"],
                totalProperty : 'total',
                root : 'rows'
            }) ,
            listeners : {
                load : function(){
                    var value = Ext.getCmp('my.update.server_site').getValue();
                    Ext.getCmp('my.update.server_site').setValue(value);
                }
            }
        });
        var id =   recode.get("id");
        var site_name =   recode.get("site_name");
        var site_id =   recode.get("site_id");
        var server_site_name =   recode.get("server_site_name");
        var server_site_id =   recode.get("server_site_id");
        var managerip =   recode.get("managerip");
        var managerport =   recode.get("managerport");
        var proxyip =   recode.get("proxyip");
        var proxyport =   recode.get("proxyport");
        var protocol =   recode.get("protocol");
        myform = new Ext.form.FormPanel({
            labelWidth:160,
            //renderTo : "formt",
            frame:true,
            fileUpload:true,
            defaultType:'textfield',
            buttonAlign:'right',
            labelAlign:'right',
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url:'../../BsProxyConfigAction_updateConfig.action',
//            baseParams:{create:true },
            baseParams:{id:id,oldManagerip:managerip,oldManagerPort:managerport,oldProxyip:proxyip,oldProxyport:proxyport,oldProtocol:protocol,oldSiteId:site_id,oldServerSiteId:server_site_id},
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText:'不能为空!',
                msgTarget:'side'
            },
            items:[
                new Ext.form.ComboBox({
                    hiddenName:'protocol',
                    id:'updateprotocol1',
                    fieldLabel:"代理类型",
                    emptyText:'请选择',
                    width:250,
                    value:protocol,
                    store:protocolstore,
                    valueField:"value",
                    displayField:"name",
                    typeAhead:true,
                    mode:"local",
                    forceSelection:true,
                    triggerAction:"all",
                    OnFocus:true,
                    listeners:{
                        select:function (combo, record, index) {
                            var prvdr = record.get('value');
                            if (prvdr == "http") {
                                Ext.getCmp("updatewinhttps").close();
                                updateMyForm(recode);
                                Ext.getCmp("updateprotocol2").setValue("http");
                                updateWinOpen(myform);
                            } else if(prvdr=="tcp"){
                                Ext.getCmp("updatewinhttps").close();
                                updateMyForm(recode);
                                Ext.getCmp("updateprotocol2").setValue("tcp");
                                updateWinOpen(myform);
                            }else if(prvdr=="thttp"){
                                Ext.getCmp("updatewinhttps").close();
                                updateTHttpMyForm(recode);
                                Ext.getCmp("updateprotocol2").setValue("thttp");
                                updateThttpWinOpen(myform);
                            }
                        }
                    }
                }),  new Ext.form.ComboBox({
                    mode : 'remote',// 指定数据加载方式，如果直接从客户端加载则为local，如果从服务器断加载// 则为remote.默认值为：remote
                    border : true,
                    frame : true,
                    pageSize : 15,// 当元素加载的时候，如果返回的数据为多页，则会在下拉列表框下面显示一个分页工具栏，该属性指定每页的大小
                    // 在点击分页导航按钮时，将会作为start及limit参数传递给服务端，默认值为0，只有在mode='remote'的时候才能够使用
//                title : '省份',
                    editable : false,
                    emptyText:'请选择匹配站点',
                    fieldLabel:'请选择匹配站点',
                    id:'my.update.site',
                    value:site_id,
//                hiddenName : 'hzihCa.hzihprovince',
                    triggerAction : "all",// 是否开启自动查询功能
                    store : update_store,// 定义数据源
                    displayField : "site_name",// 关联某一个逻辑列名作为显示值
                    valueField : "site",// 关联某一个逻辑列名作为实际值
                    //mode : "local",// 如果数据来自本地用local 如果来自远程用remote默认为remote
                    emptyText : "请选择",// 没有选择时候的默认值
//                    name:'site',
                    hiddenName:'site',
                    allowBlank:true,
                    blankText:"请选择匹配站点"/*,
                     listeners:{
                     select:function(){
                     var value = this.getValue();
                     cityStore.proxy   = new Ext.data.HttpProxy ({
                     url:"../../../DistrictAction_comboChild.action?parentId="+value
                     })
                     cityStore.load();
                     } ,
                     render:function(){
                     Ext.getCmp("hzih.ca.addCa.province").getEl().up('.x-form-item').setDisplayed(false)
                     }
                     }*/

                }), new Ext.form.ComboBox({
                    mode : 'remote',// 指定数据加载方式，如果直接从客户端加载则为local，如果从服务器断加载// 则为remote.默认值为：remote
                    border : true,
                    frame : true,
                    pageSize : 15,// 当元素加载的时候，如果返回的数据为多页，则会在下拉列表框下面显示一个分页工具栏，该属性指定每页的大小
                    // 在点击分页导航按钮时，将会作为start及limit参数传递给服务端，默认值为0，只有在mode='remote'的时候才能够使用
//                title : '省份',
                    editable : false,
                    emptyText:'请选择目标服务器站点',
                    fieldLabel:'请选择目标服务器站点',
                    id:'my.update.server_site',
                    value:server_site_id,
//                hiddenName : 'hzihCa.hzihprovince',
                    triggerAction : "all",// 是否开启自动查询功能
                    store : update_server_store,// 定义数据源
                    displayField : "site_name",// 关联某一个逻辑列名作为显示值
                    valueField : "site",// 关联某一个逻辑列名作为实际值
                    //mode : "local",// 如果数据来自本地用local 如果来自远程用remote默认为remote
                    emptyText : "请选择",// 没有选择时候的默认值
//                    name:'site',
                    hiddenName:'serverSite',
                    allowBlank:true,
                    blankText:"请选择目标服务器站点"/*,
                     listeners:{
                     select:function(){
                     var value = this.getValue();
                     cityStore.proxy   = new Ext.data.HttpProxy ({
                     url:"../../../DistrictAction_comboChild.action?parentId="+value
                     })
                     cityStore.load();
                     } ,
                     render:function(){
                     Ext.getCmp("hzih.ca.addCa.province").getEl().up('.x-form-item').setDisplayed(false)
                     }
                     }*/

                }),/*, {
                 id:'outFile.info',
                 fieldLabel:"请选择上传证书",
                 width:250,
                 name:'uploadFile',
                 xtype:'textfield',
                 inputType:'file',
                 allowBlank:false,
                 regexText:'请选择要上传的证书',
                 listeners:{
                 render:function () {
                 Ext.get('outFile.info').on("change", function () {
                 var file = this.getValue();
                 var fs = file.split('.');
                 if (fs[fs.length - 1].toLowerCase() != 'p12' && fs[fs.length - 1].toLowerCase() != 'pfx') {
                 Ext.MessageBox.show({
                 title:'信息',
                 width:200,
                 msg:'上传文件格式不对,请重新选择!',
                 buttons:{'ok':'确定'},
                 icon:Ext.MessageBox.ERROR,
                 closable:false,
                 fn:function (e) {
                 if (e == 'ok') {
                 Ext.getCmp('outFile.info').setValue('');
                 }
                 }
                 });
                 }
                 });
                 }
                 }

                 }, {
                 fieldLabel:'证书密码',
                 name:'ecpassword',
                 width:250,
                 inputType:'password',
                 regex:/^\S{4,20}$/,
                 allowBlank:false,
                 regexText:'请输入应用名称'
                 },*/
                new Ext.form.ComboBox({
                    hiddenName:'managerip',
                    id:'managerip1',
                    value:managerip,
                    fieldLabel:"管理IP",
                    emptyText:'请选择管理IP',
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
                    fieldLabel:'管理端口',
                    name:'managerport',
                    value:managerport,
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'请输入正确的端口',
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
                    fieldLabel:'代理IP',
                    name:'proxyip',
                    value:proxyip,
                    width:250,
                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                    regexText:'请输入正确的ip地址',
                    allowBlank:false
                }, {
                    fieldLabel:'代理端口',
                    name:'proxyport',
                    value:proxyport,
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'请输入正确的端口',
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
        update_store.load({
            params:{
                start:0,
                limit:10000
            }
        });
        update_server_store.load({
            params:{
                start:0,
                limit:10000
            }
        });
    }

    function updateHsWinOpen(form){
        var myform = form;
        win = new Ext.Window({
            id:'updatewinhttps',
            title:'',
            width:500,
//            height :440,
            items:[myform],
            bbar:[
                '->',
                {
                    text:'确定',
                    handler:function () {
                        //FormPanel自身带异步提交方式
                        if (myform.getForm().isValid()) {
                            myform.getForm().submit({
//                            url: '../../UserManageAction_addUserManage.action',
                                waitTitle:'请等待',
                                waitMsg:'正在提交中',
                                success:function (form, action) {
                                    var msg = action.result.msg;
//                                    if (msg == "mmcw") {
//                                        Ext.Msg.alert('提示',msg);
//                                    } else if (msg == "ssadd") {
//                                        Ext.Msg.alert('提示', '应用名称重复');
//                                    } else if (msg == "portcf") {
//                                        Ext.Msg.alert('提示', '端口重复');
//                                    } else {
                                    Ext.Msg.alert('提示', msg);
//                                        hpgrid.render();
                                    hpds.reload();
                                    win.close();
//                                    }
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

    function updateTHttpMyForm(recode){
        var id =   recode.get("id");
        var site_name =   recode.get("site_name");
        var site_id =   recode.get("site_id");
        var server_site_name =   recode.get("server_site_name");
        var server_site_id =   recode.get("server_site_id");
        var managerip =   recode.get("managerip");
        var managerport =   recode.get("managerport");
        var proxyip =   recode.get("proxyip");
        var proxyport =   recode.get("proxyport");
        var protocol =   recode.get("protocol");
        myform = new Ext.form.FormPanel({
            labelWidth:130,
            //renderTo : "formt",
            frame:true,
            defaultType:'textfield',
            buttonAlign:'right',
            labelAlign:'right',
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url:'../../BsProxyConfigAction_updateConfig.action',
//            baseParams:{create:true },
            baseParams:{id:id,oldManagerip:managerip,oldManagerPort:managerport,oldProxyip:proxyip,oldProxyport:proxyport,oldProtocol:protocol,oldSiteId:site_id,oldServerSiteId:server_site_id},
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText:'不能为空!',
                msgTarget:'side'
            },
            items:[
                new Ext.form.ComboBox({
                    hiddenName:'protocol',
                    id:'updateprotocol2',
                    fieldLabel:"代理类型",
                    emptyText:'请选择',
                    width:250,
                    store:protocolstore,
                    value:protocol,
                    valueField:"value",
                    displayField:"name",
                    typeAhead:true,
                    mode:"local",
                    forceSelection:true,
                    triggerAction:"all",
                    OnFocus:true,
                    listeners:{
                        select:function (combo, record, index) {
                            var prvdr = record.get('value');
                            if (prvdr == "https") {
                                Ext.getCmp("updatewinhttp").close();
                                win.close();
                                updateHsForm(recode);
                                Ext.getCmp("updateprotocol1").setValue("https");
                                updateHsWinOpen(myform);
                            } else if(prvdr=="http"){
                                Ext.getCmp("updatewinhttp").close();
                                win.close();
                                updateMyForm(recode);
                                Ext.getCmp("updateprotocol2").setValue("http");
                                updateWinOpen(myform);
                            }  else if(prvdr=="tcp"){
                                Ext.getCmp("updatewinhttp").close();
                                win.close();
                                updateMyForm(recode);
                                Ext.getCmp("updateprotocol2").setValue("tcp");
                                updateWinOpen(myform);
                            }
                        }
                    }
                }), new Ext.form.ComboBox({
                    hiddenName:'managerip',
                    id:'managerip1',
                    fieldLabel:"管理IP",
                    value:managerip,
                    emptyText:'请选择管理IP',
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
                    fieldLabel:'管理端口',
                    name:'managerport',
                    value:managerport,
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'请输入正确的端口',
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
                }/*,

                {
                    fieldLabel:'代理IP',
                    name:'proxyip',
                    value:proxyip,
                    id:'update.proxyip',
                    width:250,
                    regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                    regexText:'请输入正确的ip地址',
                    allowBlank:false
                }, {
                    fieldLabel:'代理端口',
                    name:'proxyport',
                    value:proxyport,
                    id:'update.proxyport',
                    width:250,
                    regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                    regexText:'请输入正确的端口',
                    allowBlank:false*//*,
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
                     }*//*
                }*/
            ]
        });
    }

    Model.updateModel = function updateModel() {
        var recode = hpgrid.getSelectionModel().getSelected();
//        var id =   recode.get("id");
//        var site_name =   recode.get("site_name");
//        var site_id =   recode.get("site_id");
//        var managerip =   recode.get("managerip");
//        var managerport =   recode.get("managerport");
//        var proxyip =   recode.get("proxyip");
//        var proxyport =   recode.get("proxyport");
        var protocol =   recode.get("protocol");
        if(protocol=="https"){
            updateHsForm(recode);
            Ext.getCmp("updateprotocol1").setValue("https");
            updateHsWinOpen(myform);
        } else if(protocol=="http"){
            updateMyForm(recode);
            Ext.getCmp("updateprotocol2").setValue("http");
            updateWinOpen(myform);
        } else if(protocol=="tcp"){
            updateMyForm(recode);
            Ext.getCmp("updateprotocol2").setValue("tcp");
            updateWinOpen(myform);
        } else if(protocol=="thttp"){
            updateTHttpMyForm(recode);
            Ext.getCmp("updateprotocol2").setValue("thttp");
            updateThttpWinOpen(myform);
        }
    }

    Model.del_proxy =function del_proxy() {
    var hpgrid = Ext.getCmp("hpgrid");
//    var recode = hpgrid.getSelectionModel().getSelected();
//    var managerip = recode.get("managerip");
//    var managerport = recode.get("managerport");
//    var proxyip = recode.get("proxyip");
//    var proxyport = recode.get("proxyport");
//    var protocol = recode.get("protocol");
    var id =   hpgrid.getSelectionModel().getSelections()[0].get("id");
    Ext.MessageBox.confirm('提示', '是否确定删除这代理', callBack);
    function callBack(qrid) {
        if ("yes" == qrid) {
            Ext.Ajax.request({
                url:'../../BsProxyConfigAction_delConfig.action',
                success:function (response, result) {
                    Ext.Msg.alert('提示', "删除成功");
                    hpgrid.render();
                    hpgrid.getStore().reload();
                },
                failure:function (response, result) {
                    Ext.Msg.alert('提示', "删除失败");
                },
                params:{/*managerip:managerip, managerport:managerport, proxyip:proxyip, proxyport:proxyport, protocol:protocol*/id:id}
            });
        }
    }
};
});

var Model = new Object;
function updateModel() {
    Model.updateModel();
}

var Model = new Object;
function delmodel() {
    Model.del_proxy();
}


