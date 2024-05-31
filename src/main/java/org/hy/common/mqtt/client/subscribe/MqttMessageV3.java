package org.hy.common.mqtt.client.subscribe;

import java.io.Serializable;

import org.eclipse.paho.client.mqttv3.MqttMessage;





/**
 * MQTT消息对象，V3版本的
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-26
 * @version     v1.0
 */
public class MqttMessageV3 implements IMqttMessage ,Serializable
{
    
    private static final long serialVersionUID = 7345584394939946999L;
    
    
    
    private MqttMessage message;
    
    
    
    public MqttMessageV3(MqttMessage i_V3)
    {
        this.message = i_V3;
    }
    
    
    /**
     * 获取：报文体
     */
    @Override
    public byte [] getPayload()
    {
        return this.message.getPayload();
    }

    
    /**
     * 设置：报文体
     * 
     * @param i_Payload 报文体
     */
    @Override
    public IMqttMessage setPayload(byte [] i_Payload)
    {
        this.message.setPayload(i_Payload);
        return this;
    }

    
    /**
     * 获取：服务质量等级
     */
    @Override
    public int getQos()
    {
        return this.getQos();
    }

    
    /**
     * 设置：服务质量等级
     * 
     * @param i_Qos 服务质量等级
     */
    @Override
    public IMqttMessage setQos(int i_Qos)
    {
        this.message.setQos(i_Qos);
        return this;
    }

    
    /**
     * 获取：保留消息
     */
    @Override
    public boolean isRetained()
    {
        return this.message.isRetained();
    }

    
    /**
     * 设置：保留消息
     * 
     * @param i_Retained 保留消息
     */
    @Override
    public IMqttMessage setRetained(boolean i_Retained)
    {
        this.message.setRetained(i_Retained);
        return this;
    }

    
    /**
     * 获取：重复标志。对 QoS（服务质量）级别为 1 或 2 的消息进行确认时使用的一个标志位。它用于标识发送方是否已经收到相应的确认消息
     */
    @Override
    public boolean isDuplicate()
    {
        return this.message.isDuplicate();
    }

    
    /**
     * 获取：消息标识符
     */
    @Override
    public int getMessageId()
    {
        return this.message.getId();
    }

    
    /**
     * 设置：消息标识符
     * 
     * @param i_MessageId 消息标识符
     */
    @Override
    public IMqttMessage setMessageId(int i_MessageId)
    {
        this.message.setId(i_MessageId);
        return this;
    }}
