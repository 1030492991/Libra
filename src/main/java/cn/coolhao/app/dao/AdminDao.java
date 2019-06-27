package cn.coolhao.app.dao;

import cn.coolhao.app.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 管理员dao层
 * @Author: 谭浩
 * @Date: 2019/5/23 13:34
 */
@Mapper
@Repository
public interface AdminDao {
    Integer getAdmin(@Param("adminName") String adminName);
    int updateToken(@Param("token") String token,@Param("id") Integer id);
    Admin getInfo(@Param("id") Integer id);
    List<Admin> getAll();
    int insertAdmin(@Param("admin") Admin admin);
    int deleteAdmin(@Param("id") Integer id);
}
