package BackEnd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import BackEnd.CurrencyDir.Currency;
import java.util.List;

public class HomeBalance {
    List<Account> BankAccounts;
    List<Investment> Investments;
    List<Currency> Currencies;
    public HomeBalance(String path){
        try{
            BankAccounts = new ArrayList<>();
            Investments  = new ArrayList<>();
            BufferedReader reader =  new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            boolean corrupted_file = true;
            while (line != null){
                for (int i=0 ; line.charAt(i) != 0 && line.charAt(i) != ';' ; i++){
                    if (line.charAt(i) == '0'){
                        corrupted_file = false;
                        BankAccounts.add(new Account(line));
                        break;
                    }
                    else if (line.charAt(i)=='1'){
                        corrupted_file = false;
                        Investments.add(new Investment(line));
                        break;
                    }
                }
                if (corrupted_file) throw new Exception("Format pliku został zaburzony.");

                line = reader.readLine();
            }
            reader.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    public int addAccount(String line){
        int res=0;
        try{
            BankAccounts.add(new Account(line));
        }
        catch (Exception e){
            e.printStackTrace();
            res = -1;
        }
        return res;
    }
    public void addAccount(String name, String curr, double balance, double minimum_balance, double monthly_limit){
        BankAccounts.add(new Account(nextAccID() , name, curr, balance, minimum_balance, monthly_limit));
       //System.out.println(BankAccounts.get(BankAccounts.size()-1));
        for(Account a : BankAccounts){
            System.out.println(a);
        }
    }
    public void load_curr(){
        Currencies = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("dane/currency.txt"));
            String line = reader.readLine();
            while (line!=null){
                String splited[] = line.split(";");
                Currencies.add(new Currency(Integer.parseInt(splited[0]), splited[1]));
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String[] get_curr(){
        String[] list = new String[Currencies.size()];
        int i = 0;
        for (Currency curr : Currencies){
            list[i] = curr.getName();
            i++;
        }
        return list;
    }

    int nextAccID(){
        int i; /// Rozwiązanie mało wydajne, ale działamy na małych danych więc akceptowalne.
        for (i = 1; i<BankAccounts.size();)
            for (Account a : BankAccounts){
                if (a.getID()== i){
                    i++;
                    break;
                }
             }
        return i;
    }
}
