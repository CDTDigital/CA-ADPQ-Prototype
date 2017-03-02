package com.intimetec.crns.core.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

/**
 * {@code HibernateConfig} class for the Hibernate configurations.
 *  @author In Time Tec.
 */
@Configuration
public class HibernateConfig {
	/**
	 * 
	 * @param emf   the entity manager factory.
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
