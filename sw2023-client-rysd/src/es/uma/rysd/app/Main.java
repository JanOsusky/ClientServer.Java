package es.uma.rysd.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import es.uma.rysd.entities.Person;
import es.uma.rysd.entities.Person;
import es.uma.rysd.entities.Planet;
import es.uma.rysd.entities.Specie;

public class Main {	
	private static Random rand; // for random numbers
	private static Scanner sc; // to read from the keyboard
	private final static String proxy = "proxy.lcc.uma.es";
	private final static String proxy_port = "3128";
	
    public static void main(String[] args) {
    	// Uncomment the following lines if you are working with laboratory computers and/or accessing the Internet using the proxy
    	// System.setProperty("https.proxyHost",proxy); 
    	// System.setProperty("https.proxyPort",proxy_port);
    	
        SWClient sw = new SWClient();
        String reply = null;
    	rand = new Random();
        sc = new Scanner(System.in);

        do{
        	//whoIsThisSpecie(sw);
        	moreHeavy(sw);
        	//moreTall(sw);
        	//whoLives1(sw);
        	//whoLives2(sw);
	       	System.out.println("Another round (y/n)?");
	       	reply = sc.nextLine();
	    }while(reply.equals("y"));
        sc.close();
        
    }
    
    // Generate a number between 0 and max-1 that has not been used previously (used ones come in l).
    public static Integer getRandomResource(int max, List<Integer> l){
    	if(max == l.size()) return null;

    	Integer p = rand.nextInt(max);
    	while(l.contains(p)){
    		p = (p+1)%max;
    	}
    	return p;
    }
    
