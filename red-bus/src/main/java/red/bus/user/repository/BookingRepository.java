package red.bus.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import red.bus.user.entity.Booking;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface BookingRepository extends JpaRepository<Booking,String> {
    Booking findByMobile(String mobile);
    Booking findBySeatNumber(int seatNumber);
    List<Booking> findByBusId(String busId);
    Booking findByEmail(String email);
}
