package BackEnd.OperationDir;

import java.util.Date;

public class PlannedIncome extends BackEnd.OperationDir.CyclicOperation {

    public PlannedIncome(double value, double balance, long ID, Date day, String tag, String description) {
        super(value, balance, ID, day, tag, description);
    }
}
