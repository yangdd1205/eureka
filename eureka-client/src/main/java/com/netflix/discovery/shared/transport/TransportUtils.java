/*
 * Copyright 2015 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.discovery.shared.transport;

import com.netflix.discovery.shared.transport.jersey.JerseyApplicationClient;
import com.netflix.discovery.shared.transport.jersey.JerseyEurekaHttpClientFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Tomasz Bak
 */
public final class TransportUtils {

    private TransportUtils() {
    }

    public static EurekaHttpClient getOrSetAnotherClient(AtomicReference<EurekaHttpClient> eurekaHttpClientRef, EurekaHttpClient another) {
        EurekaHttpClient existing = eurekaHttpClientRef.get();
        // 为空才设置
        if (eurekaHttpClientRef.compareAndSet(null, another)) {
            return another;
        }
        // 设置失败，意味着另外一个线程已经设置
        another.shutdown();
        // Fixing bug. 失败的线程直接返回 existing 的是 null
        //return existing;
        return eurekaHttpClientRef.get();
    }

    public static void shutdown(EurekaHttpClient eurekaHttpClient) {
        if (eurekaHttpClient != null) {
            eurekaHttpClient.shutdown();
        }
    }

    private static final CountDownLatch LATCH = new CountDownLatch(10);
    public static void main(String[] args) throws InterruptedException {
        AtomicReference<EurekaHttpClient> reference = new AtomicReference<>();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            EurekaHttpClient another = new JerseyApplicationClient(null,null,null);
            new Thread(()->{
                LATCH.countDown();
                System.out.println("i: " +index +" : " + getOrSetAnotherClient(reference,another));
            }).start();
        }

        Thread.sleep(Long.MAX_VALUE);
    }
}
