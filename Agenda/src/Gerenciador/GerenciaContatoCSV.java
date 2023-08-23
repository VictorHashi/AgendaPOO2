package Gerenciador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Class.Contato;

public class GerenciaContatoCSV implements GerenciaContato{


    @Override
    public void exportar(List<Contato> contatos) {

        try (FileWriter fileWriter = new FileWriter("contatos.csv");){

            if (!contatos.isEmpty()) {
                Class<?> classe = contatos.get(0).getClass();
                Field[] campos = classe.getDeclaredFields();

                StringBuilder header = new StringBuilder();
                for (Field campo : campos) {
                    header.append(campo.getName()).append(",");
                }
                header.deleteCharAt(header.length() - 1);
                fileWriter.write(header.toString()+"\n");

                for (Contato contato : contatos) {
                    StringBuilder linha = new StringBuilder();
                    for (Field campo : campos) {
                        String nomeMetodo = "get" + campo.getName().substring(0, 1).toUpperCase() + campo.getName().substring(1);
                        Method metodo = getGetterMethod(classe, nomeMetodo);

                        if (metodo != null){
                            Object valor = metodo.invoke(contato);
                            linha.append(valor).append(",");
                        }

                    }
                    linha.deleteCharAt(linha.length() - 1);
                    fileWriter.write(linha.toString()+"\n");
                }
            }
            fileWriter.close();
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Erro ao escrever no arquivo", e);
        }
    }

        @Override
    public List<Contato> importar() {
            List<Contato> contatos = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader("contatos.csv"))) {
                String linha;
                boolean primeiraLinha = true;
                Map<Integer, String> index = new HashMap<>();

                while ((linha = br.readLine()) != null) {
                    if (primeiraLinha) {
                        primeiraLinha = false;
                        String[] cabecalho = linha.split(",");
                        for (int i = 0; i < cabecalho.length; i++) {
                            index.put(i, cabecalho[i]);
                        }
                        continue;
                    }

                    String[] dados = linha.split(",");

                    Contato contato = new Contato();

                    for (int i = 0; i < dados.length; i++) {
                        String atributo = index.get(i);
                        String metodoSetter = "set" + atributo.substring(0, 1).toUpperCase() + atributo.substring(1);
                        Method metodo = getSetterMethod(Contato.class, metodoSetter);//Retorna nulo na data
                        System.out.println(metodo.toString());

                        if (metodo != null) {
                            Field campo = Contato.class.getDeclaredField(atributo);
                            Class<?> tipo = campo.getType();
                            if (tipo.equals(Date.class)) {
                                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                                Date data = format.parse(dados[i]);
                                metodo.invoke(contato, data);
                            } else {
                                metodo.invoke(contato, dados[i]);
                            }
                        }
                    }
                    contatos.add(contato);
                }
            } catch (IOException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Erro ao escrever no arquivo", e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            return contatos;
    }

    private static Method getSetterMethod(Class<?> classe, String method) {
        try {
            Field field = classe.getDeclaredField(method.substring(3).toLowerCase());
            return classe.getMethod(method, field.getType());
        } catch (NoSuchMethodException e) {
            return null;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static Method getGetterMethod(Class<?> classe, String method) {
        try {
            return classe.getMethod(method);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}
