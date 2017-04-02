package sq.vk.configuration.mvc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sq.vk.configuration.hibernate.CachingConfig;
import sq.vk.configuration.hibernate.HibernateConfiguration;

/**
 * Created by Vadzim Kavalkou on 23.03.2016.
 */
@Configuration
@ComponentScan(basePackages = {"sq.vk"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
@Import({HibernateConfiguration.class, CachingConfig.class})
public class RootConfig {
}