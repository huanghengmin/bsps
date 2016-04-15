package com.hzih.bsps.web.action.bs;
import com.hzih.bsps.entity.NetInfo;
import com.hzih.bsps.utils.GetListNetInfo;
import com.hzih.bsps.utils.StringContext;
import com.hzih.bsps.utils.StringContext;
import com.hzih.myjfree.ProcessUtil;
import com.inetec.common.util.Proc;
import org.apache.log4j.Logger;
import java.io.*;
import java.util.List;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-24
 * Time: 下午2:53
 * To change this template use File | Settings | File Templates.
 */
public class CertUtils {

    private static Logger logger = Logger.getLogger(CertUtils.class);

    public static boolean saveUploadFile(String save_path,File uploadFile,String uploadFileFileName) throws IOException {
        String file_url=save_path+"/" + uploadFileFileName;
        File file = new File(file_url);
        if(!file.exists()){
            FileInputStream in= new FileInputStream(uploadFile);
            FileOutputStream out= new FileOutputStream(file_url);
            byte[] b = new byte[1024];
            int count = -1;
            while((count = in.read(b)) != -1){
                out.write(b);
            }
            out.flush();
            out.close();
            return true;
        }else {
            return false;
        }
    }

    public static boolean splitUploadFileAndSave(String savePath,String pfxPwd,String uploadFileFileName){
        String fileName = uploadFileFileName.split("\\.")[0];
        String pfx = savePath +"/"+uploadFileFileName;
        String pem = savePath+"/"+fileName+".pem";
        String cert = savePath+"/"+fileName+".crt";
        String private_key = savePath+"/"+fileName+".key";
        boolean flag =execute("openssl pkcs12 -in "+pfx+" -passin pass:"+pfxPwd +" -nocerts -out "+pem+" -passout pass:123456");
        if(flag){
            boolean ex = execute("openssl pkcs12 -in " +pfx+" -passin pass:"+pfxPwd+" -clcerts -nokeys -out "+cert);
            if(ex){
                execute("openssl rsa -in "+pem+" -passin pass:123456 -out "+private_key);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
                Proc cat_proc = new Proc();
                String cat_command = "sh "+ StringContext.systemPath+"/bsshell/cat.sh "+
                        cert+" " +
                        private_key+" "+
                        pem;
                logger.info("cat.sh::::::::"+cat_command);
                cat_proc.exec(cat_command);
            }
        }
        if(new File(pem).exists() && new File(cert).exists() && new File(private_key).exists()) {
            return true;
        }
        return false;
    }

    public static boolean execute(String command){
        try
        {
            Process process = Runtime.getRuntime().exec (command);
            InputStreamReader ir=new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader (ir);
            String line;
            while ((line = input.readLine ()) != null){
                logger.info(line);
            }
            return true;
        }catch (java.io.IOException e){
            logger.info("IOException:"+e);
        }
        return false;
    }

    public static String executeString(String command){
        String netFlow = "";
        try
        {
            Process process = Runtime.getRuntime().exec (command);
            InputStreamReader ir=new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader (ir);
            String line;
            while ((line = input.readLine ()) != null){
                netFlow = netFlow+line+"\n";
            }
        }catch (java.io.IOException e){
            logger.info("IOException:"+e);
        }
        return netFlow;
    }

    public static String executeContainsProcess(String command,String processName) {
        String netFlow = "0";
        try
        {
            Process process = Runtime.getRuntime().exec (command);
            InputStreamReader ir=new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader (ir);
            String line;
            while ((line = input.readLine ()) != null){
                if(line.indexOf(processName)!=-1) {
                    netFlow = line;
                    break;
                }
            }
        }catch (java.io.IOException e){
            logger.info("IOException:"+e);
        }
        return netFlow;
    }



    public String getLinuxNetFlow(String eth) {
        String netFlow = "";
        try
        {
            Process process = Runtime.getRuntime().exec ("/sbin/ifconfig "+eth+" | grep bytes");
            InputStreamReader ir=new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader (ir);
            String line;
            while ((line = input.readLine ()) != null){
                netFlow = netFlow+line;
            }
        }catch (java.io.IOException e){
            logger.info("IOException:"+e);
        }
        return netFlow;
    }

    public Double getRxBytesNum() {
        GetListNetInfo getListNetInfo = new GetListNetInfo();
        Double rxnum=0.0;
        try {
            List<NetInfo> netInfos = getListNetInfo.readInterfaces();
            for(NetInfo netInfo : netInfos) {
                String netFlow = getLinuxNetFlow(netInfo.getInterfaceName());
                String[] lines = netFlow.split(":");
                String[] aa = lines[1].split(" ");
                String rxbytes = aa[0];
                rxnum = rxnum + Double.valueOf(rxbytes);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return rxnum/1024/1024;
    }

    public Double getTxBytesNum() {
        GetListNetInfo getListNetInfo = new GetListNetInfo();
        Double txnum=0.0;
        try {
            List<NetInfo> netInfos = getListNetInfo.readInterfaces();
            for(NetInfo netInfo : netInfos) {
                String netFlow = getLinuxNetFlow(netInfo.getInterfaceName());
                String[] lines = netFlow.split(":");
                String[] aa = lines[2].split(" ");
                String rxbytes = aa[0];
                txnum = txnum + Double.valueOf(rxbytes);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return txnum/1024/1024;
    }

}
