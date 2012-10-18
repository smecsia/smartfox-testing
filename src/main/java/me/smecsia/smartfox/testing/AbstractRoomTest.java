package me.smecsia.smartfox.testing;

import com.smartfoxserver.bitswarm.sessions.Session;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.Zone;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.extensions.ISFSExtension;
import com.smartfoxserver.v2.extensions.SFSExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;


/**
 * @author Ilya Sadykov
 *         Date: 21.09.12
 *         Time: 17:54
 */
public abstract class AbstractRoomTest {
    private final Room parentRoom = initRoom();
    private final Zone parentZone = initZone();
    private final ISFSExtension parentExtension = initExtension();
    private boolean basicMocksPrepared = false;

    protected ISFSExtension initExtension() {
        return mock(SFSExtension.class);
    }

    protected Room initRoom() {
        return mock(Room.class);
    }

    protected Session initSession() {
        return new Session();
    }

    protected Zone initZone() {
        return mock(Zone.class);
    }

    private User[] playersInRoom = {};

    protected void setPlayersInRoom(User... playersInRoom) {
        this.playersInRoom = playersInRoom;
        List<User> userList = Arrays.asList(playersInRoom);
        doReturn(userList).when(getParentRoom()).getPlayersList();
    }

    public User[] getPlayersInRoom() {
        return playersInRoom;
    }

    public Room getParentRoom() {
        return parentRoom;
    }

    public Zone getParentZone() {
        return parentZone;
    }

    public ISFSExtension getParentExtension() {
        return parentExtension;
    }

    protected User player() {
        return player(null);
    }

    protected User player(String login) {
        User res = mock(User.class);
        List<UserVariable> userVariables = new ArrayList<UserVariable>();
        doReturn((int) System.currentTimeMillis()).when(res).getId();
        if (!isEmpty(login)) {
            doReturn(login).when(res).getName();
        }
        doReturn(userVariables).when(res).getVariables();
        doReturn(initSession()).when(res).getSession();
        return res;
    }

    protected synchronized void prepareBasicMocksIfRequired() {
        if (!basicMocksPrepared) {
            parentExtension.init();
            doReturn(parentRoom).when(parentExtension).getParentRoom();
            doReturn(parentZone).when(parentExtension).getParentZone();
            doReturn((int) System.currentTimeMillis()).when(parentRoom).getId();
            setPlayersInRoom();
            basicMocksPrepared = true;
        }
    }

}
