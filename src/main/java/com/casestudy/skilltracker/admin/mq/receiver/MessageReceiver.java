package com.casestudy.skilltracker.admin.mq.receiver;

import javax.validation.Valid;

public interface MessageReceiver<T> {
    public void receiveMessage( T t);
}
