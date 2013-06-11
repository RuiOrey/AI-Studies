import java.util.*;
import java.lang.*;
import java.io.*;

class Arvore{
	Scanner in=new Scanner(System.in);
	Tabuleiro pai;
	int depthfinal=100000;
	Tabuleiro dest;
	int opc;
	int depthpi=0;
	int gerados,visitados=0;
	ArrayList<Tabuleiro> principal = new ArrayList<Tabuleiro>();
	ArrayList<Tabuleiro> auxiliar = new ArrayList<Tabuleiro>();
	
	Arvore(Tabuleiro inicio, Tabuleiro destino)
	
	{
		pai=inicio;
		dest=destino;
	}

	public int getDepth1(){return depthfinal;}
	
	public void putDepth(int deptt){
		depthfinal=deptt;
	}

	public void algoritmos (){

System.out.println("ESCOLHA O TIPO DE PESQUISA :");
		System.out.println("1.profundidade\n2.largura\n3.profundidade iterativa\n4.greed\n5.estrela");
		
		opc=in.nextInt();
		Tabuleiro fim;
		final long inicio=System.currentTimeMillis();
		

		switch(opc){
			case 1: fim=prof();
					break;

			case 2:fim =larg();
					break;
			case 3: fim=profit();
					break;
			case 4: fim=greed();
					break;
			case 5: fim=estrela();
					break;
			default: fim=null;
		}
	System.out.println("\n\n\n\n\n-----------------------------TERMINADO\n");
	final long tempototal = System.currentTimeMillis() - inicio;
	System.out.println("TEMPO DE COMPUTACAO\nEm milisegundos: " + tempototal +"ms.\nArredondado a segundos: "+(tempototal/1000 )+"s.");
	System.out.println("\nProfundidade: "+fim.depth);
	System.out.printf("\nNos Visitados: %d\nNos Gerados: %d\n",visitados,gerados);

	}

public Tabuleiro prof(){							//busca em profundidade
principal.add(pai);									//adiciona pai a lista principal

while(!principal.isEmpty()){						//enquanto a lista nao estiver vazia remove o primeiro elemento
		Tabuleiro actual=principal.remove(0);		//e analisa se é final. se não for expande
		visitados++;
		if (actual.isFinal(dest.tabuleiro)){
			return actual;

		}
		else{
		
			principal.addAll(0,expand(actual));
		}
	}
	return pai;

}

public Tabuleiro larg(){

principal.add(pai);									//adiciona pai a lista principal
while(!principal.isEmpty()){						//enquanto a lista nao estiver vazia remove o primeiro elemento
		Tabuleiro actual=principal.remove(0);		//e analisa se é final. se não for expande
		visitados++;
		if (actual.isFinal(dest.tabuleiro)){
			return actual;

		}
		else{
		
			principal.addAll(expand(actual));
		}
	}
	return pai;

}
public Tabuleiro profit(){
			principal.add(pai);				//adiciona pai a lista principal
			for (int i=0;i<10000;i++){
					principal.clear();
					principal.add(pai);	
						while(!principal.isEmpty()){						//enquanto a lista nao estiver vazia remove o primeiro elemento
								Tabuleiro actual=principal.remove(0);		//e analisa se é final. se não for expande
								visitados++;
								if (actual.isFinal(dest.tabuleiro)){
									return actual;

								}
								else{
									if (actual.depth<=i)
									{
									principal.addAll(0,expand(actual));
									}
				}
	}
}
	return pai;
}
public Tabuleiro greed()
		{
			ArrayList<Tabuleiro> auxili=new ArrayList<Tabuleiro>();
			principal.add(pai);									//adiciona pai a lista principal
			while(!principal.isEmpty()){						//enquanto a lista nao estiver vazia remove o primeiro elemento
				auxili.clear();
				Tabuleiro actual=principal.remove(0);		//e analisa se é final. se não for expande
				visitados++;
				if (actual.isFinalH(dest.tabuleiro)){
					return actual;

				}
				else
				{
					auxili=expandEG(actual);
					   for(int i=0; i<auxili.size(); i++)
					   		{
					   				auxili.get(i).geraHeuristica(dest.tabuleiro);
							}

					principal.addAll(0,auxili);

					Collections.sort(principal);
					
				}
			}
			return pai;
	

		}

public Tabuleiro estrela(){
			ArrayList<Tabuleiro> auxili=new ArrayList<Tabuleiro>();
			principal.add(pai);									//adiciona pai a lista principal
			while(!principal.isEmpty()){						//enquanto a lista nao estiver vazia remove o primeiro elemento
					auxili.clear();
					Tabuleiro actual=principal.remove(0);		//e analisa se é final. se não for expande
					visitados++;
					if (actual.isFinalH(dest.tabuleiro))
						{
							return actual;
						}
				else{
						auxili=expandE(actual);
				   		for(int i=0; i<auxili.size(); i++)
				   				{
					   				auxili.get(i).geraHeuristica(dest.tabuleiro);
					   				auxili.get(i).heurEstr();
					   			}
						principal.addAll(0,auxili);
						Collections.sort(principal);
					}
				}
	return pai;
}


public ArrayList<Tabuleiro> expand(Tabuleiro pai3){    //expande o pai para algoritmos sem heuristica
	int[][] tabpai=copiatabuleiro(pai3);
	ArrayList<Tabuleiro> test= new ArrayList<Tabuleiro>();
	if ((pai3.getvazioY()-1)>=0)
		{
			Tabuleiro filho3=new Tabuleiro(pai3,tabpai,3,pai3.getDepthT());
			gerados++;
			tabpai=copiatabuleiro(pai3);
			if (!repeat(filho3,pai3))
				{
				test.add(filho3);
				}
		}

	if ((pai3.getvazioX()+1)<=2)
		{
			Tabuleiro filho4=new Tabuleiro(pai3,tabpai,4,pai3.getDepthT());
			gerados++;
			tabpai=copiatabuleiro(pai3);
			if (!repeat(filho4,pai3))
				{
				test.add(filho4);
				}
		}

	
	if ((pai3.getvazioY()+1)<=2)
		{
			Tabuleiro filho2=new Tabuleiro(pai3,tabpai,2,pai3.getDepthT());
			gerados++;
			tabpai=copiatabuleiro(pai3);
			if (!repeat(filho2,pai3))
				{
				test.add(filho2);
				}
		}

	if ((pai3.getvazioX()-1)>=0)
		{
			Tabuleiro filho1=new Tabuleiro(pai3,tabpai,1,pai3.getDepthT());
			gerados++;
			tabpai=copiatabuleiro(pai3);
			if (!repeat(filho1,pai3))
				{
				test.add(filho1);
				}
		}



	return test;
	}




public ArrayList<Tabuleiro> expandE(Tabuleiro pai3){    //expande o pai para o algoritmo A* 
	int[][] tabpai=copiatabuleiro(pai3);
	ArrayList<Tabuleiro> test= new ArrayList<Tabuleiro>();
	
	if ((pai3.getvazioY()+1)<=2)
		{
			Tabuleiro filho2=new Tabuleiro(pai3,tabpai,2,pai3.getDepthT());
			gerados++;
			tabpai=copiatabuleiro(pai3);
			test.add(filho2);
		}

	if ((pai3.getvazioX()-1)>=0)
		{
			Tabuleiro filho1=new Tabuleiro(pai3,tabpai,1,pai3.getDepthT());
			gerados++;
			tabpai=copiatabuleiro(pai3);
			test.add(filho1);
		}

	if ((pai3.getvazioY()-1)>=0)
		{
			Tabuleiro filho3=new Tabuleiro(pai3,tabpai,3,pai3.getDepthT());
			gerados++;
			tabpai=copiatabuleiro(pai3);
			test.add(filho3);
		}

	if ((pai3.getvazioX()+1)<=2)
		{
			Tabuleiro filho4=new Tabuleiro(pai3,tabpai,4,pai3.getDepthT());
			gerados++;
			tabpai=copiatabuleiro(pai3);
			test.add(filho4);
		}

	return test;

}

public ArrayList<Tabuleiro> expandEG(Tabuleiro pai3){    //expande o pai para algoritmo Greedy(= a A* mas com teste de repeticao)
	
	int[][] tabpai=copiatabuleiro(pai3);
	ArrayList<Tabuleiro> test= new ArrayList<Tabuleiro>();
	
	if ((pai3.getvazioY()+1)<=2)
		{
			Tabuleiro filho2=new Tabuleiro(pai3,tabpai,2,pai3.getDepthT());
			gerados++;
			tabpai=copiatabuleiro(pai3);
			if (!repeat(filho2,pai3))
				{
					test.add(filho2);
				}
				
		}

	if ((pai3.getvazioX()-1)>=0)
		{
			Tabuleiro filho1=new Tabuleiro(pai3,tabpai,1,pai3.getDepthT());
			gerados++;
			tabpai=copiatabuleiro(pai3);
			if (!repeat(filho1,pai3))
				{
					test.add(filho1);
				}
		}

	if ((pai3.getvazioY()-1)>=0)
		{

			Tabuleiro filho3=new Tabuleiro(pai3,tabpai,3,pai3.getDepthT());
			gerados++;
			tabpai=copiatabuleiro(pai3);
			if (!repeat(filho3,pai3))
				{
					test.add(filho3);
				}
		}

	if ((pai3.getvazioX()+1)<=2)
		{

			Tabuleiro filho4=new Tabuleiro(pai3,tabpai,4,pai3.getDepthT());
			gerados++;
			tabpai=copiatabuleiro(pai3);
			if (!repeat(filho4,pai3))
				{
					test.add(filho4);
				}
		}

	return test;

	}



public int[][] copiatabuleiro(Tabuleiro acopiar){
	int[][] auxiliar=new int[3][3];
	int ax;
	for (int i=0;i<3;i++){
		for (int j=0;j<3;j++){
			ax=acopiar.tabuleiro[i][j];
			auxiliar[i][j]=ax;
		}
	}
return auxiliar;
}
public void imprimetabuleiro(int tabul[][]){
 	String impressao;
 	System.out.println("");
	for (int i=0;i<3;i++)
			{	
				System.out.println();
				impressao="titulo";
				for(int j=0;j<3;j++)
				{

					System.out.printf("%d",tabul[i][j]);
}
}

			
}


public boolean repeat(Tabuleiro objectivo,Tabuleiro filhoa){
	
			Tabuleiro teste=filhoa;
			int flag=0;
			while(teste!=null) 												// enquanto tabuleiro a testar não chegar ao pai da raiz(null)
				{
					flag=0;
					for (int i=0;i<3;i++)
						{
						
							for(int j=0;j<3;j++)
								{
									if (objectivo.tabuleiro[i][j]!=teste.tabuleiro[i][j])
											{
												flag=1;										//tabuleiros são diferentes
											}
								}
						
						}
					
					if (flag==1)
						{
							teste=teste.pai;
						}
				
					else 
						{
							return true;
						}
				}

			return false;
				}	
				
		}
	













 class Tabuleiro implements Comparable<Tabuleiro>{
	int [][] tabuleiro= new int [3][3];
	int vaziox,vazioy;
	Tabuleiro pai=null;
	int depth;
	boolean finala;
	int outplace=0;
	int movesneed=0;
	int heuristica;
		
		

		Tabuleiro(int [][] finalt){                               //construtor para pai
			tabuleiro=finalt;
			depth=0;
			finala=true;
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					if (finalt[i][j]==0){vazioy=i;vaziox=j;}
				}
			}
		}

		

		Tabuleiro(Tabuleiro tabp,int[][] aux,int child,int dept){    //construtor de filho. Recebe como input o tabuleiro pai, a direcçao a seguir e a profundidade actual
			depth=1+dept;
			pai=tabp;
			int paix=pai.getvazioX();
			int paiy=pai.getvazioY();
			int temp;
			vaziox=paix;
			vazioy=paiy;
			int tab[][]=aux;
			int par;
			switch (child){
					case 1: 
							tab[paix][paiy] =tab[paix-1][paiy];			//cima
							tab[paix-1][paiy]=0;
							vaziox--;
							break;
					
					case 2: 											//direita
							tab[paix][paiy] =tab[paix][paiy+1];
							tab[paix][paiy+1]=0;
							vazioy++;
							break;
					
					case 3: tab[paix][paiy] =tab[paix][paiy-1];			//esquerda
							tab[paix][paiy-1]=0;
							vazioy--;
							break;
					
					case 4: tab[paix][paiy] =tab[paix+1][paiy];			//baixo
							tab[paix+1][paiy]=0;
							vaziox++;
							break;
					default:System.out.println("ERRO NA DIRECAO A SEGUIR");
							break;
						}

			tabuleiro=tab;

		}




	public int getDepthT(){return depth;}


	public int[][] getTabuleiro(){
		return tabuleiro;
	}


	public int getvazioX(){
		return vaziox;
	}
	
	public int getvazioY(){
		return vazioy;
	}

		

	public Boolean isFinal(int[][] test)
		{
			Scanner in=new Scanner(System.in);
			for (int i=0;i<3;i++)
				{
					for(int j=0;j<3;j++)
						{
							if (tabuleiro[i][j]!=test[i][j]){return false;}
							
						}
				}
			System.out.println("Encontrada solucao com profundidade "+depth);
	    	System.out.println("Terminado.");
			return true;
		}


	public int compareTo(Tabuleiro compara) 			
		{																	//comparador para organizar precedencias de heuristica
			int comparaHeuristica = ((Tabuleiro) compara).heur(); 
			return    (this.heur()-comparaHeuristica);
		}


	public void geraHeuristica(int[][] test){
		outplace=0;
		movesneed=0;
		for (int i=0;i<3;i++)
			{
					for(int j=0;j<3;j++)
					{
						if (tabuleiro[i][j]!=test[i][j])
							{
								movesneed= movesneed + movesUntil(tabuleiro[i][j],test,i,j);
							}
					}
		}

		heuristica=outplace+movesneed;
	}


	public boolean isFinalH(int[][] test)
		{
			Scanner in=new Scanner(System.in);
			outplace=0;
			movesneed=0;
			for (int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					if (tabuleiro[i][j]!=test[i][j]){
						outplace++;
					
					}
				}
			}
			if (outplace==0){
			System.out.println("");
			System.out.println("Finished.");
			return true;}
			else{
					return false;
				}
		}


	public int heur()
		{
		return heuristica;
		}


	public void heurEstr()
		{
			heuristica=heuristica+depth;
		}


	public int movesUntil(int alvo,int [][] tested,int x, int y)
		{
			for (int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					if(tested[i][j]==alvo){
						return (Math.abs(i-x)+Math.abs(j-y));
					}
				}
			}
			return 0;
		}


	public int[][] tabulArray()
		{
			return tabuleiro;
		}


	public int isPar()
		{
			int par=0;
			for (int linha=0;linha<3;linha++)
				{
					for(int coluna=0;coluna<3;coluna++)
						{
							for(int colunac=0;colunac<3;colunac++)
								{
									for (int linhac=0;linhac<3;linhac++)
										{
											if (linhac<linha || (linhac==linha && colunac<=coluna) || tabuleiro[linhac][colunac]==0 ||  tabuleiro[linha][coluna]==0 )
												{}
											else
												{
													if (tabuleiro[linha][coluna]>tabuleiro[linhac][colunac] )
														{
															par++;
														}
													else
														{}

												}
										}
								}
						}
					
				}
			return par;
		}

}


