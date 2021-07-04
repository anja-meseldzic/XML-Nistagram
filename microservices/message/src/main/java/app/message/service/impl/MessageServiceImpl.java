package app.message.service.impl;

import app.message.model.Message;
import app.message.model.MessageType;
import app.message.repository.MessageRepository;
import app.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
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
