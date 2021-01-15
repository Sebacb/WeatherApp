package ro.mta.se.lab;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerTest {

    private Controller controllerUnderTest;

    @Before
    public void setUp() {
        controllerUnderTest = new Controller();
        controllerUnderTest.cityList = mock(Map.class);
    }

    @Test
    public void testReadCities() throws Exception {
        // Setup
        when(controllerUnderTest.cityList.get("key")).thenReturn(new ArrayList<>(Arrays.asList("value")));
        when(controllerUnderTest.cityList.put("key", new ArrayList<>(Arrays.asList()))).thenReturn(new ArrayList<>(Arrays.asList()));

        // Run the test
        controllerUnderTest.readCities();

        // Verify the results
    }

    @Test(expected = IOException.class)
    public void testReadCities_ThrowsIOException() throws Exception {
        // Setup
        when(controllerUnderTest.cityList.get("key")).thenReturn(new ArrayList<>(Arrays.asList("value")));
        when(controllerUnderTest.cityList.put("key", new ArrayList<>(Arrays.asList()))).thenReturn(new ArrayList<>(Arrays.asList()));

        // Run the test
        controllerUnderTest.readCities();
    }

    @Test
    public void testReadCities_MapGetReturnsNull() throws Exception {
        // Setup
        when(controllerUnderTest.cityList.get("key")).thenReturn(null);
        when(controllerUnderTest.cityList.put("key", new ArrayList<>(Arrays.asList()))).thenReturn(new ArrayList<>(Arrays.asList()));

        // Run the test
        controllerUnderTest.readCities();

        // Verify the results
    }

    @Test
    public void testInitialize() {
        // Setup
        when(controllerUnderTest.cityList.get("key")).thenReturn(new ArrayList<>(Arrays.asList("value")));
        when(controllerUnderTest.cityList.put("key", new ArrayList<>(Arrays.asList()))).thenReturn(new ArrayList<>(Arrays.asList()));
        when(controllerUnderTest.cityList.keySet()).thenReturn(new HashSet<>(Arrays.asList("value")));

        // Run the test
        controllerUnderTest.initialize();

        // Verify the results
    }

    @Test
    public void testInitialize_MapGetReturnsNull() {
        // Setup
        when(controllerUnderTest.cityList.get("key")).thenReturn(null);
        when(controllerUnderTest.cityList.put("key", new ArrayList<>(Arrays.asList()))).thenReturn(new ArrayList<>(Arrays.asList()));
        when(controllerUnderTest.cityList.keySet()).thenReturn(new HashSet<>(Arrays.asList("value")));

        // Run the test
        controllerUnderTest.initialize();

        // Verify the results
    }

    @Test
    public void testCountryChange() {
        // Setup
        when(controllerUnderTest.cityList.get("key")).thenReturn(new ArrayList<>(Arrays.asList("value")));

        // Run the test
        controllerUnderTest.countryChange();

        // Verify the results
    }

    @Test
    public void testCountryChange_MapReturnsNull() {
        // Setup
        when(controllerUnderTest.cityList.get("key")).thenReturn(null);

        // Run the test
        controllerUnderTest.countryChange();

        // Verify the results
    }

    @Test
    public void testCitySelect() {
        // Setup

        // Run the test
        controllerUnderTest.citySelect();

        // Verify the results
    }
}
