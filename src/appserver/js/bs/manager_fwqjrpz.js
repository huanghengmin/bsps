Ext.onReady(function () {
   /* var tabs = new Ext.TabPanel({
        renderTo:Ext.getBody(),
        height:setHeight()
    });*/
    var record = new Ext.data.Record.create([
        {name:'interface',mapping:'interface'} ,
        {name:'port',mapping:'port'}/* ,
        {name:'first_dns',mapping:'first_dns'} ,
        {name:'second_dns',mapping:'second_dns'} ,
        {name:'email',mapping:'email'}*/

    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../ServerParamsAction_findConfig.action"
    });

    var reader = new Ext.data.JsonReader({
        totalProperty:"totalCount",
        root:"root"
    }, record);

    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy:proxy,
        reader:reader
    });

    store.load();
    store.on('load',function(){
        var interface = store.getAt(0).get('interface');
        var port  =  store.getAt(0).get('port');
        if(interface=="all_interface"){
            Ext.getCmp("server").setValue("监听网口:"+"所有接口/端口:"+port);
        }else{
            Ext.getCmp("server").setValue("监听网口:"+interface +"/端口:"+port);
        }

//        Ext.getCmp('server_port').setValue(store.getAt(0).get('port'));
//        Ext.getCmp('first_dns').setValue(store.getAt(0).get('first_dns'));
//        Ext.getCmp('second_dns').setValue(store.getAt(0).get('second_dns'));
//        Ext.getCmp('email').setValue(store.getAt(0).get('email'));
    });


   /* var interface = new Ext.data.JsonStore({
        fields:[ "eth", "interface" ],
        url:'../../InterfaceManagerAction_readInterfaceCombo.action',
        autoLoad:true,
        root:"rows" ,
        listeners : {
            load : function(store, records, options) {// 读取完数据后设定默认值
                var value =Ext.getCmp("server_interface_id").getValue();
                Ext.getCmp("server_interface_id").setValue(value);
            }
        }
    });*/


