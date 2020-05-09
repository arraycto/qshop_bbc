package co.lq.modules.quartz.task;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试用
 *
 * @author billy
 * @date 2019-01-08
 */
@Slf4j
@Component
public class TestTask {

    public void run() {
        log.info("执行成功");
    }

    public void run1(String str) {
        log.info("执行成功，参数为： {}" + str);
    }
}
