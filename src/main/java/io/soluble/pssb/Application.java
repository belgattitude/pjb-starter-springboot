package io.soluble.pssb;

import org.springframework.boot.Banner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import org.springframework.web.WebApplicationInitializer;

import io.soluble.pjb.servlet.ContextLoaderListener;
import io.soluble.pjb.servlet.PhpJavaServlet;


@SpringBootApplication
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {


    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        //configureApplication(new SpringApplicationBuilder()).run(args);
        /*
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        System.out.println("Let's inspect the beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        */
    }
/*
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return configureApplication(builder);
    }


    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        return builder.sources(Application.class).bannerMode(Banner.Mode.CONSOLE);
    }
*/
    /**
     * Register the io.soluble.pjb.servlet.ContextLoaderListener
     * <p>
     * It replaces the following section found in the original web.xml config:
     * <p>
     * <pre>
     * {@code
     * <listener>
     *    <listener-class>io.soluble.pjb.servlet.ContextLoaderListener</listener-class>
     * </listener>
     * }
     * </pre>
     */
    @Bean
    public ContextLoaderListener executorListener() {
        return new ContextLoaderListener();
    }


    /**
     * Register the io.soluble.pjb.servlet.PhpJavaServlet
     * <p>
     * It replaces the following section found in the original web.xml config:
     * <p>
     * <pre>
     * {@code
     * <servlet-mapping>
     *    <servlet-name>PhpJavaServlet</servlet-name>
     *    <url-pattern>*.phpjavabridge</url-pattern>
     * </servlet-mapping>
     * }
     * </pre>
     */
    @Bean
    public ServletRegistrationBean phpJavaServletDispatcherRegistration() {

        PhpJavaServlet phpJavaServlet = new PhpJavaServlet();
        ServletRegistrationBean registration = new ServletRegistrationBean(
                phpJavaServlet);
        registration.addUrlMappings("*.phpjavabridge");
        return registration;
    }


    /*
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
    */
}