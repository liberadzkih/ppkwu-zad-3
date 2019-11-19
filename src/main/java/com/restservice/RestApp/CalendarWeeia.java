package com.restservice.RestApp;

import com.sun.corba.se.impl.protocol.InfoOnlyServantCacheLocalCRDImpl;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Version;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CalendarWeeia {


    @RequestMapping("Calendar/{year}/{month}")
    public String getCalendar(@PathVariable String month, @PathVariable String year) throws IOException {
        if(!isMonthCorrect(month) || !isYearCorrect(year)){
            return "Month should be a number between 1 and 12\nYear should be number between 1900-2100";
        }
        String url = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + month + "&lang=1";
        Document document;
        document = Jsoup.parse(new URL(url), 10000);
        Elements html = document.select("a.active");
        Elements events = document.select("div.InnerBox");

        List<String> dates = new ArrayList<>();
        List<String> event = new ArrayList<>();
        for (Element e : html)
            dates.add(e.text());
        for (Element e : events)
            event.add(e.text());

        ICalFileGenerator ical = new ICalFileGenerator();
        ical.writeToFile(month, dates, event);

        return document.outerHtml();

    }

    public boolean isMonthCorrect(String month){
        int s;
        try{
            s = Integer.parseInt(month);
        }
        catch(NumberFormatException e){
            return false;
        }

        if(s < 1 || s > 12)
            return false;
        else
            return true;
    }

    public boolean isYearCorrect(String year){
        int s;
        try{
            s = Integer.parseInt(year);
        }
        catch(NumberFormatException e){
            return false;
        }

        if(s < 1900 || s > 2100)
            return false;
        else
            return true;
    }
}
