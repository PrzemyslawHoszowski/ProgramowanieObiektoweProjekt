package FrontEnd;

import BackEnd.Account;
import BackEnd.HomeBalance;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AccountsWin extends JFrame  {
    JList list;
    JScrollPane accountList;
    HomeBalance homeBalance;
    MainWindow previousWin;
    public AccountsWin(MainWindow previousWin, HomeBalance homeBalance){
        AccountsWin thisObj = this;
        this.homeBalance = homeBalance;
        this.previousWin = previousWin;
        //Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1000,520);
        //setLocation((d.width-1000)/2,(d.height-520)/2);
        setLocationRelativeTo(previousWin);
        setTitle("Konta Bankowe");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    /// Lista kont
        list = new JList(homeBalance.get_all_accounts());
        accountList = new JScrollPane(list);
        accountList.setBounds(10,10,980,430);
        add(accountList);


    ///Dodawanie konta
        final AddAccountWin[] addAccountWin = new AddAccountWin[1];

        JButton BackButton = new JButton("Cofnij");
        BackButton.setBounds(10,450,130,20);
        add(BackButton);
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWin.setVisible(true);
                dispose();
                if (addAccountWin[0]!= null){
                    addAccountWin[0].dispose();
                }
            }
        });

        JButton AddNew = new JButton("Dodaj nowe konto");
        AddNew.setBounds(150, 450,200,20);
        add(AddNew);
        AddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (addAccountWin[0] == null)
                    addAccountWin[0] = new AddAccountWin(homeBalance,thisObj,addAccountWin, previousWin);
                addAccountWin[0].show(); ///

            }
        });

        JButton SeeHistory = new JButton("Pokaż historie");
        SeeHistory.setBounds(360, 450, 200 , 20);
        add(SeeHistory);
        SeeHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.getSelectedValue() == null) {
                    new Blad("Proszę wybrać konto");
                    return;
                }
                Account account;
                int id;
                try{
                    account = homeBalance.getAcc (getID ((String) list.getSelectedValue()));
                } catch (Exception exception){
                    new Blad(exception.getMessage());
                    return;
                }
                setVisible(false);
                new AccOperationHistory(account, thisObj);
            }
        });

        JButton SeePlanned = new JButton("Transakcje okresowe");
        SeePlanned.setBounds(570,450,200,20);
        add(SeePlanned);
        SeePlanned.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///TODO
            }
        });

        JButton SeeLimits = new JButton("Pokaż limity");
        SeeLimits.setBounds(780,450,200,20);
        add(SeeLimits);
        SeeLimits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///TODO
            }
        });
    }
    private int getID(String line){
        char z;
        int id=0;
        for (int i = 3; i < line.length() && (z = line.charAt(i)) >= '0' && z <='9'; i++ ){
            id*=10;
            id+= (int) z - 48;
        }
        return id;
    }
    void refresh(){
      // list.add(homeBalance.get_last_account());
    }
}
