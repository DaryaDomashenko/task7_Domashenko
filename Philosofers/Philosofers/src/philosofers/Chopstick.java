package philosofers;

/**
**Палочка для еды
*/

public class Chopstick {
    private boolean taken = false; //переменная определяет взята ли палочка кем-то из философов
    public synchronized void take() throws InterruptedException{
        //если палочка свободна, то философ берет ее, если нет, то ждет, пока она освободится
        while(taken){
            wait();
        }
        taken = true;//философ берет палочку
    }
    public synchronized void drop(){
        //философ бросает палочку и она становится свободной
        taken=false;
        notifyAll();
    }
}
