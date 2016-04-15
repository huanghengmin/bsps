package com.hzih.ssl.core.util;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-9-27
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class ZdUser {
    /*是否登陆  0 离线 1 登陆*/
    private  String state;
    /*终端用户的姓名*/
    private String name;
    /*终端用户所在部门*/
    private String dept;
    /*终端证书序列号*/
    private String crtnumber;
    /*终端ip地址*/
    private String sourceIp;
    /*终端访问资源的ip地址*/
    private String targetIp;
    /*终端登录的时间*/
    private String loginTime;
    /*终端获取数据时间*/
    private String lastTime;
    /*终端登录的时长*/
    private String duration;
    /*终端访问的流量*/
    private int inFlow;
    private int outFlow;
    /*终端类型（pc机，手机终端）*/
    private String type;
    /*终端地理位子（手机终端）*/
    private String location;
     /*是否终端地理位子（手机终端）*/
    private String isObtainLocation;
    /*终端截屏*/
    private String viewvpn;

    public String getIsObtainLocation() {
        return isObtainLocation;
    }

    public void setIsObtainLocation(String isobtainLocation) {
        isObtainLocation = isobtainLocation;
    }

    public String getViewvpn() {
        return viewvpn;
    }

    public void setViewvpn(String viewvpn) {
        this.viewvpn = viewvpn;
    }

    public int getInFlow() {
        return inFlow;
    }

    public void setInFlow(int inFlow) {
        this.inFlow = inFlow;
    }

    public int getOutFlow() {
        return outFlow;
    }

    public void setOutFlow(int outFlow) {
        this.outFlow = outFlow;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setCrtnumber(String crtnumber) {
        this.crtnumber = crtnumber;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getTargetIp() {
        return targetIp;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getName() {

        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getCrtnumber() {
        return crtnumber;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public String getDuration() {
        return duration;
    }


    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }

    @Override
    public boolean equals(Object obj) {
        boolean t=false;
        ZdUser user = (ZdUser)obj;
        if(user.getName().equals(this.getName())&&user.getSourceIp().equals(this.getSourceIp())){
            t=true;
        }
        return t;    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
