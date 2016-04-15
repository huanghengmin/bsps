Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var start = 0;
    var pageSize = 15;
    var record = new Ext.data.Record.create([
        {name:'site', mapping:'site'},
        {name:'site_name', mapping:'site_name'}
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../SiteAction_findByPages.action"
    });

    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows"
    }, record);

    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy:proxy,
        reader:reader
    });

    store.load({
        params:{
            start:start, limit:pageSize
        }
    });

    var boxM = new Ext.grid.CheckboxSelectionModel({singleSelect:false});   //复选框单选
    var rowNumber = new Ext.grid.RowNumberer();         //自动编号
    var colM = new Ext.grid.ColumnModel([
        boxM,
        rowNumber,
        {header:"站点标识", dataIndex:"site_name", sortable:true, menuDisabled:true,sort:true},
        {header:'操作标记', dataIndex:'flag', sortable:true, menuDisabled:true, renderer:show_flag, width:300}
    ]);
    var page_toolbar = new Ext.PagingToolbar({
        pageSize:pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });
    function setHeight() {
        var h = document.body.clientHeight - 8;
        return h;
    };

    var tbar = new Ext.Toolbar({
        autoWidth :true,
        autoHeight:true,
        items:[
            {
                id:'approval.info',
                xtype:'button',
                text:'添加站点证书',
                iconCls:'add',
                handler:function () {
                    add_site(grid_panel, store);
                }
            },   {
                id:'refused.info',
                xtype:'button',
                text:'删除站点证书',
                iconCls:'delete',
                handler:function () {
                    del_site(grid_panel, store);
                }
            }
        ]
    });



    var grid_panel = new Ext.grid.GridPanel({
        id:'grid.info',
        plain:true,
        height:setHeight(),
        viewConfig:{
            forceFit:true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
        },
        bodyStyle:'width:100%',
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:true,
        cm:colM,
        sm:boxM,
        store:store,
        tbar:tbar,
        bbar:page_toolbar
    });

    var port = new Ext.Viewport({
        layout:'fit',
        renderTo:Ext.getBody(),
        items:[grid_panel]
    });
});

function show_flag(value, p, r){
    return String.format(
        '<a id="view_cert.info" href="javascript:void(0);" onclick="view_cert();return false;"style="color: green;">信任证书</a>&nbsp;&nbsp;&nbsp;'+
            '<a id="view_user.info" href="javascript:void(0);" onclick="view_user();return false;"style="color: green;">服务证书</a>&nbsp;&nbsp;&nbsp;'
    );
}

function view_user(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var site_name  = recode.get("site_name");

    var record = new Ext.data.Record.create([
        {name:'name', mapping:'name'},
        {name:'content', mapping:'content'}
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../SiteAction_findServerCrt.action?site_name="+site_name
    });

    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows"
    }, record);

    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy:proxy,
        reader:reader
    });

    store.load();

    var boxM = new Ext.grid.CheckboxSelectionModel({singleSelect:true});   //复选框单选

    var rowNumber = new Ext.grid.RowNumberer();         //自动编号

    var colM = new Ext.grid.ColumnModel([
        boxM,
        rowNumber,
        {header:"证书项", dataIndex:"name", sortable:true, menuDisabled:true} ,
        {header:"信息",dataIndex:'content', sortable:true, menuDisabled:true}
    ]);

    var page_toolbar = new Ext.PagingToolbar({
        store:store,
        displayInfo:true,
        displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });
    var list_grid_panel = new Ext.grid.GridPanel({
        id:'grid.downloadList.info',
        plain:true,
        height:500,
        viewConfig:{
            forceFit:true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
        },
        bodyStyle:'width:100%',
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:true,
        cm:colM,
        sm:boxM,
        store:store,
        //tbar : tb,
        bbar:page_toolbar
    });
    var win = new Ext.Window({
        title:"服务器证书信息",
        width:800,
        layout:'fit',
        height:300,
        modal:true,
        items:list_grid_panel
    }).show();

}

function view_cert(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var site_name  = recode.get("site_name");

    var record = new Ext.data.Record.create([
        {name:'name', mapping:'name'},
        {name:'content', mapping:'content'}
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../SiteAction_findTrustCrt.action?site_name="+site_name
    });

    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows"
    }, record);

    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy:proxy,
        reader:reader
    });
    store.load();


    var boxM = new Ext.grid.CheckboxSelectionModel({singleSelect:true});   //复选框单选

    var rowNumber = new Ext.grid.RowNumberer();         //自动编号

    var colM = new Ext.grid.ColumnModel([
        boxM,
        rowNumber,
        {header:"证书项", dataIndex:"name", sortable:true, menuDisabled:true} ,
        {header:"信息",dataIndex:'content', sortable:true, menuDisabled:true}
    ]);

    var page_toolbar = new Ext.PagingToolbar({
        store:store,
        displayInfo:true,
        displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });
    var list_grid_panel = new Ext.grid.GridPanel({
        id:'grid.downloadList.info',
        plain:true,
        height:500,
        viewConfig:{
            forceFit:true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
        },
        bodyStyle:'width:100%',
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:true,
        cm:colM,
        sm:boxM,
        store:store,
        //tbar : tb,
        bbar:page_toolbar
    });
    var win = new Ext.Window({
        title:"信任证书信息",
        width:800,
        layout:'fit',
        height:300,
        modal:true,
        items:list_grid_panel
    }).show();
};

