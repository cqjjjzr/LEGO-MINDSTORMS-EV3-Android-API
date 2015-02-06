package com.charlie.ev3;

/**
 * 属性更改事件监听器。
 * @author Charlie Jiang
 */
public interface PropertyChangedListener {
    /**
     * 处理属性更改事件。
     * @param port 事件源。
     */
    public void onPropertyChanged(Port port);
}
