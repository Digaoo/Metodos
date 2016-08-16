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
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import java.awt.Component;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.table.TableColumnModel;
import javax.swing.UIManager;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

//Adicionar item jacobiano
//Possibilitar guardar dados em arquivos
//Aviso se máximo de iterações ?
//Tentar usar BigDecimal

//Classe base dos itens da aba derivada
class Derivada extends JPanel {
  
  String[] itens = new String [3]; //Vetor que guarda os itens do combo box
  JComboBox<String> jcb; //Combo box
  JPanel pmain = new JPanel(new CardLayout()); //Painel que cuida dos itens do combo box
  JFrame jan; //Janela principal
  CardLayout cl; // Instância do layout que cuida dos itens do combo box
  Completa comp; //Instancia do painel de derivada completa
  Parcial parc; //Instancia do painel de derivada parcial
  Raiz ra; //Instancia do painel de raiz de função
  
  //Cria a estrutura para acessar os itens da aba
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
        parc.janaux.setVisible(false);
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
  
}

//Classe do item derivada completa
class Completa extends JPanel {
	
  JFrame jan; // Instancia da janela principal
  Metodos mtd = new Metodos(); // Instancia do interpretador de funções
  StringBuilder sb = new StringBuilder(); // Guarda caracteres especias a serem utilizados ao longo do programa
  JFrame warning; // Janela de aviso
  GridBagConstraints grid = new GridBagConstraints(); // Controla o layout do painel auxiliar
  
  JPanel pentry = new JPanel(new GridBagLayout()); // Contém os paineis de entrada
  JPanel func = new JPanel (new BorderLayout()); // Painel que representa a função
  JTextArea f = new JTextArea(1,39); // Recebe a função
  JPanel valor = new JPanel (new BorderLayout()); // Painel que representa o valor de x
  JTextArea x = new JTextArea(1,9); // Recebe o valor de x
  JPanel erro = new JPanel (new BorderLayout()); // Painel que representa o erro
  JSpinner e = new JSpinner(new SpinnerNumberModel (0.01,0.001,0.1,0.001)); // Determina o valor do erro
  JButton b = new JButton("Calcular"); // Anexa os painéis de resposta na janela principal
  
  JPanel presul = new JPanel(new GridBagLayout()); // Contém painéis de saída
  JPanel primeira = new JPanel (new BorderLayout()); // Painel de f'
  JPanel segunda = new JPanel (new BorderLayout()); // Painel de f''
  JPanel terceira = new JPanel (new BorderLayout()); // Painel de f'''
  JPanel quarta = new JPanel (new BorderLayout()); // Painel de f''''
  JTextArea[] resul = new JTextArea[4]; // Contém as respostas

  //Cria a interface base para recepção dos dados necessários
  Completa (JFrame j) {
	  
	int i;
	  
	jan=j;
	setLayout (new BorderLayout(0,10));
	sb.appendCodePoint(0X03B5);
	((DefaultEditor) e.getEditor()).getTextField().setEditable(false);
	func.add(new JLabel ("f(x) ="),BorderLayout.WEST);
	func.add(f,BorderLayout.EAST);
	valor.add(new JLabel ("x ="),BorderLayout.WEST);
	valor.add(x,BorderLayout.EAST);
	erro.add(new JLabel (sb.charAt(0)+" ="),BorderLayout.WEST);
    erro.add(e,BorderLayout.EAST);
    grid.gridx=0;
    grid.gridy=0;
    grid.gridwidth=3;
	pentry.add(func,grid);
	grid.gridy++;
    grid.gridwidth=1;
    grid.insets=new Insets(5,14,0,0);
	pentry.add(valor,grid);
	grid.gridx++;
	grid.insets=new Insets(5,52,0,0);
	pentry.add(erro,grid);
	grid.gridx++;
	grid.insets=new Insets(5,64,0,0);
	pentry.add(b,grid);
	add(pentry,BorderLayout.NORTH);
	b.setPreferredSize(new Dimension(120,17));
	b.addActionListener(new ActionListener () {
	    
	  @Override
	  public void actionPerformed(ActionEvent ae) {
		
		double temp=0;
		  
		if (f.getText().length()==0||x.getText().length()==0) {
			
		  JOptionPane.showMessageDialog(warning,"Preencha todos os campos","Erro De Sintaxe",JOptionPane.ERROR_MESSAGE);
		  return;
		  
		}
		
		else if (f.getText().length()>0) { 
		  
		  try {
			
			temp=Double.parseDouble( x.getText().replace(",",".") );
			  
		  }catch (Exception ex) {
		    
		    JOptionPane.showMessageDialog(warning,"Valor de x inválido","Erro De Sintaxe",JOptionPane.ERROR_MESSAGE);
		    return;
		      
		  }

		  mtd.funcao(f.getText(),temp);
		  
		  if (!mtd.ok) {
		    
		    JOptionPane.showMessageDialog(warning,"Função inválida","Erro De Sintaxe",JOptionPane.ERROR_MESSAGE);
		    mtd.ok=true;
		    return;

	      }
		  
		}
		  
		fazPainel();
		temp=Double.parseDouble( x.getText().replace(",",".") );
		resul[0].setText(""+mtd.derCompleta1(f.getText(),temp,(Double)e.getValue()));
		resul[1].setText(""+mtd.derCompleta2(f.getText(),temp,(Double)e.getValue()));
		resul[2].setText(""+mtd.derCompleta3(f.getText(),temp,(Double)e.getValue()));
		resul[3].setText(""+mtd.derCompleta4(f.getText(),temp,(Double)e.getValue()));
	    add(presul,BorderLayout.SOUTH);
	    jan.pack();
		  	
      }
	      
	});
	  	
  }
  
