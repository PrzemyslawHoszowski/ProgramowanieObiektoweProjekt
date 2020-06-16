package BackEnd.OperationDir;

import java.util.Date;

public class Operation{
    protected double value;
    protected double balance;
    protected long ID;
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

    public long getID(){
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

    public void setID(long ID) {
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

    public String toString(){
        return ID + ";" + day + ";" + tag + ";"  + value  +  ";" + balance;
    }
}
