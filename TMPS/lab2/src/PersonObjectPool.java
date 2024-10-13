package src;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class PersonObjectPool<T extends PoolObject> {
    private final BlockingQueue<T> pool;
    private final int maxPoolSize;
    private final ObjectFactory<T> objectFactory;

    private static volatile PersonObjectPool<Person> instance;

    private PersonObjectPool(int maxPoolSize, ObjectFactory<T> objectFactory) {
        this.maxPoolSize = maxPoolSize;
        this.objectFactory = objectFactory;
        this.pool = new LinkedBlockingQueue<>(maxPoolSize);
    }

    public static PersonObjectPool<Person> getInstance(int maxPoolSize) {
        if (instance == null) {
            synchronized (PersonObjectPool.class) {
                if (instance == null) {
                    instance = new PersonObjectPool<>(maxPoolSize, Person::new);
                }
            }
        }
        return instance;
    }

    public T borrowObject() {
        T obj = pool.poll();
        if (obj == null) {
            obj = objectFactory.createObject();
        }
        return obj;
    }

    public void returnObject(T obj) {
        if (obj != null) {
            obj.reset();
            if (pool.size() < maxPoolSize) {
                pool.offer(obj);
            }
        }
    }
}