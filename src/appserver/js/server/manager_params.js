Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';


    var record = new Ext.data.Record.create([
        {name:'interface',mapping:'interface'} ,
        {name:'port',mapping:'port'} ,
        {name:'first_dns',mapping:'first_dns'} ,
        {name:'second_dns',mapping:'second_dns'} ,
        {name:'email',mapping:'email'}

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
        Ext.getCmp("server_interface_id").setValue(store.getAt(0).get('interface'));
        Ext.getCmp('server_port').setValue(store.getAt(0).get('port'));
        Ext.getCmp('first_dns').setValue(store.getAt(0).get('first_dns'));
        Ext.getCmp('second_dns').setValue(store.getAt(0).get('second_dns'));
        Ext.getCmp('email').setValue(store.getAt(0).get('email'));
    });


    var interface = new Ext.data.JsonStore({
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
    });

    var formPanel = new Ext.form.FormPanel({
        plain:true,
        labelAlign:'right',
        labelWidth:120,
        defaultType:'textfield',
        defaults:{
//            width:250,
            allowBlank:false,
            blankText:'该项不能为空!'
        },

        items:[
            {
                xtype:'fieldset',
                title:'服务配置',
                collapsible:true,
                defaultWidth:250,
                items:[
                    new Ext.form.ComboBox({
                        hiddenName:'interface_box',
                        id:'server_interface_id',
                        fieldLabel:"服务接口",
                        emptyText:'服务接口',
                        store:interface,
                        valueField:"eth",
                        displayField:"interface",
                        triggerAction:"all",
                        allowBlank:false
                    })  ,
                    {
                        fieldLabel:'端口号',
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
                    }
                ]
            },
            {
                xtype:'fieldset',
                title:'DNS服务器',
                collapsible:true,
                items:[

                    {
                        xtype: 'textfield',
                        name: 'first_dns',
                        id:'first_dns',
                        fieldLabel: '首选DNS',
                        emptyText:'首选DNS',
                        regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                        regexText:'请输入正确的DNS',
                        blankText:'首选DNS'
                    } ,
                    {
                        xtype: 'textfield',
                        id:'second_dns',
                        name: 'second_dns',
                        fieldLabel: '备用DNS'  ,
                        emptyText:'备用DNS',
                        regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                        regexText:'请输入正确的DNS',
                        blankText:'备用DNS'

                    }
                ]
            } ,
            {
                xtype:'fieldset',
                title:'管理员邮箱',
                collapsible:true,
                items:[
                    {
                        xtype: 'textfield',
                        id:'email',
                        name: 'email',
                        regex:/^[0-9a-zA-Z_\-\.]+@[0-9a-zA-Z_\-]+(\.[0-9a-zA-Z_\-]+)*$/,
                        regexText:'请输入正确的邮箱',
                        fieldLabel: '管理员邮箱' ,
                        emptyText:'管理员邮箱',
                        blankText:'管理员邮箱'
                    }
                ]
            }
        ],
        buttons:['->',
            {
                id:'insert_win.info',
                text:'保存配置',
                handler:function () {
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url:"../../ServerParamsAction_updateConfig.action",
                            method:'POST',
                            waitTitle:'系统提示',
                            waitMsg:'正在连接...',
                            success:function () {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'保存成功,点击返回页面!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false
                                });
                            },
                            failure:function () {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'保存失败，请与管理员联系!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.ERROR,
                                    closable:false
                                });
                            }
                        });
                    } else {
                        Ext.MessageBox.show({
                            title:'信息',
                            width:200,
                            msg:'请填写完成再提交!',
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.ERROR,
                            closable:false
                        });
                    }
                }
            }
        ]
    });


    var panel = new Ext.Panel({
        plain:true,
//        width:800,
        autoWidth:true,
        autoHeight:true,
        border:false,
        items:[
            /* {
             id:'panel.info',
             xtype:'fieldset',
             title:'VPN设置',
             width:730,
             items:[*/formPanel/*]
             }*/
        ]
    });
    new Ext.Viewport({
        layout:'fit',
        renderTo:Ext.getBody(),
        autoScroll:true,
        items:[
            {
                frame:true,
                autoScroll:true,
                items:[panel]
            }
        ]
    });


});
