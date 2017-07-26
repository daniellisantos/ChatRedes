package threads;

import chatredes.Usuario;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ConexaoTCP implements Runnable {

    private static final int portaMulticast = 6868;
    private final String salas;
    private final Usuario usuario;
    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    private final Socket socketThread;
    private InetAddress ipGrupo;
    private MulticastSocket socketMulticast;
    public DataOutputStream paraCliente;
    
    
    public ConexaoTCP(Usuario usuarioThread, Socket socketCon, ArrayList<Usuario> usuariosConect){
        this.salas = "Redes de Computadores II;Programacao Movel;Banco de Dados;Administracao";
        this.usuario = usuarioThread;
        socketThread = socketCon;
        usuarios=usuariosConect; // recebo a lista de usuarios conectados do servidor
    }

    @Override
    public void run() {
        System.out.println("Aguardando dados do usuario...");
        try {
            paraCliente = new DataOutputStream(socketThread.getOutputStream());//inicializa uma varivel para enviar dados
            paraCliente.writeBytes(salas + '\n');//envia as salas disponiveis para o cliente
            
            //RECEBENDO O RESTANTE DOS DADOS DO USUARIO:
            BufferedReader doUsuario = new BufferedReader(new InputStreamReader(socketThread.getInputStream()));//inicializa uma varivel com os dados vindos do cliente
            String dadosBrutoUsuario = doUsuario.readLine();//pega os bytes enviados do cliente e salva como String
            String[] dadosUsuario = dadosBrutoUsuario.split(Pattern.quote(";"));
            usuario.setNomeUsuario(dadosUsuario[0]);//pega o nome de usuario
            usuario.setSala(dadosUsuario[1]);//pega a sala (o nome como String)            
            String[] temp = dadosUsuario[2].split(Pattern.quote(":"));//separar a lista de arquivos
            ArrayList<String> temp2 = new ArrayList<>();//converter de String[] para ArrayList
            for (int i = 0; i < temp.length; i++) {
                temp2.add(temp[i]);
            }
            usuario.setListaArq(temp2);//passa o ArrayList para o objeto usuario
            usuario.setCaminhoPasta(dadosUsuario[3]);//pega o caminho da pasta compartilhada
            
            System.out.println(usuario.getNomeUsuario() + " entrou no chat.");
            
        } catch (IOException ex) {
            Logger.getLogger(ConexaoTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            definirSala();
        } catch (IOException ex) {
            Logger.getLogger(ConexaoTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            atualizarListaNoServidor(); //chamando o atualizar lista
        } catch (IOException ex) {
            Logger.getLogger(ConexaoTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void definirSala() throws IOException {
        //DIRECIONANDO PARA A SALA DE CHAT CORRETA:
        String temp = "";
        DatagramPacket dtgrm;
        switch (usuario.getSala()) {
            case "Redes de Computadores II":
                ipGrupo = InetAddress.getByName("224.225.226.227");
                socketMulticast = new MulticastSocket(portaMulticast);
                socketMulticast.joinGroup(ipGrupo);
                usuario.setSocketMulticast(socketMulticast);
                temp = usuario.getNomeUsuario() + " entrou.|";
                dtgrm = new DatagramPacket(temp.getBytes(), temp.length(), ipGrupo, portaMulticast);
                socketMulticast.send(dtgrm);
                paraCliente.writeBytes(ipGrupo.toString() + '\n');
                break;
            case "Programacao Movel":
                ipGrupo = InetAddress.getByName("224.225.226.228");
                socketMulticast = new MulticastSocket(portaMulticast);
                socketMulticast.joinGroup(ipGrupo);
                usuario.setSocketMulticast(socketMulticast);
                temp = usuario.getNomeUsuario() + " entrou.|";
                dtgrm = new DatagramPacket(temp.getBytes(), temp.length(), ipGrupo, portaMulticast);
                socketMulticast.send(dtgrm);
                paraCliente.writeBytes(ipGrupo.toString() + '\n');
                break;
            case "Banco de Dados":
                ipGrupo = InetAddress.getByName("224.225.226.229");
                socketMulticast = new MulticastSocket(portaMulticast);
                socketMulticast.joinGroup(ipGrupo);
                usuario.setSocketMulticast(socketMulticast);
                temp = usuario.getNomeUsuario() + " entrou.|";
                dtgrm = new DatagramPacket(temp.getBytes(), temp.length(), ipGrupo, portaMulticast);
                socketMulticast.send(dtgrm);
                paraCliente.writeBytes(ipGrupo.toString() + '\n');
                break;
            case "Administracao":
                ipGrupo = InetAddress.getByName("224.225.226.230");
                socketMulticast = new MulticastSocket(portaMulticast);
                socketMulticast.joinGroup(ipGrupo);
                usuario.setSocketMulticast(socketMulticast);
                temp = usuario.getNomeUsuario() + " entrou.|";
                dtgrm = new DatagramPacket(temp.getBytes(), temp.length(), ipGrupo, portaMulticast);
                socketMulticast.send(dtgrm);
                paraCliente.writeBytes(ipGrupo.toString() + '\n');
                break;
        }
    }
    public void atualizarListaNoServidor() throws IOException
    {
        String redes ="" , programacaoMovel= "";
        
                 for( int i =0; i<= usuarios.size(); i++){ // pegando apenas os usuarios da sala redes e guardando na variavel redes
                   if(usuarios.get(i).getSala().equals("Redes de Computadores II"))
                   redes +=usuarios.get(i).getNomeUsuario()+";";
                   
                   if(usuarios.get(i).getSala().equals("Programacao Movel"))// pegando apenas os usuarios da sala programaçãoMovel e guardando na variavel programaçãoMovel
                     programacaoMovel += usuarios.get(i).getNomeUsuario()+";";
                 }
    
         if(usuario.getSala().equals("Redes de Computadores II")){ // enviando a string de nomes para o cliente de acordo com a sala que ele entrou
            paraCliente.writeBytes(redes);
            System.out.println(redes);// não imprime nada
        }
            
        if(usuario.getSala().equals("Programacao Movel")){
            paraCliente.writeBytes(programacaoMovel);
            System.out.println(programacaoMovel); //não imprime
        }
  
        
        
        
        
        
        /*if(usuario.getSala().equals("Redes de Computadores II")) {// de acordo com a sala que o usuario conectou ele pega o nome dos usuarios e envia
             for( int i =0; i<= usuarios.size(); i++) {
                if(usuarios.get(i).getSala().equals("Redes de Computadores II"));
                   redes +=usuarios.get(i).getNomeUsuario()+";";
                   //paraCliente.writeBytes(usuarios.get(i).getNomeUsuario()+";");
                    //System.out.println(usuarios.get(i).getSala()) ;
                    //System.out.println(usuarios.get(i).getNomeUsuario()+";");
                    System.out.println(redes);
                    }
              paraCliente.writeBytes(redes);
              System.out.println(redes);
         }
                // estou tentando enviar para o cliente. Mas como sei que eu vou receber essa linha. Já que utilizo 
                //a variavel paraClinte para enviar os todos dados nessa classe.
                
          if(usuario.getSala().equals("Programacao Movel")) {
            
                for( int i =0; i<= usuarios.size(); i++) {
                  
                if(usuarios.get(i).getSala().equals("Programacao Movel")){;
                //paraCliente.writeBytes(usuarios.get(i).getNomeUsuario());
                programacaoMovel += usuarios.get(i).getNomeUsuario()+";";
                //System.out.println(usuarios.get(i).getSala());
               // System.out.println(usuarios.get(i).getNomeUsuario()+";");}
                
                }
                }
                paraCliente.writeBytes(programacaoMovel);
                System.out.println(programacaoMovel);
          }*/
    }
}
