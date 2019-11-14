import java.util.*;
import java.io.*;

public class MOBLIMA {

	public static void Serialize(String filename, Object obj) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(filename);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		if (filename.equals("BookingManager.dat")) {
			objectOutputStream.writeObject((BookingManager) obj);
		} else {
			objectOutputStream.writeObject((MovieManager) obj);
		}
		objectOutputStream.flush();
		objectOutputStream.close();
	}

	public static Object DeSerialize(String fileName) throws IOException, ClassNotFoundException {

		FileInputStream fileInputStream = new FileInputStream(fileName);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

		BookingManager bm = null;
		MovieManager mm = null;
		

		if (fileName.equals("BookingManager.dat")) {
			bm = (BookingManager) objectInputStream.readObject();
		} 
		else if(fileName.contentEquals("MovieManager.dat")){
			mm = (MovieManager) objectInputStream.readObject();
		}
		
		objectInputStream.close();
		if (fileName.equals("BookingManager.dat")) {
			return bm;
		} 
		else
			return mm;
		
	}

	private static String password = "a";

	public static void main(String[] args) throws Exception {

		File movieDat = new File("MovieManager.dat");
		File bookingDat = new File("BookingManager.dat");
		
		BookingManager bm = null;
		MovieManager mm = null;

		if (movieDat.exists()) {
			try {
				mm = (MovieManager) DeSerialize("MovieManager.dat");
				bm = (BookingManager) DeSerialize("BookingManager.dat");
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			bm = new BookingManager();
			mm = new MovieManager();

			Cineplex orchard = new Cineplex("Orchard", "Orchard");
			bm.getCineplexManager().addCineplex(orchard);
			Cineplex jcube = new Cineplex("JCube", "JCube");
			bm.getCineplexManager().addCineplex(jcube);
			Cineplex changi = new Cineplex("Changi", "Changi");
			bm.getCineplexManager().addCineplex(changi);

			Cinema o1 = new Cinema("OC1", "Standard");
			Cinema o2 = new Cinema("OC2", "Gold");
			Cinema o3 = new Cinema("OC3", "Platinum");
			Cinema j1 = new Cinema("JC1", "Standard");
			Cinema j2 = new Cinema("JC2", "Gold");
			Cinema j3 = new Cinema("JC3", "Platinum");
			Cinema c1 = new Cinema("CH1", "Standard");
			Cinema c2 = new Cinema("CH2", "Gold");
			Cinema c3 = new Cinema("CH3", "Platinum");

			orchard.addCinema(o1);
			orchard.addCinema(o2);
			orchard.addCinema(o3);
			jcube.addCinema(j1);
			jcube.addCinema(j2);
			jcube.addCinema(j3);
			changi.addCinema(c1);
			changi.addCinema(c2);
			changi.addCinema(c3);
		}
		String choice=null;
		do {
			Scanner sc = new Scanner(System.in);
			System.out.println("Welcome to MOBLIMA!");
			System.out.println("<press 1 if you're a customer>");
			System.out.println("<press 2 to login as admin>");
			System.out.println("<press 3 to exit>");
			choice = sc.nextLine();
			if (choice.equals("1")) {
				
						do {
							try {
							System.out.println(" ");
							System.out.println("Welcome customer!");
							System.out.println("Enter choice no:");
							System.out.println("1) Show list of movies");
							System.out.println("2) Book tickets");
							System.out.println("3) View booking history");
							System.out.println("4) List the top 5 movies by ratings and by sales");
							System.out.println("5) Enter review for movie");
							System.out.println("6) Quit");
							choice = sc.nextLine();
							switch (choice) {
							case "1":
								mm.selectMovie().printDetails();
								break;
							case "2":
								bm.bookSeat();
								break;
							case "3":
								bm.showHistory();
								break;
							case "4":
								mm.printTop5MoviesByRatings();
								mm.printTop5MoviesBySales();
								break;
							case "5":
								mm.createReview();
								break;
							case "6":
								break;
							default:
								System.out.println("Option unavailable! Please try again.");
							}
							}
							catch(ArrayIndexOutOfBoundsException e2) {
								System.out.println("No movies is on list.");
							}
							catch(NullPointerException e1) {
								System.out.println("No movies to be shown.");
							}
						} while (!choice.equals("6"));
					
				
			} else if(choice.equals("2")){
				String pass;
				do {
					System.out.println(" ");
					System.out.println("Enter password or enter 'exit' to exit:");
					pass=sc.next();
				} while (!authenticate(pass));
				if(pass.equalsIgnoreCase("exit"))
					break;
				System.out.println(" ");
				System.out.println("Welcome administrator!");
				choice = sc.nextLine();
				do {
					System.out.println(" ");
					System.out.println("Select:");
					System.out.println("1) Create/update/remove listing");
					System.out.println("2) Create/update/remove cinema showtimes and the movie details to be shown");
					System.out.println("3) Configure system settings");
					System.out.println("4) Quit");
					choice = sc.nextLine();
					switch (choice) {
					case "1":
						System.out.println(" ");
						System.out.println("1) Add");
						System.out.println("2) Update");
						System.out.println("3) Remove");
						System.out.println("4) Exit");
						choice = sc.nextLine();
						if (choice.equals("1")) {
							mm.createMovie();
							break;
						}
						else if (choice.equals("2")) {
							mm.updateMovie();
							break;
						}
						else if (choice.equals("3"))
							mm.deleteMovie();
						else if(choice.equals("4"))
							break;
						else
							System.out.println("Option unavailable! Please try again.");
							break;
					case "2":
						System.out.println(" ");
						System.out.println("1) Add");
						System.out.println("2) Remove");
						System.out.println("3) Exit");
						choice = sc.nextLine();
						if (choice.equals("1"))
							bm.createShowtime(mm);
						else if (choice.equals("2"))
							bm.deleteShowtime();
						else if (choice.equals("3"))
							break;
						else
							System.out.println("Option unavailable! Please try again.");
							break;
					case "3":
						System.out.println(" ");
						System.out.println("1) Update pricing");
						System.out.println("2) Update holidays");
						System.out.println("3) Exit");
						choice = sc.nextLine();
						if (choice.equals("1")) {
							bm.updatePrices();
						} else if (choice.equals("2")) {
							bm.updateHolidays();
						}else if (choice.equals("3"))
							break;
						else 
							System.out.println("Option unavailable! Please try again.");
							break;
					case "4":
						break;
					default:
						System.out.println("Option unavailable! Please try again.");
					}
				} while (!choice.equals("4"));
			}
			else if(choice.equals("3")) {
					System.out.println(" ");
					System.out.println("See you again! Have a good day!✌✌");
					break;
			}
			else continue;
		}while(!choice.equals("1")||!choice.equals("2")||!choice.equals("3"));
		try {
			Serialize("BookingManager.dat", bm);
			Serialize("MovieManager.dat", mm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param password
	 */
	private static boolean authenticate(String pw) {
		if (pw.equals(password)||pw.contentEquals("exit")) {
			return true;
		}
		System.out.println("Wrong password!");
		return false;
	}
}