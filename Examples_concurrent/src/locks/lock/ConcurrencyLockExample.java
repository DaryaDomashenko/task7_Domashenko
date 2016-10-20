package locks.lock;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**класс для работы с Lock API
 *
 */

public class ConcurrencyLockExample implements Runnable{

    private Resource resource;
    private Lock lock;

    public ConcurrencyLockExample(Resource r){
        this.resource = r;
        this.lock = new ReentrantLock();
    }

    @Override
    public void run() {
        try {
            // лочим на 10 секунд
            if(lock.tryLock(10, TimeUnit.SECONDS)){
                resource.doSomething();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            //убираем лок
            lock.unlock();
        }
        // Для логгирования не требуется потокобезопасность
        resource.doLogging();
    }

}