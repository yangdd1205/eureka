package com.netflix.discovery.shared.resolver;

/**
 * 可关闭的解析器接口
 * @author David Liu
 */
public interface ClosableResolver<T extends EurekaEndpoint> extends ClusterResolver<T> {
    /**
     * 关闭
     */
    void shutdown();
}
