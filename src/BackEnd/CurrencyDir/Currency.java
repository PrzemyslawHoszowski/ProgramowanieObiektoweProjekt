package BackEnd.CurrencyDir;

import java.util.List;

public class Currency {
    int ID;
    String name;
    private List<ExchangeRate> value_history;
    public Currency(int ID, String name){
        this.ID = ID;
        this.name = name;
    }
    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }
}
