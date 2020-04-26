package kg.megacom.okhttptest.models;

import lombok.Data;
import java.io.File;
import java.util.Date;


@Data
public class Lot {
    private Long id;
    private String name;
    private double minPrice;
    private double maxPrice;
    private Date startDate;
    private Date endDate;
    private File file;
    private double step;
    private Status status;


}
