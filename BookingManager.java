import java.io.*;
import java.util.*;
import java.text.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Represents a booking manager. A booking manager can update details of a movie
 * and keep all the transaction history.
 * 
 * @author
 * @version
 * @since 2019-11-08
 */
@SuppressWarnings("serial")
public class BookingManager implements Serializable {

	/**
	 * Cineplex manager has a list of cineplexes.
	 */
	private CineplexManager cineplexManager;
	/**
	 * An array list of payment transactions.
	 */
	private ArrayList<PaymentTransaction> paymentTransactions;
	/**
	 * Hash maps of the prices sorted by movie type/cinema class/age of movie
	 * goer/days of week.
	 */
	private HashMap<String, Double> pricesByMovieType, pricesByCinemaClass, pricesByAge, pricesByDay;
	/**
	 * An array list of holidays.
	 */
	private ArrayList<Date> holidays;


	/**
	 * Creates a booking manager who has a record of the payment transactions,
	 * prices of movie tickets, and holidays.
	 */
	public BookingManager() {
		cineplexManager = new CineplexManager();
		paymentTransactions = new ArrayList<PaymentTransaction>();
		pricesByMovieType = new HashMap<String, Double>();
		pricesByMovieType.put("2D", 0.0);
		pricesByMovieType.put("3D", 2.0);
		pricesByCinemaClass = new HashMap<String, Double>();
		pricesByCinemaClass.put("Platinum", 12.0);
		pricesByCinemaClass.put("Gold", 6.0);
		pricesByCinemaClass.put("Standard", 0.0);
		pricesByAge = new HashMap<String, Double>();
		pricesByAge.put("child", 6.0);
		pricesByAge.put("adult", 9.5);
		pricesByAge.put("senior", 7.0);
		pricesByDay = new HashMap<String, Double>();
		pricesByDay.put("weekday", 0.0);
		pricesByDay.put("holiday", 1.5);
		holidays = new ArrayList<Date>();
	}


