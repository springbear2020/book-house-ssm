package edu.whut.bear.panda.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import edu.whut.bear.panda.util.PropertyUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/26 19:41
 */
@Configuration
@MapperScan("edu.whut.bear.panda.dao")
@ComponentScan(basePackages = {"edu.whut.bear.panda.service", "edu.whut.bear.panda.dao", "edu.whut.bear.panda.pojo", "edu.whut.bear.panda.util"})
public class SpringConfig {
    @Autowired
    private PropertyUtils propertyUtils;

    /**
     * Druid data source
     */
    @Bean
    public DruidDataSource getDruidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(propertyUtils.getDriverClass());
        druidDataSource.setUrl(propertyUtils.getUrl());
        druidDataSource.setUsername(propertyUtils.getUsername());
        druidDataSource.setPassword(propertyUtils.getPassword());
        return druidDataSource;
    }

    /**
     * MyBatis Sql Session Factory Bean
     */
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // Reference druid data source
        sqlSessionFactoryBean.setDataSource(getDruidDataSource());
        // Set an alias for the pojo class
        sqlSessionFactoryBean.setTypeAliasesPackage("edu.whut.bear.panda.pojo");
        // Set the relevant mapper file(.xml) location
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        // Set the pagination plugin
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{getPageInterceptor()});
        // Enable CamelCase auto convert between pojo object and the field of the database table
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);
        return sqlSessionFactoryBean;
    }

    /**
     * MyBatis pagination plugin
     *
     * @return PageInterceptor
     */
    @Bean
    public PageInterceptor getPageInterceptor() {
        PageInterceptor pageIntercptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("value", "true");
        pageIntercptor.setProperties(properties);
        return pageIntercptor;
    }
}
