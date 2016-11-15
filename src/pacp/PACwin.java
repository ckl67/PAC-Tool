package pacp;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;

import org.json.simple.JSONObject;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JSeparator;


public class PACwin {

	private JFrame frame;
	private JTextField textFieldH1;
	private JTextField textFieldH2;
	private JTextField textFieldH3;
	private JTextField textFieldTK;
	private JTextField textFieldT0;
	private JTextField textFieldCarnotFroid;
	private JTextField textFieldEvap;
	private JTextField textFieldRG;
	private JTextField textFieldCond;
	private JTextField textFieldLiq;
	private JTextField textFieldCapacity;
	private JTextField textFieldPower;
	private JTextField textFieldCurrent;
	private JTextField textFieldSurchauffe;
	private JTextField textFieldSousRefroid;
	private JTextField textFieldEER;
	private JTextField textFieldMassFlow;
	private JTextField textFieldDeltaH0;
	private JTextField textFieldVoltage;
	private JTextField textFieldCosPhi;
	private JTextField textFieldScrollName;


	/**
	 * Create the application.
	 */
	public PACwin(PACcop pac) {
		initialize(pac);
		
		textFieldScrollName.setText(pac.getName());
		textFieldSurchauffe.setText(String.valueOf(Math.round(pac.getRG() - pac.getEvap())));
		textFieldSousRefroid.setText(String.valueOf(Math.round(pac.getCond() - pac.getLiq())));
		textFieldEER.setText(String.valueOf(Math.round(pac.getCapacity()/pac.getPower()*10.0)/10.0));
		textFieldDeltaH0.setText("-----");
		
		double tmp = Math.round(PACmain.cosphi(pac.getPower(), pac.getVoltage(), pac.getCurrent())*10000.0)/10000.0;
		textFieldCosPhi.setText(String.valueOf(tmp));

	}

	/**
	 * 
	 * Initialize the contents of the frame.
	 */
	private void initialize(PACcop pac) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(PACwin.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frame.setTitle("PAC Tool");
		frame.setBounds(100, 100, 443, 544);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		// ===============================================================================================================
		// 													MENU
		// ===============================================================================================================
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);
		
		JMenu file = new JMenu("File");
		menubar.add(file);
		JMenuItem mexit = new JMenuItem("Exit");
		mexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		JMenuItem mloadcfg = new JMenuItem("Load Config.");
		file.add(mloadcfg);
		
