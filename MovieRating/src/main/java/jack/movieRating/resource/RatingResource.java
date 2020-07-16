package jack.movieRating.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jack.movieRating.model.Rating;
import jack.movieRating.model.UserRating;

@RestController
@RequestMapping("rating")
public class RatingResource {

	@RequestMapping("{movieId}")
	public Rating getRating(@PathVariable String movieId) {
		return new Rating(movieId, 5);
	}
	
	@RequestMapping("user/{userId}")
	public UserRating getUserRating(@PathVariable String userId) {
		List<Rating> rating = Arrays.asList(
				new Rating("123", 4),
				new Rating("234", 3)
				);
		
		UserRating userRating = new UserRating();
		userRating.setUserRating(rating);
		return userRating;
	}
	
	
}
