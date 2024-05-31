package org.hy.common.mqtt.junit;

import org.hy.common.mqtt.broker.XBroker;
import org.hy.common.xml.XJava;
import org.hy.common.xml.annotation.XType;
import org.hy.common.xml.annotation.Xjava;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;





/**
 * 测试单元：通过代理发送请求
 *
 * @author      ZhengWei(HY)
 * @createDate  2024-05-31
 * @version     v1.0
 */
@Xjava(value=XType.XML)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JU_MQTT_Config
{
    
    private static boolean $isInit = false;
    
    
    
    public JU_MQTT_Config() throws Exception
    {
        if ( !$isInit )
        {
            $isInit = true;
            XJava.parserAnnotation(this.getClass().getName());
        }
    }
    
    
    
    @Test
    public void test_Publish_V311()
    {
        XBroker v_XBroker = (XBroker) XJava.getObject("XBroker_V311");
        
        v_XBroker.getMqttClient().connect();
        v_XBroker.getMqttClient().publish("hy/mqtt/getWeight" ,"v3.1.1" ,1 ,false);
        v_XBroker.getMqttClient().close();
    }
    
    
    
    @Test
    public void test_Publish_V5()
    {
        XBroker v_XBroker = (XBroker) XJava.getObject("XBroker_V5");
        
        v_XBroker.getMqttClient().connect();
        v_XBroker.getMqttClient().publish("hy/mqtt/getWeight" ,"v5.0" ,1 ,false);
        v_XBroker.getMqttClient().close();
    }
    
}
