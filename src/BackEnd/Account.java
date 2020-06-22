package BackEnd;

import BackEnd.CurrencyDir.Currency;
import BackEnd.OperationDir.Expanse;
import BackEnd.OperationDir.Income;
import BackEnd.OperationDir.Operation;
import FrontEnd.CommunicationWindow;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.exit;

public class Account extends addOperationStrategy{
    public long getID() {
        return ID;
    }

    long ID;
    String name;
    List<Object> operation_history;
    List<Object> cyclic_operations;
    Currency used_currency;
    double monthly_limit;
    double balance;
    double minimum_balance;
    String path;

    Account(String line,  HomeBalance homeBalance) throws Exception {
        operation_history = new ArrayList<>();
        cyclic_operations = new ArrayList<>();
        String[] parts = line.split(";");
        if (parts.length != 9) throw new Exception("File is corrupted");
        ID = Integer.parseInt(parts[1]);
        name = parts[2];
        path = parts[3];
        BufferedReader reader = new BufferedReader(new FileReader(parts[3]));

        String OP = reader.readLine();

        boolean corrupted_file = true;
        int mistake_counter = 0;
        while (OP != null) {
            for (int i = 0; OP.charAt(i) != 0 && OP.charAt(i) != ';'; i++) {
                if (OP.charAt(i) == '-') {
                    corrupted_file = false;
                    try {
                        operation_history.add(new Income(OP));
                    }
                    catch (ParseException e){
                        new CommunicationWindow("Nie udało się przetłumaczyć daty operacji");
                        mistake_counter++;
                        if (mistake_counter > 4){
                            exit(1);
                        }
                        break;
                    }
                    break;
                } else if (OP.charAt(i) >= '0' && OP.charAt(i) <= '9') {
                    corrupted_file = false;
                    operation_history.add(new Expanse(OP));
                    break;
                }
            }
            if (corrupted_file) throw new Exception("Format pliku został zaburzony.");
            OP = reader.readLine();
        }
        reader.close();
        /// TODO cyclic ops
        used_currency = homeBalance.get_curr_ref(parts[5]);
        balance = Double.parseDouble(parts[6]);
    }

    Account(int ID,String name, String curr, double balance, double minimum_balance, double monthly_limit,
            HomeBalance homeBalance){
        this.ID = ID;
        this.name = name;
        this.used_currency = homeBalance.get_curr_ref(curr);
        this.balance = balance;
        this.minimum_balance = minimum_balance;
        this.monthly_limit = monthly_limit;
        this.path = "dane/Operacje/operations" + ID + "txt";
        operation_history = new ArrayList<>();
    }

    @Override
    public String toString() {
        return  "ID=" + ID +
                ", nazwa=" + name +
                ", waluta=" + used_currency.getName() +
                ", bilans=" + balance;
    }

    public String[][] getData(){
        String [][] data = new String[operation_history.size()][6];
        int i = 0;
        for (Object op : operation_history){
            data[i] =  op.toString().split(";");
            i++;
        }
        return data;
    }

    public Operation getOperation(int index) throws Exception {
        for (Object a : operation_history){
            if (((Operation) a).getID() == index){
                return (Operation) a;
            }
        }
        throw new Exception("Operacji nie znaleziono");
    }

    int findOperation(int ID){
        int len = operation_history.size();
        for (int i = 0 ; i<len; i++)
            if (((Operation) operation_history.get(i)).getID()==ID) return i;
        return -1;
    }

    public int getNewOperationID(){
        if (operation_history.size() == 0){
            return 0;
        }
        return ((Operation) operation_history.get(operation_history.size()-1)).getID() + 1;
    }

    public void deleteOperation(int ID){
        int i = 0;
        for (Object a : operation_history){
            if (((Operation) a).getID() == ID)
            {
                balance += (((Operation) a).getPriority()==-1? -1 : 1 ) *  ((Operation) a).getValue();
                Operation toDelete = (Operation) operation_history.get(i);
                changeAfter(toDelete.getDay(), toDelete.getPriority()==-1? -toDelete.getValue() :toDelete.getValue(),i);
                operation_history.remove(i);
                return;
            }
            i++;
        }
    }

