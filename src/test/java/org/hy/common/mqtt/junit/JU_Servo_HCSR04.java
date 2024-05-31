package org.hy.common.mqtt.junit;

import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.hy.common.Help;
import org.hy.common.xml.log.Logger;





/**
 * 舵机与超声波组成的抓娃娃物联网实验
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-13
 * @version     v1.0
 */
public class JU_Servo_HCSR04 implements MqttCallback
{
    
    private static Logger $Logger = new Logger(JU_Servo_HCSR04.class ,true);
    
    
    /** 舵机的MQTT客户端 */
    private static MqttClient $Servo  = null;
    
    /** 超声波的MQTT客户端 */
    private static MqttClient $HCSR04 = null;
    
    /** 控制舵机的主题 */
    private static String     $ServoTopic       = "zhengwei/esp32/servo/E05A1BA70588/control";
    private static String     $ServoBrokerIP    = "192.168.31.250";
    private static int        $ServoBrokerPort  = 1883;
                              
    /** 超声波的消息 */
    private static String     $HCSR04Topic      = "zhengwei/esp32/hcsr04/A0B765DCB718/publish";
    private static String     $HCSR04BrokerIP   = "192.168.31.250";
    private static int        $HCSR04BrokerPort = 1883;
    
    private static int        $OpenClose        = 0;
    
    
    
    public static void main(String[] args)
    {
        try
        {
            $Servo = connect("连接舵机" ,$ServoBrokerIP ,$ServoBrokerPort ,true ,null);
            if ( $Servo == null )
            {
                $Logger.error("Servo is offline");
            }
            
            $HCSR04 = connect("连接超声波" ,$HCSR04BrokerIP ,$HCSR04BrokerPort ,true ,new JU_Servo_HCSR04());
            if ( $HCSR04 != null )
            {
                subscribe($HCSR04 ,$HCSR04Topic ,0);
            }
            
            //Thread.sleep(60 * 10 * 1000);
        }
        catch (Exception exce)
        {
            $Logger.error(exce);
        }
        /*
        finally
        {
            try
            {
                if ( $HCSR04 != null )
                {
                    $HCSR04.disconnect();
                }
            }
            catch (Exception exce)
            {
                $Logger.error(exce);
            }
            
            try
            {
                if ( $Servo != null )
                {
                    $Servo.disconnect();
                }
            }
            catch (Exception exce)
            {
                $Logger.error(exce);
            }
        }
        */
    }
    
    
    
    /**
     * 连接舵机的Broker服务器
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-13
     * @version     v1.0
     *
     * @param i_IP    服务器IP
     * @param i_Port  服务器端口号
     * @return
     */
    public static MqttClient servoConnect(String i_IP ,int i_Port)
    {
        String            v_Broker      = "tcp://" + i_IP + ":" + i_Port;
        String            v_ClientID    = Help.getMacs() + "_" + Help.random(8 ,true);
        MemoryPersistence v_Persistence = new MemoryPersistence();
        MqttClient        v_MQTTClient  = null;
        
        try
        {
            v_MQTTClient = new MqttClient(v_Broker, v_ClientID, v_Persistence);
            
            MqttConnectionOptions v_MQTTConnOpts = new MqttConnectionOptions();
            v_MQTTConnOpts.setCleanStart(false);
            
            System.out.println("Connecting to broker: "+ v_Broker);
            v_MQTTClient.connect(v_MQTTConnOpts);
            System.out.println("Connected");
        }
        catch (MqttException mqttExce)
        {
            $Logger.error("reason " + mqttExce.getReasonCode());
            $Logger.error("msg "    + mqttExce.getMessage());
            $Logger.error("loc "    + mqttExce.getLocalizedMessage());
            $Logger.error("cause "  + mqttExce.getCause());
            $Logger.error(mqttExce);
        }
        
        return v_MQTTClient;
    }
    
    
    
