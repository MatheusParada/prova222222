package br.com.av2.estruturadedados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArvoreAVL {
    public class Node {
        private Node left, right, parent;
        private int height = 1;
        private int value;

        private Node(int val) {
            this.value = val;
        }
    }

    private Node root;

    private int height(Node N) {
        if (N == null)
            return 0;
        return N.height;
    }

    private Node insert(Node node, int value) {
        if (node == null) {
            return (new Node(value));
        }

        if (value < node.value)
            node.left = insert(node.left, value);
        else
            node.right = insert(node.right, value);

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBalance(node);

        if (balance > 1 && value < node.left.value)
            return rightRotate(node);

        if (balance < -1 && value > node.right.value)
            return leftRotate(node);

        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    private int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    public void preOrder(Node root) {
        if (root != null) {
            preOrder(root.left);
            System.out.printf("%d ", root.value);
            preOrder(root.right);
        }
    }

    private Node minValueNode(Node node) {
        Node current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }

    private Node deleteNode(Node root, int value) {
        if (root == null)
            return root;

        if (value < root.value)
            root.left = deleteNode(root.left, value);
        else if (value > root.value)
            root.right = deleteNode(root.right, value);
        else {
            if ((root.left == null) || (root.right == null)) {
                Node temp;
                if (root.left != null)
                    temp = root.left;
                else
                    temp = root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;

                temp = null;
            } else {
                Node temp = minValueNode(root.right);

                root.value = temp.value;

                root.right = deleteNode(root.right, temp.value);
            }
        }

        if (root == null)
            return root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    public void print(Node root) {
        if (root == null) {
            System.out.println("(XXXXXX)");
            return;
        }

        int height = root.height;
        int width = (int) Math.pow(2, height - 1);


    }

    public static void main(String[] args) {
        ArvoreAVL tree = new ArvoreAVL();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 5) {
            System.out.println("Menu:");
            System.out.println("<1> Inserir");
            System.out.println("<2> Excluir");
            System.out.println("<3> Pesquisar");
            System.out.println("<4> Caminhamento em pré-ordem");
            System.out.println("<5> Sair");
            System.out.print("Escolha uma opção: ");

            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    try {
                        FileReader fileReader = new FileReader("C:\\Users\\12114376\\IdeaProjects\\untitled\\src\\br\\com\\av2\\estruturadedados\\dados.txt");
                        BufferedReader bufferedReader = new BufferedReader(fileReader);

                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            int value = Integer.parseInt(line.trim());
                            tree.root = tree.insert(tree.root, value); // Supondo que 'root' seja o nó raiz da árvore
                        }

                        bufferedReader.close();
                        System.out.println("Dados do arquivo inseridos na árvore com sucesso.");

                    } catch (IOException | NumberFormatException e) {
                        System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
                    }
                    break;
                case 2:
                    // Opção de Excluir
                    System.out.print("Digite o valor a ser excluído da árvore: ");
                    int valueToDelete = scanner.nextInt();
                    tree.root = tree.deleteNode(tree.root, valueToDelete);
                    System.out.println(valueToDelete + " removido da árvore.");
                    break;
                case 3:
                    // Opção de Pesquisar
                    System.out.print("Digite o valor a ser pesquisado na árvore: ");
                    int valueToSearch = scanner.nextInt();
                    // Implemente a lógica para pesquisa aqui usando tree.search() ou outro método adequado
                    break;
                case 4:
                    // Opção de Caminhamento em pré-ordem
                    System.out.println("Caminhamento em pré-ordem:");
                    tree.preOrder(tree.root);
                    System.out.println();
                    break;
                case 5:
                    System.out.println("Encerrando o programa. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Escolha novamente.");
                    break;
            }
        }
        scanner.close();
    }
}
