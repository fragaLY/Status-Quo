package sq.vk.web.configuration.hibernate;

import java.util.Properties;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Vadzim Kavalkou on 23.03.2016.
 *
 * Hibernate configurations.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories({"sq.vk.core.dao.client",
                        "sq.vk.core.dao.statistic",
                        "sq.vk.core.dao.gameinfo"})
@PropertySource("classpath:properties/jdbc.properties")
@ComponentScan({"sq.vk.core",
                "sq.vk.service"})
public class HibernateConfiguration {

  private static final String CREATE_CLIENTS_TABLE = "sql/create-clients.sql";
  private static final String CREATE_GAMEINFO_TABLE = "sql/create-gameinfo.sql";
  private static final String CREATE_STATISTICS_TABLE = "sql/create-statistics.sql";

  private static final String CREATE_STATISTIC_CLIENT_TABLE = "sql/create-statistics-clients.sql";
  private static final String CREATE_STATISTIC_GAMEINFO_TABLE = "sql/create-statistics-gameinfo.sql";

  private static final String INIT_CLIENT_TABLE = "sql/init-clients.sql";
  private static final String INIT_STATISTIC_TABLE = "sql/init-statistics.sql";
  private static final String INIT_GAMEINFO_TABLE = "sql/init-gameinfo.sql";

  private final Environment environment;

  @Autowired
  public HibernateConfiguration(Environment environment) {
    this.environment = environment;
  }

  @Bean(name = "dataSource")
  public DataSource getDataSource() {

    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

    return builder
            .continueOnError(true)
            .setType(EmbeddedDatabaseType.H2)
            .addScript(CREATE_CLIENTS_TABLE)
            .addScript(CREATE_GAMEINFO_TABLE)
            .addScript(CREATE_STATISTICS_TABLE)
            .addScript(CREATE_STATISTIC_CLIENT_TABLE)
            .addScript(CREATE_STATISTIC_GAMEINFO_TABLE)
            .addScript(INIT_CLIENT_TABLE)
            .addScript(INIT_STATISTIC_TABLE)
            .addScript(INIT_GAMEINFO_TABLE)
          .build();
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

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

    LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();

    emfb.setDataSource(getDataSource());
    emfb.setPackagesToScan("sq.vk.core.domain.client",
                           "sq.vk.core.domain.gameinfo",
                           "sq.vk.core.domain.statistic");
    emfb.setJpaVendorAdapter(jpaVendorAdapter());
    emfb.setJpaProperties(getHibernateProperties());

    return emfb;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    return new HibernateJpaVendorAdapter();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {

    JpaTransactionManager transactionManager = new JpaTransactionManager();

    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

    return transactionManager;

  }

}