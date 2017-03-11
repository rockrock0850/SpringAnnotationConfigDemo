package com.promeritage.annotation.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@ComponentScan("com.promeritage.annotation.demo")
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({DataSourceConfig.class, MultiLanguageConfig.class, ViewResolverConfig.class, CacheConfig.class})
public class Config extends WebMvcConfigurerAdapter {
	
	@Autowired
	private MultiLanguageConfig multiLanguage;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor);
	}

	@Override
	public Validator getValidator() {
		
		return multiLanguage.validator();
	}

	/*
	 * 配置資源檔案路徑
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

}
