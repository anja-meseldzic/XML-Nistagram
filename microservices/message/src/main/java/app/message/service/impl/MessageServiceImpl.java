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
    public Collection<Message> getUserMessages(String sender, String receiver) {
        return messageRepository.findAll().stream()
                .filter(m -> (m.getReceiver().equals(sender) && m.getSender().equals(receiver)) || (m.getReceiver().equals(receiver) && m.getSender().equals(sender)))
                .filter(m -> m.isActive() || !m.getDeletedBy().equals(receiver))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMessages(String sender, String receiver) {
        Collection<Message> messages = messageRepository.findAllBySenderAndReceiver(sender, receiver);
        messages.forEach(m -> {
            m.setActive(false);
            m.setDeletedBy(receiver);
        });
        messages.forEach(messageRepository::save);
    }
}
