package BackEnd.OperationDir;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Expanse extends Operation {
    int priority; /// Może mieć wartość od 0 do 20
    public Expanse(int priority, double value, double balance, int ID, Date day, String tag, String description) throws Exception {
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

    public Expanse(String line){
        String parts[] = line.split(";");
        priority = Integer.parseInt(parts[0]);
        ID = Integer.parseInt(parts[1]);
        value = Double.parseDouble(parts[2]);
        balance = Double.parseDouble(parts[3]);
        tag = parts[4];
        description = parts[5];
    }
    public String toString(){
        if (day != null)
            return ID + ";" + priority + ";" + new SimpleDateFormat("dd.MM.yyyy").format(day) + ";" + tag + ";"  + value  +  ";" +
                    balance + ";" + description;
        return ID + ";" + priority + ";Brak;" + tag + ";"  + value  +  ";" + balance + ";" + description;
    }

}
