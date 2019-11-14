import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("serial")

/**
 * Represents a Movie Manager of the movie. A Movie Manager manages the movies and
 * update the details of the movies.
 * 
 *
 */

public class MovieManager implements Serializable {
	
	/**
	 * An array list of movies.
	 * 
	 */
	private ArrayList<Movie> movies;
	
	/**
	 * Creates a movie manager who has a record of the movies
	 * 
	 */
	public MovieManager() {
		this.movies = new ArrayList<Movie>();
	}

	/**
	 * Prints each movies according to the printOutline in Movie.
	 * 
	 */
	public void printMovies() {
		if(movies.size()==0) {
			System.out.println(" ");
			System.out.println("No movies currently available.");
			return;
		}
		for (int i = 1; i <= movies.size(); i++) {
			System.out.print(i + ". ");
			movies.get(i - 1).printOutline();
			System.out.printf("\n");
		}
	}

	public int compare(Movie m1, Movie m2) {
		return (int) Math.round(m1.getSales()-m2.getSales());
	}
	/**
	 * Prints the top 5 movies based on sales 
	 * and according to the printOutline in Movie.
	 * 
	 */
	public void printTop5MoviesBySales() {
		if(movies.size()==0) {
			System.out.println(" ");
			System.out.println("No movie is currently available in Top 5 Movies By Sales.");
			return;
		}
		try {
			System.out.println(" ");
			System.out.println("Top 5 Movies by Sales: ");
			Movie[] moviesClone = movies.toArray(new Movie[movies.size()]);
			Arrays.sort(moviesClone, new Comparator<Movie>() {public int compare(Movie m1, Movie m2) {
					return (int)(m1.getSales()-m2.getSales());
				}
			});

			for(int i=0;i<moviesClone.length&&i<5;i++) {
				System.out.println(moviesClone[i].getTitle());
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints the top 5 movies based on ratings 
	 * and according to the printOutline in Movie.
	 * 
	 */
	public void printTop5MoviesByRatings() {
		if(movies.size()==0) {
			System.out.println(" ");
			System.out.println("No movie is currently available in Top 5 Movies By Ratings.");
			return;
		}
		try {
			System.out.println(" ");
			System.out.println("Top 5 Movies by Ratings: ");
			
		}
		catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a new movie
	 * Ask administrator to input the title, duration, showing status
	 * type, synopsis, director, casts and movie rating of the movie
	 * before adding to the arraylist of the movie
	 * 
	 */
	public void createMovie() {
		Scanner sc = new Scanner(System.in);
		System.out.println(" ");
		System.out.println("Please enter the following details for the movie. ");

		System.out.println(" ");
		System.out.println("Title: ");
		String title = sc.nextLine();

		String lengthInMinutesOld="";
		int lengthInMinutes=0;
		do {
			System.out.println(" ");
			System.out.println("Duration in minutes: ");
			lengthInMinutesOld = sc.nextLine();
			if(lengthInMinutesOld.matches("^[0-9]+$")) {
				lengthInMinutes = Integer.parseInt(lengthInMinutesOld);
				break;
			}
			System.out.println("Please do not enter non-numeric value.");
		}while(true);
		
		String showingStatus="";
		do {
			System.out.println(" ");
			System.out.println("Showing status - (Coming Soon, Preview, Now Showing): ");
			showingStatus = sc.nextLine();
			if(showingStatus.equals("Coming Soon")||showingStatus.equals("Preview")||showingStatus.equals("Now Showing"))
				break;
			System.out.println("Please enter the choice available only.");
		}while(true);

		String movieType="";
		do {
			System.out.println(" ");
			System.out.println("Type - (2D, 3D): ");
			movieType = sc.nextLine();
			if(movieType.equals("2D")||movieType.equals("3D"))
				break;
			System.out.println("Please enter the choice available only.");
		}while(true);

		System.out.println(" ");
		System.out.println("Synopsis: ");
		String synopsis = sc.nextLine();

		System.out.println(" ");
		System.out.println("Director: ");
		String director = sc.nextLine();

		int castNo = 1;
		String cast = "";
		ArrayList<String> casts = new ArrayList<String>();
		System.out.println(" ");
		System.out.println("Cast "+castNo+": ");
		cast = sc.nextLine();
		casts.add(cast);
		castNo++;
		System.out.println(" ");
		System.out.println("Cast "+castNo+": ");
		cast = sc.nextLine();
		castNo++;
		while (!cast.equalsIgnoreCase("stop")) {
			casts.add(cast);
			System.out.println(" ");
			System.out.println("Enter cast "+castNo+" or enter 'stop' to end.");
			cast = sc.nextLine();
			castNo++;
		}
		
		String movieRating="";
		do {
			System.out.println(" ");
			System.out.println("Rating (G, PG13, NC16, M18, R21): ");
			movieRating = sc.nextLine();
			if(movieRating.equals("G")||movieRating.equals("PG13")||movieRating.equals("M18")||movieRating.equals("NC16")||movieRating.equals("R21"))
				break;
			System.out.println("Please enter the choice available only.");
		}while(true);
		
		Movie movie = new Movie(title, lengthInMinutes, showingStatus, movieType, synopsis, director, casts,
				movieRating);
		this.movies.add(movie);
	}

	/**
	 * Update the Movie after selecting the specific movie to change
	 * Asks the administrator to updates director, casts, showing status
	 * and synopsis of the movie
	 * 
	 */
	public void updateMovie() {
		Scanner sc = new Scanner(System.in);
		Movie movie = selectMovie();

		if(movie==null) {
			System.out.println(" ");
			System.out.println("There is currently no movie available for update.");
			return;
		}
		
		String showingStatus="";
		do {
			System.out.println(" ");
			System.out.println("Showing status - (Coming Soon, Preview, Now Showing, End of Showing): ");
			showingStatus = sc.nextLine();
			if(showingStatus.equals("End of Showing")) {
				movie.setShowingStatus(showingStatus);
				movies.remove(movie);
				return;
			}
			if(showingStatus.equalsIgnoreCase("coming soon")||showingStatus.equalsIgnoreCase("preview")||showingStatus.equalsIgnoreCase("now showing"))
				break;
			System.out.println("Please enter the choice available only.");
		}while(true);
		
		
		System.out.println(" ");
		System.out.print("Director: ");
		String director = sc.nextLine();

		int castNo = 1;
		String cast = "";
		ArrayList<String> casts = new ArrayList<String>();
		System.out.println(" ");
		System.out.println("Cast "+castNo+": ");
		cast = sc.nextLine();
		casts.add(cast);
		castNo++;
		System.out.println(" ");
		System.out.println("Cast "+castNo+": ");
		cast = sc.nextLine();
		castNo++;
		while (!cast.equalsIgnoreCase("stop")) {
			casts.add(cast);
			System.out.println(" ");
			System.out.println("Enter cast "+castNo+" or enter 'stop' to end.");
			cast = sc.nextLine();
			castNo++;
		}

		System.out.println(" ");
		System.out.print("Synopsis: ");
		String synopsis = sc.nextLine();

		movie.setDirector(director);
		movie.setCasts(casts);
		movie.setShowingStatus(showingStatus);
		movie.setSynopsis(synopsis);
		
	}
	
	/**
	 * Select the movie that wants to be removed
	 * 
	 */
	public void deleteMovie() {
		Movie movie = selectMovie();
		if(movie==null) {
			System.out.println(" ");
			System.out.println("There is currently no movie available for deletion.");
			return;
		}
		movies.remove((movies.indexOf(movie)));
	}

	/**
	 * Create a new review
	 * Ask users to input the nickname and content of the review, 
	 * rating and title of the movie
	 * 
	 */
	public void createReview() {
		Movie movie = selectMovie();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Preferred nickname to be shown: ");
		String nickname = sc.nextLine();

		String ratingOld = "";
		int rating=0;
		System.out.println(" ");
		System.out.println("Rating for the movie (0-5): ");
		do {
			ratingOld = sc.nextLine();
			if(ratingOld.matches("^[0-5]+$")) {
				rating = Integer.parseInt(ratingOld);
				break;
			}
			System.out.println("Please only enter integers (0-5):");
		}while(true);
		
		System.out.println(" ");
		System.out.println("Review title: ");
		String title = sc.nextLine();

		System.out.println(" ");
		System.out.println("Your comments: ");
		String content = sc.nextLine();

		Review review = new Review(nickname, rating, title, content);
		movie.addReview(review);
	}

	/**
	 * print out the list of movies and ask users to select the movie
	 * 
	 */
	public Movie selectMovie() {
		if(movies.size()==0)
			return null;
		for (int i = 1; i <= movies.size(); i++) {
			System.out.print(i + ". ");
			movies.get(i - 1).printOutline();
			System.out.printf("\n");
		}
		Scanner sc = new Scanner(System.in);
		int c=0;
		String cOld = "";
		retry: while(true){
			System.out.println(" ");
			System.out.print("Enter the movie no: ");
			try {
				cOld = sc.next();
				c = Integer.parseInt(cOld);
				if(c > movies.size()) {
					System.out.println(" ");
					System.out.println("You have selected an incorrect movie no.");
					continue retry;
				}
				break;
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter a numeric value.");
			}
			
		}
		return movies.get(c - 1);
	}

}
