package io.javabrains.coronavirustracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import net.minidev.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Controller
public class HomeController {
	
    @Autowired
   private CoronaVirusDataService coronaVirusDataService;
    @Autowired
    private CoronaVirusDataDeathsService coronaVirusDataDeathsService;
    @Autowired
    private CoronaVirusDataRecoveredService coronaVirusDataRecoveredService;

    @GetMapping(value = "/")
    public String display() {
        return "home";
    }
    
    
  
    @GetMapping("/search")
    public String searchdata(Model model, @RequestParam (defaultValue="") String country) {
    	  List<LocationStats> allStats = coronaVirusDataService.getAllStats();
   	  List<LocationStatsDeaths> allDeathsStats = coronaVirusDataDeathsService.getAllDeathStats();
   	  List<LocationStatsRecovered> allRecoveredStats = coronaVirusDataRecoveredService.getAllRecoveryStats();
    	  
    	model.addAttribute("data", coronaVirusDataService.findByCountry(country));
    	model.addAttribute("datadeaths", coronaVirusDataDeathsService.findByCountry(country));
    	model.addAttribute("datarecovered", coronaVirusDataRecoveredService.findByCountry(country));
    //	 model.addAttribute("locationStats", coronaVirusDataService.getAllStats());
    	 
    	 int todaysCases = allStats.stream().mapToInt(stat -> Integer.parseInt(stat.getTodaysCases())).sum();
    	 model.addAttribute("todaysCases", todaysCases);
    	 
    	 int totalDeaths = allDeathsStats.stream().mapToInt(stat -> Integer.parseInt(stat.getTotalDeaths())).sum();
    	 model.addAttribute("totalDeaths", totalDeaths);
    	 
    	 int totalRecovered = allRecoveredStats.stream().mapToInt(stat -> Integer.parseInt(stat.getTotalRecovered())).sum();
    	 model.addAttribute("totalRecovered", totalRecovered);
    	return "searchdata.html";
    }
    

    
    
    @GetMapping("/sort")
    public String sortdata(Model model, @RequestParam (defaultValue="") String country) {
    	  List<LocationStats> allStats = coronaVirusDataService.getAllStats();
   	  List<LocationStatsDeaths> allDeathsStats = coronaVirusDataDeathsService.getAllDeathStats();
   	  List<LocationStatsRecovered> allRecoveredStats = coronaVirusDataRecoveredService.getAllRecoveryStats();
    	  
    	model.addAttribute("data", coronaVirusDataService.findByCountry(country));
    	model.addAttribute("datadesc", coronaVirusDataService.findByCountry(country));
    	model.addAttribute("dataasc", coronaVirusDataService.findByCountry(country));
    	model.addAttribute("datadesc", coronaVirusDataService.findAllOrderByTodaysCasesDesc());
    	model.addAttribute("dataasc", coronaVirusDataService.findAllOrderByTodaysCasesAsc());
    	
    	
    	model.addAttribute("datadeaths", coronaVirusDataDeathsService.findByCountry(country));
    	model.addAttribute("datadeathsdesc", coronaVirusDataDeathsService.findAllOrderByTotalDeathsDesc());
    	model.addAttribute("datadeathsasc", coronaVirusDataDeathsService.findAllOrderByTotalDeathsAsc());
    	
    	model.addAttribute("datarecovered", coronaVirusDataRecoveredService.findByCountry(country));
    	model.addAttribute("datarecovereddesc", coronaVirusDataRecoveredService.findAllOrderByTotalRecoveredDesc());
    	model.addAttribute("datarecoveredasc", coronaVirusDataRecoveredService.findAllOrderByTotalRecoveredAsc());
    //	 model.addAttribute("locationStats", coronaVirusDataService.getAllStats());
    	 
    	 int todaysCases = allStats.stream().mapToInt(stat -> Integer.parseInt(stat.getTodaysCases())).sum();
    	 model.addAttribute("todaysCases", todaysCases);
    	 
    	 int totalDeaths = allDeathsStats.stream().mapToInt(stat -> Integer.parseInt(stat.getTotalDeaths())).sum();
    	 model.addAttribute("totalDeaths", totalDeaths);
    	 
    	 int totalRecovered = allRecoveredStats.stream().mapToInt(stat -> Integer.parseInt(stat.getTotalRecovered())).sum();
    	 model.addAttribute("totalRecovered", totalRecovered);
    	return "sortcountries.html";
    }
    
    
    
    
    
    
    @GetMapping("/confirmedcases")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);        
      //  model.addAttribute("keyword", keyword);       
        return "confirmedcases.html";
    }
    
    
    @GetMapping("/deaths")
    public String deaths(Model model) {
        List<LocationStatsDeaths> allDeathsStats = coronaVirusDataDeathsService.getAllDeathStats();
        int totalReportedCases = allDeathsStats.stream().mapToInt(stat -> stat.getLatestTotalDeaths()).sum();
        int totalNewCases = allDeathsStats.stream().mapToInt(stat -> stat.getDiffFromPrevDayDeaths()).sum();
        model.addAttribute("locationStats", allDeathsStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);        
      //  model.addAttribute("keyword", keyword);       
        return "deaths.html";
    }
    
    
    @GetMapping("/recovered")
    public String recovered(Model model) {
        List<LocationStatsRecovered> allRecoveredStats = coronaVirusDataRecoveredService.getAllRecoveryStats();
        int totalReportedCases = allRecoveredStats.stream().mapToInt(stat -> stat.getLatestTotalRecovered()).sum();
        int totalNewCases = allRecoveredStats.stream().mapToInt(stat -> stat.getDiffFromPrevDayRecovered()).sum();
        model.addAttribute("locationStats", allRecoveredStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);        
      //  model.addAttribute("keyword", keyword);       
        return "recovered.html";
    }
    
      
  
    
    
    @RequestMapping(path="confirmed")
    public void saveConfirmedInDB() {
    	coronaVirusDataService.saveData();   	
    }
    
    

  
    
    @RequestMapping(path="death")
    public void saveDeathDataInDB() {   	
    	coronaVirusDataDeathsService.saveDeathsData();   	
    }
    
    
   
    @RequestMapping(path="recovery")
    public void saveRecoveredDataInDB() {    	
    	coronaVirusDataRecoveredService.saveRecoveredData();    	
    }
    
    
    
    


  


 
    
}