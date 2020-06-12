package FrontEnd;

import BackEnd.HomeBalance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AccountsWin extends JFrame  {
    public AccountsWin(MainWindow previousWin, HomeBalance homeBalance){
        AccountsWin thisObj = this;
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1000,600);
        setLocation((d.width-1000)/2,(d.height-600)/2);
        setTitle("Konta Bankowe");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    /// Lista kont
                


    ///Dodawanie konta
        final AddAccountWin[] addAccountWin = new AddAccountWin[1];

        JButton BackButton = new JButton("Cofnij");
        BackButton.setBounds(30,530,100,20);
        add(BackButton);
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWin.setVisible(true);
                setVisible(false);
            }
        });

        JButton AddNew = new JButton("Dodaj nowe konto");
        AddNew.setBounds(150, 530,200,20);
        add(AddNew);
        AddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //setVisible(false);
                if (addAccountWin[0] == null)
                    addAccountWin[0] = new AddAccountWin(homeBalance,thisObj,addAccountWin);
                addAccountWin[0].show();
            }
        });
    }


}
