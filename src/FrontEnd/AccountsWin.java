package FrontEnd;

import BackEnd.HomeBalance;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AccountsWin extends JFrame  {
    public AccountsWin(MainWindow previousWin, HomeBalance homeBalance){
        AccountsWin thisObj = this;
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1000,520);
        setLocation((d.width-1000)/2,(d.height-600)/2);
        setTitle("Konta Bankowe");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    /// Lista kont
        JScrollPane accountList = new JScrollPane(new JList(homeBalance.get_all_accounts()));
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
                setVisible(false);
            }
        });

        JButton AddNew = new JButton("Dodaj nowe konto");
        AddNew.setBounds(150, 450,200,20);
        add(AddNew);
        AddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (addAccountWin[0] == null)
                    addAccountWin[0] = new AddAccountWin(homeBalance,thisObj,addAccountWin);
                addAccountWin[0].show(); ///
            }
        });

        JButton SeeHistory = new JButton("Pokaż historie");
        SeeHistory.setBounds(360, 450, 200 , 20);
        add(SeeHistory);
        SeeHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
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


}
