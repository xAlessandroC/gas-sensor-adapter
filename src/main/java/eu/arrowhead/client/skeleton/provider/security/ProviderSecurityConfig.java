package eu.arrowhead.client.skeleton.provider.security;

import javax.servlet.Filter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration;
import eu.arrowhead.client.library.config.DefaultSecurityConfig;

@Configuration
@EnableWebSecurity
public class ProviderSecurityConfig extends DefaultSecurityConfig
{
    @Value("${token.security.filter.enabled:true}")
    private boolean tokenSecurityFilterEnabled;
    private ProviderTokenSecurityFilter tokenSecurityFilter;
    
    protected void configure(final HttpSecurity http) throws Exception {
        super.configure(http);
        if (this.tokenSecurityFilterEnabled) {
            http.addFilterAfter((Filter)(this.tokenSecurityFilter = new ProviderTokenSecurityFilter()), SecurityContextHolderAwareRequestFilter.class);
        }
    }
    
    public ProviderTokenSecurityFilter getTokenSecurityFilter() {
        return this.tokenSecurityFilter;
    }
}
