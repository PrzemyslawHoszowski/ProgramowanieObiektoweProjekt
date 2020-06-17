package FrontEnd;

import BackEnd.Account;
import BackEnd.OperationDir.Operation;

import javax.swing.*;
import java.awt.*;

public class AccOperationHistory extends JFrame {
    AccOperationHistory thisobj;
    JTable table;
    JScrollPane scrollPane;
    AccOperationHistory(Account account, AccountsWin previousWin){
        thisobj = this;
        setSize(1000,700);
        setLocationRelativeTo(previousWin);
        setTitle("Historia Operacji");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        String[] columnName = {"ID", "Dzień", "Tag", "Wartość", "Bilans", "Opis"};
        String[][] data = account.getData();
        scrollPane = new JScrollPane();
        scrollPane.setBounds(10,10,980,650);
        table = new JTable(new javax.swing.table.DefaultTableModel (data, columnName));
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(510);

        scrollPane.setViewportView(table);
        add(scrollPane);
    }
    
}
