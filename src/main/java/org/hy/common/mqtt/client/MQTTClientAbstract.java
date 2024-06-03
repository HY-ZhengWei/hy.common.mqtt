package org.hy.common.mqtt.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.hy.common.StringHelp;
import org.hy.common.mqtt.client.subscribe.IMqttMessageListener;
import org.hy.common.mqtt.client.subscribe.MqttSubscribeInfo;





/**
 * MQTT客户端接口的通用属性&方法的实现
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-21
 * @version     v1.0
 */
public abstract class MQTTClientAbstract implements IMQTTClient ,Serializable
{
    private static final long serialVersionUID = -178737343485570629L;
    
    
    
    /** 协议版本（只读） */
    private String    mqttVersion;
    
    /** IP地址（只读） */
    protected String  host;
    
    /** 访问端口（只读） */
    protected Integer port;
    
    /** 连接用户名称 */
    protected String  userName;
    
    /** 连接访问密码 */
    protected String  userPassword;
    
    /** 协议类型（只读） */
    protected String  protocol;
    
    /** 客户端ID（只读）。全局惟一 */
    protected String  clientID;
    
    /** 连接超时时长（单位：秒） */
    protected Integer connTimeout;
    
    /** 心跳时长（单位：秒） */
    protected Integer keepAlive;
    
    /** 是否清除会话 */
    protected Boolean cleanSession;
    
    /** 是否自动重连 */
    protected Boolean autoReconnect;
    
    /** 重连周期（单位：秒） */
    protected Integer reconnectPeriod;
    
    /** 遗嘱主题 */
    protected String  lastWillTopic;
    
    /** 遗嘱服务质量等级 */
    protected Integer lastWillQoS;
    
    /** 是否遗嘱保留 */
    protected Boolean lastWillRetain;
    
    /** 遗嘱消息 */
    protected String  lastWillPayload;
    
    /** 上线消息。首次上线或中断后再次上线均发送的消息。对应遗嘱消息是在离线时发送的功能 */
    protected String  onLinePayload;
    
    /** 正常退出消息。遗嘱消息仅在意外断开时才生效。当正常关闭设备时不发消息，可用此属性配置退出消息 */
    protected String  exitPayload;
    
