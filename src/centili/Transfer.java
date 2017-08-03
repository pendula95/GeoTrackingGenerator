package centili;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by lbulic on 3/25/17.
 */
public class Transfer {

    /*Basic Request*/
    private String countryName;
    private String countryCode;
    private long timeStamp;

    /*Expended Fields*/
    private String operator = null;
    private String service = null;
    private Float price = null;
    private Float revenue = null;
    private ServiceType serviceType = null;
    private Boolean successful = null;




    public Transfer(List<String> countryInfo) {
        Random random = new Random();
        Date date = new Date();


        String country = countryInfo.get(random.nextInt(countryInfo.size()));
        this.countryName = country.split(",")[1].trim();
        this.countryCode = country.split(",")[0].trim();
        this.successful = random.nextBoolean();
        this.timeStamp = date.getTime();
    }


    public Transfer(List<String> countryInfo, List<String> serviceInfo, List<String> operatorsInfo) {
        Date date = new Date();
        Random random = new Random();

        String country = countryInfo.get(random.nextInt(countryInfo.size()));
        this.countryName = country.split(",")[1].trim();
        this.countryCode = country.split(",")[0].trim();

        String operator = operatorsInfo.get(random.nextInt(operatorsInfo.size()));
        this.operator = operator;

        String service = serviceInfo.get(random.nextInt(serviceInfo.size()));
        this.service = service;

        float maxPrice = 10;
        float minPrice = 1;

        this.price = random.nextFloat() * (maxPrice - minPrice) + minPrice;
        this.revenue = (this.price / 100) * (random.nextInt((15 - 3) + 1) + 3);
        this.successful = true;
        this.serviceType = random.nextBoolean() ? ServiceType.ONETIME : ServiceType.SUBSCRIPTION;
        this.timeStamp = date.getTime();
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "countryName='" + countryName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", timeStamp=" + timeStamp +
                ", operator='" + operator + '\'' +
                ", service='" + service + '\'' +
                ", price=" + price +
                ", revenue=" + revenue +
                ", serviceType=" + serviceType +
                ", successful=" + successful +
                '}';
    }
}

enum ServiceType {
    SUBSCRIPTION("SUBSCRIPTION"),
    ONETIME ("ONETIME");

    String value;

    private ServiceType(String value) {
        this.value = value;

    }
}
