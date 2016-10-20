package philosofers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *Философ. Имеется определенное количество философов.
 *Каждый философ проводит часть времени за едой и часть времени размышляя.
 *Для того, чтобы поесть, философу требуются палочки слева и справа.
 *Если палочка у другого философа, то философ ожидает.
 */

public class Philosofer implements Runnable{
    private Chopstick left; //левая палочка
    private Chopstick right; //правая палочка
    private final int id; //идентификатор философа
    //коэффициент, который влияет на время, проводимое философами за размышлениями.
    //если он маленький, то deadlock возникает быстро
    private final int pounderFactor;
    private Random rand=new Random(47);
    
    private void pause() throws InterruptedException{
        //вызывает sleep на случайный период
        //таким образом философ размышляет и ест некоторое случайное время
        if(pounderFactor==0) return;
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(pounderFactor*250));
    }

    public Philosofer(Chopstick left, Chopstick right, int ident, int pounder) {
        //в конструкторе передаются ссылки на левую и правую палочки для философа
        // а также id философа и коэффициент, влияющий на время
        this.left = left;
        this.right = right;
        id = ident;
        pounderFactor = pounder;
    }
    
    public void run() {
        try{
            while(!Thread.interrupted()){
                //будет выполняться, пока Thread.interrupted() не вернет true
                System.out.println(this+" "+"thinking");
                pause();//философ размышляет случайное время
                System.out.println(this+" "+"grabbing right");
                right.take();//берет правую палочку, если возможно. Если нет-ждет.
                System.out.println(this+" "+"grabbing left");
                left.take();//берет левую палочку, если возможно. Если нет-ждет.
                System.out.println(this+" "+"eating");
                pause();//философ ест случайное время
                right.drop();//философ бросает правую палочку
                left.drop();//философ бросает левую палочку
            }
        }
        catch(InterruptedException e){
            System.out.println(this+" "+"exiting via interrupt");
        }
    }
    
    public String toString(){ 
        return "Philosofer "+id;
    }
    
}
