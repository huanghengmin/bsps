package com.hzih.bsps.web.action.cs;

import com.hzih.bsps.cs.ProcessEntity;
import com.hzih.bsps.utils.StringContext;
import com.inetec.common.util.OSInfo;
import com.inetec.common.util.Proc;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 15-5-5.
 */
public class ShellUtils {
    private static Logger logger = Logger.getLogger(ShellUtils.class);

    /**
     *
     * @param processEntity
     * @return
     */
    public static boolean add_nat_tcp(ProcessEntity processEntity,String $5) {
        Proc proc = new Proc();
        String command = null;
        logger.info(
                "添加tcp映射 sourceIp:"+processEntity.getSourceIp()
                +",sourcePort:"+processEntity.getSourcePort()
                +",distIp:"+processEntity.getDistIp()
                +",distPort:"+processEntity.getDistPort()
                +",pre_post:"+$5
        );
        if (OSInfo.getOSInfo().isWin()) {
            command = StringContext.systemPath + "/script/add_nat_tcp.bat " +
                    processEntity.getSourceIp() + " " +
                    processEntity.getSourcePort()+ " " +
                    processEntity.getDistIp()+ " " +
                    processEntity.getDistPort()+ " " +
                    $5;
        } else {
            command = StringContext.systemPath + "/script/add_nat_tcp.sh " +
                    processEntity.getSourceIp() + " " +
                    processEntity.getSourcePort()+ " " +
                    processEntity.getDistIp()+ " " +
                    processEntity.getDistPort()+ " " +
                    $5;
        }
        proc.exec(command);
        if (proc.getResultCode() != -1) {
            if(!proc.getErrorOutput().contains("error")&&!proc.getErrorOutput().contains("Error")){
                return true;
            } else {
                logger.equals(proc.getErrorOutput());
            }
        }
        return false;
    }

    /**
     *
     * @param processEntity
     * @return
     */
    public static boolean del_nat_tcp(ProcessEntity processEntity,String $5) {
        Proc proc = new Proc();
        String command = null;
        logger.info(
                "删除tcp映射 sourceIp:"+processEntity.getSourceIp()
                +",sourcePort:"+processEntity.getSourcePort()
                +",distIp:"+processEntity.getDistIp()
                +",distPort:"+processEntity.getDistPort()
                +",pre_post:"+$5
        );
        if (OSInfo.getOSInfo().isWin()) {
            command = StringContext.systemPath + "/script/del_nat_tcp.bat " +
                    processEntity.getSourceIp() + " " +
                    processEntity.getSourcePort()+ " " +
                    processEntity.getDistIp()+ " " +
                    processEntity.getDistPort()+ " " +
                    $5;
        } else {
            command = StringContext.systemPath + "/script/del_nat_tcp.sh " +
                    processEntity.getSourceIp() + " " +
                    processEntity.getSourcePort()+ " " +
                    processEntity.getDistIp()+ " " +
                    processEntity.getDistPort()+ " " +
                    $5;
        }
        proc.exec(command);
        if (proc.getResultCode() != -1) {
            if(!proc.getErrorOutput().contains("error")&&!proc.getErrorOutput().contains("Error")){
                return true;
            } else {
                logger.equals(proc.getErrorOutput());
            }
        }
        return false;
    }


    /**
     *
     * @param processEntity
     * @return
     */
    public static boolean add_nat_udp(ProcessEntity processEntity,String $5) {
        Proc proc = new Proc();
        String command = null;
        logger.info(
                "添加udp映射 sourceIp:"+processEntity.getSourceIp()
                +",sourcePort:"+processEntity.getSourcePort()
                +",distIp:"+processEntity.getDistIp()
                +",distPort:"+processEntity.getDistPort()
                +",pre_post:"+$5
        );
        if (OSInfo.getOSInfo().isWin()) {
            command = StringContext.systemPath + "/script/add_nat_udp.bat " +
                    processEntity.getSourceIp() + " " +
                    processEntity.getSourcePort()+ " " +
                    processEntity.getDistIp()+ " " +
                    processEntity.getDistPort()+ " " +
                    $5;
        } else {
            command = StringContext.systemPath + "/script/add_nat_udp.sh " +
                    processEntity.getSourceIp() + " " +
                    processEntity.getSourcePort()+ " " +
                    processEntity.getDistIp()+ " " +
                    processEntity.getDistPort()+ " " +
                    $5;
        }
        proc.exec(command);
        if (proc.getResultCode() != -1) {
            if(!proc.getErrorOutput().contains("error")&&!proc.getErrorOutput().contains("Error")){
                return true;
            } else {
                logger.equals(proc.getErrorOutput());
            }
        }
        return false;
    }


    /**
     *
     * @param processEntity
     * @return
     */
    public static boolean del_nat_udp(ProcessEntity processEntity,String $5) {
        Proc proc = new Proc();
        String command = null;
        logger.info(
                "删除udp映射 sourceIp:"+processEntity.getSourceIp()
                        +",sourcePort:"+processEntity.getSourcePort()
                        +",distIp:"+processEntity.getDistIp()
                        +",distPort:"+processEntity.getDistPort()
                        +",pre_post:"+$5
        );
        if (OSInfo.getOSInfo().isWin()) {
            command = StringContext.systemPath + "/script/del_nat_udp.bat " +
                    processEntity.getSourceIp() + " " +
                    processEntity.getSourcePort()+ " " +
                    processEntity.getDistIp()+ " " +
                    processEntity.getDistPort()+ " " +
                    $5;
        } else {
            command = StringContext.systemPath + "/script/del_nat_udp.sh " +
                    processEntity.getSourceIp() + " " +
                    processEntity.getSourcePort()+ " " +
                    processEntity.getDistIp()+ " " +
                    processEntity.getDistPort()+ " " +
                    $5;
        }
        proc.exec(command);
        if (proc.getResultCode() != -1) {
            if(!proc.getErrorOutput().contains("error")&&!proc.getErrorOutput().contains("Error")){
                return true;
            } else {
                logger.equals(proc.getErrorOutput());
            }
        }
        return false;
    }



    public static void enable_ipforward() {
        Proc proc = new Proc();
        String command = null;
        logger.info(
                "执行 echo 1 > /proc/sys/net/ipv4/ip_forward"
        );
        if (OSInfo.getOSInfo().isWin()) {
            command = "echo 1 > /proc/sys/net/ipv4/ip_forward";
        } else {
            command = "echo 1 > /proc/sys/net/ipv4/ip_forward";
        }
        proc.exec(command);
    }
}
