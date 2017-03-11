package com.promeritage.annotation.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * 定義spring對於jsp網頁的跟目錄路徑
 * @author Adam
 *
 */
@Configuration
public class ViewResolverConfig {

	/*
	 * jsp路徑位址
	 */
	@Bean
	public UrlBasedViewResolver viewResolver() {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
}
