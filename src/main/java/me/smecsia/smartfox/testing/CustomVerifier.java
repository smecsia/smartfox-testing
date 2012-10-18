package me.smecsia.smartfox.testing;

/**
 * @author Ilya Sadykov
 *         Date: 05.10.12
 *         Time: 16:47
 */
public abstract class CustomVerifier extends SimpleResponseVerifier {

    public CustomVerifier() {
        super(null);
    }

    @Override
    public void doVerifyException(Throwable e) throws AssertionError {
        // do nothing
    }

    @Override
    public void doVerifyAfter(Object handler) throws Exception {
        verify(handler);
    }

    protected abstract void verify(Object handler);
}
