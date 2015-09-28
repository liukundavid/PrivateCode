/**
 * The driver of the simulation 
 */

public class Sim {
    /**
     * Create all components and start all of the threads
     */
    public static void main(String[] args) {
        
        Animation a = new Animation();
        Belt belt = new MainBelt(a, 5);
        
        Belt shortBelt = new ShortBelt(a, 2);
      
        Scanner scanner = new Scanner(a);
        
        Producer producer = new Producer(belt);
        Consumer consumer = new Consumer(belt, shortBelt);
        
        BeltMover mover = new BeltMover(belt);
        BeltMover shortMover = new BeltMover(shortBelt);
        
        ScannerHandler handler = new ScannerHandler(scanner);
        Sensor sensor = new Sensor();
        belt.setSensor(sensor);
        
        Robot robot = new MainRobot(belt, scanner);
        Robot subRobot = new SubRobot(shortBelt, scanner);
        
        
        consumer.start();
        producer.start();
        mover.start();
        shortMover.start();
        robot.start();
        subRobot.start();
        handler.start();

        while (consumer.isAlive() && 
               producer.isAlive() && 
               mover.isAlive() &&
               shortMover.isAlive() &&
               robot.isAlive() &&
               subRobot.isAlive() &&
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
        shortMover.interrupt();
        robot.interrupt();
        subRobot.interrupt();
        handler.interrupt();

        System.out.println("Sim terminating");
        System.out.println(BaggageHandlingThread.getTerminateException());
        System.exit(0);
    }
}
