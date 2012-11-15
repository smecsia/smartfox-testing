package me.smecsia.smartfox.testing;

import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSDataWrapper;
import org.mockito.internal.matchers.Equals;

import java.util.Iterator;

/**
 * @author Ilya Sadykov
 *         Date: 05.10.12
 *         Time: 16:28
 */
public class ISFSObjectMatcher extends Equals {
    public ISFSObjectMatcher(Object wanted) {
        super(wanted);
    }

    public boolean matches(Object actual) {
        if (!(actual instanceof ISFSObject)) {
            return false;
        }
        ISFSObject actualObj = (ISFSObject) actual;
        ISFSObject wanted = (ISFSObject) getWanted();
        for (String key : wanted.getKeys()) {

            SFSDataWrapper actualData = actualObj.get(key);
            SFSDataWrapper wantedData = wanted.get(key);
            if (actualData != null && wantedData != null) {
                final Boolean valuesEqual;
                if (actualData.getObject() instanceof ISFSObject) {
                    valuesEqual = new ISFSObjectMatcher(wantedData.getObject()).matches(actualData.getObject());
                } else if (actualData.getObject() instanceof ISFSArray) {
                    boolean res = true;
                    Iterator<SFSDataWrapper> actualIterator = ((ISFSArray) actualData.getObject()).iterator();
                    Iterator<SFSDataWrapper> wantedIterator = ((ISFSArray) wantedData.getObject()).iterator();
                    if (actualIterator.hasNext() && !wantedIterator.hasNext()) {
                        return false;
                    }
                    while (wantedIterator.hasNext()) {
                        if (!actualIterator.hasNext()) {
                            return false;
                        }
                        res = res && new ISFSObjectMatcher(wantedIterator.next().getObject()).matches
                                (actualIterator.next().getObject());
                    }
                    valuesEqual = res;
                } else {
                    valuesEqual = wantedData.getObject().equals(actualData.getObject());
                }
                if (!actualData.getTypeId().equals(wantedData.getTypeId()) || !valuesEqual) {
                    return false;
                }
            }
        }
        return true;
    }

}
