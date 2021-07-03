package app.message.service;

import app.message.model.Message;

import java.util.Collection;

public interface MessageService {
    void create(Message message);

    Collection<Message> getUserMessages(String user);
}
