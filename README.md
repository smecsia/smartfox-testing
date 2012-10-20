# SmartFox Server Testing library

This is a small library helping you to write the JUnit tests for your custom SmartFox Server extensions.

## What it gives

You can easily check the input and output of your client request handlers like so:
```java

    @Test
    public void testPrivateMessageSendError() throws Exception {
        User fromUser = player("Guest1");
        User toUser = player("Guest2");
        setPlayersInRoom(fromUser, toUser);

        verifyWhen(
                fromUser,

                doRequest().
                        putString("message", "Hello!").
                        putInt("toUserId", toUser.getId()),
                then(
                        user(toUser).
                                getResponse("privateMessage").
                                putString("message", "Hello!").
                                putInt("fromUserId", fromUser.getId()).
                                withNoUDP()
                )
        );
    }

```
You can also test your server event handlers in the following manner:

```java

    @Test
    public void testLoginWithEmptyLoginName() {
        verifyWhen(
                eventOccurred(SFSEventType.USER_LOGIN).
                        withParam(SFSEventParam.LOGIN_NAME, "").
                        withLoginInInt(LOGIN_TYPE, LOGIN_WITH_USERNAME),
                then(
                        exceptionIsRaised(SFSLoginException.class, LOGIN_BAD_USERNAME)
                )
        );
    }

    @Test
    public void testRequestGuestToken() {
        verifyWhen(
                eventOccurred(SFSEventType.USER_LOGIN).
                        withParam(SFSEventParam.LOGIN_NAME, "").
                        withLoginInInt(LOGIN_TYPE, LOGIN_WITH_GUEST_ACCOUNT),
                then(
                        noExceptionRaised()
                )
        );
    }
```

## How to use

## Supported SmartFox versions

* 2.3.0

## Changelog
