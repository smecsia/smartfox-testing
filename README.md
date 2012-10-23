# SmartFox Server Testing library

This is a small library helping you to write the JUnit tests for your custom SmartFox Server extensions.

## What it gives

You can easily check the input and output of your client request handlers like so:
```java

    @Test
    public void testPrivateMessageSendError() {
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

First you need to setup the environment to use Maven as a builder for your SmartFox server extension. You can follow
[this tutorial](http://smecsia.me/blog/74/Developing+the+extension+for+Smartfox+server+using+Maven%2C+Spring%2C+Hibernate+and+Kundera)
to see how to do it. Or you can start with [this example](https://github.com/smecsia/smartfox-extension-example). Then
 you need to add the following repository and dependency to your pom.xml:

```xml
    <!-- ... -->
    <dependencies>
        <dependency>
            <groupId>me.smecsia.smartfox</groupId>
            <artifactId>smartfox-testing</artifactId>
            <scope>test</scope>
            <version>2.3.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
    <!-- ... -->
    <repositories>
        <repository>
            <id>smecsia.me</id>
            <name>smecsia public repository</name>
            <url>http://maven.smecsia.me/</url>
        </repository>
    </repositories>
    <!-- ... -->
```

And then you can extend the AbstractRequestHandlerTest and AbstractServerEventHandlerTest classes for your JUnit
tests and use the features described above.


## Supported SmartFox versions

* 2.3.0

## Changelog
