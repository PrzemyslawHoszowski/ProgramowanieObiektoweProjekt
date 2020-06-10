package BackEnd.OperationDir;

import java.util.Date;

public class Income extends Operation {
    public Income(double value, double balance, long ID, Date day, String tag, String description) {
        this.value = value;
        this.balance = balance;
        this.ID = ID;
        this.day = day;
        this.tag = tag;
        this.description = description;
    }
    public Income(String line){
        String parts[] = line.split(";");
        ID = Integer.parseInt(parts[1]);
        value = Double.parseDouble(parts[2]);
        balance = Double.parseDouble(parts[3]);
        tag = parts[4];
        description = parts[5];
    }
}
