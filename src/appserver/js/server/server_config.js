Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    var record = new Ext.data.Record.create([
        {name:'bindAddress', mapping:'bindAddress'},
        {name:'bindPort', mapping:'bindPort'} /*,
        {name:'keystore', mapping:'keystore'} ,
        {name:'keystorePwd',mapping:'keystorePwd'},
        {name:'keystoreTrust', mapping:'keystoreTrust'},
        {name:'keystoreTrustPwd', mapping:'keystoreTrustPwd'}*/
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../BsConfigAction_selectConfig.action"
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
        var bindAddress = store.getAt(0).get('bindAddress');
        var bindPort = store.getAt(0).get('bindPort');
//        var keystore = store.getAt(0).get('keystore');
//        var keystorePwd = store.getAt(0).get('keystorePwd');
//        var keystoreTrust = store.getAt(0).get('keystoreTrust');
//        var keystoreTrustPwd = store.getAt(0).get('keystoreTrustPwd');
        Ext.getCmp('config.bindAddress').setValue(bindAddress);
        Ext.getCmp('config.bindPort').setValue(bindPort);
//        Ext.getCmp('config.keystore').setValue(keystore);
//        Ext.getCmp('config.keystorePwd').setValue(keystorePwd);
//        Ext.getCmp('config.keystoreTrust').setValue(keystoreTrust);
//        Ext.getCmp('config.keystoreTrustPwd').setValue(keystoreTrustPwd);
    });

    var formPanel = new Ext.form.FormPanel({
        plain:true,
        width:500,
        labelAlign:'right',
        labelWidth:200,
        defaultType:'textfield',
        defaults:{
            width:250,
            allowBlank:false,
            blankText:'该项不能为空!'
        },
        items:[
            new Ext.form.TextField({
                fieldLabel:'BS代理绑定ip地址',
                name:'bindAddress',
                regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                regexText:'请输入正确的ip地址',
                id:"config.bindAddress",
                allowBlank:false,
                blankText:"BS代理绑定ip地址"
            }),
            new Ext.form.TextField({
                fieldLabel:'BS代理绑定端口',
                name:'bindPort',
                id:"config.bindPort",
                regex:/^(6553[0-6]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{3}|[1-9][0-9]{2}|[1-9][0-9]|[1-9])$/,
                regexText:'请输入正确的端口号',
                allowBlank:false,
                blankText:"BS代理绑定端口"
            })/*,
            new Ext.form.TextField({
                fieldLabel : '设备证书地址',
                name : 'keystore',
                regexText:'设备证书地址',
                id:"config.keystore",
                allowBlank : false,
                blankText : "不能为空，请正确填写"
            }),
            new Ext.form.TextField({
                fieldLabel : '设备证书密码',
                id:"config.keystorePwd",
                regexText:'设备证书密码',
                inputType:'password',
                name : 'keystorePwd',
                allowBlank : false,
                blankText : "不能为空，请正确填写"
            })*//*,
            new Ext.form.TextField({
                fieldLabel:'信任证书地址',
                name:'keystoreTrust',
                id:"config.keystoreTrust",
                allowBlank:false,
                blankText:"信任证书地址"
            }),
            new Ext.form.TextField({
                fieldLabel:'信任证书密码',
                name:'keystoreTrustPwd',
                inputType:'password',
                allowBlank:false,
                id:"config.keystoreTrustPwd",
                blankText:"信任证书密码"
            })*/
        ],
        buttons:[
            '->',
            {
                id:'insert_win.info',
                text:'保存配置',
                handler:function () {
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url:"../../BsConfigAction_saveConfig.action",
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
        width:600,
        border:false,
        items:[{
            id:'panel.info',
            xtype:'fieldset',
            title:'BS代理配置',
            width:530,
            items:[formPanel]
        }]
    });
    new Ext.Viewport({
        layout :'fit',
        renderTo:Ext.getBody(),
        autoScroll:true,
        items:[{
            frame:true,
            autoScroll:true,
            items:[panel]
        }]
    });

});