//-----------------------------------------------------------------------------------------------服务状态---------------------------------------------------------------------------------------------------------
    var fwztform = new Ext.form.FormPanel({
        labelWidth:100,
        height:80,
        frame:false,
        border:false,
//        height:setHeight(),
        frame:true,
        defaultType:'textfield',
        buttonAlign:'left',
        labelAlign:'right',
        //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
        baseParams:{create:true },
        defaults:{
            blankText:'不能为空!',
            msgTarget:'side'
        },
        items:[
            {
                xtype:'fieldset',
                title:'B/S服务状态',
                collapsible:true,
                items:[

                    {
//                        fieldLabel:'状态',
                        id:'serverstatus',
                        name:'serverstatus',
                        value:"<font color='red'>服务未运行</font> [<a href='javascript;' style='color:blue;' onclick='qidong()'>启动服务</a>]",
                        width:300,
                        xtype:'displayfield'

                    }/*,
                    {
                        fieldLabel:'运行时间',
                        name:'runtime',
                        id:'runtime',
                        value:"<font color='orange'>服务服务未运行</font>",
                        width:300,
                        xtype:'displayfield'

                    }*/
                ]
            }, {
                xtype:'fieldset',
                title:'运行参数',
                collapsible:true,
                items:[

                    {
                        fieldLabel:'服务参数',
                        id:'server',
                        name:'server',
                        width:300,
                        xtype:'displayfield'

                    }
                   /* new Ext.form.ComboBox({
                        hiddenName:'interface_box',
                        id:'server_interface_id',
                        fieldLabel:"服务接口",
                        editable:false   ,
                        emptyText:'服务接口',
                        store:interface,
                        valueField:"eth",
                        displayField:"interface",
                        triggerAction:"all",
                        allowBlank:false
                    }),*/
                   /* {
                        fieldLabel:'端口号',
                        editable:false   ,
                        name:'port',
                        xtype:'textfield',
                        emptyText:'请输入端口号(0~65535)',
                        regexText:'请输入端口号(0~65535)',
                        id:"server_port",
                        allowBlank:false,
                        blankText:"请输入端口号(0~65535)" ,
                        regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                        allowBlank:false,
                        listeners:{
                            blur:function () {
                                var cmp = this;
                                var value = cmp.getValue();
                                if((0<=value&&value<=1024)||value==8000||value ==8080||value == 8443||value>=65535){
                                    Ext.MessageBox.show({
                                        title:'提示',
                                        width:400,
                                        msg:'0-1024端口可能被系统占用,且端口不能为8000,8080,8443出产服务已监听,且端口不能大于65535!',
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
                    }*/
                ]
            }
           /* {
                xtype:'displayfield',
//                fieldLabel:'服务',
                border: false,
                bodyStyle: 'background:none;',
                html: '</br></br></br><p  style="font-size:15;*//*font-family:楷体_GB2312;font-weight:bolder;*//*">服务状态</p><br/> '
            },*/
          /*,{
             fieldLabel : '服务日志' ,
             name : 'wapip',
             value: "[<a href='javascript:;' style='color:blue;' onclick='downloadAccessLog()'>运行日志</a>]<!--[<a href='javascript:;' style='color:blue;' onclick='lookmodel()'>打包下载</a>]-->",
             width:163,
             xtype:'displayfield'

             }*/
        ]
    });



    var port = new Ext.Viewport({
        layout:'fit',
        renderTo:Ext.getBody(),
        items:[fwztform]
    });
   /* var internal_record = new Ext.data.Record.create([
        {name:'fileName', mapping:'fileName'}
    ]);
    var internal_proxy = new Ext.data.HttpProxy({
        url:"../../DownLoadNginxLogAction_readLocalLogName.action"
    });
    var internal_reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows"
    }, internal_record);
    var internal_store = new Ext.data.Store({
        proxy:internal_proxy,
        reader:internal_reader
    });
    internal_store.load();

    var internal_logBoxM = new Ext.grid.CheckboxSelectionModel();   //复选框
//    var internal_logBoxM = new Ext.grid.RadioboxSelectionModel();
    var internal_logRowNumber = new Ext.grid.RowNumberer();         //自动 编号
    var internal_logColM = new Ext.grid.ColumnModel({
        columns:[
            internal_logBoxM,
            internal_logRowNumber,
            {header:"下载本地日志文件名", dataIndex:"fileName", align:'center', sortable:true, menuDisabled:true, renderer:internal_logDownloadShowUrl}
        ],
        defaults:{sortable:false}//不允许客户端点击列头排序，可以打开s
    });
    var internal_logGrid = new Ext.grid.GridPanel({
        id:'grid.info',
        store:internal_store,
        cm:internal_logColM,
        sm:internal_logBoxM,
        height:setHeight() - 60,
//        height:setHeight,
        columnLines:true,
//        frame:true,
        autoScroll:true,
        loadMask:true,
        border:false,
        collapsible:false,
        stripeRows:true,
        autoExpandColumn:'Position',
        enableHdMenu:true,
        enableColumnHide:true,
        bodyStyle:'width:100%',
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        tbar:[
            new Ext.Button({
                id:'clear.log.info',
                text:'清空日志',
                iconCls:'removeall',
                handler:function () {
                    clear_logs(internal_logGrid, internal_store);
                }
            })
        ],
        viewConfig:{
            forceFit:true
//            enableRowBody:true,
//            getRowClass:function(record,rowIndex,p,store){
//                return 'x-grid3-row-collapsed';
//            }
        }
    });
    internal_logGrid.loadMask.msg = '正在加载数据，请稍后...';*/
    /*var external_record = new Ext.data.Record.create([
     {name:'externalLog',mapping:'externalLog'}
     ]);
     var external_proxy = new Ext.data.HttpProxy({
     url:"../../DownLoadNginxLogAction_readRemoteLogName.action"
     });
     var external_reader = new Ext.data.JsonReader({
     totalProperty:"total",
     root:"rows"
     },external_record);
     var external_store = new Ext.data.Store({
     proxy : external_proxy,
     reader : external_reader
     });
     external_store.load();

     var external_logBoxM = new Ext.grid.CheckboxSelectionModel();   //复选框
     var external_logRowNumber = new Ext.grid.RowNumberer();         //自动 编号
     var external_logColM = new Ext.grid.ColumnModel([
     external_logBoxM,
     external_logRowNumber,
     {header:"下载远程日志文件名",dataIndex:"fileName",align:'center',renderer : external_logDownloadShowUrl}
     ]);

     var external_logGrid = new Ext.grid.GridPanel({
     plain:true,
     animCollapse:true,
     height:300,
     loadMask:{msg:'正在加载数据，请稍后...'},
     border:false,
     collapsible:false,
     cm:external_logColM,
     sm:external_logBoxM,
     store:external_store,
     stripeRows:true,
     autoExpandColumn:2,
     disableSelection:true,
     bodyStyle:'width:100%',
     enableDragDrop: true,
     selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
     viewConfig:{
     forceFit:true,
     enableRowBody:true,
     getRowClass:function(record,rowIndex,p,store){
     return 'x-grid3-row-collapsed';
     }
     }
     });*/


    function setHeight() {
        var h = document.body.clientHeight - 8;
        return h;
    }

    function internal_logDownloadShowUrl(value) {
        var type = 'internal_log';
        return "<a href='javascript:;'style='color: green;' onclick='download_log(\"" + value + "\",\"" + type + "\");'>" + value + "</a>";
    }

    function external_logDownloadShowUrl(value) {
        var type = 'external_log';
        return "<a href='javascript:;' style='color: green;' onclick='download_log(\"" + value + "\",\"" + type + "\");'>" + value + "</a>";
    }




    function checkServeStaus() {
        Ext.Ajax.request({
            url:'../../SquidServerStatusAction_checkServerStatus.action',
            success:function (response, result) {
                var reText = Ext.util.JSON.decode(response.responseText);
                if ("0" == reText.msg) {
                    Ext.getCmp("serverstatus").setValue("<font color='red'>服务未运行</font> [<a href='javascript:;' style='color:blue;' onclick='qidong()'>启动服务</a>]")
//                    Ext.getCmp("runtime").setValue("<font color='orange'>服务未启动</font>");
                } else {
                    Ext.getCmp("serverstatus").setValue("<font color='green'>已启动</font> [<a href='javascript:;' style='color:blue;' onclick='guanbi()'>关闭</a>][<a href='javascript:;' style='color:blue;' onclick='chongqi()'>重启</a>]");
//                    Ext.getCmp("runtime").setValue("<font color='orange'>" + reText.msg + "</font>");
                }
            }
        });
    }

    checkServeStaus();

    Model.openServe = function openServe() {
        var myMask = new Ext.LoadMask(Ext.getBody(),{
            msg : '正在开启,请稍后...',
            removeMask : true
        });
        myMask.show();
        Ext.Ajax.request({
            url:'../../SquidServerStatusAction_openServer.action',
            success:function (response, result) {
                myMask.hide();
                var reText = Ext.util.JSON.decode(response.responseText);
                if ("1" == reText.msg) {
                    Ext.getCmp("serverstatus").setValue("<font color='green'>已启动</font> [<a href='javascript:;' style='color:blue;' onclick='guanbi()'>关闭</a>]");
                    Ext.Msg.alert('提示', "开启代理服务成功");
                } else {
                    Ext.Msg.alert('提示', "开启代理服务失败");
                }
                checkServeStaus();
            } ,
            failure:function (form, action) {
                myMask.hide();
                Ext.Msg.alert('提示', "开启代理服务失败");
            }
        });
    }

    Model.closeServe = function closeServe() {
        var myMask = new Ext.LoadMask(Ext.getBody(),{
            msg : '正在关闭,请稍后...',
            removeMask : true
        });
        myMask.show();
        Ext.Ajax.request({
            timeout:1000*60*20,
            url:'../../SquidServerStatusAction_closeServer.action',
            success:function (response, result) {
                myMask.hide();
                var reText = Ext.util.JSON.decode(response.responseText);
                if ("0" == reText.msg) {
                    Ext.getCmp("serverstatus").setValue("<font color='red'>服务未运行</font> [<a href='javascript:;' style='color:blue;' onclick='qidong()'>启动服务</a>]");
                    Ext.Msg.alert('提示', "关闭代理服务成功");
                } else {
                    Ext.Msg.alert('提示', "关闭代理服务失败");
                }
                checkServeStaus();
            },
            failure:function (form, action) {
                myMask.hide();
                Ext.Msg.alert('提示', "关闭代理服务失败");
            }
        });
    }



  /*  tabs.add({
        title:'服务状态',
        border:false,
        frame:false,
        items:[
            fwztform*//*,
            internal_logGrid*//*

        ]
    });*/

    Model.reloadServe = function reloadServe() {
        var myMask = new Ext.LoadMask(Ext.getBody(),{
            msg : '正在重启,请稍后...',
            removeMask : true
        });
        myMask.show();
        Ext.Ajax.request({
            timeout:1000*60*20,
            url:'../../SquidServerStatusAction_reloadServer.action',
            success:function (response, result) {
                myMask.hide();
                var reText = Ext.util.JSON.decode(response.responseText);
                if ("1" == reText.msg) {
                    checkServeStaus();
                    Ext.Msg.alert('提示', "重启代理服务成功");
                } else {
                    checkServeStaus();
                    Ext.Msg.alert('提示', "重启代理服务失败");
                }
            },
            failure:function (form, action) {
                myMask.hide();
                Ext.Msg.alert('提示', "关闭代理服务失败");
            }
        });
    }

