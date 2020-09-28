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
public class CoronaVirusDataDeathsService {

	private static String VIRUS_DEATHS_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    
    
    @Autowired
    private CoronavirusDeathsDTO2 cdeathsdto;

    @Autowired
    private static List<LocationStatsDeaths> allDeathsStats = new ArrayList<>();
    
    String line="";
    
    @Bean
    public static List<LocationStatsDeaths> getAllDeathStats() {
        return allDeathsStats;
    }

  
    //fetches data from csv file and reads csv file line by line. has been scheduled so that the fetching takes place automatically.
   
    @PostConstruct
    // @Scheduled(cron = "* * 1 * * *")
     public void fetchDeathData() throws IOException, InterruptedException {
         List<LocationStatsDeaths> newDeathStats = new ArrayList<>();
         HttpClient client = HttpClient.newHttpClient();
         HttpRequest request = HttpRequest.newBuilder()
                 .uri(URI.create(VIRUS_DEATHS_URL))
                 .build();
         HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
         StringReader csvBodyReader = new StringReader(httpResponse.body());
         Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
         for (CSVRecord record : records) {
        	 LocationStatsDeaths locationStatDeaths = new LocationStatsDeaths();
            locationStatDeaths.setState(record.get("Province/State"));
            locationStatDeaths.setCountry(record.get("Country/Region"));
             int latestDeaths = Integer.parseInt(record.get(record.size() - 1));
             int prevDayDeaths = Integer.parseInt(record.get(record.size() - 2));
             locationStatDeaths.setLatestTotalDeaths(latestDeaths);
             locationStatDeaths.setDiffFromPrevDayDeaths(latestDeaths - prevDayDeaths);
             int todayDeaths = latestDeaths - prevDayDeaths;
             
              String setTotalDeaths = Integer.toString(latestDeaths);
              String setTodayDeaths = Integer.toString(todayDeaths);
             
             newDeathStats.add(locationStatDeaths);
         }
         this.allDeathsStats = newDeathStats;
     }
     
    
    
   
	// save data into database.
    
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
	public void saveDeathsData() {
		// TODO Auto-generated method stub
		try {
			InputStream input = new URL("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv").openStream();
			InputStreamReader inputST =new InputStreamReader(input);
			 BufferedReader br = new BufferedReader(inputST);
			 br.readLine();
			 while((line=br.readLine())!=null) {
    		String [] datadeaths=line.split(",");   	
    		int totalDeathsTillToday = datadeaths.length-1;
    		int totalDeathsTillYest = datadeaths.length-2;   		
    		LocationStatsDeaths ld=new LocationStatsDeaths();
    		ld.setState(datadeaths[0]);
    		ld.setCountry(datadeaths[1]);
    		//ld.setNewCases(datadupe[nc]);
    		ld.setTotalDeaths(datadeaths[totalDeathsTillToday]);
    		cdeathsdto.save(ld);
    	}    	
    	}
    	catch(IOException e) {
        	e.printStackTrace();    	
    } 
	}
	
	
	
	
	

	
	public List<LocationStatsDeaths> findAll() {
		// TODO Auto-generated method stub
		return (List<LocationStatsDeaths>) cdeathsdto.findAll();
	}

//to search data by country name
	public List<LocationStatsDeaths> findByCountry(String country) {
		// TODO Auto-generated method stub
		return cdeathsdto.findByCountryLike("%"+country+"%");
	}


//to sort data in descending order
	 public List<LocationStatsDeaths> findAllOrderByTotalDeathsDesc() {
	        return cdeathsdto.findAllOrderByTotalDeathsDesc();
	    }

	 
	//to sort data in ascendng order 
	 public List<LocationStatsDeaths> findAllOrderByTotalDeathsAsc() {
	        return cdeathsdto.findAllOrderByTotalDeathsAsc();
	    }



}

