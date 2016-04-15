package com.hzih.ssl.core.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.spec.KeySpec;

public class Des {

	private String strkey;

	private SecretKey skey=null;

	private String[] algo={"DES/ECB/PKCS5Padding","DES/ECB/NoPadding","DES"};

	public Des(String key){
		strkey=key;
	}

    //生成key
	public void keyGenerating() throws Exception{

		byte[] bkey=strkey.getBytes();

		KeySpec ks = new DESKeySpec(bkey);

		SecretKeyFactory kf = SecretKeyFactory.getInstance(algo[2]);

        skey = kf.generateSecret(ks);

	}
    // 压缩加密
	public byte[] Encripting(String plaintext,int i)throws Exception{

		byte[] bpt=plaintext.getBytes();

		Cipher cf = Cipher.getInstance(algo[i]);

		if(skey==null)this.keyGenerating();

		cf.init(Cipher.ENCRYPT_MODE,skey);

		byte[] bct = cf.doFinal(bpt);

		return bct;

	}
    public byte[] Encripting(byte[]bpt,int i)throws Exception{

		Cipher cf = Cipher.getInstance(algo[i]);

		if(skey==null)this.keyGenerating();

		cf.init(Cipher.ENCRYPT_MODE,skey);

		byte[] bct = cf.doFinal(bpt);

		return bct;

	}
    //解密
	public byte[] Decrypting(byte[] bct,int i)throws Exception{

		Cipher cf = Cipher.getInstance(algo[i]);

		if(skey==null){
			this.keyGenerating();
		}

		cf.init(Cipher.DECRYPT_MODE,skey);

		byte[] bpt = cf.doFinal(bct);

		return bpt; 

	}

    public  byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
	public  String bytes2Hex(byte[] data) {
		if (data==null) {
			return null;
		} 
		else {
			int len = data.length;
			String str = "";
			for (int i=0; i<len; i++) {
				if ((data[i]&0xFF)<16) {
					str = str + "0"+ Integer.toHexString(data[i]&0xFF);
				}
				else {
					str = str+ Integer.toHexString(data[i]&0xFF);
				}
			}
			return str.toUpperCase();
		}
	}
    public static void main(String arg[]){
        Des des=new Des("11111111");
        try {
            byte[] b= des.Encripting("你好吗！",2);
//            System.out.println("加密后:"+new String(b));
            byte[] bt=des.Decrypting(b,2);
//             System.out.println("解密后:"+new String(bt));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}


