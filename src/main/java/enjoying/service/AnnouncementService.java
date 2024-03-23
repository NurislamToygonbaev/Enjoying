package enjoying.service;

import enjoying.dto.request.announcement.SaveAnnouncementRequest;
import enjoying.dto.response.SimpleResponse;

public interface AnnouncementService {
    SimpleResponse save(SaveAnnouncementRequest saveAnnouncementRequest);
}
