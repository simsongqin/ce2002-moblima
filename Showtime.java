import java.io.*;
import java.util.*;

@SuppressWarnings("serial")

/**
 * Represents a showtime in the cinema. Each showtime has value pertaining to
 * the seat information, ID, number of empty seats available, the movie being
 * played and the starttime of the showtime.
 */

public class Showtime implements Comparable<Showtime>, Serializable {
	/**
	 * An array of seats in the showtime.
	 */
	private Seat[] seats;
	/**
	 * The showtimeId of the showtime.
	 */
	private String showtimeId;
	/**
	 * The number of empty seats for the showtime.
	 */
	private int numEmptySeats=0;
	/**
	 * The movie being played in the showtime.
	 */
	private Movie movie;
	/**
	 * The starting time of the showtime.
	 */
	private Date startTime;

	/**
	 * Constructs a new showtime.
	 * 
	 * @param showtimeId The id of the showtime created.
	 * @param movie      The movie being played at the showtime.
	 * @param startTime  The starting time of the showtime.
	 */
	public Showtime(String showtimeId, Movie movie, Date startTime) {
		this.showtimeId = showtimeId;
		this.movie = movie;
		this.startTime = startTime;
		seats = new Seat[90];
		int cc = 1;
		for(int i = 0;i<90;i++)
			{
				if(cc==11)
					cc=1;
				char a = (char)((65+((i)/10)));
				String aa=Character.toString(a);
				String b = Integer.toString(cc);
				seats[i]=new Seat(aa.concat(b));
				cc++;
			}
	}

	/**
	 * Prints the details of the showtime including the seat layout.
	 */
	public void printDetails() {
		System.out.println(" ");
		System.out.println(showtimeId + " " + movie.getTitle() + " starts at " + startTime.toString());
	}

	/**
	 * Assigns the given seatId if it is available. It will return a true if the
	 * operation is successful. It will return a false if the input seatId is
	 * invalid or the seat was previously occupied.
	 * 
	 * @param seatId - seatId user is trying to book.
	 * @return boolean outcome of the operation.
	 */
	public boolean assignSeat(String seatId) {
		int c1;
		char c = ' ';

		if ((!(seatId.length() == 2 || seatId.length() == 3)) || (!Character.isLetter(seatId.charAt(0)))) {
			return false;
		}
		c = seatId.charAt(0);
		c = Character.toUpperCase(c);
		char cc = seatId.charAt(1);
		c1 = Character.getNumericValue(cc);
		if (seatId.length() == 3) {
			if (!((Character.getNumericValue(seatId.charAt(1)) == 1)
					&& (Character.getNumericValue(seatId.charAt(2)) == 0))) {
				return false;
			} else
				c1 = 10;
		}

		if (!(c >= 'A' && c <= 'J' && c1 >= 1 && c1 <= 10)) {
			return false;
		}
		c1--;

		int x = ((int) (c)) % 65;
		if (seats[((x * 10) + c1)].isOccupied())
			return false;
		else {
			seats[((x * 10) + c1)].assign();
			return true;
		}
	}

	/**
	 * Unassigns the input seatId if it was previously occupied.
	 * 
	 * @param seatId the seat Id user is trying to unassign.
	 * @return boolean outcome of the operation. Operation returns true if
	 *         successful. Returns false if the seat was always unoccupied or the
	 *         seatId format is incorrect.
	 */
	public boolean unAssignSeatId(String seatId) {
		int c1;
		char c = ' ';

		if ((!(seatId.length() == 2 || seatId.length() == 3)) || (!Character.isLetter(seatId.charAt(0)))) {
			return false;
		}
		c = seatId.charAt(0);
		c = Character.toUpperCase(c);
		char cc = seatId.charAt(1);
		c1 = Character.getNumericValue(cc);
		if (seatId.length() == 3) {
			if (!((Character.getNumericValue(seatId.charAt(1)) == 1)
					&& (Character.getNumericValue(seatId.charAt(2)) == 0))) {
				return false;
			} else
				c1 = 10;
		}

		if (!(c >= 'A' && c <= 'J' && c1 >= 1 && c1 <= 10)) {
			return false;
		}

		c1--;

		int x = ((int) (c)) % 65;

		if (!(seats[((x * 10) + c1)].isOccupied()))
			return false;
		else {
			seats[((x * 10) + c1)].unAssign();
			return true;
		}
	}

	/**
	 * To get the Id of the showtime
	 * 
	 * @return showtimeId of the showtime.
	 */
	public String getShowtimeId() {
		return showtimeId;
	}

	/**
	 * To get the movie being played at the showtime.
	 * 
	 * @return movie in the showtime.
	 */
	public Movie getMovie() {
		return movie;
	}

	/**
	 * Used to obtain the starting time of the showtime.
	 * 
	 * @return startTime of the showtime.
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Used to print out the seat layout of the showtime's seats according to the
	 * pre-existing seat booking details.
	 */
	public void printSeatLayout() {
		int c = 65;
		int s = 0;
		System.out.println(movie.getSales());
		System.out.println("\t\t\t\t\t^ SCREEN THIS WAY ^");
		for (int i = 1; i <= 13; i++) {
			if (i == 1 || i == 6)
				System.out.println("\u25C1\t\t\t\t\t\t\t\t\t\t\t\t\u25B7");
			else if (i == 5 || i == 13) {
				System.out.print("\t");
				for (int j = 1; j <= 10; j++) {
					System.out.print(j + "\t");
					if (j == 5)
						System.out.print("\t");
				}
				System.out.println("");
			} else {
				System.out.print((char) (c) + "\t");
				c++;
				for (int j = 0; j < 10; j++) {
					if (seats[s + j].isOccupied()) {
						System.out.print("\u25A0\t");
					} else {
						System.out.print("\u25A1\t");
						numEmptySeats++;
					}
					if (j == 4)
						System.out.print("\t");
				}
				s += 10;
				System.out.println("");
			}
		}
		System.out.println(numEmptySeats+" left");
		numEmptySeats=0;
	}

	/**
	 * Used to compare to and return the input showtime's starttime against the
	 * calling parameter showtime's start time.
	 * 
	 * @param showtime is the value the calling showtime's starttime is being
	 *                 compared against.
	 * @return boolean value. It will be 0 if equal, positive integer if calling
	 *         showtime's startime is later and negative integer if it is earlier.
	 */
	public int compareTo(Showtime showtime) {
		int x = this.startTime.compareTo(showtime.startTime);
		return x;
	}

}
