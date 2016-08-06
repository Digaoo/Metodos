import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
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
import java.lang.Double;

class Derivada extends JPanel {
  
  String[] itens = new String [3];
  JComboBox<String> jcb;
  JFrame jan;
  
  Derivada (JFrame j) {
    
    jan=j;
    itens[0]=new String("Achar Derivada Completa Em Um Ponto");
    itens[1]=new String("Achar Derivada Parcial Em Um Ponto");
    itens[2]=new String("Achar Raizes");
    jcb = new JComboBox <> (itens);
    setLayout (new BorderLayout(10,5));
	add(jcb,BorderLayout.NORTH);
	add(new Completa(jan),BorderLayout.SOUTH);
	  
  }
  
  class Completa extends JPanel {
	
	JFrame jan;
	Expressao exp = new Expressao();
	JPanel[] p = new JPanel[6];
	JPanel[] p2 = new JPanel[4];
	JPanel paux = new JPanel(new GridBagLayout());
	JPanel pentry = new JPanel(new BorderLayout(0,3));
	JPanel presul = new JPanel(new BorderLayout());
	GridBagConstraints grid = new GridBagConstraints();
	JTextArea func = new JTextArea();
	JTextArea ponto = new JTextArea();
	JTextArea erro = new JTextArea();
	JTextArea[] resul = new JTextArea[4];
	JButton b = new JButton("Calcular");
	StringBuilder sb = new StringBuilder();
	
	Completa (JFrame j) {
	  
	  int i;
	  
	  jan=j;
	  setLayout (new BorderLayout(0,5));
	  sb.appendCodePoint(0X03B5);
	  for(i=0;i<6;i++) p[i]= new JPanel(new BorderLayout());
	  p[3].setLayout(new BorderLayout(10,0));
	  b.setPreferredSize(new Dimension(95,17));
	  b.addActionListener(new ActionListener () {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		  
		  //Colocar janela de aviso
		  
		  if (func.getText().length()==0||ponto.getText().length()==0||erro.getText().length()==0) {
	        
	      }
	      
	      else {
			
			fazPainel();
	        add(presul,BorderLayout.SOUTH);
	        jan.pack();
			  
		  }
		  	
		}
	      
	  });
	  func.setColumns(27);
	  ponto.setColumns(7);
	  erro.setColumns(7);
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
		
	  return 1.2;
	  	
	}
	  
  }
  
}

class Metodos {
  
  public static void main(String[] args) {
    
    JTabbedPane tp = new JTabbedPane ();
    JFrame jan = new JFrame ("Métodos");
    ImageIcon icon = new ImageIcon("Derivada_v2.jpg");
    
    tp.addTab("D & R",icon,new Derivada(jan),"Deriva e Acha Raízes");
    jan.add(tp);
    jan.pack();
    jan.setVisible(true);
    
    
  } 
  
}