  //Cria o painel que mostra a resposta do cálculo
  public void fazPainel () {
		
	int i;
	  
	for(i=0;i<4;i++) resul[i]= new JTextArea(1,13);
	for(i=0;i<4;i++) resul[i].setEditable(false);
	grid.gridx=0;
	grid.gridy=0;
	grid.insets=new Insets(2,10,0,0);
	primeira.add(new JLabel ("f'   "),BorderLayout.WEST);
	primeira.add(resul[0],BorderLayout.EAST);
	presul.add(primeira,grid);
	grid.gridx++;
	grid.insets=new Insets(2,20,0,0);
	segunda.add(new JLabel ("f''  "),BorderLayout.WEST);
	segunda.add(resul[1],BorderLayout.EAST);
	presul.add(segunda,grid);
	grid.gridy++;
	grid.gridx=0;
	grid.insets=new Insets(2,10,0,0);
	terceira.add(new JLabel ("f''' "),BorderLayout.WEST);
	terceira.add(resul[2],BorderLayout.EAST);
	presul.add(terceira,grid);
	grid.gridx++;
	grid.insets=new Insets(2,20,0,0);
	quarta.add(new JLabel ("f''''"),BorderLayout.WEST);
	quarta.add(resul[3],BorderLayout.EAST);
	presul.add(quarta,grid);
	  	
  }
	  
}

//Classe do item derivada parcial
class Parcial extends Completa { 
  
  JPanel dimensao = new JPanel (new BorderLayout()); // Pega o numero de xn
  JSpinner nx = new JSpinner(new SpinnerNumberModel (2,2,10,1)); //Controla o número de variáveis da função
  JButton b2 = new JButton ("Ok"); // Vai para a segunda entrada de dados
  
  JPanel pentry2 = new JPanel(new GridBagLayout());
  JPanel[] xnval; // Contém cada xn e recebe seus valores
  JTextArea[] jta; // Recebem o valor de cada xn
  JButton b3 = new JButton("Continuar"); // Cria a janela da hessiana e gradiente
  int a,i; // Variáveis auxiliares/contadores
  double[] xn = new double[10]; //Guarda o valor de cada xn
  
  JFrame janaux = new JFrame ("G & H"); // Janela auxiliar que contém gradiente e hessiana
  JPanel gradiente = new JPanel (new GridLayout(2,0)); // Painel que contém a interface dos valores do gradiente
  JPanel hessiana = new JPanel (); // Painel que contém a interface dos valores da hessiana
  JTextArea[] grad; // Mostram os valores do gradiente
  JTextArea[][] hess; // Mostram os valores do gradiente
  
