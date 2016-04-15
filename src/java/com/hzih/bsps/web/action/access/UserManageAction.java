package com.hzih.bsps.web.action.access;

import com.hzih.bsps.domain.UserManage;
import com.hzih.bsps.service.UserManageService;
import com.hzih.bsps.utils.FileUtil;
import com.hzih.bsps.utils.StringContext;
import com.hzih.bsps.web.action.ActionBase;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts2.ServletActionContext;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-21
 * Time: 下午1:43
 * To change this template use File | Settings | File Templates.
 */
public class UserManageAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(UserManageAction.class);

    private int start;
    private int limit;
    private File uploadFile;
    private String uploadFileFileName;
    private String uploadFileContentType;
    private String coloum;
    private String name;
    private String ids;
    private int id;
    private String cacn;
    private String province;
    private String city;
    private String department;
    private String policestation;
    private String email;
    private String tel;
    private String address;
    private String idcard;
    private String description;

    private String ldapip;
    private String ldapport;
    private String ldapusername;
    private String ldappassword;
    private String ldapnode;
    private String ldaphpath;
    private String ldapfilter;
    private String ldaptime;

    private UserManageService userManageService;

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

    public String getLdapip() {
        return ldapip;
    }
    public void setLdapip(String ldapip) {
        this.ldapip = ldapip;
    }
    public String getLdapport() {
        return ldapport;
    }
    public void setLdapport(String ldapport) {
        this.ldapport = ldapport;
    }
    public String getLdapusername() {
        return ldapusername;
    }
    public void setLdapusername(String ldapusername) {
        this.ldapusername = ldapusername;
    }
    public String getLdappassword() {
        return ldappassword;
    }
    public void setLdappassword(String ldappassword) {
        this.ldappassword = ldappassword;
    }
    public String getLdapnode() {
        return ldapnode;
    }

    public void setLdapnode(String ldapnode) {
        this.ldapnode = ldapnode;
    }
    public String getLdaphpath() {
        return ldaphpath;
    }
    public void setLdaphpath(String ldaphpath) {
        this.ldaphpath = ldaphpath;
    }
    public String getLdapfilter() {
        return ldapfilter;
    }
    public void setLdapfilter(String ldapfilter) {
        this.ldapfilter = ldapfilter;
    }
    public String getLdaptime() {
        return ldaptime;
    }
    public void setLdaptime(String ldaptime) {
        this.ldaptime = ldaptime;
    }
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }
    public File getUploadFile() {
        return uploadFile;
    }
    public String getColoum() {
        return coloum;
    }
    public String getName() {
        return name;
    }
    public void setColoum(String coloum) {
        this.coloum = coloum;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setIds(String ids) {
        this.ids = ids;
    }
    public String getIds() {
        return ids;
    }
    public static Logger getLogger() {
        return logger;
    }
    public int getId() {
        return id;
    }
    public String getCacn() {
        return cacn;
    }
    public String getProvince() {
        return province;
    }
    public String getCity() {
        return city;
    }
    public String getDepartment() {
        return department;
    }
    public String getPolicestation() {
        return policestation;
    }
    public String getEmail() {
        return email;
    }
    public String getTel() {
        return tel;
    }
    public String getAddress() {
        return address;
    }
    public String getIdcard() {
        return idcard;
    }
    public String getDescription() {
        return description;
    }
    public UserManageService getUserManageService() {
        return userManageService;
    }
    public void setUserManageService(UserManageService userManageService) {
        this.userManageService = userManageService;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setCacn(String cacn) {
        this.cacn = cacn;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setPolicestation(String policestation) {
        this.policestation = policestation;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String findAllUserManage() throws Exception {
        ServletActionContext.getRequest().setCharacterEncoding("utf-8");
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        List<UserManage> userlist = null;
        List<UserManage> userListAll = userManageService.findUserManages();
        String jsons = null;
        try{
            userlist = userManageService.findUserManages(start, limit);
            StringBuffer jsonb = new StringBuffer();
            jsonb.append("{success:true,'modellist':").append(userListAll.size()).append(",'rows':[");
            for(UserManage s : userlist){
                jsonb.append("{id:").append(s.getId()).append(",cacn:'").append(s.getCacn());
                jsonb.append("',province:'").append(s.getProvince()).append("',city:'").append(s.getCity());
                jsonb.append("',department:'").append(s.getDepartment()).append("',policestation:'").append(s.getPolicestation());
                jsonb.append("',email:'").append(s.getEmail()).append("',tel:'").append(s.getTel());
                jsonb.append("',address:'").append(s.getAddress()).append("',idcard:'").append(s.getIdcard());
                jsonb.append("',description:'").append(s.getDescription()).append("'},");
            }
            jsonb.deleteCharAt(jsonb.length()-1);
            jsonb.append("]}");
            jsons = jsonb.toString();
        } catch (Exception e)  {
            logger.error("",e);
        }
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return SUCCESS;
    }

    public String select() throws Exception {
        ServletActionContext.getRequest().setCharacterEncoding("utf-8");
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        List<UserManage> userlist = null;
        List<UserManage> userListAll = null;
        String jsons = null;
        try{
            if(null==coloum||"".equals(coloum)){
                userListAll = userManageService.findUserManages();
                userlist = userManageService.findUserManages(start, limit);
            }else{
                userListAll = userManageService.findUserManageByWhere(coloum,name);
                userlist = userManageService.findUserManageByWhere(coloum,name,start,limit);
            }
            StringBuffer jsonb = new StringBuffer();
            jsonb.append("{success:true,'modellist':").append(userListAll.size()).append(",'rows':[");
            for(UserManage s : userlist){
                jsonb.append("{id:").append(s.getId()).append(",cacn:'").append(s.getCacn());
                jsonb.append("',province:'").append(s.getProvince()).append("',city:'").append(s.getCity());
                jsonb.append("',department:'").append(s.getDepartment()).append("',policestation:'").append(s.getPolicestation());
                jsonb.append("',email:'").append(s.getEmail()).append("',tel:'").append(s.getTel());
                jsonb.append("',address:'").append(s.getAddress()).append("',idcard:'").append(s.getIdcard());
                jsonb.append("',description:'").append(s.getDescription()).append("'},");
            }
            if(userlist.size()!=0) {
                jsonb.deleteCharAt(jsonb.length()-1);
            }
            jsonb.append("]}");
            jsons = jsonb.toString();
        } catch (Exception e)  {
             logger.error("",e);
        }
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return SUCCESS;
    }

    public String setNull() {
        coloum = null;
        name = null;
        return SUCCESS;
    }


    public String addUserManage() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        if(userManageService.checkUserManageByCacn(cacn)){
            msg = "ssadd";
            json = "{success:true,msg:'"+msg+"'}";
        }else {
            try{
                userManageService.addUserManage(cacn,province,city,department,policestation,email,tel,address,idcard,description);
                msg = "增加成功";
            } catch (Exception e){
                logger.error(e);
                msg = "增加失败";
            }
            json = "{success:true,msg:'"+msg+"'}";
        }
        try {
            actionBase.actionEnd(response, json, result);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public String delUserManageByIds() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        try{
            userManageService.delUserManageByIds(ids);
            msg = "删除成功";
        } catch (Exception e){
            msg = "删除失败";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String updUserManage() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        try{
            userManageService.updUserManage(id, cacn, province, city, department, policestation, email, tel, address, idcard, description);
            msg = "修改成功";
        } catch (Exception e){
            logger.error(e);
            e.printStackTrace();
            msg = "修改失败";
        }
        json = "{success:true,msg:'"+msg+"'}";
        try {
            actionBase.actionEnd(response, json, result);
        } catch (IOException e) {
            logger.error(e);
        }
        return null;
    }

    public String importFile() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String  json="{success:true}";
        String msg = null;
        try {
            HSSFWorkbook workbook = null;
            try {
                workbook =  new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(uploadFile)));
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("没有找到导入文件::" + e.getMessage());
                return null;
            }
            HSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum > 5000){
                msg = "Excel文件实际数据内容大于5000行,导入失败!<br/>请选中5002行到"+(lastRowNum+1)+"行数据,执行一次删除操作";
                logger.info("批量导入用户条目过多::"+msg);
                json="{success:false}";
            }else{
                HSSFRow row = null;
                HSSFCell cell = null;
                for ( int i = 1;i < lastRowNum;i++){
                    row = sheet.getRow(i);
                    if(row == null) {
                        continue;
                    }
                    cell = row.getCell((short) 0);
                    if(cell!=null) {
                        cacn = cell.toString();
                    }else {
                        continue;
                    }
//                    province = row.getCell((short) 1).getStringCellValue();
                    cell = row.getCell((short) 1);
                    if(cell!=null) {
                        province = cell.toString();
                    }else {
                        province = "";
                    }
//                    city = row.getCell((short) 2).getStringCellValue();
                    cell = row.getCell((short) 3);
                    if(cell!=null) {
                        city = cell.toString();
                    }else {
                        city = "";
                    }
//                    department = row.getCell((short) 3).getStringCellValue();
                    cell = row.getCell((short) 3);
                    if(cell!=null) {
                        department = cell.toString();
                    }else {
                        department = "";
                    }
//                    policestation = row.getCell((short) 4).getStringCellValue();
                    cell =   row.getCell((short) 4);
                    if(cell!=null) {
                        policestation = cell.toString();
                    }else {
                        policestation = "";
                    }
//                    email = row.getCell((short) 5).getStringCellValue();
                    cell = row.getCell((short) 5);
                    if(cell!=null) {
                        email = cell.toString();
                    }else {
                        email = "";
                    }
//                    tel = row.getCell((short) 6).getStringCellValue();
                    cell = row.getCell((short) 6);
                    if(cell!=null) {
                        tel = cell.toString();
                    }else {
                        tel = "";
                    }
//                    address = row.getCell((short) 7).getStringCellValue();
                    cell = row.getCell((short) 7);
                    if(cell!=null) {
                        address = cell.toString();
                    }else {
                        address = "";
                    }
//                    idcard = row.getCell((short) 8).getStringCellValue();
                    cell = row.getCell((short) 8);
                    if(cell!=null) {
                        idcard = cell.toString();
                    }else {
                        idcard = "";
                    }
//                    description = row.getCell((short) 9).getStringCellValue();
                    cell = row.getCell((short) 9);
                    if(cell!=null) {
                        description = cell.toString() ;
                    }else {
                        description = "";
                    }
                    ArrayList<UserManage> list = userManageService.findUserManagesByCacn(cacn);
                    if(null==list||list.size()==0){
                        userManageService.addUserManage(cacn,province,city,department,policestation,email,tel,address,idcard,description);
                    }else {
                        UserManage userManage = list.get(0);
                        userManage.setCacn(cacn);
                        userManage.setProvince(province);
                        userManage.setCity(city);
                        userManage.setDepartment(department);
                        userManage.setPolicestation(policestation);
                        userManage.setEmail(email);
                        userManage.setTel(tel);
                        userManage.setAddress(address);
                        userManage.setIdcard(idcard);
                        userManage.setDescription(description);
                        userManageService.updUserManage(userManage);
                    }
                }
                msg = "导入成功";
                json = "{success:true,msg:'"+msg+"'}";
            }

        }catch (Exception e){
            msg = "导入失败";
            json = "{success:true,msg:'"+msg+"'}";
            logger.error("用户导入失败",e);
            e.printStackTrace();
        }
        
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String outportFile() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        request.getCharacterEncoding();
        response.setCharacterEncoding("utf-8");
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String  json="{success:true}";
        String msg = null;
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("usermanages");
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCell cell = row.createCell((short)0);
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
//        font.setFontHeight((short) 100);
        style.setFont(font);
        cell.setCellStyle(style);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("证书cn项"); cell.setCellStyle(style);
        cell = row.createCell((short)1);
        cell.setCellValue("省份"); cell.setCellStyle(style);
        cell = row.createCell((short)2);
        cell.setCellValue("市"); cell.setCellStyle(style);
        cell = row.createCell((short)3);
        cell.setCellValue("所属单位"); cell.setCellStyle(style);
        cell = row.createCell((short)4);
        cell.setCellValue("所属派出所");cell.setCellStyle(style);
        cell = row.createCell((short)5);
        cell.setCellValue("用户邮箱");cell.setCellStyle(style);
        cell = row.createCell((short)6);
        cell.setCellValue("联系电话"); cell.setCellStyle(style);
        cell = row.createCell((short)7);
        cell.setCellValue("联系地址"); cell.setCellStyle(style);
        cell = row.createCell((short)8);
        cell.setCellValue("身份证号码"); cell.setCellStyle(style);
        cell = row.createCell((short)9);
        cell.setCellValue("用户描述");cell.setCellStyle(style);
        cell.setCellStyle(style);
        ArrayList<UserManage> list = userManageService.findUserManages();
        UserManage user = null;
        for(int i=0;i<list.size();i++) {
            row = sheet.createRow(i+1);
            user = list.get(i);
            try{
            row.createCell((short)0).setCellValue(user.getCacn());
            row.createCell((short)1).setCellValue(user.getProvince());
            row.createCell((short)2).setCellValue(user.getCity());
            row.createCell((short)3).setCellValue(user.getDepartment());
            row.createCell((short)4).setCellValue(user.getPolicestation());
            row.createCell((short)5).setCellValue(user.getEmail());
            row.createCell((short)6).setCellValue(user.getTel());
            row.createCell((short)7).setCellValue(user.getAddress());
            row.createCell((short)8).setCellValue(user.getIdcard());
            row.createCell((short)9).setCellValue(user.getDepartment());
            }catch (Exception e){
                e.printStackTrace();
            }   
        }
        try {
            FileOutputStream fout = new FileOutputStream(StringContext.systemPath+File.separator+"bsconfig"+File.separator+"usermanage.xls");
            wb.write(fout);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String Agent = null;
        StringTokenizer st = null;
        String userBrowser = null;
        try{
            Agent= request.getHeader("User-Agent");
            st = new StringTokenizer(Agent,";");
            st.nextToken();
            //得到用户的浏览器名  MSIE  Firefox
            userBrowser = st.nextToken();
        }catch (Exception e){
        }
        File file = new File(StringContext.systemPath+File.separator+"bsconfig"+File.separator+"usermanage.xls");
        if(file.exists()){
            String name = file.getName();
            if(userBrowser!=null) {
            FileUtil.downType(response, name, userBrowser);
            }
            response = FileUtil.copy(file, response);
        }
//        json = "{success:true}";
//        logger.info("publicca downloadCa!!下载成功"+DN);

        msg = "导出成功";
        json = "{success:true,msg:'"+msg+"'}";
        try {
            actionBase.actionEnd(response, json, result);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public String toUtf8String(String s){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if (c >= 0 && c <= 255){sb.append(c);}
            else{
                byte[] b;
                try { b = Character.toString(c).getBytes("utf-8");}
                catch (Exception ex) {
//                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    public String addUsermanagesByLDAP()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://"+ldapip+":"+ldapport);
        env.put(Context.AUTHORITATIVE, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ldaphpath);
        env.put(Context.SECURITY_CREDENTIALS,"123456");
        env.put("com.sun.jndi.ldap.connect.pool", "true");
        DirContext ctx = null ;
        try {
            ctx = new InitialDirContext(env);
        } catch (NamingException e) {
            logger.info("ldap 连接不通");
        }
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration en = null;
        if(ctx!=null){
            //3已发证 4吊销 5废除
            en = ctx.search("cn=admin,dc=nodomain", "objectClass=hzihuser",constraints);
        }
        int i=1;
        while (en != null && en.hasMoreElements()) {
            Object obj = en.nextElement();
            if (obj instanceof SearchResult) {
                SearchResult si = (SearchResult) obj;
//                System.out.println(si.getNameInNamespace()+   "  i=  "+i++);
                Attributes attribute = si.getAttributes();
//                user.setName(si.getNameInNamespace());
                cacn = getAttrValue(attribute,"cn");
                province = getAttrValue(attribute,"hzihprovince");
                city = getAttrValue(attribute, "hzihcity");
                department = getAttrValue(attribute, "hzihinstitutions");
                policestation = getAttrValue(attribute, "hzihorganization");
                email = getAttrValue(attribute,"hzihemail");
                tel = getAttrValue(attribute,"hzihphone");
                address = getAttrValue(attribute,"hzihaddress");
                idcard = getAttrValue(attribute,"hzihid");
                description = getAttrValue(attribute,"hzihcastatus");
                ArrayList<UserManage> list = userManageService.findUserManagesByCacn(cacn);
                if(null==list||list.size()==0){
                    userManageService.addUserManage(cacn,province,city,department,policestation,email,tel,address,idcard,description);
                }else {
                    UserManage userManage = list.get(0);
                    userManage.setCacn(cacn);
                    userManage.setProvince(province);
                    userManage.setCity(city);
                    userManage.setDepartment(department);
                    userManage.setPolicestation(policestation);
                    userManage.setEmail(email);
                    userManage.setTel(tel);
                    userManage.setAddress(address);
                    userManage.setIdcard(idcard);
                    userManage.setDescription(description);
                    userManageService.updUserManage(userManage);
                }
//                String hzihcity= getAttrValue(attribute,"hzihcity");
//                String hzihinstitutions=getAttrValue(attribute,"hzihinstitutions");  //机构
//                String hzihprovince=getAttrValue(attribute,"hzihprovince");         //省
//                String hzihaddress=getAttrValue(attribute,"hzihaddress");
//                user.setAddress(hzihaddress);
//                String hzihorganization=getAttrValue(attribute,"hzihorganization");  //组织
//                String hzihphone=getAttrValue(attribute,"hzihphone");
//                String hzihcastatus=getAttrValue(attribute,"hzihcastatus");       //状态
//                String hzihid=getAttrValue(attribute,"hzihid");                //
//                String hzihemail=getAttrValue(attribute,"hzihemail");
//                user.setEmail(hzihemail);
//                String hzihjobnumber=getAttrValue(attribute,"hzihjobnumber");
//                String cn=getAttrValue(attribute,"cn");
            }
        }
//        loggor.info("查询成功");
        ctx.close();
        String msg = "导入成功";
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response,json,result);
        return  SUCCESS;
    }

    public String getAttrValue(Attributes attrs, String attributeName) throws NamingException {
        String att = new String() ;
        Attribute cn = attrs.get(attributeName);
        try{
            for (NamingEnumeration nm1 = cn.getAll(); nm1.hasMoreElements();) {
                Object o = nm1.nextElement();
                if (o instanceof byte[]){

                }
                else  {
                    att = o.toString();
                }
            }
        }catch (Exception e){
            return  null;
        }
        return  att;
    }

}
