/**
 * 网卡配置
 */
Ext.onReady(function() {

    Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var record = new Ext.data.Record.create([
        {name:'ms_pre',mapping:'ms_pre'},
        {name:'ms_post',mapping:'ms_post'},
        {name:'gap_pre',mapping:'gap_pre'},
        {name:'gap_post',mapping:'gap_post'}/*,
        {name:'ps_pre',mapping:'ps_pre'},
        {name:'ps_post',mapping:'ps_post'}*/
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../../PlatformInitAction_find.action"
    });

    var reader = new Ext.data.JsonReader({
        totalProperty:"totalCount",
        root:"root"
    },record);

    var store = new Ext.data.Store({
        proxy : proxy,
        reader : reader
    });

    store.load();

    store.on('load',function(){
        var ms_pre = store.getAt(0).get('ms_pre');
        Ext.getCmp('config.platform.ms_pre').setValue(ms_pre);
        var ms_post = store.getAt(0).get('ms_post');
        Ext.getCmp('config.platform.ms_post').setValue(ms_post);
        var gap_pre = store.getAt(0).get('gap_pre');
        Ext.getCmp('config.platform.gap_pre').setValue(gap_pre);
        var gap_post = store.getAt(0).get('gap_post');
        Ext.getCmp('config.platform.gap_post').setValue(gap_post);
   /*     var ps_pre = store.getAt(0).get('ps_pre');
        Ext.getCmp('config.platform.ps_pre').setValue(ps_pre);
        var ps_post = store.getAt(0).get('ps_post');
        Ext.getCmp('config.platform.ps_post').setValue(ps_post);*/
    });

    var formPanel = new Ext.form.FormPanel({
        plain:true,
        labelAlign:'right',
        labelWidth:200,
        defaultType:'textfield',
        defaults:{
            width:250,
            blankText:'该项不能为空!'
        },
        items:[

           /* {
                fieldLabel:'网闸前置口',
                name:'gap_pre',
                regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                regexText:'请输入正确的IP地址',
                id:"config.platform.gap_pre",
                blankText:"请输入正确的IP地址"
            },
            {
                fieldLabel:'网闸后置口',
                name:'gap_post',
                regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                regexText:'请输入正确的IP地址',
                id:"config.platform.gap_post",
                blankText:"请输入正确的IP地址"
            },*/ {
                fieldLabel:'BS代理入口',
                name:'ms_pre',
                regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                regexText:'请输入正确的IP地址',
                id:"config.platform.ms_pre",
                blankText:"请输入正确的IP地址"
            },
            {
                fieldLabel:'BS代理出口',
                name:'ms_post',
                regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                regexText:'请输入正确的IP地址',
                id:"config.platform.ms_post",
                blankText:"请输入正确的IP地址"
            }/*,
            {
                fieldLabel:'BS代理前置口',
                name:'ps_pre',
                regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                regexText:'请输入正确的IP地址',
                id:"config.platform.ps_pre",
                blankText:"请输入正确的IP地址"
            },
            {
                fieldLabel:'BS代理后置口',
                name:'ps_post',
                regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                regexText:'请输入正确的IP地址',
                id:"config.platform.ps_post",
                blankText:"请输入正确的IP地址"
            }*/

        ],
        buttons:[
            '->',
            {
                id:'insert_win.info',
                text:'保存配置',
                handler:function () {
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url:"../../../PlatformInitAction_save.action",
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

    var pic_panel = new Ext.Panel({
        id:'pic.panel.info',
        plain:true,
        height:340,
        width:setWidth(),
        items:[{
            xtype:'fieldset',
            title:'BS管理拓扑图',
            items:[{
                xtype: 'box',
                width: 900,
                height: 320,
                autoEl: {
                    tag: 'img',    //指定为img标签
                    src: '../../img/icon/tp.png'    //指定url路径
                }
            }]
        }]
    });
    var panel = new Ext.Panel({
        plain:true,
        border:false,
        autoScroll:false,
        items:[{
            xtype:'fieldset',
            title:'配置',
            items:[formPanel]
        }]
    });

    var panel_ = new Ext.Panel({
        layout:'form',
        items:[{
            layout:'form',
            frame:true,
            buttonAlign :'left',
            autoScroll:false,
            items:[/*pic_panel,*/panel]
        }]

    });

    var port = new Ext.Viewport({
        layout:'fit',
        renderTo: Ext.getBody(),
        items:[
            {
                layout:'form',
                frame:true,
                buttonAlign :'left',
                autoScroll:true,
                items:[panel_]
            }
           ]
    });

});

function setHeight(){
    var h = document.body.clientHeight-8;
    return h;
}

function setWidth(){
    return document.body.clientWidth-8;
}
