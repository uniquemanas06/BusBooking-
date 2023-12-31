package red.bus.user.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import red.bus.operator.entity.BusOperator;
import red.bus.operator.entity.TicketCost;
import red.bus.operator.repository.BusOperatorRepository;
import red.bus.operator.repository.TicketCostRepository;
import red.bus.user.entity.Booking;
import red.bus.user.exception.ResourceNotFound;
import red.bus.user.payload.BookingDto;
import red.bus.user.payload.PassengerDetails;
import red.bus.user.repository.BookingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    private BusOperatorRepository busOperatorRepository;
    private TicketCostRepository ticketCostRepository;
    private BookingRepository bookingRepository;
    int seatNumber;
    public BookingService(BusOperatorRepository busOperatorRepository, TicketCostRepository ticketCostRepository, BookingRepository bookingRepository) {
        this.busOperatorRepository = busOperatorRepository;
        this.ticketCostRepository = ticketCostRepository;
        this.bookingRepository = bookingRepository;
    }

    public BookingDto createBooking(String busId, String ticketId,
                                    PassengerDetails passengerDetails) throws StripeException {

        BusOperator busOperator = busOperatorRepository.findById(busId).orElseThrow(
                () -> new ResourceNotFound("busId not found " + busId)
        );
        TicketCost ticketCost = ticketCostRepository.findById(ticketId).orElseThrow(
                () -> new ResourceNotFound("ticketId not found " + ticketId)
        );
        TicketCost byBusId = ticketCostRepository.findByBusId(busId);

        Stripe.apiKey = stripeSecretKey;

        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("inr") // Replace with your desired currency
                .setAmount(byBusId.getCost())// Amount in cents byBusId.getCost()*100 or 10
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(createParams);
        Booking booking = new Booking();
        BookingDto bookingDto = new BookingDto();
        //"requires_payment_method".equals(paymentIntent.getStatus())
        if ("requires_payment_method".equals(paymentIntent.getStatus())) {
            String bookingId = UUID.randomUUID().toString();
            booking.setBookingId(bookingId);
            booking.setBusId(busId);
            booking.setTicketId(ticketId);
            booking.setDepartureCity(busOperator.getDepartureCity());
            booking.setArrivalCity(busOperator.getArrivalCity());
            booking.setBusCompany(busOperator.getBusOperatorCompanyName());
            booking.setBusNumber(busOperator.getBusNumber());
            booking.setCost(byBusId.getCost());
            booking.setFirstName(passengerDetails.getFirstName());
            booking.setLastName(passengerDetails.getLastName());
            booking.setEmail(passengerDetails.getEmail());
            booking.setMobile(passengerDetails.getMobile());
            booking.setDepartureDate(busOperator.getDepartureDate());
            booking.setArrivalDate(busOperator.getArrivalDate());
            booking.setDepartureTime(busOperator.getDepartureTime());
            booking.setArrivalTime(busOperator.getArrivalTime());
            Booking savedBooking = null;
             seatNumber=passengerDetails.getSeatNumber();
            Booking bySeatNumber = bookingRepository.findBySeatNumber(seatNumber);
            if (busOperator.getNumberOfSeats() > 0 ) {
                if(bySeatNumber==null){
                    booking.setSeatNumber(seatNumber);
                    savedBooking = bookingRepository.save(booking);
                }else{
                    throw new RuntimeException("Seat Not Available");
                }
            } else {
                throw new RuntimeException("Seat Not Available");
            }

            bookingDto.setBookingId(savedBooking.getBookingId());
            bookingDto.setBusId(savedBooking.getBusId());
            bookingDto.setTicketId(savedBooking.getTicketId());
            bookingDto.setDepartureCity(savedBooking.getDepartureCity());
            bookingDto.setArrivalCity(savedBooking.getArrivalCity());
            bookingDto.setBusCompany(savedBooking.getBusCompany());
            bookingDto.setBusNumber(savedBooking.getBusNumber());
            bookingDto.setCost(savedBooking.getCost());
            bookingDto.setFirstName(savedBooking.getFirstName());
            bookingDto.setLastName(savedBooking.getLastName());
            bookingDto.setEmail(savedBooking.getEmail());
            bookingDto.setMobile(savedBooking.getMobile());
            bookingDto.setDepartureDate(savedBooking.getDepartureDate());
            bookingDto.setArrivalDate(savedBooking.getArrivalDate());
            bookingDto.setDepartureTime(savedBooking.getDepartureTime());
            bookingDto.setArrivalTime(savedBooking.getArrivalTime());
            bookingDto.setSeatNumber(booking.getSeatNumber());
            bookingDto.setMessage("Booking Confirmed");
            if (busOperator.getNumberOfSeats() > 0) {
                busOperator.setNumberOfSeats(busOperator.getNumberOfSeats() - 1);
                busOperatorRepository.save(busOperator);
                return bookingDto;
            } else {
                throw new RuntimeException("Seat not Available");
            }

        }
        bookingDto.setMessage("Payment failed. Please check your payment details and try again.");
        return null;

    }

    public String getSeatAvailabilityInfo(String busId) {
        BusOperator busOperator = busOperatorRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFound("busId not found " + busId));
        int availableSeats = busOperator.getNumberOfSeats();
        return "Available seats "+availableSeats;
    }

    public List<Integer> getAllBookedSeats(String busId) {
        List<Booking> bookedSeats = bookingRepository.findByBusId(busId);
        List<Integer> bookedSeatNumbers = new ArrayList<>();

        for (Booking booking : bookedSeats) {
            bookedSeatNumbers.add(booking.getSeatNumber());
        }

        return bookedSeatNumbers;
    }

}


