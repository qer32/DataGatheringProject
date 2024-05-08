package com.example.ProjectDemo;

import com.example.ProjectDemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;
import javax.annotation.PostConstruct;

@SpringBootApplication
class YourApplication {

    @Autowired
    private PersonService personService;

    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
    }

    // 使用 @PostConstruct 注解确保在依赖注入后立即执行 init() 方法
    @PostConstruct
    public void init() {
        // 使用 StopWatch 来测量操作执行所需的时间
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("Starting initialization process...");

        // 执行初始化操作
        personService.performCrudOperations();

        // 测量并输出初始化过程所需的时间
        stopWatch.stop();
        System.out.println("Initialization process finished in " + stopWatch.getTotalTimeMillis() + " ms");
    }
}