package red.bus.operator.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import red.bus.operator.entity.TicketCost;
import red.bus.user.service.BookingService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TicketCostRepositoryTest {

    @Mock
    private TicketCostRepository ticketCostRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindByBusId() {
        // Mock data
        String busId = "123";
        TicketCost mockTicketCost = new TicketCost(); // adjust based on your entity

        // Mock behavior of the repository
        when(ticketCostRepository.findByBusId(busId)).thenReturn(mockTicketCost);

        // Call the method to be tested
        TicketCost result = ticketCostRepository.findByBusId(busId);

        // Assertions
        assertEquals(mockTicketCost, result); // adjust based on your entity and expected result
    }
}
