
//Created for SeBn Mezdra from Miroslav Vencislavov Shilov

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.*;

public class SplacesLabels extends JFrame {

	static StringBuilder sNulite;

	static String varNumber;

	static String settComPort, setPath;

	static String prgWride1;

	static int PAKETAJ;

	static Boolean printingKraen;

	private static final long serialVersionUID = 1L;

	static Enumeration<?> portList = null;

	private static final String newLine = System.getProperty("line.separator");

	private synchronized static void writeToFile(String msg) {

		// Zapisva statistika v C:\

		String fileName = "C:\\LCheck\\Statistika\\STATISTIC.txt";
		PrintWriter printWriter = null;
		File file = new File(fileName);
		try {
			if (!file.exists())
				file.createNewFile();
			printWriter = new PrintWriter(new FileOutputStream(fileName, true));
			printWriter.write(newLine + msg);
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}

	

	static OutputStream outputStream, comPort;
	
	int countData, packCounter, globalCounter, counterHarness, pack_counter = 0;
	
    String nfoForPassField1, DateToStr, passContainer, maoInfoFromField, OK_LABEL_toString, SHIPPING_LABEL_toString;

	StringBuilder nfoForPassField, SHIPPING_LABEL, OK_LABEL;

	Scanner scanEti, scanEtiKraen, scanPath;

    Date date;

	String[] repeatedTaileNumContainer = new String[10000];

	JButton exitButton, printButton, logInButton, logOutButton, okButtonButton, clearButton, button1,
			button2, button3, button4, button5, button6, button7, button8,
			button9, button0, printHalvePackButton;

    JFrame passFrame, mainFrame, noDataBaseframeError, differentBarcodeFrameError;

    JTextField userField, kasaField, passField, infoPassField, statusField,
			harnessField, statusPaketaj, maoField;

    String[] listProgramsName;
	

	SplacesLabels() {
		super("Spoiki");

		nfoForPassField = new StringBuilder();

		passFrame = new JFrame("¬⁄¬≈ƒ» œ≈–—ŒÕ¿À≈Õ ÕŒÃ≈–");
		passFrame.setLayout(null);
		passFrame.setSize(400, 400);
		passFrame.setResizable(false);
		passFrame.setLocationRelativeTo(null);
		passFrame.setVisible(true);
		passFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		passFrame.setAlwaysOnTop(true);
		passFrame.setContentPane(new JLabel(new ImageIcon(
				"C:\\LCheck\\icons\\passFrame.png")));

		passField = new JTextField();
		passField.setFont(new Font("SansSerif", Font.BOLD, 30));

		passFrame.add(passField);

		passField.setBounds(100, 20, 200, 50);
		passField.requestFocus();

		logInButton = new JButton("OK");
		logInButton.setVisible(true);
		logInButton.addActionListener(new okPass());
		logInButton.setFont(new Font("SansSerif", Font.ITALIC, 30));
		logInButton.setBackground(Color.GREEN);

		passFrame.add(logInButton);

		logInButton.setBounds(280, 100, 100, 80);

		clearButton = new JButton("CLEAR");

		passFrame.add(clearButton);

		clearButton.setBounds(280, 190, 100, 80);
		clearButton.addActionListener(new ClearPass());
		clearButton.setFont(new Font("SansSerif", Font.ITALIC, 20));

		button1 = new JButton("1");

		passFrame.add(button1);

		button1.setBounds(10, 100, 80, 80);
		button1.addActionListener(new Button1());
		button1.setFont(new Font("SansSerif", Font.ITALIC, 50));

		button2 = new JButton("2");

		passFrame.add(button2);

		button2.setBounds(100, 100, 80, 80);
		button2.addActionListener(new Button2());
		button2.setFont(new Font("SansSerif", Font.ITALIC, 50));

		button3 = new JButton("3");

		passFrame.add(button3);

		button3.setBounds(190, 100, 80, 80);
		button3.addActionListener(new Button3());
		button3.setFont(new Font("SansSerif", Font.ITALIC, 50));

		button4 = new JButton("4");

		passFrame.add(button4);

		button4.setBounds(10, 190, 80, 80);
		button4.addActionListener(new Button4());
		button4.setFont(new Font("SansSerif", Font.ITALIC, 50));

		button5 = new JButton("5");

		passFrame.add(button5);

		button5.setBounds(100, 190, 80, 80);
		button5.addActionListener(new Button5());
		button5.setFont(new Font("SansSerif", Font.ITALIC, 50));

		button6 = new JButton("6");

		passFrame.add(button6);

		button6.setBounds(190, 190, 80, 80);
		button6.addActionListener(new Button6());
		button6.setFont(new Font("SansSerif", Font.ITALIC, 50));

		button7 = new JButton("7");

		passFrame.add(button7);

		button7.setBounds(10, 280, 80, 80);
		button7.addActionListener(new Button7());
		button7.setFont(new Font("SansSerif", Font.ITALIC, 50));

		button8 = new JButton("8");

		passFrame.add(button8);

		button8.setBounds(100, 280, 80, 80);
		button8.addActionListener(new Button8());
		button8.setFont(new Font("SansSerif", Font.ITALIC, 50));

		button9 = new JButton("9");

		passFrame.add(button9);

		button9.setBounds(190, 280, 80, 80);
		button9.addActionListener(new Button9());
		button9.setFont(new Font("SansSerif", Font.ITALIC, 50));

		button0 = new JButton("0");

		passFrame.add(button0);

		button0.setBounds(280, 280, 80, 80);
		button0.addActionListener(new Button0());
		button0.setFont(new Font("SansSerif", Font.ITALIC, 50));

		// Pravi vazmojno vavejdaneto s enter
		passFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW

		).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");

		passFrame.getRootPane().getActionMap().put("clickButton",
				new AbstractAction() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					/**
				 	 * 
				 	 */

					public void actionPerformed(ActionEvent ae) {
						logInButton.doClick();
						System.out.println("button clicked");
					}
				});

	}

	public class logOut implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			logInButton.setBackground(Color.GREEN);

			passFrame.setVisible(true);
			mainFrame.setVisible(false);

		}

	}

	// Izpalnenie na mainFrame
	public class okPass implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			// Vzema path kum
			// programite......................................................

			File filePath = new File("C:\\LCheck\\PATH.txt");

			try {
				scanPath = new Scanner(filePath);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (scanPath.hasNextLine()) {

				setPath = scanPath.next();
			}

			// Inicializira bazata danni ot programi
			// ////////////////////////////////////////////////////
			File pathToDataBase = new File(setPath);

			listProgramsName = pathToDataBase.list();

			for (int i = 0; i <= listProgramsName.length - 1; i++) {

				// System.out.println(listProgramsName[i]);
			}

			passContainer = passField.getText().trim();

			File passwordFile = new File("C:\\LCheck\\PWD.txt");

			Scanner passwordFileScan = null;
			try {
				passwordFileScan = new Scanner(passwordFile, "windows-1251");
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			boolean isPasswordAccept = false;

			while (passwordFileScan.hasNextLine()) {

				if (passContainer.equals(passwordFileScan.nextLine().trim())) {
					isPasswordAccept = true;
					logInButton.setBackground(Color.GREEN);
					passContainer = String.valueOf(passField.getText());

				} else {
					logInButton.setBackground(Color.RED);
				}
			}

			if (isPasswordAccept) {

				nfoForPassField = new StringBuilder();

				// Inicializacia na etiketa ot baza danni C//LCheck

				File etiFile = new File("C:\\LCheck\\LCheck_OK.txt");

				try {
					scanEti = new Scanner(etiFile, "windows-1252");
				} catch (FileNotFoundException e1) {

					System.out.println("Nqma etiket");
				}

				OK_LABEL = new StringBuilder();
				while (scanEti.hasNextLine()) {

					OK_LABEL.append(scanEti.nextLine());

				}
				// preobrazuvane na etiketa v String

				OK_LABEL_toString = String.valueOf(OK_LABEL);

			
				passField.setText("");

				// Iniciailzacia na kraen etiket

				File etiFile1 = new File("C:\\LCheck\\LCheck_KRAEN.txt");

				try {
					scanEtiKraen = new Scanner(etiFile1, "windows-1252");
				} catch (FileNotFoundException e1) {

					// System.out.println("Nqma etiket");
				}

				SHIPPING_LABEL = new StringBuilder();
				while (scanEtiKraen.hasNextLine()) {

					SHIPPING_LABEL.append(scanEtiKraen.nextLine());

				}

				// preobrazuvane na etiketa v String

				SHIPPING_LABEL_toString = String.valueOf(SHIPPING_LABEL);

				
				passField.setText("");

				passFrame.setVisible(false);

				infoPassField = new JTextField();

				mainFrame = new JFrame("LCheck");
				mainFrame.setLayout(null);
				mainFrame.setSize(1024, 768);
				mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setContentPane(new JLabel(new ImageIcon(
						"C:\\LCheck\\icons\\mainFrame.png")));

				statusField = new JTextField();

				mainFrame.add(statusField);

				statusField.setBounds(100, 20, 800, 80);
				statusField.setEditable(false);
				statusField.setFont(new Font("SansSerif", Font.CENTER_BASELINE,
						35));
				statusField.setOpaque(false);

				// ///////////////////////////////////////////////////////buton
				// za printirane na polovin paketaj

				printHalvePackButton = new JButton("œŒÀŒ¬»Õ œ¿ ≈“¿∆");

				mainFrame.add(printHalvePackButton);
				printHalvePackButton.setBounds(360, 605, 300, 40);
				printHalvePackButton
						.addActionListener(new PrintiraiPolovinPaketaj());

				JLabel kasaLabel = new JLabel("— ¿Õ»–¿… ¡¿– Œƒ Õ¿  ¿—¿“¿");

				JLabel harnessLabel = new JLabel("— ¿Õ»–¿… ¡¿– Œƒ Õ¿  ŒÃœÀ≈ “¿");

				JLabel unikalenLabel = new JLabel(
						"— ¿Õ»–¿… ¡¿– Œƒ — ”Õ» ¿À≈Õ ÕŒÃ≈–");

				mainFrame.add(kasaLabel);

				kasaLabel.setBounds(350, 100, 500, 70);

				mainFrame.add(harnessLabel);

				harnessLabel.setBounds(330, 72, 500, 375);

				kasaLabel.setFont(new Font("SansSerif", Font.ITALIC, 20));

				harnessLabel.setFont(new Font("SansSerif", Font.ITALIC, 20));

				mainFrame.add(unikalenLabel);

				unikalenLabel.setBounds(310, 350, 500, 70);

				unikalenLabel.setFont(new Font("SansSerif", Font.ITALIC, 20));

				JLabel java = new JLabel("BUILT ON ECLIPSE");
				java.setFont(new Font("SansSerif", Font.BOLD, 12));
				mainFrame.add(java);
				java.setBounds(900, 695, 200, 50);

				mainFrame.add(infoPassField);

				infoPassField.setEditable(false);
				infoPassField.setBounds(415, 640, 170, 80);
				infoPassField.setFont(new Font("SansSerif", Font.BOLD, 55));
				infoPassField.setBackground(Color.ORANGE);
				infoPassField.setText(passContainer);
				infoPassField.setOpaque(false);
				infoPassField.setBorder(null);

				logOutButton = new JButton("LOGOUT");
				logOutButton.setFont(new Font("SansSerif", Font.BOLD, 40));
				logOutButton.setBounds(15, 550, 250, 150);
				logOutButton.setVisible(true);
				logOutButton.addActionListener(new logOut());

				mainFrame.add(logOutButton);

				printButton = new JButton("œ–»Õ“»–¿…");
				printButton.setFont(new Font("SansSerif", Font.BOLD, 45));
				printButton.setBounds(310, 500, 400, 100);
				printButton.addActionListener(new Printing());

				mainFrame.add(printButton);

				exitButton = new JButton("»«’Œƒ");
				exitButton.setFont(new Font("SansSerif", Font.BOLD, 40));
				exitButton.setBounds(750, 550, 250, 150);
				exitButton.addActionListener(new Exit());

				mainFrame.add(exitButton);

				kasaField = new JTextField();

				mainFrame.add(kasaField);

				kasaField.setBounds(220, 150, 600, 100);
				kasaField.setFont(new Font("SansSerif", Font.BOLD, 70));

				Icon thatIcon = new ImageIcon(
						"C:\\LCheck\\icons\\kasaField.png");

				Border thatBorder1 = new MatteBorder(10, 10, 10, 10, thatIcon);
				Border thatBorder2 = new TitledBorder(thatBorder1,
						"Barcode Scan");

				Font font = new Font("Serif", Font.ITALIC, 12);

				Border thatBorder = new TitledBorder(thatBorder2, "",
						TitledBorder.LEFT, TitledBorder.ABOVE_BOTTOM, font,
						Color.red);

				kasaField.setBorder(thatBorder);

				mainFrame.setVisible(true);

				kasaField.requestFocus();

				harnessField = new JTextField();

				mainFrame.add(harnessField);

				harnessField.setBorder(thatBorder);

				harnessField.setFont(new Font("SansSerif", Font.BOLD, 70));

				harnessField.setBounds(220, 270, 600, 100);

				// pole za rabota s tretia barkod dobaven na 02.08.2016

				maoField = new JTextField();

				mainFrame.add(maoField);

				maoField.setBorder(thatBorder);

				maoField.setFont(new Font("SansSerif", Font.BOLD, 70));

				maoField.setBounds(220, 400, 600, 100);

				statusField.setBorder(null);

				statusPaketaj = new JTextField();

				mainFrame.add(statusPaketaj);

				statusPaketaj.setBounds(340, 80, 600, 45);
				statusPaketaj.setFont(new Font("SansSerif", Font.BOLD, 20));
				statusPaketaj.setOpaque(false);
				statusPaketaj.setBorder(null);
				statusPaketaj.setEditable(false);

				// Pravi vazmojno vavejdaneto s enter
				mainFrame.getRootPane().getInputMap(
						JComponent.WHEN_IN_FOCUSED_WINDOW

				)
						.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0),
								"clickButton");

				mainFrame.getRootPane().getActionMap().put("clickButton",
						new AbstractAction() {
							/**
												 * 
												 */
							private static final long serialVersionUID = 1L;

							/**
			                    			 	 * 
			                    			 	 */

							public void actionPerformed(ActionEvent ae) {

								printButton.doClick();

							}
						});

				mainFrame.getRootPane().getInputMap(
						JComponent.WHEN_IN_FOCUSED_WINDOW

				).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),
						"clickButton");

				mainFrame.getRootPane().getActionMap().put("clickButton",
						new AbstractAction() {
							/**
												 * 
												 */
							private static final long serialVersionUID = 1L;

							/**
			                    			 	 * 
			                    			 	 */

							public void actionPerformed(ActionEvent ae) {

								printButton.doClick();

							}
						});
			}

		}

	}

	// klasove s komandite na botoniite na menu parola
	public class Button1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			nfoForPassField.append("1");

			nfoForPassField1 = String.valueOf(nfoForPassField);

			passField.setText(nfoForPassField1);
		}

	}

	public class Button2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			nfoForPassField.append("2");

			nfoForPassField1 = String.valueOf(nfoForPassField);

			passField.setText(nfoForPassField1);
		}

	}

	public class Button3 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			nfoForPassField.append("3");

			nfoForPassField1 = String.valueOf(nfoForPassField);

			passField.setText(nfoForPassField1);
		}

	}

	public class Button4 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			nfoForPassField.append("4");

			nfoForPassField1 = String.valueOf(nfoForPassField);

			passField.setText(nfoForPassField1);
		}

	}

	public class Button5 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			nfoForPassField.append("5");

			nfoForPassField1 = String.valueOf(nfoForPassField);

			passField.setText(nfoForPassField1);
		}

	}

	public class Button6 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			nfoForPassField.append("6");

			nfoForPassField1 = String.valueOf(nfoForPassField);

			passField.setText(nfoForPassField1);
		}

	}

	public class Button7 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			nfoForPassField.append("7");

			nfoForPassField1 = String.valueOf(nfoForPassField);

			passField.setText(nfoForPassField1);
		}

	}

	public class Button8 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			nfoForPassField.append("8");

			nfoForPassField1 = String.valueOf(nfoForPassField);

			passField.setText(nfoForPassField1);
		}

	}

	public class Button9 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			nfoForPassField.append("9");

			nfoForPassField1 = String.valueOf(nfoForPassField);

			passField.setText(nfoForPassField1);
		}

	}

	public class Button0 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			nfoForPassField.append("0");

			nfoForPassField1 = String.valueOf(nfoForPassField);

			passField.setText(nfoForPassField1);
		}

	}

	public class ClearPass implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			nfoForPassField = new StringBuilder();

			logInButton.setBackground(Color.GREEN);

			passField.setText("");
		}

	}

	public class errMess implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			noDataBaseframeError.setVisible(false);

			kasaField.requestFocus();
			kasaField.setText("");

			harnessField.setText("");
			maoField.setText("");

		}

	}

	public class Exit implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.exit(getDefaultCloseOperation());

		}

	}

	public class Printing implements ActionListener {

		Enumeration<?> portList = null;

		CommPortIdentifier portId;

		SerialPort serialPort;

		OutputStream outputStream;

		String kraen;

		int index = 1000;

		Scanner scanSetting;

		public void actionPerformed(ActionEvent e) {

			// razre6enie za printirane na kraen

			printingKraen = false;

			// Aktualna data i 4as

			date = new Date();

			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy  HH:mm");

			DateToStr = format.format(date);
			// System.out.println(DateToStr);

			String harnessInfo = harnessField.getText().trim();
			String kasaFIeld = kasaField.getText().trim();

			if (harnessInfo.equals(kasaFIeld)) {

				// Vzema nastroika ot Setting.txt

				File fileSetting = new File("C:\\LCheck\\SETTING.txt");

				try {
					scanSetting = new Scanner(fileSetting);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while (scanSetting.hasNextLine()) {

					settComPort = scanSetting.next();
				}

				noDataBaseframeError = new JFrame("ERROR");
				noDataBaseframeError.setLayout(null);
				noDataBaseframeError.setSize(300, 200);
				noDataBaseframeError.setAlwaysOnTop(true);
				noDataBaseframeError.setLocationRelativeTo(null);

				JLabel label = new JLabel(
						"ÕˇÏ‡ Ï‡ÚÂË‡Î ‚ ·‡Á‡ ‰‡ÌÌË ËÎË ÌÂ ÒÚÂ ÒÍ‡ÌË‡ÎË ÛÌËÍ‡ÎÂÌ ·‡ÍÓ‰");

				label.setFont(new Font("SansSerif", Font.BOLD, 14));
				label.setBounds(10, 20, 50, 30);
				label.setSize(500, 100);

				noDataBaseframeError.add(label);

				okButtonButton = new JButton("OK");

				noDataBaseframeError.add(okButtonButton);
				noDataBaseframeError.setBounds(500, 450, 520, 200);

				okButtonButton.setBounds(130, 100, 250, 50);
				okButtonButton.addActionListener(new errMess());

				// Dobavqne na ikona v frame

				ImageIcon img = new ImageIcon("C:\\LCheck\\icons\\difFrame.png");

				JPanel panel = new JPanel();

				panel.add(new JLabel(img));

				noDataBaseframeError.add(panel);

				panel.setOpaque(false);
				panel.setBounds(230, 10, 50, 50);

				// Pravi vazmojno vavejdaneto s enter

				noDataBaseframeError.getRootPane().getInputMap(
						JComponent.WHEN_IN_FOCUSED_WINDOW

				).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
						"clickButton");

				noDataBaseframeError.getRootPane().getActionMap().put("clickButton",
						new AbstractAction() {
							/**
						 * 
						 */
							private static final long serialVersionUID = 1L;

							/**
					 	 * 
					 	 */

							public void actionPerformed(ActionEvent ae) {
								okButtonButton.doClick();
								System.out.println("button clicked");
							}
						});
				String pathToFile = null;

				StringBuilder prgWride = new StringBuilder();

				String tileNomer = String.valueOf(kasaField.getText().trim()
						+ ".prg");

				// System.out.println(tileNomer);

				// tuk sse pravi proverka za nali4ie v baza danni

				maoInfoFromField = maoField.getText().trim();

				System.out.println("mao nomera za sravnenie" + maoInfoFromField);

				System.out.println("tile nomera za sravnenie" + tileNomer);

				boolean key;
				if (harnessInfo.equals(maoInfoFromField)) {
					key = false;
				} else {
					key = true;
					kasaField.requestFocus();
					kasaField.setText("");
					harnessField.setText("");
					maoField.setText("");
				}

				for (int i = 0; i <= listProgramsName.length - 1; i++) {

					String temp1 = listProgramsName[i];

					// System.out.println(listProgramsName[i]);

					// VAJNOOOOOOOOOOOOOO///// !!!!!!!!!!!!!!!!!!!!!pravi
					// proverka za razli4ie v etiketite

					if (tileNomer.equals(temp1) && (maoInfoFromField.length() > 0)
							&& key) {

						pathToFile = temp1;

						// System.out.println("pathToFile====="+pathToFile);

						// Ako nqma material va baza danni izliza tazi error
						// ramka...
						// Zatvarqne na potoka za printera.....

					} else {

						kasaField.requestFocus();
						kasaField.setText("");
						harnessField.setText("");
						maoField.setText("");
					}

				}
				// Otvarqne na programata i zapisa na redovete v StringBuilder

				Scanner scanPrg = null;

				File openPrg = new File(setPath + pathToFile);

				try {
					scanPrg = new Scanner(openPrg);
				} catch (FileNotFoundException e1) {
					noDataBaseframeError.setVisible(true);
					e1.printStackTrace();
				}

				System.out.println(openPrg);
				while (scanPrg.hasNextLine()) {

					prgWride.append(scanPrg.nextLine());
				}
				prgWride1 = String.valueOf(prgWride).trim();

				System.out.println(prgWride1);

				String TAB = null;
				String ZDAT = null;
				String KMCHANGES = null;

				String Var6 = null;
				String PLACE = null;
				String Var7 = null;
				String Var8 = null;
				String Var9 = null;
				String Var10 = null;

				varNumber = prgWride1.substring(prgWride.indexOf("@Var1:") + 6,
						prgWride1.indexOf("<1>")).trim();
				TAB = prgWride1.substring(prgWride1.indexOf("@Var2:") + 6,
						prgWride1.indexOf("<2>")).trim();
				ZDAT = prgWride1.substring(prgWride1.indexOf("@Var3:") + 6,
						prgWride1.indexOf("<3>")).trim();
				KMCHANGES = prgWride1.substring(
						prgWride1.indexOf("@Var4:") + 6,
						prgWride1.indexOf("<4>")).trim();
				PAKETAJ = Integer.valueOf(prgWride1.substring(
						prgWride1.indexOf("@PAKETAJ=") + 9,
						prgWride1.indexOf("<P>")).trim());
				PLACE = prgWride1.substring(prgWride1.indexOf("@Var5:") + 6,
						prgWride1.indexOf("<5>")).trim();

				Var6 = prgWride1.substring(prgWride1.indexOf("@Var6:") + 6,
						prgWride1.indexOf("<6>")).trim();
				Var7 = prgWride1.substring(prgWride1.indexOf("@Var7:") + 6,
						prgWride1.indexOf("<7>")).trim();
				Var8 = prgWride1.substring(prgWride1.indexOf("@Var8:") + 6,
						prgWride1.indexOf("<8>")).trim();
				Var9 = prgWride1.substring(prgWride1.indexOf("@Var9:") + 6,
						prgWride1.indexOf("<9>")).trim();
				Var10 = prgWride1.substring(prgWride1.indexOf("@Var10:") + 6,
						prgWride1.indexOf("<10>")).trim();

				System.out.println(varNumber);
				System.out.println(TAB);
				System.out.println(ZDAT);
				System.out.println(KMCHANGES);
				System.out.println(PAKETAJ);
				System.out.println(PLACE);

				kasaField.requestFocus();

				// Broia4 na paketaj i komplekti

				repeatedTaileNumContainer[globalCounter] = tileNomer;

				globalCounter++;

				repeatedTaileNumContainer[globalCounter] = tileNomer;

				if (repeatedTaileNumContainer[0].equals(repeatedTaileNumContainer[globalCounter])) {

					packCounter++;

					counterHarness++;

				} else {
					globalCounter = 0;

					packCounter = 1;

					counterHarness = 1;

					pack_counter = 0;

				}

				statusPaketaj.setText("œ¿ ≈“¿∆=" + PAKETAJ
						+ "       ¡–Œ… œ¿ ≈“¿∆»=" + pack_counter);
				statusField.setText("¬¿–»¿Õ“=" + varNumber + "       "
						+ " ŒÃœÀ≈ “»=" + counterHarness);

				if (PAKETAJ == packCounter) {

					printingKraen = true;
				}

				// zapisva statistika
				// //////////////////Statistika///////////Statistika

				writeToFile("Original number of harness=" + tileNomer + "    "
						+ "TaileNumber=" + varNumber + "   " + passContainer + "   "
						+ DateToStr + "   " + "SpecialInfo=" + maoInfoFromField);

				// Zamenq reda na user i tile nomera v OK etiketa s realnia ot
				// programata

				String ETI = OK_LABEL_toString.replace("@USER", passContainer);
				String ETI1 = ETI.replace("@Var1", varNumber);
				String ETI2 = ETI1.replace("@Var2", TAB);
				String ETI3 = ETI2.replace("@Var3", ZDAT);
				String ETI4 = ETI3.replace("@Var4", KMCHANGES);
				String ETI5 = ETI4.replace("@Var5", PLACE);
				String ETI6 = ETI5.replace("@Var6", Var6);
				String ETI7 = ETI6.replace("@Var7", Var7);
				String ETI8 = ETI7.replace("@Var8", Var8);
				String ETI9 = ETI8.replace("@Var9", Var9);
				String ETI10 = ETI9.replace("@Var10", Var10);
				String ETI11 = ETI10.replace("@DATE", DateToStr);
				String ETI12 = ETI11.replace("@MAONUMBER", maoInfoFromField);
				System.out.println(ETI12);

				printHalvePackButton.setVisible(true);

				portList = CommPortIdentifier.getPortIdentifiers();

				while (portList.hasMoreElements()) {
					portId = (CommPortIdentifier) portList.nextElement();
					if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
						if (portId.getName().equals(settComPort)) {
							// if (portId.getName().equals("/dev/term/a")) {

							try {
								serialPort = (SerialPort)

								portId.open("SimpleWriteApp", 2000);
							} catch (PortInUseException a) {
							}

							try {
								outputStream = serialPort.getOutputStream();
							} catch (IOException a) {
							}

							try {
								serialPort.setSerialPortParams(9600,
										SerialPort.DATABITS_8,
										SerialPort.STOPBITS_2,
										SerialPort.PARITY_NONE);
							} catch (UnsupportedCommOperationException a) {
							}

							try {
								outputStream.write(ETI12.getBytes());

							} catch (IOException a) {
							}

							serialPort.close();

							try {
								outputStream.close();

							} catch (IOException a) {

								a.printStackTrace();
							}

						}

					}
				}
				kasaField.requestFocus();
				kasaField.setText("");
				harnessField.setText("");
				maoField.setText("");

				// Printirane na kraen etiket
				if (printingKraen) {
					printHalvePackButton.setVisible(false);
					String Paketaj = prgWride1.substring(
							prgWride1.indexOf("@PAKETAJ=") + 9,
							prgWride1.indexOf("<P>")).trim();

					// Zamenq reda na user i tile nomera v KRAEN etiketa s
					// realnia ot programata
					String ETI_Kraen = SHIPPING_LABEL_toString.replace("@USER", passContainer);
					String ETI_Kraen1 = ETI_Kraen.replace("@Var1", varNumber);
					String ETI_Kraen2 = ETI_Kraen1.replace("@COUNTER", Paketaj);

					pack_counter++;

					packCounter = 0;

					System.out.println(ETI_Kraen2);

					statusPaketaj.setText("œ¿ ≈“¿∆=" + PAKETAJ
							+ "       ¡–Œ… œ¿ ≈“¿∆»=" + pack_counter);

					portList = CommPortIdentifier.getPortIdentifiers();

					while (portList.hasMoreElements()) {
						portId = (CommPortIdentifier) portList.nextElement();
						if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
							if (portId.getName().equals(settComPort)) {
								// if (portId.getName().equals("/dev/term/a")) {

								try {
									serialPort = (SerialPort)

									portId.open("SimpleWriteApp", 2000);
								} catch (PortInUseException a) {
								}

								try {
									outputStream = serialPort.getOutputStream();
								} catch (IOException a) {
								}

								try {
									serialPort.setSerialPortParams(9600,
											SerialPort.DATABITS_8,
											SerialPort.STOPBITS_2,
											SerialPort.PARITY_NONE);
								} catch (UnsupportedCommOperationException a) {
								}

								try {
									outputStream.write(ETI_Kraen2.getBytes());
									outputStream.flush();
								} catch (IOException a) {
								}
								// System.out.println(serialPort);

								serialPort.close();

							}

						}
					}

					try {
						outputStream.close();

					} catch (IOException a) {

						a.printStackTrace();
					}

				}

			} else {

				differentBarcodeFrameError = new JFrame("ERROR");

				differentBarcodeFrameError.setLayout(null);

				differentBarcodeFrameError.setSize(300, 200);

				differentBarcodeFrameError.setAlwaysOnTop(true);

				differentBarcodeFrameError.setLocationRelativeTo(null);

				JLabel label1 = new JLabel("≈“» ≈“»“≈ —¿ –¿«À»◊Õ» !!!");
				label1.setFont(new Font("SansSerif", Font.BOLD, 18));

				label1.setBounds(10, 20, 50, 30);

				label1.setSize(300, 100);

				differentBarcodeFrameError.add(label1);
				okButtonButton = new JButton("OK");

				differentBarcodeFrameError.add(okButtonButton);

				differentBarcodeFrameError.setBounds(500, 450, 300, 200);

				// Dobavqne na ikona v difFrame
				ImageIcon img = new ImageIcon("C:\\LCheck\\icons\\difFrame.png");

				JPanel panel = new JPanel();
				panel.add(new JLabel(img));
				differentBarcodeFrameError.add(panel);
				panel.setOpaque(false);
				panel.setBounds(115, 10, 50, 50);

				okButtonButton.setBounds(70, 100, 150, 50);

				okButtonButton.addActionListener(new difFrameClose());

				// Pravi vazmojno vavejdaneto s enter
				differentBarcodeFrameError.getRootPane().getInputMap(
						JComponent.WHEN_IN_FOCUSED_WINDOW

				).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
						"clickButton");

				differentBarcodeFrameError.getRootPane().getActionMap().put("clickButton",
						new AbstractAction() {
							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							/**
						 	 * 
						 	 */

							public void actionPerformed(ActionEvent ae) {
								okButtonButton.doClick();

							}
						});

				differentBarcodeFrameError.setVisible(true);
			}

		}
	}

	public class difFrameClose implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			nfoForPassField = new StringBuilder();

			logInButton.setBackground(Color.GREEN);

			differentBarcodeFrameError.setVisible(false);

			kasaField.requestFocus();
			kasaField.setText("");
			harnessField.setText("");
			maoField.setText("");
		}

	}

	public static void main(String[] args) {

		new SplacesLabels();

	}

	public class PrintiraiPolovinPaketaj implements ActionListener {

		Enumeration<?> portList = null;

		CommPortIdentifier portId;

		SerialPort serialPort;

		public void actionPerformed(ActionEvent e) {

			sNulite = new StringBuilder();

			String broiki = String.valueOf(counterHarness);
			sNulite.append(broiki);

			while (sNulite.length() <= 2) {
				sNulite.insert(0, "0");
			}

			System.out.println(varNumber);
			// Zamenq reda na user i tile nomera v KRAEN etiketa s realnia ot
			// programata
			String ETI_Kraen = SHIPPING_LABEL_toString.replace("@USER", passContainer);
			String ETI_Kraen1 = ETI_Kraen.replace("@Var1", varNumber);
			String ETI_Kraen2 = ETI_Kraen1.replace("@COUNTER", sNulite);

			packCounter = 0;

			System.out.println(ETI_Kraen2);

			statusPaketaj.setText("œ¿ ≈“¿∆=" + PAKETAJ
					+ "       ¡–Œ… œ¿ ≈“¿∆»=" + pack_counter);

			portList = CommPortIdentifier.getPortIdentifiers();

			while (portList.hasMoreElements()) {
				portId = (CommPortIdentifier) portList.nextElement();
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					if (portId.getName().equals(settComPort)) {
						// if (portId.getName().equals("/dev/term/a")) {

						try {
							serialPort = (SerialPort)

							portId.open("SimpleWriteApp", 2000);
						} catch (PortInUseException a) {
						}

						try {
							outputStream = serialPort.getOutputStream();
						} catch (IOException a) {
						}

						try {
							serialPort.setSerialPortParams(9600,
									SerialPort.DATABITS_8,
									SerialPort.STOPBITS_2,
									SerialPort.PARITY_NONE);
						} catch (UnsupportedCommOperationException a) {
						}

						try {
							outputStream.write(ETI_Kraen2.getBytes());
							outputStream.flush();
						} catch (IOException a) {
						}
						// System.out.println(serialPort);

						serialPort.close();

					}
				}

			}

			counterHarness = 0;
			printHalvePackButton.setVisible(false);
		}

	}
}