	/**
	 * To book seats by asking preferred showtime, seat, movie-goer age, movie type
	 * and class of cinema. Then ask movie-goer for name, mobile number and email.
	 * Then add a record of payment transaction.
	 */
	public void bookSeat() {
		Scanner sc = new Scanner(System.in);
		Cinema cinema = this.selectCinema();
		if(cinema==null)
			return;
		cinema.printShowtimes();
		if(cinema.showtimes.size()==0)
			return;
		String showtimeId;
		Showtime showtime = null;
		while (true) {
			System.out.println(" ");
			showtimeId = "";
			System.out.print("Enter showtime ID or type in 'exit' to exit: ");
			showtimeId = sc.nextLine();
			if(showtimeId.equalsIgnoreCase("exit"))
				return;
			showtime = cinema.getShowtimeById(showtimeId);
			if (Objects.nonNull(showtime)) 
				break;
			else 
				System.out.println("The showtime ID doesn't exist. Please try again.");
		}
		showtime.printSeatLayout();
		String seatId;
		String age;
		String option;
		double amount = 0;
		HashMap<String, Double> seatFees = new HashMap<String, Double>();
		retry: while (true) {
			System.out.println(" ");
			seatId = "";
			System.out.println("White boxes are free seats, black boxes are occupied seats.");
			System.out.println("Enter seat ID or type in 'exit' to exit: ");
			seatId = sc.nextLine();
			if(seatId.equalsIgnoreCase("exit"))
				return;
			System.out.println("This seat is for a/an 1) child 2) adult 3) senior citizen or type in 'exit' to exit: ");
			option = sc.nextLine();
			if(option.equalsIgnoreCase("exit"))
				return;
			switch (option) {
			case "1":
				age = "child";
				break;
			case "2":
				age = "adult";
				break;
			case "3":
				age = "senior";
				break;
			default:
				System.out.println("The operation failed. Please try again.");
				continue retry;
			}
			if (showtime.assignSeat(seatId)) {
				seatFees.put(seatId, pricesByAge.get(age));
				if (pricesByMovieType.get(showtime.getMovie().getMovieType()) != null)
					amount += pricesByMovieType.get(showtime.getMovie().getMovieType());
				if (pricesByCinemaClass.get(cinema.getCinemaClass()) != null)
					amount += pricesByCinemaClass.get(cinema.getCinemaClass());
				Date date = showtime.getStartTime();
				boolean flag = false;
				for (Date holiday : holidays) {
					if (holiday.getYear() == date.getYear() && holiday.getMonth() == date.getMonth() && holiday.getDay() == date.getDay())
						flag = true;
				}
				if (flag) {
					//System.out.println(" ");
					//System.out.println("The day you booked is a holiday.");
					amount += pricesByDay.get("holiday");
				} 
				else {
					//System.out.println(" ");
					//System.out.println("The day you booked is a weekday.");
					amount += pricesByDay.get("weekday");
				}
			}
			else 
				System.out.println("The operation failed. Please select available seats.");
			boolean wrongOption = true;
			do {
				System.out.print("Do you want to reserve another seat? (Y/N): ");
				option = sc.nextLine();
				if (option.equalsIgnoreCase("n")||option.equalsIgnoreCase("y"))
					break;
			}while(wrongOption);
			if(option.equalsIgnoreCase("n"))
				break;
		}
		for (double d : seatFees.values()) 
			amount += d;
		showtime.getMovie().increaseSales(amount);
		String title = showtime.getMovie().getTitle();
		String cinemaId = cinema.getCinemaId();
		Date startTime = showtime.getStartTime();
		String[] seatIds = seatFees.keySet().toArray(new String[seatFees.keySet().size()]);
		
		System.out.println(" ");
		System.out.println("Enter your name or enter 'cancel' to cancel booking: ");
		String name = sc.nextLine();
		if(name.equalsIgnoreCase("cancel")) {
			System.out.println("Booking is cancelled. Thank you.");
			return;
		}
		boolean nonnumber = true;
		String mobileNumber;
		do {
			System.out.println(" ");
			System.out.println("Enter your mobile number (without telephone code) or enter 'cancel' to cancel booking: ");
			mobileNumber = sc.nextLine();
			if(mobileNumber.matches("^[0-9]+$"))
				nonnumber = false;
			else if(mobileNumber.equalsIgnoreCase("cancel")) {
				System.out.println("Booking is cancelled. Thank you.");
				return;
			}
		}while(nonnumber);
		System.out.println(" ");
		System.out.println("Enter your email address or enter 'cancel' to cancel booking: ");
		String email = sc.nextLine();
		if(email.equalsIgnoreCase("cancel")) {
			System.out.println("Booking is cancelled. Thank you.");
			return;
		}
		PaymentTransaction paymentTransaction = new PaymentTransaction(amount, title, cinemaId, startTime, seatIds,
				name, mobileNumber, email);
		paymentTransactions.add(paymentTransaction);
	}


	/**
	 * Movie-goer can check for his booking history by entering his email address.
	 */
	public void showHistory() {
		Scanner sc = new Scanner(System.in);
		boolean flag = false;
		String email;
		System.out.println(" ");
		System.out.println("Enter your email address or type in 'exit' to exit: ");
		email = sc.nextLine();
		if(email.equalsIgnoreCase("exit"))
			return;
		for (PaymentTransaction p : paymentTransactions) {
			if (p.getEmail().equals(email)) {
				p.printDetails();
				flag = true;
			}
		}
		if(!flag)
			System.out.println("No booking history is found for this email address.");
	}


