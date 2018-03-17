/*
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant.GUI;

/**
 * @author Luan Nguyen Thanh - S3312335
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.UIManager.*;
import restaurant.GUI.component.*;
import restaurant.controller.RestaurantController;

public class GeneralView extends RestaurantView {

    /** General Components that can be reused */
    private Resizer resizer;
    private JLabel label;
    private JMenuItem menuItem;
    private JPanel panel;
    private JPanel insidePanel;
    private JButton button;
    /** Core Components for this createView */
    private GeneralToolbar toolBar;
    private JMenuBar menuBar;
    private JMenu menu;
    private JSplitPane splitPane;
    private MenuPane menuPane;
    private TablePane tablePane;
    private JScrollPane menuScrollPane;
    private JScrollPane tableScrollPane;
    private JTextField searchTextField;

    public GeneralView(RestaurantController controller, String username,
            String userFullname, String type) {
        super(controller, username, userFullname, type);

        // Frame initializing        
        setLayout(new BorderLayout());

        // setView();

        // Finishing creating the GUI
        int indent = 50;

        //Get the screen size of the user
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(indent, indent,
                screenSize.width - indent * 2, screenSize.height - indent * 2);

        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Creating the GUI for the Manager
     */
    private void setView() {
        setSplitPanes();

        toolBar = new GeneralToolbar(controller);
        this.add(toolBar, BorderLayout.NORTH);
    }

    private void setSplitPanes() {
        // Set the left and right JPanel
        menuPane = new MenuPane(controller,
                controller.getEZmodel().getCategoryList(), type);
        tablePane = new TablePane(controller);
        tablePane.setTableList(controller.getEZmodel().getTableList());

        // Set menuPane size
        Dimension minSize = new Dimension(400, HEIGHT);
        Dimension preferSize = new Dimension(400, HEIGHT);
        menuPane.setMinimumSize(minSize);
        menuPane.setPreferredSize(preferSize);

        // Set tablePane size
        minSize = new Dimension(500, HEIGHT);
        preferSize = new Dimension(600, 500);
        tablePane.setMinimumSize(minSize);
        tablePane.setPreferredSize(preferSize);

        // Set the SplitPane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                true, menuPane, tablePane);

        this.add(splitPane);
    }

    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        setView();
    }

    @Override
    public void refresh() {
        super.refresh();
        getContentPane().removeAll();
        setView();
        revalidate();
    }
}
