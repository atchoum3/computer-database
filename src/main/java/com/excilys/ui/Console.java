package com.excilys.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.excilys.model.Computer;
import com.excilys.persistence.CompanyDAO;
import com.excilys.service.CompanyControler;
import com.excilys.service.ComputerControler;

public class Console {
	private CompanyControler companyControler;
	private ComputerControler computerControler;

	public Console() {
		companyControler = new CompanyControler();
		computerControler = new ComputerControler();
	}
	
	public void start() {
		while (true) {
			displayChoice();
			readChoice();
		}
	}
	

	private void displayChoice() {
		System.out.println("Select a choice :");
		System.out.println("1 : see all companies");
		System.out.println("----------");
		System.out.println("2 : see all computers");
		System.out.println("3 <id> : see detail of one computer");
		System.out.println("4 : insert one computer");
		System.out.println("5 <id> : update one computer");
		System.out.println("6 <id> : delete one computer");
		System.out.println("");
	}
	
	private void selectChoice(String line) {
		line = line.trim();
		String number = line.substring(0,1);
		Optional<Long> optionalId;
		Computer computer;
		
		switch (number) {
		case "1":
			DisplayCompany.displayCollection(companyControler.getAllCompany());
			break;
			
		case "2":
			DisplayComputer.displayCollection(computerControler.getAllComputer());
			break;
			
		case "3":
			optionalId = getIdFromCommandLine(line);
			if (optionalId.isPresent()) {
				Optional<Computer> optionalComputer = computerControler.getComputerById(optionalId.get());
				if (optionalComputer.isPresent()) {
					DisplayComputer.displayComputer(optionalComputer.get(), true);
				} else {
					System.out.println("This id is not on the database.");
				}
			}
			break;
			
		case "4":
			computer = new Computer("", null, null, 0);
			updateComputer(computer);
			computerControler.createComputer(computer);
			break;
			
		case "5":
			 optionalId = getIdFromCommandLine(line);
			if (optionalId.isPresent()) {
				Optional<Computer> optionalComputer = computerControler.getComputerById(optionalId.get());
				if (optionalComputer.isPresent()) {
					// start update
					computer = optionalComputer.get();
					updateComputer(computer);
					computerControler.updateComputer(computer);
				} else {
					System.out.println("This id is not on the database.");
				}
			}
			break;
			
		case "6":
			optionalId = getIdFromCommandLine(line);
			optionalId.ifPresent(id -> computerControler.deleteComputer(id));
			break;
			
		default:
			System.out.println("I do not understand your choice");
			break;
		}
	}
	
	private void readChoice() {
		System.out.print("your choice: ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
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
					}
				}
			} while(continueFlag);
			
			// Discontinued time
			do {
				continueFlag = false;
				System.out.print("Discontinued time (you can put nothing to do not enter this parameter, format: yyyy-MM-dd HH:mm:ss): ");
				line = reader.readLine().trim();
				
				if (line.isEmpty()) {
					computer.setDiscontinued(null);
				} else {
					try {
						computer.setDiscontinued(LocalDateTime.parse(line, formatter));
					} catch(DateTimeParseException e) {
						System.out.println("Not good format");
						continueFlag = true;
					} catch(IllegalArgumentException e) {
						System.out.println("The discontinued time have to be gretter than the introduced.");
						continueFlag = true;
					}
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
				if (CompanyDAO.getInstance().getById(id).isPresent()) {
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
}
		