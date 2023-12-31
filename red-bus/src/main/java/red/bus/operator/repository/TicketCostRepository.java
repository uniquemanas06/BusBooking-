package red.bus.operator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import red.bus.operator.entity.TicketCost;

public interface TicketCostRepository extends JpaRepository<TicketCost,String> {
    TicketCost findByBusId(String busId);
}
