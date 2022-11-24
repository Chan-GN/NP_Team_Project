
// JavaObjClientView.java ObjecStram ��� Client
//�������� ä�� â
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;


public class JavaObjClientMain extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public String UserName;
	
	/* test code */
	private Socket socket; // �������
	public JavaObjClientMain testview; // view ����� ���� view ����
	public List<JavaObjClientChatRoom> testchatviews = new ArrayList<JavaObjClientChatRoom>(); // Ŭ���̾�Ʈ�� ä�ù��� ��Ƶδ� ����Ʈ
	private JTextPane chatRoomArea; // scrollpane ���ο� ������ chatRoomBox�� ����� ģ��
	private JTextPane friendListArea;
	private String test_roomid = ""; // ������ �� ���̵�
	private List<FriendListPanel> friend_lists = new ArrayList<FriendListPanel>(); // ģ�� ����Ʈ �г��� ���� ����Ʈ
	private List<ChatRoomBoxTest> chatRoomlists = new ArrayList<ChatRoomBoxTest>(); // ä�ù� ����Ʈ�� ���� ����Ʈ
	
	public String[] user_list;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public JButton btnfriend;
	public JButton btnchat;
	//test//
	public ImageIcon profileBasic = new ImageIcon("src/profileIMG_basic.png");
	public String[] usertemp;
	public JTextPane myprofile;
	public int count=0;
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
		chatRoomArea.setFont(new Font("����ü", Font.PLAIN, 2));
		chatRoomArea.setOpaque(false);
		chat_scrollPane.setViewportView(chatRoomArea); // scrollpane�� chatRoomArea �߰�

		JScrollPane myprofileArea = new JScrollPane();
		myprofileArea.setBounds(64, 65, 320, 94);
		myprofileArea.getViewport().setOpaque(false);
		myprofileArea.setOpaque(false);
		myprofileArea.setBorder(null);		
		contentPane.add(myprofileArea);

		myprofile = new JTextPane();
		myprofile.setEditable(true);
		myprofile.setBackground(Color.white);
		myprofile.setBounds(64, 156, 320, -90);
		myprofile.setFont(new Font("����ü", Font.PLAIN, 14));
		myprofile.setOpaque(false);
		myprofile.setLayout(null);
		myprofileArea.setViewportView(myprofile);

		myprofileArea.setVisible(false);

		JLabel myprofilename = new JLabel(username);
		myprofilename.setBounds(120, -70, 65, 18);
		myprofilename.setFont(new Font("����ü", Font.PLAIN, 14));
		myprofile.add(myprofilename);

		JButton myprofileBtn = new JButton(profileBasic);
		myprofileBtn.setBounds(30, -50, 65, 18);
		myprofileBtn.setBorderPainted(false);
		myprofileBtn.setContentAreaFilled(false);
		myprofileBtn.setFocusPainted(false);
		myprofile.add(myprofileBtn);
		
		myprofile = new JTextPane();
		myprofile.setEditable(true);
		myprofile.setBackground(Color.white);
		myprofile.setBounds(64, 156, 320, -90);
		myprofile.setFont(new Font("����ü", Font.PLAIN, 14));
		myprofile.setOpaque(false);
		myprofile.setLayout(null);
		myprofileArea.setViewportView(myprofile);
		
		myprofileArea.setVisible(false);
		
		JScrollPane friend_scrollPane = new JScrollPane();
		friend_scrollPane.setBounds(64, 143, 320, 450);
		friend_scrollPane.getViewport().setOpaque(false);
		friend_scrollPane.setOpaque(false);
		friend_scrollPane.setBorder(null);		
		contentPane.add(friend_scrollPane);

		friendListArea = new JTextPane();
		friendListArea.setEditable(true);
		friendListArea.setFont(new Font("����ü", Font.PLAIN, 14));
		friendListArea.setOpaque(false);
		friend_scrollPane.setViewportView(friendListArea); // scrollpane�� chatRoomArea �߰�
				
		friendListArea = new JTextPane();
		friendListArea.setEditable(true);
		friendListArea.setFont(new Font("����ü", Font.PLAIN, 14));
		friendListArea.setOpaque(false);
		friend_scrollPane.setViewportView(friendListArea); // scrollpane�� chatRoomArea �߰�

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
				myprofileArea.setVisible(true);
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
				myprofileArea.setVisible(false);
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
				ChatMsg obcm = new ChatMsg(UserName, "601", "LIST");
				SendObject(obcm);
				
				// ���� ��� ���� ����
