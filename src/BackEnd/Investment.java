package BackEnd;

import BackEnd.CurrencyDir.Currency;
import BackEnd.OperationDir.Expanse;
import BackEnd.OperationDir.Income;
import BackEnd.OperationDir.Operation;
import BackEnd.OperationDir.PlannedIncome;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Investment {
    long ID;
    String name;
    List<Operation> operation_history;
    List <PlannedIncome> cyclic_income;
    Currency used_currency;
    double balance;
    public Investment(String line) throws Exception {
        String[] parts = line.split(";");
        if (parts.length != 7) throw new Exception("File is corrupted");
        ID = Integer.parseInt(parts[1]);
        name = parts[2];
        try{
            BufferedReader reader =  new BufferedReader(new FileReader(parts[3]));
            String OP = reader.readLine();
            boolean corrupted_file = true;
            while (line != null){
                for (int i=0 ; line.charAt(i) != 0 && line.charAt(i) != ';' ; i++){
                    if (line.charAt(i) == '-'){
                        corrupted_file = false;
                        operation_history.add(new Income(OP));
                        break;
                    }
                    else if (line.charAt(i)=='1'){
                        corrupted_file = false;
                        operation_history.add(new Expanse(OP));
                        break;
                    }
                }
                if (!corrupted_file) throw new Exception("Format pliku został zaburzony.");

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        /// TODO cyclic income
        /// TODO used_currency
        balance = Double.parseDouble(parts[6]);
    }
}
