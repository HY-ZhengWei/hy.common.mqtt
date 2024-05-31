package org.hy.common.mqtt.client;

import java.util.Map;

import org.hy.common.mqtt.client.subscribe.IMqttMessageListener;
import org.hy.common.mqtt.client.subscribe.MqttSubscribeInfo;





/**
 * MQTT客户端统一接口
 * 
 * 整合V5和V3两个版本的接口
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-21
 * @version     v1.0
 */
public interface IMQTTClient
{
    
    
    /**
     * 连接服务器
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-21
     * @version     v1.0
     *
     * @return      是否连接成功
     */
    public boolean connect();
    
    
    
    /**
     * 断开与服务器的连接
     * 
     * 与 close() 关闭方法的区别是：
     *   1. disconnect() 执行后，还允许再连接服务器  connect();
     *   2. close()      执行后，将释放所有资源（如之前订阅的主题），拒绝再次连接服务
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-28
     * @version     v1.0
     *
     * @return
     */
    public boolean disconnect();
    
    
    
    /**
     * 关闭连接
     * 
     * 与 disconnect() 关闭方法的区别是：
     *   1. disconnect() 执行后，还允许再连接服务器  connect();
     *   2. close()      执行后，将释放所有资源（如之前订阅的主题），拒绝再次连接服务
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-28
     * @version     v1.0
     *
     * @return      关闭是否成功
     */
    public boolean close();
    
    
    
    /**
     * MQTT发布消息
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-26
     * @version     v1.0
     *
     * @param i_Topic    发布消息的主题
     * @param i_Message  消息内容
     * @return           是否成功
     */
    public boolean publish(String i_Topic ,String i_Message);
    
    
    
    /**
     * MQTT发布消息
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-26
     * @version     v1.0
     *
     * @param i_Topic    发布消息的主题
     * @param i_Message  消息内容
     * @param i_QoS      服务质量等级
     * @return           是否成功
     */
    public boolean publish(String i_Topic ,String i_Message ,int i_QoS);
    
    
    
    /**
     * MQTT发布消息
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-26
     * @version     v1.0
     *
     * @param i_Topic    发布消息的主题
     * @param i_Message  消息内容
     * @param i_QoS      服务质量等级
     * @param i_Retain   保留消息
     * @return           是否成功
     */
    public boolean publish(String i_Topic ,String i_Message ,int i_QoS ,boolean i_Retain);
    
    
    
    /**
     * 订阅消息
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-26
     * @version     v1.0
     *
     * @param i_Topic            订阅消息的主题
     * @param i_QoS              服务质量等级
     * @param i_MessageListener  订阅消息的监听器
     * @return                   是否成功
     */
    public boolean subscribe(String i_Topic ,int i_QoS ,IMqttMessageListener i_MessageListener);
    
    
    
    /**
     * 重新订阅。重新订阅之前订阅过的主题，主要用于重新连接或异常的情况
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-28
     * @version     v1.0
     *
     * @return      是否成功
     */
    public boolean resubscribes();
    
    
    
    /**
     * 取消订阅
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-28
     * @version     v1.0
     *
     * @param i_Topic  订阅消息的主题
     * @return         是否成功
     */
    public boolean unsubscribe(String i_Topic);
    
    
    
    /**
     * 已订阅的主题信息集合
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-28
     * @version     v1.0
     *
     * @return
     */
    public Map<String ,MqttSubscribeInfo> getSubscribes();
    
    
    
    /**
     * 获取服务器的连接URL
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-27
     * @version     v1.0
     *
     * @return
     */
    public String getBrokerURL();
    
    
    
    /**
     * 获取：IP地址（只读）
     */
    public String getHost();


    
    /**
     * 获取：访问端口（只读）
     */
    public Integer getPort();


    
    /**
     * 获取：协议类型（只读）
     */
    public String getProtocol();
    
    
    
    /**
     * 获取：客户端ID（只读）。全局惟一
     */
    public String getClientID();
    
    
    
