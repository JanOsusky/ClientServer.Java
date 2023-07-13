package es.uma.rysd.entities;

//Class obtained when searching for a person's name that can potentially return many (or none).

public class SearchResponse {
	public Integer count;
	public Person[] results;
	
}
