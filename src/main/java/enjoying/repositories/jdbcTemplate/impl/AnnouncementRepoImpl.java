package enjoying.repositories.jdbcTemplate.impl;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.response.AnnouncementResponses;
import enjoying.enums.HouseType;
import enjoying.enums.Region;
import enjoying.repositories.jdbcTemplate.AnnouncementRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Types;
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
                 select array_agg(ai.images) AS images, a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          from announcements a
                          join announcement_images ai on a.id = ai.announcement_id
                          where a.is_active = false
                          group by a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                            limit ? offset ?
                """;

        List<AnnouncementResponses> announcementResponses = jdbcTemplate.query(
                query,
                (rs, rowNum) -> AnnouncementResponses.builder()
                        .image(Arrays.asList((String[]) rs.getArray("images").getArray()))
                        .id(rs.getLong("id"))
                        .description(rs.getString("description"))
                        .price(String.valueOf(rs.getBigDecimal("price")))
                        .town(rs.getString("town"))
                        .address(rs.getString("address"))
                        .rating(rs.getDouble("rating"))
                        .guest(rs.getInt("max_guests"))
                        .build(),
                size,
                offset
        );

        return UserPagination.builder()
                .page(page)
                .size(size)
                .responses(announcementResponses)
                .build();
    }

    @Override
    public UserPagination findAllAnnouncement(int page, int size) {
        int offset = (page - 1) * size;
        String query = """
                 select array_agg(ai.images) AS images, a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          from announcements a
                          join announcement_images ai on a.id = ai.announcement_id
                          where a.is_active = true
                          group by a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          order by a.created_at desc limit ? offset ?
                """;
        List<AnnouncementResponses> responses = jdbcTemplate.query(
                query,
                (rs, rowNum) -> AnnouncementResponses.builder()
                        .image(Arrays.asList((String[]) rs.getArray("images").getArray()))
                        .id(rs.getLong("id"))
                        .description(rs.getString("description"))
                        .price(String.valueOf(rs.getBigDecimal("price")))
                        .town(rs.getString("town"))
                        .address(rs.getString("address"))
                        .rating( rs.getDouble("rating"))
                        .guest(rs.getInt("max_guests"))
                        .build(),
                size,
                offset
        );
        return UserPagination.builder()
                .page(page)
                .size(size)
                .responses(responses)
                .build();
    }

    @Override
    public UserPagination regionFilterAcceptedAnnouncement(int page, int size, Region region) {
        int offset = (page - 1) * size;
        String query = """
                 select array_agg(ai.images) AS images, a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          from announcements a
                          join announcement_images ai on a.id = ai.announcement_id
                          where a.is_active = true and a.region = ?
                          group by a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          limit ? offset ?
                """;

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setObject(1, region, Types.OTHER);
            ps.setInt(2, size);
            ps.setInt(3, offset);
            return ps;
        };

        List<AnnouncementResponses> responses = jdbcTemplate.query(
                preparedStatementCreator,
                (rs, rowNum) -> AnnouncementResponses.builder()
                        .image(Arrays.asList((String[]) rs.getArray("images").getArray()))
                        .id(rs.getLong("id"))
                        .description(rs.getString("description"))
                        .price(String.valueOf(rs.getBigDecimal("price")))
                        .town(rs.getString("town"))
                        .address(rs.getString("address"))
                        .rating(rs.getDouble("rating"))
                        .guest(rs.getInt("max_guests"))
                        .build()
        );
        return UserPagination.builder()
                .page(page)
                .size(size)
                .responses(responses)
                .build();
    }

    @Override
    public UserPagination popularAcceptedAnnouncement(int page, int size) {
        int offset = (page - 1) * size;
        String query = """
                 select array_agg(ai.images) AS images, a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          from announcements a
                          join announcement_images ai on a.id = ai.announcement_id
                          where a.is_active = true and a.rating > 4
                          group by a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          limit ? offset ?
                """;
        List<AnnouncementResponses> responses = jdbcTemplate.query(
                query,
                (rs, rowNum) -> AnnouncementResponses.builder()
                        .image(Arrays.asList((String[]) rs.getArray("images").getArray()))
                        .id(rs.getLong("id"))
                        .description(rs.getString("description"))
                        .price(String.valueOf(rs.getBigDecimal("price")))
                        .town(rs.getString("town"))
                        .address(rs.getString("address"))
                        .rating( rs.getDouble("rating"))
                        .guest(rs.getInt("max_guests"))
                        .build(),
                size,
                offset
        );
        return UserPagination.builder()
                .page(page)
                .size(size)
                .responses(responses)
                .build();
    }

    @Override
    public UserPagination houseTypeFilterAcceptedAnnouncement(int page, int size, HouseType houseType) {
        int offset = (page - 1) * size;
        String query = """
                 select array_agg(ai.images) AS images, a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          from announcements a
                          join announcement_images ai on a.id = ai.announcement_id
                          where a.is_active = true and a.house_type = ?
                          group by a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          limit ? offset ?
                """;

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setObject(1, houseType, Types.OTHER);
            ps.setInt(2, size);
            ps.setInt(3, offset);
            return ps;
        };

        List<AnnouncementResponses> responses = jdbcTemplate.query(
                preparedStatementCreator,
                (rs, rowNum) -> AnnouncementResponses.builder()
                        .image(Arrays.asList((String[]) rs.getArray("images").getArray()))
                        .id(rs.getLong("id"))
                        .description(rs.getString("description"))
                        .price(String.valueOf(rs.getBigDecimal("price")))
                        .town(rs.getString("town"))
                        .address(rs.getString("address"))
                        .rating(rs.getDouble("rating"))
                        .guest(rs.getInt("max_guests"))
                        .build()
        );
        return UserPagination.builder()
                .page(page)
                .size(size)
                .responses(responses)
                .build();
    }

    @Override
    public UserPagination highPriceAcceptedAnnouncement(int page, int size) {
        int offset = (page - 1) * size;
        String query = """
                 select array_agg(ai.images) AS images, a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          from announcements a
                          join announcement_images ai on a.id = ai.announcement_id
                          where a.is_active = true
                          group by a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          order by a.price desc limit ? offset ?
                """;
        List<AnnouncementResponses> responses = jdbcTemplate.query(
                query,
                (rs, rowNum) -> AnnouncementResponses.builder()
                        .image(Arrays.asList((String[]) rs.getArray("images").getArray()))
                        .id(rs.getLong("id"))
                        .description(rs.getString("description"))
                        .price(String.valueOf(rs.getBigDecimal("price")))
                        .town(rs.getString("town"))
                        .address(rs.getString("address"))
                        .rating( rs.getDouble("rating"))
                        .guest(rs.getInt("max_guests"))
                        .build(),
                size,
                offset
        );
        return UserPagination.builder()
                .page(page)
                .size(size)
                .responses(responses)
                .build();
    }

    @Override
    public UserPagination lowPriceAcceptedAnnouncement(int page, int size) {
        int offset = (page - 1) * size;
        String query = """
                 select array_agg(ai.images) AS images, a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          from announcements a
                          join announcement_images ai on a.id = ai.announcement_id
                          where a.is_active = true
                          group by a.id, a.title, a.description, a.price, a.max_guests, a.town, a.address, a.rating
                          order by a.price limit ? offset ?
                """;
        List<AnnouncementResponses> responses = jdbcTemplate.query(
                query,
                (rs, rowNum) -> AnnouncementResponses.builder()
                        .image(Arrays.asList((String[]) rs.getArray("images").getArray()))
                        .id(rs.getLong("id"))
                        .description(rs.getString("description"))
                        .price(String.valueOf(rs.getBigDecimal("price")))
                        .town(rs.getString("town"))
                        .address(rs.getString("address"))
                        .rating( rs.getDouble("rating"))
                        .guest(rs.getInt("max_guests"))
                        .build(),
                size,
                offset
        );
        return UserPagination.builder()
                .page(page)
                .size(size)
                .responses(responses)
                .build();
    }


}
