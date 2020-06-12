import BackEnd.HomeBalance;
import FrontEnd.MainWindow;
import javax.swing.*;

public class main {
    public static void main (String[] args){
        HomeBalance homeBalance = new HomeBalance("dane/HomeBalance.txt");
        MainWindow mainWindow = new MainWindow(homeBalance);
    }
}