function add_site(grid_panel, store){
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        autoScroll:true,
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        autoWidth:true,
        fileUpload:true,
        layout:'form',
        border:false,
        defaults : {
            width : 250,
//            allowBlank : false,
            blankText : '该项不能为空！'
        },
        items:[
            new Ext.form.TextField({
                fieldLabel : '站点标识',
                name : 'site.site_name',
                regex:/^(?!_)(?!.*?_$)[a-zA-Z0-9_.]+$/,
                regexText:'只能输入数字,字母,下划线,.不能以下划线开头和结尾!',
                id:  'site.add.site.site_name',
                allowBlank : false,
                emptyText:"请输入要添加站点标识",
                blankText : "不能为空，站点标识",
                listeners:{
                    blur:function(){
                        var site_name = Ext.getCmp("site.add.site.site_name").getValue();
                            Ext.Ajax.request({
                                url : '../../SiteAction_checkSite.action',
                                timeout: 20*60*1000,
                                method : 'post',
                                params : {
                                    site_name : site_name
                                },
                                success : function(r,o) {
                                    var respText = Ext.util.JSON.decode(r.responseText);
                                    var msg = respText.msg;
                                    if (msg == 'false') {
                                        Ext.MessageBox.show({
                                            title:'信息',
                                            width:250,
                                            msg:'目标地址已存在,请更换！',
                                            buttons:Ext.MessageBox.OK,
                                            buttons:{'ok':'确定'},
                                            icon:Ext.MessageBox.INFO,
                                            closable:false,
                                            fn:function(e){
                                                if(e=='ok'){
                                                    Ext.getCmp("site.add.site.site_name").setValue('');
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                    }
                }
            }),{
                id:'crtFile',
                fieldLabel:"请选择信任证书",
                width:250,
                name : 'crtFile',
                xtype:'textfield',
                inputType:'file',
                allowBlank:false,
                regexText:'请选择信任证书',
                listeners:{
                    render:function () {
                        Ext.get('crtFile').on("change", function () {
                            var file = this.getValue();
                            var fs = file.split('.');
                            if (fs[fs.length - 1].toLowerCase() != 'crt' && fs[fs.length - 1].toLowerCase() != 'cer') {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:200,
                                    msg:'上传文件格式不对,请重新选择!',
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.ERROR,
                                    closable:false,
                                    fn:function (e) {
                                        if (e == 'ok') {
                                            Ext.getCmp('crtFile').setValue('');
                                        }
                                    }
                                });
                            }
                        });
                    }
                }

           },{
                id:'keyFile',
                fieldLabel:"请选择要上传服务器证书",
                width:250,
                name : 'keyFile',
                xtype:'textfield',
                inputType:'file',
                allowBlank:false,
                regexText:'请选择要上传服务器证书',
                listeners:{
                    render:function () {
                        Ext.get('keyFile').on("change", function () {
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
                                            Ext.getCmp('keyFile').setValue('');
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
           },{
                fieldLabel:'证书密码',
                name:'pwd',
                xtype:'textfield',
                width:250,
                inputType:'password',
                regex:/^\S{4,20}$//*,
                allowBlank:false*/
            }
        ]
    });
    var win = new Ext.Window({
        title:"新增站点证书",
        width:500,
        layout:'fit',
        height:340,
        modal:true,
        items:formPanel,
        bbar:[
            '->',
            {
                id:'add_win.info',
                text:'新增',
                width:50,
                handler:function(){
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url :'../../SiteAction_add.action',
                            timeout: 20*60*1000,
                            method :'POST',
                            waitTitle :'系统提示',
                            waitMsg :'正在连接...',
                            success : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'新增成功,点击返回页面!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        store.reload();
                                        win.close();
                                    }
                                });
                            },
                            failure : function() {
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
            },{
                text:'重置',
                width:50,
                handler:function(){
                    formPanel.getForm().reset();
                }
            }
        ]
    }).show();
}

function del_site(grid_panel, store){
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    if(!recode){
        Ext.Msg.alert("提示", "请选择一条记录!");
    }else{
        Ext.Msg.confirm("提示", "确认删除吗？", function(sid) {
            if (sid == "yes") {
                Ext.Ajax.request({
                    url : "../../SiteAction_delete.action",
                    timeout: 20*60*1000,
                    method : "POST",
                    params:{id:recode.get("site")},
                    success : function(r, o) {
                        var respText = Ext.util.JSON.decode(r.responseText);
                        var msg = respText.msg;
                        Ext.Msg.alert("提示", msg);
                        grid_panel.getStore().reload();
                    },
                    failure : function(r,o) {
                        Ext.Msg.alert("提示", "删除失败!");
                    }
                });
            }
        });
    }
}