    // Question that obtains two equal resources (characters in this case) and compares them.
    public static void moreTall(SWClient sw){
    	// Obtaining the number of people in storage
    	int max_people = sw.getNumberOfResources("people");
    	if(max_people == 0){
    		System.out.println("No people found.");
    		return;
    	}
    	
    	System.out.println("Generating new question...");
    	// Taking two random people without repetition
        List<Integer> used = new ArrayList<Integer>();
    	List<Person> people = new ArrayList<Person>();
    	int counter = 0;
    	while(counter < 2){
    		Integer p = getRandomResource(max_people, used);
    		Person person = sw.getPerson(sw.generateEndpoint("people", p));
    		if(person == null){
    			System.out.println("There was an error in finding the resource " + p);
    		} else {
    			people.add(person);
    			counter++;
    		}
    		used.add(p);
    	}
    	
    	// By typing the question and reading the option
    	Integer n = null;
    	do{
    		System.out.println("Who is taller? [0] "+ people.get(0).name + " o [1] " + people.get(1).name);
    		try{
    			n = Integer.parseInt(sc.nextLine());
    		}catch(NumberFormatException ex){
    			n = -1;
    		}
    	}while(n != 0 && n != 1);
    	
    	// Showing the information of the chosen characters
    	for(Person p: people){
    		System.out.println(p.name + " measures " + p.height);
    	}
    	
    	// Resultado
    	if(Double.parseDouble(people.get(n).height) >= Double.parseDouble(people.get((n+1)%2).height)){
    		System.out.println("Congratulations!!! "+ success[rand.nextInt(success.length)]);
    	} else {
    		System.out.println("You failed :( " + fail[rand.nextInt(fail.length)]);
    	}
    }
    
    
     // Question that obtains two equal resources (characters in this case) and compares them.
    public static void moreHeavy(SWClient sw){
    	// Obtaining the number of people in storage
    	int max_people = sw.getNumberOfResources("people");
    	if(max_people == 0){
    		System.out.println("No people found.");
    		return;
    	}
    	
    	System.out.println("Generating new question...");
    	// Taking two random people without repetition
        List<Integer> used = new ArrayList<Integer>();
    	List<Person> people = new ArrayList<Person>();
    	int counter = 0;
    	while(counter < 2){
    		Integer p = getRandomResource(max_people, used);
    		Person person = sw.getPerson(sw.generateEndpoint("people", p));
    		if(person == null){
    			System.out.println("There was an error in finding the resource " + p);
    		} else {
    			people.add(person);
    			counter++;
    		}
    		used.add(p);
    	}
    	
    	// By typing the question and reading the option
    	Integer n = null;
    	for(Person p : people)
    	{
    		if(p.mass.contains("unknown"))
    			p.mass.replace("unknown", "0");
    		if(p.mass.contains(","))
    		{
    			p.mass.replace(",", ".");
    		}
    	}
    	do{
    		System.out.println("Who is Heavier? [0] "+ people.get(0).name + " o [1] " + people.get(1).name);
    		try{
    			n = Integer.parseInt(sc.nextLine());
    		}catch(NumberFormatException ex){
    			n = -1;
    		}
    	}while(n != 0 && n != 1);
    	
    	// Showing the information of the chosen characters
    	for(Person p: people){
    		System.out.println(p.name + " weights " + p.mass);
    	}
    	
    	// Resultado
    	if(Double.parseDouble(people.get(n).mass) >= Double.parseDouble(people.get((n+1)%2).mass)){
    		System.out.println("Congratulations!!! "+ success[rand.nextInt(success.length)]);
    	} else {
    		System.out.println("You failed :( " + fail[rand.nextInt(fail.length)]);
    	}
    }
    
    
    
public static void whoIsThisSpecie(SWClient sw){
    	
    	// Obtaining the number of planets and people
    	int max_people = sw.getNumberOfResources("people");
    	int max_species = sw.getNumberOfResources("species");
    	if(max_people == 0 || max_species == 0){
    		System.out.println("No people or species found.");
    		return;
    	}
    	
    	System.out.println("Generating new question...");
    	// Getting the planet (with people)
        List<Integer> used = new ArrayList<Integer>();
        Specie specie = null;
        do{
        	Integer s  = getRandomResource(max_species, used);
        	specie = sw.getSpecie(sw.generateEndpoint("species", s));
    		if(specie == null){
    			System.out.println("There was an error in finding the resource " + s);
    		} 
        	used.add(s);
        }while(specie == null || specie.people == null || specie.people.length < 1);
        used.clear();
        // We take one at random as success
        String [] members = specie.people;
        Person correcta = sw.getPerson(members[rand.nextInt(members.length)]);
        // We put all the people on the planet as used so that they are not selected.
        for(String s: members){
        	used.add(sw.getIDFromURL(s));
        }
        
        // We are looking for 3 more
        List<Person> people = new ArrayList<Person>();
        int counter = 0;
    	while(counter < 3){
    		Integer p = getRandomResource(max_people, used);
    		Person person = sw.getPerson(sw.generateEndpoint("people", p));
    		if(person == null){
    			System.out.println("There was an error in finding the resource " + p);
    		} else {
    			people.add(person);
    			counter++;
    		}
    		used.add(p);
    	}
    	// We enter the correct one randomly
    	int pos_success = rand.nextInt(4);
    	people.add(pos_success, correcta);
    	
    	// We read the option
    	Integer n = null;
    	do{
    		System.out.print("Who is member of this specie "+ specie.name +"?");
    		for(int i = 0; i < 4; i++){
    			System.out.print(" ["+i+"] "+ people.get(i).name);
    		}
    		System.out.println();
    		try{
    			n = Integer.parseInt(sc.nextLine());
    		}catch(NumberFormatException ex){
    			n = -1;
    		}
    	}while(n < 0 || n > 3);
    	
    	// The results are shown    	
    	for(Person p: people){
    		System.out.println(p.name + " is member of " + p.species);
    	}
    	
    	// Results
    	if(n == pos_success){
    		System.out.println("Congratulations!!! "+ success[rand.nextInt(success.length)]);
    	} else {
    		System.out.println("You failed :( " + fail[rand.nextInt(fail.length)]);
    	}
    }
    
    
    
    
    
    
    
    
    // Question linking several resources:
    // - Choose a resource (planet in this case).
    // - Ask about a related resource (person who was born there or was created there).
    // - Search for that resource and check if it is related to the first one (if the person searched for has the original planet as a planet)
    public static void whoLives1(SWClient sw){
    	// Obtaining the number of planets
    	int max_planet = sw.getNumberOfResources("planets");
    	if(max_planet == 0){
    		System.out.println("No planets were found.");
    		return;
    	}
    	
    	System.out.println("Generating new question...");
    	// Getting the planet (which has people)
        List<Integer> used = new ArrayList<Integer>();
        Planet planet = null;
        do{
        	Integer p = getRandomResource(max_planet, used);
        	planet = sw.getPlanet(sw.generateEndpoint("planets", p));
    		if(planet == null){
    			System.out.println("There was an error in finding the resource " + p);
    		}
        	used.add(p);
        }while(planet == null || planet.residents == null || planet.residents.length < 1);

        // We pose the question
        String s = null;
   		System.out.println("Who was born or created in " + planet.name + "?");
   		s = sc.nextLine();
   		// We are looking for the right person
   		Person p = sw.search(s);
   		
   		// We validate the reply and display your details
   		if(p == null){
   			System.out.println("There is no one by that name");
   		} else {
   			System.out.println(p.name + " was born in " + p.homeplanet.name);
   		}
   		
   		// Resultados
   		if(p != null && p.homeplanet.name.equals(planet.name)){
    		System.out.println("Congratulations!!! "+ success[rand.nextInt(success.length)]);
    	} else {
    		System.out.println("You failed :( " + fail[rand.nextInt(fail.length)]);
    	}
    }
    
    
    
    
    
    
    // Similar to the previous one, but instead of asking you to write, you are offered an alternative:
    // - One is chosen at random from those available in the resource, person on the planet (the correct one).
    // - Randomly search for 3 others that are not related to the resource (the incorrect ones).
    // - The correct one is randomly inserted.
    public static void whoLives2(SWClient sw){
    	
    	// Obtaining the number of planets and people
    	int max_people = sw.getNumberOfResources("people");
    	int max_planet = sw.getNumberOfResources("planets");
    	if(max_people == 0 || max_planet == 0){
    		System.out.println("No people or planets found.");
    		return;
    	}
    	
    	System.out.println("Generating new question...");
    	// Getting the planet (with people)
        List<Integer> used = new ArrayList<Integer>();
        Planet planet = null;
        do{
        	Integer p = getRandomResource(max_planet, used);
        	planet = sw.getPlanet(sw.generateEndpoint("planets", p));
    		if(planet == null){
    			System.out.println("There was an error in finding the resource " + p);
    		} 
        	used.add(p);
        }while(planet == null || planet.residents == null || planet.residents.length < 1);
        used.clear();
        // We take one at random as success
        String [] residents = planet.residents;
        Person correcta = sw.getPerson(residents[rand.nextInt(residents.length)]);
        // We put all the people on the planet as used so that they are not selected.
        for(String s: residents){
        	used.add(sw.getIDFromURL(s));
        }
        
        // We are looking for 3 more
        List<Person> people = new ArrayList<Person>();
        int counter = 0;
    	while(counter < 3){
    		Integer p = getRandomResource(max_people, used);
    		Person person = sw.getPerson(sw.generateEndpoint("people", p));
    		if(person == null){
    			System.out.println("There was an error in finding the resource " + p);
    		} else {
    			people.add(person);
    			counter++;
    		}
    		used.add(p);
    	}
    	// We enter the correct one randomly
    	int pos_success = rand.nextInt(4);
    	people.add(pos_success, correcta);
    	
    	// We read the option
    	Integer n = null;
    	do{
    		System.out.print("Who was born or made in "+planet.name +"?");
    		for(int i = 0; i < 4; i++){
    			System.out.print(" ["+i+"] "+ people.get(i).name);
    		}
    		System.out.println();
    		try{
    			n = Integer.parseInt(sc.nextLine());
    		}catch(NumberFormatException ex){
    			n = -1;
    		}
    	}while(n < 0 || n > 3);
    	
    	// The results are shown    	
    	for(Person p: people){
    		System.out.println(p.name + " was born in " + p.homeplanet.name);
    	}
    	
    	// Results
    	if(n == pos_success){
    		System.out.println("Congratulations!!! "+ success[rand.nextInt(success.length)]);
    	} else {
    		System.out.println("You failed :( " + fail[rand.nextInt(fail.length)]);
    	}
    }     
			
