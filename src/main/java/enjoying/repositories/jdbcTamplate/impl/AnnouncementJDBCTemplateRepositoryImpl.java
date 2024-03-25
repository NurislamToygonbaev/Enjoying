package enjoying.repositories.jdbcTamplate.impl;

import enjoying.dto.request.PaginationRequest;
import enjoying.dto.response.ForPagination;
import enjoying.dto.response.ResultPaginationAnnouncement;
import enjoying.enums.Region;
import enjoying.repositories.jdbcTamplate.AnnouncementJDBCTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class AnnouncementJDBCTemplateRepositoryImpl implements AnnouncementJDBCTemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ResultPaginationAnnouncement getAll(PaginationRequest paginationRequest) {
        int offset = (paginationRequest.page() - 1) * paginationRequest.size(); // Вычисляем смещение
        int limit = paginationRequest.size(); // Вычисляем максимальное количество записей

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
                            WHERE a.is_active = true
                            GROUP BY a.id, a.price, a.description, a.address, a.town, a.region, a.max_guests
                            ORDER BY a.id desc
                            LIMIT ? OFFSET ?
                        """,
                new Object[]{limit, offset},
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
                .page(paginationRequest.page())
                .size(paginationRequest.size())
                .paginations(list)
                .build();
    }

}
