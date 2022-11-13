
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.PublicKey;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.print.attribute.standard.JobOriginatingUserName;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.color.CMMException;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class JavaObjClientChatRoom extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel EmoLabel;
	private JTextField txtInput;
	private String UserName;
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
	private Socket socket; // �������
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private JLabel lblUserName;
	// private JTextArea textArea;
	private JTextPane textArea;
	
	public EmojiView emoji;
	public JavaObjClientChatRoom view;
	//frame��ġ
	public int frameX;
	public int frameY;
	public ImageIcon panelIMG;
		
	private Frame frame;
	private FileDialog fd;
	private JButton imgBtn;
	private JButton filebtn;
	private JButton emobtn;
	private JButton listbtn;

	/**
	 * Create the frame.
	 */
	public JavaObjClientChatRoom(String username, String ip_addr, String port_no) {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(186, 206, 224));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 352, 467);
		scrollPane.getViewport().setOpaque(false);
		

		ImageIcon close_img = new ImageIcon("src/closebtn.png");

		//�̸�Ƽ�� �г�
		EmoLabel = new JLabel() {
		    protected void paintComponent(Graphics g)
		    {
		        g.setColor(new Color(255, 255, 255, 90));
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		EmoLabel.setOpaque(false); 
		contentPane.add(EmoLabel);
		
		EmoLabel.setBounds(0, 393, 378, 90);
		
		
		
		EmoLabel.setOpaque(false);
		EmoLabel.setBackground(new Color(192, 192, 192, 80));		
				EmoLabel.setHorizontalAlignment(JLabel.RIGHT);
				contentPane.add(EmoLabel);
				EmoLabel.setVisible(false);
				JButton closebtn = new JButton(close_img);
				closebtn.setBounds(350, 0, 20, 20);
				closebtn.setBorder(BorderFactory.createEmptyBorder());
				//closebtn.setContentAreaFilled(false);
				closebtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						EmoLabel.setVisible(false);
						EmoLabel.repaint();
					}
				});
				EmoLabel.add(closebtn);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);

		textArea = new JTextPane();
		textArea.setEditable(true);
		textArea.setFont(new Font("����ü", Font.PLAIN, 14));
		textArea.setOpaque(false);
		scrollPane.setViewportView(textArea);

		txtInput = new JTextField();
		txtInput.setBounds(12, 490, 366, 40);
		txtInput.setBorder(null);
		contentPane.add(txtInput);
		txtInput.setColumns(10);

		btnSend = new JButton("\uC804\uC1A1");
		btnSend.setFont(new Font("����", Font.PLAIN, 12));
		btnSend.setBounds(305, 542, 61, 34);
		btnSend.setBackground(new Color(250, 218, 10));
		btnSend.setBorder(null);
		setHandCursor(btnSend);
		contentPane.add(btnSend);

		lblUserName = new JLabel("Name");
		lblUserName.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblUserName.setBackground(Color.WHITE);
		lblUserName.setFont(new Font("����", Font.BOLD, 14));
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setBounds(12, 539, 62, 40);
		// contentPane.add(lblUserName);
		setVisible(true);

		// AppendText("User " + username + " connecting " + ip_addr + " " + port_no);
		UserName = username;
		lblUserName.setText(username);

		
		JButton btnNewButton = new JButton("�� ��");
		btnNewButton.setFont(new Font("����", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatMsg msg = new ChatMsg(UserName, "400", "Bye");
				SendObject(msg);
				System.exit(0);
			}
		});
		btnNewButton.setBounds(220, 540, 69, 40);
		setHandCursor(btnNewButton);
		// contentPane.add(btnNewButton);
				
		
		// �̹��� ��ư ����
		ImageIcon emobtn_img = new ImageIcon("src/emoticon_btn.png");
		emobtn = new JButton(emobtn_img);
		emobtn.setBounds(10, 550, 25, 25);
		emobtn.setBorder(BorderFactory.createEmptyBorder());
		emobtn.setContentAreaFilled(false);		
		setHandCursor(emobtn);
		contentPane.add(emobtn);

		
		ImageIcon imgbtn_img = new ImageIcon("src/image_btn.png");
		imgBtn = new JButton(imgbtn_img);
		imgBtn.setBounds(45, 550, 25, 25);
		imgBtn.setBorder(BorderFactory.createEmptyBorder());
		imgBtn.setContentAreaFilled(false);
		setHandCursor(imgBtn);
		contentPane.add(imgBtn);
		
		ImageIcon filebtn_img = new ImageIcon("src/file_btn.png");
		filebtn = new JButton(filebtn_img);
		filebtn.setBounds(80, 550, 25, 25);
		filebtn.setBorder(BorderFactory.createEmptyBorder());
		filebtn.setContentAreaFilled(false);
		setHandCursor(filebtn);
		contentPane.add(filebtn);
		
		ImageIcon listbtn_img = new ImageIcon("src/list_btn.png");
		listbtn = new JButton(listbtn_img);
		listbtn.setBounds(115, 550, 25, 25);
		listbtn.setBorder(BorderFactory.createEmptyBorder());
		listbtn.setContentAreaFilled(false);
		setHandCursor(listbtn);
		contentPane.add(listbtn);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 479, 390, 123);
		panel.setBackground(Color.white);
		contentPane.add(panel);
		
		view=this;
				
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");
			SendObject(obcm);
			
			ListenNetwork net = new ListenNetwork();
			net.start();
			TextSendAction action = new TextSendAction();
			btnSend.addActionListener(action);
			txtInput.addActionListener(action);
			txtInput.requestFocus();
			ImageSendAction action2 = new ImageSendAction();
			imgBtn.addActionListener(action2);
			ListSendAction action3 = new ListSendAction(); 
			listbtn.addActionListener(action3);
			FileSendAction action4 = new FileSendAction();
			filebtn.addActionListener(action4);
			EmoticonSendAction action5 = new EmoticonSendAction();
			emobtn.addActionListener(action5);
			


		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppendText("connect error");
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
						AppendText(msg);
						break;				
					case "300": // Image ÷��
						AppendText("[" + cm.getId() + "]");
						AppendImage(cm.img);
						break;
					case "301": // ����Ŭ��
						AppendText("[" + cm.getId() + "]");
						AppendImage(cm.img);
						break;
					case "302": // �ѹ� Ŭ��
						panelIMG=cm.img;
						EmoLabel.setVisible(true);
						EmoLabel.setIcon(cm.img);
						EmoLabel.repaint();
						break;
					case "500":
						AppendText("[" + cm.getId() + "] " + cm.filename);
						AppendFile(cm.file, cm.filename);
						break;					
					}
				} catch (IOException e) {
					AppendText("ois.readObject() error");
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

	// keyboard enter key ġ�� ������ ����
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button�� �����ų� �޽��� �Է��ϰ� Enter key ġ��
			if (e.getSource() == btnSend || e.getSource() == txtInput) {
				String msg = null;
				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
				msg = txtInput.getText();
				if(msg.equals("(����)")) { // �ӽ�
					ChatMsg obcm = new ChatMsg(UserName, "300", "EMOTICON");
					obcm.setImg(icon1);
					SendObject(obcm);
				} else if(msg.equals("(����)")) {
					ChatMsg obcm = new ChatMsg(UserName, "300", "EMOTICON");
					obcm.setImg(icon2);
					SendObject(obcm);
				} else if(EmoLabel.isVisible()) {
					ChatMsg obcm = new ChatMsg(UserName, "300", "Emoji");
					obcm.setImg(panelIMG);
					SendObject(obcm);
					SendMessage(msg);
				}
				else {				
					SendMessage(msg);
				}
				txtInput.setText(""); // �޼����� ������ ���� �޼��� ����â�� ����.
				txtInput.requestFocus(); // �޼����� ������ Ŀ���� �ٽ� �ؽ�Ʈ �ʵ�� ��ġ��Ų��
				if (msg.contains("/exit")) // ���� ó��
					System.exit(0);
			}
		}
	}

	class ImageSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// �׼� �̺�Ʈ�� sendBtn�϶� �Ǵ� textField ���� Enter key ġ��
			if (e.getSource() == imgBtn) {
				frame = new Frame("�̹���÷��");
				fd = new FileDialog(frame, "�̹��� ����", FileDialog.LOAD);
				fd.setVisible(true);
				
				if(fd.getFile() == null) {
//					System.out.println("��ҵ�");
				} else {
					ChatMsg obcm = new ChatMsg(UserName, "300", "IMG");
					ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
					obcm.setImg(img);
					SendObject(obcm);
				}
				
			}
		}
	}
	
	
	
	class FileSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// �׼� �̺�Ʈ�� sendBtn�϶� �Ǵ� textField ���� Enter key ġ��
			if (e.getSource() == filebtn) {
				frame = new Frame("����÷��");
				fd = new FileDialog(frame, "���� ����", FileDialog.LOAD);
				fd.setVisible(true);
				/* ���� ���� ���� */
				if(fd.getFile() == null) {
//					System.out.println("��ҵ�");
				} else {
					ChatMsg obcm = new ChatMsg(UserName, "500", "FILE");
					File file = new File(fd.getDirectory() + fd.getFile()); // ���� ��� �� �̸�
					try {
						FileInputStream fis = new FileInputStream(file);
						byte b[] = new byte[fis.available()]; 
						fis.read(b); // ���� ���� ���� �б�
						obcm.setFile(b); // ���� ���� ���� ����
						obcm.setFilename(fd.getFile()); // ���� �̸� ����
						SendObject(obcm);
						fis.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	class ListSendAction implements ActionListener { // ����Ʈ ��ư Ŭ�� ������
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == listbtn) {
				ChatMsg obcm = new ChatMsg(UserName, "600", "LIST"); // 600 ����
				SendObject(obcm);
			}
		}
	}
	
	class EmoticonSendAction implements ActionListener { // �̸�Ƽ�� ��ư Ŭ�� ������
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == emobtn) {
				frameX=getBounds().x;
				frameY=getBounds().y;
				if(emoji==null) {
				emoji = new EmojiView(UserName, view);
				}
				else {
					emoji.dispose();
					emoji=null;
				}
			}
		}
	}
	
	ImageIcon icon1 = new ImageIcon("src/icon1.jpg");
	ImageIcon icon2 = new ImageIcon("src/icon2.jpg");
	private JPanel panel_1;


	public void AppendIcon(ImageIcon icon) {
		int len = textArea.getDocument().getLength();
		// ������ �̵�
		textArea.setCaretPosition(len);
		textArea.insertIcon(icon);
	}



	// ȭ�鿡 ���
	public void AppendText(String msg) {
		msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection(msg + "\n");
		
		
		// ���� ���ڿ����� �̸� �и�
		String[] msg_c = msg.split(" ");
		String username = msg_c[0];
		if(("["+this.UserName+"]").equals(username)) { // ���� ����� �ڽ��̶�� ������ ����
			alignLeft();
		} else { // ���� ����� Ÿ���̶�� ���� ����
			alignRight();
		}
	}

	public void alignLeft() {
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(right, Color.blue);
		doc.setParagraphAttributes(textArea.getSelectionStart(), textArea.getSelectionEnd(), right, false);
	}
	
	public void alignRight() {
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setForeground(left, Color.black);
		doc.setParagraphAttributes(textArea.getSelectionStart(), textArea.getSelectionEnd(), left, false);		
	}
	
	public void setHandCursor(JButton btn) { // ��ư�� Ŀ�� �ø� �� Ŀ�� �����ϴ� �޼ҵ�
		btn.getCursor();
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void AppendFile(byte[] file, String filename) { // ���� ����, ���� ���� ����, ���� �̸�
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		ImageIcon filebtn_img = new ImageIcon("src/filedown_btn.png"); 
		JButton file_yes = new JButton(filebtn_img); // ���� ���� �г��� �̹��� ��ư
		setHandCursor(file_yes);
		file_yes.setBorder(BorderFactory.createEmptyBorder());
		file_yes.setContentAreaFilled(false);

		JLabel file_name = new JLabel(filename);
		file_name.setFont(new Font("����ü", Font.BOLD, 14)); // ���� ���� �г��� ���� �̸�
			
		JPanel file_panel = new JPanel();
		file_panel.setBackground(Color.white);
		file_panel.add(file_name); // ���� �̸� �߰�
		file_panel.add(file_yes); // ���� �ٿ� ��ư �߰�

		String ext = filename.substring(filename.lastIndexOf(".")); // ���� Ȯ���� ȹ��
			
		file_yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ư Ŭ���� ���� �ٿ� Dialog ����
				try {
					frame = new Frame("���� ����");
					fd = new FileDialog(frame, "���� ������ ���丮", FileDialog.LOAD);
					fd.setVisible(true);
					// ���õ� ���͸��� �Էµ� ���� �̸����� ���� ���� ( Ȯ���� �߰� )
					if(fd.getFile() == null) {
//						System.out.println("��ҵ�");
					} else {
						File recvfile = new File(fd.getDirectory() +"[" + UserName + "] " + fd.getFile() + ext);
						FileOutputStream fos = new FileOutputStream(recvfile);
						fos.write(file);
						fos.flush();
						fos.close();						
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}						
			}
		});
			
		textArea.insertComponent(file_panel);
			
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
	}
	
	public void AppendImage(ImageIcon ori_icon) {
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		Image ori_img = ori_icon.getImage();
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		// Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 250 �������� ��ҽ�Ų��.
		if (width > 250 || height > 250) {
			if (width > height) { // ���� ����
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // ���� ����
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			textArea.insertIcon(new_icon);
		} else
			textArea.insertIcon(ori_icon);
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
	}

	// Windows ó�� message ������ ������ �κ��� NULL �� ����� ���� �Լ�
	public byte[] MakePacket(String msg) {
		byte[] packet = new byte[BUF_LEN];
		byte[] bb = null;
		int i;
		for (i = 0; i < BUF_LEN; i++)
			packet[i] = 0;
		try {
			bb = msg.getBytes("euc-kr");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		for (i = 0; i < bb.length; i++)
			packet[i] = bb[i];
		return packet;
	}

	// Server���� network���� ����
	public void SendMessage(String msg) {
		EmoLabel.setVisible(false);
		try {
			ChatMsg obcm = new ChatMsg(UserName, "200", msg);
			oos.writeObject(obcm);
		} catch (IOException e) {
			AppendText("oos.writeObject() error");
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}


	public void SendObject(Object ob) { // ������ �޼����� ������ �޼ҵ�
		EmoLabel.setVisible(false);
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			AppendText("SendObject Error");
		}
	}
}