//-----------------------------------------------------------------------------------------------基本参数---------------------------------------------------------------------------------------------------------

    /* var data1 = [ [0,'0.0.0.0'], [1,'1.1.1.1'] ];
     var store1 = new Ext.data.SimpleStore({fields:['value','name'],data:data1});
     var data2 = [ [0,'不记录日志'], [1,'运行日志'], [2,'调试模式'] ];
     var store2 = new Ext.data.SimpleStore({fields:['value','name'],data:data2});
     var jbcsform = new Ext.form.FormPanel({
     labelWidth: 85,
     //renderTo : "formt",
     //        height:500,
     frame : true ,
     defaultType : 'textfield' ,
     buttonAlign : 'left' ,
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
     new Ext.form.ComboBox({
     fieldLabel:'监听地址',
     hiddenName:'listenaddress',
     id:'listenaddress1',
     store : store1,
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
     fieldLabel : '服务端口号' ,
     name : 'serveport',
     id : 'serveport',
     value:'61694',
     width:163,
     xtype:'displayfield'
     }, new Ext.form.ComboBox({
     fieldLabel:'运行日志等级',
     hiddenName:'runloglevel',
     id:'runloglevel1',
     store : store2,
     valueField : "value",
     displayField : "name",
     typeAhead : true,
     mode : "local",
     forceSelection : true,
     triggerAction : "all",
     OnFocus : true,
     allowBlank: false
     })
     ],
     buttons:[
     {
     text:'保存',
     handler:function() {

     }
     },{
     text:'返回',
     handler:function() {

     }
     }
     ]
     });
     Ext.getCmp("listenaddress1").setValue(0);
     Ext.getCmp("runloglevel1").setValue(0);

     tabs.add({
     title : '基本参数',
     items:[
     //            {
     //                html : "<img src='../../img/bsexample/服务器配置-基本参数.jpg' />"
     //            },
     jbcsform
     ]
     });
     */
