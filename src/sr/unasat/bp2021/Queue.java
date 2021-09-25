package sr.unasat.bp2021;

public class Queue {
        private final int SIZE = 20;
        private final int[] queArray;
        private int front;
        private int rear;

        public Queue(){
            queArray = new int[SIZE];
            front = 0;
            rear = -1;
        }
        public void insert(int data){                      // put item at rear of queue
            if(rear == SIZE-1)
                rear = -1;
            queArray[++rear] = data;
        }

        public int remove(){                               // take item from front of queue
            int temp = queArray[front++];
            if(front == SIZE)
                front = 0;
            return temp;
        }
        public boolean isEmpty() {                          // true if queue is empty
            return ( rear+1==front || (front+SIZE-1==rear) );
        }
    }

