package app.profile.service;

public interface MessageService {
    boolean verifyMessage(String sender, String receiver);

    void createMessagePermission(String sender, String receiver);

    boolean verifyProfileRestrictions(String sender, String receiver);
}
