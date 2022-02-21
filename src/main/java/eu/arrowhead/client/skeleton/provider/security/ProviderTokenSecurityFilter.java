package eu.arrowhead.client.skeleton.provider.security;

import java.security.PublicKey;
import java.security.PrivateKey;
import eu.arrowhead.common.token.TokenSecurityFilter;

public class ProviderTokenSecurityFilter extends TokenSecurityFilter
{
    private PrivateKey myPrivateKey;
    private PublicKey authorizationPublicKey;
    
    protected PrivateKey getMyPrivateKey() {
        return this.myPrivateKey;
    }
    
    protected PublicKey getAuthorizationPublicKey() {
        return this.authorizationPublicKey;
    }
    
    public void setMyPrivateKey(final PrivateKey myPrivateKey) {
        this.myPrivateKey = myPrivateKey;
    }
    
    public void setAuthorizationPublicKey(final PublicKey authorizationPublicKey) {
        this.authorizationPublicKey = authorizationPublicKey;
    }
}
