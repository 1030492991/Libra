package cn.coolhao.app.utils.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 获取日志logger
 * @Author: 谭浩
 * @Date: 2019/5/23 13:25
 */
public class logUtil {
    /**
     * @Description: 获取日志logger
     * @Author: 谭浩
     * @Date: 2019/5/23 13:26
     * @Param: []
     * @Return: org.slf4j.Logger
     */
    public static Logger getLog() {
        return LoggerFactory.getLogger(logEnum.BUSSINESS.getCategory());
    }
}
