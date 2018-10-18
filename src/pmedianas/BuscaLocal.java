/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmedianas;

import java.util.Random;

/**
 *
 * @author tarcisio
 */

public class BuscaLocal {
    private int[] solucao;
    private double [][]distancias;
    private int[] atendido_por;//Este vetor informa qual é a "UPA" que está mais próxima do bairro
    private int pontos_demanda;
    private int facilidades;
    
    public BuscaLocal(double[][] distancias, int pontos_demanda, 
        int facilidades) {
        this.distancias = distancias;
        this.pontos_demanda = pontos_demanda;
        this.facilidades=facilidades;
        
        this.solucao= new int[facilidades];
        this.atendido_por= new int[pontos_demanda];
        
        gerar_solucao_inicial_aleatoria();
        
        
        //Mostrando a solução inicial
        for (int i=0;i<this.facilidades;i++){
            System.out.print(this.solucao[i]+"-");
        }
        
        //Melhorando a solução
        AllPairs();
        
        //Mostrando a solução Melhorada
        System.out.println();
        for (int i=0;i<this.facilidades;i++){
            System.out.print(this.solucao[i]+"-");
        }
       
        
    }
    
    private void atendido_por_quem(int[] vet){
        //Vet corresponde a um vetor que contém as facilidades
        //que estão sendo usadas em um dado momento
        //Percorrendo todos os pontos de demanda
        for (int i=0;i<this.pontos_demanda;i++){
            this.atendido_por[i]=ponto_demanda_atendido_por(i, vet);
        }
    }
    
    private double distancia_total(){
        double aux=0;
        int quem_atende;
        for(int i=0; i<this.pontos_demanda; i++){
            quem_atende=this.atendido_por[i];
            aux+=this.distancias[i][quem_atende];
            
        }
        return aux;
    }
   
    private int ponto_demanda_atendido_por(int ponto_demanda, int[] vet){
        double mais_perto=this.distancias[vet[0]][ponto_demanda];
        int facilidade_mais_perto=vet[0];
        
        //Percorrtendo todas as facilidades
        for (int i=1;i<this.facilidades;i++){
            if (this.distancias[vet[i]][ponto_demanda]<mais_perto){
                mais_perto=this.distancias[vet[i]][ponto_demanda];
                facilidade_mais_perto=vet[i];
            }
        }
        return facilidade_mais_perto;
    }    
    
    private void AllPairs(){
        int fu[] = new int[this.facilidades], troca; //Facilidades Usadas
        int fnu[]= new int[this.pontos_demanda-this.facilidades]; //Facilidades não usadas
        int aux_fnu[]= new int[this.pontos_demanda-this.facilidades];
        double menor_distancia, distancia_testada;
        boolean melhorou=true;
        
        //Primeiro copia-se solucao inicial gerada aleatoriamente
        System.arraycopy(this.solucao,0,fu,0,this.facilidades);
        
        //Preenchendo o vetor facilidades nao usadas
        int k=0,i=0;
        for (k=0;k<this.pontos_demanda;k++){
            if (!existe(k)){
                fnu[i]=k;
                i++;
            }
        }
        
        //Calculando o custo da soluçao total
        menor_distancia=distancia_total();
         distancia_testada=menor_distancia;
        
        //Iniciando a busca all pairs
        while (melhorou){
            melhorou=false;
            for (i=0;i<this.facilidades;i++){
                for (int j=i+1;j<this.pontos_demanda-this.facilidades;j++){
                    troca=fu[i];
                    fu[i]=fnu[j];
                    fnu[j]=troca;

                    //Apos a troca verifica quem atende os pontos de demanda
                    atendido_por_quem(fu);
                    
                    //Calcula agora o custo desta nova configuração
                    distancia_testada=distancia_total();

                    //Melhorou?
                    if (distancia_testada<menor_distancia){
                       //Este custo ficouo melhor. Copitando esta configuracao
                        melhorou=true;
                        menor_distancia=distancia_testada;

                        //Gravar qual e a melhor solução
                        System.arraycopy(fu, 0, this.solucao, 0, this.facilidades);
                        System.arraycopy(fnu, 0, aux_fnu, 0, this.pontos_demanda-this.facilidades);
                    }
                    
                    //Desfazendo a troca feita para testar outra facilidade
                    troca=fnu[j];
                    fnu[j]=fu[i];
                    fu[i]=troca;
                                        
                }//Neste local encerram-se os ciclos de troca

                //Atualizando com a melhor rota
                //Quando termina de percorrer todas as facilidades é hora de guardar a melhor de todas
                System.arraycopy(this.solucao, 0, fu, 0, this.facilidades);
                System.arraycopy(aux_fnu, 0, fnu, 0, this.pontos_demanda-this.facilidades);
            }
        }
    }
    
    
      private void gerar_solucao_inicial_aleatoria(){
        //Esvaziando o vetor
        for (int i=0;i<this.facilidades;i++){
            this.solucao[i]=-1;
        }
        
        Random r = new Random();
        int aux=0, i=0;
        while (i<this.facilidades){
            aux=r.nextInt(this.pontos_demanda);
            if (!existe(aux)){
                this.solucao[i]=aux;
                i++;
            }
        }
    }
    private boolean existe(int ponto){
        boolean retorno=false;
        for (int i=0;i<this.facilidades;i++){
            if (ponto==this.solucao[i]){
                retorno=true;
                break;
            }
        }
        return retorno;
    }
    
}
