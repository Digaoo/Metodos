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

//Consertar bug da janela g&h de resetar o botão e continuar adicionando elementos
//Para raiz preparar classe com matriz big decimal e vetor jbutton, para fazer a matriz desejada
//Adicionar item jacobiano
//Possibilitar guardar dados em arquivos
//Colocar combo box em raizes para escolher método de encontrar raízes
//Modificar derivada parcial para que vá apenas até a derivada segunda

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
  Expressao exp = new Expressao(); // Instancia do interpretador de funções
  JPanel[] p = new JPanel[6]; // Paineis da interface que recebe valor de x,função e erro (entrada)
  JPanel[] p2 = new JPanel[4]; // Paineis da interface que mostra a resposta
  JPanel paux = new JPanel(new GridBagLayout()); // Contém os paineis com a interface da resposta
  JPanel pentry = new JPanel(new BorderLayout(0,3)); // Contém os paineis de entrada
  JPanel presul = new JPanel(new BorderLayout()); // Contém painéis de saída
  GridBagConstraints grid = new GridBagConstraints(); // Controla o layout do painel auxiliar
  JTextArea func = new JTextArea(); // Recebe a função
  JTextArea ponto = new JTextArea(); // Recebe o valor de x
  JSpinner erro = new JSpinner(new SpinnerNumberModel (0.01,0.001,0.1,0.001)); // Determina o valor do erro
  JTextArea[] resul = new JTextArea[4]; // Contém as respostas
  JButton b = new JButton("Calcular"); // Anexa os painéis de resposta na janela principal
  StringBuilder sb = new StringBuilder(); // Guarda caracteres especias a serem utilizados ao longo do programa
  // h = 1000*erro
  
  //Cria a interface base para recepção dos dados necessários
  Completa (JFrame j) {
	  
	int i;
	  
	jan=j;
	setLayout (new BorderLayout(0,10));
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
  
  //Cria o painel que mostra a resposta do cálculo
  public void fazPainel () {
		
	int i;
	  
	for(i=0;i<4;i++) resul[i]= new JTextArea();
	for(i=0;i<4;i++) resul[i].setColumns(13);
	for(i=0;i<4;i++) resul[i].setEditable(false);
	for(i=0;i<4;i++) p2[i]= new JPanel(new BorderLayout());
	grid.gridx=0;
	grid.gridy=0;
	grid.insets=new Insets(2,42,2,2);
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
  
  //Calcula o valor de uma função dada em um ponto dado
  public double funcao(double x,String funcao) {
	  
	double num = 0;
	  
	try{
		  
	  num = exp.valor(funcao);
		
	}catch(Exception e){}
	    
    return num;
		
  }

  //Calcula o valor da derivada para o ponto com o erro dado	
  public double calculaDerivada (String f,double x,double erro,double h) {
		
	return 1.23;
	  	
  }
	  
}

//Classe do item derivada parcial
class Parcial extends Completa { 
	  
  JSpinner nx = new JSpinner(new SpinnerNumberModel (2,2,10,1)); //Controla o número de variáveis da função
  JSpinner[] der = new JSpinner[4]; // Vetor com os spinners que controlam em função de que xn cada derivada será feita
  JPanel aux = new JPanel(new BorderLayout(0,5)); // Contém os paineis que recebem o valor de cada xn e a partir de quais deles será feita a derivada
  JPanel[] p3; // Contém cada xn e recebe seus valores
  JPanel[] p4 = new JPanel [10]; // Contém a partir de qual xn cada derivada será calculada
  JPanel[] p5 = new JPanel [7]; // Paineis com as respostas das derivadas
  JPanel what = new JPanel (); // Painel que contém o botão para criar a janela auxiliar
  JTextArea[] jta; // Recebem o valor de cada xn
  JTextArea[] jta2 = new JTextArea[4]; // Contém a resposta das derivadas
  StringBuilder str; // Forma os jlabels da resposta
  int[] aux2 = new int[4]; // Recebe o indice de cada xn que será usado nas derivadas
  int a,i; // Variáveis auxiliares/contadores
  JFrame janaux = new JFrame ("G & H"); // Janela auxiliar que contém gradiente e hessiana
  JPanel janp = new JPanel (new GridLayout(1,0)); // Painel que contém a interface dos valores do gradiente
  JPanel janp2 = new JPanel (); // Painel que contém a interface dos valores da hessiana
  JTextArea[] grad; // Mostram os valores do gradiente
  JTextArea[][] hess; // Mostram os valores do gradiente
  boolean boo=false; // Controla o andamento da primeira entrada do progrma
  boolean[] boo2 = new boolean[10]; // Controla o andamento da segunda entrada do programa
  
  //Cria a interface base para recepção dos dados necessários, modificando alguns campos, e preparando uma janela auxiliar de gradiente e hessiana
  Parcial (JFrame j) {
	  
	super(j);
	func.addCaretListener(new CaretListener () {
		  
	  @Override
	  public void caretUpdate (CaretEvent e) {
	  
	    boo=true;
	  	
	  }
	      
	});
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

  //Cria o painel que recebe o valor de cada xn, e os distribui simetricamente na tela para serem preenchidos
  public void fazPainel2() {
	  	
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
  
  //Cria o painel de cada derivada recebendo a informação de em função de que xn essa derivada será calculada
  public void fazPainel3 () {
	  
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
  
  //Cria o painel com o resultado do calculo das derivadas parciais
  public void fazPainel4 () {
	  
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
  
  //Cria a interface da janela auxiliar de gradiente e hessiana
  public void fazJanela() {
		
	int i,j;
	
	janaux.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	janaux.setLayout(new BorderLayout(6,6));
	janaux.add(janp,BorderLayout.NORTH);
	janaux.add(janp2,BorderLayout.SOUTH);
	janaux.setLocationRelativeTo(null);
	janaux.setResizable(false);
	janaux.setVisible(false);
	  
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

//Classe do itrm raiz de função em um intervalo 
class Raiz extends Completa {
	  
  JTextArea pa = new JTextArea(1,5); //Recebe o valor do ponto a
  JTextArea pb = new JTextArea(1,5); //Recebe o valor do ponto b
  JTable table; // Tabela que mostra os intervalos
  String[] colunas = {"a","b","Raiz"}; // Contém o nome das colunas da tabelas
  JScrollPane jsp; // Contém a tabela
  Vector<BigDecimal> x1 = new Vector<>(); // Contém o valor de cada ponto a
  Vector<BigDecimal> x2 = new Vector<>(); // Contém o valor de cada ponto b
  Object[][] data = new Object[5][3]; // Contém os dados mostrados na tabela
  Integer datum [][]={{1,2},{3,4},{5,6},{7,8},{9,10}}; // Temporário  
  
  //Cria a interface inicial de recepção de dados fazendo algumas modificações
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
  
  //Guarda os dados recebidos em cada respectivo vector
  public void novoDado(BigDecimal a,BigDecimal b) {
     
    x1.add(a);
    x2.add(b);
       
  }
  
  //Cria a tabela que mostra os intervalos que possuem raízes
  public void done() {
     
    int i;
    
    for (i=0;i<5;i++) {
	  
	  data[i][0] = new BigDecimal(i);
	  data[i][1] = new BigDecimal(i);
	  data[i][2] = "Calcular";
	  	
	}
    
    table = new JTable (data,colunas) {
	  
	  public boolean isCellEditable(int row, int column) {
        
        // Colocar mecanismo para tirar linhas especificas
        return (column==2);
        
      }
      
	};
    
    Action intervalo = new AbstractAction() {
      
      public void actionPerformed(ActionEvent e){
		  
        JTable table = (JTable)e.getSource();
        int modelRow = Integer.valueOf( e.getActionCommand() );
        System.out.println("Linha: "+modelRow);
        //modelRow = index do vector com valor de interesse
        
         
      }
   
    };
    ButtonColumn buttonColumn = new ButtonColumn(table, intervalo, 2);
    jsp = new JScrollPane (table);
    add(jsp,BorderLayout.SOUTH);
    jsp.setPreferredSize(new Dimension (300,80));
     
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
  
  //Cria as abas
  public static void main(String[] args) {
	  
    JTabbedPane tp = new JTabbedPane (); // Contém as abas que separam as áreas do programa
    JFrame jan = new JFrame ("Metodos"); // Janela principal
    ImageIcon icon = new ImageIcon("Derivada_v2.jpg"); // Icone da aba de derivada
    
    tp.addTab("D & R",icon,new Derivada(jan),"Deriva e Acha Raízes");
    jan.add(tp);
    jan.pack();
    jan.setResizable(false);
    jan.setVisible(true);
    
    
  } 
  
}
