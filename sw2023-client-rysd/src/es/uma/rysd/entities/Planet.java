package es.uma.rysd.entities;

//Class obtained when querying the information of a planet

public class Planet {
	public String name;
	public String diameter;
	public String rotation_period;
	public String orbital_period;
	public String gravity;
	public String population;
	public String climate;
	public String terrain;
	public String surface_water;
	public String[] residents;
	
	public String toString(){
		return name + " ("+terrain+" - "+climate+")";
	}
}
