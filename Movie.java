import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")

/**
 * Represents a Movie. A Movie consists of the review of the Movie
 * It also has rating of the movie, movie rating (PG13, NC16..), movie title,
 * duration of the movie, showing status of the movie, type of movie, the synopsis of the movie
 * director of the movie, casts of the movie and sales of the movie
 * 
 *
 */
public class Movie implements Comparable<Movie>, Serializable {

	/**
	 * An arraylist of reviews of the movie.
	 */
	private ArrayList<Review> reviews;
	
	/**
	 * The rating of the movie.
	 */
	private double rating; // 3, 4, 5
	
	/**
	 * The movie rating (PG13, NC16..) of movie.
	 */
	private String movieRating; // PG13, NC16...
	
	/**
	 * The title of the movie.
	 */
	private String title;
	
	/**
	 * The duration of the movie.
	 */
	private int lengthInMinutes;
	
	/**
	 * The showingStatus of the movie.
	 */
	private String showingStatus;
	
	/**
	 * The genre of the movie.
	 */
	private String movieType;
	
	/**
	 * The synopsis of the movie.
	 */
	private String synopsis;
	
	/**
	 * The director of the movie.
	 */
	private String director;
	
	/**
	 * An arraylist of casts of the movie.
	 */
	private ArrayList<String> casts;
	
	/**
	 *Total sales of the movie.
	 */
	private long sales;
	
	
	/**
	 * Constructs review with the input paramaters as details
	 * 
	 * @param title is the title of the movie
	 * @param lengthInMinutes   specifies the duration of the movie.
	 * @param showingStatus    specifies the showing status of the movie.
	 * @param movieType  specifies the type of the movie. (2D/3D)
	 * @param synopsis  specifies the synopsis of the movie.
	 * @param director  specifies the director of the movie.
	 * @param casts  specifies the casts of the movie.
	 * @param movieRating  specifies the movie rating (PG13, NC16...) of the movie.
	 * 
	 */
	public Movie(String title, int lengthInMinutes, String showingStatus, String movieType, String synopsis,
			String director, ArrayList<String> casts, String movieRating) {
		this.title = title;
		this.lengthInMinutes = lengthInMinutes;
		this.movieType = movieType;
		this.showingStatus = showingStatus;
		this.synopsis = synopsis;
		this.director = director;
		this.casts = casts;
		this.movieRating = movieRating;
		this.reviews = new ArrayList<Review>();
		this.sales = this.getSales();

	}

	/**
	 * Prints the movie type(2D/3D), title of movie and duration of movie.
	 */
public void printOutline() {
		System.out.print(" ");
		System.out.print("Title: " + title);
		System.out.print(" Type: " + movieType);
		System.out.print(" Length: " + lengthInMinutes + "min ");
		System.out.println("Status: " + showingStatus);
	}

	/**
	 * Prints the movie type (2D/3D), title of movie, duration of movie, showing status in the movie, synopsis of the movie
	 * director of the movie, casts of movie, rating of movie out of 5.
	 */
	public void printDetails() {
		System.out.println(" ");
		String ratingIfLessThanOne = "";
		if(reviews.size()==0||reviews.size()==1) {
			ratingIfLessThanOne = String.valueOf(rating);
			ratingIfLessThanOne = "NA";
			System.out.print("Movie Type: " + movieType + "     " + "Title: "+ title + "     " + "Duration of movie: " + lengthInMinutes + " minutes" + "\n" + showingStatus
					+ "\n" + "Synopsis: " + synopsis + "\n" + "Director: " + director + "\n" + "Casts: " + casts + "\n"
					+ "Movie ratings: " + ratingIfLessThanOne + "\n");
			reviews.forEach((reviews) -> reviews.printDetails());
			return;
		}
		else {
			rating = Math.round(rating * 10) / 10.0;
			System.out.print("Movie Type: " + movieType + "     " + "Title: "+ title + "     " + "Duration of movie: " + lengthInMinutes + " minutes" + "\n" + showingStatus
					+ "\n" + "Synopsis: " + synopsis + "\n" + "Director: " + director + "\n" + "Casts: " + casts + "\n"
					+ "Movie ratings: " + rating + "\n");
			reviews.forEach((reviews) -> reviews.printDetails());
		}
			
	}

	/**
	 * Used to add the review.
	 * 
	 */
	public void addReview(Review review) {
		reviews.add(review);
		rating = (rating*(reviews.size()-1))+review.getRating();
		rating = rating/reviews.size();
	}

	/**
	 * Used to obtain the movie rating(PG13, NC16...) of the movie.
	 * 
	 * @return movieRating of the movie.
	 */
	public String getMovieRating() {
		return this.movieRating;
	}
	
	/**
	 * Used to set the movie rating of the movie.
	 * 
	 */
	public void setMovieRating(String movieRating) {
		this.movieRating = movieRating;
	}

	/**
	 * Used to obtain the title of the movie.
	 * 
	 * @return title of the movie.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Used to obtain the showing status of the movie.
	 * 
	 * @return showingStatus of the movie.
	 */
	public String getShowingStatus() {
		return this.showingStatus;
	}

	/**
	 * Used to set the showing status of the movie.
	 * 
	 */
	public void setShowingStatus(String showingStatus) {
		this.showingStatus = showingStatus;
	}

	/**
	 * Used to obtain the type of the movie.
	 * 
	 * @return movieType of the movie.
	 */
	public String getMovieType() {
		return this.movieType;
	}
	
	/**
	 * Used to obtain the synopsis of the movie.
	 * 
	 * @return synopsis of the movie.
	 */
	public String getSynopsis() {
		return this.synopsis;
	}

	/**
	 * Used to set the synopsis of the movie.
	 * 
	 */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	/**
	 * Used to obtain the director of the movie.
	 * 
	 * @return director of the movie.
	 */
	public String getDirector() {
		return this.director;
	}

	/**
	 * Used to set the director of the movie.
	 * 
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * Used to obtain the arraylist of the casts of the movie.
	 * 
	 * @return casts of the movie.
	 */
	public ArrayList<String> getCasts() {
		return this.casts;
	}

	/**
	 * Used to set the casts of the movie.
	 * 
	 */
	public void setCasts(ArrayList<String> casts) {
		this.casts = casts;
	}

	/**
	 * Used to increment the sales of the movie. By adding the amount.
	 * 
	 */
	public void increaseSales(int amount) {
		this.sales += (long)amount;
	}
	
	/**
	 * Used to obtain the sales of the movie.
	 * 
	 * @return sales of the movie.
	 */
	
	public long getSales() {
		return sales;
	}

	/**
	 * Used to compare to and return the input movie rating against the
	 * calling parameter movie rating.
	 * 
	 * @param movie rating is the value the calling movie rating is
	 *                           being compared against.
	 * @return boolean value. It will be 0 if equal, positive integer if calling
	 *         movie is later and negative integer if it is earlier.
	 */
	public int compareTo(Movie movie) {
		return (int)(movie.getSales()-this.sales);
	}
}
