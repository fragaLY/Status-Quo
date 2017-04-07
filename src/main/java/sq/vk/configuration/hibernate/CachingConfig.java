package sq.vk.configuration.hibernate;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CachingConfig {

    private static final String PATH_EHCACHE = "cache/ehcache.xml";
    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cm) {
        return new EhCacheCacheManager(ehcacheManagerFactory().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehcacheManagerFactory() {

        EhCacheManagerFactoryBean ehCacheFactoryBean = new EhCacheManagerFactoryBean();

        ehCacheFactoryBean.setConfigLocation(new ClassPathResource(PATH_EHCACHE));

        return ehCacheFactoryBean;
    }
}