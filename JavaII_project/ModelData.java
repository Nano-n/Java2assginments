package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class ModelData extends Thread {
    /* Declare global variables */
    public static String url_day = "https://min-api.cryptocompare.com/data/histoday?aggregate=1&e=CCCAGG&extraParams=CryptoCompare&fsym=BTC&limit=10&tryConversion=false&tsym=USD";
    public static String url_hour = "https://min-api.cryptocompare.com/data/histohour?aggregate=1&e=CCCAGG&extraParams=CryptoCompare&fsym=BTC&limit=10&tryConversion=false&tsym=USD";
    public static String url_min = "https://min-api.cryptocompare.com/data/histominute?aggregate=1&e=CCCAGG&extraParams=CryptoCompare&fsym=BTC&limit=10&tryConversion=false&tsym=USD";

    //private String urlStr;
    private URL url;
    private List<Map> listOfMap;        // a list of map that stores file data
    private List<Map> timePriceMapList; // a list of map that stores time and respective price

    /* Create a constructor */
    public ModelData(String str) throws MalformedURLException {
        url = new URL(str);
        listOfMap = new ArrayList();
        timePriceMapList = new ArrayList<>();
    }

    /* Return a string represented the file content at specific url address*/
    public String getFileContent() throws Exception {
        String inputLine = "";
        String fileContent = "";

        /* Read the data on the url */
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream()));

        while((inputLine = reader.readLine()) != null) {
            fileContent += inputLine;
        }
        reader.close();

        return fileContent;
    }

    @Override
    public void run() {
        /* Retrieve a string array from the file content
           after removing any non alphanumeric characters */
        String[] strArr = new String[0];
        try {
            strArr = this.getFileContent().split("[^a-zA-Z0-9'.]+", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int strArrLen = strArr.length;
        /* Iterate through the string array to find keys and values */
        for(int i = 0; i < strArrLen; i += 1) {
            /* Create a list to store crypto data when time is fetched */
            if(strArr[i].equals("time")) {
                Map<String, String> map = new HashMap<>();

                map.put(strArr[i], (strArr[i+1]));          // key = time, value = time string
                map.put(strArr[i+2], (strArr[i + 3]));      // key = close, value = price string
                map.put(strArr[i+4], (strArr[i + 5]));      // key = high, value = price string
                map.put(strArr[i+6], (strArr[i + 7]));      // key = low, value = price string
                map.put(strArr[i+8], (strArr[i + 9]));      // key = open, value = price string

                /* Add the current map to the list */
                this.listOfMap.add(map);
            }
        }
        setTimePriceMapList();
    }

    /* Convert listOfMap to a list of map that contains a pair of time and its respective price value */
    public void setTimePriceMapList() {
        // this is used to display time in HH:mm:ss format
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd:HH:mm:ss");

        /* Get time duration btw each time point and convert them to milliseconds */
        long time1 = Long.parseLong(this.listOfMap.get(1).get("time").toString()) * 1000;
        long time0 = Long.parseLong(this.listOfMap.get(0).get("time").toString()) * 1000;
        /* Get the chunk of time by dividing the time period by 3 */
        long chunk = Math.floorDiv(time1 - time0, 3);

        int listOfMap_size = this.listOfMap.size();
        /* Iterate through the listOfMap */
        for(int t = 0; t < listOfMap_size; t += 1) {
            /* Create a map to store the time and its corresponding price in order. */
            Map<String, Float> tp_map = new LinkedHashMap<>();

            /* Get the time value and convert it to milliseconds */
            long otime = Long.parseLong(this.listOfMap.get(t).get("time").toString()) * 1000;
            long htime = otime + chunk;
            long ltime = htime + chunk;

            /* Add the pairs of time and price to the map */
            tp_map.put(dateFormat.format(otime), Float.parseFloat(this.listOfMap.get(t).get("open").toString()));
            tp_map.put(dateFormat.format(htime), Float.parseFloat(this.listOfMap.get(t).get("high").toString()));
            tp_map.put(dateFormat.format(ltime), Float.parseFloat(this.listOfMap.get(t).get("low").toString()));

            /* Add the map to the list */
            this.timePriceMapList.add(tp_map);
        }
    }

    /* Return a list of map with time as the key and price as the value for crypto stock market */
    public List<Map> getTimePriceMapList() {
        return this.timePriceMapList;
    }
}