    double getPreviousBalance(Date day, int ID){
        int pop = previousOperation(day,ID);
        if (pop == ID) return 0;
        return ((Operation) operation_history.get(previousOperation(day, ID))).getBalance();
    }
    /// ?Wzorzec projektowy : Fabryka?
    public Operation createOperation(int prior, Date day, String tag, double value, String desc) {
        int newID = getNewOperationID();
        if (prior == -1)
            return new Income(value,getPreviousBalance(day,newID) + value, newID, day, tag, desc);
        else {
            try {
                return new Expanse(prior,value,getPreviousBalance(day,newID) - value, newID,day,tag,desc);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null; /// Nigdy do tego momentu nie dojdzie, wartość prior została sprawdzona na etapie wczytania
                         /// od użytkownika
        }
    }

    public void addOperation(Operation operation){
        operation_history.add(operation);
    }

    public double getBalance(){
        return balance;
    }

    void changeBalance(double change){
        balance += change;
    }

    void changeAfter(Date day, double diffrence, int ID){
        for (Object a : operation_history){
            Operation x = (Operation) a;
            if (x.getDay().compareTo(day) > 0 || ( x.getDay().compareTo(day)==0 && (x.getID() > ID) ))
                ((Operation) a).changeBalance(diffrence);
        }
    }
    /// Operacja 1 jest przed operacja 2
    boolean IFbefore(Date day1, int ID1, Date day2, int ID2){
        if (day1.compareTo(day2) < 0) return true;
        if (day1.compareTo(day2) > 0) return false;
        if (ID1 < ID2) return true;
        return false;
    }

    int previousOperation(Date day,int ID){
        int len = operation_history.size();
        if (len == 0)
            return ID;
        int i = 0;
        Date prevDate = ((Operation) operation_history.get(0)).getDay();
        int prevID = ((Operation) operation_history.get(0)).getID();
        for (; i< len; i++){
            if (IFbefore(prevDate,prevID,day,ID)) break;
            prevDate = ((Operation) operation_history.get(i)).getDay();
            prevID = ((Operation) operation_history.get(i)).getID();
        }
        if (i == len){
            if (IFbefore(prevDate,prevID,day,ID)) return ((Operation) operation_history.get(i-1)).getID();
            return ID;
        }
        for (;i < len;i ++){
            Operation op = (Operation) operation_history.get(i);
            if (IFbefore(op.getDay(),op.getID(),day,ID) && (IFbefore(prevDate,prevID,op.getDay(),op.getID()))){
                prevID = op.getID();
                prevDate = op.getDay();
            }
            else if (prevDate.compareTo(op.getDay()) == 0 && op.getID() < prevID)
                prevID = op.getID();
        }
        return prevID;
    }

    public void changeAt(int ID, int prior, Date day, String tag, double value, String desc) throws Exception {

        int index = findOperation(ID);
        double balance = getPreviousBalance(day,ID);
        Operation operation = (Operation) operation_history.get(index);
        {   /// erasing previous impact on other operations balances
            double diffrence = (operation.getPriority() == -1 ? -1 : 1) * operation.getValue();
            changeAfter(operation.getDay(), diffrence, ID);
            changeBalance(diffrence);
        }
        if (prior == -1){
            operation_history.set(index,
                    new Income(value,balance + value,ID,day,tag,desc));
            changeBalance(value);
            changeAfter(day,value,ID);
        }
        else{
            operation_history.set(index,
                    new Expanse(prior,value,balance - value,ID,day,tag,desc));
            changeBalance(-value);
            changeAfter(day,-value,ID);
        }
    }

    String save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        int len = operation_history.size();
        for (int i = 0;;){
            writer.write(((Operation) operation_history.get(i)).toSave());
            if (++i < len) writer.write("\n");
            else break;
        }
        writer.close();
        /// TODO when adding CyclicOperations
        return "0;" + ID + ";" + name +";"+ path + ";;" + used_currency.getName() + ";" + balance +";"+ monthly_limit +
        ";" + minimum_balance;
    }
}
