package org.hy.common.mqtt.junit;

import org.hy.common.mqtt.broker.BrokerConfig;
import org.hy.common.mqtt.broker.XBroker;
import org.hy.common.mqtt.client.subscribe.IMqttMessage;
import org.hy.common.mqtt.client.subscribe.IMqttMessageListener;
import org.hy.common.xml.log.Logger;
import org.junit.Test;





/**
 * 测试单元：MQTT基础功能的测试
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-02-26
 * @version     v1.0
 */
public class JU_MQTT
{
    private final Logger $Logger = new Logger(JU_MQTT.class ,true);
    
    

    @Test
    public void test_Publish()
    {
        BrokerConfig v_Borker = new BrokerConfig();
        
        v_Borker.setXid("XBroker");
        v_Borker.setProtocol("tcp://");
        v_Borker.setHost("10.1.20.105");
        v_Borker.setPort(7788);
        v_Borker.setMqttVersion("3.1.1");
        v_Borker.setLastWillTopic("hy/v3.1.1/will");
        v_Borker.setLastWillPayload("offline");
        v_Borker.setOnLinePayload("online");
        v_Borker.setExitPayload("exit");
        
        XBroker v_XBroker = new XBroker(v_Borker);
        
        v_XBroker.getMqttClient().connect();
        v_XBroker.getMqttClient().publish("hy/v3.1.1/getWeight" ,"1234");
    }
    
    
    
    @Test
    public void test_Publish_V5()
    {
        BrokerConfig v_Borker = new BrokerConfig();
        
        v_Borker.setXid("XBroker");
        v_Borker.setProtocol("tcp://");
        v_Borker.setHost("10.1.20.105");
        v_Borker.setPort(7788);
        v_Borker.setMqttVersion("5.0");
        v_Borker.setLastWillTopic("hy/v5.0/will");
        v_Borker.setLastWillPayload("offline");
        v_Borker.setOnLinePayload("online");
        v_Borker.setExitPayload("exit");
        
        XBroker v_XBroker = new XBroker(v_Borker);
        
        v_XBroker.getMqttClient().connect();
        v_XBroker.getMqttClient().publish("hy/v5.0/getWeight" ,"1234");
    }
    
    
    
    @Test
    public void test_subscribe() throws InterruptedException
    {
        BrokerConfig v_Borker = new BrokerConfig();
        
        v_Borker.setXid("XBroker");
        v_Borker.setProtocol("tcp://");
        v_Borker.setHost("10.1.20.105");
        v_Borker.setPort(7788);
        v_Borker.setMqttVersion("3.1.1");
        v_Borker.setLastWillTopic("hy/v3.1.1/will");
        v_Borker.setLastWillPayload("offline");
        v_Borker.setOnLinePayload("online");
        v_Borker.setExitPayload("exit");
        
        XBroker v_XBroker = new XBroker(v_Borker);
        
        v_XBroker.getMqttClient().connect();
        v_XBroker.getMqttClient().subscribe("hy/v3.1.1/iamok" ,1 , new IMqttMessageListener()
        {
            @Override
            public void messageArrived(String i_Topic ,IMqttMessage i_Message) throws Exception
            {
                System.out.println(i_Topic + " = " + new String(i_Message.getPayload()));
            }}
        );
        
        
        Thread.sleep(5 * 1000);
        $Logger.info("关闭");
        v_XBroker.getMqttClient().disconnect();
        
        
        Thread.sleep(5 * 1000);
        $Logger.info("重连");
        v_XBroker.getMqttClient().connect();
        v_XBroker.getMqttClient().subscribe("hy/v3.1.1/iamok" ,1 , new IMqttMessageListener()
        {
            @Override
            public void messageArrived(String i_Topic ,IMqttMessage i_Message) throws Exception
            {
                System.out.println(i_Topic + " = " + new String(i_Message.getPayload()));
            }}
        );
        
        Thread.sleep(60 * 1000 * 10);
    }
    
    
    
    @Test
    public void test_subscribe_V5() throws InterruptedException
    {
        BrokerConfig v_Borker = new BrokerConfig();
        
        v_Borker.setXid("XBroker");
        v_Borker.setProtocol("tcp://");
        v_Borker.setHost("10.1.20.105");
        v_Borker.setPort(7788);
        v_Borker.setMqttVersion("5.0");
        v_Borker.setLastWillTopic("hy/v5.0/will");
        v_Borker.setLastWillPayload("offline");
        v_Borker.setOnLinePayload("online");
        v_Borker.setExitPayload("exit");
        
        XBroker v_XBroker = new XBroker(v_Borker);
        
        v_XBroker.getMqttClient().connect();
        v_XBroker.getMqttClient().subscribe("hy/v5.0/iamok" ,1 , new IMqttMessageListener()
        {
            @Override
            public void messageArrived(String i_Topic ,IMqttMessage i_Message) throws Exception
            {
                System.out.println(i_Topic + " =1 " + new String(i_Message.getPayload()));
            }}
        );
        
        // 多次订阅
        v_XBroker.getMqttClient().subscribe("hy/v5.0/iamok" ,1 , new IMqttMessageListener()
        {
            @Override
            public void messageArrived(String i_Topic ,IMqttMessage i_Message) throws Exception
            {
                System.out.println(i_Topic + " =2 " + new String(i_Message.getPayload()));
            }}
        );
        
        
        Thread.sleep(5 * 1000);
        $Logger.info("断开");
        v_XBroker.getMqttClient().disconnect();
        
        
        Thread.sleep(5 * 1000);
        $Logger.info("重连");
        v_XBroker.getMqttClient().connect();
        v_XBroker.getMqttClient().subscribe("hy/v5.0/iamok" ,1 , new IMqttMessageListener()
        {
            @Override
            public void messageArrived(String i_Topic ,IMqttMessage i_Message) throws Exception
            {
                System.out.println(i_Topic + " = " + new String(i_Message.getPayload()));
            }}
        );
        
        Thread.sleep(5 * 1000);
        $Logger.info("关闭");
        v_XBroker.getMqttClient().close();
        
        Thread.sleep(60 * 1000 * 10);
    }
    
}
