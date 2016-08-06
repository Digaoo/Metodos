import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import java.awt.Container;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.JTabbedPane;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JScrollPane;
import java.util.Vector;
import java.math.BigDecimal;

class Derivada extends JPanel {
  
  String[] itens = new String [3];
  JComboBox<String> jcb;
  JPanel pmain = new JPanel(new CardLayout());
  JFrame jan;
  CardLayout cl;
  Completa comp;
  Parcial parc;
  Raiz ra;
  
  Derivada (JFrame j) {
    
    jan=j;
    comp = new Completa(jan);
    parc = new Parcial(jan);
    ra = new Raiz(jan);
    setLayout (new BorderLayout(10,5));
    itens[0]=new String("Achar Derivada Completa Em Um Ponto");
    itens[1]=new String("Achar Derivada Parcial Em Um Ponto");
    itens[2]=new String("Achar Raizes");
    jcb = new JComboBox <> (itens);
    jcb.addActionListener(new ActionListener () {
	 	
	  @Override
      public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String painel = (String)cb.getSelectedItem();
        pmain.removeAll();
        comp = new Completa(jan);
        parc = new Parcial(jan);
        ra = new Raiz(jan);
        pmain.add(comp,"Achar Derivada Completa Em Um Ponto");
	    pmain.add(parc,"Achar Derivada Parcial Em Um Ponto");
	    pmain.add(ra,"Achar Raizes");
        cl.show(pmain,painel);
        jan.pack();
      }
      
    });  
    cl = (CardLayout)(pmain.getLayout());
	add(jcb,BorderLayout.NORTH);
	pmain.add(comp,"Achar Derivada Completa Em Um Ponto");
	pmain.add(parc,"Achar Derivada Parcial Em Um Ponto");
	pmain.add(ra,"Achar Raizes");
	add(pmain,BorderLayout.SOUTH);
	  
  }
  
  class Completa extends JPanel {
	
	protected JFrame jan;
	protected Expressao exp = new Expressao();
	protected JPanel[] p = new JPanel[6];
	protected JPanel[] p2 = new JPanel[4];
	protected JPanel paux = new JPanel(new GridBagLayout());
	protected JPanel pentry = new JPanel(new BorderLayout(0,3));
	protected JPanel presul = new JPanel(new BorderLayout());
	protected GridBagConstraints grid = new GridBagConstraints();
	protected JTextArea func = new JTextArea();
	protected JTextArea ponto = new JTextArea();
	protected JSpinner erro = new JSpinner(new SpinnerNumberModel (0.01,0.001,0.1,0.001));
	protected JTextArea[] resul = new JTextArea[4];
	protected JButton b = new JButton("Calcular");
	protected StringBuilder sb = new StringBuilder();
	// h = 1000*erro
	
	Completa (JFrame j) {
	  
	  int i;
	  
	  jan=j;
	  setLayout (new BorderLayout(0,5));
	  sb.appendCodePoint(0X03B5);
	  for(i=0;i<6;i++) p[i]= new JPanel(new BorderLayout());
	  p[3].setLayout(new BorderLayout(10,0));
	  b.setPreferredSize(new Dimension(120,17));
	  b.addActionListener(new ActionListener () {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		  
		  //Colocar janela de aviso
		  
		  if (func.getText().length()==0||ponto.getText().length()==0) {
	        
	      }
	      
	      else {
			
			fazPainel();
	        add(presul,BorderLayout.SOUTH);
	        jan.pack();
			  
		  }
		  	
		}
	      
	  });
	  func.setColumns(40);
	  ponto.setColumns(9);
	  ((DefaultEditor) erro.getEditor()).getTextField().setEditable(false);
	  p[3].setLayout(new BorderLayout(35,0));
	  p[0].add(new JLabel ("f(x) ="),BorderLayout.WEST);
	  p[0].add(func,BorderLayout.EAST);
	  p[1].add(new JLabel ("x ="),BorderLayout.WEST);
	  p[1].add(ponto,BorderLayout.EAST);
	  p[2].add(new JLabel (sb.charAt(0)+" ="),BorderLayout.WEST);
	  p[2].add(erro,BorderLayout.EAST);
	  p[3].add(p[1],BorderLayout.WEST);
	  p[3].add(p[2],BorderLayout.EAST);
	  p[4].add(p[3],BorderLayout.WEST);
	  p[4].add(b,BorderLayout.EAST);
	  pentry.add(p[0],BorderLayout.NORTH);
	  pentry.add(p[4],BorderLayout.SOUTH);
	  add(pentry,BorderLayout.NORTH);
	  	
	}
	
	Completa () { }
	
	public void fazPainel () {
		
	  int i;
	  
	  for(i=0;i<4;i++) resul[i]= new JTextArea();
	  for(i=0;i<4;i++) resul[i].setColumns(13);
	  for(i=0;i<4;i++) resul[i].setEditable(false);
	  for(i=0;i<4;i++) p2[i]= new JPanel(new BorderLayout());
	  grid.gridx=0;
	  grid.gridy=0;
	  grid.insets=new Insets(2,2,2,2);
	  p2[0].add(new JLabel ("f'   "),BorderLayout.WEST);
	  p2[0].add(resul[0],BorderLayout.EAST);
	  paux.add(p2[0],grid);
	  grid.gridx++;
	  p2[1].add(new JLabel ("f''  "),BorderLayout.WEST);
	  p2[1].add(resul[1],BorderLayout.EAST);
	  paux.add(p2[1],grid);
	  grid.gridy++;
	  grid.gridx=0;
	  p2[2].add(new JLabel ("f''' "),BorderLayout.WEST);
	  p2[2].add(resul[2],BorderLayout.EAST);
	  paux.add(p2[2],grid);
	  grid.gridx++;
	  p2[3].add(new JLabel ("f''''"),BorderLayout.WEST);
	  p2[3].add(resul[3],BorderLayout.EAST);
	  paux.add(p2[3],grid);
	  
	  presul.add(paux,BorderLayout.WEST);
	  	
	}
	
	public double funcao(double x,String funcao) {
	  
	  double num = 0;
	  
	  try{
		  
		num = exp.valor(funcao);
		
	  }catch(Exception e){}
	    
		return num;
		
    }
	
	public double calculaDerivada (String f,double x,double erro,double h) {
		
	  return 1.23;
	  	
	}
	  
  }
  
  class Parcial extends Completa { 
	  
	private JSpinner nx = new JSpinner(new SpinnerNumberModel (2,2,10,1));
	private JSpinner[] der = new JSpinner[4];
	private JPanel aux = new JPanel(new BorderLayout());
	private JPanel[] p3;
	private JPanel[] p4 = new JPanel [10];
	private JPanel[] p5 = new JPanel [7];
	private JPanel pextra = new JPanel(new BorderLayout());
	private JTextArea[] jta;
	private StringBuilder str = new StringBuilder ("f"); 
	private int[] aux2 = new int[4];
	
	Parcial (JFrame j) {
	  
	  super(j);
	  sb.appendCodePoint(0X2080);
	  sb.appendCodePoint(0X2081);
	  sb.appendCodePoint(0X2082);
	  sb.appendCodePoint(0X2083);
	  sb.appendCodePoint(0X2084);
	  sb.appendCodePoint(0X2085);
	  sb.appendCodePoint(0X2086);
	  sb.appendCodePoint(0X2087);
	  sb.appendCodePoint(0X2088);
	  sb.appendCodePoint(0X2089);
	  sb.appendCodePoint(0X2070);
	  ((BorderLayout)p[3].getLayout()).setHgap(45);
	  p[1].removeAll();
	  p[1].add(new JLabel ("n"+sb.charAt(11)+" x ="),BorderLayout.WEST);
	  p[1].add(nx,BorderLayout.EAST);
	  ((DefaultEditor) nx.getEditor()).getTextField().setEditable(false);
	  b.setText("Ok");
	  b.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed (ActionEvent e) {
		  
		  fazPainel2();
		  fazPainel3();
		  add(aux,BorderLayout.SOUTH);
		  aux.removeAll();
		  aux.add(p3[0],BorderLayout.NORTH);
		  jan.pack();
		  	
		}
	     
	  });
	  	
	}
	
	void fazPainel2() {
	  	
	  int i;
	  int a = Integer.parseInt(((DefaultEditor) nx.getEditor()).getTextField().getText());
	  b = new JButton("Ok");
	  p3 = new JPanel[a+1];
	  jta = new JTextArea[a];
	  for (i=0;i<a+1;i++) p3[i]=new JPanel(new BorderLayout());
	  for (i=0;i<a;i++) jta[i]=new JTextArea();
	  for (i=0;i<a;i++) jta[i].setColumns(3);
	  for (i=1;i<a+1;i++) {
		  
		p3[i].add(new JLabel ("x"+sb.charAt(i)+" ="),BorderLayout.WEST);
	    p3[i].add(jta[i-1],BorderLayout.EAST);  
		  
	  }
	  b.addActionListener( new ActionListener () {
		
		@Override
		public void actionPerformed (ActionEvent e) {
		  
		  fazPainel3();
		  pextra.add(p4[0],BorderLayout.NORTH);
		  aux.add(pextra,BorderLayout.SOUTH);
		  jan.pack();
		  	
		}
		  
	  });
	  if (a<=5)b.setPreferredSize(new Dimension(60*a,17));
	  else b.setPreferredSize(new Dimension(300,17));
	  p3[0].setLayout(new GridBagLayout());
	  p3[0].removeAll();
	  grid.gridx=0;
	  grid.gridy=0;
	  grid.insets=new Insets(4,2,4,2);
	  for (i=1;i<a+1;i++) {
		
		p3[0].add(p3[i],grid);
		grid.gridx++;
		
		if (i%5==0) { grid.gridy++; grid.gridx=0; }  
		  
	  }
	  grid.gridx=0;
	  grid.gridy++;
	  grid.gridwidth = a;
	  p3[0].add(b,grid);
	  grid.gridwidth = 1;
	  repaint();
	  
	}
	
	void fazPainel3 () {
	  
	  int i;
	  
	  for (i=0;i<10;i++) p4[i]=new JPanel (new BorderLayout());
	  
	  for (i=0;i<4;i++) { der[i]=new JSpinner(new SpinnerNumberModel (0,0,9,1)); }
	  
	  b = new JButton("Calcular");
	  b.addActionListener( new ActionListener () {
		
		@Override
		public void actionPerformed (ActionEvent e) {
		  
		  aux2[0]=Integer.parseInt(((DefaultEditor) der[0].getEditor()).getTextField().getText());
		  aux2[1]=Integer.parseInt(((DefaultEditor) der[1].getEditor()).getTextField().getText());
		  aux2[2]=Integer.parseInt(((DefaultEditor) der[2].getEditor()).getTextField().getText());
		  aux2[3]=Integer.parseInt(((DefaultEditor) der[3].getEditor()).getTextField().getText());
		  fazPainel4();
		  	
		}
		  
	  });
	  b.setPreferredSize(new Dimension(100,17));
	  p4[6].add(new JLabel ("f'"),BorderLayout.WEST);
	  p4[6].add(der[0],BorderLayout.EAST);
	  p4[7].add(new JLabel ("f''"),BorderLayout.WEST);
	  p4[7].add(der[1],BorderLayout.EAST);
	  p4[8].add(new JLabel ("f'''"),BorderLayout.WEST);
	  p4[8].add(der[2],BorderLayout.EAST);
	  p4[9].add(new JLabel ("f''''"),BorderLayout.WEST);
	  p4[9].add(der[3],BorderLayout.EAST);
	  p4[5].add(p4[6],BorderLayout.EAST);
	  p4[4].add(p4[7],BorderLayout.WEST);
	  p4[4].add(p4[8],BorderLayout.EAST);
	  p4[3].add(p4[9],BorderLayout.WEST);
	  p4[2].add(p4[5],BorderLayout.WEST);
	  p4[2].add(p4[4],BorderLayout.CENTER);
	  p4[2].add(p4[3],BorderLayout.EAST);
	  p4[1].add(p4[2],BorderLayout.NORTH);
	  p4[1].add(b,BorderLayout.CENTER);
	  p4[0].add(new JLabel("Escolha os Elementos a Partir dos Quais f Sera Derivada:"),BorderLayout.NORTH);
	  p4[0].add(p4[1],BorderLayout.SOUTH);
	  ((BorderLayout)p4[5].getLayout()).setHgap(130);
	  ((BorderLayout)p4[3].getLayout()).setHgap(130);
	  ((BorderLayout)p4[2].getLayout()).setHgap(10);
	  ((BorderLayout)p4[1].getLayout()).setVgap(6);
	  ((BorderLayout)p4[1].getLayout()).setHgap(60);
	  ((BorderLayout)p4[0].getLayout()).setVgap(8);
	  	
	}
	
	void fazPainel4 () {
	  
	  int i;
	  for(i=0;i<7;i++) p5[i]=new JPanel(new BorderLayout());
	  
	  //p5[3].add();
	  	
	}
	  
  }
  
  class Raiz extends Completa {
	  
	JTextArea pa = new JTextArea(1,5);
	JTextArea pb = new JTextArea(1,5);
	private JTable table;
    private String[] colunas = {"x 1","x 2"};
    private JScrollPane jsp;
    private Vector<BigDecimal> x1 = new Vector<>(10,2);
    private Vector<BigDecimal> x2 = new Vector<>(10,2);
    private BigDecimal[][] data;
    private Integer datum [][]={{1,2},{3,4},{5,6},{7,8},{9,10}};
	
	Raiz (JFrame j) {
		
	  super(j);
	  int i;
	  ((BorderLayout)p[3].getLayout()).setHgap(20);
	  p[1].removeAll();
	  for (i=0;i<4;i++) p2[i]=new JPanel(new BorderLayout());
	  p[1].setLayout(new BorderLayout(3,3));
	  p[1].add(p2[0],BorderLayout.WEST);
	  p[1].add(p2[1],BorderLayout.EAST);
	  p2[0].add(new JLabel ("a ="),BorderLayout.WEST);
	  p2[0].add(pa,BorderLayout.EAST);
	  p2[1].add(new JLabel ("b ="),BorderLayout.WEST);
	  p2[1].add(pb,BorderLayout.EAST);
	  b.addActionListener (new ActionListener () {
		
		@Override
		public void actionPerformed (ActionEvent e) {
		  
		  done();
		  jan.pack();
		  	
		}
	     
	  });
	  
	}
	
	public void novoDado(BigDecimal a,BigDecimal b) {
     
      x1.add(a);
      x2.add(b);
       
    }
   
    public void done() {
     
      int i;
      /*
      data = new BigDecimal[x1.size()][2];
     
      for (i=0;i<x2.size();i++) {
       
        data[i][0]=x1.get(x1.size()-i-1);
        data[i][1]=x2.get(x2.size()-i-1);
       
      }
      */
      table = new JTable (datum,colunas);
      //http://stackoverflow.com/questions/15300997/getting-component-from-a-specific-coordinate-in-gridbaglayout
      jsp = new JScrollPane (table);
      add(jsp,BorderLayout.SOUTH);
      table.setEnabled(false);
      jsp.setPreferredSize(new Dimension (300,80));
     
    }
    
  }
  
}

class Metodos {
  
  public static void main(String[] args) {
	  
    JTabbedPane tp = new JTabbedPane ();
    JFrame jan = new JFrame ("Metodos");
    ImageIcon icon = new ImageIcon("Derivada_v2.jpg");
    
    tp.addTab("D & R",icon,new Derivada(jan),"Deriva e Acha Raízes");
    jan.add(tp);
    jan.pack();
    jan.setResizable(false);
    jan.setVisible(true);
    
    
  } 
  
}
