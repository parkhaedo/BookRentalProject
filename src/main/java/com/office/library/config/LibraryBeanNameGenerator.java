package com.office.library.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryBeanNameGenerator implements BeanNameGenerator {

	private final static Logger logger = LoggerFactory.getLogger(LibraryBeanNameGenerator.class);
	
	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		// TODO Auto-generated method stub
		
		logger.info(definition.getBeanClassName());
		return definition.getBeanClassName();
	}

}
