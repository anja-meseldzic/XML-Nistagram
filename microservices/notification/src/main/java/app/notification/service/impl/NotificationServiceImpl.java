package app.notification.service.impl;

import app.notification.dto.NewNotificationDTO;
import app.notification.dto.NotificationDTO;
import app.notification.model.Notification;
import app.notification.model.NotificationSettingsPerFollow;
import app.notification.model.NotificationSettingsPerProfile;
import app.notification.repository.NotificationRepository;
import app.notification.repository.SettingsPerFollowRepository;
import app.notification.repository.SettingsPerProfileRepository;
import app.notification.service.FollowService;
import app.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;
    private SettingsPerFollowRepository settingsPerFollowRepository;
    private SettingsPerProfileRepository settingsPerProfileRepository;
    private FollowService followService;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   SettingsPerFollowRepository settingsPerFollowRepository,
                                   SettingsPerProfileRepository settingsPerProfileRepository,
                                   FollowService followService) {
        this.notificationRepository = notificationRepository;
        this.settingsPerFollowRepository = settingsPerFollowRepository;
        this.settingsPerProfileRepository = settingsPerProfileRepository;
        this.followService = followService;
    }

    @Override
    public List<NotificationDTO> getAll(String requestedBy) {
        List<NotificationDTO> result = notificationRepository.findAllByReceiverUsername(requestedBy)
                .stream().sorted(Comparator.comparing(Notification::getCreated))
                .map(n -> new NotificationDTO(n.getText(), n.getResource(), n.getType().toString()))
                .collect(Collectors.toList());
        Collections.reverse(result);
        return result;
    }

    @Override
    public void create(NewNotificationDTO dto) {
        switch (dto.getType()) {
            case NEW_FOLLOW:
                handleNewFollow(dto);
                break;
            case NEW_FOLLOW_REQUEST:
                handleNewFollowRequest(dto);
                break;
            case FOLLOW_REQUEST_ACCEPTED:
                handleNewRequestAccepted(dto);
                break;
            case MESSAGE:
                handleNewMessage(dto);
                break;
            case COMMENT:
                handleNewComment(dto);
                break;
            case RATING:
                handleNewRating(dto);
                break;
            case POST:
                handleNewPost(dto);
                break;
            case STORY:
                handleNewStory(dto);
                break;
        }
    }

    private void handleNewFollow(NewNotificationDTO dto) {
        NotificationSettingsPerProfile settings = settingsPerProfileRepository
                .findByProfile(dto.getReceiverUsername()).orElse(null);
        if(settings != null && settings.isNotifyOnFollw()) {
            save(dto);
        }
    }

    private void handleNewFollowRequest(NewNotificationDTO dto) {
        NotificationSettingsPerProfile settings = settingsPerProfileRepository
                .findByProfile(dto.getReceiverUsername()).orElse(null);
        if(settings != null && settings.isNotifyOnFollw()) {
            save(dto);
        }
    }

    private void handleNewRequestAccepted(NewNotificationDTO dto) {
        NotificationSettingsPerProfile settings = settingsPerProfileRepository
                .findByProfile(dto.getReceiverUsername()).orElse(null);
        if(settings != null && settings.isNotifyOnAcceptedFollowRequest()) {
                save(dto);
        }
    }

    private void handleNewMessage(NewNotificationDTO dto) {
        if(followed(dto.getInitiatorUsername(), dto.getReceiverUsername())) {
            NotificationSettingsPerFollow settings = getSettings(dto.getInitiatorUsername(), dto.getReceiverUsername());
            if(settings != null && settings.isNotifyOnMessage()) {
                save(dto);
            }
        } else {
            NotificationSettingsPerProfile settings = settingsPerProfileRepository
                    .findByProfile(dto.getReceiverUsername()).orElse(null);
            if(settings != null && settings.isNotifyOnNonFollowedMessage()) {
                save(dto);
            }
        }
    }

    private void handleNewComment(NewNotificationDTO dto) {
        if(followed(dto.getInitiatorUsername(), dto.getReceiverUsername())) {
            NotificationSettingsPerFollow settings = getSettings(dto.getInitiatorUsername(), dto.getReceiverUsername());
            if(settings != null && settings.isNotifyOnComment()) {
                save(dto);
            }
        } else {
            NotificationSettingsPerProfile settings = settingsPerProfileRepository
                    .findByProfile(dto.getReceiverUsername()).orElse(null);
            if(settings != null && settings.isNotifyOnNonFollowedComment()) {
                save(dto);
            }
        }
    }

    private void handleNewRating(NewNotificationDTO dto) {
        if(followed(dto.getInitiatorUsername(), dto.getReceiverUsername())) {
            NotificationSettingsPerFollow settings = getSettings(dto.getInitiatorUsername(), dto.getReceiverUsername());
            if(settings != null && settings.isNotifyOnRating()) {
                save(dto);
            }
        } else {
            NotificationSettingsPerProfile settings = settingsPerProfileRepository
                    .findByProfile(dto.getReceiverUsername()).orElse(null);
            if(settings != null && settings.isNotifyOnNonFollowedRating()) {
                save(dto);
            }
        }
    }

    private void handleNewPost(NewNotificationDTO dto) {
        if(followed(dto.getInitiatorUsername(), dto.getReceiverUsername())) {
            NotificationSettingsPerFollow settings = getSettings(dto.getInitiatorUsername(), dto.getReceiverUsername());
            if(settings != null && settings.isNotifyOnPost()) {
                save(dto);
            }
        }
    }

    private void handleNewStory(NewNotificationDTO dto) {
        if(followed(dto.getInitiatorUsername(), dto.getReceiverUsername())) {
            NotificationSettingsPerFollow settings = getSettings(dto.getInitiatorUsername(), dto.getReceiverUsername());
            if(settings != null && settings.isNotifyOnStory()) {
                save(dto);
            }
        }
    }

    private boolean followed(String profile, String followedBy) {
        return settingsPerFollowRepository.findAll().stream()
                .filter(s -> s.getProfile().equals(profile)
                            && followService.getProfileByFollow(s.getFollowId()).equals(followedBy))
                .count() > 0;
    }

    private NotificationSettingsPerFollow getSettings(String profile, String followedBy) {
        return settingsPerFollowRepository.findAll().stream()
                .filter(s -> s.getProfile().equals(profile)
                        && followService.getProfileByFollow(s.getFollowId()).equals(followedBy))
                .findFirst().orElse(null);
    }

    private void save(NewNotificationDTO dto) {
        Notification notification = new Notification(dto.getType(), dto.getReceiverUsername(), dto.getInitiatorUsername(), dto.getResource(), LocalDateTime.now());
        notificationRepository.save(notification);
        //socket
    }
}
