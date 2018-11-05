package com.alibaba.ttl.threadpool

import com.alibaba.noTtlAgentRun
import com.alibaba.ttl.threadpool.TtlExecutors.*
import org.junit.Assert.*
import org.junit.Test
import java.util.concurrent.Executor
import java.util.concurrent.Executors.newScheduledThreadPool
import java.util.concurrent.ThreadFactory

/**
 * @author Jerry Lee (oldratlee at gmail dot com)
 */
class TtlExecutorsTest {

    @Test
    fun test_common_executors() {
        val newScheduledThreadPool = newScheduledThreadPool(3)

        getTtlExecutor(newScheduledThreadPool).let {
            if (noTtlAgentRun()) assertTrue(it is ExecutorTtlWrapper)
            assertEquals(noTtlAgentRun(), isTtlWrapper(it))

            assertSame(newScheduledThreadPool, unwrap(it))
        }
        getTtlExecutorService(newScheduledThreadPool).let {
            if (noTtlAgentRun()) assertTrue(it is ExecutorServiceTtlWrapper)
            assertEquals(noTtlAgentRun(), isTtlWrapper(it))

            assertSame(newScheduledThreadPool, unwrap(it))
        }
        getTtlScheduledExecutorService(newScheduledThreadPool).let {
            if (noTtlAgentRun()) assertTrue(it is ScheduledExecutorServiceTtlWrapper)
            assertEquals(noTtlAgentRun(), isTtlWrapper(it))

            assertSame(newScheduledThreadPool, unwrap(it))
        }

        val threadFactory = ThreadFactory { Thread(it) }
        getDisableInheritableThreadFactory(threadFactory).let {
            assertTrue(it is DisableInheritableThreadFactory)
            assertTrue(isDisableInheritableThreadFactory(it))

            assertSame(threadFactory, unwrap(it))
        }
    }

    @Test
    fun test_null_executors() {
        assertNull(getTtlExecutor(null))
        assertNull(getTtlExecutorService(null))
        assertNull(getTtlScheduledExecutorService(null))

        assertFalse(isTtlWrapper(null))
        assertNull(unwrap<Executor>(null))
    }


}