package BackEnd;

import BackEnd.CurrencyDir.Currency;
import FrontEnd.CommunicationWindow;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeBalance {
    List<Account> BankAccounts;
    List<Investment> Investments;
    List<Currency> Currencies;
    String path;
    public HomeBalance(String path){
        try{
            BankAccounts = new ArrayList<>();
            Investments  = new ArrayList<>();
            Currencies   = new ArrayList<>();
            this.path = path;
            load_curr();
            File file = new File(path);
            if (file.exists() == false){
                file.createNewFile();
                return;
            }
            FileReader fileReader = new FileReader(file);

            BufferedReader reader =  new BufferedReader(fileReader);

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
    }

    public void load_curr(){
        Currencies = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("dane/currency.txt"));
            String line = reader.readLine();
            while (line!=null){
                String splited[] = line.split(";");
                Currencies.add(new Currency(Integer.parseInt(splited[0]),
                        splited[1],
                        Double.parseDouble(splited[2]),
                        new SimpleDateFormat("dd.MM.yyyy").parse(splited[3])));
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void addCurr(Currency currency){
        Currencies.add(currency);
    }

    public int getNextCurrencyID(){
        return Currencies.get(Currencies.size() - 1).getID() + 1;
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
        if (BankAccounts.isEmpty()) return 0;
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

    public String[][] getData(){
        String[][] res = new String[BankAccounts.size()][4];
        int i = 0;
        for (Account a : BankAccounts){
            res[i] = a.toString().split(";");
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

    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        int len = BankAccounts.size();
        if (len !=0)
        for (int i = 0;;){
            writer.write(BankAccounts.get(i).save());
            if (++i < len) writer.write("\n");
            else break;
        }
        writer.close();
        writer = new BufferedWriter(new FileWriter("dane/currency.txt"));
        len = Currencies.size() -1;
        for (int i = 0; i <= len; i++){
            writer.write(Currencies.get(i).toString());
            if (i < len) writer.write("\n");
        }
        writer.close();
    }

    public void updateCurrency() {
        for (Currency curr : Currencies) {
            try {
                curr.Update();
            } catch (Exception e) {
                new CommunicationWindow("Nie udało się zaktualizować" + curr.getName());
                e.printStackTrace();
            }
        }
    }

    public String[] getLastCurrencyData(){
        Currency last = Currencies.get(Currencies.size()-1);
        return new String[]{
                Integer.toString(last.getID()),
                last.getName(),
                String.format("%.4f",last.getExchangeRate()),
                new SimpleDateFormat("dd.MM.yyyy").format(last.getExchangeDate()),
                "0"
        };
    }

    public String[][] getCurrencyData(){
        String[][] data = new String [Currencies.size()][5];
        int i =0;
        for (Currency curr : Currencies){
            double sum = 0;
            for (Account acc : BankAccounts){
                if (acc.getCurrency() == curr)
                sum += acc.balance;
            }
            data[i][0] =  Integer.toString(curr.getID());
            data[i][1] =  curr.getName();
            data[i][2] =  String.format("%.4f",curr.getExchangeRate());
            data[i][3] =  new SimpleDateFormat("dd.MM.yyyy").format(curr.getExchangeDate());
            data[i][4] =  String.format("%.2f",sum);
            i++;
        }
        return data;
    }

    public boolean findCurrency(String currency_name){
        for (Currency currency : Currencies)
            if (currency_name.compareTo(currency.getName()) == 0) return true;
        return false;
    }

    public boolean findAccountWith(String currency_tag){
        for (Account account : BankAccounts)
            if (account.getCurrency().getName().compareTo(currency_tag)==0) return true;
        return false;
    }

    public void deleteCurrency(String currency_tag) throws Exception {
        int i = 0;
        if (currency_tag.compareTo("PLN") == 0) {
            throw new Exception("Nie mozna usunąc PLN");
        }
        for (Currency currency : Currencies){
            if (currency.getName().compareTo(currency_tag)==0) {
                Currencies.remove(i);
                return;
            }
            i++;
        }
    }
}
