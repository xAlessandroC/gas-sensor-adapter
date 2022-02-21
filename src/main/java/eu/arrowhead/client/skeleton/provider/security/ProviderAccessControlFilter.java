package eu.arrowhead.client.skeleton.provider.security;

import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import eu.arrowhead.common.security.AccessControlFilter;

@Component
@ConditionalOnExpression("${server.ssl.enabled:true} and !${token.security.filter.enabled:true}")
public class ProviderAccessControlFilter extends AccessControlFilter
{
    protected void checkClientAuthorized(final String clientCN, final String method, final String requestTarget, final String requestJSON, final Map<String, String[]> queryParams) {
        super.checkClientAuthorized(clientCN, method, requestTarget, requestJSON, (Map<String, String[]>)queryParams);
    }
}
