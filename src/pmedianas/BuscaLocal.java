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
        
        //Melhorando o trajeto do caixeiro viajante
        AllPairs();
        
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
        
        double total_distancia, aux_total_distancia;
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
        total_distancia=distancia_total();
         aux_total_distancia=total_distancia;
        
        //Iniciando a busca all pairs
        while (melhorou){
            melhorou=false;
            for (int i=0;i<this.qtde-1;i++){
                for (int j=i+1;j<this.qtde;j++){
                    troca=aux_rota[i];
                    aux_rota[i]=aux_rota[j];
                    aux_rota[j]=troca;

                    //Verificando o custo da nova rota
                    aux_distancia_total=custo_rota(aux_rota);

                    //Melhorou?
                    if (aux_distancia_total<distancia_total){
                        melhorou=true;
                        distancia_total=aux_distancia_total;

                        //Atualizando a rota que foi melhorada
                        System.arraycopy(aux_rota, 0, this.rota, 0, this.qtde);
                    }
                    //Voltando com a configuração original para testar as outras
                    //Combinações
                    troca=aux_rota[j];
                    aux_rota[j]=aux_rota[i];
                    aux_rota[i]=troca;
                }//Neste local encerram-se os ciclos de troca

                //Atualizando com a melhor rota
                System.arraycopy(this.rota, 0, aux_rota, 0, this.qtde);
            }
        }
    }
    
    
    private double custo_solucao(int[] rota_testada){
        double custo=0.0;
        int i,de, para;
        for (i=0;i<this.qtde-1;i++){
            de=rota_testada[i];
            para=rota_testada[i+1];
            
            custo+=this.distancias[de][para];
        }
        //Voltando para a cidade de origem
        custo+=this.distancias[i][0];
        return custo;
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