  //Cria a interface base para recepção dos dados necessários, modificando alguns campos, e preparando uma janela auxiliar de gradiente e hessiana
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
	((DefaultEditor) nx.getEditor()).getTextField().setEditable(false);
	pentry.remove(valor);
	pentry.remove(erro);
	pentry.remove(b);
	dimensao.add(new JLabel ("n"+sb.charAt(11)+"x ="),BorderLayout.WEST);
	dimensao.add(nx,BorderLayout.EAST);
	grid.gridx=0;
	grid.gridy=1;
	grid.insets=new Insets(5,0,0,0);
	pentry.add(dimensao,grid);
	grid.gridx=1;
	grid.insets=new Insets(5,120,0,0);
	pentry.add(erro,grid);
	grid.gridx=2;
	grid.insets=new Insets(5,117,0,0);
	pentry.add(b2,grid);
	nx.addChangeListener( new ChangeListener () {
		
	  @Override
	  public void stateChanged (ChangeEvent e) {
		  
		janaux.setVisible(false);
		gradiente.removeAll();
		hessiana.removeAll();
		  	
	  }
		
	});  
	b2.setPreferredSize(new Dimension(55,17));
	b2.addActionListener(new ActionListener() {
	    
	  @Override
	  public void actionPerformed (ActionEvent e) {
		  
		a = Integer.parseInt(((DefaultEditor) nx.getEditor()).getTextField().getText());
		double[] temp = {0,1,2,3,4,5,6,7,8,9};
		
		if (f.getText().length()==0) {
			
		  JOptionPane.showMessageDialog(warning,"Preencha o campo da função","Erro De Sintaxe",JOptionPane.ERROR_MESSAGE);
		  return;
		  
		}
		
		else if (f.getText().length()>0) { 

		  mtd.funcao(f.getText(),temp,a);
		  
		  if (!mtd.ok) {
		    
		    JOptionPane.showMessageDialog(warning,"Função inválida","Erro De Sintaxe",JOptionPane.ERROR_MESSAGE);
		    mtd.ok=true;
		    return;

	      }
		  
		}

		fazPainel2();
		add(pentry2,BorderLayout.CENTER);
		jan.pack();
		  	
      }
	     
	});
	  	
  }

  //Cria o painel que recebe o valor de cada xn, e os distribui simetricamente na tela para serem preenchidos
  public void fazPainel2() {
		
	xnval = new JPanel[a];
	jta = new JTextArea[a];
	for (i=0;i<a;i++) xnval[i]=new JPanel(new BorderLayout());
	for (i=0;i<a;i++) jta[i]=new JTextArea(1,3);
	for (i=0;i<a;i++) {
		  
	  xnval[i].add(new JLabel ("x"+sb.charAt(i+1)+" ="),BorderLayout.WEST);
	  xnval[i].add(jta[i],BorderLayout.EAST);  
		  
	}
	pentry2.removeAll();
	grid.gridx=0;
	grid.gridy=0;
	grid.insets=new Insets(4,2,4,2);
	for (i=0;i<a;i++) {
		
	  pentry2.add(xnval[i],grid);
	  grid.gridx++;
		
	  if (i==4||i==9) { grid.gridy++; grid.gridx=0; }  
		  
	}
	grid.gridx=0;
	grid.gridy++;
	if (a<=5) grid.gridwidth=a;
	else grid.gridwidth=5;
	grid.fill=grid.HORIZONTAL;
	pentry2.add(b3,grid);
    b3.addActionListener( new ActionListener () {
		
      @Override
	  public void actionPerformed (ActionEvent e) {
		  
		double temp;
		boolean boo=true;
		  
		for (i=0;i<a&&boo;i++) { 
		
		  try {
			
			temp=Double.parseDouble( jta[i].getText().replace(",",".") );
			  
		  }catch (Exception ex) {
		    
		    JOptionPane.showMessageDialog(warning,"Os x[n] não foram preenchidos ou são inválidos","Erro De Sintaxe",JOptionPane.ERROR_MESSAGE);
		    boo=false;
		    return;
		      
		  }
		  
		}
		
		for (i=0;i<a;i++) xn[i]=Double.parseDouble( jta[i].getText().replace(",",".") );
		
		fazJanela();
		janaux.setLocationRelativeTo(null);
		janaux.pack();
		janaux.setResizable(false);
		janaux.setVisible(true);
		  	
      }
		  
	});
	b3.setPreferredSize(new Dimension(100,17));
	grid.gridwidth=1;
	grid.fill=grid.NONE;
	
	repaint();
	  
  }
  
  //Cria a interface da janela auxiliar de gradiente e hessiana
  public void fazJanela() {
		
	int i,j;
    
    gradiente.removeAll();
    hessiana.removeAll();
	grad = new JTextArea[a];
	hess = new JTextArea[a][a];
	hessiana.setLayout(new GridLayout(a+1,a+1));
	((GridLayout)gradiente.getLayout()).setHgap(5);
	((GridLayout)hessiana.getLayout()).setHgap(5);
	((GridLayout)hessiana.getLayout()).setVgap(5);
	gradiente.setBorder(BorderFactory.createTitledBorder("Gradiente"));
	hessiana.setBorder(BorderFactory.createTitledBorder("Hessiana"));
	  
	for (i=0;i<a;i++) { 
	  
	  grad[i] = new JTextArea(1,5); 
	  grad[i].setEditable(false); 
	  
	}
	  
	for (i=0;i<a;i++) 
	  for (j=0;j<a;j++) {
			
	    hess[i][j]= new JTextArea(1,5);
	    hess[i][j].setEditable(false);
	      
	  }
	      
	for (i=0;i<a;i++) 
	  
	  grad[i].setText(mtd.derParcial1(f.getText(),xn,i,a,(Double)e.getValue())+"");
	
	for (i=0;i<a;i++)
      for (j=i;j<a;j++)
      
        hess[i][j].setText(mtd.derParcial2(f.getText(),xn,i,j,a,(Double)e.getValue())+"");
        
    for (i=1;i<a;i++)
      for (j=i;j>=0;j--)
        hess[i][j].setText(hess[j][i].getText()); 
	
	for (i=0;i<a;i++)   
	  for (j=0;j<a;j++) {
	    
	    if (i==0) gradiente.add(new JLabel ("f x"+sb.charAt(j+1)));
	    else gradiente.add(grad[j]);
	    
	  }
	  
	hessiana.add(new JLabel ("      f"));
	  
	for (i=0;i<a+1;i++)
	  for (j=0;j<a+1;j++) {
	  
	    if (i==0&&j>0) hessiana.add(new JLabel ("      x"+sb.charAt(j)));
	    
	    else if (j==0&&i>0) hessiana.add(new JLabel ("      x"+sb.charAt(i)));
	    
	    else if (i>0&&j>0) hessiana.add(hess[i-1][j-1]);
	  
      }
      
    janaux.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	janaux.setLayout(new BorderLayout(6,6));
	janaux.add(gradiente,BorderLayout.NORTH);
	janaux.add(hessiana,BorderLayout.SOUTH);
	
  }
	  
}

