package com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline;

import android.os.AsyncTask;
import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class NotifierJSONDecode extends AsyncTask<Void, Void, Void> {

    private final static String sOffice = "https://passoa.online.ntnu.no/api/affiliation/online";
    private final static String MEETING = "meeting", SERVANT = "servant", COFFEE = "coffee", STATUS = "status";
    private Office mOffice;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL officeURL = new URL(sOffice);
            setOffice(parseOfficeStatus(officeURL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public Office getOffice() {
        return mOffice;
    }

    private void setOffice(Office office) {
        mOffice = office;
    }

    private Office parseOfficeStatus(URL url) throws IOException, JSONException, ParseException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
        try {
            return officeParser(jsonReader);
        } finally {
            jsonReader.close();
        }
    }

    private Office officeParser(JsonReader reader) throws IOException, ParseException {
        Meetings meetings = null;
        Servant servant = null;
        Coffee coffee = null;
        com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.Status status = null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {
                    case MEETING:
                        reader.skipValue();
                        //meetings = parseMeetings(reader);
                        break;
                    case SERVANT:
                        servant = parseServant(reader);
                        break;
                    case COFFEE:
                        coffee = parseCoffee(reader);
                        break;
                    case STATUS:
                        status = parseStatus(reader);
                        break;
                    default:
                        reader.skipValue();

                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Office(meetings, servant, coffee, status);
    }



    private Servant parseServant(JsonReader reader) throws IOException, ParseException {
        final String RESPONSIBLE = "responsible", MESSAGE = "message", SERVANTS = "servants";
        boolean responsible = true;
        String message = "";
        List<ServantPerson> servantPersonList = new ArrayList<>();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case RESPONSIBLE:
                    responsible = reader.nextBoolean();
                    break;
                case MESSAGE:
                    message = reader.nextString();
                    break;
                case SERVANTS:
                    servantPersonList = parseServantPerson(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        Servant servant = new Servant(responsible, message, servantPersonList);
        return servant;
    }

    private List<ServantPerson> parseServantPerson(JsonReader reader) throws IOException, ParseException {
        final String SUMMARY = "summary", START = "start", END = "end", PRETTY = "pretty";
        String summary = "";
        ServantTime start = null;
        ServantTime end = null;
        String pretty = "";
        List<ServantPerson> servantPersonList = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case SUMMARY:
                        summary = reader.nextString();
                        break;
                    case START:
                        start = parseServantTime(reader);
                        break;
                    case END:
                        end = parseServantTime(reader);
                        break;
                    case PRETTY:
                        pretty = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            ServantPerson servantPerson = new ServantPerson(summary, start, end, pretty);
            servantPersonList.add(servantPerson);
        }
        reader.endArray();
        return servantPersonList;
    }

    private ServantTime parseServantTime(JsonReader reader) throws IOException, ParseException {
        final String TIMEZONE = "timeZone", DATE = "date", PRETTY = "pretty";
        String timezone = "";
        Calendar date = null;
        String pretty = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case TIMEZONE:
                    timezone = reader.nextString();
                    break;
                case DATE:
                    date = parseDate(reader.nextString());
                    break;
                case PRETTY:
                    pretty = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new ServantTime(timezone, date, pretty);

    }

    private Coffee parseCoffee(JsonReader reader) throws IOException, ParseException {
        final String DATE = "date", POTS = "pots";
        Calendar date = null;
        int pots = 0;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case DATE:
                    if(!(reader.peek() == null)) {
                        date = parseDate(reader.nextString());
                    }
                    break;
                case POTS:
                    pots = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        Coffee coffee = new Coffee(date, pots);
        return coffee;
    }

    private com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.Status parseStatus(JsonReader reader) throws IOException {
        final String STATUS = "status", UPDATED = "updated";
        boolean status = true;
        String updated = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case STATUS:
                    status = reader.nextBoolean();
                    break;
                case UPDATED:
                    updated = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.Status statusObject = new com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.Status(status, updated);
        return statusObject;
    }

    public static Calendar parseDate(String timeZone) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = format.parse(timeZone);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return calendar;
    }

}
