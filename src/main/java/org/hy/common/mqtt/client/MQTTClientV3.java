package org.hy.common.mqtt.client;

import java.io.Serializable;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.hy.common.Help;
import org.hy.common.mqtt.client.subscribe.IMqttMessageListener;
import org.hy.common.mqtt.client.subscribe.MqttMessageListenerV3;
import org.hy.common.mqtt.client.subscribe.MqttSubscribeInfo;
import org.hy.common.xml.log.Logger;





/**
 * MQTT V3版本的客户端
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-26
 * @version     v1.0
 */
public class MQTTClientV3 extends MQTTClientAbstract implements IMQTTClient ,Serializable
{
    private static final long serialVersionUID = 3195143240637930435L;
    
    private static Logger $Logger = new Logger(MQTTClientV3.class);
    
    
    
    private IMqttClient mqttClient;
    
    
    
    /**
     * 构造器
     *
     * @author      ZhengWei(HY)
     * @createDate  2024-02-26
     * @version     v1.0
     *
     * @param i_Version   协议版本。支持如下
     *                        1. 3.1
     *                        2. 3.1.1
     *                        3. 5.0
     * @param i_Protocol  通讯协议。支持如下
     *                        1. tcp:// 或 mqtt://
     *                        2. ssl:// 或 mqtts://
     * @param i_Host      IP地址
     * @param i_Port      访问端口
     * @param i_ClientID  客户端ID。全局惟一
     */
    public MQTTClientV3(String i_Version ,String i_Protocol ,String i_Host ,int i_Port ,String i_ClientID)
    {
        super(i_Version ,i_Protocol ,i_Host ,i_Port ,i_ClientID);
        
        String v_BrokerURL = this.getBrokerURL();
        try
        {
            final MQTTClientV3 v_This = this;
            this.mqttClient = new MqttClient(v_BrokerURL ,this.getClientID() ,new MemoryPersistence());
            this.mqttClient.setCallback(new MqttCallback()
            {
                @Override
                public void connectionLost(Throwable i_Cause)
                {
                    $Logger.error(v_This.getBrokerURL() + " is connectionLost" ,i_Cause);
                    if ( v_This.isAutoReconnect() )
                    {
                        int v_ReconnectMaxCount = 1000;
                        int v_ReconnectCount    = 0;
                        do
                        {
                            v_ReconnectCount++;
                            try
                            {
                                Thread.sleep(v_This.getReconnectPeriod() * 1000);
                                $Logger.info(v_This.getBrokerURL() + " is reconnect ...");
                                mqttClient.reconnect();
                                $Logger.info(v_This.getBrokerURL() + " is reconnect ok");
                            }
                            catch (MqttException exce)
                            {
                                $Logger.error($Logger);
                            }
                            catch (InterruptedException e)
                            {
                                $Logger.error($Logger);
                            }
                        } while ( !mqttClient.isConnected() && v_ReconnectCount < v_ReconnectMaxCount );
                    }
                    
                    // 重新连接发送上线消息
                    if ( !Help.isNull(v_This.getLastWillTopic()) && !Help.isNull(v_This.getOnLinePayload()) )
                    {
                        v_This.publish(v_This.getLastWillTopic()
                                      ,v_This.getOnLinePayload()
                                      ,v_This.getLastWillQoS()
                                      ,v_This.getLastWillRetain());
                    }
                    
                    v_This.resubscribes();
                }

                @Override
                public void messageArrived(String i_Topic ,MqttMessage i_Message) throws Exception
                {
                    // Nothing.
                    // 消息订阅，在本类库中被 MQTTClientV3.subscribe(...) 方法取代，并且没有冲突
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken i_Token)
                {
                    // Nothing.
                    // 用于在消息成功传递到目标客户端后进行通知。当客户端发布一条消息时，它会收到一个
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
            MqttConnectOptions v_MQTTConnOpts = new MqttConnectOptions();
            
            if ( "3.1".equals(this.getMqttVersion()) )
            {
                v_MQTTConnOpts.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            }
            else
            {
                v_MQTTConnOpts.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            }
            
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
                v_MQTTConnOpts.setCleanSession(this.getCleanSession());
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
                v_MQTTConnOpts.setWill(this.getLastWillTopic()
                                      ,this.getLastWillPayload().getBytes()
                                      ,Help.NVL(this.getLastWillQoS() ,2)
                                      ,Help.NVL(this.getLastWillRetain() ,true));
            }
            
            this.mqttClient.connect(v_MQTTConnOpts);
            
            // 首次连接发送上线消息
            if ( !Help.isNull(this.getLastWillTopic()) && !Help.isNull(this.getOnLinePayload()) )
            {
                this.publish(this.getLastWillTopic()
                            ,this.getOnLinePayload()
                            ,this.getLastWillQoS()
                            ,this.getLastWillRetain());
            }
            
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
     *
     * @param i_Topic    发布消息的主题
     * @param i_Message  消息内容
     * @param i_QoS      服务质量等级
     * @param i_Retain   保留消息
     * @return           是否成功
     */
    @Override
    public boolean publish(String i_Topic ,String i_Message ,int i_QoS ,boolean i_Retain)
    {
        try
        {
            MqttMessage v_Payload = new MqttMessage(i_Message.getBytes());
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
            this.mqttClient.subscribe(i_Topic ,i_QoS ,new MqttMessageListenerV3(i_MessageListener));
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
                    this.mqttClient.subscribe(v_Subscribe.getTopic() ,v_Subscribe.getQoS() ,new MqttMessageListenerV3(v_Subscribe.getMessageListener()));
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
