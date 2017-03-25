package centili;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;

import java.util.List;
import java.util.Random;

/**
 * Created by lbulic on 3/25/17.
 */
public class Transfer {

    @Expose
    private String countryName;
    @Expose
    private String countryCode;
    @Expose
    private float price;

    private Random random;


    public Transfer(List<String> countryInfo) {
        random = new Random();
        String country = countryInfo.get(random.nextInt(countryInfo.size()));
        this.countryName = country.split(",")[0].trim();
        this.countryCode = country.split(",")[1].trim();
        this.price = price;

        float maxPrice = 10;
        float minPrice = 1;

        this.price = random.nextFloat() * (maxPrice - minPrice) + minPrice;
    }


}
