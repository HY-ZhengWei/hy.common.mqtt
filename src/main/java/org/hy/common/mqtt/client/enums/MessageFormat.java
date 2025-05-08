package org.hy.common.mqtt.client.enums;





/**
 * 消息内容的格式
 *
 * @author      ZhengWei(HY)
 * @createDate  2025-05-08
 * @version     v1.0
 */
public enum MessageFormat
{
    
    Text("TEXT" ,"文本内容"),
    
    Hex ("HEX"  ,"16进制文本"),
    
    ;
    
    
    
    /** 值 */
    private String value;
    
    /** 描述 */
    private String comment;
    
    
    
    /**
     * 数值转为常量
     * 
     * @author      ZhengWei(HY)
     * @createDate  2024-07-29
     * @version     v1.0
     *
     * @param i_Value
     * @return
     */
    public static MessageFormat get(String i_Value)
    {
        if ( i_Value == null )
        {
            return null;
        }
        
        for (MessageFormat v_Enum : MessageFormat.values())
        {
            if ( v_Enum.value.equalsIgnoreCase(i_Value.trim()) )
            {
                return v_Enum;
            }
        }
        
        return null;
    }
    
    
    
    MessageFormat(String i_Value ,String i_Comment)
    {
        this.value   = i_Value;
        this.comment = i_Comment;
    }

    
    
    public String getValue()
    {
        return this.value;
    }
    
    
    
    public String getComment()
    {
        return this.comment;
    }
    
    

    @Override
    public String toString()
    {
        return this.value;
    }
    
}
