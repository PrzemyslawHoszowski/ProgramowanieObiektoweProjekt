package BackEnd;

import BackEnd.CurrencyDir.Currency;
import BackEnd.OperationDir.Expanse;
import BackEnd.OperationDir.Income;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.List;

public class Account {
    long ID;
    String name;
    List<Object> operation_history;
    List<Object> cyclic_operations;
    Currency used_currency;
    double monthly_limit;
    double balance;
    double minimum_balance;

    Account(String line) throws Exception {
        operation_history = new ArrayList<>();
        cyclic_operations = new ArrayList<>();
        String[] parts = line.split(";");
        if (parts.length != 9) throw new Exception("File is corrupted");
        ID = Integer.parseInt(parts[1]);
        name = parts[2];
        BufferedReader reader = new BufferedReader(new FileReader(parts[3]));
        String OP = reader.readLine();
        boolean corrupted_file = true;
        while (line != null) {
            for (int i = 0; line.charAt(i) != 0 && line.charAt(i) != ';'; i++) {
                if (line.charAt(i) == '-') {
                    corrupted_file = false;
                    operation_history.add(new Income(OP));
                    break;
                } else if (line.charAt(i) >= '0' && line.charAt(i) <= '9') {
                    corrupted_file = false;
                    operation_history.add(new Expanse(OP));
                    break;
                }
            }
            if (corrupted_file) throw new Exception("Format pliku został zaburzony.");
            line = reader.readLine();
        }
        reader.close();
        /// TODO cyclic income
        /// TODO used_currency
        balance = Double.parseDouble(parts[6]);
    }
    Account(String name, String curr, double balance, double minimum_balance, double monthly_limit){
        this.name = name;
        /// TODO curr
        this.balance = balance;
        this.minimum_balance = minimum_balance;
        this.monthly_limit = monthly_limit;
    }
}
