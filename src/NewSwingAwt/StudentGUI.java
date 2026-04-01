package NewSwingAwt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentGUI extends JFrame implements ActionListener {

    JTextField nameField, m1Field, m2Field, m3Field, totalField, avgField, gradeField, resultField;
    JTextArea output;
    JButton insertBtn, viewBtn, calcBtn;

    Connection con;

    public StudentGUI() {

        setTitle("Student Database Form");
        setSize(650, 550);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBackground(new Color(45, 45, 45));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Consolas", Font.PLAIN, 14);

        
        formPanel.add(createLabel("Name:", labelFont));
        nameField = createField(fieldFont);
        formPanel.add(nameField);

        formPanel.add(createLabel("Mark1:", labelFont));
        m1Field = createField(fieldFont);
        formPanel.add(m1Field);

        formPanel.add(createLabel("Mark2:", labelFont));
        m2Field = createField(fieldFont);
        formPanel.add(m2Field);

        formPanel.add(createLabel("Mark3:", labelFont));
        m3Field = createField(fieldFont);
        formPanel.add(m3Field);

        formPanel.add(createLabel("Total:", labelFont));
        totalField = createField(fieldFont);
        totalField.setEditable(false);
        formPanel.add(totalField);

        formPanel.add(createLabel("Average:", labelFont));
        avgField = createField(fieldFont);
        avgField.setEditable(false);
        formPanel.add(avgField);

        formPanel.add(createLabel("Grade:", labelFont));
        gradeField = createField(fieldFont);
        gradeField.setEditable(false);
        formPanel.add(gradeField);

        formPanel.add(createLabel("Result:", labelFont));
        resultField = createField(fieldFont);
        resultField.setEditable(false);
        formPanel.add(resultField);

        add(formPanel, BorderLayout.NORTH);

        
        output = new JTextArea();
        output.setFont(new Font("Consolas", Font.PLAIN, 14));
        output.setBackground(Color.BLACK);
        output.setForeground(Color.GREEN);
        add(new JScrollPane(output), BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 30, 30));

        calcBtn = createButton("Calculate", Color.GREEN);
        insertBtn = createButton("Insert", Color.BLUE);
        viewBtn = createButton("View", Color.ORANGE);

        buttonPanel.add(calcBtn);
        buttonPanel.add(insertBtn);
        buttonPanel.add(viewBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        calcBtn.addActionListener(this);
        insertBtn.addActionListener(this);
        viewBtn.addActionListener(this);

        connectDB();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    
    private JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(font);
        return lbl;
    }

    private JTextField createField(Font font) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        return field;
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    
    public void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/DB_NAME",
                    "Username",
                    "Password"
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == calcBtn) calculate();
        if (e.getSource() == insertBtn) insertData();
        if (e.getSource() == viewBtn) viewData();
    }

    
    public void calculate() {
        try {
            int m1 = Integer.parseInt(m1Field.getText());
            int m2 = Integer.parseInt(m2Field.getText());
            int m3 = Integer.parseInt(m3Field.getText());

            int total = m1 + m2 + m3;
            double avg = total / 3.0;

            totalField.setText(String.valueOf(total));
            avgField.setText(String.valueOf(avg));

            // Result
            String result = (m1 >= 35 && m2 >= 35 && m3 >= 35) ? "Pass" : "Fail";
            resultField.setText(result);

            // Grade
            String grade;
            if (avg >= 90) grade = "A+";
            else if (avg >= 75) grade = "A";
            else if (avg >= 60) grade = "B";
            else if (avg >= 50) grade = "C";
            else grade = "Fail";

            gradeField.setText(grade);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter valid marks!");
        }
    }

    
    public void insertData() {
        try {
            String name = nameField.getText();
            int m1 = Integer.parseInt(m1Field.getText());
            int m2 = Integer.parseInt(m2Field.getText());
            int m3 = Integer.parseInt(m3Field.getText());
            int total = Integer.parseInt(totalField.getText());
            double avg = Double.parseDouble(avgField.getText());
            String grade = gradeField.getText();
            String result = resultField.getText();

            String query = "INSERT INTO student (name, m1, m2, m3, total, avg, grade, result) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setInt(2, m1);
            ps.setInt(3, m2);
            ps.setInt(4, m3);
            ps.setInt(5, total);
            ps.setDouble(6, avg);
            ps.setString(7, grade);
            ps.setString(8, result);

            int res = ps.executeUpdate();

            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Inserted Successfully!");

                
                nameField.setText("");
                m1Field.setText("");
                m2Field.setText("");
                m3Field.setText("");
                totalField.setText("");
                avgField.setText("");
                gradeField.setText("");
                resultField.setText("");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    
    public void viewData() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM student");

            output.setText("");

            int i = 1;
            while (rs.next()) {
                output.append("---- Student " + i + " ----\n");
                output.append("Name: " + rs.getString("name") + "\n");
                output.append("M1: " + rs.getInt("m1") + "\n");
                output.append("M2: " + rs.getInt("m2") + "\n");
                output.append("M3: " + rs.getInt("m3") + "\n");
                output.append("Total: " + rs.getInt("total") + "\n");
                output.append("Average: " + rs.getDouble("avg") + "\n");
                output.append("Grade: " + rs.getString("grade") + "\n");
                output.append("Result: " + rs.getString("result") + "\n\n");
                i++;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {}

        new StudentGUI();
    }
}
