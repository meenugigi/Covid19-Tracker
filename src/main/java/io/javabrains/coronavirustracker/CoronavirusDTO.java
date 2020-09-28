package io.javabrains.coronavirustracker;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoronavirusDTO extends CrudRepository<LocationStats, String>{


	List<LocationStats> findByCountryLike(String country);
	
	
	
	List<LocationStats> findAll();

	 @Query("select country from LocationStats")
	List<LocationStats> getAllCountry();

	 @Query("select todaysCases from LocationStats")
	List<LocationStats> getAllTodaysCases();

	
	LocationStats findByCountry(String country);

	 @Query("select Id from LocationStats")
	LocationStats findById(Integer Id);


	 @Query("select country from LocationStats")
	LocationStats  findAllCountry();


	 @Query("FROM LocationStats ORDER BY length(todaysCases) DESC, todaysCases DESC")
	    List<LocationStats> findAllOrderByTodaysCasesDesc();

	 

	 @Query("FROM LocationStats ORDER BY length(todaysCases) ASC, todaysCases ASC")
	    List<LocationStats> findAllOrderByTodaysCasesAsc();

	

}
