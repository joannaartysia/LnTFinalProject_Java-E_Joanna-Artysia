//Joanna Artysia- Java E
import java.sql.*;
public class Connector {
	
	Connection connectingaja;
	ResultSet resultingaja;
	Statement yourstate;
	
	boolean 
	checking = true;
	
	public Connector() {
		
		while (checking) {
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		connectingaja = DriverManager.getConnection("jdbc:mysql://localhost:3306/pt_pudding", "root", "");
		System.out.println("Sukses Connecting :D");
		
		yourstate=connectingaja.createStatement();
		checking = false;
		yourstate.executeUpdate("USE pt_pudding");
		break;
		
	} catch (Exception e) {}
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		connectingaja = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
		System.out.println("Menghubungkan ke base");
		yourstate=connectingaja.createStatement();

		
		yourstate.executeUpdate("create database pt_pudding");
		System.out.println("DATABASE pt_pudding berhasil dibuat HOREEE :D");
		yourstate.executeUpdate("USE pt_pudding");
		} catch (Exception e) {}
	}
		
	InisialisasiTable();
}
	
	public void InisialisasiTable() {
		
	try {
		yourstate.executeUpdate("create table Menu (KodeMenu CHAR(6) primary key, NamaMenu VARCHAR(250) not null, HargaMenu Integer, StokMenu Integer)");
		
		System.out.println("===Membuat Table Menu===");
	
	} catch (Exception e) {}
	}
	
	public void insert (String KodeMenu, String NamaMenu, Integer HargaMenu, Integer StokMenu) {
	try {
		
		String CODE = "insert into Menu values ('" + KodeMenu + "','" + NamaMenu + "'," + HargaMenu + "," + StokMenu + ")";
		System.out.println(CODE);
		yourstate.executeUpdate(CODE);
		
		System.out.println("Berhasil melakukan insert:D : " + CODE);
	} catch (Exception e) {e.printStackTrace();}
	
	}
	
	public ResultSet select() {
	try {
		resultingaja = yourstate.executeQuery("select * from Menu");
		System.out.println("Berhasil memilih menu yang diinginkan :D");
	} catch (Exception e) {e.printStackTrace(); 
	System.out.println("select error");
	}
	
	return resultingaja;
	}
	
	public void update (String KodeMenu, Integer HargaMenu, Integer StokMenu) {
	try {
		String code = "update Menu set HargaMenu = " + HargaMenu + ", StokMenu = " + StokMenu + " WHERE KodeMenu = '" + KodeMenu + "'";
		System.out.println(code);
		yourstate.executeUpdate(code);
		
		System.out.println("Berhasil: " + code);
	} catch (Exception e) {e.printStackTrace();}
	}
	
	public void delete (String kodeMenu) {
	try {
		String codee = "delete from Menu where KodeMenu = '" + kodeMenu + "'";
		System.out.println(codee);
		yourstate.executeUpdate(codee);
		
		System.out.println("Success: " + codee);
	} catch (Exception e) {e.printStackTrace();}
	
	}
	
	public Connection getCon() 
	{
		return connectingaja;}
	
	public void close() 
	{
		try {connectingaja.close();
	} catch (Exception e) {}

	
	}
}
