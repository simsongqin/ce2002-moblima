import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
/**
 * Represents a Review of the Movie. A Review consists of the nickname of the
 * user writing the review. It also has rating of the movie, movie title and the
 * content of review
 * 
 *
 */
public class Review implements Comparable<Review>, Serializable {
	
	/**
	 * The nickname of the user that's writing the review
	 */
	private String nickname;
	
	/**
	 * The rating of the movie. Out of 5.
	 */
	private int rating;
	
	/**
	 * The title of the movie.
	 */
	private String title;
	
	/**
	 * The content of the review.
	 */
	private String content;

	/**
	 * Constructs review with the input paramaters as details
	 * 
	 * @param nickname is the user's nickname.
	 * @param rating   specifies the rating of the movie.
	 * @param title    specifies the title of the movie.
	 * @param content  specifies the content of the review.
	 * 
	 */
	public Review(String nickname, int rating, String title, String content) {
		this.nickname = nickname;
		this.rating = rating;
		this.title = title;
		this.content = content;
	}

	public int getRating()
	{
		return rating;
	}
	
	/**
	 * Prints the nickname, title of movie, rating of movie and content of the review.
	 */
	public void printDetails() {
		System.out.println(" ");
		System.out.print("Name: " + nickname + "\n" + "\n" + "Rating: " + rating
				+ "\n" +"Title: "+title+ "\n"+ "Comments: " + content + "\n");
	}

	/**
	 * Used to compare to and return the input review rating against the
	 * calling parameter review rating.
	 * 
	 * @param review rating is the value the calling review rating is
	 *                           being compared against.
	 * @return boolean value. It will be 0 if equal, positive integer if calling
	 *         rating is later and negative integer if it is earlier.
	 */
	public int compareTo(Review review) {
		return (int) (this.rating - review.rating);
	}
}
