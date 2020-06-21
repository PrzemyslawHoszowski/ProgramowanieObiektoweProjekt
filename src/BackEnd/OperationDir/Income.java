package BackEnd.OperationDir;

import BackEnd.Account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Income extends Operation {
    public Income(double value, double balance, int ID, Date day, String tag, String description) {
        this.value = value;
        this.balance = balance;
        this.ID = ID;
        this.day = day;
        this.tag = tag;
        this.description = description;
    }
    public Income(String line) throws ParseException {
        String parts[] = line.split(";");
        ID = Integer.parseInt(parts[1]);
        value = Double.parseDouble(parts[2]);
        balance = Double.parseDouble(parts[3]);
        day = new SimpleDateFormat("dd.MM.yyyy").parse(parts[4]);
        tag = parts[5];
        description = parts[6];
        System.out.println(this.toString());
    }

    @Override
    public String toString(){
        if (day != null)
            return ID + ";-1;" + new SimpleDateFormat("dd.MM.yyyy").format(day) + ";" + tag + ";"  + value  +  ";" +
                    balance + ";" + description;
        return ID + ";-1;Brak;" + tag + ";"  + value  +  ";" + balance + ";" + description;
    }
}
