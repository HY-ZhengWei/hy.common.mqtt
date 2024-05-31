package org.hy.common.mqtt.broker;

import java.io.Serializable;

import org.hy.common.Help;
import org.hy.common.XJavaID;





/**
 * MQTT服务配置
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-20
 * @version     v1.0
 */
public class BrokerConfig implements XJavaID ,Serializable
{
    
    private static final long serialVersionUID = -6100833276411225309L;

    /** 主键 */
    private String  id;
    
    /** 逻辑ID */
    private String  xid;
    
    /** 旧的逻辑ID */
    private String  xidOld;
    
    /** IP地址 */
    private String  host;
    
    /** 访问端口 */
    private Integer port;
    
    /** 协议类型 */
    private String  protocol;
    
    /** 连接用户名称 */
    private String  userName;
    
    /** 连接访问密码 */
    private String  userPassword;
    
    /** 协议版本 */
    private String  mqttVersion;
    
    /** 连接超时时长（单位：秒） */
    private Integer connTimeout;
    
    /** 心跳时长（单位：秒） */
    private Integer keepAlive;
    
    /** 是否清除会话 */
    private Integer cleanSession;
    
    /** 是否自动重连 */
    private Integer autoReconnect;
    
    /** 重连周期（单位：秒） */
    private Integer reconnectPeriod;
    
    /** 遗嘱主题 */
    private String  lastWillTopic;
    
    /** 遗嘱服务质量等级 */
    private Integer lastWillQoS;
    
    /** 是否遗嘱保留 */
    private Integer lastWillRetain;
    
    /** 遗嘱消息 */
    private String  lastWillPayload;
    
    /** 上线消息。首次上线或中断后再次上线均发送的消息。对应遗嘱消息是在离线时发送的功能 */
    private String  onLinePayload;
    
    /** 正常退出消息。遗嘱消息仅在意外断开时才生效。当正常关闭设备时不发消息，可用此属性配置退出消息 */
    private String  exitPayload;
    
    /** 说明备注 */
    private String  comment;
    
    
    
    /**
     * 获取：主键
     */
    public String getId()
    {
        return id;
    }

    
    /**
     * 设置：主键
     * 
     * @param i_Id 主键
     */
    public void setId(String i_Id)
    {
        this.id = i_Id;
    }

    
    /**
     * 获取：逻辑ID。
     */
    public String getXid()
    {
        return xid;
    }

    
    /**
     * 设置：逻辑ID。
     * 
     * @param i_Xid 逻辑ID。
     */
    public void setXid(String i_Xid)
    {
        this.xid = i_Xid;
    }

    
    /**
     * 获取：旧的逻辑ID
     */
    public String getXidOld()
    {
        return xidOld;
    }

    
    /**
     * 设置：旧的逻辑ID
     * 
     * @param i_XidOld 旧的逻辑ID
     */
    public void setXidOld(String i_XidOld)
    {
        this.xidOld = i_XidOld;
    }


