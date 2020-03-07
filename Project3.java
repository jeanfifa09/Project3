/**
 * Name: Jordan Germinal
 * Course: CNT 4714 – Fall 2019
 * Assignment title: Program 3 – Two-Tier Client-Server Application Development With MySQL and JDBC
 * Date: Sunday October 27, 2019
 * 
 */
package project3;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.EventQueue;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;

class connectdatabase {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Project3 frame = new Project3();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
public class Project3 extends JFrame {

	private static final long serialVersionUID = -7986607807936678977L;
	private JPanel cp;private JTextField uname;private JPasswordField passf;private JTextArea areatext;private JLabel consetup;private JComboBox<String> driverbox;
	private JComboBox<String> urlb;private JTable resultsquery;private boolean isConnected = false;private JScrollPane scrollPaneResults;
	
	
        // Function to be used as Table  model depending on selecting query
		public TableModel resultSetToTableModel(ResultSet resultsq) {
	        try {
                    // Getin stuuff from the mysql database 
	            java.sql.ResultSetMetaData metaData = resultsq.getMetaData();int numcol = metaData.getColumnCount(); Vector<String> columnNames = new Vector<String>();
	            // Get the column names
	            for (int column = 0; column < numcol; column++) {
	                columnNames.addElement(metaData.getColumnLabel(column + 1));
	            }

	            // Get all rows.
	            Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

	            while (resultsq.next()) {
	                Vector<Object> newRow = new Vector<Object>();

	                for (int i = 1; i <= numcol; i++) {
	                    newRow.addElement(resultsq.getObject(i));
	                }

	                rows.addElement(newRow);
	            }

	            return new DefaultTableModel(rows, columnNames);
	        } catch (Exception e) {
	            e.printStackTrace();

	            return null;
	        }
	    }
	
	public Project3() {
		setTitle("SQL Client GUI-(MJL-CNT 4714-Fall 2019)");
		String[] driverList = new String[]{ "com.mysql.jdbc.Driver", "oracle.jdbc.driver.OracleDriver", "com.ibm.db2.jdbc.netDB2Driver", "com.jdbc.odbc.jdbcOdbcDriver" };
		String[] urlList = new String[] { "jdbc:mysql://localhost:3306/project3", "jdbc:mysql://192.168.1.14:3306/project3"};
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 801, 403);
		cp = new JPanel();
		cp.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(cp);
		
		JScrollPane scrollPane = new JScrollPane();
		JPanel dbInfopanel = new JPanel();
		// COnfuring Text area to use fo
		areatext = new JTextArea();
		GridBagLayout gbl_dbInfopanel = new GridBagLayout();
		gbl_dbInfopanel.columnWidths = new int[] {75, 75, 0};gbl_dbInfopanel.rowHeights = new int[]{27, 27, 27, 27, 0};gbl_dbInfopanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_dbInfopanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		dbInfopanel.setLayout(gbl_dbInfopanel);
		driverbox = new JComboBox(driverList);
		GridBagConstraints gbc_driverbox = new GridBagConstraints();gbc_driverbox.fill = GridBagConstraints.BOTH;gbc_driverbox.insets = new Insets(0, 0, 5, 0);
		gbc_driverbox.gridx = 1;gbc_driverbox.gridy = 0;dbInfopanel.add(driverbox, gbc_driverbox);
		JLabel dbURLlbl = new JLabel("Database URL:");GridBagConstraints gbc_dbURLlbl = new GridBagConstraints();gbc_dbURLlbl.fill = GridBagConstraints.BOTH;
		gbc_dbURLlbl.insets = new Insets(0, 0, 5, 5);gbc_dbURLlbl.gridx = 0;gbc_dbURLlbl.gridy = 1;dbInfopanel.add(dbURLlbl, gbc_dbURLlbl);
		urlb = new JComboBox(urlList);GridBagConstraints gbc_urlb = new GridBagConstraints();gbc_urlb.fill = GridBagConstraints.BOTH;gbc_urlb.insets = new Insets(0, 0, 5, 0);
		gbc_urlb.gridx = 1;gbc_urlb.gridy = 1;dbInfopanel.add(urlb, gbc_urlb);
		JLabel userNamelbl = new JLabel("Username:");userNamelbl.setHorizontalAlignment(SwingConstants.LEFT);GridBagConstraints gbc_userNamelbl = new GridBagConstraints();
                gbc_userNamelbl.fill = GridBagConstraints.BOTH;gbc_userNamelbl.insets = new Insets(0, 0, 5, 5);gbc_userNamelbl.gridy = 2;gbc_userNamelbl.gridx = 0;dbInfopanel.add(userNamelbl, gbc_userNamelbl);
                uname = new JTextField();GridBagConstraints griduname = new GridBagConstraints();griduname.fill = GridBagConstraints.BOTH;
		griduname.insets = new Insets(0, 0, 5, 0);griduname.gridx = 1;griduname.gridx = 1;griduname.gridy = 2;dbInfopanel.add(uname, griduname);
		//Password field
		passf = new JPasswordField();GridBagConstraints gridpassf = new GridBagConstraints();gridpassf.fill = GridBagConstraints.BOTH;gridpassf.gridx = 1;gridpassf.gridy = 3;
		dbInfopanel.add(passf, gridpassf);
		 //Button and names for them
		String x="Connect to Database";String y="Clear SQL Command"; String z= "Execute SQL Command";
                JButton clearbutton = new JButton(y);JButton conbutton = new JButton(x);JButton executebutton = new JButton(z);
                //Connection Setup label
		consetup = new JLabel("No Connection");
		uname.setColumns(10);
                //Password label
		JLabel lblPassword = new JLabel("Password:");
                //Setting the label colors and properties
		lblPassword.setBackground(Color.GRAY);
		GridBagConstraints gridpass = new GridBagConstraints();
		gridpass.fill = GridBagConstraints.BOTH;
		gridpass.insets = new Insets(0, 0, 0, 5);
		gridpass.gridx = 0;
		gridpass.gridy = 3;
		dbInfopanel.add(lblPassword, gridpass);
		scrollPaneResults = new JScrollPane();
                //Naming the button for clear results
		JButton clrresultsbutton = new JButton("Clear Results Window");
                //Creating a new label JDBC driver
		JLabel jdbclbl = new JLabel("JDBC Driver:");
                //Configuring its properties
		GridBagConstraints gbc_jdbclbl = new GridBagConstraints();
		gbc_jdbclbl.fill = GridBagConstraints.BOTH;
		gbc_jdbclbl.insets = new Insets(0, 0, 5, 5);
		gbc_jdbclbl.gridx = 0;
		gbc_jdbclbl.gridy = 0;
		dbInfopanel.add(jdbclbl, gbc_jdbclbl);
		
