package BackEnd.OperationDir;

import java.util.Date;

public class PlannedExpanse extends BackEnd.OperationDir.CyclicOperation {
    public PlannedExpanse(double value, double balance, long ID, Date day, String tag, String description) {
        super(value, balance, ID, day, tag, description);
    }
}
