package philosofers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *Исправленная версия программы без возможности возникновения deadlock.
 *Если последний философ будет брать сперва левую палочку, а затем правую,
 *то он не помешает первому философу взять свою левую палочку,
 *и, следовательно, deadlock не возникнет. 
 */
public class WithoutDeadlock {
    public static void main(String[] args) throws Exception{
        int ponder=5;//коэффициент, влияющий на время размышлений или еды
        int size=5;//количество философов и палочек
        ExecutorService exec=Executors.newCachedThreadPool();//создаем пул потоков
        Chopstick[] sticks=new Chopstick[size];
        for(int i=0;i<size;i++){
            sticks[i]=new Chopstick();
        }
        for(int i=0;i<size;i++){
            if(i<(size-1)){
                //если это не последний философ, передаем палочки в обычном порядке
                exec.execute(new Philosofer(sticks[i],sticks[i+1],i,ponder));
            }
            else{
                //если это последний философ, передаем палочки в обратном порядке
                //следовательно, сначала он будет брать левую палочку, а потом правую
                exec.execute(new Philosofer(sticks[0],sticks[i],i,ponder));
            }
        }
        System.out.println("Press 'Enter' to quit");
        System.in.read();
        exec.shutdownNow();//пытаемся прервать потоки
    }
}
