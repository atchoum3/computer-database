package com.excilys.ui;

import java.awt.Choice;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.excilys.controler.ConsoleControler;
import com.excilys.exception.ComputerDateGreaterLessThan1970;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.Database;

public class Console {
	private ConsoleControler consoleControler;
	private Database database;
	private BufferedReader reader;

	public Console() {
		database = Database.getInstance();
		// connect to Database
		database.connection();
		consoleControler = ConsoleControler.getInstance();
		
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void start() {
		while (true) {
			displayChoice();
			readChoice();
		}
	}
	

	private void displayChoice() {
		System.out.println("Possibilities :");
		System.out.println("1 : see all companies");
		System.out.println("----------");
		System.out.println("2 : see all computers");
		System.out.println("3 <id> : see detail of one computer");
		System.out.println("4 : insert one computer");
		System.out.println("5 <id> : update one computer");
		System.out.println("6 <id> : delete one computer");
		System.out.println("----------");
		System.out.println("0 : quit");
		System.out.println("");
	}
	
	private void selectChoice(String line) {
		String number = "";
		Optional<Long> optionalId;
		Computer computer;
		
		line = line.trim();
		if (line.length() >= 1)
			number = line.trim().substring(0,1);
		
		switch (number) {
		case "1":
			displayAllCompanies();
			break;
			
		case "2":
			displayAllComputers();
			break;
			
		case "3":
			optionalId = getIdFromCommandLine(line);
			if (optionalId.isPresent()) {
				Optional<Computer> optionalComputer = consoleControler.getComputerById(optionalId.get());
				if (optionalComputer.isPresent()) {
					DisplayComputer.displayComputer(optionalComputer.get());
				} else {
					System.out.println("This id is not on the database.");
				}
			}
			break;
			
		case "4":
			computer = new Computer("", null, null, 0);
			updateComputer(computer);
			consoleControler.createComputer(computer);
			break;
			
		case "5":
			 optionalId = getIdFromCommandLine(line);
			if (optionalId.isPresent()) {
				Optional<Computer> optionalComputer = consoleControler.getComputerById(optionalId.get());
				if (optionalComputer.isPresent()) {
					// start update
					computer = optionalComputer.get();
					updateComputer(computer);
					consoleControler.updateComputer(computer);
				} else {
					System.out.println("This id is not on the database.");
				}
			}
			break;
			
		case "6":
			optionalId = getIdFromCommandLine(line);
			optionalId.ifPresent(id -> consoleControler.deleteComputer(id));
			break;
			
		case "0":
			database.close();
			System.exit(0);
			break;
			
		default:
			System.out.println("I do not understand your choice");
			break;
		}
	}
	
	private void readChoice() {
		System.out.print("your choice: ");
		try {
			String line = reader.readLine();
			selectChoice(line);
			System.out.println("");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void updateComputer(Computer computer) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		boolean continueFlag = false;
		
		try {
			// name
			do {
				System.out.print("Name of computer: ");
				line = reader.readLine().trim();
				
				computer.setName(line);
			} while(line.isEmpty());
			
			// introduced time
			do {
				continueFlag = false;
				System.out.print("Introduced time (you can put nothing to do not enter this parameter, format: yyyy-MM-dd HH:mm:ss): ");
				line = reader.readLine().trim();
				
				if (line.isEmpty()) {
					computer.setIntroduced(null);
				} else {
					try {
						computer.setIntroduced(LocalDateTime.parse(line, formatter));
					} catch(DateTimeParseException e) {
						System.out.println("Not good format");
						continueFlag = true;
					} catch(ComputerDateGreaterLessThan1970 e) {
						System.out.println("The date need to be greater than the 1970-01-01");
						continueFlag = true;
					}
				}
			} while(continueFlag);
			
			// Discontinued time
			do {
				continueFlag = false;
				System.out.print("Discontinued time (you can put nothing to do not enter this parameter, format: yyyy-MM-dd HH:mm:ss): ");
				line = reader.readLine().trim();
				
				try {
					if (line.isEmpty()) {
						computer.setDiscontinued(null);
					} else {
						try {
							computer.setDiscontinued(LocalDateTime.parse(line, formatter));
						} catch(DateTimeParseException e) {
							System.out.println("Not good format");
							continueFlag = true;
						} catch(ComputerDateGreaterLessThan1970 e) {
							System.out.println("The date need to be greater than the 1970-01-01");
							continueFlag = true;
						}
					}
				} catch(IllegalArgumentException e) {
					System.out.println("The discontinued time have to be gretter than the introduced.");
					continueFlag = true;
				}
			} while(continueFlag);
			
			// id company
			do {
				continueFlag = false;
				Optional<Long> optionalId;
				
				do {
					System.out.print("id company: ");  
					line = reader.readLine().trim();
					optionalId = convertStringToId(line);
				} while (optionalId.isEmpty());
					
				long id = optionalId.get();
				if (consoleControler.getCompanyById(id).isPresent()) {
					computer.setIdCompany(id);
				} else {
					System.out.println("This company does not exist.");
					continueFlag = true;
				}
				
			} while(continueFlag);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("");
	}
	
	private Optional<Long> getIdFromCommandLine(String line) {
		String[] splitLine = line.split(" ");
		Optional<Long> l = Optional.empty();
		
		if (splitLine.length >= 2) {
			l = convertStringToId(splitLine[1]);
		} else {
			System.out.println("You have to enter an id");
		}
		return l;
	}
	
	
	private Optional<Long> convertStringToId(String string) {
		Optional<Long> l = Optional.empty();
		try {
			l = Optional.of(Long.parseLong(string));
		} catch (NumberFormatException e) {
			System.out.println("Incorrect id.");
		}
		return l;
	}
	
	private void displayAllCompanies() {
		Page page = new Page();
		do {
			DisplayCompany.displayCollection(consoleControler.getAllCompanies(page));
			displayFooterPage(page);
		} while (selectPage(page));
	}
	
	private void displayAllComputers() {
		Page page = new Page();
		do {
			DisplayComputer.displayCollection(consoleControler.getAllComputers(page));
			displayFooterPage(page);
		} while (selectPage(page));
	}
	
	private boolean selectPage(Page page) {
		try {
			boolean loop = true;
			while (loop) {
				System.out.print("Your choice: ");
				String number = "";
				String line = reader.readLine().trim();
	
				if (line.length() >= 1)
					number = line.trim().substring(0,1);
				
				
	
				switch (number) {
				case "+":
					page.nextPage();
					loop = false;
					break;
				case "-":
					page.previousPage();
					loop = false;
					break;
				case "q":
					return false;
				default:
					try {
						int pageNb = Integer.valueOf(line);
						page.setCurrentPage(pageNb);
						loop = false;
					} catch (NumberFormatException e) {
						// it's not a number, do nothing
					}
					// if the input don't match with anything
					if (loop) {
						System.out.println("I do not understand your choice");
					}
				}
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private void displayFooterPage(Page page) {
		System.out.println("- : previous page");
		System.out.println("+ : next page");
		System.out.println("<number> : go to page <number>");
		System.out.println("q : to quit");
		System.out.println("current page: " + page.getCurrentPage());
		
	}
}
		