class SI
	{
		public static void main(String args[])
			{
				int[][] tabuleiro1=new int[3][3]; 
				int[][] tabuleiro2=new int[3][3]; 
				Scanner in=new Scanner(System.in);
				int opc;
				int fim[][]={{1,2,3},{8,0,4},{7,6,5}};

				tabuleiro1[0][0]=6;
				tabuleiro1[0][1]=5;
				tabuleiro1[0][2]=4;
				tabuleiro1[1][0]=7;
				tabuleiro1[1][1]=8;
				tabuleiro1[1][2]=1;	
				tabuleiro1[2][0]=2;
				tabuleiro1[2][1]=3;
				tabuleiro1[2][2]=0;

				tabuleiro2[0][0]=6;
				tabuleiro2[0][1]=5;
				tabuleiro2[0][2]=7;
				tabuleiro2[1][0]=2;
				tabuleiro2[1][1]=0;
				tabuleiro2[1][2]=1;	
				tabuleiro2[2][0]=8;
				tabuleiro2[2][1]=4;
				tabuleiro2[2][2]=3;


				Tabuleiro fimt = new Tabuleiro(fim);
				Tabuleiro umt= new Tabuleiro(tabuleiro1);
				Tabuleiro doist= new Tabuleiro(tabuleiro2);
				
				System.out.println("\n*******************************************************\nTESTE DE PARIDADE\n-----------------------------");
				System.out.println("Soma final "+fimt.isPar() + ".Paridade = "+(fimt.isPar()%2) );
				System.out.println("----------------------------\nPARIDADE TABULEIRO UM.\nSoma um "+umt.isPar()+".Paridade = "+(umt.isPar()%2) );
				System.out.println("----------------------------\nPARIDADE TABULEIRO DOIS.\nSoma dois "+doist.isPar()+".Paridade = "+(doist.isPar()%2) );
				if (doist.isFinal(fim)){System.out.println("Tabuleiro FINAL");} else{System.out.println("Tabuleiro NAO FINAL");}
				if ((doist.isPar()%2)==(fimt.isPar()%2))
					{System.out.println("Tabuleiro RESOLVIVEL pelo teste de paridade");} 
				else{System.out.println("Tabuleiro NAO RESOLVIVEL pelo teste de paridade");}
				Arvore dois=new Arvore(doist,fimt);
				dois.algoritmos();
			}
			



		
	}