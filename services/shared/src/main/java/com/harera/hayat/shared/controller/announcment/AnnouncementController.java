package com.harera.hayat.shared.controller.announcment;

import com.harera.hayat.shared.model.announcement.Announcement;
import com.harera.hayat.shared.service.announcement.AnnouncementService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    @Operation(summary = "List", description = "List announcements")
    public List<Announcement> list() {
        return announcementService.list();
    }
}
