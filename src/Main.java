
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {


    public static void HuffmanResult(Node root, String s, HashMap<String, String> result) {
        if (root.right == null && root.left == null && !root.value.isEmpty()) {
            //System.out.println(root.value+" -> "+s);
            result.put(root.value, s);

            return;
        }
        HuffmanResult(root.left, s + "0", result);
        HuffmanResult(root.right, s + "1", result);

    }


    public static void Compression() {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("input.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String s = input.nextLine();
        int n = s.length();
        HashMap<String, Integer> freq = new HashMap<>();
        HashMap<String, String> result = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<Node>(n, new HuffmanComparator());

        // frequency of characters
        for (int i = 0; i < n; i++) {
            if (freq.containsKey(String.valueOf(s.charAt(i)))) {
                Integer curr = freq.get(String.valueOf(s.charAt(i)));
                freq.put(String.valueOf(s.charAt(i)), curr + 1);
            } else {
                freq.put(String.valueOf(s.charAt(i)), 1);
            }
        }
        // put nodes in priority queue
        for (Map.Entry<String, Integer> x : freq.entrySet()) {
            String key = x.getKey();
            Integer value = x.getValue();
            //System.out.println(key+"   "+value);
            Node node = new Node(value, key, null, null);
            pq.add(node);

        }
        Node root = new Node(0, "", null, null);
        while (pq.size() > 1) {
            Node node1 = pq.peek();
            pq.poll();

            Node node2 = pq.peek();
            pq.poll();

            Node newNode = new Node(node1.freq + node2.freq, "", node1, node2);
            root = newNode;
            pq.add(newNode);
        }
        int sizeAfter = 0;
        int sizeBefore = n * 8;
        try {
            PrintWriter printWriter = new PrintWriter("output.txt");
            String decodedString = "";
            printWriter.println("Huffman Code:");
            if (freq.size() == 1) {
                printWriter.println(s.charAt(0) + " : 0");
                sizeAfter = n;
                for (int i = 0; i < n; i++) {
                    decodedString += "0";
                }

            } else {
                HuffmanResult(root, "", result);
                for (Map.Entry<String, String> x : result.entrySet()) {
                    printWriter.println(x.getKey() + " : " + x.getValue());
                    sizeAfter += x.getValue().length() * freq.get(x.getKey());
                }
                for (int i = 0; i < n; i++) {
                    String key = String.valueOf(s.charAt(i));
                    decodedString += result.get(key);
                }
            }

            printWriter.println();
            printWriter.println("Decoded Data: " + decodedString);
            printWriter.println("Data Size Before Compression: " + sizeBefore + " bits");
            printWriter.println("Data Size After Compression: " + sizeAfter + " bits");
            //printWriter.flush();
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void Decompression() {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("input.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String s = input.nextLine();
        int nn = s.length();
        HashMap<String, String> decode = new HashMap<>();
        int n = input.nextInt();
        for (int i = 0; i < n; i++) {
            String code = input.next();
            String key = input.next();
            decode.put(key, code);
        }
        String temp = "";
        String decoded = "";
        for (int i = 0; i < nn; i++) {
            temp += s.charAt(i);
            if (decode.containsKey(temp)) {
                decoded += decode.get(temp);
                temp = "";
            }
        }
        try {
            PrintWriter printWriter = new PrintWriter("output.txt");
            printWriter.println("Decoded Text: " + decoded);
            //printWriter.flush();
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {

        JFrame frame_ = new JFrame("Huffman App");
        JLabel label_1;
        label_1 = new JLabel("Choose what do you want");
        label_1.setBounds(200, 20, 1000, 90);
        
        
        JButton button1 = new JButton("Compression");
        button1.setBounds(300, 125, 150, 30);
        JButton button2 = new JButton("Decompression");
        button2.setBounds(100, 125, 150, 30);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Compression();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);


            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Decompression();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);


            }
        });
        frame_.add(label_1);
        frame_.add(button1);
        frame_.add(button2);
        frame_.setSize(600, 250);
        frame_.setLayout(null);
        frame_.setVisible(true);
        frame_.setResizable(false);



    }
}

/*
abaacaadaa



101011001101111
4
a   1
b   010
c   00
d   011








*/
