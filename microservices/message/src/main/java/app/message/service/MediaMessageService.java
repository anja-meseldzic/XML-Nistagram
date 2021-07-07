package app.message.service;

import app.message.model.Message;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface MediaMessageService {
    String createMultimediaMessage(MultipartFile file, Message message) throws IOException;
    UrlResource getContent(String contentName) throws MalformedURLException;

    void seeMessage(Long id, String seenBy);
}
