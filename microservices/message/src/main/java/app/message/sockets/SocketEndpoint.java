package app.message.sockets;

//import app.message.model.TextMessage;
//import com.google.gson.Gson;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;

import app.message.model.TextMessage;
import app.message.service.TextMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SocketEndpoint {

    private final TextMessageService textMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SocketEndpoint(TextMessageService textMessageService, SimpMessagingTemplate messagingTemplate) {
        this.textMessageService = textMessageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload TextMessage msg) {
        textMessageService.create(msg);
        messagingTemplate.convertAndSendToUser(msg.getMessage().getReceiver(), "/queue/messages", msg);
    }
}

//@Component
//@ServerEndpoint("/chat/{username}")
//public class SocketEndpoint {
//    private Map<String, Session> sessions;
//    private Gson mapper;
//
//    @PostConstruct
//    public void init() {
//        sessions = new HashMap<>();
//        mapper = new Gson();
//    }
//
//    @OnOpen
//    public void onOpen(@PathParam("username") String username, Session session) {
//        if(!sessions.containsKey(username))
//            sessions.put(username, session);
//    }
//
//    @OnClose
//    public void onClose(@PathParam("username") String username, Session session) {
//        sessions.remove(username);
//    }
//
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        if(session.isOpen()) {
//            try {
//                session.getBasicRemote().sendText(message);
//            } catch (IOException e) {
//                try {
//                    session.close();
//                } catch (IOException ioException) {
//                    ioException.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public void handleTextMessage(TextMessage textMessage) {
//        Session session = sessions.get(textMessage.getMessage().getReceiver());
//        onMessage(mapper.toJson(textMessage), session);
//    }
//}
