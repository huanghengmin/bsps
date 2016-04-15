package com.hzih.bsps.utils;

import com.hzih.bsps.service.LogService;
import com.hzih.bsps.web.action.ActionBase;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts2.ServletActionContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-8-3
 * Time: 上午11:41
 * To change this template use File | Settings | File Templates.
 */
public class EndUserBatchImport {
    private File uploadFile;
    private String uploadFileFileName;
    private String uploadFileContentType;
    private Logger logger = Logger.getLogger(EndUserBatchImport.class);
    private String status ="0";
    private LogService logService;

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public File getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getUploadFileFileName() {
        return uploadFileFileName;
    }

    public void setUploadFileFileName(String uploadFileFileName) {
        this.uploadFileFileName = uploadFileFileName;
    }

    public String getUploadFileContentType() {
        return uploadFileContentType;
    }

    public void setUploadFileContentType(String uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
    }

    public String batchImportUser() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String superDN =request.getParameter("DN");
        String json=null;
        String msg = null;
        logger.info(uploadFileFileName);
        logger.info(uploadFileContentType);
        if(uploadFileFileName.endsWith(".xls")||uploadFileFileName.endsWith(".et")){
        }  else {
            msg = "导入的文件不是[.xls]或者[.et]文件";
        }
        if(msg == null){
            HSSFWorkbook workbook = null;
            try {
                workbook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(uploadFile)));
            } catch (IOException e) {
                logger.info("没有找到导入文件::"+e.getMessage());
            }
            HSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum > 5000){
                msg = "Excel文件实际数据内容大于5000行,导入失败!<br/>请选中5002行到"+(lastRowNum+1)+"行数据,执行一次删除操作";
                logger.info("批量导入用户条目过多::"+msg);
                json="{success:false}";
            } else {
                json = readExcelDataSaveToLdap(request, superDN, msg, sheet, lastRowNum);
            }
        } else {
            json="{success:false}";
            logger.info("批量导入用户失败::"+msg);
        }
        actionBase.actionEnd(response,json,result);
        return null;
    }

    private String readExcelDataSaveToLdap(HttpServletRequest request, String superDN, String msg, HSSFSheet sheet, int lastRowNum) throws NamingException {
        String json;
        boolean isNeedToAddMany = true;

        String baseDn = null;
        String cn = null;
        if(superDN.contains(",")){
            baseDn =  superDN.substring(superDN.indexOf(",")+1,superDN.length());
            cn = superDN.substring(0,superDN.indexOf(","));
        }
        //查找父Ca属性
        for ( int i = 1;i <= lastRowNum;i++){
            HSSFRow row = sheet.getRow(i);
            short cellNum = 0;
            HSSFCell cell = row.getCell(cellNum ++);
            String username = cell.getStringCellValue();
            if (username.equals("") || username == null){
                msg = "第"+(i+1)+"行,第"+cellNum+"列 用户名 不存在,导入失败";
                logger.info(msg);
                isNeedToAddMany = false;break;
            }
            cell = row.getCell(cellNum ++);
            String password = cell.getStringCellValue().toString();
            if(password.equals("") || password == null){
                msg = "第"+(i+1)+"行,第"+cellNum+"列 密码 不存在,导入失败";
                logger.info(msg);
                isNeedToAddMany = false;break;
            }
            cell = row.getCell(cellNum ++);
            String id = cell.getStringCellValue().toString();
            if(id.equals("") || id == null){
                msg = "第"+(i+1)+"行,第"+cellNum+"列 身份证 不存在,导入失败";
                logger.info(msg);
                isNeedToAddMany = false;break;
            } else if (id.length()<15){
                msg = "第"+(i+1)+"行,第"+cellNum+"列 身份证 长度小于15,导入失败";
                isNeedToAddMany = false;break;
            }   else {
                if(id.length()>18){
                    msg = "第"+(i+1)+"行,第"+cellNum+"列 身份证 长度大于18,导入失败";
                    logger.info(msg);
                    isNeedToAddMany = false;break;
                }
            }
            cell = row.getCell(cellNum ++);
            String phone = cell.getStringCellValue().toString();
            if(phone.equals("") || phone == null){
                msg = "第"+(i+1)+"行,第"+cellNum+"列 电话 不存在,导入失败";
                logger.info(msg);
                isNeedToAddMany = false;break;
            }
            cell = row.getCell(cellNum ++);
            String address = cell.getStringCellValue().toString();
            if(address.equals("") || address == null){
                msg = "第"+(i+1)+"行,第"+cellNum+"列 联系地址 不存在,导入失败";
                logger.info(msg);
                isNeedToAddMany = false;break;
            }

            cell = row.getCell(cellNum ++);
            String email = cell.getStringCellValue().toString();
            if(email.equals("") || email == null){
                msg = "第"+(i+1)+"行,第"+cellNum+"列 电子邮件 不存在,导入失败";
                logger.info(msg);
                isNeedToAddMany = false;break;
            }
            cell = row.getCell(cellNum ++);
            String jobNumber = cell.getStringCellValue().toString();
            if(jobNumber.equals("") || jobNumber == null){
                msg = "第"+(i+1)+"行,第"+cellNum+"列 工号 不存在,导入失败";
                logger.info(msg);
                isNeedToAddMany = false;break;
            }
            if(isNeedToAddMany){
                //保存用户到ldap

            }
        }
        //关闭ldap连接

        json="{success:true}";
        return json;
    }

    private Attributes selectSuperCaAttrs(DirContext context, String baseDn, String cn) throws NamingException {
        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration results = context.search(baseDn, cn, sc);
        Attributes attrs = null;
        if (results.hasMore()) {
            SearchResult sr = (SearchResult) results.next();
            attrs = sr.getAttributes();
        }
        return attrs;
    }

}
