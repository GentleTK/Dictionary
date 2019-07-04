package dic;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.io.*;

public class Dic extends Frame implements ActionListener {
	MenuBar menubar=new MenuBar();
	Menu fileMenu,editMenu;
	MenuItem fileenglish,filechinese,editAdd,editmod,editDel;
	TextField inputtext;
	TextArea txt;
	Label label1,label2;
	Button btn1,exit;
	Panel p,p1,p2,p3;		
	private class WindowCloser extends  WindowAdapter{
		public void windowClosing(WindowEvent e){
			System.exit(0);
			}
		}
	/*菜单界面设置*/
	public Dic(){
		super("电子词典");
		setBounds(300, 200, 335, 350);
		setMenuBar(menubar);			
		fileMenu = new Menu("词典类型");
		editMenu = new Menu("编辑词典");				
		fileenglish = new MenuItem("英汉词典");
		filechinese = new MenuItem("汉英词典");			
		editAdd = new MenuItem("添加词汇");
		editmod = new MenuItem("修改词汇");
		editDel = new MenuItem("删除词汇");			
		menubar.add(fileMenu);
		menubar.add(editMenu);			
		fileMenu.add(fileenglish);
		fileMenu.add(filechinese);
		editMenu.add(editAdd);
		editMenu.add(editmod);
		editMenu.add(editDel);
		inputtext = new TextField("",10);
		txt = new TextArea(20,20);
		label1 = new Label("输入要查询的英语单词：");
		label2 = new Label("查询结果：");
		btn1 = new Button("查询");
		exit = new Button("退出");
		p = new Panel(new BorderLayout());
		p2 = new Panel(new FlowLayout(FlowLayout.LEFT,0,0));
		p2.add(label1);
		p2.add(inputtext);
		p2.add(btn1);
		p2.add(exit);
		add(p2,"North");
		p.add(label2,"North");
		p.add(txt,"Center");
		add(p,"Center");	
		setVisible(true);
		setResizable(false);
		validate();
		fileenglish.addActionListener(this);
		filechinese.addActionListener(this);
		exit.addActionListener(this);	
		editAdd.addActionListener(this);	
		editmod.addActionListener(this);	
		editDel.addActionListener(this);	
		btn1.addActionListener(this);
		addWindowListener(new WindowCloser());
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit){
			System.exit(0);
		}
		else if(e.getSource() == btn1){
			txt.setText("");
			if(inputtext.getText().equals("")){
				JOptionPane.showMessageDialog(this,"查询对象不能为空！","警告",JOptionPane.WARNING_MESSAGE);
			}
			else{
				try{
					Listwords();	
				}catch(SQLException ee){}
			}
		}
		else if(e.getSource() == fileenglish){
			label1.setText("输入要查询的英语单词：");
		}
		else if(e.getSource() == filechinese){
			label1.setText("输入要查询的汉语词语：");
		}
		else if( e.getSource() == editAdd){
			Addwin add = new Addwin();
		}
		else if(e.getSource() == editmod){
			Modwin mod = new Modwin();
		}
		else if(e.getSource() == editDel){
			Delwin del = new Delwin();
		}
	}
	public static void main(String[] args){
		Dic m = new Dic();
		String url ="jdbc:derby:MyData;create=true";
		File file = new File(".\\MyData");
		Connection con = null;
		Statement sm = null;
		ResultSet rs = null;
		String Command = null;
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			System.out.println("驱动程序已装载！");
			System.out.println("即将连接数据库！");
		}catch(Exception ex){
			System.out.println("Can not load Jdbc Driver"+ex.getMessage());
			return;
		}
		try{
			if (!file.isDirectory()){
				con = DriverManager.getConnection(url);
				sm = con.createStatement();
				sm.execute("create table mytable (word varchar(20),chinese varchar(20))");
				DatabaseMetaData dmd = con.getMetaData();
				System.out.println("已连接到的数据库："+dmd.getURL());
				System.out.println("所用的驱动程序是："+dmd.getDriverName());
			}	
		}catch(SQLException ex){
			System.out.println("Failed to Connect!");
			System.out.println(ex.getMessage());	
		}
	}
	/*修改词汇窗口设置*/
	class Modwin  extends Frame implements ActionListener{
		private TextField inputword = new TextField("",10);
		private TextArea  wordMeaning = new TextArea(20,20);
		private Label lab1 = new Label("输入要修改的英语单词：");
		private Button mod = new Button("修改");
		private Label lab2 = new Label("输入要修改的中文释义：");
		private class WindowCloser extends WindowAdapter{
			public void windowClosing(WindowEvent we){
				setVisible(false);
			}	
		}
		public Modwin(){
			super("修改词汇");
			setBounds(300,200,335,350);
			setup();
			mod.addActionListener(this);
			addWindowListener(new WindowCloser());
			pack();
		}
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==mod){
					try{
						modwords();
				}catch(SQLException ee){}	
			}
		}
		private void setup(){
			Panel t1,t2;
			t1 = new Panel(new BorderLayout());
			t2 = new Panel(new FlowLayout(FlowLayout.LEFT,5,0));
			t2.add(lab1);
			t2.add(inputword);
			t2.add(mod);
			add(t2,"North");
			t1.add(lab2,"North");
			t1.add(wordMeaning,"Center");
			add(t1,"Center");
			setVisible(true);
			setResizable(false);
			validate();
		}
		/*修改词汇方法*/
		public void modwords() throws SQLException
		{
			String ename,cname;
			try{
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			}catch(ClassNotFoundException e){}
			Connection Ex1Con = DriverManager.getConnection("jdbc:derby:MyData","","");
			Statement Ex1Stmt = Ex1Con.createStatement();
			ResultSet rs=Ex1Stmt.executeQuery("SELECT * FROM mytable");
			boolean boo = false;
			while((boo=rs.next()) == true){
				ename = rs.getString("word");
				cname = rs.getString("chinese");
				if(ename.equals(inputword.getText())){
					String s1 = "'"+inputword.getText().trim()+"'",s2="'"+wordMeaning.getText().trim()+"'";
					String temp = "UPDATE mytable SET chinese="+s2+"WHERE word="+s1;
					Ex1Stmt.executeUpdate(temp);
					JOptionPane.showMessageDialog(this,"记录修改成功！","恭喜",
					JOptionPane.WARNING_MESSAGE);
					dispose();
					break;	
				}
			}
			Ex1Con.close();	
			if(boo == false){
				JOptionPane.showMessageDialog(this,"不存在此单词！","警告",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	/*删除词汇窗口设置*/
	class Delwin  extends Frame implements ActionListener{
		private TextField inputword = new TextField("",10);
		private Label lab1 = new Label("输入要删除的英语单词：");
		private Button cancel = new Button("删除");
		private class WindowCloser extends WindowAdapter{
			public void windowClosing(WindowEvent we){
				setVisible(false);
			}	
		}
		public Delwin(){
			super("删除词汇");
			setBounds(300,200,335,350);
			setup();
			cancel.addActionListener(this);
			addWindowListener(new WindowCloser());
			pack();
		}
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == cancel)
				try{
					delwords();
			}catch(SQLException ee){}	
		}
		private void setup(){
			Panel t1,t2;
			t1 = new Panel(new BorderLayout());
			t2 = new Panel(new FlowLayout(FlowLayout.RIGHT,0,0));
			t2.add(lab1);
			t2.add(inputword);
			t2.add(cancel);
			add(t2,"North");
			add(t1,"Center");
			setVisible(true);
			setResizable(false);
			validate();
		}
		/*删除词汇方法*/
		public void delwords() throws SQLException
		{
			@SuppressWarnings("unused")
			String cname,ename;
			try{
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			}catch(ClassNotFoundException e){}
			Connection Ex1Con = DriverManager.getConnection("jdbc:derby:MyData","","");
			Statement Ex1Stmt = Ex1Con.createStatement();
			ResultSet rs = Ex1Stmt.executeQuery("SELECT * FROM mytable");
			boolean boo = false;
			while((boo = rs.next()) == true){
				ename = rs.getString("word");
				cname = rs.getString("chinese");
				if(ename.equals(inputword.getText())){
					String s1 = "'"+inputword.getText().trim()+"'";
					String temp = "DELETE FROM mytable WHERE word="+s1;
					Ex1Stmt.executeUpdate(temp);
					JOptionPane.showMessageDialog(this,"成功删除记录！","恭喜",JOptionPane.WARNING_MESSAGE);
					dispose();
					break;
				}
			}
			Ex1Con.close();	
			if(boo == false){
				JOptionPane.showMessageDialog(this,"不存在此单词！","警告",
				JOptionPane.WARNING_MESSAGE);
			}	
		}
	}
	/*添加词汇窗口设置*/
	class Addwin  extends Frame implements ActionListener{
		private TextField inputword= new TextField("",10);
		private TextArea  wordMeaning=new TextArea(20,20);
		private Label lab1 = new Label("输入要添加的英语单词：");
		private Button ok = new Button("添加");
		private Label lab2 = new Label("输入要添加的中文释义：");
		private class WindowCloser extends WindowAdapter{
			public void windowClosing(WindowEvent we){
				setVisible(false);
			}	
		}
		public Addwin(){
			super("添加词汇");
			setBounds(300,200,335,350);
			setup();
			ok.addActionListener(this);
			addWindowListener(new WindowCloser());
			pack();
		}
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == ok) {
				try{
					addwords();
				}catch(SQLException ee){}	
			}
		}
		private void setup(){
			Panel t1,t2;
			t1 = new Panel(new BorderLayout());
			t2 = new Panel(new FlowLayout(FlowLayout.RIGHT,5,0));
			t2.add(lab1);
			t2.add(inputword);
			t2.add(ok);
			add(t2,"North");
			t1.add(lab2,"North");
			t1.add(wordMeaning,"Center");
			add(t1,"Center");
			setVisible(true);
			setResizable(false);
			validate();
		}
		/*添加词汇方法*/
		public void addwords() throws SQLException
		{
			String cname,ename;
			try{
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			}catch(ClassNotFoundException e){}
			Connection Ex1Con = DriverManager.getConnection("jdbc:derby:MyData","","");
			Statement Ex1Stmt = Ex1Con.createStatement();
			ResultSet rs = Ex1Stmt.executeQuery("SELECT * FROM mytable");
			boolean boo = false;
			while(boo = rs.next()){
				ename = rs.getString("word");
				cname = rs.getString("chinese");
				System.out.println("ename" + ename);
				if(ename.equals(inputword.getText())){
					JOptionPane.showMessageDialog(this,"此词汇已存在！","警告",
					JOptionPane.WARNING_MESSAGE);
					break;
				}
			}
			if(boo == false){
				String s1 = "'"+inputword.getText().trim()+"'",s2="'"+wordMeaning.getText().trim()+"'";
				String temp = "INSERT INTO mytable VALUES ("+s1+","+s2+")";
				Ex1Stmt.executeUpdate(temp);
				JOptionPane.showMessageDialog(this,"添加成功！","恭喜",JOptionPane.WARNING_MESSAGE);
				dispose();
			}
			Ex1Con.close();	
		}
	}
	/*查询单词*/
	public void Listwords() throws SQLException
	{
		String cname,ename;
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		}
		catch(ClassNotFoundException e){}
		Connection Ex1Con = DriverManager.getConnection("jdbc:derby:MyData","","");
		Statement Ex1Stmt = Ex1Con.createStatement();
		boolean boo = false;
		if(label1.getText().equals("输入要查询的英语单词："))
		{
			ResultSet rs = Ex1Stmt.executeQuery("SELECT * FROM mytable");
			while(rs.next()){
				ename = rs.getString("word");
				cname = rs.getString("chinese");
				if(ename.equals(inputtext.getText())){
					txt.append(cname+'\n');
				}
			}
			Ex1Con.close();
			if(txt.getText().equals("")){
					JOptionPane.showMessageDialog(this,"查无此词！","警告",
				JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(label1.getText().equals("输入要查询的汉语词语：")){
		 	ResultSet rs1 = Ex1Stmt.executeQuery("SELECT * FROM mytable WHERE chinese LIKE '%"+inputtext.getText()+"%'");
				while(rs1.next()){
					ename = rs1.getString("word");
					cname = rs1.getString("chinese");
					txt.append(ename+'\n');	
					Ex1Con.close();
					if(txt.getText().equals("")){
						JOptionPane.showMessageDialog(this,"查无此单词！","警告",
						JOptionPane.WARNING_MESSAGE);
				}
			}		
		}	
	}	
}