package priv.gaozhe.transactionmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 交易管理系统主启动类
 * 功能配置：
 * 1. @SpringBootApplication 启用自动配置
 * 2. @EnableCaching 启用Spring缓存机制
 * 3. 组件扫描基础包配置
 */
@SpringBootApplication
@EnableCaching
public class TransactionManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionManagementApplication.class, args);
    }
}
