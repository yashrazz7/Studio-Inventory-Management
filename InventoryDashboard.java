import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class InventoryDashboard extends JFrame {

    private JPanel leftGridPanel;
    private JTextField nameField;
    private JComboBox<String> productDropdown; 
    private JLabel totalSalesLabel;            
    private JLabel totalRevenueLabel;          
    
    private Color bgColor = new Color(18, 22, 29);       
    private Color cardColor = new Color(29, 36, 47);   
    private Color panelBg = new Color(24, 30, 40);
    private Color textMuted = new Color(130, 140, 155);
    private Color accentNeon = new Color(46, 213, 115);

    private int totalSalesCount = 0;
    private double totalRevenueAmount = 0.0;

    public InventoryDashboard() {
        setTitle("StreamPulse Studio Control");
        setSize(1250, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color inputBg = new Color(33, 42, 54);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(bgColor);
        mainPanel.setLayout(new BorderLayout(25, 25));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel topPanel = new JPanel(new BorderLayout(20, 10));
        topPanel.setBackground(bgColor);

        JLabel titleLabel = new JLabel("🎬 StreamPulse Studio Control");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        statsPanel.setBackground(bgColor);

        JPanel orderCard = new JPanel(new GridLayout(2, 1, 2, 2));
        orderCard.setBackground(new Color(43, 39, 59));
        orderCard.setPreferredSize(new Dimension(140, 55));
        orderCard.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JLabel orderTitle = new JLabel("Total Order", SwingConstants.CENTER);
        orderTitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        orderTitle.setForeground(new Color(180, 170, 200));
        totalSalesLabel = new JLabel("Sales: 0", SwingConstants.CENTER);
        totalSalesLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        totalSalesLabel.setForeground(new Color(165, 125, 245));
        orderCard.add(orderTitle);
        orderCard.add(totalSalesLabel);

        JPanel earnCard = new JPanel(new GridLayout(2, 1, 2, 2));
        earnCard.setBackground(new Color(60, 48, 40));
        earnCard.setPreferredSize(new Dimension(220, 55)); 
        earnCard.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel earnTitle = new JLabel("Total Earnings", SwingConstants.CENTER);
        earnTitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        earnTitle.setForeground(new Color(210, 180, 150));
        totalRevenueLabel = new JLabel("Revenue: $0.00", SwingConstants.CENTER);
        totalRevenueLabel.setFont(new Font("SansSerif", Font.BOLD, 15)); 
        totalRevenueLabel.setForeground(new Color(245, 165, 65));
        earnCard.add(earnTitle);
        earnCard.add(totalRevenueLabel);

        statsPanel.add(orderCard);
        statsPanel.add(earnCard);
        topPanel.add(statsPanel, BorderLayout.EAST);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);

        leftGridPanel = new JPanel();
        leftGridPanel.setBackground(bgColor);
        leftGridPanel.setLayout(new GridLayout(2, 2, 25, 25)); 

        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setBackground(panelBg);
        rightSidePanel.setPreferredSize(new Dimension(360, 0));
        rightSidePanel.setLayout(new BorderLayout(20, 20));
        rightSidePanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel billTitle = new JLabel("⚡ Quick Bill Generator");
        billTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        billTitle.setForeground(Color.WHITE);
        rightSidePanel.add(billTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setBackground(panelBg);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Customer Name:");
        nameLabel.setForeground(textMuted);
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        nameField.setBackground(inputBg);
        nameField.setForeground(Color.WHITE);
        nameField.setCaretColor(Color.WHITE);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 150, 136)), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel idLabel = new JLabel("Select Product:");
        idLabel.setForeground(textMuted);
        idLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        productDropdown = new JComboBox<>();
        productDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        productDropdown.setBackground(inputBg);
        productDropdown.setForeground(Color.WHITE);
        productDropdown.setFont(new Font("SansSerif", Font.PLAIN, 14));
        productDropdown.setBorder(BorderFactory.createLineBorder(new Color(55, 68, 85)));
        productDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(nameLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(nameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(idLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(productDropdown);

        rightSidePanel.add(formPanel, BorderLayout.CENTER);

        JButton actionBtn = new JButton("Print Invoice");
        actionBtn.setBackground(new Color(230, 126, 34));
        actionBtn.setForeground(Color.WHITE);
        actionBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        actionBtn.setFocusPainted(false);
        actionBtn.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 0));
        
        actionBtn.addActionListener(e -> generateInvoice());
        
        rightSidePanel.add(actionBtn, BorderLayout.SOUTH);

        mainPanel.add(leftGridPanel, BorderLayout.CENTER);
        mainPanel.add(rightSidePanel, BorderLayout.EAST);

        add(mainPanel);

        loadInventoryFromDB();
    }

    private void loadInventoryFromDB() {
        String url = "jdbc:mysql://localhost:3306/studio_db";
        String user = "root";
        String password = ""; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT item_name, stock FROM inventory")) {

                leftGridPanel.removeAll();
                String selectedItem = (String) productDropdown.getSelectedItem();
                productDropdown.removeAllItems();

                while (rs.next()) {
                    String name = rs.getString("item_name");
                    int stock = rs.getInt("stock");

                    productDropdown.addItem(name);

                    JPanel card = new JPanel();
                    card.setBackground(cardColor);
                    card.setLayout(new BorderLayout());
                    card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(45, 55, 72), 1), BorderFactory.createEmptyBorder(25, 25, 25, 25)));
                    
                    JLabel itemTitle = new JLabel(name);
                    itemTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
                    itemTitle.setForeground(Color.WHITE); 
                    
                    JLabel stockStatus = new JLabel("In Stock: " + stock + " units");
                    stockStatus.setFont(new Font("SansSerif", Font.PLAIN, 14));
                    stockStatus.setForeground(accentNeon);

                    card.add(itemTitle, BorderLayout.NORTH);
                    card.add(stockStatus, BorderLayout.CENTER);
                    leftGridPanel.add(card);
                }
                
                if (selectedItem != null) {
                    productDropdown.setSelectedItem(selectedItem);
                }
                
                leftGridPanel.revalidate();
                leftGridPanel.repaint();
            }
        } catch (Exception e) {
            leftGridPanel.removeAll();
            JLabel errorLabel = new JLabel("Database Connection Error!");
            errorLabel.setForeground(Color.RED);
            leftGridPanel.add(errorLabel);
        }
    }

    private void generateInvoice() {
        String customerName = nameField.getText().trim();
        String productName = (String) productDropdown.getSelectedItem();

        if (customerName.isEmpty() || productName == null) {
            JOptionPane.showMessageDialog(this, "Please fill in Customer Name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String url = "jdbc:mysql://localhost:3306/studio_db";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("SELECT price, stock FROM inventory WHERE item_name = ?")) {
            
            stmt.setString(1, productName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double price = rs.getDouble("price");
                    int stock = rs.getInt("stock");

                    if (stock <= 0) {
                        JOptionPane.showMessageDialog(this, "Product out of stock!", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE inventory SET stock = stock - 1 WHERE item_name = ?")) {
                        updateStmt.setString(1, productName);
                        updateStmt.executeUpdate();
                    }

                    totalSalesCount++;
                    totalRevenueAmount += price;
                    totalSalesLabel.setText("Sales: " + totalSalesCount);
                    totalRevenueLabel.setText(String.format("Revenue: $%.2f", totalRevenueAmount));

                    String receipt = "--------------------------------------\n" +
                                     "       STREAMPULSE STUDIO RECEIPT     \n" +
                                     "--------------------------------------\n" +
                                     "Customer: " + customerName + "\n" +
                                     "Product:  " + productName + "\n" +
                                     "Price:    $" + price + "\n" +
                                     "--------------------------------------\n" +
                                     "System Status: Dynamic Sync OK\n" +
                                     "Thank you for your business!";
                    
                    JTextArea receiptArea = new JTextArea(receipt);
                    receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
                    receiptArea.setEditable(false);
                    JOptionPane.showMessageDialog(this, new JScrollPane(receiptArea), "Pro Invoice System", JOptionPane.INFORMATION_MESSAGE);
                    
                    loadInventoryFromDB();
                    nameField.setText("");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error during billing!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InventoryDashboard().setVisible(true);
        });
    }
}