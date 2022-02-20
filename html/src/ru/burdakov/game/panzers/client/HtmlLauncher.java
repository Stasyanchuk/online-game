package ru.burdakov.game.panzers.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.user.client.Timer;
import ru.burdakov.game.panzers.Starter;
import ru.burdakov.game.panzers.client.dto.InputStateImpl;
import ru.burdakov.game.panzers.client.ws.EventListenerCallback;
import ru.burdakov.game.panzers.client.ws.WebSocket;

public class HtmlLauncher extends GwtApplication {

    private MessageProcessor messageProcessor;

    @Override
    public GwtApplicationConfiguration getConfig() {
        // Resizable application, uses available space in browser
        return new GwtApplicationConfiguration(true);
        // Fixed size application:
        //return new GwtApplicationConfiguration(480, 320);
    }

    public native WebSocket getWebSocket(String url)
        /*-{
            return new WebSocket(url);
        }-*/
    ;

    public native void log(Object obj)
        /*-{
            console.log(obj)
        }-*/
    ;

    public native String toJson(Object obj)
        /*-{
            return JSON.stringify(obj);
        }-*/
    ;

    @Override
    public ApplicationListener createApplicationListener() {
        WebSocket client = getWebSocket("ws://localhost:8081/ws");


        Starter starter = new Starter(new InputStateImpl());
        messageProcessor = new MessageProcessor(starter);
        starter.setMessageSender(message -> {
            client.send(toJson(message));
        });

        Timer timer = new Timer() {
            @Override
            public void run() {
                starter.handleTimer();
            }
        };

        EventListenerCallback callback = event -> {
            messageProcessor.processEvent(event);
        };

        client.addEventListener("open", event -> {
            timer.scheduleRepeating(1000 / 30);
            messageProcessor.processEvent(event);
        });
        client.addEventListener("close", callback);
        client.addEventListener("error", callback);
        client.addEventListener("message", callback);
        return starter;
    }
}