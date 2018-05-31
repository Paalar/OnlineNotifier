package com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline;


import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MeetingsJSONDecode extends AsyncTask<Void, Void, Void> {

    private final static String sOffice = "https://passoa.online.ntnu.no/api/affiliation/online";
    private final static String MEEETING = "meeting";
    private Meetings mMeetings;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL officeURL = new URL(sOffice);
            setMeetings(parseMeetingStatus(officeURL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    private void setMeetings(Meetings meetings) {
        mMeetings = meetings;
    }

    public Meetings getMeetings() {
        return mMeetings;
    }

    private Meetings parseMeetingStatus(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        try {
            return meetingParser(reader);
        } finally {
            reader.close();
        }
    }

    private Meetings meetingParser(JsonReader reader) throws IOException {
        mMeetings = null;

        while (reader.hasNext()) {
            reader.beginObject();
            try {
                String name = reader.nextName();
                switch (name) {
                    case MEEETING:
                        mMeetings = parseMeetings(reader);
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return mMeetings;
    }

    private Meetings parseMeetings(JsonReader reader) throws IOException, ParseException {
        final String MESSAGE = "message", FREE = "free", MEETINGS = "meetings";
        String message = "";
        boolean responsible = true;
        List<Meeting> meetings = new ArrayList<>();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case MESSAGE:
                    message = reader.nextString();
                    break;
                case FREE:
                    responsible = reader.nextBoolean();
                    break;
                case MEETINGS:
                    try {
                        meetings = parseMeetingList(reader);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Meetings(message, responsible, meetings);
    }

    private List<Meeting> parseMeetingList(JsonReader reader) throws IOException, ParseException {
        final String SUMMARY = "summary", START = "start", END = "end", PRETTY = "pretty", MESSAGE = "message";
        String summary = "";
        Calendar startDate = new GregorianCalendar(), endDate = new GregorianCalendar();
        String pretty = "";
        String message = "";
        Meeting meeting;
        List<Meeting> meetingList = new ArrayList<>();

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
                        startDate = parseMettingTime(reader);
                        break;
                    case END:
                        endDate = parseMettingTime(reader);
                        break;
                    case PRETTY:
                        pretty = reader.nextString();
                        break;
                    case MESSAGE:
                        message = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            meeting = new Meeting(summary, startDate, endDate, pretty, message);
            meetingList.add(meeting);
        }
        reader.endArray();
        return meetingList;
    }

    private Calendar parseMettingTime(JsonReader reader) throws IOException, ParseException {
        final String DATE = "date", PRETTY = "pretty";
        Calendar date = new GregorianCalendar();
        reader.beginObject();
        while(reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case DATE:
                    date = NotifierJSONDecode.parseDate(reader.nextString());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return date;
    }
}
