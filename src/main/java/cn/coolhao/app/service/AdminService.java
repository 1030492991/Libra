package cn.coolhao.app.service;

import cn.coolhao.app.pojo.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 备注
 * @Author: 谭浩
 * @Date: 2019/5/23 13:34
 */
public interface AdminService {
    Integer getAdmin(String adminName);
    int updateToken(String token,Integer id);
    Admin getInfo(Integer id);
    List<Admin> getAll();
    int insertAdmin(Admin admin);
    int deleteAdmin(@Param("id") Integer id);
}
