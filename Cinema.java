import java.util.*;
import java.io.*;

@SuppressWarnings("serial")

/**
 * Represents a Cinema Hall of the Cineplex. A cinema consists of the showtimes
 * being shown inside. It also has its own cinema ID and is defined as being of
 * a cinema class type.
 * 
 *
 */
public class Cinema implements Serializable {
	/**
	 * An arraylist of showtimes being showm in the cinema.
	 */
	public ArrayList<Showtime> showtimes;
	/**
	 * The cinema ID of the cinema.
	 */
	private String cinemaId;
	/**
	 * The cinema class of the cinema.
	 */
	private String cinemaClass;

	/**
	 * Constructs a cinema with the input paramaters as details and also declares an
	 * arraylist of the showtimes available.
	 * 
	 * @param cinemaId    is the new cinema's cinema ID.
	 * @param cinemaClass specifies the cinema class of the new cinema.
	 */
	public Cinema(String cinemaId, String cinemaClass) {
		this.cinemaId = cinemaId;
		this.cinemaClass = cinemaClass;
		showtimes = new ArrayList<Showtime>();
	}

	/**
	 * Used to obtain the cinema ID of the cinema.
	 * 
	 * @return cinemaId of the cinema.
	 */
	public String getCinemaId() {
		return cinemaId;
	}

	/**
	 * Used to obtain the cinema class of the cinema.
	 * 
	 * @return cinemaClass of the cinema.
	 */
	public String getCinemaClass() {
		return cinemaClass;
	}

	/**
	 * Prints the cinema ID and cinema class of the cinema.
	 */
	public void printDetails() {
		System.out.println(" ");
		System.out.println("Name of Cinema: " + this.cinemaId);
		System.out.println("Cinema Class: " + this.cinemaClass);
	}

	/**
	 * Prints the details of each of the showtimes in the list of showtimes of the
	 * cinema.
	 */
	public void printShowtimes() {
		if(this.showtimes.size()==0) {
			System.out.println(" ");
			System.out.println("We are sorry, there is currently no showtime for this cinema.");
			return;
		}
		for (int i = 0; i < this.showtimes.size(); i++) {
			this.showtimes.get(i).printDetails();
		}
	}

	/**
	 * Returns the showtime based on the input showtime ID.
	 * 
	 * @param showtimeId of the showtime being searched for.
	 * @return showtime with the same showTime ID. Returns null if no search
	 *         showtime exists.
	 */
	public Showtime getShowtimeById(String showtimeId) {
		for (Showtime showtime : showtimes) {
			if (showtimeId.equals(showtime.getShowtimeId()))
				return showtime;
		}
		return null;
	}

	/**
	 * Function to add a new showtime to the list of showtimes present.
	 * 
	 * @param showtime is the input showtime to be added.
	 */
	public void addShowtime(Showtime showtime) {
		showtimes.add(showtime);
	}

	/**
	 * Function to delete a showtime from the list of available showtimes.
	 * 
	 * @param showtime is the input showtime to be removed.
	 */
	public void deleteShowtime(String showtimeId) {
		for (Showtime showtime : showtimes) {
			if (showtime.getShowtimeId().equals(showtimeId)) {
				showtimes.remove(showtime);
				return;
			}
		}
	}
}
