package co.lq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import co.lq.utils.SpringContextHolder;

/**
 * @author billy
 * @date 2019/10/1 9:20:19
 */
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@MapperScan({ "co.lq.modules.*.mapper" })
public class ApiRun {

    public static void main(String[] args) {
        SpringApplication.run(ApiRun.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