//				Date now = new Date(); // ���� ��¥ �� �ð��� ����ؼ�
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss��"); // ������ ���ϰ�
//				test_roomid = formatter.format(now); // ���� �ð��� ���Ŀ� ����
//				SendRoomId(test_roomid); // ������ ä�ù� �̸� ����
//				testchatviews.add(new JavaObjClientChatRoom(username, test_roomid, testview)); // ä�ù濡�� ���� �̸��� ä�ù� �̸�, ���� ������ Mainview ����, ���� ä�ù� ���� ����Ʈ �߰� ���
//				for(JavaObjClientChatRoom testchatview : testchatviews) {
////					System.out.println(testchatview.getRoomId());
//				}
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
						case "601": // ���� ������ ���� ����Ʈ�� ����
//							System.out.println(UserName + cm.con_user_list);
							new ChatRoomPlus(UserName, cm.con_user_list, testview);
							break;
						case "777": // Ŭ�󿡼� ���� Login �ν��ϸ�
							String uls[] = cm.getData().split(","); // uls[n] = username OFF, ���� �и�
							myprofile.setText(""); // JTextPane �ʱ�ȭ
							friendListArea.setText(""); // JTextPane �ʱ�ȭ
							friend_lists.clear(); // ����Ʈ �ʱ�ȭ
 							for(int i = 0; i < uls.length; i++) {
								String uis[] = uls[i].split(" "); // uis[0] = username, uis[1] = userstatus
								FriendListPanel f = new FriendListPanel(profileBasic, uis[0], testview, uis[1]); // �г� �߰� �� ���µ� ����
								if(uis[0].equals(UserName)) { // �̸��� ������ ���������ʿ� �߰�
									myprofile.insertComponent(f);
								}
								else {
									friendListArea.insertComponent(f);											
								}
								friend_lists.add(f); // ����Ʈ�� �߰�
							}							
							break;
						case "888": // 888�� ���Ź�����
							for(FriendListPanel fl : friend_lists) { // ����Ʈ ������ ����
								if(fl.getFriendList_username().equals(cm.getId())) { // �г��� �̸��� �̸��� ���� ģ���� ã�Ƽ� ( �� ���� ��û ���� )
									fl.profileBtn.setIcon(cm.img); // �̹��� ����
//									System.out.println(UserName + " �� �� " + cm.getId());
								}
							}
							for(ChatRoomBoxTest chatRoomlist: chatRoomlists) {
//								System.out.println(UserName + " " + chatRoomlist.getChatroombox_title());
								String[] ul = chatRoomlist.getChatroombox_title().trim().split(", ");
								if(ul.length == 1) {
									for(int i=0; i<ul.length; i++) {
										if(cm.getId().equals(ul[i])) {
//											System.out.println(cm.getId());
											chatRoomlist.usersPfImgOne.setIcon(cm.img);
										}
									}									
								} else if(ul.length == 2) {
									for(int i=0; i<ul.length; i++) {
										if(cm.getId().equals(ul[i])) {
//											System.out.println(cm.getId());
											if(cm.getId().equals(chatRoomlist.usersPfImgTwo_1.getText()))
												chatRoomlist.usersPfImgTwo_1.setIcon(cm.img);
											else
												chatRoomlist.usersPfImgTwo_2.setIcon(cm.img);
										}
									}									
								} else if(ul.length == 3) {
									for(int i=0; i<ul.length; i++) {
										if(cm.getId().equals(ul[i])) {
//											System.out.println(cm.getId());
											if(cm.getId().equals(chatRoomlist.usersPfImgTh_1.getText()))
												chatRoomlist.usersPfImgTh_1.setIcon(cm.img);
											else if(cm.getId().equals(chatRoomlist.usersPfImgTh_2.getText()))
												chatRoomlist.usersPfImgTh_2.setIcon(cm.img);
											else
												chatRoomlist.usersPfImgTh_3.setIcon(cm.img);
										}
									}									
								} else {
									for(int i=0; i<ul.length; i++) {
										if(cm.getId().equals(ul[i])) {
//											System.out.println(cm.getId());
											if(cm.getId().equals(chatRoomlist.usersPfImgF_1.getText()))
												chatRoomlist.usersPfImgF_1.setIcon(cm.img);
											else if(cm.getId().equals(chatRoomlist.usersPfImgF_2.getText()))
												chatRoomlist.usersPfImgF_2.setIcon(cm.img);
											else if(cm.getId().equals(chatRoomlist.usersPfImgF_3.getText()))
												chatRoomlist.usersPfImgF_3.setIcon(cm.img);
											else
												chatRoomlist.usersPfImgF_4.setIcon(cm.img);
										}
									}									
								}
							}
							for(JavaObjClientChatRoom testchatview : testchatviews) {
								String[] ul = testchatview.roomUserlist.trim().split(" ");
								if(ul.length == 1) {
									for(int i=0; i<ul.length; i++) {
										if(cm.getId().equals(ul[i])) {
//											System.out.println(cm.getId());
											testchatview.usersPfImgOne.setIcon(cm.img);
										}
									}									
								} else if(ul.length == 2) {
									for(int i=0; i<ul.length; i++) {
										if(cm.getId().equals(ul[i])) {
//											System.out.println(cm.getId());
											if(cm.getId().equals(testchatview.usersPfImgTwo_1.getText()))
												testchatview.usersPfImgTwo_1.setIcon(cm.img);
											else
												testchatview.usersPfImgTwo_2.setIcon(cm.img);
										}
									}									
								} else if(ul.length == 3) {
									for(int i=0; i<ul.length; i++) {
										if(cm.getId().equals(ul[i])) {
//											System.out.println(cm.getId());
											if(cm.getId().equals(testchatview.usersPfImgTh_1.getText()))
												testchatview.usersPfImgTh_1.setIcon(cm.img);
											else if(cm.getId().equals(testchatview.usersPfImgTh_2.getText()))
												testchatview.usersPfImgTh_2.setIcon(cm.img);
											else
												testchatview.usersPfImgTh_3.setIcon(cm.img);
										}
									}									
								} else {
									for(int i=0; i<ul.length; i++) {
										if(cm.getId().equals(ul[i])) {
//											System.out.println(cm.getId());
											if(cm.getId().equals(testchatview.usersPfImgF_1.getText()))
												testchatview.usersPfImgF_1.setIcon(cm.img);
											else if(cm.getId().equals(testchatview.usersPfImgF_2.getText()))
												testchatview.usersPfImgF_2.setIcon(cm.img);
											else if(cm.getId().equals(testchatview.usersPfImgF_3.getText()))
												testchatview.usersPfImgF_3.setIcon(cm.img);
											else
												testchatview.usersPfImgF_4.setIcon(cm.img);
										}
									}									
								}
							}
							break;
						case "999": // �ڵ尡 999��� ä�ù� ������ ��� �ִ� �г��� ä�ù� ��Ͽ� �߰���
							int len = chatRoomArea.getDocument().getLength();
							chatRoomArea.setCaretPosition(len); // place caret at the end (with no selection)
							String room_title = cm.selected_userlist.trim();
							room_title = room_title.replace(" ", ", ");
							ChatRoomBoxTest chatroombox_test = new ChatRoomBoxTest(room_title, profileBasic);
							chatRoomlists.add(chatroombox_test);
