package org.hy.common.mqtt.broker;

import java.io.Serializable;

import org.hy.common.Date;
import org.hy.common.Help;
import org.hy.common.XJavaID;
import org.hy.common.mqtt.client.IMQTTClient;
import org.hy.common.mqtt.client.MQTTClientV3;
import org.hy.common.mqtt.client.MQTTClientV5;





/**
 * MQTT服务Broker的XJava实例类
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-01-22
 * @version     v1.0
 */
public class XBroker implements XJavaID ,Serializable
{
    
    private static final long serialVersionUID = 5476092263614840546L;
    
    
    
    /** MQTT服务配置 */
    private BrokerConfig broker;
    
    /** MQTT客户端统一接口 */
    private IMQTTClient  mqttClient;
    
    
    
    public XBroker(BrokerConfig i_Broker)
    {
        this.broker = i_Broker;
        
        String v_ClientID = i_Broker.getXid() + ":" + Help.getMacs() + ":" + Date.getNowTime().getFull_ID();
        
        if ( "3.1"  .equals(this.broker.getMqttVersion())
          || "3.1.1".equals(this.broker.getMqttVersion()) )
        {
            this.mqttClient = new MQTTClientV3(this.broker.getMqttVersion()
                                              ,this.broker.getProtocol()
                                              ,this.broker.getHost()
                                              ,this.broker.getPort()
                                              ,v_ClientID);
        }
        else if ( "5.0".equals(this.broker.getMqttVersion()) )
        {
            this.mqttClient = new MQTTClientV5(this.broker.getProtocol()
                                              ,this.broker.getHost()
                                              ,this.broker.getPort()
                                              ,v_ClientID);
        }
        else
        {
            throw new InstantiationError("xid=" + i_Broker.getXid() + " version(" + this.broker.getMqttVersion() + ") is invalid");
        }
        
        this.mqttClient.setConnTimeout(    Help.NVL(i_Broker.getConnTimeout()     ,10));
        this.mqttClient.setKeepAlive(      Help.NVL(i_Broker.getKeepAlive()       ,60));
        this.mqttClient.setCleanSession(   Help.NVL(i_Broker.getCleanSession()    ,1) >= 1);
        this.mqttClient.setAutoReconnect(  Help.NVL(i_Broker.getAutoReconnect()   ,1) >= 1);
        this.mqttClient.setReconnectPeriod(Help.NVL(i_Broker.getReconnectPeriod() ,60));
        this.mqttClient.setLastWillTopic(           i_Broker.getLastWillTopic());
        this.mqttClient.setLastWillPayload(         i_Broker.getLastWillPayload());
        this.mqttClient.setLastWillQoS(    Help.NVL(i_Broker.getLastWillQoS()     ,2));
        this.mqttClient.setLastWillRetain( Help.NVL(i_Broker.getLastWillRetain()  ,1) >= 1);
        this.mqttClient.setOnLinePayload(           i_Broker.getOnLinePayload());
        this.mqttClient.setExitPayload(             i_Broker.getExitPayload());
        this.mqttClient.setUserName(       Help.NVL(i_Broker.getUserName()     ,null));
        this.mqttClient.setUserPassword(   Help.NVL(i_Broker.getUserPassword() ,null));
    }
    
    

    /**
     * 获取：MQTT客户端统一接口
     */
    public IMQTTClient getMqttClient()
    {
        return mqttClient;
    }



    /**
     * 设置XJava池中对象的ID标识。此方法不用用户调用设置值，是自动的。
     * 
     * @param i_XJavaID
     */
    @Override
    public void setXJavaID(String i_XJavaID)
    {
        this.broker.setXid(i_XJavaID);
    }
    
    
    
    /**
     * 获取XJava池中对象的ID标识。
     * 
     * @return
     */
    @Override
    public String getXJavaID()
    {
        return this.broker.getXid();
    }


    
    /**
     * 获取：注解说明
     */
    @Override
    public String getComment()
    {
        return this.broker.getComment();
    }


    
    /**
     * 设置：注解说明
     * 
     * @param i_Comment 注解说明
     */
    @Override
    public void setComment(String i_Comment)
    {
        this.broker.setComment(i_Comment);
    }


    
    /**
     * 获取：MQTT服务配置
     */
    public BrokerConfig getBrokerConfig()
    {
        return broker;
    }

}
