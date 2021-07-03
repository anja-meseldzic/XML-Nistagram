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

    @Value("${media.storage}")
    private String storageDirectory;

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
    public void createMultimediaMessage(MultipartFile file, Message message) throws IOException {
        String filename = saveFile(file, storageDirectory);
        String fileDownloadUri = "messages/content/" + filename;
        System.out.println(fileDownloadUri);

        message.setLinkToSource(fileDownloadUri);
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

    @Override
    public UrlResource getContent(String contentName) throws MalformedURLException {
        return new UrlResource("file:" + storageDirectory + File.separator + contentName);
    }

    private String saveFile(MultipartFile file, String storagePath) throws IOException {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = getFileExtension(originalFileName);
        String filename = UUID.randomUUID().toString() + "." + extension;

        Path storage = Paths.get(storagePath);
        if(!Files.exists(storage))
            Files.createDirectories(storage);

        Path dest = Paths.get(storagePath.toString() + File.separator + filename);
        Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

    private String getFileExtension(String filename) throws IOException {
        String[] parts = filename.split("\\.");
        if(parts.length > 0)
            return parts[parts.length - 1];
        else
            throw new IOException();
    }
}
