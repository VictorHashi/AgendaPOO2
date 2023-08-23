package Gerenciador;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.security.TypePermission;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import Class.Contato;

public class GerenciaContatoXML implements GerenciaContato{

    @Override
    public void exportar(List<Contato> contatos) {

        XStream xStream = new XStream();
        xStream.processAnnotations(Contato.class);

        try {

            FileWriter fileWriter = new FileWriter("contatos.xml");
            fileWriter.write(xStream.toXML(contatos));
            fileWriter.close();

            System.out.println("XML exportado");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contato> importar() {

        XStream xStream = new XStream();
        xStream.processAnnotations(Contato.class);
        TypePermission contatoPermission = AnyTypePermission.ANY;
        xStream.addPermission(contatoPermission);

        try {
            FileReader fileReader = new FileReader("contatos.xml");
            List<Contato> contatos = (List<Contato>) xStream.fromXML(fileReader);
            return contatos;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
