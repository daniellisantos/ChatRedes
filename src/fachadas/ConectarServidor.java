package fachadas;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

public class ConectarServidor implements Runnable {
    
    public InetAddress enderecoIP = InetAddress.getByName("localhost"); //do servidor
    private final int portaTCP = 6790; //do servidor
    private final int portaTCPreceber = 6791;
    //variaveis tcp
    private final Socket socketClienteTCP;
    private final DataOutputStream paraServidorTCP;
    private final BufferedReader doServidorTCP;
    private final String listaSalas;
    private String dados;
    private String ipGrupo;
    private String nomeSala;
    private String usuarioOnline;
    
    
    //SOBRE CONEXAO TCP:
    //conexao inicial TCP estabelecida na abertura da janelaLogin
    public ConectarServidor() throws IOException{
        socketClienteTCP = new Socket(enderecoIP, portaTCP); // cria um novo socket TCP
        doServidorTCP = new BufferedReader(new InputStreamReader(socketClienteTCP.getInputStream()));//cria uma variavel para receber os dados do servidor
        paraServidorTCP = new DataOutputStream(socketClienteTCP.getOutputStream());//cria uma variavel para enviar os dados para o servidor e ja conecta com ele
        listaSalas = doServidorTCP.readLine();//recebe as salas do servidor
        
    }
    public void setDadosLogin(String login, String sala, String arquivosPasta, String caminhoPasta) throws IOException{
        dados = login+";"+sala+";"+arquivosPasta+";"+caminhoPasta+"\n";
        nomeSala = sala;
    }
    public void enviarDadosLogin() throws IOException{
        paraServidorTCP.writeBytes(dados);
        ipGrupo = doServidorTCP.readLine();
    } 
    public String getListaSalas(){
        return listaSalas;
    }
    public String getIpGrupo(){
        return ipGrupo;
    }
    public String getNomeSala(){
        return nomeSala;
    }
    
    public ArrayList<String> atualizarLista() throws IOException
    {
        ArrayList<String> usuariosOnline =new ArrayList();;
        usuarioOnline= doServidorTCP.readLine(); // transformando em string
         String []listadeusuarios = usuarioOnline.split(Pattern.quote(";")); //separando por ;
           for (int i=0; i<listadeusuarios.length; i++)
        {
            usuariosOnline.add(listadeusuarios[i]); //adcionando no array
        }
        return usuariosOnline; // enviando para janelaChat
        
    }
 

    //PARA DESCONECTAR DE TUDO:
    public void desconetar() throws IOException{
//        socketMulticast.leaveGroup(ipMulticast);//sai primeiro do grupo multicast
        socketClienteTCP.close(); //encerra conexao TCP com servidor 
    }

    @Override
    public void run() {
        
    }
}