//Classe do itrm raiz de função em um intervalo 
class Raiz extends Completa {
  
  JPanel valuea = new JPanel(new BorderLayout()); // Painel que representa o ponto a
  JTextArea pa = new JTextArea(1,5); //Recebe o valor do ponto a
  JPanel valueb = new JPanel(new BorderLayout()); // Painel que representa o ponto b 
  JTextArea pb = new JTextArea(1,5); //Recebe o valor do ponto b
  JButton b4 = new JButton ("Checar Intervalo"); // Anexa a próxima parte do programa e calcula o número de intervalos com raiz
  int intervalos=0; // Guarda o numero de intervlos com raiz
  
  String[] metodos = {"Divisão ao Meio","Cordas","Newton","Cordas Modificado","Newton Modificado"}; // Contém o nome dos metodos de calculo de raiz
  JComboBox<String> jcbox = new JComboBox <> (metodos); // Contém os metodos de calculo de raiz
  int metodo=0; // metodo utilizado efeteivamente no calculo
  String[] colunas = {"a","b","Raiz"}; // Contém o nome das colunas da tabelas
  Vector<BigDecimal> x1 = new Vector<>(); // Contém o valor de cada ponto a
  Vector<BigDecimal> x2 = new Vector<>(); // Contém o valor de cada ponto b
  Object[][] data; // Contém os dados mostrados na tabela
  ButtonColumn buttonColumn; // Instancia da classe que cria os botões na tabela
  boolean[] calculado;
  JTable table; // Tabela que mostra os intervalos
  JScrollPane jsp; // Contém a tabela
  