	/**
	 * For admin to add showtime.
	 * 
	 * @throws ParseException
	 */
	public void createShowtime(MovieManager movieManager) throws ParseException {
		Scanner sc = new Scanner(System.in);
		Cinema cinema = this.selectCinema();
		String showtimeId  = "";
		System.out.println(" ");
		System.out.println("Create showtime ID (Alpha-numeric input) or type in 'exit' to exit: ");
		retry: while(true) {
			showtimeId = sc.nextLine();
			if(showtimeId.equalsIgnoreCase("exit"))
				return;
			for(int i=0;i<cinema.showtimes.size();i++) {
				if(showtimeId.equalsIgnoreCase(cinema.showtimes.get(i).getShowtimeId())) {
					System.out.println("The showtime ID exists. Please create another one!");
					System.out.println("Create showtime ID (Alpha-numeric input) or type in 'exit' to exit: ");
					continue retry;
				}
			}
			break;
		}
		Movie movie = movieManager.selectMovie();
		
		sc = new Scanner(System.in);
		String strStartTime;
		Date startTime;
		System.out.println(" ");
		System.out.println("Enter movie start time (yyyymmddhhmm) (entire input must be numeric) or type in 'exit' to exit: ");
		doAgain: do{
			try {
				strStartTime = sc.nextLine();
				if(strStartTime.equalsIgnoreCase("exit"))
					return;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
				startTime = simpleDateFormat.parse(strStartTime);
				ParsePosition parsePosition = new ParsePosition(0);
				startTime = simpleDateFormat.parse(strStartTime, parsePosition); 
				
				if(strStartTime.equals(simpleDateFormat.format(startTime))) {
					for(int i=0;i<cinema.showtimes.size();i++) {
						String strDate = simpleDateFormat.format(cinema.showtimes.get(i).getStartTime());
						if(strStartTime.equals(strDate)) {
							System.out.println("The showtime is not available for the cinema. Please create another one!");
							System.out.println("Enter movie start time (yyyymmddhhmm) (entire input must be numeric) or type in 'exit' to exit: ");
							continue doAgain;
						}
					}
					break;
				}
				
				System.out.println(" ");
				System.out.println("Wrong date format.");
				System.out.println("Enter movie start time (yyyymmddhhmm) (entire input must be numeric) or type in 'exit' to exit: ");
			}
			catch(ParseException e) {
				System.out.println(" ");
				System.out.println("Wrong date format.");
				System.out.println("Enter movie start time (yyyymmddhhmm) (entire input must be numeric) or type in 'exit' to exit: ");
			}
		}while(true);
		Showtime showtime = new Showtime(showtimeId, movie, startTime);
		cinema.addShowtime(showtime);
	}


	/**
	 * For admin to delete showtime.
	 */
	public void deleteShowtime() {
		Scanner sc = new Scanner(System.in);
		Cinema cinema = this.selectCinema();
		System.out.println(" ");
		cinema.printShowtimes();
		System.out.println(" ");
		System.out.println("Enter showtime ID or type in 'exit' to exit: ");
		String showtimeId = sc.nextLine();
		if(showtimeId.equalsIgnoreCase("exit"))
			return;
		for(int i=0;i<cinema.showtimes.size();i++) {
			if(showtimeId.equals(cinema.showtimes.get(i).getShowtimeId())) {
				cinema.deleteShowtime(showtimeId);
				System.out.println("The showtime with ID "+showtimeId+" is deleted successfully.");
				return;
			}
		}
		System.out.println("The showtime does not exist!");
	}


	/**
	 * User selects a cineplex. Then selects a cinema.
	 * 
	 * @return a cinema.
	 */
	private Cinema selectCinema() {
		Scanner sc = new Scanner(System.in);
		System.out.println(" ");
		cineplexManager.printCineplexes();
		System.out.println("Enter cineplex ID (ex. Orchard) or type in 'exit' to exit: ");
		String cineplexId;
		do {
			cineplexId = sc.nextLine();
			if(cineplexId.equalsIgnoreCase("exit"))
				return null;
			else if(cineplexId.equals("Orchard")||cineplexId.equals("JCube")||cineplexId.equals("Changi"))
				break;
			System.out.println(" ");
			System.out.println("The cineplex ID does not exist.");
			System.out.println("Try again or type in 'exit' to exit: ");
		}while(true);	
		Cineplex cineplex = cineplexManager.getCineplexById(cineplexId);
		if (Objects.isNull(cineplex))
			return null;
		cineplex.printCinemas();
		System.out.println(" ");
		System.out.println("Enter cinema ID (ex. OC1, JC2, CH3) or type in 'exit' to exit: ");
		String cinemaId;
		do {
			cinemaId = sc.nextLine();
			if(cinemaId.equalsIgnoreCase("exit"))
				return null;
			else if(cineplexId.equals("Orchard")&&cinemaId.equals("OC1")||cinemaId.equals("OC2")||cinemaId.equals("OC3"))
				break;
			else if(cineplexId.equals("JCube")&&cinemaId.equals("JC1")||cinemaId.equals("JC2")||cinemaId.equals("JC3"))
				break;
			else if(cineplexId.equals("Changi")&&cinemaId.equals("CH1")||cinemaId.equals("CH2")||cinemaId.equals("CH3"))
				break;
			System.out.println(" ");
			System.out.println("The cinema ID does not exist.");
			System.out.println("Try again or type in 'exit' to exit: ");
		}while(true);
		if (Objects.isNull(cinemaId))
			return null;
		return cineplex.getCinemaById(cinemaId);
	}


