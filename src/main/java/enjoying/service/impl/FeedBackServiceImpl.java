package enjoying.service.impl;

import enjoying.repositories.FeedBackRepository;
import enjoying.service.FeedBackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedBackServiceImpl implements FeedBackService {
    private final FeedBackRepository feedBackRepo;
}
