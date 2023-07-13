package es.uma.rysd.app;

import javax.net.ssl.HttpsURLConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.google.gson.Gson;

import es.uma.rysd.entities.*;

public class SWClient {
	// TODO: Fill in the name of the application
    private final String app_name = "Picovina";
    private final int year = 2023;
    
    private final String url_api = "https://swapi.dev/api/";
 
    // Auxiliary methods provided
    
    // Gets the URL of the resource id of type resource
	public String generateEndpoint(String resource, Integer id){
		return url_api + resource + "/" + id + "/";
	}
	
	// Given a URL of a resource get its ID
	public Integer getIDFromURL(String url){
		String[] parts = url.split("/");

		return Integer.parseInt(parts[parts.length-1]);
	}
	
	// Queries a resource and returns how many elements it has
	public int getNumberOfResources(String resource){    	
		// TODO: Deal appropriately with possible exceptions that may occur.
		
    	// TODO: Create the corresponding URL: https://swapi.dev/api/{resource}/ by replacing the resource by the parameter 
    	
    	// TODO: Create the connection from the URL
    	
    	// TODO: Add the User-Agent and Accept headers (see the statement)
    	
    	// TODO: Indicate that it is a GET request
    	
    	// TODO: Check that the code received in the reply is correct.
    	
    	// TODO: Deserialise the response to ResourceCountResponse
		try {
            URL url = new URL(url_api + resource + "/");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", app_name);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                Gson parser = new Gson();
                InputStream in = connection.getInputStream();
                ResourceCountResponse c = parser.fromJson(new InputStreamReader(in), ResourceCountResponse.class);
                return c.count;
            } else {
                // Handle error response
                return -1;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
	}
	
	

    public Person getPerson(String urlname) {
        Person p = null;
        urlname = urlname.replaceAll("http:", "https:");

        try {
            URL url = new URL(urlname);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", app_name);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                Gson parser = new Gson();
                InputStream in = connection.getInputStream();
                p = parser.fromJson(new InputStreamReader(in), Person.class);

                // For exercises 2 and 3
                if (p.homeworld != null) {
                    Planet homePlanet = getPlanet(p.homeworld);
                    p.homeplanet = homePlanet;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }


	public Planet getPlanet(String urlname) {
    	Planet p = null;
    	// In case it comes as http we switch it to https.
    	urlname = urlname.replaceAll("http:", "https:");

    	// TODO: Deal appropriately with possible exceptions that may occur.
		    	
    	
    	// TODO: Create the connection from the received URL
    	
    	// TODO: Add User-Agent and Accept headers (see statement)
    	
    	// TODO: Indicate that it is a GET request
    	
    	// TODO: Check that the code received in the reply is correct.
    	
    	// TODO: Deserialise the response to Planet
    	try {
            URL url = new URL(urlname);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", app_name);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                Gson parser = new Gson();
                InputStream in = connection.getInputStream();
                p = parser.fromJson(new InputStreamReader(in), Planet.class);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;

        
	}
	
	public Specie getSpecie(String urlname) {
    	Specie s = null;
    	// In case it comes as http we switch it to https.
    	urlname = urlname.replaceAll("http:", "https:");

    	// TODO: Deal appropriately with possible exceptions that may occur.
		    	
    	
    	// TODO: Create the connection from the received URL
    	
    	// TODO: Add User-Agent and Accept headers (see statement)
    	
    	// TODO: Indicate that it is a GET request
    	
    	// TODO: Check that the code received in the reply is correct.
    	
    	// TODO: Deserialise the response to Planet
    	try {
            URL url = new URL(urlname);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", app_name);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                Gson parser = new Gson();
                InputStream in = connection.getInputStream();
                s = parser.fromJson(new InputStreamReader(in), Specie.class);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;

        
	}


	public Person search(String name){
    	Person p = null;
    	// TODO: Deal appropriately with possible exceptions that may occur.
		    	
    	// TODO: Create the connection from the URL (url_api + treated name - see statement)
    	
    	// TODO: Add User-Agent and Accept headers (see statement)
    	
    	// TODO: Indicate that it's a GET request
    	
    	// TODO: Check that the code received in the reply is correct
    	
    	// TODO: Deserialise the response to SearchResponse -> Use the first position of the array as the result
    	
        // TODO: For exercises 2 and 3 (you don't need to complete this for exercise 1)
    	// TODO: From the URL in the homreworld field get the planet data and store it in homeplanet attribute
    	   try {
               String encodedName = URLEncoder.encode(name, "UTF-8");
               URL url = new URL(url_api + "people/?search=" + encodedName);
               HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
               connection.setRequestProperty("User-Agent", app_name);
               connection.setRequestProperty("Accept", "application/json");
               connection.setRequestMethod("GET");

               if (connection.getResponseCode() == 200) {
                   Gson parser = new Gson();
                   InputStream in = connection.getInputStream();
                   SearchResponse response = parser.fromJson(new InputStreamReader(in), SearchResponse.class);

                   if (response.results.length > 0) {
                       p = response.results[0];

                       // For exercises 2 and 3
                       if (p.homeworld != null) {
                           Planet homePlanet = getPlanet(p.homeworld);
                           p.homeplanet = homePlanet;
                       }
                   }
               }
           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }

        return p;
    }

}
	