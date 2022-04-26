package edu.whut.bear.panda.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/26 20:32
 */
@Data
@Component
@PropertySource("classpath:panda.properties")
@PropertySource("classpath:jdbc.properties")
public class PropertyUtils {
    /**
     * JDBC property
     */
    @Value("${jdbc.driverClass}")
    private String driverClass;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    /**
     * Panda data config property
     */
    @Value("${panda.bookPageSize}")
    private int pageSize;
    @Value("${panda.navigationPages}")
    private int navigationPages;
}
