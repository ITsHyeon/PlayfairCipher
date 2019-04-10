import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class main {
	public static Object[][] key = new Object[5][5];
	public static String virtualkey, plaintext;

	public static void main(String[] args) {

		String decryption; // 암호화된 문자열
		String encryption; // 복호화된 문자열

		JFrame frame = new JFrame();

		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = frame.getContentPane();
		JPanel pane = new JPanel();
		// 가상키
		JTextField tfVirtualKey = new JTextField(15);
		JLabel lbVirtualKey = new JLabel("가상키 : ");

		// 평문
		JTextField tfPlainText = new JTextField(15);
		JLabel lbPlainText = new JLabel("평문 : ");

		// 암호판
		String title[] = { "1", "2", "3", "4", "5" };
		JTable tableKey = new JTable(key, title);
		JScrollPane scrollpane = new JScrollPane(tableKey);

		// 암호문
		JTextField tfPw = new JTextField(15);
		JLabel lbPw = new JLabel("암호문 : ");

		// 복호문
		JTextField tfDecryption = new JTextField(15);
		JLabel lbDecryption = new JLabel("복호문 : ");

		// 버튼
		JButton btpw = new JButton("암호화");
		JButton btDecryption = new JButton("복호화");

		// 암호판 설정
		tableKey.getTableHeader().setReorderingAllowed(false); // 이동 불가
		tableKey.getTableHeader().setResizingAllowed(false); // 크기 조절 불가
		scrollpane.setPreferredSize(new Dimension(500, 300));

		// 가상키 추가
		pane.add(lbVirtualKey);
		pane.add(tfVirtualKey);

		// 평문 추가
		pane.add(lbPlainText);
		pane.add(tfPlainText);

		// 암호판 추가
		pane.add(scrollpane);

		// 암호문 추가
		pane.add(lbPw);
		pane.add(tfPw);

		// 복호문 추가
		pane.add(lbDecryption);
		pane.add(tfDecryption);

		// 버튼 추가
		pane.add(btpw);
		pane.add(btDecryption);

		// 암호화
		btpw.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				virtualkey = tfVirtualKey.getText(); // 가상키 입력값
				plaintext = tfPlainText.getText();

				init();
			}
		});

		contentPane.add(pane);
		frame.setVisible(true);

	}

	public static void init() {
		System.out.println("가상키 : " + virtualkey + ", " + "평문 : " + plaintext);
		for(int i = 0; i < plaintext.length(); i++) {
			if(plaintext.charAt(i)==' ') {
				plaintext = plaintext.substring(0, i) + plaintext.substring(i + 1, plaintext.length());
				
			}
		}
	}

}
