package com.urfu.chadnovelengine;

public class Script {
    private final DialogNode[] Nodes;

    public Script(DialogNode[] scriptNodes) {
        Nodes = scriptNodes;
    }

    public DialogNode getNode(int index) {
        return Nodes[index];
    }

}
