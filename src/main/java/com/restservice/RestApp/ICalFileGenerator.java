package com.restservice.RestApp;

import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Version;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ICalFileGenerator {
    private String prodid =     "PRODID:-//Hubert Liberadzki//iCal4j 1.0//EN\r\n";
    private String calBegin =   "BEGIN:VCALENDAR\r\n";
    private String calEnd =     "END:VCALENDAR\r\n";

    private String fileName =   "kalendarzWeeiaMiesiac";
    private String fileExtension = ".ics";

    private String eventStart = "DTSTART:2019";
    private String eventEnd = "DTEND:2019";

    public void writeToFile(String month, List<String> days, List<String> eventsName){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fileName);
        stringBuilder.append(month);
        stringBuilder.append(fileExtension);

        if(Integer.parseInt(month) < 10)
            month = "0" + month;

        File file = new File(stringBuilder.toString());
        try {
            file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(calBegin);
            bufferedWriter.write(String.valueOf(Version.VERSION_2_0));
            bufferedWriter.write(prodid);
            bufferedWriter.write(String.valueOf(CalScale.GREGORIAN));
            bufferedWriter.write(String.valueOf(Method.PUBLISH));

            for(int i=0; i<days.size()-1; i++){
                bufferedWriter.write("BEGIN:VEVENT\r\n");
                if(days.get(i).length() < 2){
                    System.out.println(days.get(i));
                    bufferedWriter.write(eventStart + month + "0" + days.get(i) + "\r\n");
                    bufferedWriter.write(eventEnd + month + "0" + days.get(i) + "\r\n");
                } else {
                    bufferedWriter.write(eventStart + month + days.get(i) + "\r\n");
                    bufferedWriter.write(eventEnd + month + days.get(i) + "\r\n");
                }
                bufferedWriter.write("SUMMARY:" + eventsName.get(i) + "\r\n");
                System.out.println(eventsName.get(i));
                bufferedWriter.write("END:VEVENT\r\n");
            }
            bufferedWriter.write(calEnd);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