		JMenuItem msavecfg = new JMenuItem("Save Config.");
		msavecfg.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				
				JSONObject ObjPacTool = new JSONObject();  
				ObjPacTool.put("Scroll", pac.getJsonObject());  
				System.out.println(ObjPacTool);
				
			}
		});
		file.add(msavecfg);
		
		JSeparator separator = new JSeparator();
		file.add(separator);
		file.add(mexit);
		
		JMenu help = new JMenu("Help");
		menubar.add(help);
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PACwabout nabw = new PACwabout();
				nabw.NewAboutWin();
			}
		});
		help.add(about);
			
		// ===============================================================================================================
		//													TABBED PANE
		// ===============================================================================================================
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// ===============================================================================================================
		//									                 PANEL PAC
		// ===============================================================================================================
		JPanel panelPAC = new JPanel();
		tabbedPane.addTab("PAC", null, panelPAC, null);
		panelPAC.setLayout(null);

		// ================================================================
		// 					  	Performance Panel
		// ================================================================
		JPanel panel_pc1 = new JPanel();
		panel_pc1.setBorder(new TitledBorder(null, "Performance Constructeur 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_pc1.setBounds(10, 46, 413, 161);
		panel_pc1.setLayout(null);
		panelPAC.add(panel_pc1);

		// ---------------------------------------------------------------
		// EVAP
		// ---------------------------------------------------------------
		JLabel lblEvap = new JLabel("Evap :");
		lblEvap.setBounds(10, 28, 51, 14);
		panel_pc1.add(lblEvap);

		textFieldEvap = new JTextField();
		textFieldEvap.setToolTipText("Temp\u00E9rature d'\u00E9vaporation (T0)");
		textFieldEvap.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				pac.setEvap(Double.valueOf( textFieldEvap.getText()));
				
				double tmp = Math.round(pac.getRG() - pac.getEvap());
				textFieldSurchauffe.setText(String.valueOf(tmp));

			}
		});
		textFieldEvap.setBounds(59, 25, 75, 20);
		panel_pc1.add(textFieldEvap);
		textFieldEvap.setText(String.valueOf(pac.getEvap()));
		textFieldEvap.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldEvap.setColumns(10);

		JLabel lblTemp_unity1 = new JLabel("\u00B0F");
		lblTemp_unity1.setBounds(144, 28, 46, 14);
		panel_pc1.add(lblTemp_unity1);

		// ---------------------------------------------------------------
		// RG
		// ---------------------------------------------------------------
		JLabel lblRG = new JLabel("RG :");
		lblRG.setBounds(10, 67, 51, 14);
		panel_pc1.add(lblRG);

		textFieldRG = new JTextField();
		textFieldRG.setToolTipText("Temp\u00E9rature d'aspiration du compresseur Point : (1)");
		textFieldRG.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				
				pac.setRG(Double.valueOf( textFieldRG.getText()));

				double tmp = Math.round(pac.getRG() - pac.getEvap());
				textFieldSurchauffe.setText(String.valueOf(tmp));			
			}
		});
		textFieldRG.setBounds(59, 64, 75, 20);
		panel_pc1.add(textFieldRG);
		textFieldRG.setText(String.valueOf(pac.getRG()));
		textFieldRG.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldRG.setColumns(10);

		JLabel lblTemp_unity2 = new JLabel("\u00B0F");
		lblTemp_unity2.setBounds(144, 67, 46, 14);
		panel_pc1.add(lblTemp_unity2);

		// ---------------------------------------------------------------
		// SURCHAUFFE
		// ---------------------------------------------------------------
		JLabel lblSurchauffe = new JLabel("Surchauffe :");
		lblSurchauffe.setBounds(10, 101, 81, 14);
		panel_pc1.add(lblSurchauffe);

		textFieldSurchauffe = new JTextField();
		textFieldSurchauffe.setText("0.0");
		textFieldSurchauffe.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldSurchauffe.setBackground(Color.PINK);
		textFieldSurchauffe.setEditable(false);
		textFieldSurchauffe.setBounds(88, 98, 46, 20);
		panel_pc1.add(textFieldSurchauffe);
		textFieldSurchauffe.setColumns(10);

		JLabel lblTemp_unity5 = new JLabel("\u00B0F");
		lblTemp_unity5.setBounds(144, 101, 46, 14);
		panel_pc1.add(lblTemp_unity5);

		// ---------------------------------------------------------------
		// COND
		// ---------------------------------------------------------------
		JLabel lblCond = new JLabel("Cond :");
		lblCond.setBounds(233, 28, 46, 14);
		panel_pc1.add(lblCond);

		textFieldCond = new JTextField();
		textFieldCond.setToolTipText("Temp\u00E9rature de condensation (TK) ");
		textFieldCond.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				
				pac.setCond(Double.valueOf( textFieldCond.getText()));

				double tmp = Math.round(pac.getCond() - pac.getLiq());
				textFieldSousRefroid.setText(String.valueOf(tmp));
			}
		});
		textFieldCond.setBounds(289, 25, 75, 20);
		textFieldCond.setText(String.valueOf(pac.getCond()));
		textFieldCond.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCond.setColumns(10);
		panel_pc1.add(textFieldCond);

		JLabel lblTemp_unity3 = new JLabel("\u00B0F");
		lblTemp_unity3.setBounds(374, 28, 29, 14);
		panel_pc1.add(lblTemp_unity3);

		// ---------------------------------------------------------------
		// LIQ
		// ---------------------------------------------------------------
		JLabel lblLiq = new JLabel("Liq :");
		lblLiq.setBounds(233, 67, 46, 14);
		panel_pc1.add(lblLiq);

		textFieldLiq = new JTextField();
		textFieldLiq.setToolTipText("Temp\u00E9rature Entr\u00E9e D\u00E9tendeur : Point (3) ");
		textFieldLiq.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				
				pac.setLiq(Double.valueOf( textFieldLiq.getText()));

				double tmp = Math.round(pac.getCond() - pac.getLiq());
				textFieldSousRefroid.setText(String.valueOf(tmp));
			}
		});
		
		textFieldLiq.setBounds(289, 64, 75, 20);
		textFieldLiq.setText(String.valueOf(pac.getLiq()));
		textFieldLiq.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldLiq.setColumns(10);
		panel_pc1.add(textFieldLiq);

		JLabel lblTemp_unity4 = new JLabel("\u00B0F");
		lblTemp_unity4.setBounds(374, 64, 29, 14);
		panel_pc1.add(lblTemp_unity4);

		// ---------------------------------------------------------------
		// SOUS REFROIDISSEMENT
		// ---------------------------------------------------------------

		JLabel lblSousRefroid = new JLabel("S-Refroidissement :");
		lblSousRefroid.setBounds(205, 101, 113, 14);
		panel_pc1.add(lblSousRefroid);

		textFieldSousRefroid = new JTextField();
		textFieldSousRefroid.setText("0.0");
		textFieldSousRefroid.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldSousRefroid.setEditable(false);
		textFieldSousRefroid.setColumns(10);
		textFieldSousRefroid.setBackground(Color.PINK);
		textFieldSousRefroid.setBounds(318, 98, 46, 20);
		panel_pc1.add(textFieldSousRefroid);

		JLabel lblTemp_unity6 = new JLabel("\u00B0F");
		lblTemp_unity6.setBounds(374, 101, 29, 14);
		panel_pc1.add(lblTemp_unity6);

		// ---------------------------------------------------------------
		// Check Box Farenheit / Celcius
		// ---------------------------------------------------------------
		JCheckBox checkoxFaren = new JCheckBox("Farenheit");
		checkoxFaren.setBounds(302, 131, 95, 23);
		panel_pc1.add(checkoxFaren);
		checkoxFaren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (checkoxFaren.isSelected()) {
					lblTemp_unity1.setText("°F");
					lblTemp_unity2.setText("°F");
					lblTemp_unity3.setText("°F");
					lblTemp_unity4.setText("°F");
					lblTemp_unity5.setText("°F");
					lblTemp_unity6.setText("°F");

					pac.setEvap(PACmain.degre2farenheit(pac.getEvap()));
					textFieldEvap.setText(String.valueOf(Math.round(pac.getEvap()*100.0)/100.0));
					
					pac.setRG(PACmain.degre2farenheit(pac.getRG()));
					textFieldRG.setText(String.valueOf(Math.round(pac.getRG()*100.0)/100.0));

					pac.setCond(PACmain.degre2farenheit(pac.getCond()));
					textFieldCond.setText(String.valueOf(Math.round(pac.getCond()*100.0)/100.0));

					pac.setLiq(PACmain.degre2farenheit(pac.getLiq()));
					textFieldLiq.setText(String.valueOf(Math.round(pac.getLiq()*100.0)/100.0));

					textFieldSurchauffe.setText(String.valueOf(Math.round(pac.getRG() - pac.getEvap())));
					textFieldSousRefroid.setText(String.valueOf(Math.round(pac.getCond() - pac.getLiq())));
				} else {
					lblTemp_unity1.setText("°C");
					lblTemp_unity2.setText("°C");
					lblTemp_unity3.setText("°C");
					lblTemp_unity4.setText("°C");
					lblTemp_unity5.setText("°C");
					lblTemp_unity6.setText("°C");

					pac.setEvap(PACmain.farenheit2degre(pac.getEvap()));
					textFieldEvap.setText(String.valueOf(Math.round(pac.getEvap()*100.0)/100.0));
					
					pac.setRG(PACmain.farenheit2degre(pac.getRG()));
					textFieldRG.setText(String.valueOf(Math.round(pac.getRG()*100.0)/100.0));

					pac.setCond(PACmain.farenheit2degre(pac.getCond()));
					textFieldCond.setText(String.valueOf(Math.round(pac.getCond()*100.0)/100.0));

					pac.setLiq(PACmain.farenheit2degre(pac.getLiq()));
					textFieldLiq.setText(String.valueOf(Math.round(pac.getLiq()*100.0)/100.0));

					textFieldSurchauffe.setText(String.valueOf(Math.round(pac.getRG() - pac.getEvap())));
					textFieldSousRefroid.setText(String.valueOf(Math.round(pac.getCond() - pac.getLiq())));
				}
			}
		});
		checkoxFaren.setSelected(true);

		// ================================================================
		// 					   	Performance 2 Panel
		// ================================================================
		JPanel panel_pc2 = new JPanel();
		panel_pc2.setBounds(10, 215, 413, 241);
		panel_pc2.setBorder(new TitledBorder(null, "Performance Constructeur 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_pc2.setLayout(null);
		panelPAC.add(panel_pc2);

		JCheckBox checkoxBTU = new JCheckBox("BTU/hr");
		checkoxBTU.setToolTipText("British Thermal Unit / hour");
		JCheckBox chckbxPound = new JCheckBox("lbs/h");
		
		// ---------------------------------------------------------------
		// Capacity
		// ---------------------------------------------------------------
		
		JLabel lblCapacity = new JLabel("Capacity :");
		lblCapacity.setToolTipText("");
		lblCapacity.setBounds(10, 28, 73, 14);
		panel_pc2.add(lblCapacity);

		textFieldCapacity = new JTextField();
		textFieldCapacity.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				
				pac.setCapacity(Double.valueOf( textFieldCapacity.getText()));

				double tmp = Math.round(pac.getCapacity()/pac.getPower()*10.0)/10.0;
				textFieldEER.setText(String.valueOf(tmp));
				
				if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
					textFieldDeltaH0.setText("-----");
				} else {
					tmp = Math.round(pac.getCapacity()/pac.getMassFlow()/1000.0);
					textFieldDeltaH0.setText(String.valueOf(tmp));
				}
			}
		});
		textFieldCapacity.setToolTipText("Puissance frigorifique: (H1-H3) x D\u00E9bit Massique");
		textFieldCapacity.setBounds(82, 25, 62, 20);
		textFieldCapacity.setText(String.valueOf(pac.getCapacity()));
		textFieldCapacity.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCapacity.setColumns(10);
		panel_pc2.add(textFieldCapacity);

		JLabel lblCapacity_unity = new JLabel("Btu/hr");
		lblCapacity_unity.setToolTipText("(BUT/hr) British Thermal Unit / hour = Unit\u00E9 de mesure d'\u00E9nergie thermique / Heure. L'unit\u00E9 de puissance du SI est le watt (symbole : W), qui correspond \u00E0  un joule fourni par seconde.");
		lblCapacity_unity.setBounds(154, 28, 46, 14);
		panel_pc2.add(lblCapacity_unity);

		// ---------------------------------------------------------------
		// Power
		// ---------------------------------------------------------------
		JLabel lblPower = new JLabel("Power :");
		lblPower.setBounds(10, 59, 73, 14);
		panel_pc2.add(lblPower);

		textFieldPower = new JTextField();
		textFieldPower.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				
				pac.setPower(Double.valueOf( textFieldPower.getText()));

				double tmp = Math.round(pac.getCapacity()/pac.getPower()*10.0)/10.0;
				textFieldEER.setText(String.valueOf(tmp));
				
				tmp = Math.round(PACmain.cosphi(pac.getPower(), pac.getVoltage(), pac.getCurrent())*10000.0)/10000.0;
				textFieldCosPhi.setText(String.valueOf(tmp));

			}
		});
		textFieldPower.setToolTipText("Puissance Absorb\u00E9e");
		textFieldPower.setText(String.valueOf(pac.getPower()));
		textFieldPower.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldPower.setBounds(82, 56, 62, 20);
		textFieldPower.setColumns(10);
		panel_pc2.add(textFieldPower);

		JLabel lblPower_Unity = new JLabel("Watt");
		lblPower_Unity.setBounds(154, 62, 46, 14);
		panel_pc2.add(lblPower_Unity);

		// ---------------------------------------------------------------
		// Courant
		// ---------------------------------------------------------------
		JLabel lblCourant = new JLabel("Current :");
		lblCourant.setBounds(10, 90, 73, 14);
		panel_pc2.add(lblCourant);

		textFieldCurrent = new JTextField();
		textFieldCurrent.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				pac.setCurrent(Double.valueOf( textFieldCurrent.getText()));
				
				double tmp = Math.round(PACmain.cosphi(pac.getPower(), pac.getVoltage(), pac.getCurrent())*10000.0)/10000.0;
				textFieldCosPhi.setText(String.valueOf(tmp));

			}
		});
		textFieldCurrent.setToolTipText("Courant absorb\u00E9");
		textFieldCurrent.setText(String.valueOf(pac.getCurrent()));
		textFieldCurrent.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCurrent.setColumns(10);
		textFieldCurrent.setBounds(82, 87, 62, 20);
		panel_pc2.add(textFieldCurrent);

		JLabel lblCurrent_unity = new JLabel("A");
		lblCurrent_unity.setBounds(154, 90, 46, 14);
		panel_pc2.add(lblCurrent_unity);
		
		// ---------------------------------------------------------------
		// EER
		// ---------------------------------------------------------------
		JLabel lblEer = new JLabel("EER :");
		lblEer.setBounds(10, 128, 73, 14);
		panel_pc2.add(lblEer);
		
		textFieldEER = new JTextField();
		textFieldEER.setEditable(false);
		textFieldEER.setBackground(Color.PINK);
		textFieldEER.setToolTipText("EER (Energy Efficiency Ratio) : Coefficient d\u2019efficacit\u00E9 frigorifique");
		textFieldEER.setText("0.0");
		textFieldEER.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldEER.setColumns(10);
		textFieldEER.setBounds(82, 125, 62, 20);
		panel_pc2.add(textFieldEER);
		
		JLabel lblEER_unity = new JLabel("BTU/(hr.W)");
		lblEER_unity.setBounds(154, 125, 73, 22);
		panel_pc2.add(lblEER_unity);

		// ---------------------------------------------------------------
		// Mass Flow
		// ---------------------------------------------------------------

		JLabel lblMassflow = new JLabel("MassFlow :");
		lblMassflow.setBounds(220, 28, 73, 14);
		panel_pc2.add(lblMassflow);

		textFieldMassFlow = new JTextField();
		textFieldMassFlow.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				pac.setMassFlow(Double.valueOf( textFieldMassFlow.getText()));

				if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
					textFieldDeltaH0.setText("-----");
				} else {
					double tmp = Math.round(pac.getCapacity()/pac.getMassFlow()/1000.0);
					textFieldDeltaH0.setText(String.valueOf(tmp));
				}

			}
		});
		textFieldMassFlow.setToolTipText("D\u00E9bit Massique");
		textFieldMassFlow.setText(String.valueOf(pac.getMassFlow()));
		textFieldMassFlow.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldMassFlow.setColumns(10);
		textFieldMassFlow.setBounds(295, 25, 51, 20);
		panel_pc2.add(textFieldMassFlow);
		
		JLabel lblMassFlow_unity = new JLabel("lbs/hr");
		lblMassFlow_unity.setBounds(356, 28, 36, 14);
		panel_pc2.add(lblMassFlow_unity);

		// ---------------------------------------------------------------
		// Check Box BTU/HR or Watt
		// ---------------------------------------------------------------
		
		checkoxBTU.setBounds(324, 171, 68, 23);
		checkoxBTU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (checkoxBTU.isSelected()) {
					lblCapacity_unity.setText("Btu/hr");
					lblEER_unity.setText("BTU/(hr.W)");
					
					pac.setCapacity(PACmain.watt2btuhr(pac.getCapacity()));
					textFieldCapacity.setText(String.valueOf(Math.round(pac.getCapacity()*100.0)/100.0));

					textFieldEER.setText(String.valueOf(Math.round(pac.getCapacity()/pac.getPower()*10.0)/10.0));
					
					textFieldDeltaH0.setText("-----");

				} else {
					lblCapacity_unity.setText("Watt");
					lblEER_unity.setText("");

					pac.setCapacity(PACmain.buthr2watt(pac.getCapacity()));
					textFieldCapacity.setText(String.valueOf(Math.round(pac.getCapacity()*100.0)/100.0));

					textFieldEER.setText(String.valueOf(Math.round(pac.getCapacity()/pac.getPower()*10.0)/10.0));
					
					if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
						textFieldDeltaH0.setText("-----");
					} else {
						double tmp = Math.round(pac.getCapacity()/pac.getMassFlow()/1000.0);
						textFieldDeltaH0.setText(String.valueOf(tmp));
					}
				}
			}
		});
		checkoxBTU.setSelected(true);
		panel_pc2.add(checkoxBTU);
		
		// ---------------------------------------------------------------
		// Pound
		// ---------------------------------------------------------------
		
		chckbxPound.setToolTipText("Pounds/hour");
		chckbxPound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxPound.isSelected()) {
					lblMassFlow_unity.setText("lbs/hr");
					
					pac.setMassFlow(PACmain.kg2pound(pac.getMassFlow()) );
					textFieldMassFlow.setText(String.valueOf(Math.round(pac.getMassFlow()*10.0)/10.0));

					textFieldDeltaH0.setText("-----");

				} else {
					lblMassFlow_unity.setText("Kg/s");

					pac.setMassFlow(PACmain.pound2kg(pac.getMassFlow()) );
					textFieldMassFlow.setText(String.valueOf(Math.round(pac.getMassFlow()*10000.0)/10000.0));		
					
					if (checkoxBTU.isSelected() | chckbxPound.isSelected())  {
						textFieldDeltaH0.setText("-----");
					} else {
						double tmp = Math.round(pac.getCapacity()/pac.getMassFlow()/1000.0);
						textFieldDeltaH0.setText(String.valueOf(tmp));
					}
					
				}
			}
		});
		chckbxPound.setSelected(true);
		chckbxPound.setBounds(324, 201, 68, 23);
		panel_pc2.add(chckbxPound);
		
		// ---------------------------------------------------------------
		// H1-H3
		// ---------------------------------------------------------------

		JLabel lblDeltaH0 = new JLabel("H1-H3");
		lblDeltaH0.setBounds(223, 59, 62, 14);
		panel_pc2.add(lblDeltaH0);
		
		textFieldDeltaH0 = new JTextField();
		textFieldDeltaH0.setBackground(Color.PINK);
		textFieldDeltaH0.setEditable(false);
		textFieldDeltaH0.setToolTipText("Delta Enthalpie ");
		textFieldDeltaH0.setText("0.0");
		textFieldDeltaH0.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldDeltaH0.setColumns(10);
		textFieldDeltaH0.setBounds(295, 56, 51, 20);
		panel_pc2.add(textFieldDeltaH0);
		
		JLabel lblDeltaH0_unity = new JLabel("KJ/Kg");
		lblDeltaH0_unity.setBounds(356, 59, 36, 14);
		panel_pc2.add(lblDeltaH0_unity);
		
		// ---------------------------------------------------------------
		// Voltage
		// ---------------------------------------------------------------
		JLabel lblVoltage = new JLabel("Voltage :");
		lblVoltage.setBounds(10, 177, 73, 14);
		panel_pc2.add(lblVoltage);
		
		textFieldVoltage = new JTextField();
		textFieldVoltage.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {			
				
				pac.setVoltage(Double.valueOf( textFieldVoltage.getText()));

				double tmp = Math.round(PACmain.cosphi(pac.getPower(), pac.getVoltage(), pac.getCurrent())*10000.0)/10000.0;
				textFieldCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldVoltage.setToolTipText("Tension");
		textFieldVoltage.setText(String.valueOf(pac.getVoltage()));
		textFieldVoltage.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldVoltage.setColumns(10);
		textFieldVoltage.setBounds(82, 174, 62, 20);
		panel_pc2.add(textFieldVoltage);
		
		JLabel lblVoltage_unity = new JLabel("V");
		lblVoltage_unity.setBounds(154, 177, 46, 14);
		panel_pc2.add(lblVoltage_unity);
		
		// ---------------------------------------------------------------
		// Cos Phi
		// ---------------------------------------------------------------
		JLabel lblCosphi = new JLabel("Cos (Phi)");
		lblCosphi.setBounds(10, 208, 73, 14);
		panel_pc2.add(lblCosphi);
		
		textFieldCosPhi = new JTextField();
		textFieldCosPhi.setToolTipText("Cosinus(Phi)");
		textFieldCosPhi.setText("0.0");
		textFieldCosPhi.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCosPhi.setEditable(false);
		textFieldCosPhi.setColumns(10);
		textFieldCosPhi.setBackground(Color.PINK);
		textFieldCosPhi.setBounds(82, 205, 62, 20);
		panel_pc2.add(textFieldCosPhi);
		
		// ---------------------------------------------------------------
		// Combo Box Scroll compressor
		// ---------------------------------------------------------------
		JComboBox comboBoxScroll = new JComboBox();
		comboBoxScroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(comboBoxScroll.getSelectedItem());
			}
		});
		comboBoxScroll.setModel(new DefaultComboBoxModel(new String[] {"one", "two", "three"}));
		comboBoxScroll.setBounds(257, 15, 139, 20);
		panelPAC.add(comboBoxScroll);
		
		textFieldScrollName = new JTextField();
		textFieldScrollName.setText("scroll");
		textFieldScrollName.setFont(new Font("Tahoma", Font.BOLD, 14));
		textFieldScrollName.setEditable(false);
		textFieldScrollName.setBounds(10, 15, 151, 20);
		panelPAC.add(textFieldScrollName);
		textFieldScrollName.setColumns(10);

		// ===============================================================================================================
		//									        PANEL MEASURE 
		// ===============================================================================================================

		JPanel panelComp = new JPanel();
		tabbedPane.addTab("Mesures", null, panelComp, null);
		panelComp.setLayout(null);

		// ---------------------------------------------------------------
		// Button Compute
		// ---------------------------------------------------------------
		JButton btnComp = new JButton("Calcul");
		btnComp.setBounds(66, 271, 89, 23);
		btnComp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pac.setH1(Double.parseDouble(textFieldH1.getText()));
				pac.setH2(Double.parseDouble(textFieldH2.getText()));
				pac.setH3(Double.parseDouble(textFieldH3.getText()));
				pac.setT0(Double.parseDouble(textFieldT0.getText()));
				pac.setTK(Double.parseDouble(textFieldTK.getText()));

				textFieldCarnotFroid.setText(String.valueOf(
						Math.round(pac.cop_carnot_froid()*100.0) /100.0
						));

				/*
						System.out.format("TO = %.2f°C\n",pac.getT0());
						System.out.format("TK = %.2f°C\n",pac.getTK());
						System.out.format("H1 = %.2fkJ/kg\n",pac.getH1());
						System.out.format("H2 = %.2fkJ/kg\n",pac.getH2());
						System.out.format("H3=H4 = %.2fkJ/kg\n",pac.getH3());
				 */
			}
		});
		panelComp.add(btnComp);


		// ---------------------------------------------------------------
		// H1
		// ---------------------------------------------------------------
		JLabel lblH = new JLabel("H1 :");
		lblH.setBounds(25, 76, 30, 14);
		panelComp.add(lblH);

		JLabel lblH1unity = new JLabel("kJ/kg");
		lblH1unity.setBounds(158, 76, 36, 14);
		panelComp.add(lblH1unity);

		textFieldH1 = new JTextField();
		textFieldH1.setText("416");
		textFieldH1.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldH1.setBounds(69, 73, 86, 20);
		textFieldH1.setColumns(10);
		textFieldH1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if ( ( (Character.isDigit(vChar)) || (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == '.') || (vChar == KeyEvent.VK_DELETE)) ) {
					if ( (vChar == '.') && numberOf(textFieldH1.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					e.consume();					
				}
			}
		});
		panelComp.add(textFieldH1);

		// ---------------------------------------------------------------
		// H2
		// ---------------------------------------------------------------
		JLabel lblH2 = new JLabel("H2 :");
		lblH2.setBounds(25, 107, 30, 14);
		panelComp.add(lblH2);

		JLabel lblH2unity = new JLabel("kJ/kg");
		lblH2unity.setBounds(158, 107, 36, 14);
		panelComp.add(lblH2unity);

		textFieldH2 = new JTextField();
		textFieldH2.setText("448");
		textFieldH2.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldH2.setBounds(69, 104, 86, 20);
		textFieldH2.setColumns(10);
		textFieldH2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if ( ( (Character.isDigit(vChar)) || (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == '.') || (vChar == KeyEvent.VK_DELETE)) ) {
					// OK
					if ( (vChar == '.') && numberOf(textFieldH2.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					//NOK
					e.consume();					
				}
			}
		});
		panelComp.add(textFieldH2);

		// ---------------------------------------------------------------
		// H3 & H4
		// ---------------------------------------------------------------
		JLabel lblH3 = new JLabel("H3=H4 :");
		lblH3.setBounds(25, 138, 46, 14);
		panelComp.add(lblH3);

		JLabel lblH3unity = new JLabel("kJ/kg");
		lblH3unity.setBounds(158, 138, 36, 14);
		panelComp.add(lblH3unity);

		textFieldH3 = new JTextField();
		textFieldH3.setText("250");
		textFieldH3.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldH3.setColumns(10);
		textFieldH3.setBounds(69, 135, 86, 20);
		textFieldH3.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if ( ( (Character.isDigit(vChar)) || (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == '.') || (vChar == KeyEvent.VK_DELETE)) ) {
					// OK
					if ( (vChar == '.') && numberOf(textFieldH3.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					//NOK
					e.consume();					
				}
			}
		});
		panelComp.add(textFieldH3);

		// ---------------------------------------------------------------
		// T0
		// ---------------------------------------------------------------
		JLabel lblT0 = new JLabel("T0 :");
		lblT0.setBounds(25, 14, 30, 14);
		panelComp.add(lblT0);

		JLabel lblT0unity = new JLabel("\u00B0C");
		lblT0unity.setBounds(158, 14, 36, 14);
		panelComp.add(lblT0unity);

		textFieldT0 = new JTextField();
		textFieldT0.setText("5");
		textFieldT0.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldT0.setColumns(10);
		textFieldT0.setBounds(69, 11, 86, 20);
		textFieldT0.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if ( ( (Character.isDigit(vChar)) || (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == '.') || (vChar == KeyEvent.VK_DELETE)) ) {
					// OK
					if ( (vChar == '.') && numberOf(textFieldT0.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					//NOK
					e.consume();					
				}
			}
		});
		panelComp.add(textFieldT0);

		// ---------------------------------------------------------------
		// TK
		// ---------------------------------------------------------------
		JLabel lblTk = new JLabel("TK :");
		lblTk.setBounds(25, 45, 30, 14);
		panelComp.add(lblTk);

		JLabel lblTkunity = new JLabel("\u00B0C");
		lblTkunity.setBounds(158, 42, 36, 14);
		panelComp.add(lblTkunity);

		textFieldTK = new JTextField();
		textFieldTK.setText("48");
		textFieldTK.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldTK.setColumns(10);
		textFieldTK.setBounds(69, 42, 86, 20);
		textFieldTK.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if ( ( (Character.isDigit(vChar)) || (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == '.') || (vChar == KeyEvent.VK_DELETE)) ) {
					// OK
					if ( (vChar == '.') && numberOf(textFieldTK.getText(),'.') >= 1) {
						e.consume();
					}
				} else {
					//NOK
					e.consume();					
				}
			}
		});
		panelComp.add(textFieldTK);

		// ---------------------------------------------------------------
		// Image
		// ---------------------------------------------------------------

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(PACwin.class.getResource("/pacp/images/enthalpie.jpg")));
		lblNewLabel.setBounds(233, 11, 150, 100);
		panelComp.add(lblNewLabel);

		// ---------------------------------------------------------------
		// Carnot Froid
		// ---------------------------------------------------------------
		JLabel labelCarnotFroid = new JLabel("Carnot Froid :");
		labelCarnotFroid.setBounds(212, 138, 75, 14);
		panelComp.add(labelCarnotFroid);

		textFieldCarnotFroid = new JTextField();
		textFieldCarnotFroid.setBackground(Color.PINK);
		textFieldCarnotFroid.setEditable(false);
		textFieldCarnotFroid.setText("0");
		textFieldCarnotFroid.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCarnotFroid.setColumns(10);
		textFieldCarnotFroid.setBounds(297, 135, 86, 20);
		panelComp.add(textFieldCarnotFroid);
		
		// ===============================================================================================================
		//									        PANEL DEFINITION 
		// ===============================================================================================================

		JPanel panelDef = new JPanel();
		tabbedPane.addTab("D\u00E9finitions", null, panelDef, null);
		panelDef.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 432, 467);
		panelDef.add(scrollPane);
		
		JEditorPane txtdef = new JEditorPane();
		txtdef.setEditable(false);
		txtdef.setContentType("text/html");
		txtdef.setText("<b>Capacity : </b>Puissance Frigorifique<b><br>\r\n&nbsp;&nbsp;&nbsp; </b>(H1-H3) x D\u00E9bit Massique<br>\r\n<b>COP:</b> COefficient de Performance <br>\r\n&nbsp;&nbsp;&nbsp; COP = Puissance <u>Restitu\u00E9e</u>\r\n(Chaleur) / Puissance consomm\u00E9e <br>\r\n&nbsp;&nbsp;&nbsp; Attention sur les catalogues le COP\r\n(constructeur) <br>\r\n&nbsp;&nbsp;&nbsp; est calcul\u00E9 \u00E0 partir d'une temp\u00E9rature\r\nd'eau de nappe <br>\r\n&nbsp;&nbsp;&nbsp; phr\u00E9atique de 10\u00B0C <br>\r\n<b>COP constructeur :</b> <br>\r\n&nbsp;&nbsp;&nbsp; Performance d'une PAC d\u00E9termin\u00E9e en\r\nlaboratoire ,<br>\r\n&nbsp;&nbsp;&nbsp; donc assez loin des r\u00E9alit\u00E9s.<br>\r\n<b>COP global de la PAC :</b> <br>\r\n&nbsp;&nbsp;&nbsp; Performance qui tient compte des\r\nauxiliaires, <br>\r\n&nbsp;&nbsp;&nbsp; ventilateurs, pompes,etc..<br>\r\n<b>COP annuel (COPPA) : </b><br>\r\n&nbsp;&nbsp;&nbsp; Performance r\u00E9elle calcul\u00E9e pendant une\r\np\u00E9riode compl\u00E8te <br>\r\n&nbsp;&nbsp;&nbsp; de chauffage qui tient compte des\r\nsp\u00E9cificit\u00E9s de l'installation.<br>\r\n<b>EER (Energy Efficiency Ratio) : </b><br>\r\n&nbsp;&nbsp;&nbsp;&nbsp;Coefficient d'Efficacit\u00E9\r\nFrigorifique (ou) COP froid <br>\r\n&nbsp;&nbsp;&nbsp; EER = Puissance&nbsp;<u>Absorb\u00E9e</u>\r\n(Froid) / Puissance consomm\u00E9e<br>\r\n<b>Mass Flow :</b><br>\r\n&nbsp;&nbsp;&nbsp; D\u00E9bit Massique (Kg/s)<br>\r\n<span style=\"font-weight: bold;\">BTU/h :</span>\r\nBritish Thermal Unit per hour<span style=\"font-weight: bold;\"></span><br\r\n style=\"font-weight: bold;\">\r\n&nbsp;&nbsp;&nbsp; Unit\u00E9 anglo-saxonne de puissance: <br>\r\n&nbsp;&nbsp;&nbsp; 1 000 BTU/h valent approximativement\r\n293,071 W &nbsp;<br>\r\n");
		scrollPane.setViewportView(txtdef);
		

		// ---------------------------------------------------------------
		// Display 
		// ---------------------------------------------------------------
		frame.setVisible(true);

	}

	/**
	 * 
	 * @param str
	 * @param c
	 * @return
	 */
	public static int numberOf(String str,char c) {
		int res=0;
		if(str==null)
			return res;
		for(int i=0;i<str.length();i++)
			if(c==str.charAt(i))
				res++;
		return res;
	}
}
