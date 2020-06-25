package BackEnd;

import BackEnd.OperationDir.Income;
import BackEnd.OperationDir.Operation;

import java.util.Date;

public abstract class addOperationStrategy {
    ///Dodanie operacji do listy operacji
    abstract void addOperation(Operation operation);
    ///Zmiana salda konta w operacjach po operacji o podanym ID w danym dniu
    abstract void changeAfter(Date day, double diffrence, int ID);
    ///Zmiana liczby reprezentującej saldo konta
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
