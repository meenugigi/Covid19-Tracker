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
import java.util.List;

@Component
@Service
public class CoronaVirusDataService {

   // private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @Autowired
    private CoronavirusDTO cdto;

    @Autowired
    private List<LocationStats> allStats = new ArrayList<>();
    
    String line="";
    
    @Bean
    public List<LocationStats> getAllStats() {
        return allStats;
    }

    
   //fetch data from raw csv file and reads line by line and saves data from last 2 columns of the csv file to fetch total cases and new cases.
    // new cases would be the difference between the 'total cases of today' - 'total cases yest'.
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
            
             String setTodaysCases = Integer.toString(latestCases);
            
            newStats.add(locationStat);
        }
        this.allStats = newStats;
    }
    
    
    
    //save data to database
   
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")  
	public void saveData() {
		// TODO Auto-generated method stub
		try {
			InputStream input = new URL("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv").openStream();
			InputStreamReader inputST =new InputStreamReader(input);
			 BufferedReader br = new BufferedReader(inputST);
			 br.readLine();
    	//	BufferedReader br=new BufferedReader(new FileReader("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv"));
    	while((line=br.readLine())!=null) {
    		String [] data=line.split(",");
    		int datasize = data.length-1;
    		LocationStats l=new LocationStats();
    		l.setState(data[0]);
    		l.setCountry(data[1]);
    		l.setTodaysCases(data[datasize]);
    		cdto.save(l);
    	}
    	
    	}
    	catch(IOException e) {
        	e.printStackTrace();
    	
    } 
	}
	

	

	
	

	
	
	public List<LocationStats> findAll() {
		// TODO Auto-generated method stub
		return (List<LocationStats>) cdto.findAll();
	}
	
	


	public List<LocationStats> getAllCountry() {
		// TODO Auto-generated method stub
		return cdto.getAllCountry();
	}
	

	
	public List<LocationStats> getAllTodaysCases() {
		// TODO Auto-generated method stub
		return cdto.getAllTodaysCases();
	}

	public List<LocationStats> findByCountry(String country) {
		// TODO Auto-generated method stub
		return cdto.findByCountryLike("%"+country+"%");
	}


	 public List<LocationStats> findAllOrderByTodaysCasesDesc() {
	        return cdto.findAllOrderByTodaysCasesDesc();
	    }

	 
	 
	 public List<LocationStats> findAllOrderByTodaysCasesAsc() {
	        return cdto.findAllOrderByTodaysCasesAsc();
	    }


}

