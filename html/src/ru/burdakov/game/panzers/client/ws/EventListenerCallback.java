package ru.burdakov.game.panzers.client.ws;

import jsinterop.annotations.JsFunction;

@JsFunction
@FunctionalInterface
public interface EventListenerCallback {

    void callEvent(WsEvent event);

}
