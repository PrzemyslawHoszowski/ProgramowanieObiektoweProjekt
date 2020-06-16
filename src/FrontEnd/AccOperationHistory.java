package FrontEnd;

import BackEnd.Account;

import javax.swing.*;
import java.awt.*;

public class AccOperationHistory extends JFrame {
    AccOperationHistory(Account account){
        AccOperationHistory thisobj = this;
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1000,700);
        setLocation((d.width-1000)/2,(d.height-700)/2);
        setTitle("Historia Operacji");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        String[] columnName = {"ID", "Dzień", "Tag", "Wartość", "Bilans"};
        String[][] data = account.getData();

        JScrollPane accountList = new JScrollPane(new JList());
        accountList.setBounds(10,10,980,430);
        add(accountList);
    }
}
