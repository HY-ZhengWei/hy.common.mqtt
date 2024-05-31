package org.hy.common.mqtt.client.subscribe;





/**
 * MQTT订阅监听接口
 * 
 * 整合V5和V3两个版本的订阅监听接口
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-26
 * @version     v1.0
 */
public interface IMqttMessageListener
{
    
    /**
     * 收到消息
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-26
     * @version     v1.0
     *
     * @param i_Topic     主题
     * @param i_Message   消息
     * @throws Exception
     */
    void messageArrived(String i_Topic, IMqttMessage i_Message) throws Exception;
    
}
