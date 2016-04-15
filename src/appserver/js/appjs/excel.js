/** 
 * @include "excel_const.js" 
 * @include "excel_app.js" 
 */ 
Ext.namespace("Ext.icss"); 
/** 
 * 数据工具类的基类 
 * @param {} config 
 */ 
Ext.icss.DataTool = function(config) { 
    config = config || {}; 
    this.initialConfig = config; 
    Ext.icss.DataTool.superclass.constructor.call(this); 
}; 

Ext.extend(Ext.icss.DataTool,Ext.util.Observable, { 

}); 

/** 
 * 数据工具类，此类主要实现将表格数据导出到Microsoft Excel功能 
 * @param {} config 
 */ 
Ext.icss.Data2ExcelTool = function(config) { 
    config = config || {}; 
    this.excelApp = Ext.icss.ExcelApp; 
    Ext.icss.Data2ExcelTool.superclass.constructor.call(this); 
}; 
Ext.extend(Ext.icss.Data2ExcelTool,Ext.icss.DataTool,{ 
     
    export2Excel:function(grid,filename){ 
            var worksheet = this.excelApp.getWorksheet(1); 
            /* 
            var worksheet = {}; 
            worksheet.Cells = {}; 
            */ 
            var cells = this.fillWorksheet(worksheet,grid,filename); 
            //this.excelApp.close(); 
    }, 
    fillWorksheet : function(worksheet,grid,filename) { 
            var cells = worksheet.Cells; 
             
            var cm = grid.getColumnModel(); 
            var ds = grid.getStore(); 
             
            var cols = cm. getColumnCount(); 
            var rows = ds.getCount(); 
            
            //生成表头同时计算导入了多少列
            this.columns = this.buildHeader(worksheet,grid,cells,1,1);
            //this.buildSumRow(grid,cells,2,1); 
            var nextRow = this.buildMultiHeader(grid,worksheet,cells);
            if(nextRow>2){
            	rows++;
            }
            rows = this.buildBody(grid,cells,nextRow,1,worksheet); 
			worksheet.Rows(1).Font.Bold = Excel.XlBoolean.True;
			worksheet.Rows(1).HorizontalAlignment = 3; 
			if(nextRow>2){
			worksheet.Rows(2).Font.Bold = Excel.XlBoolean.True;
			}
             
            this.buildSummaryRow(grid,cells,++rows,worksheet);
            worksheet.Range(cells(1, 1), cells(rows,this.columns)).Borders.LineStyle = Excel.XlBorderWeight.xlHairline; 
            worksheet.Range(cells(1, 1), cells(rows,this.columns)).Columns.AutoFit();
            
            if(this.groupColumn){//隐藏分组列
	        	worksheet.Columns(this.groupColumn).EntireColumn.Hidden = Excel.XlBoolean.True;
	        }
       		
            return cells; 
    }, 
    /** 
     * 根据grid的cm，生成表格表头 
     * @param {} grid 
     * @param {} cells excel cell对象 
     * @param {} row 开始行 
     * @param {} col 开始列 
     */ 
     
    buildHeader:function(worksheet,grid,cells,rowNumber,col){ 
        var cm = grid.getColumnModel(); 
        var ds = grid.getStore(); 
        var cfg = null; 
        var columnIndex = 0;//列计数
        var start = 0;
        var end = cm.config.length
        if(ds.groupField){
        	start++;
        	end++;
        	columnIndex++;
        }
        for(var i = 0; start<end; ++i,++columnIndex,++start){ 
            cfg = cm.config[i];
            if(cfg.id == 'numberer'){//不导序号列
            	--columnIndex;
            	continue;
            }else if(cfg.id == 'checker'){ 
                this.excelApp.setCellValue(cells,rowNumber,col+columnIndex,''); 
            }else{ 
                this.excelApp.setCellValue(cells,rowNumber,col+columnIndex,cfg.header); 
                if(cfg.dataIndex==ds.groupField){//分组列的下标
                	this.groupColumn = col+columnIndex;
                }
            } 
        }
        return columnIndex;
    }, 
    buildMultiHeader : function(grid,worksheet,cells){
    	 var cm = grid.getColumnModel(); 
        if(cm.rows){//是否有多表头
	        var cols = grid.getColumnModel().getColumnCount();
	        var row = cm.rows[0];
	        worksheet.Rows(1).Insert;
	        var c = 0;//单元格列下标
	        var range=[];//要合并的单元格
	        if(this.columns>=cols){//导出的列小于列表的列数说明表上有计数列
	       	 c = 1;//单元格列下标
	        }
	        for(var i=0;i<row.length;i++){
		        	var colspan = 0;//跨列数
		        	if(row[i].colspan
		        			&&row[i].colspan>1){//取得指定的跨列数
		        		colspan = row[i].colspan;
		        	}else{//如果没指定就是1
		        		colspan = 1;
		        	}
	        		if(row[i].header){//如果表头有文字就导出文字
		        		//开始下标,如果从第一列开始,下标就是0否则就是取当前累计的下标数
		        		var startIndex = i==0?1:c
	        			this.excelApp.setCellValue(cells,1,startIndex,row[i].header);
	        			//保存可合并的单元格
	        			range.push(worksheet.Range(cells(1, startIndex),cells(1,startIndex-1+colspan)));
		        	}
		        	
		        	c += colspan;//刷新累计的下标
		        }
		        for(var i in range){//合并单元格
		        	range[i].MergeCells = true;
		        }
	        return 3;
        }
        return 2;
    },
    /** 
     * 根据GRID的store,导出数据 
     * @param {} grid 
     * @param {} cells 
     * @param {} row 
     * @param {} col 
     */ 
    buildBody : function(grid, cells, row, col,worksheet) { 
        var cm = grid.getColumnModel(); 
        var ds = grid.getStore(); 
        var byGroup = false;
        var groupIndex = 0;
		if(ds.groupField){
			byGroup = true;
		}
        var cfg = null; 
       	var groupField;
       	var rowIndexs;
       
        ds.each(function(rec) {
       		var columnIndex = 0;//列计数	
	       	var start = 0;
	        var end = cm.config.length
	        if(ds.groupField){
	        	start++;
	        	end++;
	        	columnIndex++;
	        }
	        //处理第一行数据,当有分组时就把组名写到第一行数据前
       		if(!groupField&&ds.groupField){
               groupField = rec.data[ds.groupField];
                worksheet.Rows(row).Add;
            	this.excelApp.setCellValue(cells, row, 1,rec.data[ds.groupField]);
				worksheet.Rows(row).Font.Bold = Excel.XlBoolean.True;
            	row++;
             }
            var tr;
            var groupRowDataIndex = [];
            if(byGroup&&groupField!=rec.data[ds.groupField]){//如果分组且当前组名与当前记录的组名不相同,说明上一分组数据已经遍历完成,就写统计行和新的分组名
                //插入统计行
                worksheet.Rows(row).Insert;
      			tr = document.createElement("TR");
      			tr.innerHTML = grid.getView().groupSummary.groupResult[groupIndex++];
      			var divs = tr.getElementsByTagName("DIV");
      			for(var i=0;i<divs.length;i++){
      				var index = i;
      				var clmn = index;
      				if(ds.groupField){
      					clmn++;
      				}
      				if(divs[index])
      				this.excelApp.setCellValue(cells, row,clmn ,divs[index].innerText);
      			}
      			rowIndexs = groupRowDataIndex;
				worksheet.Rows(row).Font.Bold = Excel.XlBoolean.True;
			
				row++;//向下一行
				//添加新的分组行
            	worksheet.Rows(row).Add;
            	this.excelApp.setCellValue(cells, row, 1,rec.data[ds.groupField]);
				groupField = rec.data[ds.groupField];
				worksheet.Rows(row).Font.Bold = Excel.XlBoolean.True;
            	row++;
            }
            for (var i = 0; start<end; ++i,++columnIndex,++start) { 
                cfg = cm.config[i]; 
                if(cfg.id == 'numberer'){//不导序号列
            		--columnIndex;
            		continue;
            	}else if (cfg.id == 'checker') { 
                    this.excelApp.setCellValue(cells, row, col + columnIndex, ''); 
                }else { 
                    var val = rec.get(cfg.dataIndex); 
                     /*if(cfg.renderer){ 
                        val = cfg.renderer(val)+'render'; 
                    } 
                    if(val){ 
                        this.excelApp.setCellValue(cells, row, col + i, val );       
                    }        
                     */
                     if(cfg.renderer){ 
                        val = cfg.renderer(val,'',rec); 
                     } 
                     this.excelApp.setCellValue(cells, row, col + columnIndex, val );
                }
                if(ds.groupField){
              		groupRowDataIndex.push(col-1 + columnIndex); 
                }else{
              		groupRowDataIndex.push(col + columnIndex);  
              	}
            }
            row++; 
        },this); 
        if(byGroup&&rowIndexs){
           var tr = document.createElement("TR");
           tr.innerHTML = grid.getView().groupSummary.groupResult[groupIndex];
           divs = tr.getElementsByTagName("DIV");
	       for(var i=0;i<rowIndexs.length;i++){
 				var index = rowIndexs[i];
 				var clmn = index;
  				if(ds.groupField){
  					clmn++;
  				}
      			if(divs[index])
 				this.excelApp.setCellValue(cells, row, clmn,divs[index].innerText);
	     	}
		   worksheet.Rows(row).Font.Bold = Excel.XlBoolean.True;
		   row++;
        }
        return row++;
    }, 
     
    /** 
     * 增加合计行 
     * @param {} grid 
     * @param {} cells 
     * @param {} row 
     * @param {} col 
     */ 
    buildSumRow:function(grid,cells,row,col){        
        var cm = grid.getColumnModel(); 
        var cfg = null; 
        for (var i = 0; i < cm.config.length; ++i) { 
            cfg = cm.config[i]; 
            if(cfg.sumcaption){ 
                this.excelApp.setCellValue(cells,row,col+i,cfg.sumcaption); 
            } 
             
            if(cfg.sumvalue){ 
                this.excelApp.setCellValue(cells,row,col+i,cfg.sumvalue); 
            } 
        } 
    }, 
     
    /** 
     * 增加汇总行 
     * @param {} grid 
     * @param {} cells 
     * @param {} row 
     * @param {} col 
     */ 
    buildSummaryRow:function(grid,cells,row,worksheet){        
        var view = grid.getView();
        if(view.summary){
        	//alert(view.summary.dom.getElementsByTagName("TD")[0].outerHTML);
	        var ds = grid.getStore(); 
	        var clmns = 0;//列偏移量
			if(ds.groupField){//如果有分组就把导出的列偏移量加1
				clmns++;
			}
        	var cols = grid.getColumnModel().getColumnCount(); 
        		var tds = view.summary.dom.getElementsByTagName("TD");
        		for(var i=1;tds!=null&&i<tds.length;++i){
        			var div = tds[i].firstChild;
        			//alert(div.outerHTML)
        			this.excelApp.setCellValue(cells,row,i+clmns,div.innerText); 
        		}
        		worksheet.Rows(row).Font.Bold = Excel.XlBoolean.True;
        }
    } 
     
});