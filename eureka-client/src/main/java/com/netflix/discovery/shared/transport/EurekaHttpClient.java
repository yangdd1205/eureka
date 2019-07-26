package com.netflix.discovery.shared.transport;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;

/**
 * Low level Eureka HTTP client API.
 *
 * @author Tomasz Bak
 */
public interface EurekaHttpClient {

    /**
     * 注册
     * @param info
     * @return
     */
    EurekaHttpResponse<Void> register(InstanceInfo info);

    /**
     * 取消
     * @param appName
     * @param id
     * @return
     */
    EurekaHttpResponse<Void> cancel(String appName, String id);

    /**
     * 心跳
     * @param appName
     * @param id
     * @param info
     * @param overriddenStatus
     * @return
     */
    EurekaHttpResponse<InstanceInfo> sendHeartBeat(String appName, String id, InstanceInfo info, InstanceStatus overriddenStatus);

    /**
     * 状态更新
     * @param appName
     * @param id
     * @param newStatus
     * @param info
     * @return
     */
    EurekaHttpResponse<Void> statusUpdate(String appName, String id, InstanceStatus newStatus, InstanceInfo info);

    /**
     * 删除覆盖状态
     * @param appName
     * @param id
     * @param info
     * @return
     */
    EurekaHttpResponse<Void> deleteStatusOverride(String appName, String id, InstanceInfo info);

    /**
     * 获取全部app
     * @param regions
     * @return
     */
    EurekaHttpResponse<Applications> getApplications(String... regions);

    /**
     * 获取增量修改
     * @param regions
     * @return
     */
    EurekaHttpResponse<Applications> getDelta(String... regions);

    /**
     * 根据VIP 获取
     * @param vipAddress
     * @param regions
     * @return
     */
    EurekaHttpResponse<Applications> getVip(String vipAddress, String... regions);

    /**
     * 根据安全vip获取
     * @param secureVipAddress
     * @param regions
     * @return
     */
    EurekaHttpResponse<Applications> getSecureVip(String secureVipAddress, String... regions);

    /**
     * 获取 appName 获取
     * @param appName
     * @return
     */
    EurekaHttpResponse<Application> getApplication(String appName);

    /**
     * 获取实例
     * @param appName
     * @param id
     * @return
     */
    EurekaHttpResponse<InstanceInfo> getInstance(String appName, String id);

    /**
     * 获取实例
     * @param id
     * @return
     */
    EurekaHttpResponse<InstanceInfo> getInstance(String id);

    /**
     * 关闭
     */
    void shutdown();
}
