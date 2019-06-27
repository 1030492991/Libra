package cn.coolhao.app.service.impl;

import cn.coolhao.app.dao.AdminDao;
import cn.coolhao.app.dao.UserDao;
import cn.coolhao.app.excption.WrongException;
import cn.coolhao.app.pojo.Admin;
import cn.coolhao.app.service.AdminService;
import cn.coolhao.app.utils.Thick;
import cn.coolhao.app.utils.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description: 管理员业务逻辑层
 * @Author: 谭浩
 * @Date: 2019/5/23 13:35
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    /**
     * 根据用户名查询管理员
     * @param adminName
     * @return
     */
    @Override
    public Integer getAdmin(String adminName) {
        return adminDao.getAdmin(adminName);
    }

    /**
     * 修改token
     * @param token
     * @param id
     * @return
     */
    @Override
    public int updateToken(String token, Integer id) {
        return adminDao.updateToken(token,id);
    }

    /**
     * 根据id 查询管理员
     * @param id
     * @return
     */
    @Override
    public Admin getInfo(Integer id) {
        Admin admin=adminDao.getInfo(id);
        admin.setPhone(Thick.decode(admin.getPhone()));
        return admin;
    }

    /**
     * 查询所有管理员
     * @return array admin
     */
    @Override
    public List<Admin> getAll() {
        List<Admin> admin=adminDao.getAll();
        //遍历list集合进行数据修改
        for (Admin item:admin) {
            //电话解密
            if (!item.getPhone().isEmpty()){
                item.setPhone(Thick.decode(item.getPhone()));
            }
            //日期格式转换
//            if (!item.getAddTime().isEmpty()){
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                long lt = new Long(item.getAddTime());
//                Date addTime = new Date(lt);
//                item.setAddTime(simpleDateFormat.format(addTime));
//            }
        }
        if (admin.size()<=0){
            throw new WrongException("管理员不存在!");
        }
        return admin;
    }

    /**
     * @desc 新增管理员
     * @param admin
     * @return
     */
    @Override
    public int insertAdmin(Admin admin) {
        //电话号码加密
        admin.setPhone(Thick.encode(admin.getPhone()));
        //密码MD5加密 生成salt密钥
        String salt = Uuid.generateShortUUID(8);
        admin.setSalt(salt);
        admin.setPwd(Thick.MD5(admin.getPwd(), salt));
        //安全密码MD5加密
        String osalt = Uuid.generateShortUUID(8);
        admin.setOsalt(osalt);
        admin.setOpwd(Thick.MD5(admin.getOpwd(), osalt));
       return adminDao.insertAdmin(admin);
    }

    @Override
    public int deleteAdmin(Integer id) {
        return adminDao.deleteAdmin(id);
    }
}
