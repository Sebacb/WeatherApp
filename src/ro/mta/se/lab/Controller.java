package ro.mta.se.lab;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.*;


public class Controller {

    public Text temperature;
    public Text humidity;
    public Text wind;
    public Text precipitation;
    public Text cityL;
    public ChoiceBox countries;
    public ChoiceBox cities;

    public Map<String, String> cityIds = new HashMap<String, String>();
    Map<String, ArrayList<String>> cityList = new HashMap<String, ArrayList<String>>();

    public void readCities() throws IOException {
        String country, city, id;
        String strLine = null;
        FileInputStream fstream = null;

        try {
            fstream = new FileInputStream("list");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Set the city / country list
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        strLine = br.readLine();

        while (true)   {
            try {
                if (!((strLine = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Parsing the lines by words
            String[] words = strLine.split("\\W+");

            country = words[6];
            city = words[1];
            id = words[0];

            // if the country is not already added
            if(cityList.get(country) == null){
                ArrayList oneElement = new ArrayList<String>();
                oneElement.add(city);
                cityList.put(country, oneElement);
            }
            else{
                (cityList.get(country)).add(city);
            }

            cityIds.put(city, id);


        }


        try {
            fstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {


        try {
            this.readCities();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set countriesList = cityList.keySet();

        ArrayList<String> countriesListStr = new ArrayList<String>();
        for (Object value : countriesList)
            countriesListStr.add((String) value);



        countries.setItems(FXCollections.observableArrayList(countriesListStr));
    }

    // Select a new country to query
    @FXML
    void countryChange(){
        String option = (String) countries.getValue();
        cities.setItems(FXCollections.observableArrayList(cityList.get(option)));
    }

    // Selecting the city
    @FXML
    void citySelect(){
        if((String) cities.getValue() != null) {
            String idToGet = cityIds.get((String) cities.getValue());
            WeatherAPI wapi = new WeatherAPI(idToGet);
            temperature.setText(wapi.result.get("temperature").toString() + "°C");
            humidity.setText(wapi.result.get("humidity").toString());
            wind.setText(wapi.result.get("wind").toString());
            cityL.setText((String) cities.getValue());
            precipitation.setText(wapi.result.get("pop").toString());
        }

    }



}

