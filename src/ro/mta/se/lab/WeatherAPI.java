package ro.mta.se.lab;

import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import org.json.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class WeatherAPI {


    public Map <String, Double> result = new HashMap<String, Double>();



    public WeatherAPI(String id)  {
        // Find out the rain chance from the forecast because the basic weather has no entry
        String urlPop = "http://api.openweathermap.org/data/2.5/forecast?id=" + id +"&APPID=7dc5e57aac162b94c9114262bd98219d&units=metric&cnt=1";
        String urlWeather = "http://api.openweathermap.org/data/2.5/weather?id=" + id +"&APPID=7dc5e57aac162b94c9114262bd98219d&units=metric&cnt=1";
        try {
            // Get request
            String response = sendGET(urlPop);
            JSONObject obj = new JSONObject(response);

            // Parsing the response
            JSONArray aux =  (JSONArray)obj.get("list");
            String pop;
            try {
                // POP is 0 < POP < 1 so we have to multiply it with one hundred to get the real chance
                Double popValue = ((Double) ((((JSONObject) (aux.get(0))).get("pop")))) * 100;
                pop = popValue.toString();
            }
            catch(Exception e) {
                // Sometimes the value is an integer instead of a double
                Integer popValue = ((Integer) ((((JSONObject) (aux.get(0))).get("pop")))) * 100;
                pop = popValue.toString();
            }


            response = sendGET(urlWeather);
            obj = new JSONObject(response);

            // Storing the results
            Double temperature = obj.getJSONObject("main").getDouble("temp");
            Double humidity = obj.getJSONObject("main").getDouble("humidity");
            Double wind = obj.getJSONObject("wind").getDouble("speed");


            result.put("temperature", temperature);
            result.put("humidity", humidity);
            result.put("wind", wind);
            result.put("pop", Double.parseDouble(pop));

            // Writing the history file
            String history = obj.toString() + "\n";
            try
            {
                String filename= "history";
                FileWriter fw = new FileWriter(filename,true); //the true will append the new data
                fw.write(history);//appends the string to the file
                fw.close();
            }
            catch(IOException ioe)
            {
                System.err.println("IOException: " + ioe.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String city, String country) {



    }

    // Send GET Request method
    private static String sendGET(String GET_URL) throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString();
        } else {
            return "GET request not worked";
        }

    }
}
