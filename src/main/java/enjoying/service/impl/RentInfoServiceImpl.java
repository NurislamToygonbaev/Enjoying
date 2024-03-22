package enjoying.service.impl;


import enjoying.repositories.RentInfoRepository;
import enjoying.service.RentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentInfoServiceImpl implements RentInfoService {
    private final RentInfoRepository rentInfoRepo;
}
