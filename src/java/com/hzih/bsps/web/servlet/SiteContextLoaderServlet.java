package com.hzih.bsps.web.servlet;

import com.hzih.bsps.domain.SafePolicy;
import com.hzih.bsps.service.SafePolicyService;
import com.hzih.bsps.syslog.SysLogSendService;
import com.hzih.bsps.web.action.cs.ShellUtils;
import com.hzih.bsps.web.action.socat.HttpsProcess;
import com.hzih.bsps.web.action.socat.HttpsServer;
import com.hzih.bsps.web.thread.SystemStatusService;
import com.hzih.myjfree.RunMonitorInfoList;
import com.hzih.bsps.constant.AppConstant;
import com.hzih.bsps.constant.ServiceConstant;
import com.hzih.bsps.web.SiteContext;
import com.hzih.myjfree.RunMonitorLiuliangBean2List;
import com.hzih.ssl.core.http.HttpsAuthServer;
import com.hzih.ssl.core.util.ConfigXML;
import com.hzih.ssl.jdbc.NginxLogsTask;
import com.hzih.ssl.jdbc.TimerUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SiteContextLoaderServlet extends DispatcherServlet {
    public static Map<HttpsProcess,HttpsProcess> httpsProcesses = new HashMap<>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(SiteContextLoaderServlet.class);
    public static boolean isRunSysLogService = false;
    public static SysLogSendService sysLogSendService = new SysLogSendService();

    //启动https代理服务
    public void start_https(){
        HttpsServer.start();
    }

    //启动syslog服务
    public void runSysLogSendService(){
        log.info("启动syslog日志!");
        if (SiteContextLoaderServlet.isRunSysLogService) {
            return;
        }else {
            sysLogSendService.init();
            Thread thread = new Thread(sysLogSendService);
            thread.start();
            SiteContextLoaderServlet.isRunSysLogService = true;
        }
    }

    //启动安全服务监听
    public void startService(){
        log.info("启动安全服务监听!");
        HttpsAuthServer server = new HttpsAuthServer();
        server.init(ConfigXML.readBindAddress(), Integer.parseInt(ConfigXML.readBindPort()), ConfigXML.readKeystore(), ConfigXML.readKeystorePwd());
        server.start();
    }

    //启动nginx日志处理线程
    public void startNginxLogService(){
        log.info("启动nginx日志处理!");
        TimerUtil timerUtil = new TimerUtil();
        NginxLogsTask task = new NginxLogsTask();
        timerUtil.startMyTask(task,1,0,0);
    }
    //处理日志
//    public void hadlerNginxLogs(){
//        HandlerNginxLogs handlerNginxLogs = new HandlerNginxLogs();
//        String path = handlerNginxLogs.getFilePath();
//            while (true)   {
//                handlerNginxLogs.readLine(path);
//            try {
//                Thread.sleep(20*1000);
//            } catch (InterruptedException e) {
//
//                log.error("日志文件未找到");
//            }
//        }
//    }
    
	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		SiteContext.getInstance().contextRealPath = config.getServletContext().getRealPath("/");
		// set constants value to app context
		servletContext.setAttribute("appConstant", new AppConstant());
		SafePolicyService service = (SafePolicyService)context.getBean(ServiceConstant.SAFEPOLICY_SERVICE);
		SafePolicy data = service.getData();
		SiteContext.getInstance().safePolicy = data;
        //enable ip forward
        ShellUtils.enable_ipforward();
        //启动https代理
//         start_https();
        //启动syslog
        runSysLogSendService();
        //启动安全服务
//        startService();
        //读取网卡流量
//        new RunMonitorInfoList().start();
//        new RunMonitorLiuliangBean2List().start();

        new SystemStatusService().start();

        //启动nginx日志处理线程
//       startNginxLogService();
        //处理日志
//       hadlerNginxLogs();
    }

	@Override
	public ServletConfig getServletConfig() {
		// do nothing
		return null;
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		// do nothing
	}

	@Override
	public String getServletInfo() {
		// do nothing
		return null;
	}

	@Override
	public void destroy() {

	}
}
