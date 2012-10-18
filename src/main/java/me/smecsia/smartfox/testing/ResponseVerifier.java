package me.smecsia.smartfox.testing;

/**
 * @author Ilya Sadykov
 *         Date: 10.10.12
 *         Time: 18:41
 */
public interface ResponseVerifier {

    void doVerifyAfter(Object handler) throws Exception;

    void doVerifyException(Throwable e) throws AssertionError;
}
