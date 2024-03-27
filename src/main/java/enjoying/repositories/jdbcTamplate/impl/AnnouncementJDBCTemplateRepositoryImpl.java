package enjoying.repositories.jdbcTamplate.impl;

import enjoying.dto.request.MyAnnounceRequest;
import enjoying.dto.request.PaginationRequest;
import enjoying.dto.response.ForPagination;
import enjoying.dto.response.MyAnnounceResponse;
import enjoying.dto.response.MyAnnouncementResponses;
import enjoying.dto.response.ResultPaginationAnnouncement;
import enjoying.entities.User;
import enjoying.enums.HomePrice;
import enjoying.enums.HomeType;
import enjoying.enums.HouseType;
import enjoying.enums.Region;
import enjoying.repositories.AnnouncementRepository;
import enjoying.repositories.jdbcTamplate.AnnouncementJDBCTemplateRepository;
import enjoying.service.impl.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class AnnouncementJDBCTemplateRepositoryImpl implements AnnouncementJDBCTemplateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CurrentUser currentUser;
    private final AnnouncementRepository announcementRepository;

    @Override
    public ResultPaginationAnnouncement getAll(PaginationRequest paginationRequest) {
        int offset = (paginationRequest.page() - 1) * paginationRequest.size(); // Вычисляем смещение
        int limit = paginationRequest.size(); // Вычисляем максимальное количество записей
        String region = "";
        if(!paginationRequest.region().equals(Region.ALL)) region = "and a.region = '" + paginationRequest.region() + "'";
        double homeType = -1;
        if(paginationRequest.homeType().equals(HomeType.POPULAR)) homeType = 3.9;
        String homePrice = "";
        if(paginationRequest.homePrice().equals(HomePrice.HIGH_TO_LOW)) homePrice = "ORDER BY a.price DESC";
        if(paginationRequest.homePrice().equals(HomePrice.LOW_TO_HIGH)) homePrice = "ORDER BY a.price ASC";
        String houseType = "";
        if (paginationRequest.houseType().equals(HouseType.HOUSE)) houseType = "and a.house_type = 'HOUSE'";
        if (paginationRequest.houseType().equals(HouseType.APARTMENT)) houseType = "and a.house_type = 'APARTMENT'";

        List<ForPagination> list = jdbcTemplate.query("""
                            SELECT a.id,
                                   array_agg(ai.images) AS images,
                                   concat('$ ', a.price, ' / day'),
                                   a.description,
                                   a.address,
                                   a.town,
                                   a.region,
                                   concat(a.max_guests, ' guests'),
                                   a.rating
                            FROM announcements a
                            JOIN announcement_images ai ON a.id = ai.announcement_id
                            WHERE a.is_active = true and a.is_block = false
                            """ + region + """
                            """ + houseType + """
                            and a.rating > ?
                            GROUP BY a.id, a.price, a.description, a.address, a.town, a.region, a.max_guests
                            """ + homePrice + """
                            LIMIT ? OFFSET ?
                        """,
                new Object[]{homeType, limit, offset},
                (rs, rowNum) -> {
                    return new ForPagination(
                            rs.getLong(1),
                            Arrays.asList((String[]) rs.getArray("images").getArray()),
                            rs.getString(3),
                            rs.getDouble("rating"),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            Region.valueOf(rs.getString(7)),
                            rs.getString(8)
                    );
                });

        return ResultPaginationAnnouncement.builder()
                .region(paginationRequest.region())
                .homeType(paginationRequest.homeType())
                .houseType(paginationRequest.houseType())
                .homePrice(paginationRequest.homePrice())
                .page(paginationRequest.page())
                .size(paginationRequest.size())
                .paginations(list)
                .build();
    }

    @Override
    public MyAnnouncementResponses myAnnouncements(Long userId, MyAnnounceRequest paginationRequest) {
        User currenUser = currentUser.getCurrenUser();

        int offset = (paginationRequest.page() - 1) * paginationRequest.size();
        int limit = paginationRequest.size();

        String homePrice = "";
        if(paginationRequest.homePrice().equals(HomePrice.HIGH_TO_LOW)) homePrice = "ORDER BY a.price DESC";
        if(paginationRequest.homePrice().equals(HomePrice.LOW_TO_HIGH)) homePrice = "ORDER BY a.price ASC";

        List<MyAnnounceResponse> list = jdbcTemplate.query("""
                        SELECT a.id,
                               array_agg(ai.images) AS images,
                               concat('$ ', a.price, ' / day'),
                               a.rating,
                               a.description,
                               a.address,
                               a.town,
                               a.region,
                               concat(a.max_guests, ' guests')
                        FROM announcements a
                        JOIN announcement_images ai ON a.id = ai.announcement_id
                        GROUP BY a.id, a.price, a.description, a.address, a.town, a.region, a.max_guests
                        """ + homePrice + """
                            LIMIT ? OFFSET ?
                        """,
                new Object[]{limit, offset},
                (rs, rowNum) -> {
                    return MyAnnounceResponse.builder()
                            .id(rs.getLong(1))
                            .images(Arrays.asList((String[]) rs.getArray("images").getArray()))
                            .price(rs.getString(3))
                            .rating(rs.getDouble(4))
                            .description(rs.getString(5))
                            .address(rs.getString(6))
                            .town(rs.getString(7))
                            .region(Region.valueOf(rs.getString(8)))
                            .guest(rs.getString(9))
                            .build();
                });
        List<MyAnnounceResponse> lists = new ArrayList<>();
        for (MyAnnounceResponse response : list) {
            response.setRentInfoSize(announcementRepository.getReferenceById(response.getId()).getRentInfos().size());
            response.setLikeSize(announcementRepository.getReferenceById(response.getId()).getFavorites().size());
            if(response.getRating() > (double) paginationRequest.rating() - 0.1 && response.getRating() < paginationRequest.rating() + 1){
                if(!paginationRequest.houseTypes().contains(HouseType.ALL)){
                    for (HouseType type : paginationRequest.houseTypes()) {
                        if(announcementRepository.getReferenceById(response.getId()).getHouseType().equals(type)){
                            lists.add(response);
                        }
                    }
                }
                else lists.add(response);
            }
        }
        return MyAnnouncementResponses.builder()
                .userName(currenUser.getFullName())
                .contact(currenUser.getEmail())
                .houseTypes(paginationRequest.houseTypes())
                .homePrice(paginationRequest.homePrice())
                .ratingFromFilter(paginationRequest.rating())
                .myAnnounceResponses(lists)
                .build();
    }

}
