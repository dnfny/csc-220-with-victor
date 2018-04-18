package prog08;

import java.util.Comparator;
import java.util.Scanner;

import javax.xml.soap.Node;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Comparator;
import java.util.PriorityQueue;

public class NodeComparator implements Comparator<Node>{
	
	Node target;
	NodeComparator (Node tar)
	{
		target = tar;
	}
	public int value(Node node)
	{
		int distance = 0;
		
		
		
		return distance;
		
	}
	public int compare(Node node1, Node node2) {
		int compare = 0;
		
		compare = node1.getValue().compareTo(node2.getValue());
		
		return compare;
		
	}

}
