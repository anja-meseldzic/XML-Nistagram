package app.message.service.impl;

import app.message.model.Message;
import app.message.repository.MessageRepository;
import app.message.service.MediaMessageService;
import app.message.sockets.SocketEndpoint;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaMessageServiceImpl implements MediaMessageService {
    private final MessageRepository messageRepository;
    private final SocketEndpoint socketEndpoint;

    @Value("${media.storage}")
    private String storageDirectory;

    @Autowired
    public MediaMessageServiceImpl(MessageRepository messageRepository, SocketEndpoint socketEndpoint) {
        this.messageRepository = messageRepository;
        this.socketEndpoint = socketEndpoint;
    }

    @Override
    public String createMultimediaMessage(MultipartFile file, Message message) throws IOException {
        String filename = saveFile(file, storageDirectory);
        String fileDownloadUri = "messages/content/" + filename;
        System.out.println(fileDownloadUri);

        message.setLinkToSource(fileDownloadUri);
        message.setDate(LocalDateTime.now());
        message = messageRepository.save(message);
        socketEndpoint.publishMediaMessage(message);
        return fileDownloadUri;
    }

    @Override
    public UrlResource getContent(String contentName) throws MalformedURLException {
        return new UrlResource("file:" + storageDirectory + File.separator + contentName);
    }

    @Override
    public void seeMessage(Long id, String seenBy) {
        Optional<Message> message = messageRepository.findById(id);
        if(!message.isPresent())
            throw new IllegalArgumentException("No such message");
        message.get().setActive(false);
        message.get().setDeletedBy(seenBy);
        messageRepository.save(message.get());
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
