package com.example.demooath.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.DefaultOAuth2RequestAuthenticator;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfiguration {
	@Bean
    @ConfigurationProperties("myapp.oauth2.client")
    public ClientCredentialsResourceDetails myAppOAuthDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    public RestTemplate myAppRestTemplate() {
    	AccessTokenRequest accessTokenRequest = new DefaultAccessTokenRequest();
        OAuth2ClientContext oAuth2ClientContext = new DefaultOAuth2ClientContext(accessTokenRequest);
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate( myAppOAuthDetails(),oAuth2ClientContext );
        oAuth2RestTemplate.setAuthenticator(new DefaultOAuth2RequestAuthenticator(){
            @Override
            public void authenticate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext clientContext, ClientHttpRequest request) {
                clientContext.setAccessToken(new DefaultOAuth2AccessToken(clientContext.getAccessToken()){
                    @Override
                    public String getTokenType() {
                        if ("BearerToken".equals(super.getTokenType())) {
                            return "Bearer";
                        }
                        return super.getTokenType();
                    }
                });
                super.authenticate(resource, clientContext, request);
            }
        });
		return oAuth2RestTemplate; 
    }
}
