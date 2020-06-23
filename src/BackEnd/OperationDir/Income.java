package BackEnd.OperationDir;

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
    }

    @Override
    public String toString(){
            return ID + ";-1;" + new SimpleDateFormat("dd.MM.yyyy").format(day) + ";" + tag + ";"  +
                    String.format("%.2f",value) + ";" + String.format("%.2f",balance) + ";" + description;
    }

    public String toSave(){
        return "-1;" + ID + ";" + value + ";" + balance + ";" +  new SimpleDateFormat("dd.MM.yyyy").format(day)
                + ";" + tag + ";" + description;
    }
}
