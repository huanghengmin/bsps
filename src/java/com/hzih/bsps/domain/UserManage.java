package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-21
 * Time: 下午1:55
 * To change this template use File | Settings | File Templates.
 */
public class UserManage {

    private int id;
    private String cacn;
    private String province;
    private String city;
    private String department;
    private String policestation;
    private String email;
    private String tel;
    private String address;
    private String idcard;
    private String description;

    public UserManage() {
    }

    public UserManage(String cacn, String province, String city, String department, String policestation, String email, String tel, String address, String idcard, String description) {
        this.cacn = cacn;
        this.province = province;
        this.city = city;
        this.department = department;
        this.policestation = policestation;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.idcard = idcard;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getCacn() {
        return cacn;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDepartment() {
        return department;
    }

    public String getPolicestation() {
        return policestation;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getAddress() {
        return address;
    }

    public String getIdcard() {
        return idcard;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCacn(String cacn) {
        this.cacn = cacn;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPolicestation(String policestation) {
        this.policestation = policestation;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
