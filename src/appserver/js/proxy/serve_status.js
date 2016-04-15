
Ext.onReady(function(){
    var fwztform = new Ext.form.FormPanel({
        labelWidth:85,
        //renderTo : "formt",
//        height:800,
        frame : true ,
        defaultType : 'textfield' ,
        buttonAlign : 'left' ,
        labelAlign : 'right' ,
        //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
//        url: '../../AccessControlAction_addWapControl.action',
        baseParams : {create : true },
        //  labelWidth : 70 ,
        renderTo :Ext.getBody(),
        defaults:{
//                allowBlank: false,
            blankText: '不能为空!',
            msgTarget: 'side'
        },
        items : [

            {
                xtype:'fieldset',
                title:'服务状态',
                collapsible:true,
                items:[

                    {
                        fieldLabel : '服务状态' ,
                        id: 'serverstatus',
                        name : 'serverstatus',
                        value: "<font color='red'>未启动</font> [<a href='javascript:;' style='color:blue;' onclick='qidong()'>启动</a>]",
                        width:300,
                        xtype:'displayfield'


                    },/*{
                     fieldLabel : '运行时间' ,
                     name : 'runtime',
                     id:'runtime',
                     value: "<font color='orange'>服务未启动</font>",
                     width:300,
                     xtype:'displayfield'


                     },*/{
                        fieldLabel : '服务日志' ,
                        name : 'servelog',
                        value: "[<a href='javascript:;' style='color:blue;' onclick='lookmodel()'>运行日志</a>][<a href='javascript:;' style='color:blue;' onclick='lookmodel()'>访问日志</a>][<a href='javascript:;' style='color:blue;' onclick='lookmodel()'>打包下载</a>]",
                        width:300,
                        xtype:'displayfield'

                    }
                ]
            }



        ]
    });

    function checkServeStaus() {
        Ext.Ajax.request({
            url:'../../NginxServerStatusAction_checkServerStatus.action',
            success:function(response,result){
                var reText = Ext.util.JSON.decode(response.responseText);
                if("0"==reText.msg) {
                    Ext.getCmp("serverstatus").setValue("<font color='red'>未启动</font> [<a href='javascript:;' style='color:blue;' onclick='qidong()'>启动</a>]")
//                    Ext.getCmp("runtime").setValue("<font color='orange'>服务未启动</font>");
                }else{
                    Ext.getCmp("serverstatus").setValue("<font color='green'>已启动</font> [<a href='javascript:;' style='color:blue;' onclick='guanbi()'>关闭</a>][<a href='javascript:;' style='color:blue;' onclick='chongqi()'>重启</a>]");
//                    Ext.getCmp("runtime").setValue("<font color='orange'>"+reText.msg+"</font>");
                }
            }
//            params:{ids:ids}
        });
    }
    checkServeStaus();

    Model.openServe= function openServe() {
        Ext.Ajax.request({
            url:'../../NginxServerStatusAction_openServer.action',
            success:function(response,result){
                var reText = Ext.util.JSON.decode(response.responseText);
                if("1"==reText.msg) {
                    Ext.getCmp("serverstatus").setValue("<font color='green'>已启动</font> [<a href='javascript:;' style='color:blue;' onclick='guanbi()'>关闭</a>]");
                    Ext.Msg.alert('提示',"开启代理服务成功");
                }else{
                    Ext.Msg.alert('提示',"开启代理服务失败");
                }
                checkServeStaus();
            }
//            params:{ids:ids}
        });
    }

    Model.closeServe= function closeServe() {
        Ext.Ajax.request({
            url:'../../NginxServerStatusAction_closeServer.action',
            success:function(response,result){
                var reText = Ext.util.JSON.decode(response.responseText);
                if("1"==reText.msg) {
                    Ext.getCmp("serverstatus").setValue("<font color='red'>未启动</font> [<a href='javascript:;' style='color:blue;' onclick='qidong()'>启动</a>]");
                    Ext.Msg.alert('提示',"关闭代理服务成功");
                }else{
                    Ext.Msg.alert('提示',"关闭代理服务失败");
                }
                checkServeStaus();
            }
//            params:{ids:ids}
        });
    }

    Model.reloadServe= function reloadServe() {
        Ext.Ajax.request({
            url:'../../NginxServerStatusAction_reloadServer.action',
            success:function(response,result){
                var reText = Ext.util.JSON.decode(response.responseText);
                if("1"==reText.msg) {
//                    Ext.getCmp("serverstatus").setValue("<font color='red'>未启动</font> [<a href='javascript:;' style='color:blue;' onclick='qidong()'>启动</a>]");
                    checkServeStaus();
                    Ext.Msg.alert('提示',"重启代理服务成功");
                }else{
                    checkServeStaus();
                    Ext.Msg.alert('提示',"重启代理服务失败");
                }
//                checkServeStaus();
            }
        });
    }

});

var Model = new Object;
function qidong(){
    Model.openServe();
}
function guanbi(){
    Model.closeServe();
}

