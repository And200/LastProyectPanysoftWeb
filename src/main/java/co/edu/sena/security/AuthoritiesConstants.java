package co.edu.sena.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String WAITER = "ROLE_MESERO";

    public static final String CASHIER = "ROLE_CAJERO";

    public static final String BAKER = "ROLE_PANADERO";

    public static final String CLIENT = "ROLE_CLIENTE";

    private AuthoritiesConstants() {}
}
