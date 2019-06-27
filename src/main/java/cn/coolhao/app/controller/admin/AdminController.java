package cn.coolhao.app.controller.admin;

import cn.coolhao.app.controller.note.DateFormat;
import cn.coolhao.app.excption.WrongException;
import cn.coolhao.app.pojo.Admin;
import cn.coolhao.app.pojo.JsonResult;
import cn.coolhao.app.service.AdminService;
import cn.coolhao.app.utils.Log.logUtil;
import cn.coolhao.app.utils.Redis;
import cn.coolhao.app.utils.Thick;
import cn.coolhao.app.utils.Verify;
import cn.coolhao.app.utils.Uuid;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import java.Verify.Date;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description: 管理员接口层
 * @Author: 谭浩
 * @Date: 2019/5/23 11:52
 */
@RestController
@RequestMapping(value = "admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private Redis redis;

    private static  Logger log = logUtil.getLog();

    private static JsonResult result = new JsonResult();

    /**
     * 查询所有管理员
     *
     * @throws WrongException
     * @Description: 查询所有管理员
     * @Author: 谭浩
     * @Date: 2019/5/27 8:43
     * @Param: []
     * @Return: cn.coolhao.app.pojo.JsonResult
     */
    @DateFormat(value = "yyyy-MM-dd")
    @RequestMapping(value = "getAll", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResult getInfo() throws Exception {
//        测试
//        log.info("进来getAll");
        log.info("数据wei:" + adminService.getAll());
        List<Admin> admin = adminService.getAll();
        //使用封装好的返回类
        return result.success(admin);
    }

    /**
     * 根据id 查询单个管理员
     * @Description: 查询单个管理员
     * @Author: 谭浩
     * @Date: 2019/5/29 15:03
     * @Param: [admin_id]
     * @Return: cn.coolhao.app.pojo.JsonResult
     */
    @RequestMapping(value = "getAdmin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResult getAdmin(Integer admin_id) {
//        log.info("admin_id={}", admin_id);
        Admin admin = adminService.getInfo(admin_id);
        if (redis.hasKey(admin.getAdminName())){
            redis.del(admin.getAdminName());
            redis.set(admin.getAdminName(), admin);
        }else {
            redis.set(admin.getAdminName(), admin);
        }
        return result.success(admin);
    }

    /**
     * 查询redis中缓存的数据
     * @param adminName
     * @return
     */
    @RequestMapping(value = "getRedisAdmin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResult getAdminInfo(String adminName){
        if (redis.hasKey(adminName)){
            Admin admin=(Admin)redis.get(adminName);
//            log.info("进来controller");
            return JsonResult.success(admin);
        }else {
//            log.info("进来controller");
            return JsonResult.success();
        }
    }

    /**
     * 新增管理员
     * @Description: 新增管理员
     * @Author: 谭浩
     * @Date: 2019/6/3 12:36
     * @Param: [data]
     * @Return: cn.coolhao.app.pojo.JsonResult
     */
    @RequestMapping(value = "insertAdmin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResult insertAdmin(HttpServletRequest request) {
//        log.info("data={}",data);
        //进行json转Admin 对象
        Admin admin = new Admin();
        String adminName=request.getParameter("adminName");
        if (Verify.isNull(adminName)){
            throw new WrongException("管理员用户名不能为空！");
        }else {
            if (!Verify.isNull(adminService.getAdmin(adminName))){
                //判断用户名是否已存在
                throw new WrongException("该用户名已被占用！");
            }
            admin.setAdminName(adminName);
        }
        String pwd=request.getParameter("pwd");
        String pwdTwo=request.getParameter("pwd1");
        //密码验证
        if (Verify.isNull(pwd)) {
            throw new WrongException("请输入登陆密码");
        }else {
            if (!pwd.equals(pwdTwo)){
                throw new WrongException("登陆密码两次输入不一致");
            }else {
                admin.setPwd(pwd);
            }
        }
        //安全密码验证
        String opwd=request.getParameter("opwd");
        String OpwdTwo=request.getParameter("opwd1");
        if (Verify.isNull(opwd)) {
            throw new WrongException("请输入安全a密码");
        }else {
            if (!opwd.equals(OpwdTwo)){
                throw new WrongException("安全密码两次输入不一致");
            }else {
                admin.setOpwd(opwd);
            }
        }
        //判断手机号码
        String phone=request.getParameter("phone");
        if (Verify.isNull(phone)) {
            throw new WrongException("手机号码不能为空！");
        }else {
            if (!Verify.isMobile(phone)) {
                throw new WrongException("手机号码格式错误！");
            }else {
                admin.setPhone(phone);
            }
        }
        //是否为超管
        Integer isSuper=Integer.parseInt(request.getParameter("isSuper"));
        admin.setIsSuper(isSuper);
        //TODO 时间戳
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
//        if (Verify.isNotNull(date)){}
        admin.setAddTime(1);
        //方便后台查看用户密码 所以存入redis
        redis.set(admin.getAdminName(), admin);
        //新增
        int insert_count = adminService.insertAdmin(admin);
        if (insert_count > 0) {
            return JsonResult.success();
        } else {
            throw new WrongException("新增失败");
        }
    }
}
