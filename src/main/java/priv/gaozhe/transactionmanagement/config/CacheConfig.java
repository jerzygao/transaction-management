package priv.gaozhe.transactionmanagement.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public KeyGenerator pageKeyGenerator() {
        return (target, method, params) -> {
            PageableParam param = new PageableParam(
                (int) params[0], 
                (int) params[1], 
                (boolean) params[2]
            );
            return new SimpleKey(param);
        };
    }
    
    private record PageableParam(int page, int size, boolean ascending) {}
}