package cn.coolhao.app.pojo;

//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Description: 管理员实体类
 * @Author: 谭浩
 * @Date: 2019/5/23 11:56
 */
public class Admin {
    private Integer id;

    private String adminName;

    private String phone;
    //  不显示该字段
//    @JsonIgnore()
    private String pwd;

    private String salt;

    private String opwd;

    private String osalt;

    private String token;

//    private String redisid;


    private Integer isSuper;
    //时间转换
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", locale = "zh", timezone = "GMT+8")
    private Integer addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getOpwd() {
        return opwd;
    }

    public void setOpwd(String opwd) {
        this.opwd = opwd;
    }

    public String getOsalt() {
        return osalt;
    }

    public void setOsalt(String osalt) {
        this.osalt = osalt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Integer isSuper) {
        this.isSuper = isSuper;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

//    public String getRedisid() {
//        return redisid;
//    }
//
//    public void setRedisid(String redisid) {
//        this.redisid = redisid;
//    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", adminName='" + adminName + '\'' +
                ", phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", salt='" + salt + '\'' +
                ", opwd='" + opwd + '\'' +
                ", osalt='" + osalt + '\'' +
                ", token='" + token + '\'' +
                ", isSuper=" + isSuper +
                ", addTime=" + addTime +
                '}';
    }
}
