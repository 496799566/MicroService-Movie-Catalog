package jack.movieCatalog.resource;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import jack.movieCatalog.model.CatalogItem;
import jack.movieCatalog.model.UserRating;
import jack.movieCatalog.service.MovieInfoService;
import jack.movieCatalog.service.RatingInfoService;

@RestController
@RequestMapping("catalog")
public class MovieCatalogResource {

	@Autowired
	RestTemplate rt ;
	
	@Autowired
	WebClient.Builder wb ;

	@Autowired
	MovieInfoService movieInfoService;
	
	@Autowired
	RatingInfoService ratingInfoService;

	@RequestMapping("{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId) {
		
		// get all rated movie IDs
		UserRating userRatings = ratingInfoService.getUserRating(userId);
				
		return userRatings
				.getUserRating()
				.stream()
				.map( r -> movieInfoService.getCatalogItem(r))
				.collect(Collectors.toList());
		
	}


}

/*
Movie movie = wb.build()
				.get()
				.uri("http://localhost:8082/movies/"+ r.getMovieId())
				.retrieve()
				.bodyToMono(Movie.class)
				.block();

*/