//-----------------------------------------------------------------------------------------------证书配置---------------------------------------------------------------------------------------------------------

    /* var cccsm = new Ext.grid.CheckboxSelectionModel();
     var cccm = new Ext.grid.ColumnModel([
     cccsm,
     {
     header : 'ID',
     dataIndex : 'id',
     width:160,
     hidden:true
     }, {
     header : "CA证书名称",
     dataIndex : 'caname',
     width:320
     }, {
     header : "类型",
     dataIndex : 'type',
     width:160
     }, {
     header : "颁发者",
     dataIndex : 'issuer',
     width:160
     },{
     header : "起始日期",
     dataIndex : 'startdate',
     width:160
     },{
     header : "截止日期",
     dataIndex : 'closingdate',
     width:160
     },{
     header : "状态",
     dataIndex : 'status',
     width:160
     }
     ]);
     cccm.defaultSortable = true;

     var ccds = new Ext.data.Store({
     proxy : new Ext.data.HttpProxy({
     //            url : '../../ResourceAction_findAllResources.action'
     url : '../../SquidServerStatusAction_findCaConfig.action'

     }),//调用的动作
     reader : new Ext.data.JsonReader({
     totalProperty : 'calist',
     root : 'carow'
     }, [ {
     name : 'id',
     mapping : 'id',
     type : 'int'
     }, {
     name : 'caname',
     mapping : 'caname',
     type : 'string'
     }, {
     name : 'type',
     mapping : 'type',
     type : 'string'
     }, {
     name : 'issuer',
     mapping : 'issuer',
     type : 'string'
     }, {
     name : 'startdate',
     mapping : 'startdate',
     type : 'string'
     }, {
     name : 'closingdate',
     mapping : 'closingdate',
     type : 'string'
     }, {
     name : 'status',
     mapping : 'status',
     type : 'string'
     }//列的映射
     ])
     });
     ccds.load();

     var ccgrid = new Ext.grid.GridPanel({
     // var grid = new Ext.grid.EditorGridPanel( {
     */
    /* collapsible : true,// 是否可以展开
     animCollapse : false,// 展开时是否有动画效果*/
    /*
     id : 'ccgrid',
     height:setHeight(),
     title : '',
     store : ccds,
     cm : cccm,
     sm:cccsm,
     selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
     //        renderTo :Ext.getBody(), */
    /*'noteDiv',*/
    /*
     */
    /*
     * // 添加内陷的按钮 buttons : [ { text : '保存' }, { text : '取消' }],
     * buttonAlign : 'center',
     * // 按钮对齐
     */
    /*
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
     text : '保存',
     iconCls : 'searchbutton',
     handler : function() {

     }
     },{
     text : '返回',
     iconCls : 'searchbutton',
     handler : function() {

     }
     }
     ],
     //        width : 1472,
     //        height :600,
     frame : true,
     loadMask : true,// 载入遮罩动画
     autoShow : true
     });

     var datacc = [ [0,'www.baidu.com'], [1,'www.hizh.com'], [2,'www.abcd.com'] ];
     var storecc = new Ext.data.SimpleStore({fields:['value','name'],data:datacc});
     tabs.add({
     title : '证书配置',
     //        html : "<img src='../../img/bsexample/服务器配置-证书配置.jpg' />"
     items:[
     new Ext.form.FormPanel({
     labelWidth:80,
     //renderTo : "formt",
     //        height:500,
     frame : true ,
     defaultType : 'textfield' ,
     buttonAlign : 'center' ,
     labelAlign : 'right' ,
     //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
     //                url: '../../ResourceAction_addIpResource.action',
     baseParams : {create : true },
     //  labelWidth : 70 ,
     defaults:{
     //                allowBlank: false,
     blankText: '不能为空!',
     msgTarget: 'side'
     },
     items : [
     new Ext.form.ComboBox({
     fieldLabel:'选择站点证书',
     width:180,
     hiddenName:'xzzdzs',
     id:'xzzdzs1',
     store : storecc,
     valueField : "value",
     displayField : "name",
     typeAhead : true,
     mode : "local",
     forceSelection : true,
     triggerAction : "all",
     OnFocus : true,
     allowBlank: false
     })
     ]
     }),
     ccgrid
     ]
     });
     Ext.getCmp("xzzdzs1").setValue(0);*/

