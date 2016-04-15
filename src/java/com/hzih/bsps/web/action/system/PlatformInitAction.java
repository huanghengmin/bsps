package com.hzih.bsps.web.action.system;

import com.hzih.bsps.web.action.ActionBase;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-13
 * Time: 下午3:30
 * To change this template use File | Settings | File Templates.
 */
public class PlatformInitAction {

    public String find()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        int totalCount =0;
        StringBuilder stringBuilder = new StringBuilder();
        result(stringBuilder);
        totalCount = totalCount+1;
        StringBuilder json=new StringBuilder("{totalCount:"+totalCount+",root:[");
        json.append(stringBuilder.toString().substring(0,stringBuilder.toString().length()-1));
        json.append("]}");
        try {
            actionBase.actionEnd(response,json.toString(),result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String save()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json ="{success:false}";
        String ms_pre = request.getParameter("ms_pre");
        String ms_post = request.getParameter("ms_post");
//        String gap_pre = request.getParameter("gap_pre");
//        String gap_post = request.getParameter("gap_post");
//        String ps_pre = request.getParameter("ps_pre");
//        String ps_post = request.getParameter("ps_post");
        if(ms_pre==null){
            ms_pre = "";
        }
        if(ms_post==null){
            ms_post = "";
        }
//        if(gap_pre==null){
//            gap_pre = "";
//        }
//        if(gap_post==null){
//            gap_post = "";
//        }
       /* if(ps_pre==null){
            ps_pre = "";
        }
        if(ps_post==null){
            ps_post = "";
        }*/
        PlatformInitConfigUtil.save(ms_pre,ms_post/*,gap_pre,gap_post*//*,ps_pre,ps_post*/);
        json ="{success:true}";
        actionBase.actionEnd(response,json,result);
        return null;
    }



    private void result(StringBuilder stringBuilder) {
        stringBuilder.append("{");
        stringBuilder.append("ms_pre:'"+ PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre)+"',");
        stringBuilder.append("ms_post:'"+ PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post)+"'");
//        stringBuilder.append("gap_pre:'"+PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.gap_pre)+"',");
//        stringBuilder.append("gap_post:'"+PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.gap_post)+"'");
//        stringBuilder.append("ps_pre:'"+PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ps_pre)+"',");
//        stringBuilder.append("ps_post:'"+PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ps_post)+"'");
        stringBuilder.append("},");
    }




}
