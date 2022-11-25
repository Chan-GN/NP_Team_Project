import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class ChatRoomPlusUser extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3425750604862704099L;
	/**
	 * 
	 */
	public JButton profileBtn;
	private JLabel user_name;
	public JCheckBox checkBox;
	/**
	 * Create the panel.
	 */
	public ChatRoomPlusUser(String username, JavaObjClientMain testview) {
		setLayout(null); // absolute layout
		setBackground(Color.white);
		setPreferredSize(new Dimension(320,60)); // ���� ������ �����ѵ� ?
		user_name = new JLabel(username);
		user_name.setBounds(65, 16, 114, 30);
		user_name.setFont(new Font("����ü", Font.PLAIN, 14));
		add(user_name);
		System.out.println(testview.getUserProfile(username).toString());
		
		ImageIcon profile = testview.getUserProfile(username);
		profileBtn = new JButton(profile);
		profileBtn.setBounds(8, 8, 46, 46);
		profileBtn.setBorderPainted(false);
		profileBtn.setContentAreaFilled(false);
		profileBtn.setFocusPainted(false);
		
		add(profileBtn);
		
		addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

				setBackground(Color.white);
				checkBox.setBackground(Color.white);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				setBackground(new Color(248, 248, 248));
				checkBox.setBackground(new Color(248, 248, 248));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		
		checkBox = new JCheckBox();
		checkBox.setBackground(Color.white);
		checkBox.setBounds(240, 20, 23, 23);
		add(checkBox);
	}
	
	public String getUserName() {
		return this.user_name.getText();
	}
}
