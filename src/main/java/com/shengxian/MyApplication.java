package com.shengxian;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.IOException;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2019-03-27
 * @Version: 1.0
 */
@SpringBootApplication(scanBasePackages = "com.shengxian")
@EnableConfigurationProperties //开始事物
@EnableScheduling //启动定时任务
@CacheConfig
public class MyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class);
    }

    //重写configure方法
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                //Due to CONFIDENTIAL and /*, this will cause Tomcat to redirect every request to HTTPS.
                //You can configure multiple patterns and multiple constraints if you need more control over what is and is not redirected.

                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                collection.addMethod("post");
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;

    }
    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");

        //Set the scheme that will be assigned to requests received through this connector
        //@param scheme The new scheme
        connector.setScheme("http");

        //Set the port number on which we listen for requests.
        // @param port The new port number
        connector.setPort(8088);

        //Set the secure connection flag that will be assigned to requests received through this connector.
        //@param secure The new secure connection flag
        //if connector.setSecure(true),the http use the http and https use the https;else if connector.setSecure(false),the http redirect to https;
        connector.setSecure(false);

        //redirectPort The redirect port number (non-SSL to SSL)
        connector.setRedirectPort(8888);
        return connector;
    }

}
