package me.smecsia.smartfox.testing;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

/**
 * @author Ilya Sadykov
 *         Date: 21.09.12
 *         Time: 17:54
 */
public abstract class AbstractRequestHandlerTest extends AbstractHandlerTest {

    /**
     * Verify output against input
     */
    protected void verifyWhen(User fromUser, Params request, ResponseVerifier... verifiers) {
        BaseClientRequestHandler handler = createHandler(BaseClientRequestHandler.class);
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
