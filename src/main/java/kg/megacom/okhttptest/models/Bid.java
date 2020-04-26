package kg.megacom.okhttptest.models;

import lombok.Data;

import java.util.Date;

@Data
public class Bid {
    private Long id;
    private Date addDate;
    private double bidValue;
    private boolean active;
    private Lot lot;
    private Customer customer;

}
