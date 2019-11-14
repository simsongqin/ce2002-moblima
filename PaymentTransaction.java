import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.*;

/**
 * Represents a payment transaction of a movie-goer. A Payment Transaction
 * 
 * @author
 * @version
 * @since 2019-11-08
 */
@SuppressWarnings("serial")
public class PaymentTransaction implements Comparable<PaymentTransaction>, Serializable {

	/**
	 * Transaction ID for the booking.
	 */
	private String transactionId;
	/**
	 * Total amount for the booking.
	 */
	private double amount;
	/**
	 * Movie title for the booking.
	 */
	private String movieTitle;
	/**
	 * Movie start time for the booking.
	 */
	private Date startTime;
	/**
	 * Booked seats for the booking.
	 */
	private String[] seatIds;
	/**
	 * Name of the movie-goer for the booking.
	 */
	private String name;
	/**
	 * Mobile number of the movie-goer for the booking.
	 */
	private String mobileNumber;
	/**
	 * Email of the movie-goer for the booking.
	 */
	private String email;
	/**
	 * Cinema ID for the booking.
	 */
	private String cinemaId;

	/**
	 * Creates a new PaymentTransaction with the given parameters and generate a
	 * transaction ID for the customer.
	 * 
	 * @param amount       This payment transaction's amount.
	 * @param movieTitle   This payment transaction's movie tile.
	 * @param cinemaId     This payment transaction's cinema ID.
	 * @param startTime    This payment transaction's start time.
	 * @param seatIds      This payment transaction's seat IDs.
	 * @param name         This payment transaction's movie-goer name.
	 * @param mobileNumber This payment transaction's movie-goer mobile number.
	 * @param email        This payment transaction's movie-goer email.
	 */
	public PaymentTransaction(double amount, String movieTitle, String cinemaId, Date startTime, String[] seatIds,
			String name, String mobileNumber, String email) {
		this.amount = amount;
		this.movieTitle = movieTitle;
		this.cinemaId = cinemaId;
		this.startTime = startTime;
		this.seatIds = seatIds;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.email = email;

		LocalDateTime localDateTime = LocalDateTime.now();
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		this.transactionId = cinemaId + simpleDateFormat.format(date);
	}

	/**
	 * Gets the email of this PaymentTransaction.
	 * 
	 * @return this PaymentTransaction's email.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Print details of the booking history of a payment transaction.
	 */
	public void printDetails() {
		System.out.println(" ");
		System.out.println("Your TID is: " + transactionId);
		System.out.println("Customer name: " + name);
		System.out.println("Customer email: " + email);
		System.out.println("Customer mobile number: " + mobileNumber);
		System.out.println("Cinema ID: " + cinemaId);
		System.out.println("The movie you booked: " + movieTitle);
		System.out.println("Showtime: " + startTime);
		System.out.print("The seat number is: ");
		for (String seatId : seatIds)
			System.out.print(seatId + " ");
		System.out.println(" ");
		System.out.println("The total amount is SGD " + amount);
	}

	/**
	 * Used to compare to and return the input payment transaction against the
	 * calling parameter payment transaction.
	 * 
	 * @param paymentTransaction is the value the calling payment transaction is
	 *                           being compared against.
	 * @return boolean value. It will be 0 if equal, positive integer if calling
	 *         paymentTransaction is later and negative integer if it is earlier.
	 */
	public int compareTo(PaymentTransaction paymentTransaction) {
		return this.transactionId.compareTo(paymentTransaction.transactionId);
	}
}