//-----------------------------------------------------------------------------------------------访问控制---------------------------------------------------------------------------------------------------------
/*

    var record = new Ext.data.Record.create([
        {name:'status', mapping:'status'},
        {name:'control_url', mapping:'control_url'}
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../ControlAccessAction_findConfig.action"
    });

    var reader = new Ext.data.JsonReader({
        totalProperty:"totalCount",
        root:"root"
    }, record);

    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy:proxy,
        reader:reader
    });

    store.load();
    store.on('load', function () {
        var status = store.getAt(0).get('status');
        var control_url = store.getAt(0).get('control_url');
        Ext.getCmp('status').setValue(status);
        Ext.getCmp('control_url').setValue(control_url);
    });

    var tb = new Ext.Toolbar({
        autoWidth:true,
        autoHeight:true,
        items:[
            {
                id:'addBlackAddress.info',
                xtype:'button',
                text:'鉴权校验地址配置'
            }
        ]
    });


    tabs.add({
        title:'访问控制',
        items:[
            new Ext.form.FormPanel({
                frame:true,
                id:"author_form",
                defaultType:'textfield',
                buttonAlign:'left',
                labelAlign:'right',
                baseParams:{create:true },
                labelWidth:100,
                defaults:{
                    blankText:'不能为空!',
                    msgTarget:'side'
                },
                tbar:tb,
                items:[
                    new Ext.form.Checkbox({
                        fieldLabel:'启用访问控制',
                        id:"status",
                        regexText:'启用访问控制',
                        name:'status',
                        blankText:"启用访问控制"
                    }),
                    */
