import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class Product {
    private String productName;
    private int quantity;
    private double pricePerUnit;

    // Constructor to initialize product details
    public Product(String productName, int quantity, double pricePerUnit) {
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    // Method to calculate total cost of this product
    public double calculateTotalCost() {
        return quantity * pricePerUnit;
    }

    // Getters
    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }
}

class Order {
    private String orderID;
    private String customerName;
    private List<Product> products;

    // Constructor to initialize the order
    public Order(String orderID, String customerName) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.products = new ArrayList<>();
    }

    // Method to add products to the order
    public void addProduct(Product product) {
        this.products.add(product);
    }

    // Method to calculate total cost of the order
    public double calculateTotalCost() {
        double total = 0;
        for (Product product : products) {
            total += product.calculateTotalCost();
        }
        return total;
    }

    // Method to calculate cumulative discount for the entire order
    public double calculateDiscount() {
        double totalCost = calculateTotalCost();
        if (totalCost < 1000) {
            return 0; // No discount
        } else if (totalCost >= 1000 && totalCost < 5000) {
            return 0.05 * totalCost; // 5% discount
        } else {
            return 0.10 * totalCost; // 10% discount
        }
    }

    // Method to display order details including total cost, discount, and final cost
    public String displayOrderDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Order ID: ").append(orderID).append("\n");
        details.append("Customer Name: ").append(customerName).append("\n");
        details.append("Products in this order:\n");

        double totalCost = calculateTotalCost();
        double discount = calculateDiscount();
        double finalCost = totalCost - discount;

        for (Product product : products) {
            details.append("Product: ").append(product.getProductName())
                    .append(", Quantity: ").append(product.getQuantity())
                    .append(", Price per Unit: ").append(product.getPricePerUnit())
                    .append(", Total Cost: ").append(product.calculateTotalCost()).append("\n");
        }

        details.append("Total Cost of Order: ").append(totalCost).append("\n");
        details.append("Discount: ").append(discount).append("\n");
        details.append("Final Cost after Discount: ").append(finalCost).append("\n");

        return details.toString();
    }
}

public class OrderGUI {
    private JFrame frame;
    private JTextField orderIDField, customerNameField, productNameField, quantityField, priceField;
    private JTextArea orderDetailsArea;
    private Order order;
    private JButton addButton, displayButton;

    public OrderGUI() {
        // Initialize the frame and set its properties
        frame = new JFrame("Order Management");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Initialize the order (no products added initially)
        order = new Order("", "");

        // Create and add components to the frame
        frame.add(new JLabel("Order ID:"));
        orderIDField = new JTextField(20);
        frame.add(orderIDField);

        frame.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField(20);
        frame.add(customerNameField);

        frame.add(new JLabel("Product Name:"));
        productNameField = new JTextField(20);
        frame.add(productNameField);

        frame.add(new JLabel("Quantity:"));
        quantityField = new JTextField(5);
        frame.add(quantityField);

        frame.add(new JLabel("Price per Unit:"));
        priceField = new JTextField(5);
        frame.add(priceField);

        addButton = new JButton("Add Product");
        frame.add(addButton);

        displayButton = new JButton("Display Order Details");
        frame.add(displayButton);

        orderDetailsArea = new JTextArea(10, 30);
        orderDetailsArea.setEditable(false);
        frame.add(new JScrollPane(orderDetailsArea));

        // ActionListener for the Add Product button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String productName = productNameField.getText();
                    int quantity = Integer.parseInt(quantityField.getText());
                    double pricePerUnit = Double.parseDouble(priceField.getText());

                    // Add product to the order
                    Product product = new Product(productName, quantity, pricePerUnit);
                    order.addProduct(product);

                    // Clear the product fields for the next entry
                    productNameField.setText("");
                    quantityField.setText("");
                    priceField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid quantity and price!");
                }
            }
        });

        // ActionListener for the Display Order Details button
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get Order details and display in the text area
                String orderDetails = order.displayOrderDetails();
                orderDetailsArea.setText(orderDetails);
            }
        });

        // Show the window
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OrderGUI();
            }
        });
    }
}