package com.importsource.cloud.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.ContextLifecycleFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.filters.FilterRegistry;
import com.netflix.zuul.http.ZuulServlet;
import com.netflix.zuul.monitoring.MonitoringHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

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
public class ZuulDemoApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ZuulDemoApplication.class).web(true).run(args);
	}

	@Component
	public static class MyCommandLineRunner implements CommandLineRunner {
		public void run(String... args) throws Exception {
			MonitoringHelper.initMocks();
			initJavaFilters();
		}

		private void initJavaFilters() {
			final FilterRegistry r = FilterRegistry.instance();

			r.put("javaPreFilter", new ZuulFilter() {
				public int filterOrder() {
					return 50000;
				}
				public String filterType() {
					return "pre";
				}
				public boolean shouldFilter() {
					return true;
				}
				public Object run() {
					System.out.println("running PRE");
					RequestContext.getCurrentContext().set("name", "hezf");
					return null;
				}
			});

			r.put("javaRoutingFilter", new ZuulFilter() {
				@Override
				public int filterOrder() {
					return 50000;
				}

				public String filterType() {
					return "route";
				}

				public boolean shouldFilter() {
					return true;
				}

				public Object run() {
					System.out.println("running ROUTE");
					try {
						RequestContext.getCurrentContext().getResponse().sendRedirect("http://www.zbj.com");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}
			});

			r.put("javaPostFilter", new ZuulFilter() {
				public int filterOrder() {
					return 50000;
				}

				public String filterType() {
					return "post";
				}

				public boolean shouldFilter() {
					return true;
				}

				public Object run() {
					System.out.println("running POST");
					System.out.println(RequestContext.getCurrentContext().get("name").toString());
					return null;
				}

			});

		}

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


