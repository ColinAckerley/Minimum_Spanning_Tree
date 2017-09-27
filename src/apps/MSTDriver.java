package apps;

import structures.Graph;

import java.io.IOException;
import java.util.ArrayList;


public class MSTDriver {

    public static void main(String[] args) {
        Graph graph = null;
        try {
            graph = new Graph("graph1.txt");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //
        PartialTreeList partialTreeList = MST.initialize(graph);
        //
        ArrayList<PartialTree.Arc> arcArrayList = MST.execute(partialTreeList);
        //

        for (int i = 0; i < arcArrayList.size(); i++) {
            PartialTree.Arc anArcArrayList = arcArrayList.get(i);
            System.out.println(anArcArrayList);
        }
    }
}