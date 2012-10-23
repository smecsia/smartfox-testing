package me.smecsia.smartfox.testing;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

/**
 * @author Ilya Sadykov
 *         Date: 21.09.12
 *         Time: 17:54
 */
public abstract class AbstractRequestHandlerTest extends AbstractHandlerTest {

    protected void verifyWhen(User fromUser, Params request, ResponseVerifier... verifiers) {
        verifyWhen((BaseClientRequestHandler) createHandler(), fromUser, request, verifiers);
    }
    /**
     * Verify output against input
     */
    protected void verifyWhen(BaseClientRequestHandler handler, User fromUser, Params request, ResponseVerifier... verifiers) {
        try {
            handler.handleClientRequest(fromUser, request.get());
            checkVerifiers(handler, verifiers);
        } catch (Exception e) {
            checkExceptionThrown(e, verifiers);
            return;
        }
        checkExceptionThrown(null, verifiers);
    }

}