/*   new Ext.form.RadioGroup({
                     fieldLabel: '访问控制',
                     width: 100,
                     id:"pradio",
                     items: [{
                     name: 'status',
                     inputValue: '0',
                     boxLabel: '禁用',
                     checked: true
                     }, {
                     name: 'status',
                     inputValue: '1',
                     boxLabel: '启用'
                     }]*//*
*/
/* ,
                     listeners:{
                     change:function(rdgroup, checked) {
                     //获取单选组的值
                     alert(checked.getRawValue());
                     }
                     }*//*

                    */
/*}),*//*
 {
                        fieldLabel:'鉴权服务地址',
                        name:'control_url',
                        id:'control_url',
                        value:'http://192.168.1.8:8080/ra/AccessControls_author.action',
                        width:500
                    }
                ],
                bbar:[
                    "->",
                    {
                        text:'保存',
                        handler:function () {
                            var control_url = Ext.getCmp('control_url').getValue();
//                            var pradio = Ext.getCmp('pradio');
//                            var status = "";
//                                pradio.eachItem(function(item){
//                                  if(item.checked == true){
//                                   status += item.inputValue;
//                                 }
//                            });
                            var status = Ext.getCmp('status').getValue();
//                           alert(status);
                            Ext.Ajax.request({
                                url:'../../ControlAccessAction_saveConfig.action',
                                timeout:20 * 60 * 1000,
                                params:{status:status, control_url:control_url},
                                method:'POST',
                                success:function (form, action) {
                                    Ext.Msg.alert("提示", "保存成功!");
                                },
                                failure:function (result) {
                                    Ext.Msg.alert("提示", "保存失败!");
                                }
                            });
                        }
                    }, {
                        text:'返回',
                        handler:function () {

                        }
                    }
                ]
            })
        ]
    });
*/

//-----------------------------------------------------------------------------------------------路由配置---------------------------------------------------------------------------------------------------------
//    tabs.add({
//        title : '路由配置',
////        html :  "<img src='../../img/bsexample/服务器配置-路由配置1.jpg' /></br><img src='../../img/bsexample/服务器配置-路由配置2.jpg' /></br><img src='../../img/bsexample/服务器配置-路由配置3.jpg' /></br><img src='../../img/bsexample/服务器配置-路由配置4.jpg' />"
//        items:[
//
//        ]
//    });

//-----------------------------------------------------------------------------------------------高级配置---------------------------------------------------------------------------------------------------------

    /*  var data3 = [ [0,'UDP'], [1,'TCP'] ];
     var store3 = new Ext.data.SimpleStore({fields:['value','name'],data:data3});
     var data4 = [ [0,'不验证黑名单'], [1,'离线黑名单验证'] ];
     var store4 = new Ext.data.SimpleStore({fields:['value','name'],data:data4});
     var gjpzform = new Ext.form.FormPanel({
     labelWidth:100,
     //renderTo : "formt",
     //        height:500,
     frame : true ,
     defaultType : 'textfield' ,
     buttonAlign : 'left' ,
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
     new Ext.form.ComboBox({
     fieldLabel:'通讯协议',
     hiddenName:'txxy',
     id:'txxy1',
     store : store3,
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
     fieldLabel : '虚拟网网段' ,
     name : 'xnwwd',
     id : 'xnwwd',
     value:'172.25.2.2',
     width:163,
     xtype:'displayfield'
     },{
     fieldLabel : '虚拟网子网掩码' ,
     name : 'xnwzwym',
     id : 'xnwzwym',
     value:'255.255.255.0',
     width:163,
     xtype:'displayfield'
     }, new Ext.form.ComboBox({
     fieldLabel:'黑名单验证',
     hiddenName:'hmdyz',
     id:'hmdyz1',
     store : store4,
     valueField : "value",
     displayField : "name",
     typeAhead : true,
     mode : "local",
     forceSelection : true,
     triggerAction : "all",
     OnFocus : true,
     allowBlank: false
     })
     ],
     buttons:[
     {
     text:'保存',
     handler:function() {

     }
     },{
     text:'返回',
     handler:function() {

     }
     }
     ]
     });

     Ext.getCmp("txxy1").setValue(0);
     Ext.getCmp("hmdyz1").setValue(0);

     tabs.add({
     title : '高级配置',
     //        html : "<img src='../../img/bsexample/服务器配置-高级配置.jpg' />"
     items:[
     gjpzform
     ]

     }); */
//    tabs.activate(0);
});

