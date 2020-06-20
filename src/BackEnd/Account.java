package BackEnd;

import BackEnd.CurrencyDir.Currency;
import BackEnd.OperationDir.Expanse;
import BackEnd.OperationDir.Income;
import BackEnd.OperationDir.Operation;
import FrontEnd.Blad;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;
import java.util.List;

import static java.lang.System.exit;

public class Account {
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

    Account(String line,  HomeBalance homeBalance) throws Exception {
        operation_history = new ArrayList<>();
        cyclic_operations = new ArrayList<>();
        String[] parts = line.split(";");
        if (parts.length != 9) throw new Exception("File is corrupted");
        ID = Integer.parseInt(parts[1]);
        name = parts[2];
        BufferedReader reader = new BufferedReader(new FileReader(parts[3]));
        System.out.println(parts[3]);
        String OP = reader.readLine();

        boolean corrupted_file = true;
        int mistake_counter = 0;
        while (OP != null) {
            System.out.println(OP);
            System.out.println(OP.split(";").length);
            for (int i = 0; OP.charAt(i) != 0 && OP.charAt(i) != ';'; i++) {
                if (OP.charAt(i) == '-') {
                    corrupted_file = false;
                    try {
                        operation_history.add(new Income(OP));
                    }
                    catch (ParseException e){
                        new Blad("Nie udało się przetłumaczyć daty operacji");
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

    Account(int ID,String name, String curr, double balance, double minimum_balance, double monthly_limit, HomeBalance homeBalance){
        this.ID = ID;
        this.name = name;
        this.used_currency = homeBalance.get_curr_ref(curr);
        this.balance = balance;
        this.minimum_balance = minimum_balance;
        this.monthly_limit = monthly_limit;
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
            System.out.println(op);
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

    public int getNewOperationID(){
        return ((Operation) operation_history.get(operation_history.size()-1)).getID() + 1;
    }

    public void deleteOperation(int ID){
        int i = 0;
        for (Object a : operation_history){
            if (((Operation) a).getID() == ID)
            {
                operation_history.remove(i);
                return;
            }
            i++;
        }
    }

    public void addOperation(Operation operation){
        operation_history.add(operation);
    }

    public double getBalance(){
        return balance;
    }

    public void changeBalance(double change){
        balance += change;
    }

    public void changeAfter(Date day, double diffrence, int ID){
        for (Object a : operation_history){
            Operation x = (Operation) a;
            if (x.getDay().compareTo(day) > 0 || ( x.getDay().compareTo(day)==0 && (x.getID() > ID) )){
                ((Operation) a).changeBalance(diffrence);
            }
        }
    }
}
