package BackEnd.OperationDir;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Operation{
    protected double value;
    protected double balance;
    protected int ID;
    protected Date day;
    protected String tag;
    protected String description;

    public double getValue(){
        return value;
    }

    public double getBalance(){
        return balance;
    }

    public int getID(){
        return ID;
    }

    public Date getDay(){
        return day;
    }

    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPriority(){
        return -1;
    }

    public void changeBalance(double diffrence){
        balance += diffrence;
    }

    public String toString(){
        if (day != null)
            return ID + ";" + new SimpleDateFormat("dd.MM.yyyy").format(day) + ";" + tag + ";"  + value  +  ";" +
                    balance + ";" + description;
        return ID + ";Brak;" + tag + ";"  + value  +  ";" + balance + ";" + description;
    }

    ///Funkcja zwracająca postać transakcji w pliku tekstowym
    public String toSave() {return "";};
}
