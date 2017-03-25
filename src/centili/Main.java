package centili;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("Bad command line arguments. Please provide url to target as first argument and number of requests per minute for second argument.");
            return;
        }
        FileParser fileParser = new FileParser();
        HttpClientGEO httpClientGEO = new HttpClientGEO(args[0], Integer.parseInt(args[1]), fileParser.getCountryNameInfo());
        httpClientGEO.run();
    }
}

class FileParser {

    String line;
    List<String> countryNameInfo = new ArrayList<>();

    public FileParser() {


        try (BufferedReader br = new BufferedReader(new InputStreamReader(FileParser.class.getResourceAsStream("resources/countries.txt")))) {

            while ((line = br.readLine()) != null) {
                countryNameInfo.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getCountryNameInfo() {
        return countryNameInfo;
    }
}