//							System.out.println(cm.getData());
							chatroombox_test.addMouseListener(new MouseListener() { // ä�ù� Ŭ�� ������
								@Override
								public void mouseClicked(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void mousePressed(MouseEvent e) { 
									// TODO Auto-generated method stub
									if (e.getClickCount()==2){ // �ι� Ŭ���ϸ�
										testchatviews.add(new JavaObjClientChatRoom(UserName, cm.getData(), testview, cm.selected_userlist)); // cm.getData()���� ä�ù� �̸��� ��� �ְ� �ش� ä�ù� ����
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

							chatRoomArea.insertComponent(chatroombox_test);
							chatRoomArea.replaceSelection("\n");
							String username = cm.getId();
							if(UserName.equals(username)) {
									testchatviews.add(new JavaObjClientChatRoom(username, test_roomid, testview, cm.selected_userlist));
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
	public void SendRoomId(String userlist) { // ��ư Ŭ�� �� 
		try {
			Date now = new Date(); // ���� ��¥ �� �ð��� ����ؼ�
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss��"); // ������ ���ϰ�
			test_roomid = formatter.format(now); // ���� �ð��� ���Ŀ� ����
			ChatMsg obcm = new ChatMsg(UserName, "999", test_roomid);
			obcm.selected_userlist = userlist;
			oos.writeObject(obcm); // ���⼭���� �ϸ�� �������� �����ϴ���?
		} catch (IOException e) {
//			AppendText("SendObject Error");
		}
	}


}
