package me.smecsia.smartfox.testing;

/**
 * @author Ilya Sadykov
 *         Date: 10.10.12
 *         Time: 18:41
 */
public interface ServerEventResponseVerifier extends ResponseVerifier{

    void setEvent(ServerEvent event);

    ServerEvent getEvent();
}
