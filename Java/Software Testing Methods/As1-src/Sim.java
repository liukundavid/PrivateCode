/**
 * The driver of the simulation 
 */

public class Sim {
    /**
     * Create all components and start all of the threads
     */
    public static void main(String[] args) {
        
        Animation a = new Animation();
        Belt belt = new Belt(a);
        Scanner scanner = new Scanner(a);
        Producer producer = new Producer(belt);
        Consumer consumer = new Consumer(belt);
        BeltMover mover = new BeltMover(belt);
        ScannerHandler handler = new ScannerHandler(scanner);
        Sensor sensor = new Sensor();
        belt.setSensor(sensor);
        
        Robot robot = new Robot(belt, scanner);

        consumer.start();
        producer.start();
        mover.start();
        robot.start();
        handler.start();

        while (consumer.isAlive() && 
               producer.isAlive() && 
               mover.isAlive() &&
               robot.isAlive() &&
               handler.isAlive())
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                BaggageHandlingThread.terminate(e);
            }

        // interrupt other threads
        consumer.interrupt();
        producer.interrupt();
        mover.interrupt();
        robot.interrupt();
        handler.interrupt();

        System.out.println("Sim terminating");
        System.out.println(BaggageHandlingThread.getTerminateException());
        System.exit(0);
    }
}
