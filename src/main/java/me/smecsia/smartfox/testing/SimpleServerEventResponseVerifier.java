package me.smecsia.smartfox.testing;

/**
 * @author Ilya Sadykov
 *         Date: 05.10.12
 *         Time: 16:47
 */
public class SimpleServerEventResponseVerifier extends SimpleResponseVerifier implements ServerEventResponseVerifier {
    protected ServerEvent event;

    public SimpleServerEventResponseVerifier(Response response) {
        super(response);
    }

    @Override
    public void setEvent(ServerEvent event) {
        this.event = event;
    }

    @Override
    public ServerEvent getEvent() {
        return event;
    }
}
