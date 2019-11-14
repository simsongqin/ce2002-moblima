import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Cineplex implements Serializable {

	private ArrayList<Cinema> cinemas;
	private String name;
	private String cineplexId;

	/**
	 * 
	 * @param name
	 * @param cinemaId
	 */
	public Cineplex(String n, String Id) {
		cinemas = new ArrayList<Cinema>();
		name = n;
		cineplexId = Id;
	}

	public String getName() {
		return this.name;
	}

	public Cinema getCinemaById(String cinemaId) {
		for (int i = 0; i < cinemas.size(); i++) {
			if (cinemas.get(i).getCinemaId().equals(cinemaId)) {
				return cinemas.get(i);
			}
		}
		System.out.println("Couldn't find cinema with that ID!");
		return null;
	}

	/**
	 * 
	 * @param cinema
	 */

	public void addCinema(Cinema cinema) {
		cinemas.add(cinema);
	}

	public String getCineplexId() {
		return this.cineplexId;
	}

	public void printDetails() {
		System.out.println(" ");
		System.out.println("name: " + name);
		System.out.println("ID: " + cineplexId);
		System.out.println("no. of cinemas: " + cinemas.size());
	}

	public void printCinemas() {
		System.out.println(" ");
		System.out.println(name + " cineplex cinemas.");
		for (int i = 0; i < cinemas.size(); i++) {
			System.out.println(cinemas.get(i).getCinemaId());
		}
	}

}