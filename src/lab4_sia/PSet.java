
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
     */
    private class Node {
        public T data;              /** Данные хранящиеся в узле.*/
        public Node left, right;    /** Дочерние элементы узла.*/
        public Node parent;         /** Родительский узел.*/
        private int difference;     /** Разность между высотами левого и правого поддеревьев.*/
        
        /**
         * Создаёт узел с данными.
         */
        public Node(T data) {
            this.data = data;
            this.difference = 0;
            left = null;
            right = null;
            parent = null;
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
       public boolean traverseAdding(T item, Object[] refRoot, int[] diff) {
            
            boolean result = true;
            if (this.data.compareTo(item) == 0) {       // иначе, если мы нашли такой же элемент,
                                                        // то заканчиваем на этом
                result = false;
            } else if (this.data.compareTo(item) > 0) { // если корень больше, чем item, то
                                                        // рекурсивно работаем с левой частью
                if (left != null) {
                    result = left.traverseAdding(item, refRoot, diff);
                    this.difference -= Math.abs(diff[0]);
                } else {
                    left = new Node(item); 
                    left.parent = this;
                    this.difference--;                  // баланс смещается влево
                    diff[0] = -1;                       
                }
                    
                if (this.difference == -2) {            // проверка на имбаланс влево
                    if (left.difference == -1) {        // случай малого правого вращения
                        if (this == refRoot[0]) refRoot[0] = left;
                        else { 
                            if (this.parent.left == this) this.parent.left = left;
                            else this.parent.right = left;
                        }
                        Node tmp = left.right;
                        if (tmp != null ) tmp.parent = this;
                        left.parent = this.parent;
                        this.parent = left;
                        this.left.difference = (left.right != null ? 0 : 1);
                        left.right = this;
                        this.left = tmp;
                        this.difference = 0;
                    } else {                            // случай большого правого вращения
                        if (this == refRoot[0]) refRoot[0] = left.right;            // если надо, то меняем корень всего дерева
                        else { // иначе меняем у родителя текущей вершины нужную ветвь
                            if (this.parent.left == this) this.parent.left = left.right;
                            else this.parent.right = left.right;
                        }                         
                        Node tmp = left.right;                                      // = C, A = this, B = left
                        tmp.parent = this.parent;                                   // устанавливаем нового родителя для С
                        left.difference = (left.right.difference == 1 ? -1 : 0);    // меняем разность у В
                        tmp.difference = 0;                                         // разность у С теперь точно будет 0
                        left.right = tmp.left;                                      // левый узел B теперь правый узел С
                        tmp.left = this.left;                                       // а левый узел С теперь В
                        this.left = tmp.right;                                      // теперь у А слева то, что у С справа
                        tmp.right.parent = this;                                    // меняем родителя у штуки справа от С на А
                        tmp.right = this;                                           // теперь справа от С стоит А
                        this.parent = tmp;                                          // теперь родитель А это С
                        tmp.left.parent = tmp;                                      // теперь родитель B это С
                        this.difference = (tmp.difference != -1 ? 0 : 1);           // вычисляем новую разность у А
                    }
                    diff[0] = 0;
                }
            } else {                                    // если корень меньше, чем item, то
                                                        // рекурсивно работаем с правой частью
                if (right != null) {
                    result = right.traverseAdding(item, refRoot, diff);
                    this.difference += Math.abs(diff[0]);
                } else {
                    right = new Node(item); 
                    right.parent = this;
                    this.difference++;                  // баланс смещается вправо
                    diff[0] = 1;
                }
                
                if (this.difference == 2) {             // проверка на имбаланс вправо
                    if (right.difference == 1) {        // случай малого левого вращения
                        if (this == refRoot[0]) refRoot[0] = right;
                        else { 
                            if (this.parent.left == this) this.parent.left = right;
                            else this.parent.right = right;
                        }
                        Node tmp = right.left;
                        if (tmp != null ) tmp.parent = this;
                        right.parent = this.parent;
                        this.parent = right;
                        this.right.difference = (right.left != null ? 0 : -1);
                        right.left = this;
                        this.right = tmp;
                        this.difference = 0;
                    } else {                            // случай большого левого вращения
                        if (this == refRoot[0]) refRoot[0] = right.left;            // если надо, то меняем корень всего дерева
                        else { // иначе меняем у родителя текущей вершины нужную ветвь
                            if (this.parent.left == this) this.parent.left = right.left;
                            else this.parent.right = right.left;
                        } 
                        Node tmp = right.left;                                      // = C, A = this, B = left
                        tmp.parent = this.parent;                                   // устанавливаем нового родителя для С
                        right.difference = (right.left.difference == 1 ? 0 : 1);    // меняем разность у В
                        tmp.difference = 0;                                         // разность у С теперь точно будет 0
                        right.left = tmp.right;                                     // левый узел B теперь правый узел С
                        tmp.right = this.right;                                     // а левый узел С теперь В
                        this.right = tmp.left;                                      // теперь у А слева то, что у С справа
                        tmp.left.parent = this;                                     // меняем родителя у штуки справа от С на А
                        tmp.left = this;                                            // теперь справа от С стоит А
                        this.parent = tmp;                                          // теперь родитель А это С
                        tmp.right.parent = tmp;                                     // теперь родитель B это С
                        this.difference = (tmp.difference == -1 ? 0 : -1);          // вычисляем новую разность у А
                    }
                    diff[0] = 0;
                }
            }
            
            return result;
        }
       
        /**
         * Рекурсивный подсчёт количества узлов в двоичном дереве.
         * @param item  узел дерева
         * @return      количество дочерних узлов item + 1
         */
        public int traverseCounting() {
            return 1 + (left != null ? left.traverseCounting() : 0) + 
                    (right != null ? right.traverseCounting() : 0);
        }
        
        /**
         * Рекурсивное формирование dot-инструкций для двоичного дерева.
         * @param node узел двоичного дерева
         * @param result накопленные к текущему моменту dot-инструкции
         * @return dot-инструкции для дерева с корнем node
         */
        public String traverseGettingDot() {
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
        
        /**
         * Рекурсивное удаление элемента из множества.
         * @param item удаляемый элемент
         * @return true - при успешном удалении, иначе - false
         */
        public boolean traverseRemoving(T item, Node root) {
            if (this.data.compareTo(item) == 0) {
                if (this.left == null && this.right == null) {
                    if (this.parent.left == this) {
                        this.parent.left = null;
                    } else {
                        this.parent.right = null;
                    }
                } else if (this.left != null && this.right == null) {
                    if (this.parent.left == this) {
                        this.parent.left = this.left;
                    } else {
                        this.parent.right = this.left;
                    }
                    
                    this.left = null;
                } else if (this.left == null && this.right != null) {
                    if (this.parent.left == this) {
                        this.parent.left = this.right;
                    } else {
                        this.parent.right = this.right;
                    }
                    
                    this.right = null;
                } else {
                    Node rightmostParent = this.traverseRightmost();
                    T tmp = rightmostParent.right.data;
                    root.traverseRemoving(rightmostParent.right.data, root);
                    this.data = tmp;
                }
                
                return true;
            } else if (this.data.compareTo(item) > 0) {
                return (this.left == null ? false : this.left.traverseRemoving(item, root));
            } else {
                return (this.right == null ? false : this.right.traverseRemoving(item, root));
            }
        }
        
        /**
         * Ищет родителя самого правого узла у дерева с корнем this.
         * @return узел-родитель самого правого узла
         */
        private Node traverseRightmost() {
            return (this.right == null ? this : this.right.traverseRightmost());
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
            Object[] refRoot = new Object[] {root};
            int[] diff = {0};
            boolean result = root.traverseAdding(item, refRoot, diff);
            root = (Node)refRoot[0];
            return result;
        }
    }
    
    /**
     * Удаляет указанный элемент из множества.
     * @param item  удаляемый элемент
     * @return      true при успешном удалении, иначе - false
     */
    public boolean remove(T item) {
        if (root == null) {
            return false;
        } else if (root.data.compareTo(item) == 0) {
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.left != null && root.right == null) {
                Node tmp = root.left;
                root.left = null;
                root = tmp;
            } else if (root.left == null && root.right != null) {
                Node tmp = root.right;
                root.right = null;
                root = tmp;
            } else {
                Node rightmostParent = root.traverseRightmost();
                T tmp = rightmostParent.right.data;
                root.traverseRemoving(rightmostParent.right.data, root);
                root.data = tmp;
            }

            return true;
        } else {
            return root.traverseRemoving(item, root);
        }
    }
    
    /**
     * Проверяет наличие указанного элемента в множестве.
     * @param item  проверяемый элемент
     * @return      true при успешном нахождении, иначе - false
     */
    public boolean contains(T item) {
        return (root != null ? root.traverseSearch(item) : false);
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