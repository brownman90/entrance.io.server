
package io.entrance.model;

/**
 * A tenant is a customer of the entrance.io system. He therefore is able to use
 * the entrance.io application to manage his most important assets. The first
 * use case would be: the tenant uses entrance.io to manage his recruitment
 * processes.
 * 
 * @author jan.prill
 */
public class Tenant {

    /**
     * The tenant is identified by a main uri. Usually a webaddress, for
     * example: www.apple.com.
     */
    private String uri;

    /**
     * Hidden default constructor.
     */
    @SuppressWarnings("unused")
    private Tenant() {

    }

    public Tenant(String uri) {
        this.uri = uri;
    }

}
