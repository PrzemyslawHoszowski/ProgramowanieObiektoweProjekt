package BackEnd.CurrencyDir;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;


public class Currency {
    int ID;
    String name;
    // TODO private List<ExchangeRate> value_history;
    ExchangeRate exchangeRate;
    public Currency(int ID, String name, double value, Date day) throws ParseException {
        this.ID = ID;
        this.name = name;
        exchangeRate = new ExchangeRate();
        exchangeRate.value = value;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        exchangeRate.day = format.parse(format.format(day));
    }
    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return ID + ";" + name + ";" + exchangeRate.value + ";" +
                new SimpleDateFormat("dd.MM.yyyy").format(exchangeRate.day);
    }

    ///Funkcja aktualizująca kurs danej waluty
    public void Update() throws IOException, ParseException, ParserConfigurationException, SAXException {
        if (name.compareTo("PLN") == 0) {
            exchangeRate.value = 1;
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            try {
                exchangeRate.day = format.parse(format.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return;
        }
        URL xmlURL = new URL("http://api.nbp.pl/api/exchangerates/rates/A/"+ name + "/?format=xml");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(xmlURL.openStream()));
        doc.normalize();
        Element all = doc.getDocumentElement();

        Element element = (Element) all.getElementsByTagName("Rates").item(0);
        System.out.println(element.toString());
        /// Troche nie potrzebne ale łatwiejsze do późniejszej zmiany
        NodeList nlist = element.getElementsByTagName("Rate");
        /// nlist jest teraz lista kursów danej waluty (name)
        Element rateInfo = (Element) nlist.item(0);

        Element date = (Element) rateInfo.getElementsByTagName("EffectiveDate").item(0);
        Element rate = (Element) rateInfo.getElementsByTagName("Mid").item(0);
        exchangeRate.day = new SimpleDateFormat("yyyy-MM-dd").parse(date.getTextContent());
        exchangeRate.value = Double.parseDouble(rate.getTextContent());
    }

    public int getID() {
        return ID;
    }

    public double getExchangeRate() {
        return exchangeRate.value;
    }

    public Date getExchangeDate(){
        return exchangeRate.day;
    }
}
