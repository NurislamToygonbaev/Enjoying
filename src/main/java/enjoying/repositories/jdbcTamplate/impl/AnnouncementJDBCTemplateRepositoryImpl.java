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
                               ai.images,
                               concat('$ ', a.price, ' / day'),
                               a.description,
                               a.address,
                               a.town,
                               a.region,
                               concat(a.max_guests, ' guests')
                        FROM announcements a
                        INNER JOIN announcement_images ai ON a.id = ai.announcement_id
                        WHERE a.is_active = false
                                LIMIT ? OFFSET ?
                                        
                        """,
                new Object[]{limit, offset},
                (rs, rowNum) -> {
                    Region region = Region.valueOf(rs.getString(7));
                    // Получаем массив изображений
                    Array imagesArray = rs.getArray(2);
                    // Если массив не null
                    List<String> imagesList = new ArrayList<>();
                    try {
                        // Преобразуем массив в список строк
                        Object[] images = (Object[]) imagesArray.getArray();
                        for (Object image : images) {
                            imagesList.add((String) image);
                        }
                    } catch (SQLException e) {
                        // Обработка ошибок
                        throw new SQLException();
                    }
                    // Создаем объект ForPagination
                    return new ForPagination(
                            rs.getLong(1),
                            imagesList,
                            rs.getString(3),
                            0.1,
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            region,
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
