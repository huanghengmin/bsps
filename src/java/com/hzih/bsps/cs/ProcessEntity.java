package com.hzih.bsps.cs;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-29
 * Time: 上午10:20
 * To change this template use File | Settings | File Templates.
 */
public class ProcessEntity {
    /**
     * 当做任务号task
     */
    private int id ;
    /**
     * iptables
     */
    private String task;
    /**
     * 是否正在运行
     */
    private int flagRun;
    /**
     * tcp or udp
     */
    private String type ;
    private String sourceIp;
    private String sourcePort;
    private String distIp;
    private String distPort;
    /**
     * 是否默认启动
     */
    private int isRun;
    private String describe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getFlagRun() {
        return flagRun;
    }

    public void setFlagRun(int flagRun) {
        this.flagRun = flagRun;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getDistIp() {
        return distIp;
    }

    public void setDistIp(String distIp) {
        this.distIp = distIp;
    }

    public String getDistPort() {
        return distPort;
    }

    public void setDistPort(String distPort) {
        this.distPort = distPort;
    }

    public int getRun() {
        return isRun;
    }

    public void setRun(int run) {
        isRun = run;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
