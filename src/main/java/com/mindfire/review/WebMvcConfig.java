package com.mindfire.review;

import java.io.File;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
class WebMvcConfig extends WebMvcConfigurationSupport {

    private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";
    private static final String VIEWS = "/WEB-INF/views/";

    private static final String RESOURCES_LOCATION = "/resources/";
    private static final String RESOURCES_HANDLER = RESOURCES_LOCATION + "**";

    @Autowired
    private ServletContext servletContext;

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping requestMappingHandlerMapping = super.requestMappingHandlerMapping();
        requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
        requestMappingHandlerMapping.setUseTrailingSlashMatch(false);
        return requestMappingHandlerMapping;
    }

//    @Bean(name = "messageSource")
//    public MessageSource messageSource() {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename(MESSAGE_SOURCE);
//        messageSource.setCacheSeconds(5);
//        return messageSource;
//    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix(VIEWS);
        viewResolver.setSuffix(".jsp");
        return viewResolver;

    }

    @Bean
    public Filter httpPutFormFilter(){
        HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
        filter.setServletContext(servletContext);
        filter.setMethodParam("method");
        return filter;
    }

   
    @Bean
    public CommonsMultipartResolver multipartResolver(){
    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    	multipartResolver.setMaxUploadSize(26214400L);
    	return multipartResolver;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	 String rootPath = System.getProperty("user.home");
//         String imagePath = "file:"+rootPath + File.separator + "tmpFiles/";
    	 String imagePath = "file:"+rootPath + File.separator + "Wotkspace/git/uploads/";
         System.out.println(imagePath);
         registry.addResourceHandler("/resources/**").addResourceLocations("resources/");
         registry.addResourceHandler("/Wotkspace/git/uploads/**").addResourceLocations(imagePath);
         
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * Handles favicon.ico requests assuring no <code>404 Not Found</code> error is returned.
     */
//    @Controller
//    static class FaviconController {
//        @RequestMapping("favicon.ico")
//        String favicon() {
//            return "forward:/resources/images/favicon.ico";
//        }
//    }
}
