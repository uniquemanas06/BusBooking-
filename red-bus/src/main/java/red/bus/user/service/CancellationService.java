package red.bus.user.service;
import org.springframework.stereotype.Service;
import red.bus.operator.entity.BusOperator;
import red.bus.operator.repository.BusOperatorRepository;
import red.bus.user.entity.Booking;
import red.bus.user.payload.CancellationDto;
import red.bus.user.repository.BookingRepository;

@Service
public class CancellationService {

    private BookingRepository bookingRepository;
    private BusOperatorRepository busOperatorRepository;

    public CancellationService(BookingRepository bookingRepository, BusOperatorRepository busOperatorRepository) {
        this.bookingRepository = bookingRepository;
        this.busOperatorRepository = busOperatorRepository;
    }

    public CancellationDto cancelBooking(String bookingId, String email) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        BusOperator busOperator = busOperatorRepository.findById(booking.getBusId()).orElse(new BusOperator());

        if (booking == null) {
            // Handle booking not found error
            throw new RuntimeException("booking id not found "+bookingId);
        }else{
            Booking byEmail = bookingRepository.findByEmail(email);
            if(byEmail!=null && byEmail.getBookingId().equals(bookingId)){

                // Calculate refund amount (90% of the base amount)
                double refundAmount = 0.9 * booking.getCost();
                // Initiate refund through Stripe API
                CancellationDto cancellationDto = new CancellationDto();
                cancellationDto.setRefundAmount(refundAmount);
                cancellationDto.setMessage("Your booking has been canceled");
                cancellationDto.setEmail(email);
                bookingRepository.deleteById(bookingId);
                if(busOperator.getNumberOfSeats()>0 && busOperator.getNumberOfSeats()<50) {
                    busOperator.setNumberOfSeats(busOperator.getNumberOfSeats() + 1);
                    busOperatorRepository.save(busOperator);
                    return cancellationDto;
                } else if (busOperator.getNumberOfSeats()==50) {
                    busOperator.setNumberOfSeats(busOperator.getNumberOfSeats() -1);
                    busOperatorRepository.save(busOperator);
                    return cancellationDto;
                }else {
                    return null;
                }
            }else {

                throw  new RuntimeException("Email is not valid "+email);
            }


        }


    }

}
