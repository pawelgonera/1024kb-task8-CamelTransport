package pl._1024kb.njd.poul12.task08.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class CamelRide
{
    private Long id;
    private Camel camel;
    private City from;
    private City destination;
    private Timestamp arrivalDate;
    private Timestamp departureDate;
}
