import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

import javax.print.StreamPrintService;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class main {
	public static Object[][] key = new Object[5][5]; // 암호키 테이블
	public static String virtualkey, plaintext; // 가상키, 평문
	public static int checkCount = 0;
	public static String check = "";
	public static String decryption; // 복호화된 문자열
	public static String encryption; // 암호화된 문자열
	public static boolean oddFlag = false; // 글자수 출력

	public static void main(String[] args) {


      JFrame frame = new JFrame();

      frame.setBounds(100, 100, 540, 250);
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
      DefaultTableModel model = new DefaultTableModel(key, title);
      JTable tableKey = new JTable(model);
      JScrollPane scrollpane = new JScrollPane(tableKey);

      // 암호문
      JTextField tfPw = new JTextField(15);
      JLabel lbPw = new JLabel("암호문 : ");
      
      // 암호판
      String title1[] = { "1", "2", "3", "4", "5" };
      DefaultTableModel model1 = new DefaultTableModel(key, title1);
      JTable tableKey1 = new JTable(model1);
      JScrollPane scrollpane1 = new JScrollPane(tableKey1);

      // 복호문
      JTextField tfDecryption = new JTextField(15);
      JLabel lbDecryption = new JLabel("복호문 : ");

      // 버튼
      JButton btpw = new JButton("암호화");
      JButton btDecryption = new JButton("복호화");

      // 암호판 설정
      tableKey1.getTableHeader().setReorderingAllowed(false); // 이동 불가
      tableKey1.getTableHeader().setResizingAllowed(false); // 크기 조절 불가
      scrollpane1.setPreferredSize(new Dimension(500, 105));

      // 가상키 추가
      pane.add(lbVirtualKey);
      pane.add(tfVirtualKey);

      // 평문 추가
      pane.add(lbPlainText);
      pane.add(tfPlainText);

      // 암호판 추가
      pane.add(scrollpane1);

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
            
            setBord();
            
            // JTable에 세팅
            for(int i = 0; i < key.length; i++) {
               for(int j = 0; j < key[i].length; j++) {
                  tableKey1.setValueAt(key[i][j], i, j);
               }
            }
            tableKey1.updateUI();
            
            // 암호문 textField에 세팅
            tfPw.setText(encryption());
            System.out.println("암호화된 문자열 : " + encryption() + ", " + tfPw.getText());
         }
      });
      
      // 복호화 
      btDecryption.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent arg0) {
        	 
            for(int i = 0; i < encryption.length(); i++) {
               if(encryption.charAt(i)==' ') // 공백제거
                  encryption = encryption.substring(0,i)+encryption.substring(i+1, encryption.length());
            }
            
            decryption = strDecryption();
            
            for(int i = 0; i < decryption.length(); i++) {
               if(check.charAt(i)=='1') {
                  decryption = decryption.substring(0,i)+ " " + decryption.substring(i,decryption.length());
               }
            }
            
            // 복호문 textField에 세팅
            tfDecryption.setText(decryption);
            
            System.out.println("복호화 된 문자열 : " + decryption + ", " + tfDecryption.getText());
         }
      });

      contentPane.add(pane);
      frame.setVisible(true);

   }

	// 복호화
	public static String strDecryption() {
		ArrayList<char[]> playFair = new ArrayList<char[]>(); // 바꾸기 전 쌍자암호를 저장할 곳
		ArrayList<char[]> decPlayFair = new ArrayList<char[]>(); // 바꾼 후의 쌍자암호 저장할 곳
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0; // 쌍자 암호 두 글자의 각각의 행,열 값
		String decStr = "";

		int lengthOddFlag = 1;

		for (int i = 0; i < encryption.length(); i += 2) {
			char[] tmpArr = new char[2];
			tmpArr[0] = encryption.charAt(i);
			tmpArr[1] = encryption.charAt(i + 1);
			playFair.add(tmpArr);
		}

		for (int i = 0; i < playFair.size(); i++) {
			char[] tmpArr = new char[2];
			for (int j = 0; j < key.length; j++) {
				for (int k = 0; k < key[j].length; k++) {
					if ((char) key[j][k] == playFair.get(i)[0]) {
						x1 = j;
						y1 = k;
					}
					if ((char) key[j][k] == playFair.get(i)[1]) {
						x2 = j;
						y2 = k;
					}
				}
			}
			if (x1 == x2) { // 행이 같은 경우 각각 바로 아래열 대입
				tmpArr[0] = (char) key[x1][(y1 + 4) % 5];
				tmpArr[1] = (char) key[x2][(y2 + 4) % 5];
			} else if (y1 == y2) { // 열이 같은 경우 각각 바로 옆 열 대입
				tmpArr[0] = (char) key[(x1 + 4) % 5][y1];
				tmpArr[1] = (char) key[(x2 + 4) % 5][y2];
			} else { // 행, 열 다른 경우 각자 대각선에 있는 곳
				tmpArr[0] = (char) key[x2][y1];
				tmpArr[1] = (char) key[x1][y2];
			}

			decPlayFair.add(tmpArr);

		}

		for (int i = 0; i < decPlayFair.size(); i++) { // 중복 문자열 돌려놓음
			if (i != decPlayFair.size() - 1 && decPlayFair.get(i)[1] == 'x'
					&& decPlayFair.get(i)[0] == decPlayFair.get(i + 1)[0]) {
				decStr += decPlayFair.get(i)[0];
			} else {
				decStr += decPlayFair.get(i)[0] + "" + decPlayFair.get(i)[1];
			}
		}

		for (int i = 0; i < check.length(); i++) { // z위치를 찾아서 q로 돌려놓음
			if (check.charAt(i) == '1')
				decStr = decStr.substring(0, i) + 'z' + decStr.substring(i + 1, decStr.length());
		}

		if (oddFlag)
			decStr = decStr.substring(0, decStr.length() - 1);

		return decStr;

	}

	// 암호화
	public static String encryption() {
		System.out.println("가상키 : " + virtualkey + ", " + "평문 : " + plaintext);

		for (int i = 0; i < plaintext.length(); i++) {
			if (plaintext.charAt(i) == ' ') {
				plaintext = plaintext.substring(0, i) + plaintext.substring(i + 1, plaintext.length());
				checkCount += 10;
			} else {
				checkCount += 0;
			}
			if (plaintext.charAt(i) == 'z') {
				plaintext = plaintext.substring(0, i) + 'q' + plaintext.substring(i + 1, plaintext.length());
				check += 1;
			} else {
				check += 0;
			}
		}

		return encryption = strEncryption();

	}

	public static String strEncryption() {
		ArrayList<char[]> playFair = new ArrayList<char[]>(); // 바꾸기 전 쌍자암호를 저장할 곳
		ArrayList<char[]> encPlayFair = new ArrayList<char[]>(); // 바꾼 후의 쌍자암호를 저장할 곳
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0; // 쌍자 암호 두 글자의 각각의 행,열 값
		String encStr = "";

		for (int i = 0; i < plaintext.length(); i += 2) { // arraylist 세팅
			char[] tmpArr = new char[2];
			tmpArr[0] = plaintext.charAt(i);
			try {
				if (plaintext.charAt(i) == plaintext.charAt(i + 1)) { // 글이 반복되면 x추가
					tmpArr[1] = 'x';
					i--;
				} else {
					tmpArr[1] = plaintext.charAt(i + 1);
				}
			} catch (StringIndexOutOfBoundsException e) {
				tmpArr[1] = 'x';
				oddFlag = true;
			}
			playFair.add(tmpArr);
		}

		for (int i = 0; i < playFair.size(); i++) {
			System.out.println(playFair.get(i)[0] + "" + playFair.get(i)[1] + " ");
		}
		System.out.println();

		for (int i = 0; i < playFair.size(); i++) {
			char[] tmpArr = new char[2];
			for (int j = 0; j < key.length; j++) { // 쌍자암호의 각각 위치체크
				for (int k = 0; k < key[j].length; k++) {
					if ((char) key[j][k] == playFair.get(i)[0]) {
						x1 = j;
						y1 = k;
					}
					if ((char) key[j][k] == playFair.get(i)[1]) {
						x2 = j;
						y2 = k;
					}
				}
			}

			if (x1 == x2) { // 행이 같은 경우 각각 바로 아래열 대입
				tmpArr[0] = (char) key[x1][(y1 + 1) % 5];
				tmpArr[1] = (char) key[x2][(y2 + 1) % 5];
			} else if (y1 == y2) { // 열이 같은 경우 각각 바로 옆 열 대입
				tmpArr[0] = (char) key[(x1 + 1) % 5][y1];
				tmpArr[1] = (char) key[(x2 + 1) % 5][y2];
			} else { // 행, 열 다른 경우 각각 대각선에 있는 곳
				tmpArr[0] = (char) key[x2][y1];
				tmpArr[1] = (char) key[x1][y2];
			}

			encPlayFair.add(tmpArr);
		}
		for (int i = 0; i < encPlayFair.size(); i++) {
			encStr += encPlayFair.get(i)[0] + "" + encPlayFair.get(i)[1] + " ";
		}

		return encStr;
	}

	public static void setBord() {
		String keyForSet = ""; // 중복된 문자가 제거된 문자열을 저장할 문자열
		boolean flag = false; // 문자 중복을 체크하기 위한 flag 변수
		int lengthcount = 0; // key에 keyForSet을 넣기 위한 count변수

		virtualkey += "abcdefghijklmnopqrstuvwxyz"; // 키에 모든 알파벳을 추가

		// 중복처리
		for (int i = 0; i < virtualkey.length(); i++) {
			for (int j = 0; j < keyForSet.length(); j++) {
				if (virtualkey.charAt(i) == keyForSet.charAt(j)) {
					flag = true;
					break;
				}
			}
			if (!(flag))
				keyForSet += virtualkey.charAt(i);
			flag = false;
		}
		// 배열에 대입
		for (int i = 0; i < key.length; i++) {
			for (int j = 0; j < key[i].length; j++) {
				key[i][j] = keyForSet.charAt(lengthcount++);
			}
		}

		// 배열에 대입
		for (int i = 0; i < key.length; i++) {
			for (int j = 0; j < key[i].length; j++) {
				System.out.print(key[i][j] + "-");
			}
			System.out.println();
		}
	}

}

