
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
        
        /**
         * Рекурсивный поиск элемента в дерерве.
         * @param item искомый элемент
         * @return true - при успешном нахождении, иначе - false
         */
        public boolean traverseSearch(T item) {
            int result = data.compareTo(item);
                    
            if (result == 0) {
                return true;
            } else if (result > 0) {
                return (left == null ? false : left.traverseSearch(item));
            } else {
                return (right == null ? false : right.traverseSearch(item));
            }
        }
        
       /**
        * Рекурсивное добавление item в дерево с корнем node.
        * @param item  добавляемый элемент
        * @param node  корень дерева
        * @return      true при успешном добавлении, иначе - false
        */
       private boolean traverseAdding(T item) {
            if (this.data.compareTo(item) == 0) {        // иначе, если мы нашли такой же элемент,
                                                         // то заканчиваем на этом
                return false;
            } else if (this.data.compareTo(item) > 0) {  // если корень больше, чем item, то
                                                         // рекурсивно работаем с левой частью
                if (left != null) {
                    return left.traverseAdding(item);
                } else {
                    left = new Node(item); 
                    return true;
                }
            } else {                                 // если корень меньше, чем item, то
                                                         // рекурсивно работаем с правой частью
                if (right != null) {
                    return right.traverseAdding(item);
                } else {
                    right = new Node(item); 
                    return true;
                }
            }
        }
       
        /**
         * Рекурсивный подсчёт количества узлов в двоичном дереве.
         * @param item  узел дерева
         * @return      количество дочерних узлов item + 1
         */
        private int traverseCounting() {
            return 1 + (left != null ? left.traverseCounting() : 0) + 
                    (right != null ? right.traverseCounting() : 0);
        }
        
        /**
         * Рекурсивное формирование dot-инструкций для двоичного дерева.
         * @param node узел двоичного дерева
         * @param result накопленные к текущему моменту dot-инструкции
         * @return dot-инструкции для дерева с корнем node
         */
        private String traverseGettingDot() {
            String result = "";
 
            result += String.format("\t\"%s\";", data.toString()) + System.lineSeparator();
            if (left != null) {
                result += String.format("\t\"%s\" -> \"%s\";", data.toString(), 
                        left.data.toString()) + System.lineSeparator();

                result += left.traverseGettingDot();
            }
            if (right != null) {
                result += String.format("\t\"%s\" -> \"%s\";", data.toString(), 
                        right.data.toString()) + System.lineSeparator();

                result += right.traverseGettingDot();
            }
 
            return result;
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
        if (root == null) {
            root = new Node(item);
            return true;
        } else {
            return root.traverseAdding(item);
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
        return root.traverseSearch(item);
    }
    
    /**
     * Возвращает мощность множества.
     * @return мощность множества
     */
    public int size() {
        return (root != null ? root.traverseCounting() : 0);
    }
    
    /**
     * Возвращает внутреннее двоичное дерево в виде dot-инструкций.
     * @return dot-инструкции
     */
    public String getDotScript() {
        String result = "digraph G {" + System.lineSeparator() + "\tnode[shape=circle];";
        result += System.lineSeparator();
        
        result += (root == null ? "" : root.traverseGettingDot());
        
        return result + "}";
    }
}
