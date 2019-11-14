import java.io.*;

@SuppressWarnings("serial")

/**
 * Represents a seat in the cinema. A seat has a seatId and can be occupied or
 * not occupied.
 * 
 *
 */
public class Seat implements Serializable {
	/**
	 * The seat ID of the seat.
	 */
	private String seatId;
	/**
	 * Boolean variable which indicates if the seat is occupied.
	 */
	private boolean assigned;

	/**
	 * Creates a new seat with the given seatId and assigns it by default to false
	 * or not occupied
	 * 
	 * @param seatId This seat's seatId.
	 */
	public Seat(String seatId) {
		this.seatId = seatId;
		this.assigned = false;
	}

	/**
	 * Gets the seat's seatId.
	 * 
	 * @return this seat's seatId.
	 */
	public String getSeatId() {
		return this.seatId;
	}

	/**
	 * Checks whether a seat is occupied or not.
	 * 
	 * @return the boolean value for if the seat is occupied.
	 */
	public boolean isOccupied() {
		return this.assigned;
	}

	/**
	 * Used to modify a seat and make it occupied.
	 */
	public void assign() {
		this.assigned = true;
	}

	/**
	 * Use to modify a seat and make it unoccupied.
	 */
	public void unAssign() {
		this.assigned = false;
	}

}