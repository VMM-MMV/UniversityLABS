public class RunStructures {
    public static void main(String[] args) {
//        QueueList<Object> queueList = new QueueList<>();
//
//        queueList.enqueue("Jora");
//        queueList.enqueue("Vora");
//        System.out.println(queueList.toStrings());
        StackList<Object> stackList = new StackList<>(5);
        stackList.push("Jora");
        stackList.push("Vora");
        System.out.println(stackList.toStrings());
    }
}
