
// JavaObjClientView.java ObjecStram ��� Client
//�������� ä�� â
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import javax.swing.JToggleButton;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class JavaObjClientMain extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String UserName;
	
	/* test code */
	private Socket socket; // �������
	public JavaObjClientMain testview; // view ����� ���� view ����
	public List<JavaObjClientChatRoom> testchatviews = new ArrayList<JavaObjClientChatRoom>(); // Ŭ���̾�Ʈ�� ä�ù��� ��Ƶδ� ����Ʈ
	private JTextPane chatRoomArea; // scrollpane ���ο� ������ chatRoomBox�� ����� ģ��
	private JPanel chatRoomBox; // �� �����ø��� ������ ä�ù� �г�
	private String test_roomid = ""; // ������ �� ���̵�
	private JLabel testLabel; // chatRoomBox�� �� ���̵� �����ִ� ��
	
	public String[] user_list;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public JButton btnfriend;
	public JButton btnchat;
	
	/**
	 * Create the frame.
	 */
	public JavaObjClientMain(String username, String ip_addr, String port_no) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.white);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		

		JScrollPane chat_scrollPane = new JScrollPane();
		chat_scrollPane.setBounds(64, 65, 320, 528);
		chat_scrollPane.getViewport().setOpaque(false);
		chat_scrollPane.setOpaque(false);
		chat_scrollPane.setBorder(null);
		contentPane.add(chat_scrollPane);
		
		/* test code */
		chatRoomArea = new JTextPane();
		chatRoomArea.setEditable(true);
		chatRoomArea.setFont(new Font("����ü", Font.PLAIN, 14));
		chatRoomArea.setOpaque(false);
		chat_scrollPane.setViewportView(chatRoomArea); // scrollpane�� chatRoomArea �߰�

		
		JScrollPane friend_scrollPane = new JScrollPane();
		friend_scrollPane.setBounds(64, 65, 320, 528);
		friend_scrollPane.getViewport().setOpaque(false);
		friend_scrollPane.setOpaque(false);
		friend_scrollPane.setBorder(null);		
		contentPane.add(friend_scrollPane);
				
		JLabel lblNewLabel_1 = new JLabel("ģ��"); // �׽�Ʈ ��
		friend_scrollPane.setColumnHeaderView(lblNewLabel_1);
		friend_scrollPane.setVisible(false);
				
		JLabel chatLabel = new JLabel("ä��"); // ����Ʈ : ä��
		chatLabel.setFont(new Font("����", Font.BOLD, 18));
		chatLabel.setBounds(80, 25, 50, 32);
		contentPane.add(chatLabel);
		
		// �̹��� ��ư ����
		ImageIcon friend_icon_c = new ImageIcon("src/friendbtn_c.png");
		ImageIcon friend_icon_n = new ImageIcon("src/friendbtn_n.png");
		ImageIcon friend_icon_o = new ImageIcon("src/friendbtn_o.png");
		ImageIcon chat_icon_n = new ImageIcon("src/chatbtn_n.png");
		ImageIcon chat_icon_o = new ImageIcon("src/chatbtn_o.png");
		ImageIcon chat_icon_c = new ImageIcon("src/chatbtn_c.png");
		
		btnfriend = new JButton(friend_icon_n);
		btnfriend.setBounds(8, 21, 45, 45);
		btnfriend.setBorder(BorderFactory.createEmptyBorder());
		btnfriend.setFocusPainted(false);
		btnfriend.setContentAreaFilled(false);
		btnfriend.getCursor();
		btnfriend.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnfriend.addActionListener(new ActionListener() { // ģ�� ��ư �� ������
			public void actionPerformed(ActionEvent e) {
				chat_scrollPane.setVisible(false);
				friend_scrollPane.setVisible(true);
				chatLabel.setText("ģ��"); // ģ���� �� ����
				btnfriend.setIcon(friend_icon_c);
				btnchat.setIcon(chat_icon_n);
				// test code, ģ�� ��ư Ŭ�� �� �������� ������ ����Ʈ ��û
//				ChatMsg obcm = new ChatMsg(UserName, "600", "LIST");
//				SendObject(obcm);
			}
		});
		btnfriend.addMouseListener(new MouseListener() { // ��ư�� Ŀ�� �̺�Ʈ ����
			
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
				if(btnfriend.getIcon() != null && btnfriend.getIcon().toString() != "src/friendbtn_c.png")
					btnfriend.setIcon(friend_icon_n);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if(btnfriend.getIcon() != null && btnfriend.getIcon().toString() != "src/friendbtn_c.png")
					btnfriend.setIcon(friend_icon_o);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		contentPane.add(btnfriend);

		btnchat = new JButton(chat_icon_c);
		btnchat.setBounds(8, 85, 45, 45);
		btnchat.setBorder(BorderFactory.createEmptyBorder());
		btnchat.setContentAreaFilled(false);
		btnchat.setFocusPainted(false);
		btnchat.getCursor();
		btnchat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
		btnchat.addActionListener(new ActionListener() { // ä�� ��ư�� ������
			public void actionPerformed(ActionEvent e) {
				chat_scrollPane.setVisible(true);
				friend_scrollPane.setVisible(false);
				chatLabel.setText("ä��"); // ä������ �� ����
				btnfriend.setIcon(friend_icon_n);
				btnchat.setIcon(chat_icon_c);
			}
		});
		btnchat.addMouseListener(new MouseListener() { // ��ư�� Ŀ�� �̺�Ʈ ����
			
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
				if(btnchat.getIcon() != null && btnchat.getIcon().toString() != "src/chatbtn_c.png")
					btnchat.setIcon(chat_icon_n);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				if(btnchat.getIcon() != null && btnchat.getIcon().toString() != "src/chatbtn_c.png")
					btnchat.setIcon(chat_icon_o);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		contentPane.add(btnchat);
		
		ImageIcon chatplus_icon = new ImageIcon("src/chatplus_test.png");
		JButton btnchatplus= new JButton(chatplus_icon);
		btnchatplus.setBounds(323, 14, 45, 45);
		btnchatplus.setBorder(BorderFactory.createEmptyBorder());
		btnchatplus.setFocusPainted(false);
		btnchatplus.setContentAreaFilled(false);
		btnchatplus.getCursor();
		btnchatplus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnchatplus.addActionListener(new ActionListener() { // ä�ù� �߰� ��ư Ŭ�� ������
			@Override
			public void actionPerformed(ActionEvent e) { // ��ư�� ������
				// TODO Auto-generated method stub
				Date now = new Date(); // ���� ��¥ �� �ð��� ����ؼ�
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss��"); // ������ ���ϰ�
				test_roomid = formatter.format(now); // ���� �ð��� ���Ŀ� ����
				SendRoomId(test_roomid); // ������ ä�ù� �̸� ����
//				testchatviews.add(new JavaObjClientChatRoom(username, test_roomid, testview)); // ä�ù濡�� ���� �̸��� ä�ù� �̸�, ���� ������ Mainview ����, ���� ä�ù� ���� ����Ʈ �߰� ���
				for(JavaObjClientChatRoom testchatview : testchatviews) {
//					System.out.println(testchatview.getRoomId());
				}
			}
		});
		contentPane.add(btnchatplus);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 65, 593);
		panel.setBackground(new Color(236, 236, 237));
		contentPane.add(panel);
		
		setVisible(true);
		
		UserName = username;
		testview = this; // �� ����� ����
		
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");
			SendObject(obcm);
			
			ListenNetwork net = new ListenNetwork();
			net.start();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			AppendText("connect error");
		}
		

	}
	// Server Message�� �����ؼ� ȭ�鿡 ǥ��
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {
					Object obcm = null;
					String msg = null;
					ChatMsg cm;										
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						// if(cm.getId().equals(UserName))
							// �����ʿ� ���
						msg = String.format("[%s] %s", cm.getId(), cm.getData());
					} else
						continue;
					switch (cm.getCode()) {
						case "200": // chat message
							for(JavaObjClientChatRoom testchatview : testchatviews) { // ������ ä�ù���� ����
								if(testchatview.getRoomId().equals(cm.getRoomId())) { // ä�ù� �̸��� �˻��ؼ� 
									testchatview.AppendText(msg); // �ش��ϴ� ä�ù濡 AppendText ȣ��
								}
							}
							break;
						case "300": // Image ÷��
							for(JavaObjClientChatRoom testchatview : testchatviews) { // ������ ä�ù���� ����
								if(testchatview.getRoomId().equals(cm.getRoomId())) { // ä�ù� �̸��� �˻��ؼ� 
										testchatview.AppendText("[" + cm.getId() + "]");
										testchatview.AppendImage(cm.img);
								}
							}
							break;
						case "301": // ����Ŭ��
							for(JavaObjClientChatRoom testchatview : testchatviews) { // ������ ä�ù���� ����
								if(testchatview.getRoomId().equals(cm.getRoomId())) { // ä�ù� �̸��� �˻��ؼ� 
										testchatview.AppendText("[" + cm.getId() + "]");
										testchatview.AppendImage(cm.img);
								}
							}
							break;
						case "302": // �ѹ� Ŭ��
							for(JavaObjClientChatRoom testchatview : testchatviews) { // ������ ä�ù���� ����
								if(testchatview.getRoomId().equals(cm.getRoomId())) { // ä�ù� �̸��� �˻��ؼ� 
									testchatview.panelIMG=cm.img;
									testchatview.EmoLabel.setVisible(true);
									testchatview.EmoLabel.setIcon(cm.img);
									testchatview.EmoLabel.repaint();
								}
							}
							break;
						case "500":
							for(JavaObjClientChatRoom testchatview : testchatviews) { // ������ ä�ù���� ����
								if(testchatview.getRoomId().equals(cm.getRoomId())) { // ä�ù� �̸��� �˻��ؼ� 
									testchatview.AppendText("[" + cm.getId() + "] " + cm.filename);
									testchatview.AppendFile(cm.file, cm.filename);
								}
							}
							break;
						case "600": // ���� ������ ���� ����Ʈ�� ����
//							System.out.println(cm.getData());
							break;
						case "777":
							String a = cm.getData().substring(1, cm.getData().length()-1).replaceAll(" ","");
							user_list=a.split(",");
							break;
						case "999": // �ڵ尡 999��� ä�ù� ������ ��� �ִ� �г��� ä�ù� ��Ͽ� �߰���
							int len = chatRoomArea.getDocument().getLength();
							chatRoomArea.setCaretPosition(len); // place caret at the end (with no selection)
							chatRoomBox = new JPanel();
							chatRoomBox.setLayout(new BorderLayout(25,25));
							testLabel = new JLabel();
							testLabel.setFont(new Font("����ü", Font.PLAIN, 14));
							testLabel.setText(cm.getData());
							JLabel borderLabel = new JLabel();
							borderLabel.setBorder(null);
							/* test code, ���� ��ġ �� �г��� ���� �����غ� */
							chatRoomBox.add(new JLabel(), BorderLayout.NORTH);
							chatRoomBox.add(new JLabel(), BorderLayout.EAST);
							chatRoomBox.add(testLabel, BorderLayout.CENTER);
							chatRoomBox.add(new JLabel(), BorderLayout.WEST);
							chatRoomBox.add(new JLabel(), BorderLayout.SOUTH);
							chatRoomBox.setBackground(Color.white);
							chatRoomBox.addMouseListener(new MouseListener() { // ä�ù� Ŭ�� ������
								@Override
								public void mouseClicked(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void mousePressed(MouseEvent e) { 
									// TODO Auto-generated method stub
									if (e.getClickCount()==2){ // �ι� Ŭ���ϸ�
										testchatviews.add(new JavaObjClientChatRoom(UserName, cm.getData(), testview)); // cm.getData()���� ä�ù� �̸��� ��� �ְ� �ش� ä�ù� ����
									}
								}

								@Override
								public void mouseReleased(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void mouseEntered(MouseEvent e) {
									// TODO Auto-generated method stub
								}

								@Override
								public void mouseExited(MouseEvent e) {
									// TODO Auto-generated method stub
								}			
							});
							ChatRoomBoxTest chatroombox_test = new ChatRoomBoxTest(cm.getData());
							System.out.println(cm.getData());
							chatRoomArea.insertComponent(chatroombox_test);
							String username = cm.getId();
							if(UserName.equals(username)) {
									testchatviews.add(new JavaObjClientChatRoom(username, test_roomid, testview));
							}
							break;
						}
					}
				 catch (IOException e) {
//					AppendText("ois.readObject() error");
					try {
						ois.close();
						oos.close();
						socket.close();

						break;
					} catch (Exception ee) {
						break;
					} // catch�� ��
				} // �ٱ� catch����

			}
		}
	}
	public void SendObject(Object ob) { // ������ �޼����� ������ �޼ҵ�
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
//			AppendText("SendObject Error");
		}
	}
	
	/* test code */
	public void SendRoomId(String room_id) { // ��ư Ŭ�� �� 
		try {
			ChatMsg obcm = new ChatMsg(UserName, "999", room_id);
			oos.writeObject(obcm);
		} catch (IOException e) {
//			AppendText("SendObject Error");
		}
	}


}
