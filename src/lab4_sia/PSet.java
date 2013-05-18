
package lab4_sia;

/**
 * Контейнер типа "множество".
 * 
 * @author      Владимир
 * @param <T>   тип элементов множества 
 */
public class PSet<T extends Comparable> {
    
    /**
     * Узел двоичного дерева.
     * @author Владимир
     * @param <T>   тип данных хранимых в узле
     */
    private class Node<T extends Comparable> {
        public T data;              /** Данные хранящиеся в узле.*/
        public Node left, right;    /** Дочерние элементы узла.*/
        private byte difference;    /** Разность между высотами левого и правого поддеревьев.*/
        
        /**
         * Создаёт узел с данными.
         */
        public Node(T data) {
            this.data = data;
            this.difference = 0;
            left = null;
            right = null;
        }
    }
    
    private Node root; /** Корень двочиного дерева поиска.*/
    
    /**
     * Создаёт пустое множество.
     */
    public PSet() {
        root = null;
    }
    
    /**
     * Добавляет новый эелемент в множество.
     * @param item  добавляемый элемент
     * @return      true при успешном добавлении, иначе - false
     */
    public boolean add(T item) {
        Node tmp = traverseAdding(item, root);
        root = (root == null ? tmp : root);
        return (tmp == null ? false : true);
    }
    
    /**
     * Рекурсивное добавление item в дерево с корнем node.
     * @param item  добавляемый элемент
     * @param node  корень дерева
     * @return      true при успешном добавлении, иначе - false
     */
    private Node traverseAdding(T item, Node node) {
        if (node == null) {         // если узел пустой, то создаём его
            return new Node(item);
        } else {
            if (node.data.compareTo(item) == 0) {     // иначе, если мы нашли такой же элемент,
                                                           // то заканчиваем на этом
                return null;
            } else if (node.data.compareTo(item) > 0) {  // если корень больше, чем item, то
                                                              // рекурсивно работаем с левой частью
                return node.left = traverseAdding(item, node.left);
                } else {                                      // если корень меньше, чем item, то
                                                              // рекурсивно работаем с правой частью
                return node.right = traverseAdding(item, node.right);
            }
        }
    }
    
    /**
     * Удаляет указанный элемент из множества.
     * @param item  удаляемый элемент
     * @return      true при успешном удалении, иначе - false
     */
    public boolean remove(T item) {
        return true;
    }
    
    /**
     * Проверяет наличие указанного элемента в множестве.
     * @param item  проверяемый элемент
     * @return      true при успешном нахождении, иначе - false
     */
    public boolean contains(T item) {
        return true;
    }
    
    /**
     * Возвращает мощность множества.
     * @return мощность множества
     */
    public int size() {
        return traverseCounting(root);
    }
    
    /**
     * Рекурсивный подсчёт количества узлов в двоичном дереве.
     * @param item  узел дерева
     * @return      количество дочерних узлов item + 1
     */
    private int traverseCounting(Node node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + traverseCounting(node.left) + traverseCounting(node.right);
        }
    }
    
    /**
     * Возвращает внутреннее двоичное дерево в виде dot-инструкций.
     * @return dot-инструкции
     */
    public String getDotScript() {
        String result = "digraph G {" + System.lineSeparator() + "\tnode[shape=circle];";
        result += System.lineSeparator();
        
        result += traverseGettingDot(root);
        
        return result + "}";
    }
    
    /**
     * Рекурсивное формирование dot-инструкций для двоичного дерева.
     * @param node узел двоичного дерева
     * @param result накопленные к текущему моменту dot-инструкции
     * @return dot-инструкции для дерева с корнем node
     */
    private String traverseGettingDot(Node node) {
        String result = "";
        
        if (node != null) {
            result += String.format("\t\"%s\";", node.data.toString()) + System.lineSeparator();
            if (node.left != null) {
                result += String.format("\t\"%s\" -> \"%s\";", node.data.toString(), 
                        node.left.data.toString()) + System.lineSeparator();
                
                result += traverseGettingDot(node.left);
            }
            if (node.right != null) {
                result += String.format("\t\"%s\" -> \"%s\";", node.data.toString(), 
                        node.right.data.toString()) + System.lineSeparator();
                
                result += traverseGettingDot(node.right);
            }
        }
        
        return result;
    }
}
