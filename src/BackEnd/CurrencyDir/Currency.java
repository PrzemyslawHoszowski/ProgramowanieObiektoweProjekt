package BackEnd.CurrencyDir;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
    public void Update() throws IOException, ParseException {
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
            URL url = new URL ("http://api.nbp.pl/api/exchangerates/rates/A/"+ name);
            InputStream stream = url.openStream();
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder sb = new StringBuilder();
            String inline = "";
            while ((inline = inputReader.readLine()) != null)
                sb.append(inline);
            exchangeRate.day = new SimpleDateFormat("yyyy-MM-dd").parse(
            (sb.toString().split(":")[6].split(",")[0].split("\"")[1]));
            exchangeRate.value = Double.parseDouble(sb.toString().split(":")[7].split("}")[0]);

            System.out.println(exchangeRate.value);
            System.out.println(name);
            System.out.println(exchangeRate.day);

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
