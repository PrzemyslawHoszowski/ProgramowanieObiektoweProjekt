package FrontEnd;

import BackEnd.HomeBalance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JFrame{

    public JPanel emptySpace(){
        JPanel emptySpace = new JPanel();
        emptySpace.setMinimumSize(new Dimension(10,10));
        emptySpace.setMaximumSize(new Dimension(10,10));
        return emptySpace;
    }

    public MainWindow(HomeBalance homeBalance){
        MainWindow thisobj = this;
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        setSize(300,410);
        setLocation((d.width-300)/2,(d.height-410)/2);
        setTitle("Menu");
        setLayout(new BorderLayout());


        add(emptySpace(),BorderLayout.LINE_START);
        add(emptySpace(),BorderLayout.LINE_END);
        add(emptySpace(),BorderLayout.PAGE_START);
        add(emptySpace(),BorderLayout.PAGE_END);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(6,1,10,10));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JButton OpHis = new JButton("Historia operacji");
        //OpHis.setBounds(10,10,280,50);
        center.add(OpHis);

        JButton ShBal = new JButton("Pokaż bilans");
        //ShBal.setBounds(10,70,280,50);
        center.add(ShBal);

        JButton UsCurr = new JButton("Używane waluty");
        UsCurr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new CurrencyWindow(thisobj,homeBalance);
            }
        });
        center.add(UsCurr);

        JButton Save = new JButton("Zapisz zmiany");
        //Save.setBounds(10,190,280,50);
        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    homeBalance.save();
                }
                catch(IOException exception){
                    new CommunicationWindow("Nie udało się otworzyć pliku");
                }
                new CommunicationWindow("Pomyślnie zapisano");
            }
        });
        center.add(Save);

        JButton ShAcc = new JButton("Konta bankowe");
        ShAcc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisobj.setVisible(false);
                AccountsWin window = new AccountsWin(thisobj, homeBalance);
            }
        });
        //ShAcc.setBounds(10,250,280,50);
        center.add(ShAcc);

        JButton ShInv = new JButton("Lokaty bankowe");
        //ShInv.setBounds(10,310,280,50);
        center.add(ShInv);
        add(center, BorderLayout.CENTER);
        setVisible(true);
    }

}
