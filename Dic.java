package dic;
import java.awt.*;
import java.net.*;
import java.sql.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.io.*;

public class Dic extends Frame implements ActionListener {
	
	MenuBar menubar=new MenuBar();//�˵�
	Menu fileMenu,editMenu;
	MenuItem fileenglish,filechinese,editAdd,editmod,editDel;
	TextField inputtext;//�����û��������ʾ�ɱ༭���ı����
	TextArea txt;
	Label label1,label2;
	Button btn1,exit;
	Panel p,p1,p2,p3;		// TODO Auto-generated method stub
	private class WindowCloser extends  WindowAdapter{
          public void windowClosing(WindowEvent e){
                System.exit(0);
             }
         }
	public Dic(){
			super("���Ӵʵ�");
			setBounds(200,300,350,400);		
			setMenuBar(menubar);			
			fileMenu=new Menu("�ʵ�����");
			editMenu=new Menu("�༭�ʵ�");	
			
			fileenglish=new MenuItem("Ӣ���ʵ�");
			filechinese=new MenuItem("��Ӣ�ʵ�");
			
			editAdd=new MenuItem("��Ӵʻ�");
			editmod=new MenuItem("�޸Ĵʻ�");
			editDel=new MenuItem("ɾ���ʻ�");
			
			menubar.add(fileMenu);
			menubar.add(editMenu);
			
			fileMenu.add(fileenglish);
			fileMenu.add(filechinese);
			fileMenu.addSeparator();
			
			editMenu.add(editAdd);
			editMenu.add(editmod);
			editMenu.add(editDel);
			
			inputtext=new TextField("",10);
			txt=new TextArea(20,20);
			txt.setBackground(Color.WHITE);
			label1=new Label("����Ҫ��ѯ��Ӣ�ﵥ�ʣ�");
			label1.setBackground(Color.GREEN);
			label2=new Label("��ѯ�����");
			label2.setBackground(Color.RED);
			btn1=new Button("��ѯ");
			exit=new Button("�˳�");
			
			btn1.setForeground(Color.GREEN);
			btn1.setBackground(Color.BLUE);
			exit.setForeground(Color.RED);
			exit.setBackground(Color.BLUE);
			
			p=new Panel(new BorderLayout());
			p2=new Panel(new FlowLayout(FlowLayout.LEFT,5,0));
			
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
			if(e.getSource()==exit)
				System.exit(0);
			else if(e.getSource()==btn1){
					txt.setText("");
				if(inputtext.getText().equals("")){
					JOptionPane.showMessageDialog(this,"��ѯ������Ϊ�գ�","����",JOptionPane.WARNING_MESSAGE);
				}
				else{
						try{
							Listwords();	
					}catch(SQLException ee){}
				}
			}
			else if(e.getSource()==fileenglish){
					label1.setText("����Ҫ��ѯ��Ӣ�ﵥ�ʣ�");
			}
			else if(e.getSource()==filechinese){
					label1.setText("����Ҫ��ѯ�ĺ�����");
			}
			else if( e.getSource()==editAdd){
				Addwin add = new Addwin();
			}
			else if(e.getSource()==editmod){
				Modwin mod = new Modwin();
			}
			else if(e.getSource()==editDel){
				Delwin del = new Delwin();
			}
		}

	
	public static void main(String[] args){
		Dic m = new Dic();
		String url ="jdbc:derby:MyData;create=true";
		File file = new File("D:\\Program Files\\eclipse-workspace\\dictionary\\MyData");
		Connection con = null;
		Statement sm = null;
		ResultSet rs = null;
		String Command = null;
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			System.out.println("����������װ�أ�");
			System.out.println("�����������ݿ⣡");
		}catch(Exception ex){
			System.out.println("Can not load Jdbc Driver"+ex.getMessage());
			return;
		}
		try{
			
			if (!file.isDirectory()) {
				con = DriverManager.getConnection(url);
				sm = con.createStatement();
				sm.execute("create table mytable (word varchar(20),chinese varchar(20))");
				DatabaseMetaData dmd = con.getMetaData();
				System.out.println("�����ӵ������ݿ⣺"+dmd.getURL());
				System.out.println("���õ����������ǣ�"+dmd.getDriverName());
			}	
		}catch(SQLException ex){
			System.out.println("Failed to Connect!");
			System.out.println(ex.getMessage());	
		}
		
	}
	class Modwin  extends Frame implements ActionListener{
		private TextField inputword= new TextField("",10);
		private TextArea  wordMeaning=new TextArea(20,20);
		private Label lab1=new Label("����Ҫ�޸ĵ�Ӣ�ﵥ�ʣ�");
		private Button mod= new Button("�޸�");
		private Label lab2=new Label("����Ҫ�޸ĵ��������壺");
		private class WindowCloser extends WindowAdapter{
		public void windowClosing(WindowEvent we){
			setVisible(false);
			}	
		}
		public Modwin(){
				super("�޸Ĵʻ�");
				setBounds(200,300,350,400);
				setup();
				mod.addActionListener(this);
				addWindowListener(new WindowCloser());
				pack();
			}
			
		public void actionPerformed(ActionEvent e){
				if(e.getSource()==mod)
						try{
								modwords();
						
				}catch(SQLException ee){}	
			}
			private void setup(){
			Panel t1,t2;
			t1=new Panel(new BorderLayout());
			t2=new Panel(new FlowLayout(FlowLayout.RIGHT,5,0));
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
			public void modwords() throws SQLException//�޸Ĵʿ��м�¼
	{
		String ename,cname;
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		}catch(ClassNotFoundException e){}
		Connection Ex1Con=DriverManager.getConnection("jdbc:derby:MyData","","");
		Statement Ex1Stmt=Ex1Con.createStatement();
		ResultSet rs=Ex1Stmt.executeQuery("SELECT * FROM mytable");
		boolean boo=false;
		while((boo=rs.next())==true){
			ename=rs.getString("word");
			cname=rs.getString("chinese");
			if(ename.equals(inputword.getText())){
				String s1="'"+inputword.getText().trim()+"'",s2="'"+wordMeaning.getText().trim()+"'";
				String temp="UPDATE mytable SET chinese="+s2+"WHERE word="+s1;
				Ex1Stmt.executeUpdate(temp);
				//Ex1Stmt.executeUpdate("UPDATE mytable SET chinese='"+txt.getText().trim()
				//+"' WHERE word='"+inputtext.getText().trim()+"'");
				JOptionPane.showMessageDialog(this,"��¼�޸ĳɹ���","��ϲ",
				JOptionPane.WARNING_MESSAGE);
				dispose();
				break;	
			}
		}
		Ex1Con.close();	
		if(boo==false){
			JOptionPane.showMessageDialog(this,"�����ڴ˵��ʣ�","����",JOptionPane.WARNING_MESSAGE);
		}
	}
		
	}
	class Delwin  extends Frame implements ActionListener{
		private TextField inputword= new TextField("",10);
	//	private TextArea  wordMeaning=new TextArea(20,20);
		private Label lab1=new Label("����Ҫɾ����Ӣ�ﵥ�ʣ�");
		private Button cancel= new Button("ɾ��");
		//private Label lab2=new Label("����Ҫɾ�����������壺");
		private class WindowCloser extends WindowAdapter{
		public void windowClosing(WindowEvent we){
			setVisible(false);
		}	
	}
			public Delwin(){
				super("ɾ���ʻ�");
				setBounds(200,300,350,400);
				setup();
				cancel.addActionListener(this);
				addWindowListener(new WindowCloser());
				pack();
			}
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==cancel)
						try{
								delwords();
						
				}catch(SQLException ee){}	
			}
			private void setup(){
			Panel t1,t2;
			t1=new Panel(new BorderLayout());
			t2=new Panel(new FlowLayout(FlowLayout.RIGHT,5,0));
			t2.add(lab1);
			t2.add(inputword);
			t2.add(cancel);
			add(t2,"North");
			//t1.add(lab2,"North");
		//	t1.add(wordMeaning,"Center");
			add(t1,"Center");
			setVisible(true);
			setResizable(false);
			validate();
		}
		
	public void delwords() throws SQLException//ɾ���ʿ��м�¼
	{
		@SuppressWarnings("unused")
		String cname,ename;
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		}catch(ClassNotFoundException e){}
		Connection Ex1Con=DriverManager.getConnection("jdbc:derby:MyData","","");
		Statement Ex1Stmt=Ex1Con.createStatement();
		ResultSet rs=Ex1Stmt.executeQuery("SELECT * FROM mytable");
		boolean boo=false;
		while((boo=rs.next())==true){
			ename=rs.getString("word");
			cname=rs.getString("chinese");
			if(ename.equals(inputword.getText())){
				String s1="'"+inputword.getText().trim()+"'";
				String temp="DELETE FROM mytable WHERE word="+s1;
				Ex1Stmt.executeUpdate(temp);
		
				JOptionPane.showMessageDialog(this,"�ɹ�ɾ����¼��","��ϲ",JOptionPane.WARNING_MESSAGE);
				dispose();
				break;
			}
		}
		Ex1Con.close();	
		if(boo==false){
				JOptionPane.showMessageDialog(this,"�����ڴ˵��ʣ�","����",
				JOptionPane.WARNING_MESSAGE);
			 }	
		}
	}
	class Addwin  extends Frame implements ActionListener{
	
		private TextField inputword= new TextField("",10);
		private TextArea  wordMeaning=new TextArea(20,20);
		private Label lab1=new Label("����Ҫ��ӵ�Ӣ�ﵥ�ʣ�");
		private Button ok= new Button("���");
		private Label lab2=new Label("����Ҫ��ӵ��������壺");
		private class WindowCloser extends WindowAdapter{
		public void windowClosing(WindowEvent we){
			setVisible(false);
		}	

	}
			public Addwin(){
				super("��Ӵʻ�");
				setBounds(200,300,350,400);
				setup();
				ok.addActionListener(this);
				addWindowListener(new WindowCloser());
				pack();
			}
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==ok)
						try{
						addwords();
						
				}catch(SQLException ee){}	
			}
		private void setup(){
			Panel t1,t2;
			t1=new Panel(new BorderLayout());
			t2=new Panel(new FlowLayout(FlowLayout.RIGHT,5,0));
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
		public void addwords() throws SQLException//�����ݿ�����´ʻ�
		{
			String cname,ename;
			
			try{
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			}catch(ClassNotFoundException e){}
			Connection Ex1Con=DriverManager.getConnection("jdbc:derby:MyData","","");
			Statement Ex1Stmt=Ex1Con.createStatement();
			ResultSet rs=Ex1Stmt.executeQuery("SELECT * FROM mytable");
			boolean boo=false;
			
			while(boo=rs.next()){
				ename=rs.getString("word");
				cname=rs.getString("chinese");
				System.out.println("ename" + ename);
				if(ename.equals(inputword.getText())){
					JOptionPane.showMessageDialog(this,"�˴ʻ��Ѵ��ڣ�","����",
					JOptionPane.WARNING_MESSAGE);
					break;
				}
			}
			//System.out.println("�ɹ���");
			if(boo==false){
				String s1="'"+inputword.getText().trim()+"'",s2="'"+wordMeaning.getText().trim()+"'";
				String temp="INSERT INTO mytable VALUES ("+s1+","+s2+")";
				Ex1Stmt.executeUpdate(temp);
				JOptionPane.showMessageDialog(this,"��ӳɹ���","��ϲ",JOptionPane.WARNING_MESSAGE);
				dispose();
			}
			Ex1Con.close();	
		}
	}
	public void Listwords() throws SQLException//��ѯʵ�ֹ���
	{
		String cname,ename;
		try
		{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		}
		catch(ClassNotFoundException e){}
		
		Connection Ex1Con=DriverManager.getConnection("jdbc:derby:MyData","","");
		Statement Ex1Stmt=Ex1Con.createStatement();
		
		
		boolean boo=false;
		if(label1.getText().equals("����Ҫ��ѯ��Ӣ�ﵥ�ʣ�"))
		{
			ResultSet rs=Ex1Stmt.executeQuery("SELECT * FROM mytable");
			while(rs.next()){
				ename=rs.getString("word");
				cname=rs.getString("chinese");
				if(ename.equals(inputtext.getText())){
					txt.append(cname+'\n');
				}
			}
			Ex1Con.close();
			if(txt.getText().equals("")){
					JOptionPane.showMessageDialog(this,"���޴˴ʣ�","����",
				JOptionPane.WARNING_MESSAGE);
			}
		}
		 else if(label1.getText().equals("����Ҫ��ѯ�ĺ�����")){
		 	ResultSet rs1=Ex1Stmt.executeQuery("SELECT * FROM mytable WHERE chinese LIKE '%"+inputtext.getText()+"%'");
				while(rs1.next()){
					ename = rs1.getString("word");
					cname = rs1.getString("chinese");
					txt.append(ename+'\n');	
					Ex1Con.close();
					if(txt.getText().equals("")){
						JOptionPane.showMessageDialog(this,"���޴˵��ʣ�","����",
						JOptionPane.WARNING_MESSAGE);
				}
			}		
		}	
	}	
}

