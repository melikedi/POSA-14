package edu.vuum.mocca;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @class SimpleSemaphore
 *
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject.  It must implement both "Fair" and
 *        "NonFair" semaphore semantics, just liked Java Semaphores. 
 */
public class SimpleSemaphore {
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
	 private ReentrantLock mRLock;
    /**
     * Define a ConditionObject to wait while the number of
     * permits is 0.
     */
    // TODO - you fill in here
	 final Condition mHavePermits;

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
	 volatile int mPermits;
    /**
     * Constructor initialize the data members.  
     */
    public SimpleSemaphore (int permits,
                            boolean fair)
    { 
    	
        // TODO - you fill in here
    	mRLock = new ReentrantLock(fair);
    	mHavePermits = mRLock.newCondition();
    	mPermits = permits;
    	
    }

    /**
     * Acquire one permit from the semaphore in a manner that can
     * be interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here
    	mRLock.lockInterruptibly();
    	try {
    		  while (mPermits == 0) 
    			mHavePermits.await();
    	  		mPermits = mPermits - 1; 
		} finally {
			mRLock.unlock();
		}
    	
    }

    /**
     * Acquire one permit from the semaphore in a manner that
     * cannot be interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here
    	mRLock.lock();
    	try {
  		  while (mPermits == 0) 
  			mHavePermits.awaitUninterruptibly();
  		  	mPermits = mPermits - 1; 
		} finally {
			mRLock.unlock();
		}
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here
    	mRLock.lock();
    	mPermits = mPermits + 1;
    	mHavePermits.signal();
     	mRLock.unlock();
    }
    
    /**
     * Return the number of permits available.
     */
    public int availablePermits(){
    	// TODO - you fill in here
    	return mPermits; // You will change this value. 
    }
}

