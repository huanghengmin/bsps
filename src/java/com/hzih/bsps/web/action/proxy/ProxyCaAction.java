package com.hzih.bsps.web.action.proxy;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-3-6
 * Time: 下午3:51
 * To change this template use File | Settings | File Templates.
 */
public class ProxyCaAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(ProxyCaAction.class);

    public String getProxyCa() throws Exception {
        String jsons = "{success:true,'pclist':9,'pcrow':[" +
                "{id:1,name:'国家',abbreviation:'C',content:'asfd'}," +
                "{id:2,name:'省份',abbreviation:'ST',content:'tsadfcp'}," +
                "{id:3,name:'城市',abbreviation:'L',content:'tcsfdp'}," +
                "{id:4,name:'公司/机构',abbreviation:'O',content:'sfd'}," +
                "{id:5,name:'部门',abbreviation:'OU',content:'sdf'}," +
                "{id:6,name:'名称',abbreviation:'CN',content:'fd'}," +
                "{id:7,name:'电子邮件',abbreviation:'emailaddress',content:'sf'}," +
                "{id:8,name:'起始时间',abbreviation:'notBefore',content:'sf'}," +
                "{id:9,name:'截止时间',abbreviation:'notAfter',content:'sfdf'}" +
                "]}";
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }
}
