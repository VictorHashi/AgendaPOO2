import Gerenciador.GerenciaContato;
import Gerenciador.GerenciaContatoCSV;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Class.Contato;
import Gerenciador.GerenciaContatoSQL;

public class Main {

    public static void main(String[] args) {

        List<Contato> contatos = new ArrayList<Contato>();

        contatos.add(new Contato
                ("Alberto", new Date(2000,1,1), "4799998888","alberto@gmail.com"));
        contatos.add(new Contato
                ("Bartolomeu", new Date(2000,3,5), "4798887777","barto@gmail.com"));
        contatos.add(new Contato
                ("Carlinhos", new Date(2000,5,20),"4797776666","carlo@gmail.com"));

        GerenciaContato gerenciador = new GerenciaContatoSQL();
        gerenciador.exportar(contatos);

        contatos.clear();
        contatos = gerenciador.importar();

        for (Contato c : contatos)
            System.out.println(c.toString());

    }

}
