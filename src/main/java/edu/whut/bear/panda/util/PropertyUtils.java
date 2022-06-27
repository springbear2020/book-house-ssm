package edu.whut.bear.panda.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 09:04 Monday
 */
@Data
@Component
@PropertySource("classpath:properties/panda.properties")
public class PropertyUtils {

    /**
     * Panda config data
     */
    @Value("${panda.bookPageSize}")
    private Integer pageSize;
    @Value("${panda.bookNavigationPages}")
    private Integer bookNavigationPages;
    @Value("${panda.recordPageSize}")
    private Integer recordPageSize;
    @Value("${panda.recordNavigationPages}")
    private Integer recordNavigationPages;
    @Value("${panda.openIpParse}")
    private Boolean openIpParse;
}
