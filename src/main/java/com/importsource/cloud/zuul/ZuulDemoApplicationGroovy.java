package com.importsource.cloud.zuul;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.ContextLifecycleFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.filters.FilterRegistry;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import com.netflix.zuul.http.ZuulServlet;
import com.netflix.zuul.monitoring.MonitoringHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 你可以通过zuul设置验证、路由、限流
 *
 *
 * @author He Zhuofan
 */
@EnableEurekaClient
@EnableAutoConfiguration
@SpringBootApplication
public class ZuulDemoApplicationGroovy {

	public static void main(String[] args) {
        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        try {
            ///Users/hezhuofan/Downloads/zuuldemo/src
            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            FilterFileManager.init(10,"/Users/hezhuofan/Downloads/zuuldemo/src/groovy/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		new SpringApplicationBuilder(ZuulDemoApplicationGroovy.class).web(true).run(args);
	}



	@Bean
	public ServletRegistrationBean zuulServlet() {
		ServletRegistrationBean servlet = new ServletRegistrationBean(new ZuulServlet());
		servlet.addUrlMappings("/test");
		return servlet;
	}

	@Bean
	public FilterRegistrationBean contextLifecycleFilter() {
		FilterRegistrationBean filter = new FilterRegistrationBean(new ContextLifecycleFilter());
		filter.addUrlPatterns("/*");
		return filter;
	}

}