  //Cria a interface inicial de recepção de dados fazendo algumas modificações
  Raiz (JFrame j) {
		
	super(j);
	
	pentry.removeAll();
	valuea.add(new JLabel("a ="),BorderLayout.WEST);
	valuea.add(pa,BorderLayout.EAST);
	valueb.add(new JLabel("b ="),BorderLayout.WEST);
	valueb.add(pb,BorderLayout.EAST);
	grid.gridx=0;
	grid.gridy=0;
	grid.gridwidth=4;
	grid.insets = new Insets(0,0,0,0);
	pentry.add(func,grid);
	grid.gridwidth=1;
	grid.gridy++;
	grid.insets = new Insets(5,14,0,0);
	pentry.add(valuea,grid);
	grid.gridx++;
	grid.insets = new Insets(5,5,0,0);
	pentry.add(valueb,grid);
	grid.gridx++;
	grid.insets = new Insets(5,18,0,0);
	pentry.add(erro,grid);
	grid.gridx++;
	grid.insets = new Insets(5,26,0,0);
	pentry.add(b4,grid);
	b4.setPreferredSize(new Dimension(150,17));
	b4.addActionListener (new ActionListener () {
		
	  @Override
	  public void actionPerformed (ActionEvent e) {
		
		double temp=0;
		  
		if (f.getText().length()==0||pa.getText().length()==0||pb.getText().length()==0) {
			
		  JOptionPane.showMessageDialog(warning,"Preencha todos os campos","Erro De Sintaxe",JOptionPane.ERROR_MESSAGE);
		  return;
		  
		}
		
		else if (f.getText().length()>0) { 
		  
		  try {
			
			temp=Double.parseDouble( pa.getText().replace(",",".") );
			temp=Double.parseDouble( pb.getText().replace(",",".") );
			  
		  }catch (Exception ex) {
		    
		    JOptionPane.showMessageDialog(warning,"Valor inválido","Erro De Sintaxe",JOptionPane.ERROR_MESSAGE);
		    return;
		      
		  }

		  mtd.funcao(f.getText(),temp);
		  
		  if (!mtd.ok) {
		    
		    JOptionPane.showMessageDialog(warning,"Função inválida","Erro De Sintaxe",JOptionPane.ERROR_MESSAGE);
		    mtd.ok=true;
		    return;

	      }
		  
		}
		
		//mtd.raizBusca(f,,b);
		intervalos=5;
		data = new Object[intervalos][3];  
		done();
		jan.pack();
		  	
	  }
	     
	});
	  
  }

  //Cria a tabela que mostra os intervalos que possuem raízes
  public void done() {
     
    int i;
    
    add(jcbox,BorderLayout.CENTER);
    jcbox.addActionListener(new ActionListener () {
	 	
	  @Override
      public void actionPerformed(ActionEvent e) {
		 
        System.out.println(jcbox.getSelectedIndex());
        
      }
      
    }); 
    
    calculado = new boolean[intervalos];
	
	for (i=0;i<intervalos;i++) {
	  
	  calculado[i]=false;
	  	
	}
    
    for (i=0;i<intervalos;i++) {
	  
	  data[i][0] = new BigDecimal(i);
	  data[i][1] = new BigDecimal(i);
	  data[i][2] = "Calcular";
	  	
	}
    
    //Alguns métodos modificados
    table = new JTable (data,colunas) {
	  
	  //Controla se uma célula pode ser editada
	  public boolean isCellEditable(int row, int column) {
        
        if (column==2&&!calculado[row]) return true;
        
        else return false;
        
      }
      
      //Controla o renderizador de uma célula
      public TableCellRenderer getCellRenderer(int row, int column) {
      
        if (column==2&&calculado[row]) {
      
          return super.getCellRenderer(0, 0);
    
        }
    
        else {
    
          return super.getCellRenderer(row, column);
      
         }
    
      }
      
      //Controla o editor de uma célula
      public TableCellEditor getCellEditor (int row, int column) {
      
        if (column==2&&calculado[row]) {
      
          return super.getCellEditor(0, 0);
    
        }
    
        else {
    
          return super.getCellEditor(row, column);
      
         }
    
      }
      
	};
    
    //A ação realizada pelos botões do jtable
    Action intervalo = new AbstractAction() {
      
      public void actionPerformed(ActionEvent e){
		  
        JTable table = (JTable)e.getSource();
        int modelRow = Integer.valueOf( e.getActionCommand() );
        System.out.println("Linha: "+modelRow);
        //modelRow = index do vector com valor de interesse
        //if (!metodo)
        //else if (metodo==1)
        data[modelRow][2]=modelRow+"";
        calculado[modelRow]=true;
        repaint();
         
      }
   
    };
    
    buttonColumn = new ButtonColumn(table, intervalo, 2);
    jsp = new JScrollPane (table);
    add(jsp,BorderLayout.SOUTH);
    jsp.setPreferredSize(new Dimension (300,80));
     
  }
  
  //Guarda os dados recebidos em cada respectivo vector
  public void novoDado(BigDecimal a,BigDecimal b) {
     
    x1.add(a);
    x2.add(b);
       
  }
  
    
}

