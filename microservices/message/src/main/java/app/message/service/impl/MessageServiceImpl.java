package app.message.service.impl;

import app.message.model.Message;
import app.message.model.MessageType;
import app.message.repository.MessageRepository;
import app.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void create(Message message) {
        message.setDate(LocalDateTime.now());
        messageRepository.save(message);
    }

    @Override
    public Collection<Message> getUserMessages(String firstPeer, String secondPeer) {
        return messageRepository.findAllByActiveTrue().stream()
                .filter(m -> (m.getReceiver().equals(firstPeer) && m.getSender().equals(secondPeer)) || (m.getReceiver().equals(secondPeer) && m.getSender().equals(firstPeer)))
                .filter(m -> m.getType() != MessageType.ONETIME || !m.isSeen())
                .collect(Collectors.toList());
    }
}
