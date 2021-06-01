package com.excilys.cdb.controller.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.ChoiceMainMenu;
import com.excilys.cdb.ui.ChoicePageMenu;
import com.excilys.cdb.ui.Console;
import com.excilys.cdb.ui.DisplayCompany;
import com.excilys.cdb.ui.DisplayComputer;

public class ConsoleControler {
	private static Logger logger = LoggerFactory.getLogger(ConsoleControler.class);
	
	private static ConsoleControler instance = null;
	private Console console;
	private Scanner scanner;
	
	private ConsoleControler() {
		console = Console.getInstance();
		
		scanner = new Scanner(System.in);
	}
	
	public static ConsoleControler getInstance() {
		if (instance == null) {
			instance = new ConsoleControler();
		}
		return instance;
	}
	
	/**
	 * start the menu of the console.
	 */
	public void start() {
		do {
			console.displayMenuChoice();
			OptionalInt opt = askChoiceMainMenu();
			if (opt.isPresent()) {
				int choice = opt.getAsInt();
				if (ChoiceMainMenu.isChoiceMenu(choice)) {
					executeChoiceMainMenu(choice);
				} else {
					System.out.println("This is not a proposed choice");
				}
			}
		} while (true);
	}
	
	private OptionalInt askChoiceMainMenu() {
		OptionalInt opt = OptionalInt.empty();	
		
		System.out.print("your choice: ");
		String input = scanner.nextLine();
		try {
			opt = OptionalInt.of(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			System.out.println("Enter a number");
		}
		return opt;
	}
	
	
	private boolean executeChoicePageMenu(Page page) {
		System.out.print("your choice: ");
		String input = scanner.nextLine();
		
		try {
			switch (ChoicePageMenu.fromPropertyName(input)) {
			case NEXT_PAGE:
				page.nextPage();
				break;
			case PREVIOUS_PAGE:
				page.previousPage();
				break;
			case QUIT:
				return false;
			}
		} catch (Exception e) {
			System.out.println("I do not understand your choice");
		}
		return true;
	}
	
	
	/**
	 * Read the answer of the user and call the concerned choice.
	 * @param intChoice the input of the user
	 */
	private void executeChoiceMainMenu(int intChoice) {
		ChoiceMainMenu choice = ChoiceMainMenu.values()[intChoice - 1];
		
		try {
			switch (choice) {
			case DISPLAY_ALL_COMPANIES:
					displayAllCompanies();
				break;
				
			case DISPLAY_ALL_COMPUTERS:
				displayAllComputers();
				break;
				
			case DISPLAY_COMPUTER:
				displayComputer();
				break;
				
			case CREATE_COMPUTER:
				createComputer();
				break;
				
			case UPDATE_COMPUTER:
				updateComputer();
				break;
				
			case DELETE_COMPUTER:
				deleteComputer();
				break;
				
			case QUIT:
				System.exit(0);
				break;
		
			}
		} catch (ComputerCompanyIdException e) {
			System.out.println("This id of company does not exist.");
		} catch (CustomSQLException e) {
			logger.error(e.getMessage(), e);
			System.out.println("Communication between the programm and database failed. Try again");
		}
	}
		
	/**
	 * To display a table of all Companies selected by page.
	 * @throws CustomSQLException 
	 */
	private void displayAllCompanies() throws CustomSQLException {
		Page page = new Page(1, 10, CompanyService.getInstance().count());
		do {
			DisplayCompany.displayCollection(CompanyService.getInstance().getAll(page));
			console.displayFooterPage(page);
		} while (executeChoicePageMenu(page));
	}
	
	/**
	 * To display a table of all computers selected by page.
	 * @throws CustomSQLException 
	 */
	private void displayAllComputers() throws CustomSQLException {
		Page page = new Page(1, 10, ComputerService.getInstance().count());
		do {
			DisplayComputer.displayCollection(ComputerService.getInstance().getAll(page));
			console.displayFooterPage(page);
		} while (executeChoicePageMenu(page));
	}
	
	private void displayComputer() throws CustomSQLException {
		long id = askComputerId();

		Optional<Computer> opt = ComputerService.getInstance().getById(id);
		if (opt.isPresent()) {
			DisplayComputer.displayComputer(opt.get());
		} else {
			System.out.println("This id is not on the database.");
		}
	}
	
	private long askComputerId() {
		System.out.println("Insert the id of the computer");
		String input = scanner.nextLine();
		try {
			return Long.parseLong(input);
		} catch (NumberFormatException e) {
			System.out.println("Insert an integer");
			return askComputerId();
		}
	}
	
	private void createComputer() throws CustomSQLException {
		Computer computer = new Computer.Builder("").build();
		updateComputer(computer);
		try {
			ComputerService.getInstance().create(computer);
		} catch (ComputerCompanyIdException e) {
			System.err.println(e.getMessage());
			System.out.println("Computer not created");
		}
	}
	
	
	
	private String askComputerName() {
		System.out.print("Enter a name: ");
		String input = scanner.nextLine();
		if (input.isEmpty()) {
			return askComputerName();
		}
		return input;
	}
	
	
	private LocalDate askComputerDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		String input = scanner.nextLine();
		if (input.isEmpty()) {
			return null;
		}
		try {
			LocalDate date = LocalDate.parse(input, formatter);
			if (checkDateBefore1970(date)) {
				System.out.print("The date must start before 1 January 1970");
				return askComputerDate();
			} else {
				return date;
			}
		} catch (DateTimeParseException e) {
			System.out.print("Wrong format"); 
			return askComputerDate();
		}
	}
	
	
	private Company askComputerCompanyId() {
		System.out.print("Enter the id of the company (optional): ");
		String input = scanner.nextLine();
		if (input.isEmpty()) {
			return null;
		}
		try {
			return new Company(Long.parseLong(input));
		} catch (NumberFormatException e) {
			System.out.println("Insert an integer");
			return askComputerCompanyId();
		}
	}
	
