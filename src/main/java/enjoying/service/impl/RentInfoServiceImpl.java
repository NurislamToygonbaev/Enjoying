package enjoying.service.impl;


import enjoying.dto.request.Bookingrequest;
import enjoying.dto.response.AnnouncementBookingResponse;
import enjoying.dto.response.BookingResponse;
import enjoying.entities.Announcement;
import enjoying.entities.RentInfo;
import enjoying.entities.User;
import enjoying.exceptions.BedRequestException;
import enjoying.repositories.AnnouncementRepository;
import enjoying.repositories.RentInfoRepository;
import enjoying.repositories.UserRepository;
import enjoying.service.RentInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentInfoServiceImpl implements RentInfoService {
    private final RentInfoRepository rentInfoRepo;
    private final CurrentUser currentUser;
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;

    @Override
    public List<AnnouncementBookingResponse> bookingAcceptedAnnouncement() {
        User user = currentUser.getCurrenUser();
        List<RentInfo> rentInfos = user.getRentInfos();
        List<AnnouncementBookingResponse> responses = new ArrayList<>();

        for (RentInfo rentInfo : rentInfos) {
            Announcement announcement = rentInfo.getAnnouncement();
            AnnouncementBookingResponse booking = new AnnouncementBookingResponse(
                    announcement.getImages(), announcement.getId(),
                    String.valueOf(announcement.getPrice()), announcement.getRating(),
                    announcement.getDescription(), announcement.getTown(),
                    announcement.getAddress(), announcement.getMaxGuests(),
                    rentInfo.getCheckIn(), rentInfo.getCheckOut()
            );
            responses.add(booking);
        }
        return responses;
    }

    @Transactional
    @Override
    public BookingResponse bookingAnnouncement(Long announcementId, Bookingrequest bookingrequest) {
        User user = currentUser.getCurrenUser();
        RentInfo rentInfo = new RentInfo();
        Announcement announcement = announcementRepository.getAnnouncementByIdWhereIsActiveTrue(announcementId);
        if (!bookingrequest.checkin().isBefore(bookingrequest.checkout())) {
            throw new IllegalArgumentException("Invalid booking dates: checkIn must be before checkOut");
        }
        LocalDate checkin = bookingrequest.checkin();
        LocalDate checkout = bookingrequest.checkout();
        BigDecimal daysDifference = BigDecimal.valueOf(checkin.until(checkout, ChronoUnit.DAYS));
        BigDecimal price = announcement.getPrice();
        BigDecimal summa = price.multiply(daysDifference);
        if (user.getMoney().compareTo(summa) < 0) {
            throw new BedRequestException("not enough funds");
        }
        if (announcement.isActive()) {
            BigDecimal moneyUser = user.getMoney().subtract(summa);
            user.setMoney(moneyUser);
            user.getAnnouncements().add(announcement);
            BigDecimal a = announcement.getUser().getMoney().add(summa);
            Bookingrequest.builder()
                    .checkin(rentInfo.getCheckIn())
                    .checkout(rentInfo.getCheckOut())
                    .build();
            rentInfoRepo.save(rentInfo);
            announcement.getRentInfos().add(rentInfo);
            rentInfo.setAnnouncement(announcement);
            user.getRentInfos().add(rentInfo);
            rentInfo.setUser(user);

            announcement.getUser().setMoney(a);

        }
        return BookingResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully booked")
                .build();
    }
//    User user = currentUser.getCurrenUser();
//    Announcement announcement = announcementRepository.getAnnouncementByIdWhereIsActiveTrue(announcementId);
//
//    // Проверка, активно ли объявление
//    if (!announcement.isActive()) {
//        throw new IllegalStateException("Cannot book inactive announcement");
//    }
//
//    // Проверка корректности дат заселения и выселения
//    LocalDate checkin = bookingrequest.checkin();
//    LocalDate checkout = bookingrequest.checkout();
//    if (!checkin.isBefore(checkout)) {
//        throw new IllegalArgumentException("Invalid booking dates: checkIn must be before checkOut");
//    }
//
//    BigDecimal daysDifference = BigDecimal.valueOf(checkin.until(checkout, ChronoUnit.DAYS));
//    BigDecimal price = announcement.getPrice();
//    BigDecimal summa = price.multiply(daysDifference);
//
//    // Проверка, достаточно ли средств у пользователя
//    if (user.getMoney().compareTo(summa) < 0) {
//        throw new BedRequestException("Not enough funds");
//    }
//
//    // Вычитаем сумму из средств пользователя
//    BigDecimal moneyUser = user.getMoney().subtract(summa);
//    user.setMoney(moneyUser);
//
//    // Добавляем объявление в список пользовательских аренд
//    user.getAnnouncements().add(announcement);
//
//    // Увеличиваем баланс владельца объявления
//    BigDecimal ownerMoney = announcement.getUser().getMoney().add(summa);
//    announcement.getUser().setMoney(ownerMoney);
//
//    // Создаем объект RentInfo и сохраняем его
//    RentInfo rentInfo = new RentInfo();
//    rentInfo.setAnnouncement(announcement);
//    rentInfo.setCheckIn(checkin);
//    rentInfo.setCheckOut(checkout);
//    rentInfoRepo.save(rentInfo);
//
//    // Возвращаем успешный ответ
//    return BookingResponse.builder()
//            .httpStatus(HttpStatus.OK)
//            .message("Successfully booked")
//            .build();
//    }
    }



