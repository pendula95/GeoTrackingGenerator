package centili;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        FileParser fileParser = new FileParser();
        HttpClientGEO httpClientGEO = null;
        if(args.length == 2) {
            httpClientGEO = new HttpClientGEO(args[0], Integer.parseInt(args[1]), fileParser.getCountryNameInfo());
        }
        else if (args.length > 2 && args[2].equals("expanded")){
            httpClientGEO = new HttpClientGEO(args[0], Integer.parseInt(args[1]), fileParser.getCountryNameInfo(), fileParser.getOperatorNameInfo(), fileParser.getServiceNameInfo());
        }else {
            System.out.println("Bad command line arguments. Please provide url to target as first argument and number of requests per minute for second argument.");
            return;
        }
        httpClientGEO.run();
    }
}

class FileParser {

    private List<String> countryNameInfo = new ArrayList<>();
    private List<String> operatorNameInfo = new ArrayList<>();
    private List<String> serviceNameInfo = new ArrayList<>();

    public FileParser() {

        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(FileParser.class.getResourceAsStream("resources/countries.txt")));
            while ((line = br.readLine()) != null) {
                countryNameInfo.add(line);
            }
            br = new BufferedReader(new InputStreamReader(FileParser.class.getResourceAsStream("resources/operators.txt")));
            while ((line = br.readLine()) != null) {
                operatorNameInfo.add(line);
            }

            br = new BufferedReader(new InputStreamReader(FileParser.class.getResourceAsStream("resources/services.txt")));
            while ((line = br.readLine()) != null) {
                serviceNameInfo.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getCountryNameInfo() {
        return countryNameInfo;
    }

    public List<String> getOperatorNameInfo() {
        return operatorNameInfo;
    }

    public List<String> getServiceNameInfo() {
        return serviceNameInfo;
    }
}
