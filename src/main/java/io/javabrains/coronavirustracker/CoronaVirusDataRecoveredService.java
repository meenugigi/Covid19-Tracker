package io.javabrains.coronavirustracker;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Bean;
import javax.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Service
public class CoronaVirusDataRecoveredService {

   // private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
   // private static String VIRUS_CONFIRMED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static String VIRUS_RECOVERED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

    
    
    @Autowired
    private CoronavirusRecoveredDTO crecoveredsdto;

    @Autowired
    private static List<LocationStatsRecovered> allRecoveredStats = new ArrayList<>();
    
    String line="";
    
    @Bean
    public static List<LocationStatsRecovered> getAllRecoveryStats() {
        return allRecoveredStats;
    }

   
   
    @PostConstruct
    // @Scheduled(cron = "* * 1 * * *")
     public void fetchDeathData() throws IOException, InterruptedException {
         List<LocationStatsRecovered> newRecoveredStats = new ArrayList<>();
         HttpClient client = HttpClient.newHttpClient();
         HttpRequest request = HttpRequest.newBuilder()
                 .uri(URI.create(VIRUS_RECOVERED_URL))
                 .build();
         HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
         StringReader csvBodyReader = new StringReader(httpResponse.body());
         Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
         for (CSVRecord record : records) {
        	 LocationStatsRecovered locationStatRecovered = new LocationStatsRecovered();
            locationStatRecovered.setState(record.get("Province/State"));
            locationStatRecovered.setCountry(record.get("Country/Region"));
             int latestRecovered = Integer.parseInt(record.get(record.size() - 1));
             int prevDayRecovered = Integer.parseInt(record.get(record.size() - 2));
             locationStatRecovered.setLatestTotalRecovered(latestRecovered);
             locationStatRecovered.setDiffFromPrevDayRecovered(latestRecovered - prevDayRecovered);
             int todayRecovered = latestRecovered - prevDayRecovered;
             
              String setTotalRecovered = Integer.toString(latestRecovered);
              String setTodayRecovered = Integer.toString(todayRecovered);
             
             newRecoveredStats.add(locationStatRecovered);
         }
         this.allRecoveredStats = newRecoveredStats;
     }
     
    
    
   
	
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")

	public void saveRecoveredData() {
		// TODO Auto-generated method stub
		try {
			InputStream input = new URL("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv").openStream();
			InputStreamReader inputST =new InputStreamReader(input);
			 BufferedReader br = new BufferedReader(inputST);
			 br.readLine();
			 while((line=br.readLine())!=null) {
    		String [] datarecovered=line.split(",");   	
    		int totalRecoveredTillToday = datarecovered.length-1;
    		int totalRecoveredTillYest = datarecovered.length-2;   		
    		LocationStatsRecovered ld=new LocationStatsRecovered();
    		ld.setState(datarecovered[0]);
    		ld.setCountry(datarecovered[1]);
    		//ld.setNewCases(datadupe[nc]);
    		ld.setTotalRecovered(datarecovered[totalRecoveredTillToday]);
    		crecoveredsdto.save(ld);
    	}    	
    	}
    	catch(IOException e) {
        	e.printStackTrace();    	
    } 
	}
	
	
	
	public List<LocationStatsRecovered> findAll() {
		// TODO Auto-generated method stub
		return (List<LocationStatsRecovered>) crecoveredsdto.findAll();
	}


	public List<LocationStatsRecovered> findByCountry(String country) {
		// TODO Auto-generated method stub
		return crecoveredsdto.findByCountryLike("%"+country+"%");
	}

	 public List<LocationStatsRecovered> findAllOrderByTotalRecoveredDesc() {
	        return crecoveredsdto.findAllOrderByTotalRecoveredDesc();
	    }

	 
	 
	 public List<LocationStatsRecovered> findAllOrderByTotalRecoveredAsc() {
	        return crecoveredsdto.findAllOrderByTotalRecoveredAsc();
	    }







}

