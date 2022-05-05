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
public class PandaUtils {
    /**
     * Panda config data
     */
    @Value("${panda.bookPageSize}")
    private Integer pageSize;
    @Value("${panda.navigationPages}")
    private Integer navigationPages;
}
