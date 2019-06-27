package cn.coolhao.app.controller;

import cn.coolhao.app.controller.note.DateFormat;
import cn.coolhao.app.pojo.Admin;
import cn.coolhao.app.utils.Log.logUtil;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
//import java.text.SimpleDateFormat;

/**
 * @Description: 拦截请求进行分模块处理及404页面跳转
 * @Author: 谭浩
 * @Date: 2019/5/23 11:55
 */
//描述一个切面类，定义切面类的时候需要打上这个注解
@Aspect
//引入到spring 容器中去
@Component
public class BaseController {
    //日志
    private static final Logger log= logUtil.getLog();

    /**
     * @Description: 定义一个切入点
     * @Return: void
     */
    @Pointcut("execution(public * cn.coolhao.app.controller.*.*.*(..))")
    public void cutDot(){}
    /**
     * 自定义注解切入点
     * @Description: 自定义注解切入点
     * @Author: 谭浩
     * @Date: 2019/5/24 18:14
     * @Param: []
     * @Return: void
     */
    @Pointcut("@annotation(cn.coolhao.app.controller.note.DateFormat)")
    public void cutDate(){}
    /**
     * 自定义注解切面
     * @Description: 自定义注解切面  @Before 在方法运行前执行  @After()在方法运行后 返回前执行
     * @Author: 谭浩
     * @Date: 2019/5/24 18:13
     * @Param: [joinPoint]
     * @Return: void
     */
    @Before("cutDate()")
    public void beforePointcut(JoinPoint joinPoint) {
        MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DateFormat annotation = method.getAnnotation(DateFormat.class);
        String format = annotation.value();
        log.info("自定义注解值={}",format);
        //TODO 待学习 自定义注解进行方法操作

//        SimpleDateFormat dateformat = new SimpleDateFormat(format);
//        System.out.println(method);
//        System.out.println("准备"+value);
    }

    /**
     * @Description: 用于拦截需要登陆的路由
     * @Author: 谭浩
     * @Date: 2019/5/23 13:02
     * @Param: []
     * @Return: void
     */
    @Before("cutDot();")
    public void autoGroup(JoinPoint joinPoint) {
        ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request= attributes.getRequest();
        //获取url
        log.info("url={}",request.getRequestURI());
        //获取请求方式
        log.info("method={}",request.getMethod());
        //获取请求Ip
        log.info("ip={}",request.getRemoteAddr());
        //获取类方法（路由）
        log.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+'.'+joinPoint.getSignature().getName());
        //获取参数
        log.info("args={}",joinPoint.getArgs());
        //获取session数据
        //抓取前台http头传过来的前台缓存管理员or 用户
//        String stringAdmin=request.getHeader("admin");
//        Admin admin= JSON.parseObject(stringAdmin,Admin.class);
//         log.info("admin={}",admin);
         //进行权限操作
    }
    /**
     * @Description: 环绕通知：包围一个连接点的通知，如方法调用。这是最强大的一种通知类型。环绕通知可以在方法调用前后完成自定义的行为。      *它也会选择是否继续执行连接点或直接返回它自己的返回值或抛出异常来结束执行。环绕通知最麻烦，也最强大，其是一个对方法的环绕，
     *具体方法会通过代理传递到切面中去，切面中可选择执行方法与否，执行方法几次等。
     * @Author: 谭浩
     * @Date: 2019/5/23 13:07
     * @Param: []
     * @Return: void
     */
//    @Around("cutDot();")
//    public void resultAutoGroup(){
//
//    }
    /**
     * 方法执行完之后的回调
     * @Description: @AfterReturning 方法运行完后返回
     * @Author: 谭浩
     * @Date: 2019/5/24 18:16
     * @Param: [object]
     * @Return: void
     */
    @AfterReturning(returning = "object",pointcut = "cutDot();")
    public void AutoGroupReturning(Object object){
        log.info("returning={}",object.toString());
    }
}
