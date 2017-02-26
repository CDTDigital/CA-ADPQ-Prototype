package com.intimetec.crns.core.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

/**
 * @author shiva.dixit.
 */
@Configuration
public class HibernateConfig {
	/**
	 * 
	 * @param emf The entity manager factory.
	 * @return factory
	 */
    @Bean
	public HibernateJpaSessionFactoryBean sessionFactory(
    		 final EntityManagerFactory emf) {
         HibernateJpaSessionFactoryBean factory = 
        		 new HibernateJpaSessionFactoryBean();
         factory.setEntityManagerFactory(emf);
         return factory;
    }
}
