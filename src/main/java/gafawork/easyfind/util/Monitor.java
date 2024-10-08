/**
 *
 */

package gafawork.easyfind.util;

import gafawork.easyfind.parallel.ConsumerGitlab;
import gafawork.easyfind.parallel.ProductorGitlab;


import java.io.IOException;
import java.util.*;

@SuppressWarnings("java:S6548")
public class Monitor {
    private static Monitor instance;

    private static Object mutex = new Object();

    private static List<ErroVO> listErros = new ArrayList<>();

    private static List<SearchDetail> searchDetails = new ArrayList<>();

    private static List<ProductorGitlab> listProductor = new ArrayList<>();

    private static List<ConsumerGitlab> listConsumer = new ArrayList<>();

    private static int totalProject = 0;

    private static int totalParallel = 0;

    private Monitor() {

    }

    public static Monitor getInstance() {
        Monitor result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new Monitor();
            }
        }
        return result;
    }
@SuppressWarnings("java:S1192")
    public static void report() throws IOException {
        int totalSearch = 0;

        Iterator<SearchDetail> iteratorSearchDetail = searchDetails.iterator();

        WriteFile.getInstance().writeTxt("Easyfind by Tirso Andrade");
        WriteFile.getInstance().writeTxt("=============================================");
        if(Parameters.getFilter() != null)
            WriteFile.getInstance().writeTxt("Filter: " + Parameters.getFilter());

        WriteFile.getInstance().writeTxt("Search: " + Arrays.toString(Parameters.getTexts()));

        while (iteratorSearchDetail.hasNext()) {
            SearchDetail searchDetail = iteratorSearchDetail.next();
            if(searchDetail.getReferences() > 0) {
                totalSearch++;
                WriteFile.getInstance().writeTxt("=============================================");
                WriteFile.getInstance().writeTxt("Id Project:" + searchDetail.getId());
                WriteFile.getInstance().writeTxt("Name Project:" + searchDetail.getNome());
                WriteFile.getInstance().writeTxt("Name Branch:" + searchDetail.getBranch());
                WriteFile.getInstance().writeTxt("Path:" + searchDetail.getPath());
                WriteFile.getInstance().writeTxt("url:" + searchDetail.getUrl());
                WriteFile.getInstance().writeTxt("references:" + searchDetail.getReferences());


                Iterator<String> iteratorLines = searchDetail.getLines().iterator();
                while (iteratorLines.hasNext()) {
                    String linha = iteratorLines.next();
                    WriteFile.getInstance().writeTxt("     " + linha);
                }

            }
        }
        WriteFile.getInstance().writeTxt("=============================================");
        WriteFile.getInstance().writeTxt("Projects Total:" + searchDetails.size());
        WriteFile.getInstance().writeTxt("Projects Found:" + totalSearch);
        WriteFile.getInstance().writeTxt("=============================================");
    }

    public static void addProductor(ProductorGitlab productorGitlab) {
        listProductor.add(productorGitlab);
    }

    public static void addConsumer(ConsumerGitlab consumerGitlab) {
        listConsumer.add(consumerGitlab);
    }

    public static int getTotalProject() {
        return totalProject;
    }

    public static void setTotalProject(int totalProject) {
        Monitor.totalProject = totalProject;
    }

    public static int getTotalParallel() {
        return totalParallel;
    }

    public static void setTotalParallel(int totalParallel) {
        Monitor.totalParallel = totalParallel;
    }

    public static List<ProductorGitlab> getListaProductor() {
        return listProductor;
    }

    public static List<ConsumerGitlab> getListaConsumer() {
        return listConsumer;
    }

    public static void abort() {
        // produtores
        ProductorGitlab.abort();

        // consumidoras
        Iterator<ConsumerGitlab> iteratorConsumer = listConsumer.iterator();
        while (iteratorConsumer.hasNext()) {
            ConsumerGitlab consumerGitlab = iteratorConsumer.next();
            consumerGitlab.abort();
        }

    }

    public static List<ErroVO> getListaErros() {
        return Monitor.listErros;
    }

    public static List<SearchDetail> getSearchDetails() {
        return Monitor.searchDetails;
    }

    public static void addErro(ErroVO erroVO) {
        Monitor.getListaErros().add(erroVO);
    }

    public static void addSearchDetail(SearchDetail searchDetail) {
        Monitor.getSearchDetails().add(searchDetail);
    }

}
