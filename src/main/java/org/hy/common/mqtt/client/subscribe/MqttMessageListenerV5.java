package org.hy.common.mqtt.client.subscribe;

import java.io.Serializable;

import org.eclipse.paho.mqttv5.common.MqttMessage;





/**
 * MQTT订阅消息的V5版本的通用监听器
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-26
 * @version     v1.0
 */
public class MqttMessageListenerV5 implements org.eclipse.paho.mqttv5.client.IMqttMessageListener ,Serializable
{
    private static final long serialVersionUID = 8630144514723633214L;
    
    
    
    private org.hy.common.mqtt.client.subscribe.IMqttMessageListener messageListener;
    
    
    
    public MqttMessageListenerV5(org.hy.common.mqtt.client.subscribe.IMqttMessageListener i_MessageListener)
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
     * @see org.eclipse.paho.mqttv5.client.IMqttMessageListener#messageArrived(java.lang.String, org.eclipse.paho.mqttv5.common.MqttMessage)
     */
    @Override
    public void messageArrived(String i_Topic ,MqttMessage i_Message) throws Exception
    {
        this.messageListener.messageArrived(i_Topic ,new MqttMessageV5(i_Message));
    }
    
}
