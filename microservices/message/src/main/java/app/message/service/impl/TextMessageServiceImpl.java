package app.message.service.impl;

import app.message.model.TextMessage;
import app.message.repository.TextMessageRepository;
import app.message.service.TextMessageService;
import app.message.sockets.SocketEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TextMessageServiceImpl implements TextMessageService {
    private final TextMessageRepository textMessageRepository;

    @Autowired
    public TextMessageServiceImpl(TextMessageRepository textMessageRepository) {
        this.textMessageRepository = textMessageRepository;
    }

    @Override
    public void create(TextMessage message) {
        message.getMessage().setDate(LocalDateTime.now());
        textMessageRepository.save(message);
//        socketEndpoint.handleTextMessage(message);
    }
}
