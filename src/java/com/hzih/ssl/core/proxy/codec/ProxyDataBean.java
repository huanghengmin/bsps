package com.hzih.ssl.core.proxy.codec;

import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * 代理数据结构bean
 * Created with IntelliJ IDEA.
 * User: bluesky
 * Date: 12-7-19
 * Time: 上午9:34
 * To change this template use File | Settings | File Templates.
 */
public class ProxyDataBean implements Comparable, Serializable {


    private static Logger logger = Logger.getLogger(ProxyDataBean.class);
    public static byte B_Version = '1';
    private byte version = B_Version;

    /**
     * 目标主机地址
     */
    private byte[] host = new byte[128];
    /**
     * 目标端口
     */
    private int port;

    /**
     * 资源ID
     */
    private byte[] resourceid = new byte[300];

    /**
     * 数据包长度
     */
    private int length;
    /**
     * 数据内容
     */
    private byte[] data = new byte[0];


    private int id;



    public ProxyDataBean() {

    }



    /**
     * 对象转换成byte.
     */
    public byte[] toBytes() {
        byte[] ret = null;
        try {
            byte[] buf = new byte[8 + getLength()];
            byte[] bsum = int32ToBytes(getId());
            byte[] bcur = int32ToBytes(getLength());
            System.arraycopy(bsum, 0, buf, 0, 4);
            System.arraycopy(bcur, 0, buf, 4, 4);
            System.arraycopy(getData(), 0, buf, 8, getLength());
            ret = buf;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String toString() {
        return new String(toBytes());
    }

    /**
     * 由数据解析出相应的对像。
     *
     * @param data
     * @return
     */
    public ProxyDataBean getProxyDataBean(byte[] data) {
        if (data.length < 8) {
            return null;
        }
        ProxyDataBean bean = new ProxyDataBean();
        byte[] idbuf = new byte[4];
        byte[] length = new byte[4];
        System.arraycopy(data, 0, idbuf, 0, 4);
        bean.setId(bytesToInt32(idbuf));
        System.arraycopy(data, 4, length, 0, 4);
        bean.setLength(bytesToInt32(length));
        byte[] datas = new byte[bean.getLength()];
        int len = bean.getLength();
        if (len > (data.length - 8)) {
            len = data.length - 8;
            datas = new byte[len];
        }
        System.arraycopy(data, 8, datas, 0, datas.length);
        bean.setData(datas);
        return bean;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int compareTo(Object o) {
        ProxyDataBean s = (ProxyDataBean) o;
        return getId() > s.getId() ? 1 : (getId() == s.getId() ? 0 : -1);

    }


    /**
     * 将一个整数转换成4字节数组,注意:低位在前,高位在后.因为网络通信不能保证所有客户端都使用相同的CPU,内存存储方式可能不同
     *
     * @param val 要转换的INT值
     * @return 转换后的字节数组
     */
    public static byte[] int32ToBytes(int val) {
        byte[] ret = new byte[4];
        /*for(int i=0;i<4;i++){
              int tmp = val>>>(24-i*8);
              ret[i] = (byte)(tmp&0xff);
          }*/
        int hi = val / (256 * 256);
        int low = val % (256 * 256);
        ret[3] = (byte) (hi / 256);
        ret[2] = (byte) (hi % 256);
        ret[1] = (byte) (low / 256);
        ret[0] = (byte) (low % 256);
        return ret;
    }

    /**
     * 将一个4字节数组转换成一个INT数值,按低位在前,高位在后组织.函数本身不判断字节数组大小,不做安全检查
     *
     * @param val 要转换的字节数组
     * @return 转换后的INT数值
     */
    public static int bytesToInt32(byte[] val) {
        int ret = 0;
        ret = ((val[0] & 0xff) + (val[1] & 0xff) * 256) + ((val[2] & 0xff) + (val[3] & 0xff) * 256) * 256;
        /*for(int i=0;i<4;i++)
          {
              ret = ret << 8;
              ret |= val[i]&0xff;
          }*/
        return ret;
    }

    /**
     * 判断整个包的完整性,有效性
     *
     * @param val 整个包数据
     * @return 有效-TRUE,无效-FALSE
     */
    /* public static boolean isValidData(byte[] val) {
        boolean ret = false;
        int len = 0;
        if (val == null) return false;
        if ((val[0] & 0xff) == 0xAA) {
            switch (val[1] & 0xff) {
                case code_data_arc:
                case code_data:
                    byte[] tmp = new byte[4];
                    System.arraycopy(val, 2, tmp, 0, 4);
                    len = bytesToInt32(tmp);
                    if ((val[0] & 0xff) == 0xAA && (val[6 + len] & 0xff) == 0xFF)
                        ret = true;
                    break;
            }
        }
        return ret;
    }*/

    public static int bytesToUIntInt(byte[] bytes, int index) {
        int accum = 0;
        int i = 1;
        for (int shiftBy = 0; shiftBy < 16; shiftBy += 8) {
            accum |= ((long) (bytes[index + i] & 0xff)) << shiftBy;
            i--;
        }
        return accum;
    }



}
