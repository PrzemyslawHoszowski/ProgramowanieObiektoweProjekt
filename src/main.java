import BackEnd.HomeBalance;
import FrontEnd.MainWindow;

public class main {
    public static void main (String[] args){
        HomeBalance homeBalance = new HomeBalance("dane/HomeBalance.txt");
        MainWindow mainWindow = new MainWindow(homeBalance);
    }
}
