//Joanna Artysia - Java E

import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.table.*;
public class Main extends JFrame implements ActionListener{
Connector connectingaja;
ResultSet resultingaja;
JTextField txtKode, txtNama, txtHarga, txtStok;
DefaultTableModel tableModel;
JButton MenuInsert, MenuUpdate, MenuDelete, MenuClear;
JLabel lblKode, lblNama, lblHarga, lblStok;
JPanel top, centerMain, centerForm, centerInputPanel, centerButtonPanel, centerTablePanel, bottom;
JLabel title;
JTable table;
JScrollPane scrolling;
SpringLayout formSpring;
Vector<Vector<Object>> selected;
	
	public Main() {
		connectingaja = new Connector();
		selected = new Vector<Vector<Object>>();
		
		TampilanGUI();
	}
	
	public void TampilanGUI() {
		
		setTitle("PT PUDDING");
		setSize(920,750);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		top = new JPanel();
		title = new JLabel("PT PUDDING", SwingConstants.CENTER);
		title.setFont(new Font("Aldhabi", Font.BOLD, 28));
		top.add(title);
		top.setBorder(new EmptyBorder(10,20,10,20));
		centerMain = new JPanel();
		initialitationCenterForm();
		initialitationCenterView();
		centerMain.setBorder(new EmptyBorder(4, 30, 4, 30));
		centerMain.add(centerForm);
		centerMain.add(centerTablePanel);
		centerMain.setLayout(new BoxLayout(centerMain, BoxLayout.PAGE_AXIS));
		bottom = new JPanel();
		setLayout(new BorderLayout());
		add(centerMain, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		add(top, BorderLayout.NORTH);
		setVisible(true);
	}

	public void view() {
		
		try {
			resultingaja = connectingaja.select();
		} catch (Exception ress) {
			ress.printStackTrace();
		}
		
		try {
			while(resultingaja.next()) {
				String[] rekapan = {resultingaja.getString(1), resultingaja.getString(2), resultingaja.getString(3), resultingaja.getString(4)};
				tableModel.addRow(rekapan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}
	
	public void initialitationCenterForm() {
		
		initFormFields();
		Thebuttom();
		centerForm.add(centerInputPanel);
		centerForm.add(centerButtonPanel);
		centerForm = new JPanel();
		centerInputPanel = new JPanel();
		centerButtonPanel = new JPanel();
		centerForm.setLayout(new BoxLayout(centerForm, BoxLayout.PAGE_AXIS));
		
	}
	
	public void initialitationCenterView() {
		centerTablePanel = new JPanel();
		String [] codess = {"Kode Menu", "Nama Menu", "Harga Menu", "Stok Menu"};
		tableModel = new DefaultTableModel(codess, 0);
		view();
		
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
		txtKode.setText(tableModel.getValueAt(table.getSelectedRow(), 0).toString());
		txtNama.setText(tableModel.getValueAt(table.getSelectedRow(), 1).toString());
		txtHarga.setText(tableModel.getValueAt(table.getSelectedRow(), 2).toString());
		txtStok.setText(tableModel.getValueAt(table.getSelectedRow(), 3).toString());
		
		System.out.println(table.getSelectedRow());
			}
		}
		
);
		scrolling = new JScrollPane(table);
		centerTablePanel.add(scrolling);
	}
	
	public void initFormFields() {
			
		centerInputPanel.setLayout(new BoxLayout(centerInputPanel, BoxLayout.LINE_AXIS));
		lblKode = new JLabel("Kode Menu: ");
		lblNama = new JLabel("Nama Menu: ");
		lblHarga = new JLabel("Harga Menu: ");
		lblStok = new JLabel("Stok Menu: ");
		txtKode = new JTextField("", 12); 
		txtNama = new JTextField("", 12); 
		txtHarga = new JTextField("", 12); 
		txtStok = new JTextField("", 12);
		centerInputPanel.add(lblKode);
		centerInputPanel.add(txtKode);		
		centerInputPanel.add(lblNama);
		centerInputPanel.add(txtNama);		
		centerInputPanel.add(lblHarga);
		centerInputPanel.add(txtHarga);		
		centerInputPanel.add(lblStok);
		centerInputPanel.add(txtStok);
		
	}
	public void Thebuttom() {
		MenuInsert = new JButton("Insert");
		MenuClear = new JButton("Clear");
		MenuUpdate = new JButton("Update");
		MenuDelete = new JButton("Delete");
		
		MenuInsert.addActionListener(new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent arg0) {
				if(theBlankCheck(txtKode.getText(), txtNama.getText(),txtStok.getText() , txtHarga.getText())==false) {
					
					if (cekKode(txtKode.getText())==false) {
						connectingaja.insert(txtKode.getText(), txtNama.getText(), Integer.parseInt(txtHarga.getText()), Integer.parseInt(txtStok.getText()));
						tableModel.addRow(new String[] {txtKode.getText(), txtNama.getText(), txtHarga.getText(), txtStok.getText()});
						JOptionPane.showMessageDialog(null, "Insert berhasil dilakukan!");
					
					} else JOptionPane.showMessageDialog(null, "Insert gagal dilakukan, 'Kode Menu' harus diawali dengan 'PD-'sebanayk 6 karakter, misal PD-123");
				} else JOptionPane.showMessageDialog(null, "Insert gagal, field tidak diperbolehkan kosong");
			}
		});
		
		MenuUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(theBlankCheck(txtKode.getText(), txtNama.getText(), txtHarga.getText(), txtStok.getText())==false) {
					if (table.getSelectedRow()>=0) {
						connectingaja.update(txtKode.getText(), Integer.parseInt(txtHarga.getText()), Integer.parseInt(txtStok.getText()));
						tableModel.setValueAt(Integer.parseInt(txtHarga.getText()), table.getSelectedRow(), 2);
						tableModel.setValueAt(Integer.parseInt(txtStok.getText()), table.getSelectedRow(), 3);
						JOptionPane.showMessageDialog(null, "Update success. Price and stock changed.");
					} else JOptionPane.showMessageDialog(null, "Update failed. Please select a valid row.");
				} else JOptionPane.showMessageDialog(null, "Update failed. Field must not be empty");
			}			
		}
);
		
		MenuDelete.addActionListener(new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent arg0) {
				
				if (table.getSelectedRow()>=0) {

					connectingaja.delete(tableModel.getValueAt(table.getSelectedRow(), 0).toString());
					tableModel.removeRow(table.getSelectedRow());
					
					JOptionPane.showMessageDialog(null, "Delete berhasil dilakukan");
				} else JOptionPane.showMessageDialog(null, "Delete gagal dilakukan, lakukan select row yang benar");
			}			
		}
);
		
		MenuClear.addActionListener(new ActionListener() {
			@Override
public void actionPerformed(ActionEvent arg0) {
txtKode.setText("");txtStok.setText("");txtNama.setText("");txtHarga.setText("");
}}
); 
		
centerButtonPanel.add(MenuInsert);centerButtonPanel.add(MenuClear);centerButtonPanel.add(MenuUpdate);centerButtonPanel.add(MenuDelete);
}
	
	public boolean theBlankCheck(String KodeMenu, String NamaMenu, String HargaMenu, String StokMenu) {
		if (KodeMenu.isEmpty() || KodeMenu.equals("")) return true;
		if (StokMenu.isEmpty() || StokMenu.equals("")) return true;
		if (NamaMenu.isEmpty() || NamaMenu.equals("")) return true;
		if (HargaMenu.isEmpty()|| HargaMenu.equals("")) return true;
		return false;
	}
	public boolean cekKode(String kodeMenu) {
		if (kodeMenu.startsWith("PD-")== false) return true;
		if (kodeMenu.length()!=6) return true;
		if (tableModel.getRowCount()>=1) {
			
			for (int i = 0; i<tableModel.getRowCount(); i++) {
				if (tableModel.getValueAt(i, 0).toString().equals(kodeMenu)) 
					return true;
			}}
		
		return false;}
	public static void main(String[] args) {new Main();}
	@Override
	public void actionPerformed(ActionEvent arg0) {}

}
