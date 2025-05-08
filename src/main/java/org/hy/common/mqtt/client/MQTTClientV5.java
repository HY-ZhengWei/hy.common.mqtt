package org.hy.common.mqtt.client;

import java.io.Serializable;

import org.eclipse.paho.mqttv5.client.IMqttClient;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSecurityException;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.hy.common.Help;
import org.hy.common.StringHelp;
import org.hy.common.mqtt.client.enums.MessageFormat;
import org.hy.common.mqtt.client.subscribe.IMqttMessageListener;
import org.hy.common.mqtt.client.subscribe.MqttMessageListenerV5;
import org.hy.common.mqtt.client.subscribe.MqttSubscribeInfo;
import org.hy.common.xml.log.Logger;





/**
 * MQTT V5版本的客户端
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-26
 * @version     v1.0
 *              v2.0  2025-05-08  添加：MQTT消息格式支持16进制的格式
 */
public class MQTTClientV5 extends MQTTClientAbstract implements IMQTTClient ,Serializable
{
    private static final long serialVersionUID = -7214470102465677104L;

    private static Logger $Logger = new Logger(MQTTClientV5.class);
    
    
    
    private IMqttClient mqttClient;
    
    
    
    /**
     * 构造器
     *
     * @author      ZhengWei(HY)
     * @createDate  2024-02-26
     * @version     v1.0
     *
     * @param i_Protocol  通讯协议。支持如下
     *                        1. tcp:// 或 mqtt://
     *                        2. ssl:// 或 mqtts://
     * @param i_Host      IP地址
     * @param i_Port      访问端口
     * @param i_ClientID  客户端ID。全局惟一
     */
    public MQTTClientV5(String i_Protocol ,String i_Host ,int i_Port ,String i_ClientID)
    {
        super("5.0" ,i_Protocol ,i_Host ,i_Port ,i_ClientID);
        
        String v_BrokerURL = this.getBrokerURL();
        try
        {
            final MQTTClientV5 v_This = this;
            this.mqttClient = new MqttClient(v_BrokerURL ,this.getClientID() ,new MemoryPersistence());
            this.mqttClient.setCallback(new MqttCallback()
            {
                @Override
                public void disconnected(MqttDisconnectResponse disconnectResponse)
                {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mqttErrorOccurred(MqttException exception)
                {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void messageArrived(String i_Topic ,MqttMessage i_Message) throws Exception
                {
                    // Nothing.
                    // 消息订阅，在本类库中被 MQTTClientV5.subscribe(...) 方法取代，并且没有冲突
                }

                @Override
                public void deliveryComplete(IMqttToken token)
                {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void connectComplete(boolean i_Reconnect ,String i_ServerURI)
                {
                    // 首次连接发送上线消息
                    // 重新连接发送上线消息
                    if ( !Help.isNull(v_This.getLastWillTopic()) && !Help.isNull(v_This.getOnLinePayload()) )
                    {
                        v_This.publish(v_This.getLastWillTopic()
                                      ,v_This.getOnLinePayload()
                                      ,v_This.getLastWillQoS()
                                      ,v_This.getLastWillRetain());
                    }
                    
                    // 重新连接时
                    if ( i_Reconnect )
                    {
                        v_This.resubscribes();
                    }
                }

                @Override
                public void authPacketArrived(int reasonCode ,MqttProperties properties)
                {
                    // TODO Auto-generated method stub
                    
                }
            });
        }
        catch (MqttException exce)
        {
            $Logger.error(exce);
            throw new InstantiationError(v_BrokerURL + " client is Exception " + exce.getMessage());
        }
    }
    
    
    
    /**
     * 连接服务器
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-21
     * @version     v1.0
     *
     * @return      是否连接成功
     */
    @Override
    public synchronized boolean connect()
    {
        if ( this.mqttClient.isConnected() )
        {
            return true;
        }
        
        try
        {
            MqttConnectionOptions v_MQTTConnOpts = new MqttConnectionOptions();
            
            if ( this.getConnTimeout() != null )
            {
                v_MQTTConnOpts.setConnectionTimeout(this.getConnTimeout());
            }
            
            if ( this.getKeepAlive() != null )
            {
                v_MQTTConnOpts.setKeepAliveInterval(this.getKeepAlive());
            }
            
            if ( this.getCleanSession() != null )
            {
                v_MQTTConnOpts.setCleanStart(this.getCleanSession());
            }
            
            if ( this.isAutoReconnect() != null )
            {
                v_MQTTConnOpts.setAutomaticReconnect(this.isAutoReconnect());
            }
            
            if ( this.getReconnectPeriod() != null )
            {
                v_MQTTConnOpts.setMaxReconnectDelay(this.getReconnectPeriod() * 1000);
            }
            
            if ( !Help.isNull(this.getLastWillTopic()) && !Help.isNull(this.getLastWillPayload()) )
            {
                MqttMessage v_Will = new MqttMessage(this.getLastWillPayload().getBytes());
                
                if ( this.getLastWillQoS() != null )
                {
                    v_Will.setQos(this.getLastWillQoS());
                }
                
                if ( this.getLastWillRetain() != null )
                {
                    v_Will.setRetained(this.getLastWillRetain());
                }
                
                v_MQTTConnOpts.setWill(this.getLastWillTopic() ,v_Will);
            }
            
            if ( this.getUserName() != null )
            {
                v_MQTTConnOpts.setUserName(this.getUserName());
            }
            
            if ( this.getUserPassword() != null )
            {
                v_MQTTConnOpts.setPassword(this.getUserPassword().getBytes());
            }
            
            this.mqttClient.connect(v_MQTTConnOpts);
            return true;
        }
        catch (MqttSecurityException exce)
        {
            $Logger.error(exce);
        }
        catch (MqttException exce)
        {
            $Logger.error(exce);
        }
        
        return false;
    }
    
    
    
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
    @Override
    public synchronized boolean disconnect()
    {
        try
        {
            if ( this.mqttClient.isConnected() )
            {
                if ( !Help.isNull(this.getLastWillTopic()) && !Help.isNull(this.getExitPayload()) )
                {
                    this.publish(this.getLastWillTopic() ,this.getExitPayload() ,this.getLastWillQoS() ,this.getLastWillRetain());
                }
                
                this.mqttClient.disconnect();
            }
            return true;
        }
        catch (MqttException exce)
        {
            $Logger.error(exce);
        }
        
        return false;
    }
    
    
    
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
    @Override
    public synchronized boolean close()
    {
        this.disconnect();
        
        try
        {
            this.mqttClient.close();
            this.getSubscribes().clear();
            return true;
        }
        catch (MqttException exce)
        {
            $Logger.error(exce);
        }
        
        return false;
    }
    
    
    
    /**
     * MQTT发布消息
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-26
     * @version     v1.0
     *              v2.0  2025-05-08  添加：MQTT消息格式支持16进制的格式
     *
     * @param i_Topic    发布消息的主题
     * @param i_Message  消息内容
     * @param i_Format   消息内容的格式
     * @param i_QoS      服务质量等级
     * @param i_Retain   保留消息
     * @return           是否成功
     */
    @Override
    public boolean publish(String i_Topic ,String i_Message ,MessageFormat i_Format ,int i_QoS ,boolean i_Retain)
    {
        try
        {
            MqttMessage v_Payload = null;
            
            if ( MessageFormat.Hex.equals(i_Format) )
            {
                v_Payload = new MqttMessage(StringHelp.hexToBytes(i_Message));
            }
            else
            {
                v_Payload = new MqttMessage(i_Message.getBytes());
            }
            
            v_Payload.setQos(i_QoS);
            v_Payload.setRetained(i_Retain);
            this.mqttClient.publish(i_Topic, v_Payload);
            
            return true;
        }
        catch (MqttException exce)
        {
            $Logger.error("reason " + exce.getReasonCode());
            $Logger.error("msg "    + exce.getMessage());
            $Logger.error("loc "    + exce.getLocalizedMessage());
            $Logger.error("cause "  + exce.getCause());
            $Logger.error(exce);
        }
        
        return false;
    }
    
    
    
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
    @Override
    public synchronized boolean subscribe(String i_Topic ,int i_QoS ,IMqttMessageListener i_MessageListener)
    {
        try
        {
            this.subscribeHelp(i_Topic ,i_QoS ,i_MessageListener);
            super.subscribe(i_Topic ,i_QoS ,i_MessageListener);
            return true;
        }
        catch (MqttException exce)
        {
            $Logger.error(exce);
        }
        
        return false;
    }
    
    
    
    /**
     * 订阅消息
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-28
     * @version     v1.0
     *
     * @param i_Topic            订阅消息的主题
     * @param i_QoS              服务质量等级
     * @param i_MessageListener  订阅消息的监听器
     * @throws MqttException
     */
    private void subscribeHelp(String i_Topic ,int i_QoS ,IMqttMessageListener i_MessageListener) throws MqttException
    {
        // 2024-02-28 发现官方Debug
        // 在 1.2.5 公开发布的 jar 中 MqttClient.subscribe(String ,int ,IMqttMessageListener); 方法有递归引用的问题
        // 问题出现在 MqttClient.java 文件 519 ~ 526行间的代码。
        // 后面查看官方源码已修正，但未发布新的版本
        // 暂时解决方案如下
        MqttSubscription v_MqttSubscription = new MqttSubscription(i_Topic);
        v_MqttSubscription.setQos(i_QoS);
        
        ((MqttClient) this.mqttClient).subscribe(new MqttSubscription                                    [] {v_MqttSubscription}
                                                ,new org.eclipse.paho.mqttv5.client.IMqttMessageListener [] {new MqttMessageListenerV5(i_MessageListener)});
    }
    
    
    
    /**
     * 重新订阅。重新订阅之前订阅过的主题，主要用于重新连接或异常的情况
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-28
     * @version     v1.0
     *
     * @return      是否成功
     */
    @Override
    public synchronized boolean resubscribes()
    {
        boolean v_Ret = true;
        
        if ( !Help.isNull(this.getSubscribes()) )
        {
            for (MqttSubscribeInfo v_Subscribe : this.getSubscribes().values())
            {
                try
                {
                    this.subscribeHelp(v_Subscribe.getTopic() ,v_Subscribe.getQoS() ,v_Subscribe.getMessageListener());
                    $Logger.info(this.getBrokerURL() + " topic(" + v_Subscribe.getTopic() + ") is resubscribe ok");
                }
                catch (MqttException exce)
                {
                    $Logger.info(this.getBrokerURL() + " topic(" + v_Subscribe.getTopic() + ") is resubscribe error");
                    $Logger.error(exce);
                    v_Ret = false;
                }
            }
        }
        
        return v_Ret;
    }
    
    
    
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
    @Override
    public synchronized boolean unsubscribe(String i_Topic)
    {
        try
        {
            this.mqttClient.unsubscribe(i_Topic);
            super.unsubscribe(i_Topic);
            return true;
        }
        catch (MqttException exce)
        {
            $Logger.error(exce);
        }
        
        return false;
    }
    
}
