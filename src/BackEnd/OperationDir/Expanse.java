package BackEnd.OperationDir;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Expanse extends Operation {
    int priority; /// Może mieć wartość od 0 do 20
    public Expanse(int priority, double value, double balance, int ID, Date day, String tag, String description)
            throws Exception {
        if (priority<0 || priority>20)
            throw new Exception("Priorytet wyszedl po za zakres.");
        this.priority =  priority;
        this.ID = ID;
        this.value = value;
        this.balance = balance;
        this.day = day;
        this.tag = tag;
        this.description = description;
    }

    public void setPriority(int priority) throws Exception {
        if (priority<0 || priority>20)
            throw new Exception("Priorytet wyszedl po za zakres.");
        this.priority = priority;
    }

    public int getPriority(){
        return priority;
    }

    public Expanse(String line) throws Exception {
        String parts[] = line.split(";");
        if (parts.length < 7) throw new Exception("Za mało danych");
        priority = Integer.parseInt(parts[0]);
        ID = Integer.parseInt(parts[1]);
        value = Double.parseDouble(parts[2]);
        balance = Double.parseDouble(parts[3]);
        day = new SimpleDateFormat("dd.MM.yyyy").parse(parts[4]);
        tag = parts[5];
        description = parts[6];
    }
    public String toString(){
            return ID + ";" + priority + ";" + new SimpleDateFormat("dd.MM.yyyy").format(day) + ";" + tag + ";" +
                    String.format("%.2f",value) + ";" + String.format("%.2f",balance) + ";" + description;
    }
    public String toSave(){
        return priority + ";" + ID + ";" + value + ";" + balance + ";" +
                new SimpleDateFormat("dd.MM.yyyy").format(day) + ";" + tag + ";" + description;
    }
}
