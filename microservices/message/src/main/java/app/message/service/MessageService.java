package app.message.service;

import app.message.model.Message;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;

public interface MessageService {
    void create(Message message);

    Collection<Message> getUserMessages(String firstPeer, String secondPeer);
}
