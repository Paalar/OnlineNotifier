package com.dwarfstar.gpd.onlinenotifier.JSON.Cantina;


import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;

import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CantinaJSONDecode extends AsyncTask<Void, Void, Void> {

    private final static String sCantina = "https://passoa.online.ntnu.no/api/cantina";
    private final static String ID = "id", CANTINA_NAME = "name", FEED = "feed", LUNCH = "lunch", DINNER = "dinner";
    private static List<Cantina> mCantina;

    @Override
    protected Void doInBackground(Void... voids) {
        if(mCantina != null) {
            return null;
        }
        try {
            URL url = new URL(sCantina);
            setCantina(parseCantinaStatus(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public List<Cantina> getCantina() {
        return mCantina;
    }

    private void setCantina(List<Cantina> cantina) {
        mCantina = cantina;
    }

    private List<Cantina> parseCantinaStatus(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));

        return iterateThroughCantinaNames(getAvailableCantinas(reader));
    }

    private List<Cantina> iterateThroughCantinaNames(List<String> cantinaNames) {
        List<Cantina> cantinas = new ArrayList<>();
        for (String name : cantinaNames) {
            cantinas.add(getSpecificCantinaInfo(name));
        }
        return cantinas;
    }

    private List<String> getAvailableCantinas(JsonReader reader) throws IOException {
        final String ID = "id", FEED = "feed";
        String cantinaName = "";
        List<String> openCantinas = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case ID:
                        cantinaName = reader.nextString();
                        break;
                    case FEED:
                        if  (reader.peek() != JsonToken.NULL && parseFeed(reader)) {
                            openCantinas.add(cantinaName);
                        } else {
                            reader.skipValue();
                        }
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
        }
        reader.endArray();
        return openCantinas;
    }

    private Boolean parseFeed(JsonReader reader) throws IOException {
        final String LUNCH = "lunch", DINNER = "dinner";
        Boolean isAvailable = false;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case LUNCH:
                    if(reader.peek() != JsonToken.NULL) {
                        isAvailable = true;
                    }
                    reader.skipValue();
                    break;
                case DINNER:
                    if (reader.peek() != JsonToken.NULL) {
                        isAvailable = true;
                    }
                    reader.skipValue();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return isAvailable;
    }

    private Cantina getCantinaOffers(JsonReader reader) throws IOException {
        final String NAME = "name", HOURS = "hours", LUNCH = "lunch", DINNER = "dinner";
        String nameOfLocation = "";
        Hours cantinaInfo = null;
        List<Lunch> lunches = null;
        List<Dinner> dinners = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case NAME:
                    nameOfLocation = reader.nextString();
                    break;
                case HOURS:
                    cantinaInfo = parseHours(reader);
                    break;
                case LUNCH:
                    lunches = parseLunch(reader);
                    break;
                case DINNER:
                    dinners = parseDinner(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Cantina(nameOfLocation, cantinaInfo, lunches, dinners);
    }

    private List<Lunch> parseLunch(JsonReader reader) throws IOException {
        final String TEXT = "text", PRICE = "price";
        String foodDish = "";
        List<Integer> price = new ArrayList<>();
        List<Lunch> menu = new ArrayList<>();

        if (reader.peek() == JsonToken.BEGIN_ARRAY) {
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    switch (name) {
                        case TEXT:
                            foodDish = reader.nextString();
                            break;
                        case PRICE:
                            price = parsePrice(reader);
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                reader.endObject();
            }
            reader.endArray();
        } else if (reader.peek() == JsonToken.BEGIN_OBJECT) {
            reader.beginObject();
            reader.nextName();
            foodDish = reader.nextString();
            reader.endObject();
        }
        menu.add(new Lunch(foodDish, price));
        return menu;
    }

    private List<Dinner> parseDinner(JsonReader reader) throws IOException {
        final String TEXT = "text", PRICE = "price";
        String foodDish = "";
        List<Integer> price = new ArrayList<>();
        List<Dinner> menu = new ArrayList<>();

        if (reader.peek() == JsonToken.BEGIN_ARRAY) {
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    switch (name) {
                        case TEXT:
                            foodDish = reader.nextString();
                            break;
                        case PRICE:
                            price = parsePrice(reader);
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                menu.add(new Dinner(foodDish, price));
                reader.endObject();
            }
            reader.endArray();
        } else if (reader.peek() == JsonToken.BEGIN_OBJECT) {
            reader.beginObject();
            reader.nextName();
            foodDish = reader.nextString();
            reader.endObject();
            menu.add(new Dinner(foodDish, price));;
        }
        return menu;
    }

    private List<Integer> parsePrice(JsonReader reader) throws IOException {
        List<Integer> price = new ArrayList<>();

        if (reader.peek() == JsonToken.BEGIN_ARRAY) {
            reader.beginArray();
            while (reader.hasNext()) {
                price.add(Integer.valueOf(reader.nextString()));
            }
            reader.endArray();
        } else if (reader.peek() == JsonToken.NUMBER) {
            price.add(reader.nextInt());
        } else {
            reader.skipValue();
        }

        return price;
    }



    private Hours parseHours(JsonReader reader) throws IOException {
        final String OPEN = "open", MESSAGE = "message";
        Boolean open = false;
        String cantinaMessage = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String hoursName = reader.nextName();
            switch (hoursName) {
                case OPEN:
                    open = reader.nextBoolean();
                    break;
                case MESSAGE:
                    cantinaMessage = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Hours(open, cantinaMessage);
    }

    private Cantina getSpecificCantinaInfo(String cantinaName) {
        try {
            URL url = new URL(sCantina + "/" + cantinaName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
            return getCantinaOffers(reader);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
