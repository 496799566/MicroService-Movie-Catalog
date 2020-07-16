package jack.movieCatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import jack.movieCatalog.model.CatalogItem;
import jack.movieCatalog.model.Movie;
import jack.movieCatalog.model.Rating;

@Service
public class MovieInfoService {

	@Autowired
	RestTemplate rt ;
	
	@HystrixCommand(
		fallbackMethod = "getFallbackCatalogItem",
		threadPoolKey = "movieInfoPool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "10"), // maximum number of thread that can run at a particular time
			@HystrixProperty(name = "maxQueueSize", value = "5") // maximum number of request that can stay in a queue before handled by any thread above
		}	
	)
	public CatalogItem getCatalogItem(Rating r) {
		
		Movie movie = rt.getForObject("http://MOVIEINFO/movies/"+ r.getMovieId(), Movie.class);
		
		return new CatalogItem( movie.getName(), movie.getOverview(), r.getRating());
		
	}
	
	public CatalogItem getFallbackCatalogItem(Rating r) {
		
		return new CatalogItem( "no movie name", "no overview", r.getRating());
		
	}
}
