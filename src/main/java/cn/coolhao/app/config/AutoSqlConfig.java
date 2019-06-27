package cn.coolhao.app.config;

import cn.coolhao.app.utils.Log.logUtil;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

/**
 * @Description:
 * @Author: 谭浩
 * @Date: 2019/5/27 8:57
 */
public class AutoSqlConfig {

    private static final Logger log=logUtil.getLog();


    public  void sqlmain() throws SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/story?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT&useSSL=false","root","root");
            System.out.println("成功!");
            log.info("成功！");
        }catch (ClassNotFoundException e) {
            System.out.println("运行坏境异常");
            log.info("运行坏境异常");
//            e.printStackTrace();
        } catch (SQLException e) {
//            e.printStackTrace();
            if (e instanceof SQLSyntaxErrorException){
                System.out.println(e.getErrorCode());
                if (e.getErrorCode()==1049){
                    System.out.println("数据库不存在！");
                    //TODO 生成数据库
                    Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/story1?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT&useSSL=false","root","root");

                }
            }
            System.out.println(e);
            log.info("sql异常={}",e);
        }
    }
}