//Classe que renderiza botões em uma coluna de jtable, trocando o editor e renderizador da mesma
class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
	
  JTable table; // Instancia da tabela modificada
  Action action; // Ação realizada pelos botões
  int mnemonic;  // Variavel da celula com foco
  Border originalBorder; // Contém a borda original do botão
  Border focusBorder; // Monta a borda de foco do botão
  JButton renderButton;  // Instancia do botão renderizado
  JButton editButton; // Instancia que carrega as funções do botão para a coluna
  Object editorValue; //  Recebe o que está na celula antes dela virar botão
  boolean isButtonColumnEditor; // Muda o design da celula quando o botão é clicado

  //Recebe as instâncias necessárias para renderizar os botões
  ButtonColumn(JTable table, Action action, int column) {
	  
	this.table = table;
	this.action = action;

	renderButton = new JButton();
	editButton = new JButton();
	editButton.setFocusPainted( false );
	editButton.addActionListener( this );
	originalBorder = editButton.getBorder();
	setFocusBorder( new LineBorder(Color.BLUE) );

	TableColumnModel columnModel = table.getColumnModel();
	columnModel.getColumn(column).setCellRenderer( this );
	columnModel.getColumn(column).setCellEditor( this );
	table.addMouseListener( this );
	  
  }
  
  //Desenha a borda de foco quando  botão está focado
  public Border getFocusBorder() {
		
	return focusBorder;
  
  }
  
  //Coloca a borda de foco no botão focado
  public void setFocusBorder(Border focusBorder) {
	
	this.focusBorder = focusBorder;
	editButton.setBorder( focusBorder );
  
  }
  
  //Escolha a celula com foco
  public int getMnemonic() {
	
	return mnemonic;
	
  }
  
  //Prepara o mnemonic
  public void setMnemonic(int mnemonic) {
		
    this.mnemonic = mnemonic;
	renderButton.setMnemonic(mnemonic);
	editButton.setMnemonic(mnemonic);
		
  }

  //Prepara a string do jbutton
  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		
    if (value == null){
		
		editButton.setText( "" );
		editButton.setIcon( null );
		
	}
		
	else if (value instanceof Icon) {
	  
	  editButton.setText( "" );
	  editButton.setIcon( (Icon)value );
	
	}
		
	else {
	  
	  editButton.setText( value.toString() );
	  editButton.setIcon( null );
	
	}

	this.editorValue = value;
	return editButton;
		
  }

  //Retorna o editor da célula
  @Override
  public Object getCellEditorValue(){
		
	return editorValue;
  
  }
  
  // Implementa a interface de renderizador de celula da tabela
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
	  
	if (isSelected) {
		
	  renderButton.setForeground(table.getSelectionForeground());
	  renderButton.setBackground(table.getSelectionBackground());
	
	}
		
	else {
		
	  renderButton.setForeground(table.getForeground());
	  renderButton.setBackground(UIManager.getColor("Button.background"));
		
	}

    if (hasFocus){
			
	  renderButton.setBorder( focusBorder );
	
	}
		
	else {
			
	  renderButton.setBorder( originalBorder );
	
	}
	
	if (value == null) {
	  
	  renderButton.setText( "" );
	  renderButton.setIcon( null );
	
	}
		
	else if (value instanceof Icon) {
	  
	  renderButton.setText( "" );
	  renderButton.setIcon( (Icon)value );
		
	}
	
	else {
	  
	  renderButton.setText( value.toString() );
	  renderButton.setIcon( null );
		
	}

	return renderButton;
	
  }

  //Chama a ação dada ao botão  
  public void actionPerformed(ActionEvent e) {
	
	int row = table.convertRowIndexToModel( table.getEditingRow() );
	fireEditingStopped();

	ActionEvent event = new ActionEvent(table,ActionEvent.ACTION_PERFORMED,"" + row);
	action.actionPerformed(event);
	
  }

  //Detecta se o mouse foi pressionado
  public void mousePressed(MouseEvent e) {
    	
    if (table.isEditing() && table.getCellEditor() == this)
	  
	  isButtonColumnEditor = true;
    
  }
  
  //Detecta se a tecla do mouse foi solta
  public void mouseReleased(MouseEvent e) {
    	
    if (isButtonColumnEditor && table.isEditing())
    		
      table.getCellEditor().stopCellEditing();

	isButtonColumnEditor = false;
  
  }

  public void mouseClicked(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}

}

//Classe que contém a main e controla o painel com abas que vai para cada método
class Metodos {
  
  Expressao exp = new Expressao();
  int opc=1;
  JFrame warning; // Janela de aviso
  JFrame jan = new JFrame ("Metodos"); // Janela principal
  Derivada der;
  boolean ok=true;
  
  //Cria as abas
  public static void main(String[] args) {
	  
	Metodos mtd = new Metodos();
	JFrame jan = new JFrame ("Metodos");
	mtd.der = new Derivada(jan);
	  
    JTabbedPane tp = new JTabbedPane (); // Contém as abas que separam as áreas do programa
    ImageIcon icon = new ImageIcon("Derivada_v2.jpg"); // Icone da aba de derivada
    
    tp.addTab("D & R",icon,mtd.der,"Deriva e Acha Raízes");
    jan.add(tp);
    jan.pack();
    jan.setResizable(false);
    jan.setVisible(true);
    
    
  }
  
