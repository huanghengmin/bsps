Ext.onReady(function(){
    var data1 = [ [0,'0.0.0.0'], [1,'1.1.1.1'] ];
    var store1 = new Ext.data.SimpleStore({fields:['value','name'],data:data1});
    var data2 = [ [0,'不记录'], [1,'错误'], [2,'警告'], [3,'通知'], [4,'信息'], [5,'调试'] ];
    var store2 = new Ext.data.SimpleStore({fields:['value','name'],data:data2});
    var data3 = [ [0,'不记录'], [1,'只记录到文件'], [2,'只发送到SYSLOG'], [3,'记录到文件，同时发送到SYSLOG服务器']];
    var store3 = new Ext.data.SimpleStore({fields:['value','name'],data:data3});
    var data4 = [ [0,'否'], [1,'是']];
    var store4 = new Ext.data.SimpleStore({fields:['value','name'],data:data4});
    var jbcsform = new Ext.form.FormPanel({
        labelWidth:110,
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
        renderTo :Ext.getBody(),
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
                width:300,
                allowBlank: false
            }),
            {
                fieldLabel : '服务端口号' ,
                name : 'serveport',
                id : 'serveport1',
                value:'61694',
                width:300,
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
                width:300,
                allowBlank: false
            }),
            new Ext.form.ComboBox({
                fieldLabel:'用户服务日志',
                hiddenName:'useraccesslog',
                id:'useraccesslog1',
                store : store3,
                valueField : "value",
                displayField : "name",
                typeAhead : true,
                mode : "local",
                forceSelection : true,
                triggerAction : "all",
                OnFocus : true,
                width:300,
                allowBlank: false
            }),
            new Ext.form.ComboBox({
                fieldLabel:'是否启用访问控制',
                hiddenName:'ifusing',
                id:'ifusing1',
                store : store4,
                valueField : "value",
                displayField : "name",
                typeAhead : true,
                mode : "local",
                forceSelection : true,
                triggerAction : "all",
                OnFocus : true,
                width:300,
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
    Ext.getCmp("runloglevel1").setValue(1);
    Ext.getCmp("useraccesslog1").setValue(3);
    Ext.getCmp("ifusing1").setValue(1);

});
