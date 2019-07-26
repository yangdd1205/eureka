package com.netflix.eureka.registry;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.discovery.shared.LookupService;
import com.netflix.discovery.shared.Pair;
import com.netflix.eureka.lease.LeaseManager;

import java.util.List;
import java.util.Map;

/**
 *
 * 应用实例注册表接口
 *
 * @author Tomasz Bak
 */
public interface InstanceRegistry extends LeaseManager<InstanceInfo>, LookupService<String> {

    /**
     * 开启通道
     *
     * @param applicationInfoManager
     * @param count
     */
    void openForTraffic(ApplicationInfoManager applicationInfoManager, int count);

    /**
     * 关机
     */
    void shutdown();

    @Deprecated
    void storeOverriddenStatusIfRequired(String id, InstanceStatus overriddenStatus);

    /**
     *
     * 存储重写状态如果需要
     *
     * @param appName
     * @param id
     * @param overriddenStatus
     */
    void storeOverriddenStatusIfRequired(String appName, String id, InstanceStatus overriddenStatus);

    /**
     * 状态更新
     *
     * @param appName
     * @param id
     * @param newStatus
     * @param lastDirtyTimestamp
     * @param isReplication
     * @return
     */
    boolean statusUpdate(String appName, String id, InstanceStatus newStatus,
                         String lastDirtyTimestamp, boolean isReplication);

    /**
     * 覆盖状态
     *
     * @param appName
     * @param id
     * @param newStatus
     * @param lastDirtyTimestamp
     * @param isReplication
     * @return
     */
    boolean deleteStatusOverride(String appName, String id, InstanceStatus newStatus,
                                 String lastDirtyTimestamp, boolean isReplication);

    /**
     * 写的实例状态快照
     *
     * @return
     */
    Map<String, InstanceStatus> overriddenInstanceStatusesSnapshot();

    /**
     * 仅从本地区域获取应用程序
     *
     * @return
     */
    Applications getApplicationsFromLocalRegionOnly();

    /**
     * 获取缓存的应用实例
     * @return
     */
    List<Application> getSortedApplications();

    /**
     * 获取应用信息
     *
     * Get application information.
     *
     * @param appName The name of the application
     * @param includeRemoteRegion true, if we need to include applications from remote regions
     *                            as indicated by the region {@link java.net.URL} by this property
     *                            {@link com.netflix.eureka.EurekaServerConfig#getRemoteRegionUrls()}, false otherwise
     * @return the application
     */
    Application getApplication(String appName, boolean includeRemoteRegion);

    /**
     * 获取实例信息
     *
     * Gets the {@link InstanceInfo} information.
     *
     * @param appName the application name for which the information is requested.
     * @param id the unique identifier of the instance.
     * @return the information about the instance.
     */
    InstanceInfo getInstanceByAppAndId(String appName, String id);

    /**
     * 获取实例信息
     * Gets the {@link InstanceInfo} information.
     *
     * @param appName the application name for which the information is requested.
     * @param id the unique identifier of the instance.
     * @param includeRemoteRegions true, if we need to include applications from remote regions
     *                             as indicated by the region {@link java.net.URL} by this property
     *                             {@link com.netflix.eureka.EurekaServerConfig#getRemoteRegionUrls()}, false otherwise
     * @return the information about the instance.
     */
    InstanceInfo getInstanceByAppAndId(String appName, String id, boolean includeRemoteRegions);

    /**
     * 情况注册信息
     */
    void clearRegistry();

    /**
     * 初始化响应缓存
     */
    void initializedResponseCache();

    /**
     * 获取响应缓存
     * @return
     */
    ResponseCache getResponseCache();

    /**
     * 在最后一分钟获得更新的数量
     * @return
     */
    long getNumOfRenewsInLastMin();

    /**
     * 获取每分钟续期阈值数量
     *
     * @return
     */
    int getNumOfRenewsPerMinThreshold();

    /**
     *
     * @return
     */
    int isBelowRenewThresold();

    /**
     * 获取最后N个注册的实例
     * @return
     */
    List<Pair<Long, String>> getLastNRegisteredInstances();

    /**
     * 获取最后N个已取消的实例
     *
     * @return
     */
    List<Pair<Long, String>> getLastNCanceledInstances();

    /**
     * 检查是否启用了租约到期。
     * Checks whether lease expiration is enabled.
     * @return true if enabled
     */
    boolean isLeaseExpirationEnabled();

    /**
     * 自我保护模式是否可用
     * @return
     */
    boolean isSelfPreservationModeEnabled();

}
