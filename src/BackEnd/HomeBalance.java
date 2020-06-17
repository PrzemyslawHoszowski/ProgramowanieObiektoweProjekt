package BackEnd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import BackEnd.CurrencyDir.Currency;
import FrontEnd.Blad;

import java.util.List;

public class HomeBalance {
    List<Account> BankAccounts;
    List<Investment> Investments;
    List<Currency> Currencies;
    public HomeBalance(String path){
        try{
            BankAccounts = new ArrayList<>();
            Investments  = new ArrayList<>();
            Currencies   = new ArrayList<>();
            load_curr();
            BufferedReader reader =  new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            boolean corrupted_file = true;
            while (line != null){
                for (int i=0 ; line.charAt(i) != 0 && line.charAt(i) != ';' ; i++){
                    if (line.charAt(i) == '0'){
                        corrupted_file = false;
                        BankAccounts.add(new Account(line, this));
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
            BankAccounts.add(new Account(line, this));
        }
        catch (Exception e){
            e.printStackTrace();
            res = -1;
        }
        return res;
    }

    public void addAccount(String name, String curr, double balance, double minimum_balance, double monthly_limit){
        BankAccounts.add(new Account(nextAccID() , name, curr, balance, minimum_balance, monthly_limit, this));
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

    public Currency get_curr_ref(String name){
        for (Currency a : Currencies){
            if (a.getName().equals(name))
                return a;
        }
        throw (new Error("Nie znaleziono waluty \""+ name + "\""));
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

    public String[] get_all_accounts(){
        String[] res = new String[BankAccounts.size()];
        int i = 0;
        for (Account a : BankAccounts){
            res[i] = a.toString();
            i++;
        }
        return res;
    }

    public Account getAcc(int id) throws Exception {
        for (Account a : BankAccounts){
            if (a.getID()==id) return a;
        }
        throw (new Exception("Nie znaleziono konta o ID = " + id));
    }

    public String get_last_account(){
        return BankAccounts.get(BankAccounts.size() - 1).toString();
    }
}
