package org.hy.common.mqtt.client.subscribe;

import java.io.Serializable;

import org.eclipse.paho.client.mqttv3.MqttMessage;





/**
 * MQTT订阅消息的V3版本的通用监听器
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-26
 * @version     v1.0
 */
public class MqttMessageListenerV3 implements org.eclipse.paho.client.mqttv3.IMqttMessageListener ,Serializable
{
    
    private static final long serialVersionUID = -1449694676312403544L;
    
    
    
    private org.hy.common.mqtt.client.subscribe.IMqttMessageListener messageListener;
    
    
    
    public MqttMessageListenerV3(org.hy.common.mqtt.client.subscribe.IMqttMessageListener i_MessageListener)
    {
        this.messageListener = i_MessageListener;
    }
    
    
    
    /**
     * 消息达到
     *
     * @author      ZhengWei(HY)
     * @createDate  2024-02-26
     * @version     v1.0
     *
     * @param i_Topic     主题
     * @param i_Message   消息
     * @throws Exception
     *
     * @see org.eclipse.paho.client.mqttv3.IMqttMessageListener#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
     */
    @Override
    public void messageArrived(String i_Topic ,MqttMessage i_Message) throws Exception
    {
        this.messageListener.messageArrived(i_Topic ,new MqttMessageV3(i_Message));
    }
    
}