    /** 订阅的主题集合 */
    protected Map<String ,MqttSubscribeInfo> subscribes;
    
    
    
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
    public MQTTClientAbstract(String i_Version ,String i_Protocol ,String i_Host ,int i_Port ,String i_ClientID)
    {
        this.mqttVersion     = i_Version;
        this.host            = i_Host;
        this.port            = i_Port;
        this.clientID        = i_ClientID;
        this.protocol        = StringHelp.replaceAll(i_Protocol ,new String[] {"mqtts" ,"mqtt"} ,new String[] {"ssl" ,"tcp"});
                             
        // 配置默认值
        this.connTimeout     = 10;
        this.keepAlive       = 60;
        this.cleanSession    = true;
        this.autoReconnect   = true;
        this.reconnectPeriod = 60;
        this.lastWillQoS     = 2;
        this.lastWillRetain  = true;
        this.subscribes      = new HashMap<String ,MqttSubscribeInfo>();
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
     * @return           是否成功
     */
    @Override
    public boolean publish(String i_Topic ,String i_Message)
    {
        return this.publish(i_Topic ,i_Message ,0 ,true);
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
     * @return           是否成功
     */
    @Override
    public boolean publish(String i_Topic ,String i_Message ,int i_QoS)
    {
        return this.publish(i_Topic ,i_Message ,i_QoS ,true);
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
    public boolean subscribe(String i_Topic ,int i_QoS ,IMqttMessageListener i_MessageListener)
    {
        this.subscribes.put(i_Topic ,new MqttSubscribeInfo(i_Topic ,i_QoS ,i_MessageListener));
        return true;
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
    public boolean unsubscribe(String i_Topic)
    {
        this.subscribes.remove(i_Topic);
        return true;
    }
    
    
    
    /**
     * 已订阅的主题信息集合
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-28
     * @version     v1.0
     *
     * @return
     */
    @Override
    public Map<String ,MqttSubscribeInfo> getSubscribes()
    {
        return this.subscribes;
    }
    
    
    
    /**
     * 获取服务器的连接URL
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-27
     * @version     v1.0
     *
     * @return
     */
    @Override
    public String getBrokerURL()
    {
        return this.getProtocol() + this.getHost() + ":" + this.getPort();
    }
    
    
    
    /**
     * 获取：IP地址（只读）
     */
    @Override
    public String getHost()
    {
        return host;
    }


    
    /**
     * 获取：访问端口（只读）
     */
    @Override
    public Integer getPort()
    {
        return port;
    }


    
    /**
     * 获取：协议类型（只读）
     */
    @Override
    public String getProtocol()
    {
        return protocol;
    }
    
    
    
    /**
     * 获取：客户端ID（只读）。全局惟一
     */
    @Override
    public String getClientID()
    {
        return clientID;
    }


    
    /**
     * 获取：协议版本（只读）
     */
    public String getMqttVersion()
    {
        return mqttVersion;
    }



    /**
     * 获取：连接超时时长（单位：秒）
     */
    @Override
    public Integer getConnTimeout()
    {
        return connTimeout;
    }


    
    /**
     * 设置：连接超时时长（单位：秒）
     * 
     * @param i_ConnTimeout 连接超时时长（单位：秒）
     */
    @Override
    public IMQTTClient setConnTimeout(Integer i_ConnTimeout)
    {
        this.connTimeout = i_ConnTimeout;
        return this;
    }



    /**
     * 获取：心跳时长（单位：秒）
     */
    @Override
    public Integer getKeepAlive()
    {
        return keepAlive;
    }


    
    /**
     * 设置：心跳时长（单位：秒）
     * 
     * @param i_KeepAlive 心跳时长（单位：秒）
     */
    @Override
    public IMQTTClient setKeepAlive(Integer i_KeepAlive)
    {
        this.keepAlive = i_KeepAlive;
        return this;
    }
    
    
    
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
    @Override
    public IMQTTClient setCleanSession(Boolean i_CleanSession)
    {
        this.cleanSession = i_CleanSession;
        return this;
    }
    
    
    
    /**
     * 获取：是否清除会话
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-21
     * @version     v1.0
     *
     * @return
     */
    @Override
    public Boolean getCleanSession()
    {
        return this.cleanSession;
    }


    
    /**
     * 获取：是否自动重连
     */
    @Override
    public Boolean isAutoReconnect()
    {
        return autoReconnect;
    }


    
    /**
     * 设置：是否自动重连
     * 
     * @param i_AutoReconnect 是否自动重连
     */
    @Override
    public IMQTTClient setAutoReconnect(Boolean i_AutoReconnect)
    {
        this.autoReconnect = i_AutoReconnect;
        return this;
    }


    
    /**
     * 获取：重连周期（单位：秒）
     */
    @Override
    public Integer getReconnectPeriod()
    {
        return reconnectPeriod;
    }


    
    /**
     * 设置：重连周期（单位：秒）
     * 
     * @param i_ReconnectPeriod 重连周期（单位：秒）
     */
    @Override
    public IMQTTClient setReconnectPeriod(Integer i_ReconnectPeriod)
    {
        this.reconnectPeriod = i_ReconnectPeriod;
        return this;
    }


    
    /**
     * 获取：遗嘱主题
     */
    @Override
    public String getLastWillTopic()
    {
        return lastWillTopic;
    }


    
    /**
     * 设置：遗嘱主题
     * 
     * @param i_LastWillTopic 遗嘱主题
     */
    @Override
    public IMQTTClient setLastWillTopic(String i_LastWillTopic)
    {
        this.lastWillTopic = i_LastWillTopic;
        return this;
    }


    
    /**
     * 获取：遗嘱服务质量等级
     */
    @Override
    public Integer getLastWillQoS()
    {
        return lastWillQoS;
    }


    
    /**
     * 设置：遗嘱服务质量等级
     * 
     * @param i_LastWillQoS 遗嘱服务质量等级
     */
    @Override
    public IMQTTClient setLastWillQoS(Integer i_LastWillQoS)
    {
        this.lastWillQoS = i_LastWillQoS;
        return this;
    }


    
    /**
     * 获取：是否遗嘱保留
     */
    @Override
    public Boolean getLastWillRetain()
    {
        return lastWillRetain;
    }


    
    /**
     * 设置：是否遗嘱保留
     * 
     * @param i_LastWillRetain 是否遗嘱保留
     */
    @Override
    public IMQTTClient setLastWillRetain(Boolean i_LastWillRetain)
    {
        this.lastWillRetain = i_LastWillRetain;
        return this;
    }


    
    /**
     * 获取：遗嘱消息
     */
    @Override
    public String getLastWillPayload()
    {
        return lastWillPayload;
    }


    
    /**
     * 设置：遗嘱消息
     * 
     * @param i_LastWillPayload 遗嘱消息
     */
    @Override
    public IMQTTClient setLastWillPayload(String i_LastWillPayload)
    {
        this.lastWillPayload = i_LastWillPayload;
        return this;
    }


    
    /**
     * 获取：上线消息。首次上线或中断后再次上线均发送的消息。对应遗嘱消息是在离线时发送的功能
     */
    @Override
    public String getOnLinePayload()
    {
        return onLinePayload;
    }


    
    /**
     * 设置：上线消息。首次上线或中断后再次上线均发送的消息。对应遗嘱消息是在离线时发送的功能
     * 
     * @param i_OnLinePayload 上线消息。首次上线或中断后再次上线均发送的消息。对应遗嘱消息是在离线时发送的功能
     */
    @Override
    public IMQTTClient setOnLinePayload(String i_OnLinePayload)
    {
        this.onLinePayload = i_OnLinePayload;
        return this;
    }


    
    /**
     * 获取：正常退出消息。遗嘱消息仅在意外断开时才生效。当正常关闭设备时不发消息，可用此属性配置退出消息
     */
    @Override
    public String getExitPayload()
    {
        return exitPayload;
    }


    
    /**
     * 设置：正常退出消息。遗嘱消息仅在意外断开时才生效。当正常关闭设备时不发消息，可用此属性配置退出消息
     * 
     * @param i_ExitPayload 正常退出消息。遗嘱消息仅在意外断开时才生效。当正常关闭设备时不发消息，可用此属性配置退出消息
     */
    @Override
    public IMQTTClient setExitPayload(String i_ExitPayload)
    {
        this.exitPayload = i_ExitPayload;
        return this;
    }

    
    
    /**
     * 获取：连接用户名称
     */
    public String getUserName()
    {
        return userName;
    }


    
    /**
     * 设置：连接用户名称
     * 
     * @param i_UserName 连接用户名称
     */
    public void setUserName(String i_UserName)
    {
        this.userName = i_UserName;
    }

    
    
    /**
     * 获取：连接访问密码
     */
    public String getUserPassword()
    {
        return userPassword;
    }

    
    
    /**
     * 设置：连接访问密码
     * 
     * @param i_UserPassword 连接访问密码
     */
    public void setUserPassword(String i_UserPassword)
    {
        this.userPassword = i_UserPassword;
    }
    
}
