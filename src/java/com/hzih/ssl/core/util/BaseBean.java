package com.hzih.ssl.core.util;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-9-14
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class BaseBean {
    private String  userid;
    private String  ip;
    private String  ips;
    private int  post;

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }
      @Override
    public boolean equals(Object obj) {
        boolean t=false;
        BaseBean bean = (BaseBean)obj;
          if(bean.getIp().equals(this.getIp())&&bean.getIps().equals(this.getIps())&&bean.getPost()==this.getPost()&&bean.getUserid().equals(this.getUserid())) {
//        if(user.getName().equals(this.getName())&&user.getSourceIp().equals(this.getSourceIp())){
            t=true;
        }
        return t;
    }

    @Override
    public int hashCode() {
        return super.hashCode();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
