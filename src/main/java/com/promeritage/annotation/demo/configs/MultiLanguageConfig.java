package com.promeritage.annotation.demo.configs;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

/**
 * 定義spring多國語言相關機制
 * @author Adam
 *
 */
@Configuration
public class MultiLanguageConfig {
	
	/*
	 * 定義多國語言
	 */
	@Bean(name = "localeResolver")
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(new Locale("en"));
		return cookieLocaleResolver;
	}

	@Bean(name = "messageSource")
	public MessageSource configureMessageSource() {
		ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		reloadableResourceBundleMessageSource.setBasename("classpath:properties/messages");
		reloadableResourceBundleMessageSource.setDefaultEncoding("utf-8");
		return reloadableResourceBundleMessageSource;
	}

	@Bean(name = "validator")
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(configureMessageSource());
		return localValidatorFactoryBean;
	}
	
}
