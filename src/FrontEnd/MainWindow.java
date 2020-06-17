package FrontEnd;

import BackEnd.HomeBalance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame{

    public MainWindow(HomeBalance homeBalance){
        MainWindow thisobj = this;
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        setSize(300,410);
        setLocation((d.width-300)/2,(d.height-410)/2);
        setTitle("Menu");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JButton OpHis = new JButton("Historia operacji");
        OpHis.setBounds(10,10,280,50);
        add(OpHis);

        JButton ShBal = new JButton("Pokaż bilans");
        ShBal.setBounds(10,70,280,50);
        add(ShBal);

        JButton UsCurr = new JButton("Używane waluty");
        UsCurr.setBounds(10,130,280,50);
        add(UsCurr);

        JButton ShLim = new JButton("Limity");
        ShLim.setBounds(10,190,280,50);
        add(ShLim);

        JButton ShAcc = new JButton("Konta bankowe");
        ShAcc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisobj.setVisible(false);
                AccountsWin window = new AccountsWin(thisobj, homeBalance);
            }
        });
        ShAcc.setBounds(10,250,280,50);
        add(ShAcc);

        JButton ShInv = new JButton("Lokaty bankowe");
        ShInv.setBounds(10,310,280,50);
        add(ShInv);

        setVisible(true);
    }

}
