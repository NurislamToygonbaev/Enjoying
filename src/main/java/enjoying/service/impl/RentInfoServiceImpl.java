package enjoying.service.impl;


import enjoying.dto.request.Bookingrequest;
import enjoying.dto.response.AnnouncementBookingResponse;
import enjoying.dto.response.BookingRes;
import enjoying.entities.Announcement;
import enjoying.entities.RentInfo;
import enjoying.entities.User;
import enjoying.exceptions.BedRequestException;
import enjoying.exceptions.IllegalArgumentException;
import enjoying.repositories.AnnouncementRepository;
import enjoying.repositories.RentInfoRepository;
import enjoying.repositories.UserRepository;
import enjoying.service.RentInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            AnnouncementBookingResponse booking = new AnnouncementBookingResponse(announcement.getImages(), announcement.getId(), String.valueOf(announcement.getPrice()), announcement.getRating(), announcement.getDescription(), announcement.getTown(), announcement.getAddress(), announcement.getMaxGuests(), rentInfo.getCheckIn(), rentInfo.getCheckOut());
            responses.add(booking);
        }
        return responses;
    }

    @Transactional
    @Override
    public BookingRes bookingAnnouncement(Long announcementId, Bookingrequest bookingrequest) {
        LocalDate checkin = bookingrequest.checkin();
        LocalDate checkout = bookingrequest.checkout();
        LocalDate date = LocalDate.now();
        if ((checkin.isBefore(date) || checkout.isBefore(date))){
            throw new IllegalArgumentException("Booking dates cannot be in the past");
        }

        User user = currentUser.getCurrenUser();
        RentInfo rentInfo = new RentInfo();
        Announcement announcement = announcementRepository.getAnnouncementByIdWhereIsActiveTrue(announcementId);
        if (!bookingrequest.checkin().isBefore(bookingrequest.checkout())) {
            throw new IllegalArgumentException("Invalid booking dates: checkIn must be before checkOut");
        }

        BigDecimal daysDifference = BigDecimal.valueOf(checkin.until(checkout, ChronoUnit.DAYS));
        BigDecimal price = announcement.getPrice();
        BigDecimal summa = price.multiply(daysDifference);
        if (user.getMoney().compareTo(summa) < 0) {
            throw new BedRequestException("not enough funds");
        }

        List<RentInfo> rentInfos = announcement.getRentInfos();
        for (RentInfo info : rentInfos) {
            LocalDate existCheckIn = info.getCheckIn();
            LocalDate existCheckOut = info.getCheckOut();
            if ((checkin.isAfter(existCheckIn) && checkin.isBefore(existCheckOut)) ||
                    (checkout.isAfter(existCheckIn) && checkout.isBefore(existCheckOut)) ||
                    (checkin.isEqual(existCheckIn) || checkout.isEqual(existCheckOut)) ||
                    (checkin.isBefore(existCheckIn) && checkout.isAfter(existCheckOut))) {
                throw new BedRequestException("The apartment is already booked for these dates");
            }
        }

        BigDecimal moneyUser = user.getMoney().subtract(summa);
        user.setMoney(moneyUser);
        user.getAnnouncements().add(announcement);
        BigDecimal a = announcement.getUser().getMoney().add(summa);
        rentInfo.setCheckIn(bookingrequest.checkin());
        rentInfo.setCheckOut(bookingrequest.checkout());
        Bookingrequest.builder().checkin(rentInfo.getCheckIn()).checkout(rentInfo.getCheckOut()).build();

        rentInfoRepo.save(rentInfo);
        announcement.getRentInfos().add(rentInfo);
        rentInfo.setAnnouncement(announcement);
        user.getRentInfos().add(rentInfo);
        rentInfo.setUser(user);

        announcement.getUser().setMoney(a);

        return ResponseEntity.ok(BookingRes.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Successfully booked")
                        .build()).
                getBody();
    }
}