    /**
     * 连接Broker服务器
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-13
     * @version     v1.0
     *
     * @param i_IP            服务器IP
     * @param i_Port          服务器端口号
     * @param i_CleanSession  是否清除会话
     * @return
     */
    public static MqttClient connect(String i_Comment ,String i_IP ,int i_Port ,boolean i_CleanSession ,MqttCallback i_SubscribeCallBack)
    {
        String            v_Broker      = "tcp://" + i_IP + ":" + i_Port;
        String            v_ClientID    = Help.getMacs() + "_" + Help.random(8 ,true);
        MemoryPersistence v_Persistence = new MemoryPersistence();
        MqttClient        v_MQTTClient  = null;
        
        try
        {
            v_MQTTClient = new MqttClient(v_Broker, v_ClientID, v_Persistence);
            // 如果有订阅
            if ( i_SubscribeCallBack != null )
            {
                v_MQTTClient.setCallback(i_SubscribeCallBack);
            }
            
            MqttConnectionOptions v_MQTTConnOpts = new MqttConnectionOptions();
            v_MQTTConnOpts.setCleanStart(i_CleanSession);
            
            System.out.println("Connecting to broker: "+ v_Broker);
            v_MQTTClient.connect(v_MQTTConnOpts);
            System.out.println(i_Comment);
        }
        catch (MqttException mqttExce)
        {
            $Logger.error("reason " + mqttExce.getReasonCode());
            $Logger.error("msg "    + mqttExce.getMessage());
            $Logger.error("loc "    + mqttExce.getLocalizedMessage());
            $Logger.error("cause "  + mqttExce.getCause());
            $Logger.error(mqttExce);
        }
        
        return v_MQTTClient;
    }
    
    
    
    /**
     * MQTT发布消息
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-13
     * @version     v1.0
     *
     * @param i_MQTTClient  MQTT客户端对象
     * @param i_Topic       发布消息的主题
     * @param i_QoS         服务质量等级
     * @param i_Message     消息内容
     * @return
     */
    public static boolean publish(MqttClient i_MQTTClient ,String i_Topic ,int i_QoS ,String i_Message)
    {
        try
        {
            $Logger.info("Publishing message: "+ i_Message);
            
            MqttMessage v_Payload = new MqttMessage(i_Message.getBytes());
            v_Payload.setQos(i_QoS);
            i_MQTTClient.publish(i_Topic, v_Payload);
            
            $Logger.info("Message published");
            return true;
        }
        catch (MqttException mqttExce)
        {
            $Logger.error("reason " + mqttExce.getReasonCode());
            $Logger.error("msg "    + mqttExce.getMessage());
            $Logger.error("loc "    + mqttExce.getLocalizedMessage());
            $Logger.error("cause "  + mqttExce.getCause());
            $Logger.error(mqttExce);
        }
        
        return false;
    }
    
    
    
    /**
     * 订阅消息
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-02-13
     * @version     v1.0
     *
     * @param i_MQTTClient       MQTT客户端对象
     * @param i_Topic            订阅消息的主题
     * @param i_QoS              服务质量等级
     * @param i_MessageListener  订阅消息的监听器
     * @return
     */
    public static boolean subscribe(MqttClient i_MQTTClient ,String i_Topic ,int i_QoS)
    {
        try
        {
            i_MQTTClient.subscribe(i_Topic ,i_QoS);
            return true;
        }
        catch (MqttException exce)
        {
            $Logger.error(exce);
        }
        
        return false;
    }



    @Override
    public void connectComplete(boolean reconnect ,String serverURI)
    {
        $Logger.info("Connection established with the broker");
    }



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
    public void messageArrived(String i_Topic ,MqttMessage i_MQTTMessage) throws Exception
    {
        String v_Message = new String(i_MQTTMessage.getPayload());
        int    v_Value   = Integer.parseInt(v_Message.split(" ")[0]);
        
        synchronized ( this )
        {
            if ( v_Value <= 50 )
            {
                if ( $OpenClose != -1 )
                {
                    $OpenClose = -1;
                    System.out.println("抓娃娃");
                    publish($Servo ,$ServoTopic ,2 ,"23");
                }
            }
            else if ( v_Value > 50 )
            {
                if ( $OpenClose != 1 )
                {
                    $OpenClose = 1;
                    System.out.println("放开");
                    publish($Servo ,$ServoTopic ,2 ,"0");
                }
            }
        }
    }



    @Override
    public void deliveryComplete(IMqttToken token)
    {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void authPacketArrived(int reasonCode ,MqttProperties properties)
    {
        // TODO Auto-generated method stub
        
    }
    
}
