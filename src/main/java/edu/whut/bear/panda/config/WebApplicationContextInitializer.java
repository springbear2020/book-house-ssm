package edu.whut.bear.panda.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * WebApplicationContext initialization config class：replace the web.xml file
 *
 * @author Spring-_-Bear
 * @datetime 2022/4/26 19:36
 */
public class WebApplicationContextInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * Config class of Spring
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    /**
     * Config class of MVC
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMVCConfig.class};
    }

    /**
     * Specify the url pattern of the dispatcher servlet,
     * use "/" to except the .jsp request
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * Filter
     */
    @Override
    protected Filter[] getServletFilters() {
        // Encoding filter to resolve the Chinese
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter("UTF-8", true);
        // Method filter the support the extend method like PUT, DELETE and so on
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{characterEncodingFilter, hiddenHttpMethodFilter};
    }
}
