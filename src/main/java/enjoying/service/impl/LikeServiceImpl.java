package enjoying.service.impl;

import enjoying.repositories.LikeRepository;
import enjoying.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepo;
}
