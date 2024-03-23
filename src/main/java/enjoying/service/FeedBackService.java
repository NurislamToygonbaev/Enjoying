package enjoying.service;

import enjoying.dto.request.FeedBackSaveRequest;
import enjoying.dto.response.SimpleResponse;

public interface FeedBackService {
    SimpleResponse save(Long anId, FeedBackSaveRequest request);
}
