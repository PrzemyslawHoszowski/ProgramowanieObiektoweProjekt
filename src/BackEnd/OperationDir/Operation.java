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

    public int compare(int mode, Operation object){
        return 1;///TODO
    }

    public double getValue(){
        return value;
    }

    public void setValue(double new_value){
        value = new_value;
    }

    public double getBalance(){
        return balance;
    }

    public void setBalance(double new_balance){
        balance = new_balance;
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

    public void setDay(Date day) {
        this.day = day;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int newPrior) throws Exception {}

    public int getPriority(){
        return -1;
    }

    public String toString(){
        if (day != null)
            return ID + ";" + new SimpleDateFormat("dd.MM.yyyy").format(day) + ";" + tag + ";"  + value  +  ";" +
                    balance + ";" + description;
        return ID + ";Brak;" + tag + ";"  + value  +  ";" + balance + ";" + description;
    }
}
