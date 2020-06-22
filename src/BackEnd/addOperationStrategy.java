package BackEnd;

import BackEnd.OperationDir.Income;
import BackEnd.OperationDir.Operation;

import java.util.Date;

public abstract class addOperationStrategy {
    abstract void addOperation(Operation operation);
    abstract void changeAfter(Date day, double diffrence, int ID);
    abstract void changeBalance(double change);
    public void addNewOperation(Operation operation){
        addOperation(operation);
        if (operation instanceof Income){
            changeAfter(operation.getDay(),operation.getValue(),operation.getID());
            changeBalance(operation.getValue());
        }
        else{
            changeAfter(operation.getDay(),-operation.getValue(),operation.getID());
            changeBalance(-operation.getValue());
        }
    }
}
