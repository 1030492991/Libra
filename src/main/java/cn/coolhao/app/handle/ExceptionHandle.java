package cn.coolhao.app.handle;

import cn.coolhao.app.excption.WrongException;
import cn.coolhao.app.pojo.JsonResult;
import cn.coolhao.app.utils.Log.logUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLSyntaxErrorException;


/**
 * @Description: 异常捕捉
 * @Author: 谭浩
 * @Date: 2019/5/24 14:34
 */
@ControllerAdvice
public class ExceptionHandle {
    private static final Logger log=logUtil.getLog();
    /**
     * 统一异常处理
     * @Description: 统一异常处理
     * @Author: 谭浩
     * @Date: 2019/5/24 15:10
     * @Param: [e]
     * @Return: cn.coolhao.app.pojo.JsonResult
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult handle(Exception e){
        if (e instanceof WrongException){
            WrongException wrongException=(WrongException) e;
            return JsonResult.errorMsg(wrongException.getCode(),wrongException.getMessage());
        }else {
            if (e instanceof MyBatisSystemException){
                final Throwable cause = e.getCause();
//                return e.getCause();
//                log.info(cause);
                log.info("mybatis异常={}",e.toString());
                if (cause instanceof PersistenceException){
//                    final Throwable cause1 = cause.getCause();
//                    log.info("PersistenceException异常={}",cause1.toString());
//                    System.out.println("------------------------------------------------------------------");
//                    if (cause1 instanceof SQLSyntaxErrorException){
//                        final Throwable cause2 = cause1.getCause();
//                        log.info("SQLSyntaxErrorException={}",cause2.getMessage());
//                    }
                    //sql异常处理
                }
            }
            //捕捉数据库异常
//            if (e instanceof SQLSyntaxErrorException){
//                log.info("【系统异常】={}",e);
//                return null;
//            }else {
                return JsonResult.error();
//            }
//            log.info("【系统异常】={}",e.fillInStackTrace().toString());
        }
    }
}