	/**
	 * method to update or create a computer.
	 * @param computer all values will be saved on this object
	 */
	private void updateComputer(Computer computer) {
		LocalDate discontinued;

		computer.setName(askComputerName());
		System.out.print("Introduced time (optional, format: yyyy-MM-dd HH:mm:ss): ");
		computer.setIntroduced(askComputerDate());
		System.out.print("Discontinued time (optional, format: yyyy-MM-dd HH:mm:ss): ");
		do {
			discontinued = askComputerDate();
		} while (!checkIntroducedBeforeDisontinued(computer.getIntroduced(), discontinued));
		computer.setDiscontinued(discontinued);
		
		computer.setCompany(askComputerCompanyId());
	}
	
	private void updateComputer() throws CustomSQLException, ComputerCompanyIdException {
		long id = askComputerId();
		Optional<Computer> optComputer = ComputerService.getInstance().getById(id);
		
		if (optComputer.isPresent()) {
			Computer computer = new Computer.Builder("").build();
			updateComputer(computer);
			ComputerService.getInstance().update(computer);
		} else {
			System.out.println("This company id is not on the database.");
		}
	}
	
	private void deleteComputer() throws CustomSQLException {
		long id = askComputerId();
		ComputerService.getInstance().delete(id);
	}
	
	/**
	 * This method check if a LocalDateTime is before the 1st January 1970. 
	 * This method is useful to know if the value can be or not converted in Timestamp.
	 * @param date the date to check
	 * @return true if the date is before the 1st January 1970
	 */
	private boolean checkDateBefore1970(LocalDate date) {
		return (date != null && date.isBefore(LocalDate.ofEpochDay(0)));
	}
	
	/**
	 * Check if the introduced is before the discontinued date. If this is the case 
	 * throw IllegalArgumentException
	 * @param introduced the date to when the computer was introduced
	 * @param discontinued the date to when the computer was discontinued
	 * @return true if introduced is before discontinued
	 */
	private boolean checkIntroducedBeforeDisontinued(LocalDate introduced, 
			LocalDate discontinued) {
		if (introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
			return false;
		}
		return true;
	}
}
