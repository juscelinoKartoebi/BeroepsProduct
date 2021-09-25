package sr.unasat.bp2021;

    public class Stack {
        private final int SIZE = 20;
        private int[] stack;
        private int top;

        public Stack(){
            stack = new int[SIZE];          // make array
            top = -1;                       // wijst dat je nog geen items erin heb gezet
        }
        public void push(int data){            // put item on stack
         stack[++top] = data;
        }

        public int pop(){                  // take item off stack
            return stack[top--];
        }

        public int peek(){                // peek at top of stack
        return stack[top];
        }
        public boolean isEmpty(){         // true if nothing on stack-
         return (top == -1);
        }
    }







