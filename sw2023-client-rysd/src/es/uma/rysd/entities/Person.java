package es.uma.rysd.entities;

// Class obtained when a character's information is queried

public class Person {
	public String name;
	public String birth_year;
	public String eye_color;
	public String gender;
	public String hair_color;
	public String height;
	public String mass;
	public String skin_color;
	public String homeworld;
	public String[] films;
	public String[] species;
	public String[] starships;
	public String[] vehicles;
	
	// All of the above attributes were obtained directly from the object returned by the query.
	// The following should be filled in if deemed necessary by consulting the URLs returned in the query
	// in the respective fields above.
	public Planet homeplanet = null;
	public Film[] movies = null;
	public Specie[] especies = null;
	public Starship[] spaceships = null;
	public Vehicle[] machines = null;
	
	public String toString(){
		String text = name + " ("+ gender +") nacio en el a�o " + birth_year + " y su epecie es " + species;
		if(homeplanet != null ) text+= " en "+homeplanet;
		text += "\nPesa: " + mass + " Kg y mide: " + height + " cm\n";
		if(movies != null){
			text += "Aparece en:\n";
			for(Film f: movies){
				text += "- "+f+"\n";
			}
		}
		return text;		
	}
}
