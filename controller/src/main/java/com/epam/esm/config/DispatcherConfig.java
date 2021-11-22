package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.esm")
public class DispatcherConfig implements WebMvcConfigurer {
  private static final String PREFIX = "/WEB-INF/views/";
  private static final String SUFFIX = ".jsp";

  private static final String RESOURCES_HANDLERS = "/resources/**";
  private static final String RESOURCES_LOCATION = "/resources/";

  @Override
  public void configureViewResolvers(ViewResolverRegistry viewResolver) {
    viewResolver.jsp(PREFIX, SUFFIX);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(RESOURCES_HANDLERS).addResourceLocations(RESOURCES_LOCATION);
  }
}
