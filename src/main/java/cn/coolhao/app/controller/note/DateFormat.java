package cn.coolhao.app.controller.note;

//import org.springframework.core.annotation.AliasFor;
//import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 自定义注解
 * @Description: 自定义日期格式转换的注解
 * @Author: 谭浩
 * @Date: 2019/5/24 17:07
 */
@Target({ElementType.METHOD}) //作用范围  方法
@Retention(RetentionPolicy.RUNTIME) //生命周期 代码运行时
@Documented
@Inherited //子类允许继承
public @interface DateFormat {
    //默认参数
    String value() default "";
}
