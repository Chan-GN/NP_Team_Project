
// JavaObjClientView.java ObjecStram 기반 Client
//실질적인 채팅 창
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
	private Socket socket; // 연결소켓
	public JavaObjClientMain testview; // view 상속을 위해 view 선언
	public List<JavaObjClientChatRoom> testchatviews = new ArrayList<JavaObjClientChatRoom>(); // 클라이언트의 채팅방을 담아두는 리스트
	private JTextPane chatRoomArea; // scrollpane 내부에 하위의 chatRoomBox를 담아줄 친구
	private JPanel chatRoomBox; // 방 생성시마다 생성될 채팅방 패널
	private String test_roomid = ""; // 고유한 룸 아이디
	private JLabel testLabel; // chatRoomBox에 룸 아이디를 적어주는 라벨
	
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
		chatRoomArea.setFont(new Font("굴림체", Font.PLAIN, 14));
		chatRoomArea.setOpaque(false);
		chat_scrollPane.setViewportView(chatRoomArea); // scrollpane에 chatRoomArea 추가

		
		JScrollPane friend_scrollPane = new JScrollPane();
		friend_scrollPane.setBounds(64, 65, 320, 528);
		friend_scrollPane.getViewport().setOpaque(false);
		friend_scrollPane.setOpaque(false);
		friend_scrollPane.setBorder(null);		
		contentPane.add(friend_scrollPane);
				
		JLabel lblNewLabel_1 = new JLabel("친구"); // 테스트 라벨
		friend_scrollPane.setColumnHeaderView(lblNewLabel_1);
		friend_scrollPane.setVisible(false);
				
		JLabel chatLabel = new JLabel("채팅"); // 디폴트 : 채팅
		chatLabel.setFont(new Font("굴림", Font.BOLD, 18));
		chatLabel.setBounds(80, 25, 50, 32);
		contentPane.add(chatLabel);
		
		// 이미지 버튼 생성
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
		btnfriend.addActionListener(new ActionListener() { // 친구 버튼 을 누르면
			public void actionPerformed(ActionEvent e) {
				chat_scrollPane.setVisible(false);
				friend_scrollPane.setVisible(true);
				chatLabel.setText("친구"); // 친구로 라벨 변경
				btnfriend.setIcon(friend_icon_c);
				btnchat.setIcon(chat_icon_n);
				// test code, 친구 버튼 클릭 시 서버에게 접속자 리스트 요청
//				ChatMsg obcm = new ChatMsg(UserName, "600", "LIST");
//				SendObject(obcm);
			}
		});
		btnfriend.addMouseListener(new MouseListener() { // 버튼에 커서 이벤트 적용
			
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
		btnchat.addActionListener(new ActionListener() { // 채팅 버튼을 누르면
			public void actionPerformed(ActionEvent e) {
				chat_scrollPane.setVisible(true);
				friend_scrollPane.setVisible(false);
				chatLabel.setText("채팅"); // 채팅으로 라벨 변경
				btnfriend.setIcon(friend_icon_n);
				btnchat.setIcon(chat_icon_c);
			}
		});
		btnchat.addMouseListener(new MouseListener() { // 버튼에 커서 이벤트 적용
			
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
		btnchatplus.addActionListener(new ActionListener() { // 채팅방 추가 버튼 클릭 리스너
			@Override
			public void actionPerformed(ActionEvent e) { // 버튼이 눌리면
				// TODO Auto-generated method stub
				Date now = new Date(); // 현재 날짜 및 시간을 계산해서
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss초"); // 형식을 정하고
				test_roomid = formatter.format(now); // 계산된 시간을 형식에 적용
				SendRoomId(test_roomid); // 서버로 채팅방 이름 보냄
				testchatviews.add(new JavaObjClientChatRoom(username, test_roomid, testview)); // 채팅방에는 유저 이름과 채팅방 이름, 현재 유저의 Mainview 전달, 추후 채팅방 유저 리스트 추가 요망
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
		testview = this; // 뷰 상속을 위해
		
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
	// Server Message를 수신해서 화면에 표시
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
							// 오른쪽에 출력
						msg = String.format("[%s] %s", cm.getId(), cm.getData());
					} else
						continue;
					switch (cm.getCode()) {
						case "200": // chat message
							for(JavaObjClientChatRoom testchatview : testchatviews) { // 유저의 채팅방들을 돌며
								if(testchatview.getRoomId().equals(cm.getRoomId())) { // 채팅방 이름을 검색해서 
									testchatview.AppendText(msg); // 해당하는 채팅방에 AppendText 호출
								}
							}
							break;
						case "300": // Image 첨부
							for(JavaObjClientChatRoom testchatview : testchatviews) { // 유저의 채팅방들을 돌며
								if(testchatview.getRoomId().equals(cm.getRoomId())) { // 채팅방 이름을 검색해서 
										testchatview.AppendText("[" + cm.getId() + "]");
										testchatview.AppendImage(cm.img);
								}
							}
							break;
						case "301": // 더블클릭
							for(JavaObjClientChatRoom testchatview : testchatviews) { // 유저의 채팅방들을 돌며
								if(testchatview.getRoomId().equals(cm.getRoomId())) { // 채팅방 이름을 검색해서 
										testchatview.AppendText("[" + cm.getId() + "]");
										testchatview.AppendImage(cm.img);
								}
							}
							break;
						case "302": // 한번 클릭
							for(JavaObjClientChatRoom testchatview : testchatviews) { // 유저의 채팅방들을 돌며
								if(testchatview.getRoomId().equals(cm.getRoomId())) { // 채팅방 이름을 검색해서 
									testchatview.panelIMG=cm.img;
									testchatview.EmoLabel.setVisible(true);
									testchatview.EmoLabel.setIcon(cm.img);
									testchatview.EmoLabel.repaint();
								}
							}
							break;
						case "500":
							for(JavaObjClientChatRoom testchatview : testchatviews) { // 유저의 채팅방들을 돌며
								if(testchatview.getRoomId().equals(cm.getRoomId())) { // 채팅방 이름을 검색해서 
									testchatview.AppendText("[" + cm.getId() + "] " + cm.filename);
									testchatview.AppendFile(cm.file, cm.filename);
								}
							}
							break;
						case "600": // 현재 접속한 유저 리스트를 받음
//							System.out.println(cm.getData());
							break;
						case "777":
							String a = cm.getData().substring(1, cm.getData().length()-1).replaceAll(" ","");
							user_list=a.split(",");
							break;
						case "999": // 코드가 999라면 채팅방 정보를 담고 있는 패널을 채팅방 목록에 추가함
							int len = chatRoomArea.getDocument().getLength();
							chatRoomArea.setCaretPosition(len); // place caret at the end (with no selection)
							chatRoomBox = new JPanel();
							chatRoomBox.setLayout(new BorderLayout(25,25));
							testLabel = new JLabel();
							testLabel.setFont(new Font("굴림체", Font.PLAIN, 14));
							testLabel.setText(cm.getData());
							JLabel borderLabel = new JLabel();
							borderLabel.setBorder(null);
							/* test code, 라벨의 위치 및 패널의 높이 변경해봄 */
							chatRoomBox.add(new JLabel(), BorderLayout.NORTH);
							chatRoomBox.add(new JLabel(), BorderLayout.EAST);
							chatRoomBox.add(testLabel, BorderLayout.CENTER);
							chatRoomBox.add(new JLabel(), BorderLayout.WEST);
							chatRoomBox.add(new JLabel(), BorderLayout.SOUTH);
							chatRoomBox.setBackground(Color.white);
							chatRoomBox.addMouseListener(new MouseListener() { // 채팅방 클릭 리스너
								@Override
								public void mouseClicked(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void mousePressed(MouseEvent e) { 
									// TODO Auto-generated method stub
									if (e.getClickCount()==2){ // 두번 클릭하면
										testchatviews.add(new JavaObjClientChatRoom(UserName, cm.getData(), testview)); // cm.getData()에는 채팅방 이름이 담겨 있고 해당 채팅방 띄우기
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
							chatRoomArea.insertComponent(chatRoomBox);
							chatRoomArea.replaceSelection("\n");
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
					} // catch문 끝
				} // 바깥 catch문끝

			}
		}
	}
	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
//			AppendText("SendObject Error");
		}
	}
	
	/* test code */
	public void SendRoomId(String room_id) { // 버튼 클릭 시 
		try {
			ChatMsg obcm = new ChatMsg(UserName, "999", room_id);
			oos.writeObject(obcm);
		} catch (IOException e) {
//			AppendText("SendObject Error");
		}
	}


}
