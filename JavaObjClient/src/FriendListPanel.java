import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class FriendListPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7410115339163008109L;
	private JLabel FriendList_username;
	public JButton profileBtn;
	private Frame frame;
	private FileDialog fd;
	public JavaObjClientMain mainview; // ���� SendObject Ȱ���� ����
	public FriendListPanel view;
	/**
	 * Create the panel.
	 */
	public FriendListPanel(ImageIcon profile, String username, JavaObjClientMain testview) { // �並 ���ڷ� ����
		mainview = testview;
		setLayout(null); // absolute layout
		setBackground(Color.white);
		setPreferredSize(new Dimension(320,70)); // ���� ������ �����ѵ� ?
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
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		FriendList_username = new JLabel(username);
		FriendList_username.setBounds(111, 14, 169, 30);
		FriendList_username.setFont(new Font("����ü", Font.PLAIN, 14));
		add(FriendList_username);

		profileBtn = new JButton(profile);
		profileBtn.setBounds(12, 14, 77, 46);
		profileBtn.setBorderPainted(false);
		profileBtn.setContentAreaFilled(false);
		profileBtn.setFocusPainted(false);
		add(profileBtn);

		profileBtn.addMouseListener(new MouseListener() {			
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
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		profileBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(username.equals(mainview.UserName)) { // �г��� �̸��� ���κ��� �̸��� ���� ���, �� ������ ��쿡�� ���� ������
					if (e.getSource() == profileBtn) {
						frame = new Frame("������ ���� ����");
						fd = new FileDialog(frame, "������ ���� ����", FileDialog.LOAD);
						fd.setVisible(true);
						
						if(fd.getFile() == null) {
//							System.out.println("��ҵ�");
						} else {
							ChatMsg obcm = new ChatMsg(username, "888", "PROFILEIMG"); // ������ ���õǸ� 888 �ڵ��
							ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile()); // �����ϰ��� �ϴ� �̹��� ��������
							obcm.setImg(img); // ��ü�� ���
							mainview.SendObject(obcm); // ���κ��� SendObject ȣ��
						}	
					}
				}
			}
			
		});
	}
	public String getFriendList_username() {
		return FriendList_username.getText();
	}
}