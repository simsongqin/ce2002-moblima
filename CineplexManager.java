import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class CineplexManager implements Serializable {

	private ArrayList<Cineplex> cineplexes;

	public CineplexManager() {
		cineplexes = new ArrayList<Cineplex>();
	}

	/**
	 * 
	 * @param cineplexId
	 */
	public Cineplex getCineplexById(String cineplexId) {
		for (int i = 0; i < cineplexes.size(); i++) {
			if (cineplexes.get(i).getCineplexId().equals(cineplexId)) {
				return cineplexes.get(i);
			}
		}
		System.out.println("Couldn't find cineplex with that ID!");
		return null;
	}

	/**
	 * 
	 * @param cineplex
	 */
	public void addCineplex(Cineplex cineplex) {
		cineplexes.add(cineplex);
	}

	public void printCineplexes() {
		for (int i = 0; i < cineplexes.size(); i++) {
			System.out.println(cineplexes.get(i).getName());
		}
	}

}