    /**
     * 设置：是否清除会话
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-21
     * @version     v1.0
     *
     * @param i_CleanSession
     * @return
     */
    public IMQTTClient setCleanSession(Boolean i_CleanSession);
    
    
    
    /**
     * 获取：是否清除会话
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-21
     * @version     v1.0
     *
     * @return
     */
    public Boolean getCleanSession();
    
    
    
    /**
     * 获取：连接超时时长（单位：秒）
     */
    public Integer getConnTimeout();


    
    /**
     * 设置：连接超时时长（单位：秒）
     * 
     * @param i_ConnTimeout 连接超时时长（单位：秒）
     */
    public IMQTTClient setConnTimeout(Integer i_ConnTimeout);
    
    
    
    /**
     * 获取：心跳时长（单位：秒）
     */
    public Integer getKeepAlive();


    
    /**
     * 设置：心跳时长（单位：秒）
     * 
     * @param i_KeepAlive 心跳时长（单位：秒）
     */
    public IMQTTClient setKeepAlive(Integer i_KeepAlive);
    
    
    
    /**
     * 获取：是否自动重连
     */
    public Boolean isAutoReconnect();

    
    
    /**
     * 设置：是否自动重连
     * 
     * @param i_AutoReconnect 是否自动重连
     */
    public IMQTTClient setAutoReconnect(Boolean i_AutoReconnect);
    
    
    
    /**
     * 获取：重连周期（单位：秒）
     */
    public Integer getReconnectPeriod();


    
    /**
     * 设置：重连周期（单位：秒）
     * 
     * @param i_ReconnectPeriod 重连周期（单位：秒）
     */
    public IMQTTClient setReconnectPeriod(Integer i_ReconnectPeriod);
    
    
    
    /**
     * 获取：遗嘱主题
     */
    public String getLastWillTopic();


    
    /**
     * 设置：遗嘱主题
     * 
     * @param i_LastWillTopic 遗嘱主题
     */
    public IMQTTClient setLastWillTopic(String i_LastWillTopic);
    
    
    
    /**
     * 获取：遗嘱服务质量等级
     */
    public Integer getLastWillQoS();


    
    /**
     * 设置：遗嘱服务质量等级
     * 
     * @param i_LastWillQoS 遗嘱服务质量等级
     */
    public IMQTTClient setLastWillQoS(Integer i_LastWillQoS);


    
    /**
     * 获取：是否遗嘱保留
     */
    public Boolean getLastWillRetain();


    
    /**
     * 设置：是否遗嘱保留
     * 
     * @param i_LastWillRetain 是否遗嘱保留
     */
    public IMQTTClient setLastWillRetain(Boolean i_LastWillRetain);


    
    /**
     * 获取：遗嘱消息
     */
    public String getLastWillPayload();


    
    /**
     * 设置：遗嘱消息
     * 
     * @param i_LastWillPayload 遗嘱消息
     */
    public IMQTTClient setLastWillPayload(String i_LastWillPayload);
    
    
    
    /**
     * 获取：上线消息。首次上线或中断后再次上线均发送的消息。对应遗嘱消息是在离线时发送的功能
     */
    public String getOnLinePayload();

    
    
    /**
     * 设置：上线消息。首次上线或中断后再次上线均发送的消息。对应遗嘱消息是在离线时发送的功能
     * 
     * @param i_OnLinePayload 上线消息。首次上线或中断后再次上线均发送的消息。对应遗嘱消息是在离线时发送的功能
     */
    public IMQTTClient setOnLinePayload(String i_OnLinePayload);
    
    
    
    /**
     * 获取：正常退出消息。遗嘱消息仅在意外断开时才生效。当正常关闭设备时不发消息，可用此属性配置退出消息
     */
    public String getExitPayload();


    
    /**
     * 设置：正常退出消息。遗嘱消息仅在意外断开时才生效。当正常关闭设备时不发消息，可用此属性配置退出消息
     * 
     * @param i_ExitPayload 正常退出消息。遗嘱消息仅在意外断开时才生效。当正常关闭设备时不发消息，可用此属性配置退出消息
     */
    public IMQTTClient setExitPayload(String i_ExitPayload);
    
}