	/**
	 * For admin to update prices base on movie type, cinema class, movie-goer age
	 * and days of the week.
	 */
	public void updatePrices() {
		HashMap<String, Double> targetHashMap = null;
		Scanner sc = new Scanner(System.in);
		retry: while(true) {
			System.out.println(" ");
			System.out.println("1) Movie type 2) Cinema class 3) Age 4) Day");
			System.out.println("Enter option no. or type 'exit' to exit.");
			String option = sc.nextLine();
			if(option.equalsIgnoreCase("exit"))
				return;
			switch (option) {
			case "1":
				targetHashMap = pricesByMovieType;
				break;
			case "2":
				targetHashMap = pricesByCinemaClass;
				break;
			case "3":
				targetHashMap = pricesByAge;
				break;
			case "4":
				targetHashMap = pricesByDay;
				break;
			default: 
				System.out.println(" ");
				System.out.println("Selection not available. Please select again.");
				continue retry;
			}
			if(option.equals("1")||option.equals("2")||option.equals("3")||option.equals("4"))
				break;
		}
		targetHashMap.entrySet().forEach((entry) -> System.out.println(entry));
		String category="";
		System.out.println(" ");
		System.out.println("Enter category or type in 'exit' to exit: ");
		do {
			category = sc.nextLine();
			if(category.equalsIgnoreCase("exit"))
				return;
			else if(category.equals("holiday")||category.equals("weekday"))
				break;
			else if(category.equals("2D")||category.equals("3D"))
				break;
			else if(category.equals("Gold")||category.equals("Platinum")||category.equals("Standard"))
				break;
			else if(category.equals("senior")||category.equals("adult")||category.equals("child"))
				break;
			System.out.println(" ");
			System.out.println("Please select again or type in 'exit' to exit.");
		}while(true);
		
		double price = 0.0;
		String priceOld = "";
		
		while(true) {
			System.out.println(" ");
			System.out.println("Enter price or type in 'exit' to exit: ");
			try {	
				priceOld = sc.next();
				if(priceOld.equalsIgnoreCase("exit"))
					return;
				price = Double.parseDouble(priceOld);
				break;
			}
			catch(NumberFormatException e1){
				System.out.println(" ");
				System.out.println("Please only enter value up to one decimal places.");
			}	
		}	
		targetHashMap.put(category, price);
	}


	/**
	 * For admin to update the days which are holidays.
	 */
	public void updateHolidays() {
		for (Date d : holidays)
			System.out.println(d);
		Scanner sc = new Scanner(System.in);
		Date holidayDate;
		Date date;
		System.out.println(" ");
		System.out.println("Enter holiday (yyyymmdd) or type in 'exit' to exit: ");
		do{
			try {
			String holiday = sc.nextLine();
			if(holiday.equalsIgnoreCase("exit"))
				return;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			date = simpleDateFormat.parse(holiday);
			ParsePosition parsePosition = new ParsePosition(0);
			holidayDate = simpleDateFormat.parse(holiday, parsePosition);
			if(holiday.equals(simpleDateFormat.format(date)))
				break;
			System.out.println(" ");
			System.out.println("Wrong date format.");
			System.out.println("Enter holiday (yyyymmdd) or type in 'exit' to exit: ");
			}
			catch(ParseException e) {
				System.out.println(" ");
				System.out.println("Wrong date format.");
				System.out.println("Enter holiday (yyyymmdd) or type in 'exit' to exit: ");
			}
		}while(true);	
		boolean flag = false;
		for (Date d : holidays) {
			if (d.equals(holidayDate))
				flag = true;
		}
		if (!flag) {
			holidays.add(holidayDate);
			System.out.println("The date is successfully added as a holiday.");
		}
		else {
			holidays.remove(holidayDate);
			System.out.println("The date is successfully removed as a holiday.");
		}
		holidays.remove(null);
	}

	public CineplexManager getCineplexManager() {
		return cineplexManager;
	}
}
