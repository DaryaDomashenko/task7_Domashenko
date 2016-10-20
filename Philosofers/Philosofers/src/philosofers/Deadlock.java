package philosofers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
*Версия программы, при которой может произойти deadlock,
 *когда все философы будут ждать, пока сосед бросит палочку.
 *Если философы много времени проводят за размышлениями,
 *шанс возникновения Deadlock уменьшается.
 *Deadlock происходит, когда философы одновременно берут
 *правые палочки и соответственно не могут взять левые
*/

public class Deadlock {
    public static void main(String[] args) throws Exception{
        int ponder=5; //коэффициент, влияющий на время размышлений или еды
        int size=5; //количество философов и палочек
        ExecutorService exec = Executors.newCachedThreadPool(); //создаем пул потоков
        Chopstick[] sticks=new Chopstick[size];
        for(int i=0;i<size;i++){
            sticks[i]=new Chopstick();
        }
        for(int i=0;i<size;i++){
            //создаем и запускаем size потоков
            exec.execute(new Philosofer(sticks[i],sticks[(i+1)%size],i,ponder));
        }
        System.out.println("Press 'Enter' to quit");
        System.in.read();
        exec.shutdownNow();//пытаемся прервать потоки
    }
}
