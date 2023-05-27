package com.harera.hayat.shared.service.announcement;

import com.harera.hayat.shared.model.announcement.Announcement;
import com.harera.hayat.shared.repository.announcement.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<Announcement> list() {
        return announcementRepository.findAllByActive(true);
    }
}