    /**
     * 获取：IP地址
     */
    public String getHost()
    {
        return host;
    }

    
    /**
     * 设置：IP地址
     * 
     * @param i_Host IP地址
     */
    public void setHost(String i_Host)
    {
        this.host = i_Host;
    }

    
    /**
     * 获取：访问端口
     */
    public Integer getPort()
    {
        return port;
    }

    
    /**
     * 设置：访问端口
     * 
     * @param i_Port 访问端口
     */
    public void setPort(Integer i_Port)
    {
        this.port = i_Port;
    }

    
    /**
     * 获取：协议类型
     */
    public String getProtocol()
    {
        return protocol;
    }

    
    /**
     * 设置：协议类型
     * 
     * @param i_Protocol 协议类型
     */
    public void setProtocol(String i_Protocol)
    {
        this.protocol = Help.NVL(i_Protocol).toLowerCase();
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

    
    /**
     * 获取：协议版本
     */
    public String getMqttVersion()
    {
        return mqttVersion;
    }

    
    /**
     * 设置：协议版本
     * 
     * @param i_MqttVersion 协议版本
     */
    public void setMqttVersion(String i_MqttVersion)
    {
        this.mqttVersion = i_MqttVersion;
    }

    
    /**
     * 获取：连接超时时长（单位：秒）
     */
    public Integer getConnTimeout()
    {
        return connTimeout;
    }

    
    /**
     * 设置：连接超时时长（单位：秒）
     * 
     * @param i_ConnTimeout 连接超时时长（单位：秒）
     */
    public void setConnTimeout(Integer i_ConnTimeout)
    {
        this.connTimeout = i_ConnTimeout;
    }

    
    /**
     * 获取：心跳时长（单位：秒）
     */
    public Integer getKeepAlive()
    {
        return keepAlive;
    }

    
    /**
     * 设置：心跳时长（单位：秒）
     * 
     * @param i_KeepAlive 心跳时长（单位：秒）
     */
    public void setKeepAlive(Integer i_KeepAlive)
    {
        this.keepAlive = i_KeepAlive;
    }

    
    /**
     * 获取：是否清除会话
     */
    public Integer getCleanSession()
    {
        return cleanSession;
    }

    
    /**
     * 设置：是否清除会话
     * 
     * @param i_CleanSession 是否清除会话
     */
    public void setCleanSession(Integer i_CleanSession)
    {
        this.cleanSession = i_CleanSession;
    }

    
    /**
     * 获取：是否自动重连
     */
    public Integer getAutoReconnect()
    {
        return autoReconnect;
    }

    
    /**
     * 设置：是否自动重连
     * 
     * @param i_AutoReconnect 是否自动重连
     */
    public void setAutoReconnect(Integer i_AutoReconnect)
    {
        this.autoReconnect = i_AutoReconnect;
    }

    
    /**
     * 获取：重连周期（单位：秒）
     */
    public Integer getReconnectPeriod()
    {
        return reconnectPeriod;
    }

    
    /**
     * 设置：重连周期（单位：秒）
     * 
     * @param i_ReconnectPeriod 重连周期（单位：秒）
     */
    public void setReconnectPeriod(Integer i_ReconnectPeriod)
    {
        this.reconnectPeriod = i_ReconnectPeriod;
    }

    
    /**
     * 获取：遗嘱主题
     */
    public String getLastWillTopic()
    {
        return lastWillTopic;
    }

    
    /**
     * 设置：遗嘱主题
     * 
     * @param i_LastWillTopic 遗嘱主题
     */
    public void setLastWillTopic(String i_LastWillTopic)
    {
        this.lastWillTopic = i_LastWillTopic;
    }

    
    /**
     * 获取：遗嘱服务质量等级
     */
    public Integer getLastWillQoS()
    {
        return lastWillQoS;
    }

    
    /**
     * 设置：遗嘱服务质量等级
     * 
     * @param i_LastWillQoS 遗嘱服务质量等级
     */
    public void setLastWillQoS(Integer i_LastWillQoS)
    {
        this.lastWillQoS = i_LastWillQoS;
    }

    
    /**
     * 获取：是否遗嘱保留
     */
    public Integer getLastWillRetain()
    {
        return lastWillRetain;
    }

    
    /**
     * 设置：是否遗嘱保留
     * 
     * @param i_LastWillRetain 是否遗嘱保留
     */
    public void setLastWillRetain(Integer i_LastWillRetain)
    {
        this.lastWillRetain = i_LastWillRetain;
    }

    
    /**
     * 获取：遗嘱消息
     */
    public String getLastWillPayload()
    {
        return lastWillPayload;
    }

    
    /**
     * 设置：遗嘱消息
     * 
     * @param i_LastWillPayload 遗嘱消息
     */
    public void setLastWillPayload(String i_LastWillPayload)
    {
        this.lastWillPayload = i_LastWillPayload;
    }

    
    /**
     * 获取：上线消息。首次上线或中断后再次上线均发送的消息。对应遗嘱消息是在离线时发送的功能
     */
    public String getOnLinePayload()
    {
        return onLinePayload;
    }

    
    /**
     * 设置：上线消息。首次上线或中断后再次上线均发送的消息。对应遗嘱消息是在离线时发送的功能
     * 
     * @param i_OnLinePayload 上线消息。首次上线或中断后再次上线均发送的消息。对应遗嘱消息是在离线时发送的功能
     */
    public void setOnLinePayload(String i_OnLinePayload)
    {
        this.onLinePayload = i_OnLinePayload;
    }

    
    /**
     * 获取：正常退出消息。遗嘱消息仅在意外断开时才生效。当正常关闭设备时不发消息，可用此属性配置退出消息
     */
    public String getExitPayload()
    {
        return exitPayload;
    }

    
    /**
     * 设置：正常退出消息。遗嘱消息仅在意外断开时才生效。当正常关闭设备时不发消息，可用此属性配置退出消息
     * 
     * @param i_ExitPayload 正常退出消息。遗嘱消息仅在意外断开时才生效。当正常关闭设备时不发消息，可用此属性配置退出消息
     */
    public void setExitPayload(String i_ExitPayload)
    {
        this.exitPayload = i_ExitPayload;
    }


    /**
     * 设置XJava池中对象的ID标识。此方法不用用户调用设置值，是自动的。
     * 
     * @param i_XJavaID
     */
    @Override
    public void setXJavaID(String i_XJavaID)
    {
        this.xid = i_XJavaID;
    }
    
    
    /**
     * 获取XJava池中对象的ID标识。
     * 
     * @return
     */
    @Override
    public String getXJavaID()
    {
        return this.xid;
    }


    /**
     * 获取：说明备注
     */
    @Override
    public String getComment()
    {
        return comment;
    }

    
    /**
     * 设置：说明备注
     * 
     * @param i_Comment 说明备注
     */
    @Override
    public void setComment(String i_Comment)
    {
        this.comment = i_Comment;
    }
    
}
