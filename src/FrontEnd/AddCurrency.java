package FrontEnd;

import BackEnd.CurrencyDir.Currency;
import BackEnd.HomeBalance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.Date;

public class AddCurrency extends JFrame {
    AddCurrency(CurrencyWindow previousWin, HomeBalance homeBalance){
        JPanel buttons = new JPanel();
        JPanel input = new JPanel();
        setTitle("Dodanie Waluty");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300,100));
        setMinimumSize(new Dimension(300,100));

        JButton availableButton = new JButton("Lista walut");
        JButton backButton = new JButton("Cofnij");
        JButton OKButton = new JButton("Akceptuj");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JLabel nameLabel = new JLabel("Kod waluty ze strony NBP");
        JTextField nameText = new JTextField("EUR");
        nameText.setColumns(5);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWin.update(0);
                dispose();
            }
        });

        availableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Desktop.getDesktop().browse(URI.create("https://www.nbp.pl/home.aspx?f=/kursy/kursya.html"));
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Currency currency = new Currency(homeBalance.getNextCurrencyID(),nameText.getText(),0,new Date());
                    currency.Update();
                    homeBalance.addCurr(currency);
                    previousWin.update(1);
                    dispose();
                } catch (ParseException | IOException parseException) {
                    parseException.printStackTrace();
                    new CommunicationWindow("Nie udało się utworzyć nowej waltuy");
                }
            }
        });

        input.add(nameLabel);
        input.add(nameText);
        buttons.add(backButton);
        buttons.add(OKButton);
        buttons.add(availableButton);
        add(input,BorderLayout.PAGE_START);
        add(buttons, BorderLayout.CENTER);
        setVisible(true);
    }


}
