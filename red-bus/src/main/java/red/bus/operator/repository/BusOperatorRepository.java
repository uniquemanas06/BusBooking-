package red.bus.operator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import red.bus.operator.entity.BusOperator;
import red.bus.operator.payload.BusOperatorDto;

import java.time.LocalDate;
import java.util.List;

public interface BusOperatorRepository extends JpaRepository<BusOperator,String> {
    List<BusOperator> findByDepartureCityAndArrivalCityAndDepartureDate(
            String departureCity, String arrivalCity, LocalDate departureDate);

    @Query("SELECT bo FROM BusOperator bo " +
            "WHERE bo.departureCity = :departureCity " +
            "AND bo.arrivalCity = :arrivalCity " +
            "AND bo.departureDate = :departureDate")
    List<BusOperator> findByDepartureCityAndArrivalCityAndDepartureDateJPQL(
            @Param("departureCity") String departureCity,
            @Param("arrivalCity") String arrivalCity,
            @Param("departureDate") String departureDate);
}
