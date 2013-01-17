package tela;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import net.miginfocom.swing.MigLayout;
import conexao.Conexao;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JProgressBar;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;

	private JCheckBox cboxPRODU;
	private JCheckBox cboxFABRI;
	private JTextField txtDmdServidor;
	private JTextField txtDmdBanco;
	private JTextField txtVmdServidor;
	private JTextField txtVmdBanco;
	private JCheckBox cboxCLTRI;
	private JCheckBox cboxTBNCM;
	private JCheckBox cboxCLASS;
	private JCheckBox cboxSBBAS;
	private JCheckBox cboxGRPRC;
	private JButton btn_limpa_dados;
	private JProgressBar progressBar;
	private JButton btn_processa;
	private JProgressBar progressBar2;
	private JCheckBox cboxRGTRI;
	private JCheckBox cboxRTXCT;
	private JCheckBox cboxPCFIN;
	private JCheckBox cboxFORNE;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public App() {
		setTitle("inFarma - Conversor de dados");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelTop = new JPanel();
		getContentPane().add(panelTop, BorderLayout.NORTH);
		panelTop.setLayout(new MigLayout("", "[fill][grow][][grow]", "[][][]"));

		JLabel lblDmdServidor = new JLabel("DMD Servidor");
		panelTop.add(lblDmdServidor, "cell 0 0,alignx trailing");

		txtDmdServidor = new JTextField();
		txtDmdServidor.setText("localhost");
		panelTop.add(txtDmdServidor, "cell 1 0,growx");
		txtDmdServidor.setColumns(10);

		JLabel lblNewLabel = new JLabel("Banco");
		panelTop.add(lblNewLabel, "cell 2 0,alignx trailing");

		txtDmdBanco = new JTextField();
		txtDmdBanco.setText("DMD");
		panelTop.add(txtDmdBanco, "cell 3 0,growx");
		txtDmdBanco.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("VMD Servidor");
		panelTop.add(lblNewLabel_1, "cell 0 1,alignx trailing");

		txtVmdServidor = new JTextField();
		txtVmdServidor.setText("localhost");
		panelTop.add(txtVmdServidor, "cell 1 1,growx");
		txtVmdServidor.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Banco");
		panelTop.add(lblNewLabel_2, "cell 2 1,alignx trailing,aligny baseline");

		txtVmdBanco = new JTextField();
		txtVmdBanco.setText("VMD");
		panelTop.add(txtVmdBanco, "cell 3 1,growx");
		txtVmdBanco.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel(
				"Converte uma base do Varejo para o Atacado");
		panelTop.add(lblNewLabel_3, "cell 0 2 4 1");

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		class ProcessaWorker extends SwingWorker<Void, Void> {

			@Override
			protected Void doInBackground() throws Exception {
				progressBar.setValue(0);
				progressBar.setMaximum(33);
				btn_limpa_dados.setEnabled(false);
				btn_processa.setEnabled(false);
				int resp = JOptionPane.showConfirmDialog(panel, "Confirma?",
						"Processar Dados", JOptionPane.YES_NO_OPTION);// todo
																		// alterar
				System.out.println(resp);

				if (resp == 0) {

					try (Connection dmd = new Conexao().getConnection(
							txtDmdServidor.getText(), txtDmdBanco.getText());
							Connection vmd = new Conexao().getConnection(
									txtVmdServidor.getText(),
									txtVmdBanco.getText())) {

						if (cboxRGTRI.isSelected() && cboxCLASS.isSelected()
								&& cboxCLTRI.isSelected()
								&& cboxFABRI.isSelected()
								&& cboxFORNE.isSelected()
								&& cboxGRPRC.isSelected()
								&& cboxPCFIN.isSelected()
								&& cboxPRODU.isSelected()
								&& cboxRTXCT.isSelected()
								&& cboxSBBAS.isSelected()
								&& cboxTBNCM.isSelected()) {

							// APAGANDO DADOS
							// FORNE
							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM FORNE");
								stmt.close();
								System.out.println("Deletou FORNE");
								progressBar.setValue(1);
							}

							// RGTRI
							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("update ESTAB set Cod_RegTri=null");
								stmt.executeUpdate("DELETE FROM RGTRI");
								System.out.println("fez update");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(2);
							}

							// PRODUTO
							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM PRODU");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(3);
							}

							// FABRI
							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM FABRI");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(4);
							}

							// CLASS
							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM CLASS");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(5);
							}

							// CLTRI
							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM CLTRI");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(6);
							}

							// GRPRC
							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM GRPRC");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(7);
							}

							// SBBAS
							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM SBBAS");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(8);
							}

							// TBNCM
							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM TBNCM");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(9);
							}

							// RTXCT
							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM RTXCT");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(10);
							}

							// PCFIN
							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM PCFIN");
								stmt.close();
								System.out.println("Deletou PCFIN");
								progressBar.setValue(11);
							}

						}
						// IMPORTAÇÃO

						// RGTRI
						if (cboxRGTRI.isSelected()) {

							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("update ESTAB set Cod_RegTri=null");
								stmt.executeUpdate("DELETE FROM RGTRI");
								System.out.println("fez update");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(12);
							}

							progressBar2.setValue(0);

							String vProdu = "SELECT * FROM RGTRI";
							String dProdu = "Insert Into RGTRI (Cod_RegTri, Des_RegTri, Ctrl_ImpIcmTotNfs) Values (?,?,?)";
							try (PreparedStatement pVmd = vmd
									.prepareStatement(vProdu);
									PreparedStatement pDmd = dmd
											.prepareStatement(dProdu)) {
								ResultSet rs = pVmd.executeQuery();

								// contar a qtde de registros
								int registros = contaRegistros("RGTRI", vmd);
								progressBar2.setMaximum(registros);
								registros = 0;

								while (rs.next()) {
									// grava no atacado
									pDmd.setInt(1, rs.getInt("Cod_RegTri"));
									pDmd.setString(2,
											rs.getString("Des_RegTri"));
									pDmd.setBoolean(3,
											rs.getBoolean("Flg_ImpIcmTotNfs"));
									// todo demais campo
									pDmd.executeUpdate();

									registros++;
									progressBar2.setValue(registros);
								}
								System.out.println("Funcionou RGTRI");
								pVmd.close();
								pDmd.close();

								progressBar2.setValue(0);
							}
							progressBar.setValue(13);
						}

						// CLTRI
						if (cboxCLTRI.isSelected()) {

							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM CLTRI");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(14);
							}

							progressBar2.setValue(0);

							String vProdu = "SELECT * FROM CLTRI";
							String dProdu = "Insert Into CLTRI (Cod_ClaTri, Des_Clatri) Values (?,?)";
							try (PreparedStatement pVmd = vmd
									.prepareStatement(vProdu);
									PreparedStatement pDmd = dmd
											.prepareStatement(dProdu)) {
								ResultSet rs = pVmd.executeQuery();

								// contar a qtde de registros
								int registros = contaRegistros("CLTRI", vmd);
								progressBar2.setMaximum(registros);
								registros = 0;

								while (rs.next()) {
									// grava no atacado
									pDmd.setString(1,
											rs.getString("Cod_Clatri"));
									pDmd.setString(2,
											rs.getString("Des_Clatri"));
									// todo demais campo
									pDmd.executeUpdate();

									registros++;
									progressBar2.setValue(registros);
								}
								System.out.println("Funcionou CLTRI");
								pVmd.close();
								pDmd.close();

								progressBar2.setValue(0);
							}
							progressBar.setValue(15);
						}

						// RTXCT
						if (cboxRTXCT.isSelected()) {

							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM RTXCT");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(16);
							}

							progressBar2.setValue(0);

							String vProdu = "SELECT Cod_RegTri, Cod_ClaTri, CAST(Alq_IcmIntReg AS decimal(18,4)) as Alq_IcmIntReg, "
									+ "CAST(Alq_IcmExtReg AS decimal(18,4)) as Alq_IcmExtReg, CAST(Per_RedBasCalIntReg AS decimal(18,4)) as Per_RedBasCalIntReg, "
									+ "CAST(Per_RedBasCalExtReg AS decimal(18,4)) as Per_RedBasCalExtReg, CAST(Alq_AgrDebEntIcm AS decimal(18,4)) as Alq_AgrDebEntIcm, "
									+ "CAST(Alq_IcmIntLoc AS decimal(18,4)) as Alq_IcmIntLoc, CAST(Alq_IcmExtLoc AS decimal(18,4)) as Alq_IcmExtLoc, "
									+ "CAST(Per_RedBasCalIntLoc AS decimal(18,4)) as Per_RedBasCalIntLoc, CAST(Per_RedBasCalExtLoc AS decimal(18,4)) as Per_RedBasCalExtLoc, "
									+ "Ctr_SujSubTri, Ctr_BasCalDebEnt, CAST(Per_RedBasCalDebEnt AS decimal(18,4)) as Per_RedBasCalDebEnt, Ctr_BasCalDebSai, "
									+ "CAST(Alq_AgrDebSai AS decimal(18,4)) as Alq_AgrDebSai, CAST(Alq_AgrDebEnt AS decimal(18,4)) as Alq_AgrDebEnt, "
									+ "CAST(Per_RedBasCalDebSai AS decimal(18,4)) as Per_RedBasCalDebSai , Ctr_TriSai, CAST(C_PerRepIcmReg AS decimal(18,4)) as C_PerRepIcmReg, "
									+ "CAST(C_PerRepIcmLoc AS decimal(18,4)) as C_PerRepIcmLoc , Cst_IcmEnt, Cst_IcmSai FROM RTXCT";

							String dProdu = "Insert Into RTXCT (Cod_RegTri, Cod_ClaTri, Alq_IcmIntReg, Alq_IcmExtReg, "
									+ "Per_RedBasCalIntReg, Per_RedBasCalExtReg, Alq_AgrDebEntIcm, Alq_IcmIntLoc, "
									+ "Alq_IcmExtLoc, Per_RedBasCalIntLoc, Per_RedBasCalExtLoc, Ctrl_SujSubTri, "
									+ "Ctrl_BasCalDebEnt, Per_RedBasCalDebEnt, Ctrl_BasCalDebSai, Alq_AgrDebSai, "
									+ "Alq_AgrDebEnt, Per_RedBasCalDebSai, Ctr_TriSai, C_PerRepIcmReg, "
									+ "C_PerRepIcmLoc, Cod_ImpNotEnt, Cod_ImpNotSai"
									+ ")Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							try (PreparedStatement pVmd = vmd
									.prepareStatement(vProdu);
									PreparedStatement pDmd = dmd
											.prepareStatement(dProdu)) {
								ResultSet rs = pVmd.executeQuery();

								// contar a qtde de registros
								int registros = contaRegistros("RTXCT", vmd);
								progressBar2.setMaximum(registros);
								registros = 0;

								while (rs.next()) {
									// grava no atacado

									pDmd.setInt(1, rs.getInt("Cod_RegTri"));
									pDmd.setString(2,
											rs.getString("Cod_ClaTri"));
									pDmd.setBigDecimal(3,
											rs.getBigDecimal("Alq_IcmIntReg"));
									pDmd.setBigDecimal(4,
											rs.getBigDecimal("Alq_IcmExtReg"));
									pDmd.setBigDecimal(
											5,
											rs.getBigDecimal("Per_RedBasCalIntReg"));
									pDmd.setBigDecimal(
											6,
											rs.getBigDecimal("Per_RedBasCalExtReg"));
									pDmd.setBigDecimal(7, rs
											.getBigDecimal("Alq_AgrDebEntIcm"));
									pDmd.setBigDecimal(8,
											rs.getBigDecimal("Alq_IcmIntLoc"));
									pDmd.setBigDecimal(9,
											rs.getBigDecimal("Alq_IcmExtLoc"));
									pDmd.setBigDecimal(
											10,
											rs.getBigDecimal("Per_RedBasCalIntLoc"));
									pDmd.setBigDecimal(
											11,
											rs.getBigDecimal("Per_RedBasCalExtLoc"));
									pDmd.setBoolean(12,
											rs.getBoolean("Ctr_SujSubTri"));
									pDmd.setString(13,
											rs.getString("Ctr_BasCalDebEnt"));
									pDmd.setBigDecimal(
											14,
											rs.getBigDecimal("Per_RedBasCalDebEnt"));
									pDmd.setString(15,
											rs.getString("Ctr_BasCalDebSai"));
									pDmd.setBigDecimal(16,
											rs.getBigDecimal("Alq_AgrDebSai"));
									pDmd.setBigDecimal(17,
											rs.getBigDecimal("Alq_AgrDebEnt"));
									pDmd.setBigDecimal(
											18,
											rs.getBigDecimal("Per_RedBasCalDebSai"));
									pDmd.setString(19,
											rs.getString("Ctr_TriSai"));
									pDmd.setBigDecimal(20,
											rs.getBigDecimal("C_PerRepIcmReg"));
									pDmd.setBigDecimal(21,
											rs.getBigDecimal("C_PerRepIcmLoc"));
									pDmd.setString(22,
											rs.getString("Cst_IcmEnt"));
									pDmd.setString(23,
											rs.getString("Cst_IcmSai"));
									// todo demais campotry

									pDmd.executeUpdate();

									registros++;
									progressBar2.setValue(registros);
								}
								System.out.println("Funcionou RTXCT");
								pVmd.close();
								pDmd.close();

								progressBar2.setValue(0);
							}
							progressBar.setValue(17);
						}

						// CLASS
						if (cboxCLASS.isSelected()) {

							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM CLASS");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(18);
							}

							progressBar2.setValue(0);

							String vProdu = "SELECT * FROM CLASS";
							String dProdu = "Insert Into CLASS (Codigo, Descricao, Nivel) Values (?,?,?)";
							try (PreparedStatement pVmd = vmd
									.prepareStatement(vProdu);
									PreparedStatement pDmd = dmd
											.prepareStatement(dProdu)) {
								ResultSet rs = pVmd.executeQuery();

								// contar a qtde de registros
								int registros = contaRegistros("CLASS", vmd);
								progressBar2.setMaximum(registros);
								registros = 0;

								while (rs.next()) {
									// grava no atacado
									pDmd.setString(1,
											rs.getString("Cod_Classi"));
									pDmd.setString(2,
											rs.getString("Des_Classi"));
									pDmd.setInt(3, rs.getInt("Num_Nivel"));
									// todo demais campo
									pDmd.executeUpdate();

									registros++;
									progressBar2.setValue(registros);
								}
								System.out.println("Funcionou CLASS");
								pVmd.close();
								pDmd.close();

								progressBar2.setValue(0);
							}

							progressBar.setValue(19);

						}

						// GRPRC
						if (cboxGRPRC.isSelected()) {

							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM GRPRC");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(20);
							}

							progressBar2.setValue(0);

							String vProdu = "SELECT * FROM GRPRC";
							String dProdu = "Insert Into GRPRC (Cod_GrpPrc, Des_GrpPrc, Per_Markup) Values (?,?,?)";
							try (PreparedStatement pVmd = vmd
									.prepareStatement(vProdu);
									PreparedStatement pDmd = dmd
											.prepareStatement(dProdu)) {
								ResultSet rs = pVmd.executeQuery();

								// contar a qtde de registros
								int registros = contaRegistros("GRPRC", vmd);
								progressBar2.setMaximum(registros);
								registros = 0;

								while (rs.next()) {
									// grava no atacado
									pDmd.setString(1,
											rs.getString("Cod_GrpPrc"));
									pDmd.setString(2,
											rs.getString("Des_GrpPrc"));
									pDmd.setInt(3, rs.getInt("Per_Markup"));
									// todo demais campo
									pDmd.executeUpdate();

									registros++;
									progressBar2.setValue(registros);
								}
								System.out.println("Funcionou GRPRC");
								pVmd.close();
								pDmd.close();

								progressBar2.setValue(0);
							}
							progressBar.setValue(21);
						}

						// SBBAS
						if (cboxSBBAS.isSelected()) {

							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM SBBAS");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(22);
							}

							progressBar2.setValue(0);

							String vProdu = "SELECT * FROM SBBAS";
							String dProdu = "Insert Into SBBAS (Codigo, Descricao, Num_CodDCB) Values (?,?,?)";
							try (PreparedStatement pVmd = vmd
									.prepareStatement(vProdu);
									PreparedStatement pDmd = dmd
											.prepareStatement(dProdu)) {
								ResultSet rs = pVmd.executeQuery();

								// contar a qtde de registros
								int registros = contaRegistros("SBBAS", vmd);
								progressBar2.setMaximum(registros);
								registros = 0;

								while (rs.next()) {
									// grava no atacado
									pDmd.setInt(1, prox(dmd, "Codigo", "SBBAS"));
									pDmd.setString(2,
											rs.getString("Des_SubBas"));
									pDmd.setString(3, rs.getString("Cod_DCB"));
									// todo demais campo
									pDmd.executeUpdate();

									registros++;
									progressBar2.setValue(registros);
								}
								System.out.println("Funcionou SBBAS");
								pVmd.close();
								pDmd.close();

								progressBar2.setValue(0);
							}
							progressBar.setValue(23);
						}

						// TBNCM
						if (cboxTBNCM.isSelected()) {

							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM TBNCM");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(24);
							}

							progressBar2.setValue(0);

							String vProdu = "SELECT * FROM TBNCM";
							String dProdu = "Insert Into TBNCM (Cod_Ncm, Des_Ncm, Cst_PisEntTri, Cst_PisSaiTri, Cst_PisEntNaoTri, Cst_PisSaiNaoTri, Cst_CofEntTri, Cst_CofSaiTri, Cst_CofEntNaoTri, Cst_CofSaiNaoTri) Values (?,?,?,?,?,?,?,?,?,?)";
							try (PreparedStatement pVmd = vmd
									.prepareStatement(vProdu);
									PreparedStatement pDmd = dmd
											.prepareStatement(dProdu)) {
								ResultSet rs = pVmd.executeQuery();

								// contar a qtde de registros
								int registros = contaRegistros("TBNCM", vmd);
								progressBar2.setMaximum(registros);
								registros = 0;

								while (rs.next()) {
									// grava no atacado
									pDmd.setString(1, rs.getString("Cod_Ncm"));
									pDmd.setString(2, rs.getString("Des_Ncm"));
									pDmd.setString(3,
											rs.getString("Cst_PisEntTri"));
									pDmd.setString(4,
											rs.getString("Cst_PisSaiTri"));
									pDmd.setString(5,
											rs.getString("Cst_PisEntNaoTri"));
									pDmd.setString(6,
											rs.getString("Cst_PisSaiNaoTri"));
									pDmd.setString(7,
											rs.getString("Cst_CofEntTri"));
									pDmd.setString(8,
											rs.getString("Cst_CofSaiTri"));
									pDmd.setString(9,
											rs.getString("Cst_CofEntNaoTri"));
									pDmd.setString(10,
											rs.getString("Cst_CofSaiNaoTri"));
									// todo demais campo
									pDmd.executeUpdate();

									registros++;
									progressBar2.setValue(registros);
								}
								System.out.println("Funcionou TBNCM");
								pVmd.close();
								pDmd.close();

								progressBar2.setValue(0);
							}
							progressBar.setValue(25);
						}

						// FABRI
						if (cboxFABRI.isSelected()) {

							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM FABRI");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(26);
							}

							progressBar2.setValue(0);

							String vProdu = "SELECT * FROM FABRI";
							String dProdu = "Insert Into FABRI (Codigo, Fantasia, Cgc_Cpf, Cod_ForPref) Values (?,?,?,1)";
							try (PreparedStatement pVmd = vmd
									.prepareStatement(vProdu);
									PreparedStatement pDmd = dmd
											.prepareStatement(dProdu)) {
								ResultSet rs = pVmd.executeQuery();

								// contar a qtde de registros
								int registros = contaRegistros("FABRI", vmd);
								progressBar2.setMaximum(registros);
								registros = 0;

								while (rs.next()) {
									// grava no atacado
									pDmd.setInt(1, rs.getInt("Cod_Fabric"));
									pDmd.setString(2,
											rs.getString("Des_Fabric"));
									pDmd.setString(3, rs.getString("Num_Cnpj"));
									// todo demais campo
									pDmd.executeUpdate();

									registros++;
									progressBar2.setValue(registros);
								}
								System.out.println("Funcionou FABRI");
								pVmd.close();
								pDmd.close();

								progressBar2.setValue(0);
							}
							progressBar.setValue(27);
						}

						// PRODU

						progressBar2.setValue(0);

						if (cboxPRODU.isSelected()) {

							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM PRODU");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(28);
							}

							String vProdu = "select p.Cod_Produt, p.Cod_EAN, p.Des_Produt, p.Cod_Fabric, p.Cod_Classi, p.Cod_Clatri, p.Ctr_Preco, p.Ctr_Lista, p.Cod_Grpprc, p.Ctr_Origem, Isnull(p.Cod_Ncm,p.Cod_Clafis) as Cod_Ncm, x.Prc_VenAtu, p.Des_Univen, p.Qtd_Embven, p.Cod_Subbas, p.Ctr_Venda, p.Des_UniCom from PRODU p "
									+ "inner join prxlj x on (x.Cod_Produt = p.Cod_Produt and x.Cod_Loja=(select CodLojCon from param)) where LEN(Cod_EAN) < 14";
							String dProdu = "Insert Into PRODU (Codigo, Cod_EAN, Descricao, Cod_Fabricante, Cod_Classif, Cod_Clatri, Ctrl_Preco, Tip_Lispis, Cod_Grpprc, Ctrl_Origem, Cod_Clafis, Prc_Venda, Unidade_Venda, Qtd_Embalagem, Cod_Subbas, Ctrl_Venda, Und_EmbCmp, QTD_UndVen, Qtd_FraVen, Tipo, Localizacao) Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1,1,'R',1)";
							try (PreparedStatement pVmd = vmd
									.prepareStatement(vProdu);
									PreparedStatement pDmd = dmd
											.prepareStatement(dProdu)) {
								ResultSet rs = pVmd.executeQuery();

								// contar a qtde de registros
								int registros = contaRegistros("PRODU", vmd);
								progressBar2.setMaximum(registros);
								registros = 0;

								while (rs.next()) {

									// grava no atacado
									pDmd.setInt(1, prox(dmd, "Codigo", "PRODU"));
									pDmd.setString(2, rs.getString("Cod_EAN"));
									pDmd.setString(3,
											rs.getString("Des_Produt"));
									pDmd.setString(4,
											rs.getString("Cod_Fabric"));
									pDmd.setString(5,
											rs.getString("Cod_Classi"));
									pDmd.setString(6,
											rs.getString("Cod_Clatri"));
									pDmd.setString(7, rs.getString("Ctr_Preco"));
									pDmd.setString(8, rs.getString("Ctr_Lista"));
									pDmd.setString(9,
											rs.getString("Cod_Grpprc"));
									pDmd.setString(10,
											rs.getString("Ctr_Origem"));
									pDmd.setString(11, rs.getString("cod_ncm"));
									pDmd.setString(12,
											rs.getString("Prc_Venatu"));
									pDmd.setString(13,
											rs.getString("Des_UniVen"));
									pDmd.setString(14,
											rs.getString("Qtd_EmbVen"));
									pDmd.setString(15,
											rs.getString("Cod_SubBas"));
									pDmd.setString(16,
											rs.getString("Ctr_Venda"));
									pDmd.setString(17,
											rs.getString("Des_UniCom"));
									// todo demais campo
									pDmd.executeUpdate();

									registros++;
									progressBar2.setValue(registros);
								}
								System.out.println("Funcionou PRODU");
								pVmd.close();
								pDmd.close();

								progressBar2.setValue(0);
							}
							progressBar.setValue(29);
						}

						// PCFIN
						if (cboxPCFIN.isSelected()) {

							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM PCFIN");
								stmt.close();
								System.out.println("Deletou PCFIN");
								progressBar.setValue(30);
							}

							progressBar2.setValue(0);

							String dProdu_1 = "Insert Into PCFIN (Isn_CtaFin, Cod_CtaFin , Des_CtaFin, Cod_NatCtaFin, Tip_CtaFin) Values (1, '01', 'Despesas', 'S', 'S')";
							String dProdu_2 = "Insert Into PCFIN (Isn_CtaFin, Cod_CtaFin , Des_CtaFin, Cod_NatCtaFin, Tip_CtaFin) Values (2, '01.001', 'Despesas Operacionais', 'S', 'S')";
							String dProdu_3 = "Insert Into PCFIN (Isn_CtaFin, Cod_CtaFin , Des_CtaFin, Cod_NatCtaFin, Tip_CtaFin) Values (3, '01.001.001', 'Despesas Vendas', 'S', 'S')";
							String dProdu_4 = "Insert Into PCFIN (Isn_CtaFin, Cod_CtaFin , Des_CtaFin, Cod_NatCtaFin, Tip_CtaFin) Values (4, '01.001.001.001', 'Fornecedores', 'S', 'S')";
							try (PreparedStatement pDmd_1 = dmd
									.prepareStatement(dProdu_1);
									PreparedStatement pDmd_2 = dmd
											.prepareStatement(dProdu_2);
									PreparedStatement pDmd_3 = dmd
											.prepareStatement(dProdu_3);
									PreparedStatement pDmd_4 = dmd
											.prepareStatement(dProdu_4)) {

								// contar a qtde de registros
								int registros = contaRegistros("PCFIN", vmd);
								progressBar2.setMaximum(registros);
								registros = 0;

								// todo demais campo
								pDmd_1.executeUpdate();
								pDmd_2.executeUpdate();
								pDmd_3.executeUpdate();
								pDmd_4.executeUpdate();

								registros++;
								progressBar2.setValue(registros);

								System.out.println("Funcionou PCFIN");
								pDmd_1.close();
								pDmd_2.close();
								pDmd_3.close();
								pDmd_4.close();

								progressBar2.setValue(0);
							}

							progressBar.setValue(31);

						}

						// FORNE

						progressBar2.setValue(0);

						if (cboxFORNE.isSelected()) {

							try (Statement stmt = dmd.createStatement()) {
								stmt.executeUpdate("DELETE FROM FORNE");
								stmt.close();
								System.out.println("Deletou FORNE");
								progressBar.setValue(32);
							}

							String vProdu = "select *, SUBSTRING(Des_Bairro, 0, 20) as Bairro, Isnull(Cod_RegTri, (select top 1 Cod_RegTri from RGTRI order by Cod_RegTri)) as WCod_RegTri from FORNE";
							String dProdu = "Insert Into FORNE (Codigo, Tipo, Razao_Social, Fantasia, Pessoa, Cgc_Cpf, Cgf_Rg, Endereco_Com, Estado_Com, Cidade_Com, Bairro_Com, Cep_Com, Cod_RegTri, Fone_Com, Fax_Com, Per_Markup, Per_Dsc1, Observacao) Values (?,'R',?,?,'J',?,?,?,?,?,?,?,?,?,?,?,?,?)";
							try (PreparedStatement pVmd = vmd
									.prepareStatement(vProdu);
									PreparedStatement pDmd = dmd
											.prepareStatement(dProdu)) {
								ResultSet rs = pVmd.executeQuery();

								// contar a qtde de registros
								int registros = contaRegistros("PRODU", vmd);
								progressBar2.setMaximum(registros);
								registros = 0;

								while (rs.next()) {

									// grava no atacado
									pDmd.setInt(1, prox(dmd, "Codigo", "FORNE"));
									pDmd.setString(2,
											rs.getString("Des_RazSoc"));
									pDmd.setString(3,
											rs.getString("Des_Fantas"));
									pDmd.setString(4,
											rs.getString("Num_CgcCpf"));
									pDmd.setString(5, rs.getString("Num_CgfRg"));
									pDmd.setString(6,
											rs.getString("Des_Endere"));
									pDmd.setString(7,
											rs.getString("Des_Estado"));
									pDmd.setString(8,
											rs.getString("Des_Cidade"));
									pDmd.setString(9, rs.getString("Bairro"));
									pDmd.setString(10, rs.getString("Num_Cep"));
									pDmd.setInt(11, rs.getInt("WCod_RegTri"));
									pDmd.setString(12, rs.getString("Num_Fone"));
									pDmd.setString(13, rs.getString("Num_Fax"));
									pDmd.setBigDecimal(14,
											rs.getBigDecimal("Per_Markup"));
									pDmd.setString(15,
											rs.getString("Per_Descon"));
									pDmd.setString(16,
											rs.getString("Des_Observ"));
									// todo demais campo
									pDmd.executeUpdate();

									registros++;
									progressBar2.setValue(registros);
								}
								System.out.println("Funcionou FORNE");
								pVmd.close();
								pDmd.close();

								progressBar2.setValue(0);
							}
							progressBar.setValue(33);
						}

						// JOptionPane.showMessageDialog(null,
						// "Dados importados com sucesso.");
						JOptionPane.showMessageDialog(getContentPane(),
								"Processamento de dados realizado com sucesso",
								"Informação", JOptionPane.INFORMATION_MESSAGE);

					} 
				} else {
					JOptionPane.showMessageDialog(getContentPane(),
							"Processamento de dados cancelado", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
				}

				return null;
			}

			@Override
			protected void done() {
				try {
					progressBar.setValue(0);
					btn_limpa_dados.setEnabled(true);
					btn_processa.setEnabled(true);
					getContentPane().setCursor(Cursor.getDefaultCursor());
					// Descobre como está o processo. É responsável por lançar
					// as exceptions
					get();
					// JOptionPane.showMessageDialog(getContentPane(),
					// "Processamento de dados realizado com sucesso",
					// "Informação", JOptionPane.INFORMATION_MESSAGE);
				} catch (ExecutionException e) {
					final String msg = String.format(
							"Erro ao exportar dados: %s", e.getCause()
									.toString());
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(getContentPane(),
									"Erro ao exportar: " + msg, "Erro",
									JOptionPane.ERROR_MESSAGE);
						}
					});
				} catch (InterruptedException e) {
					System.out
							.println("Processo de exportação foi interrompido");
				}
			}
		}

		btn_processa = new JButton("Processa");
		btn_processa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().setCursor(
						Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				new ProcessaWorker().execute();
			}
		});

		class LimpaDadosWorker extends SwingWorker<Void, Void> {

			@Override
			protected Void doInBackground() throws Exception {
				progressBar.setValue(0);
				progressBar.setMaximum(11);
				btn_limpa_dados.setEnabled(false);
				btn_processa.setEnabled(false);

				try (Connection dmd = new Conexao().getConnection(
						txtDmdServidor.getText(), txtDmdBanco.getText())) {

					// APAGANDO DADOS
					// FORNE
					if (cboxFORNE.isSelected()) {
						try (Statement stmt = dmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM FORNE");
							stmt.close();
							System.out.println("Deletou FORNE");
							progressBar.setValue(1);
						}

					}

					// RGTRI
					if (cboxRGTRI.isSelected()) {
						try (Statement stmt = dmd.createStatement()) {
							stmt.executeUpdate("update ESTAB set Cod_RegTri=null");
							stmt.executeUpdate("DELETE FROM RGTRI");
							System.out.println("fez update");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(2);
						}
					}

					// PRODUTO
					if (cboxPRODU.isSelected()) {
						try (Statement stmt = dmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM PRODU");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(3);
						}
					}

					// FABRI
					if (cboxFABRI.isSelected()) {
						try (Statement stmt = dmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM FABRI");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(4);
						}
					}

					// CLASS
					if (cboxCLASS.isSelected()) {
						try (Statement stmt = dmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM CLASS");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(5);
						}
					}

					// CLTRI
					if (cboxCLTRI.isSelected()) {
						try (Statement stmt = dmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM CLTRI");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(6);
						}
					}

					// GRPRC
					if (cboxGRPRC.isSelected()) {
						try (Statement stmt = dmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM GRPRC");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(7);
						}
					}

					// SBBAS
					if (cboxSBBAS.isSelected()) {
						try (Statement stmt = dmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM SBBAS");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(8);
						}
					}

					// TBNCM
					if (cboxTBNCM.isSelected()) {
						try (Statement stmt = dmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM TBNCM");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(9);
						}
					}

					// RTXCT
					if (cboxRTXCT.isSelected()) {
						try (Statement stmt = dmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM RTXCT");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(10);
						}
					}

					// PCFIN
					if (cboxPCFIN.isSelected()) {
						try (Statement stmt = dmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM PCFIN");
							stmt.close();
							System.out.println("Deletou PCFIN");
							progressBar.setValue(11);
						}

					}
					// JOptionPane.showMessageDialog(null,
					// "Dados excluídos com sucesso.");

				}
				return null;
			}

			@Override
			protected void done() {
				try {
					progressBar.setValue(0);
					btn_limpa_dados.setEnabled(true);
					btn_processa.setEnabled(true);
					getContentPane().setCursor(Cursor.getDefaultCursor());

					// Descobre como está o processo. É responsável por lançar
					// as exceptions
					get();
					
					JOptionPane.showMessageDialog(getContentPane(),
							"Limpeza de dados realizada com sucesso", "Info",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (ExecutionException e) {
					final String msg = String.format("Erro ao limpar dados: %s", e.getCause().toString());
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(getContentPane(),
									"Erro ao limpar: " + msg, "Erro",
									JOptionPane.ERROR_MESSAGE);
						}
					});
				} catch (InterruptedException e) {
					System.out.println("Processo de exportação foi interrompido");
				}
			}
		}

		btn_limpa_dados = new JButton("Limpa Dados");
		btn_limpa_dados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getContentPane().setCursor(
						Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				new LimpaDadosWorker().execute();
			}
		});
		panel.add(btn_limpa_dados);
		panel.add(btn_processa);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new MigLayout("", "[][][][][][][grow,fill]",
				"[][][][][][]"));

		cboxRGTRI = new JCheckBox("1-RGTRI");
		cboxRGTRI.setSelected(true);
		panel_1.add(cboxRGTRI, "cell 0 0");

		cboxCLASS = new JCheckBox("4-CLASS");
		cboxCLASS.setSelected(true);
		panel_1.add(cboxCLASS, "cell 1 0");

		cboxTBNCM = new JCheckBox("7-TBNCM");
		cboxTBNCM.setSelected(true);
		panel_1.add(cboxTBNCM, "cell 2 0");

		cboxPCFIN = new JCheckBox("10-PCFIN");
		cboxPCFIN.setSelected(true);
		panel_1.add(cboxPCFIN, "cell 3 0");

		cboxCLTRI = new JCheckBox("2-CLTRI");
		cboxCLTRI.setSelected(true);
		panel_1.add(cboxCLTRI, "cell 0 1");

		cboxGRPRC = new JCheckBox("5-GRPRC");
		cboxGRPRC.setSelected(true);
		panel_1.add(cboxGRPRC, "cell 1 1");

		cboxFABRI = new JCheckBox("8-FABRI");
		cboxFABRI.setSelected(true);
		panel_1.add(cboxFABRI, "cell 2 1");

		cboxFORNE = new JCheckBox("11-FORNE");
		cboxFORNE.setSelected(true);
		panel_1.add(cboxFORNE, "cell 3 1");

		cboxRTXCT = new JCheckBox("3-RTXCT");
		cboxRTXCT.setSelected(true);
		panel_1.add(cboxRTXCT, "cell 0 2");

		cboxSBBAS = new JCheckBox("6-SBBAS");
		cboxSBBAS.setSelected(true);
		panel_1.add(cboxSBBAS, "cell 1 2");

		cboxPRODU = new JCheckBox("9-PRODU");
		cboxPRODU.setSelected(true);
		panel_1.add(cboxPRODU, "cell 2 2");

		progressBar = new JProgressBar();
		progressBar.setMaximum(33);
		panel_1.add(progressBar, "cell 0 4 7 1,growx");

		progressBar2 = new JProgressBar();
		panel_1.add(progressBar2, "cell 0 5 7 1,growx");

	}

	private int prox(Connection conn, String chave, String tabela) {
		try (Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery("(Select Isnull(MAX(" + chave
						+ "), 0) + 1 From " + tabela + ")")) {
			if (rs.next())
				return rs.getInt(1);
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	private int contaRegistros(String tabela, Connection c) throws SQLException {
		String sql = "SELECT count(*) qtde FROM " + tabela;
		try (PreparedStatement ps = c.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		}
	}

	// private int contaRegistros2(String tabela, String where, Connection c)
	// throws SQLException {
	// String sql = "SELECT count(*) qtde FROM "+tabela+" "+where;
	// try (PreparedStatement ps = c.prepareStatement(sql)) {
	// ResultSet rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1);
	// }
	// return 0;
	// }
	// }
	// public JCheckBox getCboxFORNE() {
	// return cboxFORNE;
	// }
	public JCheckBox getCboxRGTRI() {
		return cboxRGTRI;
	}

	public JCheckBox getCboxRTXCT() {
		return cboxRTXCT;
	}

	public JCheckBox getCboxPCFIN() {
		return cboxPCFIN;
	}

	public JCheckBox getCboxFORNE() {
		return cboxFORNE;
	}
}
