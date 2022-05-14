package eu.senla.myfirstapp.app.config;

import eu.senla.myfirstapp.app.filter.ContentCachingFilter;
import eu.senla.myfirstapp.app.filter.EncodingFilter;
import javax.servlet.Filter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new EncodingFilter(),
                new ContentCachingFilter()
        };
    }
}
