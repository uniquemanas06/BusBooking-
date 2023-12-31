package red.bus.operator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="ticket_cost")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketCost {
    @Id
    @Column(name="ticket_id",unique = true)
    private String ticketId;
    @Column(name = "bus_Id")
    private String busId;
    @Column(name = "bus_Number")
    private String busNumber;
    private long cost;
//    private String code;
//    @Column(name = "discount_amount",unique = true)
//    private double discountAmount;

//    @OneToOne(mappedBy = "ticketCost")  // mappedBy refers to the field in BusOperator
//    @JoinColumn(name="bus_Id")
//    private BusOperator busOperator;
}
