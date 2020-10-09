import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
/*
 * @Author 
 */

/*LineCoding deals with the creation of the window, encoding part and the decoding
 * Graph is ploted by another class called Main.
 * The signal list is passed to the constructor and the graph of the encoded signal is ploted by Main
 * Problems faced:--
 * 1>On closing the graph app the initial window is also getting closed.
 * 2>The initial window is behaving abnormally when graph is implemented on it.
 * Fixes:---
 * Created by soum_aspi...
 */

 class Main extends JFrame
{
	static ArrayList<Integer> signal;
	static int counter;
	public Main(ArrayList<Integer> sig)
	{
		signal=sig;
		counter=sig.size();
		setTitle("Graph App");
		setSize(1000,1000);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void paint(Graphics g)
	{
		
		Graphics2D g2d = (Graphics2D) g;
		
		//Origin of the graph is (300,300)
		
		drawGrid(g2d);
		g2d.setStroke(new BasicStroke(3));
		g.drawLine(0, 400, 1000,400);
		g.drawLine(200,0,200,1000);
		g.setColor(Color.blue);
		int prev=signal.get(0);
		int unit=8;
		int y=400-(unit*(prev));
		int x=200;
		int spac=40;
		g2d.setStroke(new BasicStroke(5));
		for(int i=0;i<counter;i++)
		{
			//Thread.sleep(10);
			int ele=signal.get(i);
			if(ele==prev)
			{
				g.drawLine(x, y, x+spac,y);
				x+=spac;
			}
			else if(ele!=prev)
			{
				if(ele-prev>0)
				{
				g.drawLine(x,y,x,y-(ele-prev)*unit);
				y=y-(ele-prev)*unit;
				}
				else
				{
					g.drawLine(x, y, x, y-(ele-prev)*unit);
					y-=(ele-prev)*unit;
				}
				g.drawLine(x, y, x+spac, y);
				x=x+spac;
			}
			prev=ele;
		}
		
	}
	public void drawGrid(Graphics2D g)
	{
		g.setStroke(new BasicStroke(1));
		int width = 1000;
	    int height = 1000;
       int rows=25;
       int  cols=25;
	    // draw the rows
	    int rowHt = height / (rows);
	    for (int i = 0; i < rows; i++)
	      g.drawLine(0, i * rowHt, width, i * rowHt);

	    // draw the columns
	    int rowWid = width / (cols);
	    for (int i = 0; i < cols; i++)
	      g.drawLine(i * rowWid, 0, i * rowWid, height);
	}
}
public class LineCoding extends JFrame implements ActionListener {
	JTextField inpData = new JTextField(12);
	JTextArea rcvData = new JTextArea(1,12);
	JComboBox<String> techniques = null;
	JPanel drawPanel = new JPanel();
	ArrayList<Integer> signal;
	Main mp;
	public LineCoding() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000,400);
		setTitle("Line Coding Techniques....");
		initComponents();
	}

	private void initComponents() {
		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("Input Data:"));
		topPanel.add(inpData);
		String list[] = {"NRZ-I","NRZ-L","RZ","Manchester","Differential Manchester","AMI","Pseudoternary"};
		techniques = new JComboBox<String>(list);
		topPanel.add(new JLabel("Technique:"));
		topPanel.add(techniques);
		topPanel.add(new JLabel("Received Data:"));
		topPanel.add(rcvData);
		JButton encodeBtn = new JButton("Encode");
		JButton decodeBtn = new JButton("Decode");
		JButton clrBtn = new JButton("Clear");
		topPanel.add(encodeBtn);
		encodeBtn.addActionListener(this);
		topPanel.add(decodeBtn);
		decodeBtn.addActionListener(this);
		topPanel.add(clrBtn);
		clrBtn.addActionListener(this);
		drawPanel.setBackground(Color.WHITE);
		add(drawPanel);
		add(topPanel,BorderLayout.NORTH);
		
	}

	public static void main(String[] args) {
		LineCoding f = new LineCoding();
		f.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Encode")){
			encode();
		}else if(e.getActionCommand().equals("Decode")){
			decode();
		}else if(e.getActionCommand().equals("Clear")){
			repaint();
		}
		
	}


	private void encode() {		
		String p=(String) techniques.getSelectedItem();
		String bin_data=inpData.getText();
		int volt=5;
	    signal=new ArrayList<Integer>();
		System.out.print(p);
		int len=bin_data.length();
		if(p.equals("NRZ-L"))
		{
			
			for(int i=0;i<len;i++)
			{
				if(bin_data.charAt(i)=='1')
				{
					signal.add(volt);
				    signal.add(volt);
				}
				else
				{
					signal.add(-volt);
					signal.add(-volt);
					
			}
		}
		}
		
		//////
		//////
		
		else if(p.equals("NRZ-I")) {
			if(bin_data.charAt(0)=='1')
			{
				signal.add(volt);
				signal.add(volt);
			}
			else
			{
				signal.add(-volt);
				signal.add(-volt);
			}
			for(int i=1;i<len;i++)
			{
			int pop=signal.get(signal.size()-1);
			if(bin_data.charAt(i)=='0')
			{
			 signal.add(pop);
			 signal.add(pop);
			}
			else
			{
				
				signal.add(-1 *pop);
				signal.add(-1* pop);
			}
			
		}
		}
		else if(p.equals("RZ"))
		{
			for(int i=0;i<len;i++)
			{
			//int pop=signal.get(signal.size()-1);
			if(bin_data.charAt(i)=='0')
			{
			 signal.add(-volt);
			 signal.add(0);
			}
			else
			{
				
				signal.add(volt);
				signal.add(0);
			}
			
		}
			
		}
		else if(p.equals("Manchester"))
		{
			for(int i=0;i<len;i++)
			{
				if(bin_data.charAt(i)=='0')
				{
					signal.add(-volt);
					signal.add(volt);
				}
				else if(bin_data.charAt(i)=='1')
				{
					signal.add(volt);
					signal.add(-volt);
				}
			}
		}
		else if(p.equals("Differential Manchester"))
			{
			int pop;
			if(bin_data.charAt(0)=='1')
			{
				signal.add(volt);
				signal.add(-volt);
				pop=volt;
			}
			else
			{
				signal.add(-volt);
				signal.add(volt);
				pop=-volt;
			}
			for(int i=1;i<len;i++)
			{
				if(bin_data.charAt(i)=='0')
				{
					if(pop==volt)
					{
					signal.add(pop);
					signal.add(-pop);
					
					}
					else
					{
						signal.add(pop);
						signal.add(-pop);
						
					}
				}
				else if(bin_data.charAt(i)=='1')
				{
					if(pop==volt)
					{
					signal.add(-pop);
					signal.add(pop);
					}
					else
					{
						signal.add(-pop);
						signal.add(pop);
					}
					pop=-pop;
				}
			}
			}
		else if(p.equals("AMI"))
		{
			int pop=-volt;
			for(int i=0;i<len;i++)
			{
				int cha=bin_data.charAt(i);
				
				if(cha=='1')
				{
					pop=-1*pop;
					signal.add(pop);
					signal.add(pop);
				}
				if(cha=='0')
				{
					signal.add(0);
					signal.add(0);
				}
			}
			
			
		}
		else if(p.equals("Pseudoternary"))
			{
			int pop=-volt;
			for(int i=0;i<len;i++)
			{
				int cha=bin_data.charAt(i);
				
				if(cha=='0')
				{
					pop=-1*pop;
					signal.add(pop);
					signal.add(pop);
				}
				if(cha=='1')
				{
					signal.add(0);
					signal.add(0);
				}
			}
			
			}
		System.out.println(signal);		
		mp=new Main(signal);
		mp.setVisible(true);
	}
	
	private void decode() {
		rcvData.selectAll();
		rcvData.replaceSelection("");
		String k=(String) techniques.getSelectedItem();
		String m[]={"NRZ-I","NRZ-L","RZ","Manchester","Differential Manchester","AMI","Pseudoternary"};
		String d="";
		if(k.equals(m[1]))
			d=NRZ_L_D(signal);
		else if(k.equals(m[0]))
			d=NRZ_I_D(signal);
		else if(k.equals(m[2]))
			d=RZ_D(signal);
		else if(k.equals(m[4]))
			d=diff_Manchester_D(signal);
		else if(k.equals(m[3]))
			d=Manchester_D(signal);
		else if(k.equals(m[5]))
			d=AMI_D(signal);
		else if(k.equals(m[6]))
			d=PSEUDOTERNARY_D(signal);
		System.out.println(d);
		rcvData.append(d);
		
	}
		private String NRZ_L_D(ArrayList<Integer> x){
	       String d = new String();
	        for(int i=0;i<x.size();i+=2){
	            if(x.get(i) == 5){
	                d+='1';
	            }else{
	                d+='0';
	            }
	        }
	        return d;
	    }

	    private String NRZ_I_D(ArrayList<Integer> x){
	       String d="";
	        if(x.get(0)==-5){
	            d+='0';
	        }else{
	            d+='1';
	        }
	        for(int i=2;i<x.size();i+=2){
	            if(x.get(i).equals(x.get(i - 1))){
	                d+='0';
	            }else{
	                d+='1';
	            }
	        }
	        return d;
	    }
	    private String RZ_D(ArrayList<Integer> x){
	        String d="" ;
	        for (int i=0;i<x.size();i+=2){
	            if(x.get(i) == 5){
	                d+='1';
	            }else{
	                d+='0';
	            }
	        }
	        return d;
	    }
	    private String AMI_D(ArrayList<Integer> x){
	       String d="";
	        for(int i=0;i<x.size();i+=2){
	            if(x.get(i) == 0){
	            	d+='0';
	            }else{
	            	 d+='1';
	            }
	        }
	        return d;
	    }
	    private String PSEUDOTERNARY_D(ArrayList<Integer> x){
	    	String d="";
	        for(int i=0;i<x.size();i+=2){
	            if(x.get(i)==0){
	            	 d+='1';
	            }else{
	            	d+='0';
	            }
	        }
	        return d;
	    }

	    private String Manchester_D(ArrayList<Integer> x){
	    	String d="";
	        for(int i=0;i<x.size();i+=2){
	            if(x.get(i) == -5 && x.get(i+1) == 5){
	            	d+='0';
	            }else if(x.get(i) == 5 && x.get(i+1) == -5){
	            	 d+='1';
	            }
	        }
	        return d;
	    }

	    private String diff_Manchester_D(ArrayList<Integer> x){
	    	String d="";
	        if(x.get(0)==-5 && x.get(1) == 5){
	        	d+='0';
	        }else{
	        	d+='1';
	        }
	        for (int i = 2;i<x.size();i+=2) {
	            if(x.get(i).equals(x.get(i-1))){
	            	d+='1';
	            }else{
	            	d+='0';
	            }
	        }
	        return d;
	    }
	   
	    
	}
	