  //Calcula o valor de uma função dada em um ponto dado
  public double funcao(String funcao,double x) {
	  
	double num = 0;
	
	exp.variavel("x",x);
	
	try {
		  
	  num = exp.valor(funcao);
	  
	}catch(Exception e){ 
	  
	  ok=false;
	  	
	}
	    
    return num;
		
  }
  
  //Calcula o valor de uma função dada em um ponto dado 
  public double funcao(String funcao,double[] x,int n) {
	
	int i;
	double num = 0;
	
	for (i=0;i<n;i++) exp.variavel("x["+i+"]",x[i]);
	
	try {
		  
	  num = exp.valor(funcao);
	  
	}catch(Exception e){ 
	  
	  ok=false;
	  	
	}
	    
    return num;
		
  }

  //Calcula a derivada completa de primeira ordem
  public double derCompleta1 (String f,double x,double e) {
	
	int i;
	double delta;
    double p;
    double q;
    boolean go=true;
    double erro=Double.MAX_VALUE;
    double resp=0;
    
    if (1024*e>2) delta=100*e;
    
    else delta=1024*e;

    p = (funcao(f,x+delta) - funcao(f,x-delta)) / (2*delta);
    
    for (i=0;i<50&&go;i++) {
      
      q = p;
      delta /= 2;
      p = (funcao(f,x+delta) - funcao(f,x-delta)) / (2*delta);
      
      if (Math.abs(p-q)<erro) erro = Math.abs(p-q);
      
      else {
		
		resp=q;  
		go=false;
		
	  }
      
      if (Math.abs(p-q)<e) {
		
		resp=p;
		go=false;
		  
	  }
         
    }
		
    return resp;
    	  	
  }
  
  //Calcula a derivada completa de segunda ordem
  public double derCompleta2 (String f,double x,double e) {
	
	int i;
	double delta;
    double p;
    double q;
    boolean go=true;
    double erro=Double.MAX_VALUE;
    double resp=0;
    
    if (1024*e>2) delta=100*e;
    
    else delta=1024*e;

    p = (funcao(f,x+2*delta) -2*funcao(f,x)  +funcao(f,x-2*delta) ) / (Math.pow(2*delta,2));
    
    for (i=0;i<50&&go;i++) {
      
      q = p;
      delta /= 2;
      p = (funcao(f,x+2*delta) -2*funcao(f,x)  +funcao(f,x-2*delta) ) / (Math.pow(2*delta,2));
      
      if (Math.abs(p-q)<erro) erro = Math.abs(p-q);
      
      else {
		
		resp=q;  
		go=false;
		
	  }
      
      if (Math.abs(p-q)<e) {
		
		resp=p;
		go=false;
		  
	  }
         
    }
		
    return resp;
    	  	
  }
  
  //Calcula a derivada completa de terceira ordem
  public double derCompleta3 (String f,double x,double e) {
	
	int i;
	double delta;
    double p;
    double q;
    boolean go=true;
    double erro=Double.MAX_VALUE;
    double resp=0;
    
    if (1024*e>2) delta=100*e;
    
    else delta=1024*e;

    p = (funcao(f,x+3*delta) -3*funcao(f,x+delta) +3*funcao(f,x-delta)  -funcao(f,x-3*delta) ) / (Math.pow(2*delta,3));
    
    for (i=0;i<50&&go;i++) {
      
      q = p;
      delta /= 2;
     p = (funcao(f,x+3*delta) -3*funcao(f,x+delta) +3*funcao(f,x-delta)  -funcao(f,x-3*delta) ) / (Math.pow(2*delta,3));
      
      if (Math.abs(p-q)<erro) erro = Math.abs(p-q);
      
      else {
		
		resp=q;  
		go=false;
		
	  }
      
      if (Math.abs(p-q)<e) {
		
		resp=p;
		go=false;
		  
	  }
         
    }
		
    return resp;
    	  	
  }
  