var Model = new Object;
function qidong() {
    Model.openServe();
}
function guanbi() {
    Model.closeServe();
}

function chongqi() {
    Model.reloadServe();
//    Ext.Ajax.request({
//        url:'../../SquidServerStatusAction_reloadServe.action',
//        success:function (response, result) {
//            Ext.Msg.alert('提示', "重启服务成功");
//        },
//        failure:function (response, result) {
//            Ext.Msg.alert('提示', "重启服务失败");
//        }
//    });
}

function setHeight() {
    var h = document.body.clientHeight - 8;
    return h;
}

function downloadAccessLog() {
    if (!Ext.fly('test')) {
        var frm = document.createElement('form');
        frm.id = 'test';
        frm.name = id;
        frm.style.display = 'none';
        document.body.appendChild(frm);
    }
    ;
    Ext.Ajax.request({
        url:'../../NginxAccessLogAction_download.action',
        timeout:20 * 60 * 1000,
        form:Ext.fly('test'),
        method:'POST',
        isUpload:true
    });
}

function download_log(logName, type) {
//    alert(logName + type);
    if (!Ext.fly('test')) {
        var frm = document.createElement('form');
        frm.id = 'test';
        frm.name = id;
        frm.style.display = 'none';
        document.body.appendChild(frm);
    }
    Ext.Ajax.request({
        url:'../../DownLoadNginxLogAction_download.action',
        params:{type:type, logName:logName },
        form:Ext.fly('test'),
        method:'POST',
        isUpload:true
    });
}

function clear_logs(grid, store) {
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    if (!recode) {
        Ext.MessageBox.alert("信息", "请选择清空日志条目！");
    } else {
        var fileName = recode.get("fileName");
        Ext.MessageBox.show({
            title:'信息',
            msg:"<font color='green'>确定要清空日志?</font>",
            animEl:'truncate.tb.info',
            buttons:{'ok':'确定', 'no':'取消'},
            icon:Ext.MessageBox.WARNING,
            closable:false,
            fn:function (e) {
                if (e == 'ok') {
                    var formPanel = new Ext.form.FormPanel({
                        frame:true,
                        labelAlign:'right',
                        autoScroll:true,
                        labelWidth:100,
                        defaults:{
                            width:200,
                            allowBlank:false,
                            blankText:'该项不能为空！'
                        },
                        items:[
                            {
                                id:'password.info',
                                fieldLabel:"请输入您的密码",
                                xtype:'textfield',
                                name:'password',
                                inputType:'password',
                                emptyText:'请输入您的密码'
                            }
                        ]
                    });
                    var win = new Ext.Window({
                        title:"提示信息",
                        width:400,
                        height:110,
                        layout:'fit',
                        modal:true,
                        items:[formPanel],
                        bbar:[
                            new Ext.Toolbar.Fill(),
                            new Ext.Button({
                                id:'ok.info',
                                text:'确定',
                                allowDepress:false,
                                handler:function () {
                                    if (formPanel.form.isValid()) {
                                        var myMask = new Ext.LoadMask(Ext.getBody(), {
                                            msg:'正在处理,请稍后...',
                                            removeMask:true
                                        });
                                        myMask.show();
                                        var password = Ext.getCmp('password.info').getValue();
                                        Ext.Ajax.request({
                                            url:'../../DownLoadNginxLogAction_clear.action',
                                            params:{password:password, fileName:fileName},
                                            method:'POST',
                                            success:function (r, o) {
                                                myMask.hide();
                                                var respText = Ext.util.JSON.decode(r.responseText);
                                                var msg = respText.msg;
                                                Ext.MessageBox.show({
                                                    title:'信息',
                                                    width:250,
                                                    msg:msg,
                                                    animEl:'ok.info',
                                                    buttons:{'ok':'确定'},
                                                    icon:Ext.MessageBox.INFO,
                                                    closable:false,
                                                    fn:function (e) {
                                                        if (e == 'ok') {
                                                            grid.render();
                                                            store.reload();
                                                            win.close();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            }),
                            new Ext.Button({
                                allowDepress:false,
                                text:'关闭',
                                handler:function () {
                                    win.close();
                                }
                            })
                        ]
                    }).show();
                }
            }
        });
    }
}

/*
 function setWidth(){
 var h = document.body.clientWidth-300;
 return h;
 }*/
