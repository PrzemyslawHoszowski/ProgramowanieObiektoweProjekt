package FrontEnd;

import BackEnd.Account;
import BackEnd.OperationDir.Income;
import BackEnd.OperationDir.Operation;
import com.sun.source.doctree.BlockTagTree;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.*;

public class ModifyOperationWindow extends JFrame {

    AccOperationHistory previousWin;
    Account account;
    JTextField dateField;
    JTextField priorField;
    JTextField tagField;
    JTextField valueField;
    JTextField descField;
    Operation operation;
    ModifyOperationWindow (Account account, AccOperationHistory previousWin, int index){
        this.previousWin =  previousWin;
        this.account = account;
        /// data = {SPACE, Priority, Date, Tag, Value, Description }
        String[] data;
        if (index != -1){
            operation = account.getOperation(index);
            String[] operationData = operation.toString().split(";");
            data = new String[]{
                    "",
                    operationData[1], //Prior
                    operationData[2], //Date
                    operationData[3], //Tag
                    operationData[4], //Value
                    operationData[6]  //Description
            };
        }
        else{
            index = account.getNewOperationID();
            data = new String[]{
                    "",
                    "-1",
                    new SimpleDateFormat("dd.MM.yyyy").format(new Date()),
                    "Zakupy spożywcze",
                    "100",
                    "Opis"
            };
        }

        setLocationRelativeTo(previousWin);
        setResizable(false);
        setLayout(null);
        if (operation == null)
            setTitle("Dodanie operacji");
        else setTitle("Edycja operacji");

        setSize(500,305);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel priorLabel = new JLabel("Priorytet (-1, gdy dochód)");
        priorLabel.setBounds(10,10,200,30);
        priorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(priorLabel);
        priorField = new JTextField(data[1]);
        priorField.setBounds(220,10,270,30);
        add(priorField);

        JLabel dataLabel = new JLabel("Data (DD.MM.RRRR)");
        dataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dataLabel.setBounds(10,50,200,30);
        add(dataLabel);
        dateField = new JTextField(data[2]);
        dateField.setBounds(220,50,270,30);
        add(dateField);

        JLabel tagLabel = new JLabel("Tag");
        tagLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tagLabel.setBounds(10,90,200,30);
        add(tagLabel);
        tagField =  new JTextField(data[3]);
        tagField.setBounds(220,90,270,30);
        add(tagField);

        JLabel valueLabel = new JLabel("Wartość transakcji");
        valueLabel.setHorizontalAlignment(0);
        valueLabel.setBounds(10,130,200,30);
        add(valueLabel);
        valueField = new JTextField(data[4]);
        valueField.setBounds(220,130,270,30);
        add(valueField);

        JLabel descLabel = new JLabel("Opis");
        descLabel.setBounds(10,165,480,20);
        descLabel.setHorizontalAlignment(0);
        add(descLabel);

        descField = new JTextField(data[5]);
        descField.setBounds(10,190,480,30);
        add(descField);
        setVisible(true);

        JButton returnButton = new JButton("Cofnij");
        returnButton. setBounds(10 ,230,235,30);
        add(returnButton);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JButton createButton;
        if (operation == null)
            createButton = new JButton("Stwórz");
        else
            createButton = new JButton("Zatwierdź zmiany");
        createButton.setBounds(255,230,235,30);
        add(createButton);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int priority;
                Date day;
                double value;
                try{
                    priority = Integer.parseInt(priorField.getText());
                    if (priority>20 || priority < -1) throw new Exception();
                } catch (Exception ex){
                    new Blad("Priorytet musi być liczbą całkowitą od -1 do 20");
                    return;
                }
                try{
                    day = new SimpleDateFormat("dd.MM.yyyy").parse(dateField.getText());
                } catch (ParseException parseException) {
                    new Blad("Błedny format daty");
                    return;
                }
                if (tagField.getText().length() > 40){
                    new Blad("Limit znaków dla tagu wynosi 40");
                    return;
                }
                try{
                    value = Double.parseDouble(valueField.getText());
                } catch (NumberFormatException numberFormatException) {
                    new Blad("Zły format wartości transakcji");
                    return;
                }
                if (descField.getText().length() > 100){
                    new Blad("Limit znaków dla opisu wynosi 100");
                    return;
                }
                if (operation==null){
                    if (priority == -1)
                        account.addOperation( new Income(value,account.getBalance(),
                            account.getNewOperationID(), day, tagField.getText(), descField.getText()));
                    else {
                        if((operation.getPriority()!=-1 || priority!=-1)&&(priority==-1 || operation.getPriority()==-1))
                        {
                            new Blad("Nie można zmienić typu transakcji (przychód/wydatek)");
                        }
                            double diffrence = value - operation.getValue();
                            operation.setBalance(operation.getBalance() - diffrence);
                            account.changeBalance(diffrence);
                        operation.setValue(value);
                        ///TODO Przy kazdym dodaniu/zmianie transakcji trzeba zmienic wszystkie balansy po niej
                    }
                }
            }
        });
    }

}
