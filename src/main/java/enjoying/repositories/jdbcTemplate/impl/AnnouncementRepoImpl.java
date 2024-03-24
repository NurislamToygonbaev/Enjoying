package enjoying.repositories.jdbcTemplate.impl;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.response.AnnouncementResponses;
import enjoying.repositories.jdbcTemplate.AnnouncementRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
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
                select images, price, rating, description, address, town, max_guests
                 from announcements where is_active = true limit ? offset ?
                """;
        List<AnnouncementResponses> announcementResponses = jdbcTemplate.query(
                query,
                new Object[]{size, offset},
                (ResultSet rs) -> {
                    List<AnnouncementResponses> results = new ArrayList<>();
                    while (rs.next()) {
                        String imagesStr = rs.getString("images");
                        List<String> images = Arrays.asList(imagesStr.split(","));

                        AnnouncementResponses announcementResponse = AnnouncementResponses.builder()
                                .image(images)
                                .description(rs.getString("description"))
                                .price(String.valueOf(rs.getBigDecimal("price")))
                                .guest(rs.getInt("max_guests"))
                                .town(rs.getString("town"))
                                .address(rs.getString("address"))
                                .rating(rs.getDouble("rating"))
                                .build();

                        results.add(announcementResponse);
                    }
                    return results;
                }
        );
        return UserPagination.builder()
                .page(page)
                .size(size)
                .responses(announcementResponses)
                .build();
    }
}
