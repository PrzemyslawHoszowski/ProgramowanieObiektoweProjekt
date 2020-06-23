package FrontEnd;

import BackEnd.Account;
import BackEnd.HomeBalance;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class AccountsWin extends JFrame implements Observer {

    JTable table;
    JScrollPane scrollPane;
    DefaultTableModel model;
    HomeBalance homeBalance;
    MainWindow previousWin;
    JButton BackButton;
    JButton AddNew;
    JButton SeePlanned;
    JButton SeeLimits;
    int action;

    public AccountsWin(MainWindow previousWin, HomeBalance homeBalance){
        AccountsWin thisObj = this;
        this.homeBalance = homeBalance;
        this.previousWin = previousWin;
        setSize(1000,520);
        setLocationRelativeTo(previousWin);
        setTitle("Konta Bankowe");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        String[] columnName = {"ID","Nazwa", "Waluta", "Bilans"};
        String [][]data = homeBalance.getData();
        scrollPane = new JScrollPane();
        scrollPane.setBounds(10,10,980,430);
        model = new DefaultTableModel(data,columnName);
        table = new JTable(model){
            public boolean editCellAt(int row,int column,java.util.EventObject e) {return false;}
        };
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        int[] width = {50,200, 50, 200};
        for (int i = 0; i<4 ;i++){
            table.getColumnModel().getColumn(i).setWidth(width[i]);
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeyList = new ArrayList<>();
        sorter.setSortKeys(sortKeyList);
        scrollPane.setViewportView(table);
        add(scrollPane);
    ///Dodawanie konta
        final AddAccountWin[] addAccountWin = new AddAccountWin[1];

        BackButton = new JButton("Cofnij");
        BackButton.setBounds(10,450,130,20);
        add(BackButton);
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWin.setLocationRelativeTo(thisObj);
                previousWin.setVisible(true);
                dispose();
                if (addAccountWin[0]!= null){

                    addAccountWin[0].dispose();
                }
            }
        });

        AddNew = new JButton("Dodaj nowe konto");
        AddNew.setBounds(150, 450,200,20);
        add(AddNew);
        AddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///TODO wyświetlic nowe konto
                action = 2;
                if (addAccountWin[0] == null)
                    addAccountWin[0] = new AddAccountWin(homeBalance,thisObj,addAccountWin, previousWin);
            }
        });

        JButton SeeHistory = new JButton("Pokaż historie");
        SeeHistory.setBounds(360, 450, 200 , 20);
        add(SeeHistory);
        SeeHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() == -1) {
                    new CommunicationWindow("Proszę wybrać konto");
                    return;
                }
                action = 1;
                Account account;
                int id;
                try{
                    account = homeBalance.getAcc
                            (Integer.parseInt((String) table.getValueAt(table.getSelectedRow(),0)));
                } catch (Exception exception){
                    new CommunicationWindow(exception.getMessage());
                    return;
                }
                setVisible(false);
                new AccOperationHistory(account, thisObj);
            }
        });

        SeePlanned = new JButton("Transakcje okresowe");
        SeePlanned.setBounds(570,450,200,20);
        add(SeePlanned);
        SeePlanned.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///TODO
            }
        });

        SeeLimits = new JButton("Pokaż limity");
        SeeLimits.setBounds(780,450,200,20);
        add(SeeLimits);
        SeeLimits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///TODO
            }
        });
    }

    void refresh() {
        int Row = table.getRowSorter().convertRowIndexToModel(table.getSelectedRow());
        int accountID = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(),0));
        try {
            model.setValueAt(String.format("%.2f",homeBalance.getAcc(accountID).getBalance()), Row,3);
        } catch (Exception exception) {
            new CommunicationWindow("Stracono synchronizację pomiędzy GUI, a danymi");
            exception.printStackTrace();
        }
    }

    @Override
    public void update(){
        switch(action){
            case 1:  /// Zmiana saldo konta
                refresh();
                break;
            case 2:  /// Dodanie nowego konta
                model.addRow(homeBalance.get_last_account().split(";"));
        }
    }
}
