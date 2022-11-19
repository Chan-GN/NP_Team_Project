import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;

public class ChatRoomPlusUser extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3425750604862704099L;
	/**
	 * 
	 */
	private JLabel user_name;
	public JCheckBox checkBox;
	/**
	 * Create the panel.
	 */
	public ChatRoomPlusUser(String username) {
		setLayout(null); // absolute layout
		setPreferredSize(new Dimension(284,50)); // ���� ������ �����ѵ� ?
		user_name = new JLabel(username);
		user_name.setBounds(70, 10, 141, 30);
		user_name.setFont(new Font("����ü", Font.PLAIN, 14));
		add(user_name);
		
		checkBox = new JCheckBox();
		checkBox.setBounds(242, 12, 23, 23);
		add(checkBox);
	}
	
	public String getUserName() {
		return this.user_name.getText();
	}
}
