Ext.onReady(function(){
    var caform = new Ext.form.FormPanel({
        labelWidth:60,
        //renderTo : "formt",
        frame : true ,
        defaultType : 'textfield' ,
        buttonAlign : 'left' ,
        labelAlign : 'right' ,
        baseParams : {create : true },
        //  labelWidth : 70 ,
        renderTo :Ext.getBody(),
        defaults:{
//                allowBlank: false,
            blankText: '不能为空!',
            msgTarget: 'side'
        },
        items : [
            new Ext.form.RadioGroup({
                fieldLabel : "测试模式",
                width:163,
                id:'testpattern',
                items : [{
                    boxLabel : 'SSL握手',
                    inputValue : 0,
                    checked : true,
                    name : "rights"
                }, {
                    boxLabel : '网页访问',
                    name : "rights",
                    inputValue : 1
                }],
                listeners:{
                    change :function onclic(s,ck){
                        var flag1=ck.inputValue;
                        if(1==flag1){
//                                    Ext.getCmp("accessserveaddress_panel").hide();

                        }
                    }
                }
            }), {
                fieldLabel : '测试地址' ,
                name : 'testaddress',
                id : 'testaddress',
                width:300
            }
        ],
        buttons:[
            {
                text:'测试',
                handler:function() {

                }
            }
        ]
    })

    var sscsm = new Ext.grid.CheckboxSelectionModel();
    var sscm = new Ext.grid.ColumnModel([
        sscsm,
        {
            header : 'ID',
            dataIndex : 'id',
            width:160,
            hidden:true
        }, {
            header : "证书项名称",
            dataIndex : 'name',
            width:160
        }, {
            header : "缩写",
            dataIndex : 'abbreviation',
            width:160
        }, {
            header : "证书项内容",
            dataIndex : 'content',
            width:160
        }
    ]);
    sscm.defaultSortable = true;

    var ssds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
//            url : '../../ResourceAction_findAllResources.action'
            url : '../../ProxyCaAction_getProxyCa.action'

        }),//调用的动作
        reader : new Ext.data.JsonReader({
            totalProperty : 'pclist',
            root : 'pcrow'
        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'int'
        }, {
            name : 'name',
            mapping : 'name',
            type : 'string'
        }, {
            name : 'abbreviation',
            mapping : 'abbreviation',
            type : 'string'
        }, {
            name : 'content',
            mapping : 'content',
            type : 'string'
        }//列的映射
        ])
    });
    ssds.load();

    var ssgrid = new Ext.grid.GridPanel({
        // var grid = new Ext.grid.EditorGridPanel( {
        /* collapsible : true,// 是否可以展开
         animCollapse : false,// 展开时是否有动画效果*/
        id : 'ssgrid',
        title : '',
        store : ssds,
        cm : sscm,
        sm:sscsm,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        renderTo :Ext.getBody(), /*'noteDiv',*/
        /*
         * // 添加内陷的按钮 buttons : [ { text : '保存' }, { text : '取消' }],
         * buttonAlign : 'center',// 按钮对齐
         *
         */
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
                id : 'load',
                text : '&nbsp;&nbsp;下载&nbsp;&nbsp;',
                tooltip : '下载该证书',
                iconCls : '',
                handler : function() {
                    createrRoleGrid();
                    roleds.load({params:{start:0,limit:10}});
                    winrole(rolegrid);
                }
            }

        ],
//        width : 1472,
        height :600,
        frame : true,
        loadMask : true,// 载入遮罩动画
        autoShow : true
    });
});
