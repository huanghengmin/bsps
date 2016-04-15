/** 
 * Microsoft Excel 应用，导出功能 
 */ 
Ext.namespace("Ext.icss"); 
Ext.icss.ExcelApp = function() { 

    return { 
        excelApp :  null, 
        EXCEL_APP : "Excel.Application", 
        /** 
         * 创建Excel应用程序对象 
         * @param visible{} true or false 表示主程序窗口对象是否可见 
         * @param displayAlerts{} true or false 如果不想在宏运行时被无穷无尽的提示和警告消息所困扰， 
         * 请将本属性设置为 False；这样每次出现需用户应答的消息时，Microsoft Excel 将选择默认应答。 
         * @return Application 对象 
         */ 
        createApp : function(visible, displayAlerts) { 
			try{
	            if (!this.excelApp) { 
	                this.excelApp = new ActiveXObject(this.EXCEL_APP); 
	            } 
	            if (this.excelApp) { 
	                this.excelApp.Visible = visible; 
	                this.excelApp.DisplayAlerts = displayAlerts; 
	            } 
			}catch(e){
				Ext.Msg.show({title : '提示',msg : '没有在您的计算机上找到<b>Mircrosoft Excel</b>.<br/>如果您已经安装,请在 <b><font color="blue">工具->Internet选项->安全->自定义级别</font></b> 中<br/>把 <b><font color="blue">"对没有标记为安全的ActiveX控件进行初始化和脚本运行"</font></b>设为<b><font color="blue">"启用"</font></b>后再<b><font color="blue">刷新</font></b>页面重试'})
			}
            return this.excelApp; 
        }, 

        /** 
         * 关闭指定的应用程序对象 
         * @param exApp 需要关闭的EXCEL应用程序对象 
         */ 
        closeApp : function(exApp) { 
            if (exApp) { 
                exApp.Quit(); 
            } 
        }, 

        /** 
         * 在指定的应用程序对象中，创建一个新的工作簿 
         * @param exApp EXCEL应用程序对象 
         * @return Workbook 对象 
         */ 
        addWorkbook : function(exApp) { 
            if (!exApp) { 
                return; 
            } 

            var workbook = exApp.Workbooks.Add(); 
            if (workbook) { 
                workbook.Activate(); 
            } 
            return workbook; 
        }, 

        /** 
         * 在指定的workbook对象中，查找索引为index的工作表对象 
         * @param workbook Workbook 对象 
         * @param index 工作表的索引 
         * @return Worksheet 对象 
         */ 
        findWorksheet : function(workbook, index) { 
            var worksheet = null; 
            if (index) { 
                worksheet = workbook.Worksheets(index); 
            } else { 
                worksheet = workbook.ActiveSheet; 
            } 

            return worksheet; 
        }, 
        /** 
         * 设置指定的单元格值 
         * @param cell 单元格对象，取Worksheet.Cells属性  
         * @param row cell的行 
         * @param col cell的列 
         * @param value 值 
         */ 
        setCellValue:function(cell,row,col,value){ 
            cell(row,col).Value = value; 
        }, 

        /** 
         * 返回一个指定开始与结束表格的Range对象 
         * @param worksheet Worksheet对象 
         * @param startCell eg. cell(1,1) 
         * @param endCell eg. cell(10,1) 
         * @return Range对象 
         */ 
        getRange:function(worksheet,startCell,endCell){ 
            return worksheet.Range(startCell, endCell); 
        }, 
         
        printPreview : function() { 
            var visible = this.excelApp.Visible; 
            if (this.excelApp.ActiveSheet) { 

                if (false == visible) { 
                    this.excelApp.Visible = true; 
                } 
                this.excelApp.ActiveSheet.PrintPreview(); 

                this.excelApp.Visible = visible; 
            } 
        }, 

        printOut : function() { 
            if (this.excelApp.ActiveSheet) { 

                this.excelApp.ActiveSheet.PrintOut(); 
            } 

        }, 

        close : function() { 
            this.closeApp(this.excelApp); 
            this.excelApp = null; 
        }, 
         
        getWorksheet:function(index){ 
            //if(!this.excelApp){ 
                this.createApp(true,false); 
           // } 
            
            //每次都新建一个工作薄
            var workbook = this.addWorkbook(this.excelApp); 
            
            /*if(!this.excelApp.ActiveWorkbook){ 
                workbook = this.addWorkbook(this.excelApp); 
            } else{
            	workbook = this.excelApp.ActiveWorkbook;
            }*/
            return this.findWorksheet(workbook,index); 
             
        } 
    }; 
}(); 

ExcelApp = Ext.icss.ExcelApp; 