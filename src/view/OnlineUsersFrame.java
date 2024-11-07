
package view;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

// Main Frame to display online users
public class OnlineUsersFrame extends JFrame {
    private JTable usersTable;
    private DefaultTableModel tableModel;

    public OnlineUsersFrame() {
        setTitle("Danh sách người dùng online");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(true); // Allow resizing

        getContentPane().setBackground(new Color(240, 240, 240)); // Background color

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 255, 255)); // Panel background

        // Create table model and columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };
        tableModel.addColumn("Avatar");
        tableModel.addColumn("Nickname");
        tableModel.addColumn("User Object"); // Hidden column to store User object

        // Create JTable and set custom properties
        usersTable = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? ImageIcon.class : String.class;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setBackground(row % 2 == 0 ? new Color(230, 230, 255) : Color.WHITE);
                return c;
            }
        };

        // Hide 'User Object' column (used for internal logic)
        usersTable.getColumnModel().getColumn(2).setMinWidth(0);
        usersTable.getColumnModel().getColumn(2).setMaxWidth(0);

        // Add mouse listener to detect row clicks
        usersTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = usersTable.getSelectedRow();
                if (row != -1) {
                    User user = (User) tableModel.getValueAt(row, 2); // Get User object
//                    showUserDetails(user); // Show user details in new window  
                    UserDetailFrm userDetailFrm = new UserDetailFrm(user);
                    userDetailFrm.setVisible(true);
                    
                    // Close the current frame (OnlineUsersFrame)
                     SwingUtilities.getWindowAncestor(usersTable).dispose();
                }
            }
        });

        usersTable.setRowHeight(90); // Adjust row height
        usersTable.getColumnModel().getColumn(0).setPreferredWidth(90); // Set avatar column width
        usersTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Set nickname column width

        // Customize table header
        JTableHeader tableHeader = usersTable.getTableHeader();
        tableHeader.setBackground(new Color(70, 130, 180)); // Header background color
        tableHeader.setForeground(Color.WHITE); // Header text color
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14)); // Header font

        JScrollPane scrollPane = new JScrollPane(usersTable); // Add table to scroll pane
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel); // Add panel to frame
    }

    // Update table with list of online users
    public void updateOnlineUsers(List<User> onlineUsers) {
        tableModel.setRowCount(0); // Clear previous data
        for (User user : onlineUsers) {
            ImageIcon avatar = new ImageIcon("assets/avatar/" + user.getAvatar() + ".jpg");
            avatar = resizeImage(avatar, 90, 90); // Resize avatar image
            tableModel.addRow(new Object[]{avatar, user.getNickname(), user}); // Add row to table
        }
    }

    // Resize image for better display
    private ImageIcon resizeImage(ImageIcon originalImage, int width, int height) {
        Image img = originalImage.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    // Display user details in a new window
    private void showUserDetails(User user) {
        JFrame detailsFrame = new JFrame("Thông tin người dùng: " + user.getNickname());
        detailsFrame.setSize(300, 200);
        detailsFrame.setLocationRelativeTo(this); // Center relative to main window
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout for user details
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JLabel("Nickname: " + user.getNickname()));
        panel.add(new JLabel("Avatar:"));
        panel.add(new JLabel("username: " + user.getUsername()));
        panel.add(new JLabel("numberOfGame: " + user.getNumberOfGame()));
        panel.add(new JLabel("numberOfWin: " + user.getNumberOfWin()));
        panel.add(new JLabel("numberOfDraw: " + user.getNumberOfDraw()));
        panel.add(new JLabel("rank: " + user.getRank()));

        // Display user's avatar
        ImageIcon avatar = new ImageIcon("assets/avatar/" + user.getAvatar() + ".jpg");
        Image resizedAvatar = avatar.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        panel.add(new JLabel(new ImageIcon(resizedAvatar)));

        detailsFrame.add(panel); // Add panel to frame
        detailsFrame.setVisible(true); // Show frame
    }

    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OnlineUsersFrame frame = new OnlineUsersFrame();
            frame.setVisible(true);

            // Example users for testing
            List<User> users = List.of(
                new User("Alice", "alice_avatar"),
                new User("Bob", "bob_avatar")
            );
            frame.updateOnlineUsers(users); // Load example users
        });
    }
}
