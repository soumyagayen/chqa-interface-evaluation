/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gov.nih.nlm.chqa;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.nih.nlm.nls.ner.MetaMapLite;
import java.text.SimpleDateFormat;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 *
 * @author gayens
 */

@ComponentScan("gov.nih.nlm.chqa")
@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@EnableCaching
public class CHQAWebApplication {
    
    @Autowired
    private Environment env;
    
    @Value("${tomcat.ajp.port}")
    private int ajpPort;

    @Value("${tomcat.ajp.remoteauthentication}")
    private String remoteAuthentication;

    @Value("${tomcat.ajp.enabled}")
    private boolean tomcatAjpEnabled;
    
    @Value("${tomcat.ajp.secretrequired}")
    private boolean tomcatAjpSecretRequired;
    
    @Value("${tomcat.ajp.secret}")
    private String tomcatAjpSecret;
    
    @Bean
    public TomcatServletWebServerFactory containerFactory()
    {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        if (tomcatAjpEnabled)
        {
            Connector ajpConnector = new Connector("AJP/1.3");
            ajpConnector.setPort(ajpPort);
            ajpConnector.setSecure(false);
            ajpConnector.setAllowTrace(false);
            ajpConnector.setScheme("http");
            if( tomcatAjpSecretRequired )
            {
                ((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecretRequired(true);
                ((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecret(tomcatAjpSecret);
            } 
            else
            {
                ((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecretRequired(false);
            }
            tomcat.addAdditionalTomcatConnectors(ajpConnector);
        }

        return tomcat;

    }
    
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder()
    {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true);
        builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
        builder.serializationInclusion(JsonInclude.Include.NON_ABSENT);


        return builder;
    }
    
    public static void main(String[] args)
    {
        SpringApplication.run(CHQAWebApplication.class, args);
    }        
    
}
