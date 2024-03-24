package enjoying.repositories.jdbcTemplate.impl;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.response.AnnouncementResponses;
import enjoying.dto.response.FindAnnouncementAdminRes;
import enjoying.enums.HouseType;
import enjoying.repositories.jdbcTemplate.AnnouncementRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnnouncementRepoImpl implements AnnouncementRepo {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserPagination findAllAcceptedAnnouncement(int page, int size) {
        int offset = (page - 1) * size;
        String query = """
                 select a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating, ai.images
                          from announcements a
                          join announcement_images ai on a.id = ai.announcement_id
                          where a.is_active = false limit ? offset ?
                """;
        List<AnnouncementResponses> announcementResponses = jdbcTemplate.query(
                query,
                (rs, rowNum) -> {
                    String imagesStr = rs.getString("images");
                    List<String> images = Arrays.asList(imagesStr.split(","));

                    return AnnouncementResponses.builder()
                            .id(rs.getLong("id"))
                            .image(images)
                            .description(rs.getString("description"))
                            .price(String.valueOf(rs.getBigDecimal("price")))
                            .town(rs.getString("town"))
                            .address(rs.getString("address"))
                            .rating(rs.getDouble("rating"))
                            .guest(rs.getInt("max_guests"))
                            .build();
                },
                size,
                offset
        );
        return UserPagination.builder()
                .page(page)
                .size(size)
                .responses(announcementResponses)
                .build();
    }
}
