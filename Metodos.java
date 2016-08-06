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
import javax.swing.BorderFactory;
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
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

//Consertar bug da janela g&h de resetar o botão e continuar adicionando elementos

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
	protected boolean boo=false;
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
		  
		  if (func.getText().length()==0||ponto.getText().length()==0) {}
	      
	      else {
			
			fazPainel();
	        add(presul,BorderLayout.SOUTH);
	        jan.pack();
			  
		  }
		  	
		}
	      
	  });
	  func.setColumns(40);
	  func.addCaretListener(new CaretListener() {
	    
	    @Override
	    public void caretUpdate (CaretEvent e) {
		  
		  boo=true;
		  	
		}
	    
	  });
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
	private JPanel aux = new JPanel(new BorderLayout(0,5));
	private JPanel[] p3;
	private JPanel[] p4 = new JPanel [10];
	private JPanel[] p5 = new JPanel [7];
	private JPanel what = new JPanel ();
	private JPanel pextra = new JPanel(new BorderLayout());
	private JTextArea[] jta;
	private JTextArea[] jta2 = new JTextArea[4];
	private StringBuilder str; 
	private int[] aux2 = new int[4];
	private int a,i;
	private JFrame janaux = new JFrame ("G & H");
	private JPanel janp = new JPanel (new GridLayout(1,0));
	private JPanel janp2 = new JPanel ();
	private JTextArea[] grad;
	private JTextArea[][] hess;
	private boolean[] boo2 = new boolean[10];
	
	Parcial (JFrame j) {
	  
	  super(j);
	  janaux.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	  janaux.setLayout(new BorderLayout(6,6));
	  janaux.add(janp,BorderLayout.NORTH);
	  janaux.add(janp2,BorderLayout.SOUTH);
	  janaux.setLocationRelativeTo(null);
	  janaux.setResizable(false);
	  janaux.setVisible(false);
	  for (i=0;i<10;i++) boo2[i]=false;
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
	  nx.addChangeListener( new ChangeListener () {
		
		@Override
		public void stateChanged (ChangeEvent e) {
		  
		  janaux.setVisible(false);
		  janp.removeAll();
		  janp2.removeAll();
		  boo=true;
		  	
		}
		
	  });
	  b.removeActionListener(b.getAction());
	  b.setText("Ok");
	  b.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed (ActionEvent e) {
			
		  if (boo) {
		  
		    fazPainel2();
		    fazPainel3();
		    add(aux,BorderLayout.SOUTH);
		    aux.removeAll();
		    aux.add(p3[0],BorderLayout.NORTH);
		    aux.add(p4[0],BorderLayout.CENTER);
		    jan.pack();
		    boo=false;
		  
	      }
		  	
		}
	     
	  });
	  	
	}
	
	void fazPainel2() {
	  	
	  a = Integer.parseInt(((DefaultEditor) nx.getEditor()).getTextField().getText());
	  p3 = new JPanel[a+1];
	  jta = new JTextArea[a];
	  for (i=0;i<a+1;i++) p3[i]=new JPanel(new BorderLayout());
	  for (i=0;i<a;i++) jta[i]=new JTextArea(1,3);
	  for (i=0;i<a;i++) jta[i].addCaretListener(new CaretListener () {
		  
		@Override
		public void caretUpdate (CaretEvent e) {
	  
	      JTextArea change = (JTextArea)e.getSource();
	  
	      for (i=0;i<a;i++)
	      if (change==jta[i]) {
		  
		    boo2[i]=true;
		  	
		  }
	  	
	    }
	      
	  });
	  for (i=1;i<a+1;i++) {
		  
		p3[i].add(new JLabel ("x"+sb.charAt(i)+" ="),BorderLayout.WEST);
	    p3[i].add(jta[i-1],BorderLayout.EAST);  
		  
	  }
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
	  repaint();
	  
	}
	
	void fazPainel3 () {
	  
	  for (i=0;i<10;i++) p4[i]=new JPanel (new BorderLayout());
	  
	  for (i=0;i<4;i++) { der[i]=new JSpinner(new SpinnerNumberModel (0,0,a-1,1)); }
	  
	  for (i=0;i<4;i++) { ((DefaultEditor) der[i].getEditor()).getTextField().setEditable(false); }
	  
	  b = new JButton("Calcular");
	  b.addActionListener( new ActionListener () {
		
		@Override
		public void actionPerformed (ActionEvent e) {
		  
		  for (i=0;i<a;i++) if(boo2[i]==false) return;
		  
		  boo2[a-1]=false;
		  aux2[0]=Integer.parseInt(((DefaultEditor) der[0].getEditor()).getTextField().getText());
		  aux2[1]=Integer.parseInt(((DefaultEditor) der[1].getEditor()).getTextField().getText());
		  aux2[2]=Integer.parseInt(((DefaultEditor) der[2].getEditor()).getTextField().getText());
		  aux2[3]=Integer.parseInt(((DefaultEditor) der[3].getEditor()).getTextField().getText());
		  der[0].setEnabled(false);
		  der[1].setEnabled(false);
		  der[2].setEnabled(false);
		  der[3].setEnabled(false);
		  str= new StringBuilder("f");
		  what.removeAll();
		  fazPainel4();
		  aux.add(p5[0],BorderLayout.SOUTH);
		  jan.pack();
		  	
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
	  p4[1].add(new JLabel(),BorderLayout.EAST);
	  p4[1].add(new JLabel(),BorderLayout.WEST);
	  p4[0].add(new JLabel("Escolha os Elementos a Partir de Quais f Sera Derivada:"),BorderLayout.NORTH);
	  p4[0].add(p4[1],BorderLayout.SOUTH);
	  ((BorderLayout)p4[5].getLayout()).setHgap(130);
	  ((BorderLayout)p4[3].getLayout()).setHgap(130);
	  ((BorderLayout)p4[2].getLayout()).setHgap(10);
	  ((BorderLayout)p4[1].getLayout()).setVgap(6);
	  ((BorderLayout)p4[1].getLayout()).setHgap(128);
	  ((BorderLayout)p4[0].getLayout()).setVgap(8);
	  	
	}
	
	void fazPainel4 () {
	  
	  int i;
	  for(i=0;i<7;i++) p5[i]=new JPanel(new BorderLayout(0,3));
	  for (i=0;i<4;i++) jta2[i]=new JTextArea(1,20);
	  for (i=0;i<4;i++) jta2[i].setEditable(false);
	  
	  b=new JButton("Gradiente e Hessiana");
	  b.addActionListener(new ActionListener () {
	    
	    @Override
	    public void actionPerformed (ActionEvent e) {
		  
		  janaux.setLocationRelativeTo(null);
		  janaux.pack();
		  janaux.setVisible(true);
		  
		}
	    
	  });
	  what.add(b);
	  b.setPreferredSize(new Dimension(200,17));
	  str.append('x');
	  str.append(sb.charAt(aux2[0]+1));
	  p5[6].add(new JLabel(str.toString()+" ="),BorderLayout.WEST);
	  p5[6].add(jta2[0],BorderLayout.CENTER);
	  str.append('x');
	  str.append(sb.charAt(aux2[1]+1));
	  p5[5].add(new JLabel(str.toString()+" ="),BorderLayout.WEST);
	  p5[5].add(jta2[1],BorderLayout.CENTER);
	  str.append('x');
	  str.append(sb.charAt(aux2[2]+1));
	  p5[4].add(new JLabel(str.toString()+" ="),BorderLayout.WEST);
	  p5[4].add(jta2[2],BorderLayout.CENTER);
	  str.append('x');
	  str.append(sb.charAt(aux2[3]+1));
	  p5[3].add(new JLabel(str.toString()+" ="),BorderLayout.WEST);
	  p5[3].add(jta2[3],BorderLayout.CENTER);
	  p5[2].add(p5[6],BorderLayout.NORTH);
	  p5[2].add(p5[5],BorderLayout.SOUTH);
	  p5[1].add(p5[4],BorderLayout.NORTH);
	  p5[1].add(p5[3],BorderLayout.SOUTH);
	  p5[0].add(p5[2],BorderLayout.NORTH);
	  p5[0].add(p5[1],BorderLayout.CENTER);
	  p5[0].add(what,BorderLayout.SOUTH);
	  ((BorderLayout)p5[0].getLayout()).setVgap(6);
	  ((BorderLayout)p5[6].getLayout()).setHgap(38);
	  ((BorderLayout)p5[5].getLayout()).setHgap(26);
	  ((BorderLayout)p5[4].getLayout()).setHgap(14);
	  ((BorderLayout)p5[3].getLayout()).setHgap(2);
	  fazJanela();
	  	
	}
	
	public void fazJanela() {
		
	  int i,j;
	  
	  grad = new JTextArea[a];
	  hess = new JTextArea[a][a];
	  
	  janp2.setLayout(new GridLayout(a,a));
	  
	  ((GridLayout)janp.getLayout()).setHgap(5);
	  ((GridLayout)janp2.getLayout()).setHgap(5);
	  ((GridLayout)janp2.getLayout()).setVgap(5);
	  
	  janp.setBorder(BorderFactory.createTitledBorder("Gradiente"));
	  janp2.setBorder(BorderFactory.createTitledBorder("Hessiana"));
	  
	  for (i=0;i<a;i++) { grad[i] = new JTextArea(1,5); grad[i].setEditable(false); }
	  
	  for (i=0;i<a;i++) 
	    for (j=0;j<a;j++) {
			
	      hess[i][j]= new JTextArea(1,5);
	      hess[i][j].setEditable(false);
	      
	    }
	      
	  //chama para preencher os campos
	  
	  for (i=0;i<a;i++) janp.add(grad[i]);
	  
	  for (i=0;i<a;i++)
	    for (j=0;j<a;j++)
	      janp2.add(hess[i][j]);
	  	
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
