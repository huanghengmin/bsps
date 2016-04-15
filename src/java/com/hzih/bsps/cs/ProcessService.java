package com.hzih.bsps.cs;

import org.apache.log4j.Logger;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-29
 * Time: 下午1:46
 * To change this template use File | Settings | File Templates.
 */
public class ProcessService {
    private static Logger logger = Logger.getLogger(ProcessService.class);
    private LinkedList<ProcessEntity> portEntities;
    private boolean isRun;
    private String command = "add";

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public ProcessService(LinkedList<ProcessEntity> portEntities) {
        this.portEntities = portEntities;
    }

    public ProcessService() {
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public void processEntity() {
        if (portEntities.size() > 0) {
            if ("clear".equals(command)) {
                for (int i = 0; i < portEntities.size(); i++) {
                    ProcessEntity portEntity = portEntities.get(i);
                    if (portEntity.getType().equals("tcp")) {
//                        TcpShellUtils.del(portEntity.getSourceIp(), portEntity.getSourcePort(), portEntity.getDistIp(), portEntity.getDistPort());
//                        $IPTABLES -t nat -A PREROUTING --dst $1 -p tcp -m tcp --dport $2 -j DNAT --to-destination $3:$4
//                        $IPTABLES -t nat -A POSTROUTING --dst $3 -p tcp -m tcp  --dport $4 -j SNAT --to-source $1
//                        $IPTABLES -t nat -A OUTPUT --dst $1 -p tcp -m tcp --dport $2 -j DNAT --to-destination $3:$4

                        TcpProcess tcpPreProcess = new TcpProcess();
                        tcpPreProcess.clearPreRouting(portEntity.getSourceIp(),portEntity.getSourcePort(),portEntity.getDistIp(),portEntity.getDistPort());
                        tcpPreProcess.start();

                        TcpProcess tcpPostProcess = new TcpProcess();
                        tcpPostProcess.clearPostRouting(portEntity.getDistIp(),portEntity.getSourceIp());
                        tcpPostProcess.start();
                        /**
                         * update tcpEntity status
                         */
                    } else if (portEntity.getType().equals("udp")) {
//                        UdpShellUtils.del(portEntity.getSourceIp(), portEntity.getSourcePort(), portEntity.getDistIp(), portEntity.getDistPort());
//                        $IPTABLES -t nat -A PREROUTING --dst $1 -p udp -m udp --dport $2 -j DNAT --to-destination $3:$4
//                        $IPTABLES -t nat -A POSTROUTING --dst $3 -p udp -m udp  --dport $4 -j SNAT --to-source $1
//                        $IPTABLES -t nat -A OUTPUT --dst $1 -p udp -m udp --dport $2 -j DNAT --to-destination $3:$4
                        UdpProcess udpPreProcess = new UdpProcess();
                        udpPreProcess.clearPreRouting(portEntity.getSourceIp(),portEntity.getSourcePort(),portEntity.getDistIp(),portEntity.getDistPort());
                        udpPreProcess.start();

                        UdpProcess udpPostProcess = new UdpProcess();
                        udpPostProcess.clearPostRouting(portEntity.getDistIp(),portEntity.getSourceIp());
                        udpPostProcess.start();
                        /**
                         * update udpEntity status
                         */
                    }
                }
            } else {
                for (int i = 0; i < portEntities.size(); i++) {
                    ProcessEntity portEntity = portEntities.get(i);
                    if (portEntity.getType().equals("tcp")) {
//                        TcpShellUtils.add(portEntity.getSourceIp(), portEntity.getSourcePort(), portEntity.getDistIp(), portEntity.getDistPort());
                        TcpProcess initPreProcess = new TcpProcess();
                        initPreProcess.initPreRouting(portEntity.getSourceIp(),portEntity.getSourcePort(),portEntity.getDistIp(),portEntity.getDistPort());
                        initPreProcess.start();

                        TcpProcess initPostProcess = new TcpProcess();
                        initPostProcess.initPostRouting(portEntity.getDistIp(),portEntity.getSourceIp());
                        initPostProcess.start();
                        /**
                         * update tcpEntity status
                         */
                    } else if (portEntity.getType().equals("udp")) {
//                        UdpShellUtils.add(portEntity.getSourceIp(), portEntity.getSourcePort(), portEntity.getDistIp(), portEntity.getDistPort());
                        UdpProcess udpPreProcess = new UdpProcess();
                        udpPreProcess.initPreRouting(portEntity.getSourceIp(),portEntity.getSourcePort(),portEntity.getDistIp(),portEntity.getDistPort());
                        udpPreProcess.start();

                        UdpProcess udpPostProcess = new UdpProcess();
                        udpPostProcess.initPostRouting(portEntity.getDistIp(),portEntity.getSourceIp());
                        udpPostProcess.start();
                        /**
                         * update udpEntity status
                         */
                    }
                }
            }
        }
    }

    /**
     * 初始化iptables配置
     */
    public void init() {
        InitProcess init = new InitProcess();
        init.initClear();
        init.start();
    }

    /**
     * 清除单条
     *
     * @param portEntity
     */
    public void clearPortEntity(ProcessEntity portEntity) {
        if (portEntity.getType().equals("tcp")) {
//            TcpShellUtils.del(portEntity.getSourceIp(), portEntity.getSourcePort(), portEntity.getDistIp(), portEntity.getDistPort());
            TcpProcess tcpPreProcess = new TcpProcess();
            tcpPreProcess.clearPreRouting(portEntity.getSourceIp(),portEntity.getSourcePort(),portEntity.getDistIp(),portEntity.getDistPort());
            tcpPreProcess.start();

            TcpProcess tcpPostProcess = new TcpProcess();
            tcpPostProcess.clearPostRouting(portEntity.getDistIp(),portEntity.getSourceIp());
            tcpPostProcess.start();
            /**
             * update tcpEntity status
             */
        } else if (portEntity.getType().equals("udp")) {
//            UdpShellUtils.del(portEntity.getSourceIp(), portEntity.getSourcePort(), portEntity.getDistIp(), portEntity.getDistPort());
            UdpProcess udpPreProcess = new UdpProcess();
            udpPreProcess.clearPreRouting(portEntity.getSourceIp(),portEntity.getSourcePort(),portEntity.getDistIp(),portEntity.getDistPort());
            udpPreProcess.start();

            UdpProcess udpPostProcess = new UdpProcess();
            udpPostProcess.clearPostRouting(portEntity.getDistIp(),portEntity.getSourceIp());
            udpPostProcess.start();
            /**
             * update udpEntity status
             */
        }
    }


    /**
     * 清除单条
     *
     * @param portEntity
     */
    public void addPortEntity(ProcessEntity portEntity) {
        if (portEntity.getType().equals("tcp")) {
//            TcpShellUtils.add(portEntity.getSourceIp(), portEntity.getSourcePort(), portEntity.getDistIp(), portEntity.getDistPort());
            TcpProcess initPreProcess = new TcpProcess();
            initPreProcess.initPreRouting(portEntity.getSourceIp(),portEntity.getSourcePort(),portEntity.getDistIp(),portEntity.getDistPort());
            initPreProcess.start();

            TcpProcess initPostProcess = new TcpProcess();
            initPostProcess.initPostRouting(portEntity.getDistIp(),portEntity.getSourceIp());
            initPostProcess.start();
            /**
             * update tcpEntity status
             */
        } else if (portEntity.getType().equals("udp")) {
//            UdpShellUtils.add(portEntity.getSourceIp(), portEntity.getSourcePort(), portEntity.getDistIp(), portEntity.getDistPort());
            UdpProcess udpPreProcess = new UdpProcess();
            udpPreProcess.initPreRouting(portEntity.getSourceIp(),portEntity.getSourcePort(),portEntity.getDistIp(),portEntity.getDistPort());
            udpPreProcess.start();

            UdpProcess udpPostProcess = new UdpProcess();
            udpPostProcess.initPostRouting(portEntity.getDistIp(),portEntity.getSourceIp());
            udpPostProcess.start();
            /**
             * update udpEntity status
             */
        }
    }


}
