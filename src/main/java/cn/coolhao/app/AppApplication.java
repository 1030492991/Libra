package cn.coolhao.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@MapperScan(value = "cn.coolhao.app.dao")
//@EnableAsync
public class AppApplication {
    /**
     * @Description: 主进程
     * @Author: 谭浩
     * @Date: 2019/5/24 11:37
     * @Param: [args]
     * @Return: void
     */
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}
