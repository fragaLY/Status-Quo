package sq.vk.configuration.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Vadzim Kavalkou on 23.03.2016.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:properties/jdbc.properties")
public class HibernateConfiguration {

    private static final String CREATE_CLIENTS_TABLE = "sql/create-clients.sql";
    private static final String INIT_CLIENT_TABLE = "sql/init-clients.sql";

    @Autowired
    private Environment environment;

    @Bean(name = "dataSource")
    public DataSource getDataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase embeddedDatabase = builder
                .continueOnError(true)
                .setType(EmbeddedDatabaseType.H2)
                .addScript(CREATE_CLIENTS_TABLE)
                .addScript(INIT_CLIENT_TABLE)
                .build();

        return embeddedDatabase;
    }

    private Properties getHibernateProperties() {

        Properties properties = new Properties();

        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.generate_statistics", environment.getRequiredProperty("hibernate.generate_statistics"));
        properties.put("hibernate.cache.provider_class", environment.getRequiredProperty("hibernate.cache.provider_class"));
        properties.put("hibernate.cache.use_second_level_cache", environment.getRequiredProperty("hibernate.cache.use_second_level_cache"));

        return properties;
    }

    @Bean(name = "localSessionFactory")
    public LocalSessionFactoryBean getLocalSessionFactory() {

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setHibernateProperties(getHibernateProperties());
        sessionFactory.setPackagesToScan("sq/vk/domain");

        return sessionFactory;
    }

    @Bean(name = "hibernateTxManager")
    @Autowired
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {

        HibernateTransactionManager hibernateTxManager = new HibernateTransactionManager();
        hibernateTxManager.setSessionFactory(sessionFactory);

        return hibernateTxManager;
    }
}