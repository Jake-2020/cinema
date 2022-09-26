package cinema.demo.util;


public interface Constant {
    /**
     * 权限：普通用户
     */
    String AUTHORITY_USER = "user";

    /**
     * 权限：普通用户
     */
    String AUTHORITY_ADMIN = "admin";

    /**
     * 默认状态的登录凭证的超时时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * 记住状态的登录凭证超时时间
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;
}
