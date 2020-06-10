package BackEnd.OperationDir;

import java.util.Date;

public class CyclicOperation extends Operation {
    protected Date expected_day;
    protected Date last_time;
    protected Date frequency;
    public CyclicOperation(double value, double balance, long ID, Date day, String tag, String description) {
        this.value = value;
        this.balance = balance;
        this.ID = ID;
        this.day = day;
        this.tag = tag;
        this.description = description;
    }
}
