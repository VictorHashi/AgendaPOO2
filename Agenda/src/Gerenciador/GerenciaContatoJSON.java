package Gerenciador;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import Class.Contato;

public class GerenciaContatoJSON implements GerenciaContato {


    @Override
    public void exportar(List<Contato> contatos) {
        Gson gson = new Gson();
        String json = gson.toJson(contatos);

        try {
            FileWriter fileWriter = new FileWriter("contatos.json");
            fileWriter.write(json);
            fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("JSON exportado");

    }

    @Override
    public List<Contato> importar() {

        Gson gson = new Gson();
        List<Contato> contatos = new ArrayList<Contato>();

        try(JsonReader reader = new JsonReader(new FileReader("contatos.json"))){

            reader.beginArray();
            while (reader.hasNext()){
                contatos.add(gson.fromJson(reader, Contato.class));
            }
            reader.endArray();

        }catch (Exception e){
            e.printStackTrace();
        }
        return contatos;
    }

}
