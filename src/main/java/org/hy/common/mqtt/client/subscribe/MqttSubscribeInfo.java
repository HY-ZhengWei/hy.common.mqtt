package org.hy.common.mqtt.client.subscribe;

import java.io.Serializable;





/**
 * MQTT订阅信息。
 * 
 * 可用于重新连接时，恢复之前订阅的主题
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-28
 * @version     v1.0
 */
public class MqttSubscribeInfo implements Serializable
{
    
    private static final long serialVersionUID = -5311860936666235593L;
    
    

    /** 订阅主题 */
    private String               topic;
    
    /** 服务质量等级 */
    private Integer              qoS;
    
    /** MQTT订阅监听接器 */
    private IMqttMessageListener messagelistener;
    
    
    
    public MqttSubscribeInfo()
    {
        
    }
    
    
    
    public MqttSubscribeInfo(String i_Topic ,int i_QoS ,IMqttMessageListener i_MessageListener)
    {
        this.topic           = i_Topic;
        this.qoS             = i_QoS;
        this.messagelistener = i_MessageListener;
    }
    

    
    /**
     * 获取：订阅主题
     */
    public String getTopic()
    {
        return topic;
    }

    
    /**
     * 设置：订阅主题
     * 
     * @param i_Topic 订阅主题
     */
    public void setTopic(String i_Topic)
    {
        this.topic = i_Topic;
    }

    
    /**
     * 获取：服务质量等级
     */
    public Integer getQoS()
    {
        return qoS;
    }

    
    /**
     * 设置：服务质量等级
     * 
     * @param i_QoS 服务质量等级
     */
    public void setQoS(Integer i_QoS)
    {
        this.qoS = i_QoS;
    }

    
    /**
     * 获取：MQTT订阅监听接器
     */
    public IMqttMessageListener getMessageListener()
    {
        return messagelistener;
    }

    
    /**
     * 设置：MQTT订阅监听接器
     * 
     * @param i_MessageListener MQTT订阅监听接器
     */
    public void setMessageListener(IMqttMessageListener i_MessageListener)
    {
        this.messagelistener = i_MessageListener;
    }
    
}