		// Setting the layout and configurimg the layout 
		GroupLayout groupcp = new GroupLayout(cp);
		groupcp.setHorizontalGroup(
			groupcp.createParallelGroup(Alignment.LEADING)
				.addGroup(groupcp.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupcp.createParallelGroup(Alignment.LEADING)
						.addGroup(groupcp.createSequentialGroup()
							.addGroup(groupcp.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPaneResults, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
								.addGroup(groupcp.createSequentialGroup()
									.addComponent(dbInfopanel, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(groupcp.createSequentialGroup()
									.addComponent(consetup)
									.addPreferredGap(ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
									.addComponent(conbutton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(clearbutton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(executebutton)))
							.addGap(4))
						.addGroup(groupcp.createSequentialGroup()
							.addComponent(clrresultsbutton)
							.addContainerGap(527, Short.MAX_VALUE))))
		);
		groupcp.setVerticalGroup(
			groupcp.createParallelGroup(Alignment.LEADING)
				.addGroup(groupcp.createSequentialGroup()
					.addGroup(groupcp.createParallelGroup(Alignment.LEADING)
						.addComponent(dbInfopanel, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupcp.createParallelGroup(Alignment.BASELINE)
						.addComponent(executebutton)
						.addComponent(clearbutton)
						.addComponent(conbutton)
						.addComponent(consetup))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneResults, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(clrresultsbutton))
		);
		
                // areatext
		areatext.setLineWrap(true);scrollPane.setViewportView(areatext);
		
		cp.setLayout(groupcp);ButtonHandler actionhandler = new ButtonHandler();clearbutton.addActionListener(actionhandler);conbutton.addActionListener(actionhandler);
		clrresultsbutton.addActionListener(actionhandler);executebutton.addActionListener(actionhandler);	
	}
	
	private class ButtonHandler implements ActionListener{
		
		//Variable to use for connection and to display results 
                Connection conn;
		ResultSet resultSet;
		
                //Function to Listem for actions performed
		@Override
		public void actionPerformed(ActionEvent optionselected) {
			
			//Option Selected Clear results
			if(optionselected.getActionCommand() == "Clear SQL Command") {
				areatext.setText(null);
			
			}
                        //Option selected to connect to the database
			if(optionselected.getActionCommand() == "Connect to Database") {
				
				// String password to use later
				String password = new String(passf.getPassword());
				
                                //Mysql logining in system
				MysqlDataSource dataSource = new MysqlDataSource();dataSource.setUser(uname.getText());dataSource.setPassword(password);dataSource.setURL(urlb.getSelectedItem().toString());
		
				try {
					//Trying to connect to Mysql database
					conn = dataSource.getConnection();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog( null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE );
				}
                                //message for sucesfully connecting to database
				consetup.setText("Connected to " + urlb.getSelectedItem());
				isConnected = true;
			}
                        //Option slected Clear Results window
			if(optionselected.getActionCommand() == "Clear Results Window") {
				DefaultTableModel model = (DefaultTableModel) resultsquery.getModel();model.getDataVector().removeAllElements();model.setColumnCount(0);model.fireTableDataChanged();
				
			}
			// Slect option  to get Execute command
			if(optionselected.getActionCommand() == "Execute SQL Command") {
                            // check to see the connectioon 
                            if(!isConnected) {
					JOptionPane.showMessageDialog(null,"Please Connect to a Database!", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
                            // check to see the connection is connected and areatext is not empty
				if(isConnected && areatext.getText() != null ) {
					try {
                                            //statement to use for query 
						Statement statement = conn.createStatement();
						// check to see select option is selected in the text area
						if(areatext.getText().contains("select") || areatext.getText().contains("SELECT")) {
                                                    //result from query stored in varaiblre
                                                    resultSet = statement.executeQuery(areatext.getText());
							// create a new table for it 
							resultsquery = new JTable(resultSetToTableModel(resultSet));
                                                        //scroable results
							scrollPaneResults.setViewportView(resultsquery);
						}
						else{
							
							statement.executeUpdate(areatext.getText());
						}
						}catch (SQLException e) {
							JOptionPane.showMessageDialog( null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE );
					}
				}
				
			}
		}
		
		
		
		
	}
}