	private static String [] success = {"That is the way", 
			"You are one with the Force. The Force is with you",
			"May the Force be with you",
			"Nothing happens by accident",
			"Surely, wonderful your mind is",
			"When you left, I was but the apprentice. Now you are the master",
			"The Force is calling to you, let it in.",
			"Your midichlorian count must be very high",
			"A lesson learned is a lesson earned.",
			"You must believe in yourself or no one else will", "You must believe in yourself or no one else will",
			"The path to wisdom is easy for those who are not blinded by ego" };
	private static String [] fail = {"The best teacher, fail is.",
			"Fear is the way to the Dark Side. Fear leads to anger, anger leads to hate, hate leads to suffering. I sense a lot of fear in you",
			"Your lack of faith is annoying",
			"The ability to talk does not make you intelligent",
			"Concentrate on the moment. Feel, do not think, use your instinct",
			"Do not try. Do it, or do not do it, but do not try",
			"Patience, use the Force. Think",
			"I feel a disturbance in the Force.",
			"The dark side is intensifying in you",
			"The first step in correcting a mistake is patience", "The first step in correcting a mistake is patience",
			"Overconfidence is the most dangerous of carelessness",
			"The path of ignorance is guided by fear" };

}

////C:\Users\JOsus\Downloads\sw2023-client-rysd\sslkey-app.log
