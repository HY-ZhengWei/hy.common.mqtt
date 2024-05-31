package org.hy.common.mqtt.client.subscribe;





/**
 * MQTT消息对象的统一接口
 * 
 * 整合V5和V3两个版本的消息
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-26
 * @version     v1.0
 */
public interface IMqttMessage
{
    
    /**
     * 获取：报文体
     */
    public byte [] getPayload();

    
    /**
     * 设置：报文体
     * 
     * @param i_Payload 报文体
     */
    public IMqttMessage setPayload(byte [] i_Payload);

    
    /**
     * 获取：服务质量等级
     */
    public int getQos();

    
    /**
     * 设置：服务质量等级
     * 
     * @param i_Qos 服务质量等级
     */
    public IMqttMessage setQos(int i_Qos);

    
    /**
     * 获取：保留消息
     */
    public boolean isRetained();

    
    /**
     * 设置：保留消息
     * 
     * @param i_Retained 保留消息
     */
    public IMqttMessage setRetained(boolean i_Retained);

    
    /**
     * 获取：重复标志。对 QoS（服务质量）级别为 1 或 2 的消息进行确认时使用的一个标志位。它用于标识发送方是否已经收到相应的确认消息
     */
    public boolean isDuplicate();

    
    /**
     * 获取：消息标识符
     */
    public int getMessageId();

    
    /**
     * 设置：消息标识符
     * 
     * @param i_MessageId 消息标识符
     */
    public IMqttMessage setMessageId(int i_MessageId);
    
}
