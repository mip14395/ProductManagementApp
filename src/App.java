import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.text.MaskFormatter;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        JFrame jFrame = new JFrame("JFormattedTextField Example");
        jFrame.setLayout(new java.awt.FlowLayout());
        // A phone number formatter - (country code)-(area code)-(number)
        MaskFormatter mf = new MaskFormatter("####-##-##");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        mf.setPlaceholder(format.format(new Date()));
        JFormattedTextField jftf = new JFormattedTextField(mf);
        jftf.setColumns(12);
        jFrame.add(jftf);
        jFrame.setSize(375, 250);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
