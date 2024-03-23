package enjoying.service;

import enjoying.dto.request.FeedBackSaveRequest;
import enjoying.dto.request.FeedBackUpdateReq;
import enjoying.dto.response.FindFeedBackResponse;
import enjoying.dto.response.SimpleResponse;

public interface FeedBackService {
    SimpleResponse save(Long anId, FeedBackSaveRequest request);

    SimpleResponse deleteFeedBack(Long feedId);

    SimpleResponse updateFeedBack(Long feedId, FeedBackUpdateReq req);

    FindFeedBackResponse findFeedBack(Long feedId);
}
