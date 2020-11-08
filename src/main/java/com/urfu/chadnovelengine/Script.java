package com.urfu.chadnovelengine;

public class Script {
    private final DialogNode[] nodes;

    public Script(DialogNode[] scriptNodes) {
        nodes = scriptNodes;
    }

    public DialogNode getNode(int index) {
        return nodes[index];
    }

}