  //Calcula a derivada completa de quarta ordem
  public double derCompleta4 (String f,double x,double e) {
	
	int i;
	double delta;
    double p;
    double q;
    boolean go=true;
    double erro=Double.MAX_VALUE;
    double resp=0;
    
    if (1024*e>2) delta=100*e;
    
    else delta=1024*e;

    p = (funcao(f,x+4*delta) -4*funcao(f,x+2*delta) +6*funcao(f,x) -4*funcao(f,x-2*delta) +funcao(f,x-4*delta) ) / (Math.pow(2*delta,4));
    
    for (i=0;i<50&&go;i++) {
      
      q = p;
      delta /= 2;
      p = (funcao(f,x+4*delta) -4*funcao(f,x+2*delta) +6*funcao(f,x) -4*funcao(f,x-2*delta) +funcao(f,x-4*delta) ) / (Math.pow(2*delta,4));
      
      if (Math.abs(p-q)<erro) erro = Math.abs(p-q);
      
      else {
		
		resp=q;  
		go=false;
		
	  }
      
      if (Math.abs(p-q)<e) {
		
		resp=p;
		go=false;
		  
	  }
         
    }
		
    return resp;
    	  	
  }
  
  //Calcula a derivada parcial de primeira ordem 
  public double derParcial1(String f,double[] x,int i,int n,double e) {
	
	int cont;
	double delta;
    double p;
    double q;
    boolean go=true;
    double erro=Double.MAX_VALUE;
    double resp=0;
    double aux,f1,f2;
	
	if (1024*e>2) delta=100*e;
    
    else delta=1024*e;

    aux = x[i];
    x[i] = aux+delta;
    f1 = funcao (f,x,n);
    x[i] = aux-delta;
    f2 = funcao (f,x,n);
    p = (f1-f2) / (2*delta);
    
    for (cont=0;cont<50&&go;cont++) {
		
      q = p;
      delta /= 2;
      x[i] = aux+delta;
      f1 = funcao (f,x,n);
      x[i] = aux-delta;
      f2 = funcao (f,x,n);
      p = (f1-f2) / (2*delta);
      
      
      if (Math.abs(p-q)<erro) erro=Math.abs(p-q);
      
      else {
        
        resp=q;  
		go=false;
		
	  }
	  
        
      if (Math.abs(p-q)<e) {
		
		resp=p;
		go=false;
		  
	  }
    
    }
    
    x[i]=aux;
    
    return resp;
	  
  }
  
  //Calcula a derivada parcial de segunda ordem 
  public double derParcial2(String f,double[] x,int i,int j,int n,double e) {
	
	int cont;
	double delta;
    double p;
    double q;
    boolean go=true;
    double erro=Double.MAX_VALUE;
    double resp=0;
    double aux,aux2,f1,f2,f3,f4;
	
	if (1024*e>2) delta=100*e;
    
    else delta=1024*e;

    aux = x[i];
    aux2 = x[j];
    
    if (i!=j) {
    
      x[i] = aux+delta;
      x[j] = aux2+delta;
      f1 = funcao (f,x,n);
      x[j] = aux2-delta;
      f2 = funcao (f,x,n);
      x[i] = aux-delta;
      f4 = funcao (f,x,n);
      x[j] = aux2+delta;
      f3 = funcao (f,x,n);
      p = (f1-f2-f3+f4) / (4*delta*delta);
      
    }
    
    else {

      x[i] = aux+2*delta;
      f1 = funcao (f,x,n);
      x[i] = aux-2*delta;
      f3 = funcao (f,x,n);
      x[i] = aux;
      f2 = funcao (f,x,n);
      p = (f1-2*f2+f3) / (4*delta*delta);

    }
    
    for (cont=0;cont<50&&go;cont++) {
		
      q = p;
      delta /= 2;
      
      if (i!=j) {
    
        x[i] = aux+delta;
        x[j] = aux2+delta;
        f1 = funcao (f,x,n);
        x[j] = aux2-delta;
        f2 = funcao (f,x,n);
        x[i] = aux-delta;
        f4 = funcao (f,x,n);
        x[j] = aux2+delta;
        f3 = funcao (f,x,n);
        p = (f1-f2-f3+f4) / (4*delta*delta);
      
      }
    
      else {

        x[i] = aux+2*delta;
        f1 = funcao (f,x,n);
        x[i] = aux-2*delta;
        f3 = funcao (f,x,n);
        x[i] = aux;
        f2 = funcao (f,x,n);
        p = (f1-2*f2+f3) / (4*delta*delta);

      }
      
      if (Math.abs(p-q)<erro) erro=Math.abs(p-q);
      
      else {
        
        resp=q;  
		go=false;
		
	  }
	  
        
      if (Math.abs(p-q)<e) {
		
		resp=p;
		go=false;
		  
	  }
    
    }
    
    return resp;
	  
  }
  
  public void raizBusca (String f,int a,int b) {
	
	
	  
  }
  
}
